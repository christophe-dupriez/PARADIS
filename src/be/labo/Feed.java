package be.labo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.ObjectId;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.ObjectIdQuery;
import org.apache.cayenne.query.SelectQuery;

import be.labo.auxil.AuxilServlet;
import be.labo.auxil.XBeeAddress;
import be.labo.data.DataType;
import be.labo.data.Machine;
import be.labo.data.Measure;
import be.labo.data.Rawdata17;
import be.labo.util.NoZero;
import be.labo.util.RTCvalue;

/**
 * Servlet implementation class Feed
 */
@WebServlet("/Feed")
public class Feed extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final int MAX_FRIENDS = 6; 
	public static final int PAYLOAD_SIZE = 100;

	
	// For preconfigured routing AND GPRS transmission, "I am here!"
	public static final int SENDER_ID = 31;
	// Parameter: current version of the configuration of the emitter (0 if none!)
	// Sent by GPRS to identify sender and provide information to check the token (protection against spoofing the system)
	// Sent after some listening and preferably sent after a delay after a "end of transmission" message
	// if nobody else is emitting

	// For preconfigured routing, "Welcome amongst us!"
	public static final int RADIO_ADDRESS_ID = 30;
	// Parameter: table of the known correspondances between MAC XBee (netAddress) and MAC CPU (machineId) to help the newcomer to find its place in the network

	// Measures to be uploaded to the Web server
	public static final int MEASURE_ID = Rawdata17.recordType;
	public static final int EEPROM_CONFIG_ID = 15;
	public static final int EEPROM_FUTURE_CONFIG_ID = 14;
	// Both have three parameters:
	// 1) number of nodes
	// 2) table of MAC CPU+End position in the list of "next".
	// 3) list of "next" nodes (one byte per "next" with their position in table 2)
	public static final int EEPROM_WEB_CONFIG_ID = 13;
	//	    port number (16 bits)
	//	    server domain 0x00
	//	    path 0x00
	//	    any necessary future parameters...
	public static final int ACK_MEASURE_ID = 1;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Feed() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	static Measure storeval(HttpServletRequest request, DataContext context, Date whenMeasured, Machine machine, int dataType, double value) {
		ObjectId idType = new ObjectId("Type", DataType.ID_PK_COLUMN, dataType);
		// this constructor implicitly uses "CACHE_REFRESH" policy, so a fresh object will be returned
		ObjectIdQuery query = new ObjectIdQuery(idType);
		DataType aType = (DataType) DataObjectUtils.objectForQuery(context, query);
		if (aType != null) {
			Measure mess = (Measure)context.newObject(Measure.class);
			mess.setMeasureType(aType);
			mess.setWhenmeasured(whenMeasured);
			mess.setMeasureMachine(machine);
			mess.setValue(value);
			context.commitChanges();
			return mess;
		}
		else {
			request.setAttribute("error", "Type "+dataType+" unknown.");
			return null;
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ObjectContext context = AuxilServlet.init(request,response, null/*accessedClass*/);

		TreeMap<XBeeAddress,TreeSet<Integer>> confirmations = new TreeMap<XBeeAddress,TreeSet<Integer>>(); 
		request.setAttribute("confirmations", confirmations);
		request.setAttribute("error", "processing aborted?");
		String cumulErr = "";

		String scale = request.getParameter("rescale");
		if (scale != null && !scale.isEmpty()) {
			SelectQuery query = new SelectQuery(Rawdata17.class);
			List<Rawdata17> raws = context.performQuery(query);
			for (Rawdata17 raw : raws) {
				SelectQuery queryMac = new SelectQuery(Machine.class,ExpressionFactory.matchExp(Machine.MACHINEMAC_PROPERTY,raw.getCpumac()));
				//TODO: The query should also take deviceType into account
				List<Machine> someMachines = context.performQuery(queryMac);
				if (someMachines != null && !someMachines.isEmpty()) {
					Machine currMachine = someMachines.get(0);
					raw.generateMeasures(currMachine);
				}
			}
		}

		String data =request.getParameter("data");
		//request.setAttribute("error", "Data="+data);
		Machine thisMachine = null;
		if (data != null && !data.isEmpty()) {
			// Deviation from standard Base64 for better compatibility with URLs
			data = data.replace('-','=').replace('_','/').replace('.','+');
			byte[] binData = DatatypeConverter.parseBase64Binary(data);
			//request.setAttribute("error", "binData[0]="+binData[0]);

			ByteBuffer encoded = ByteBuffer.wrap(binData);
			encoded.order(ByteOrder.LITTLE_ENDIAN).position(0); // TRES IMPORTANT!
			int precPos = -1;
			double value = 0;
			int macMsb = 0;
			int macLsb = 0;
			Date whenMeasured = null;
			int baseMillis = 0;
			List<Measure> measures = new LinkedList<Measure>();
			request.setAttribute("measures", measures);

			while (encoded.position() > precPos && encoded.position() < encoded.limit()) {
				precPos = encoded.position();
				/*String x = "";
				for (int i=0;i<encoded.limit();i++) {
					x += Integer.toString(encoded.get(i) & 0xFF,16);
					x += " ";
				}*/
				ByteBuffer decoded = NoZero.decode(encoded);
				if (decoded == null || decoded.limit() < 2) {
					break; // Encoding error!
				}
				// Decode header
				/*   Header présent dans toutes les transactions (y compris l'acknowledge)
    uint32_t netAddress; // SL of emitting XBee Address; IPV4 address in wired network
    unsigned structId : 5;      // 1:structure d'acknowledge, 17 : données.
    // Pour le CAN, les différents PDO pour les données:
    //100+Node-Id=Timestamp, 180+Node-Id=Statuses and Voltages, 280+Node-Id=Water level(s)+Digital Inputs, 380=Network Statuses and RSSI, 400=Digital Outputs, 480=MAC address?
            //NON: on va simplement découper et numéroter les paquets pour le CAN: 0F, 1F .. FF
            // et tout garder en un seul morceau sinon
    //timevalidity; // Validity level of RTC date/time
    unsigned timevalidity : 3;
    //Each module has a unique identifier derived from its Ethernet MAC address or other serial number
    unsigned machineId : 24;
    uint32_t timestamp;     // RTCint date/time of measure
      // avec le CAN, devient le paquet système TIMESTAMP ? Comment faire? les 100+NodeId ne sont pas définis! Utiliser SYNC plutôt?
    //uint8_t networkSync
    unsigned slotId:8; // RF: slot-id when sending to share time-space, CAN: Node-Id de 7 bits à programmer dans l'EPROM
    //uint8_t deviceFamily; // 0:PIC32 with Microchip MAC address + Digi RF network MAC address
    unsigned family:4;
    unsigned sentLAN:1; // Already sent(resent) at application level? (normally 0 in received records; 1 in memory AFTER sending+receiving ACK)
    unsigned sendingWAN:1; // Currently sent by WAN interface (waiting confirmation) INTERNAL USE. For Fixed Routing, indicates LAST TRANSMISSION
    unsigned relayedGO:1;  // Set AFTER sending to indicate in "Cascading" topologies that relaying in 1st direction has been done
    unsigned relayedRETURN:1;  // Set AFTER sending to indicate in "Cascading" topologies that relaying in return direction has been done
// 14 BYTES IN HEADER
				 */
				decoded.position(0);
				macLsb = decoded.getInt();
				//macMsb = decoded.getInt();
				XBeeAddress xba = new XBeeAddress(/*macMsb*/0, macLsb);
				macMsb = xba.getMsb(); // Compression of zeros...

				byte fifthByte = decoded.get();
				byte recType = (byte) (fifthByte & 0x1F);
				byte dateType = (byte) ((fifthByte >>> 5) & 0x07); // UNSIGNED SHIFT: Seems to be rotating!

				byte buff[] = new byte[4]; // to decode 24 bits without problems with signed bytes of Java!
				buff[0] = decoded.get();
				buff[1] = decoded.get();
				buff[2] = decoded.get();
				buff[3] = 0;
				int machineId = ByteBuffer.wrap(buff).order(ByteOrder.LITTLE_ENDIAN).getInt();

				if (machineId != 0) {

					int timestamp = decoded.getInt();
					RTCvalue rv = new RTCvalue();
					rv.setInt(timestamp);
					rv.setValidity(dateType);
					whenMeasured = rv;

					byte thirtiethByte = decoded.get(); //slot Id
					byte fourthiethByte = decoded.get(); //devices families. For now only Microchip PIC32/Ethernet + Digi XBee supported (0)
					byte deviceType = (byte) (fourthiethByte & 0x0F);

					SelectQuery queryMac = new SelectQuery(Machine.class,ExpressionFactory.matchExp(Machine.MACHINEMAC_PROPERTY, machineId));
					//TODO: The query should also take deviceType into account
					List<Machine> someMachines = context.performQuery(queryMac);
					if (someMachines != null && !someMachines.isEmpty()) {
						Machine currMachine = someMachines.get(0);
						// Decode specific record
						switch(recType) {
						case SENDER_ID: {
							thisMachine = currMachine;
							int routingTimestamp = decoded.getInt();
							int webHostTimestamp = decoded.getInt();
							int manualTimestamp = decoded.getInt();
							break;
						}
						case MEASURE_ID: {
							if (Rawdata17.find(whenMeasured,currMachine) == null) { // DON'T STORE TWO MEASURES AT A GIVEN TIMESTAMP
								/*

// Données propre a la structure 17; 
	//uint8_t powerStatus;    // 1:USB not connected, 2:solar panel OK, 4:power filter OK, 8:VBAT OK, 16:LIPO charging enabled, 32:LIPO charging, 64:, 128:
	unsigned usb_not_connected : 1;
	unsigned solar_panel_OK : 1;
	unsigned power_filter_OK : 1;
	unsigned voltsBatt_OK : 1;
	unsigned lipo_charging_OK : 1;
	unsigned lipo_charging : 1;
	unsigned dummyFiller2 : 2;
	//uint8_t networkStatus;  // 1:XBee ON, 2:XBee Network ON (ASSOC), 4:GSM ON, 8:Last GSM communication OK, 4 higher bits = number of GSM retries (up to now)
	unsigned xbee_ON : 1;
	unsigned xbee_ASSOC : 1;
	unsigned gsm_ON : 1;
	unsigned gsm_last_OK : 1;
	unsigned gsm_last_retries : 4;
	//uint8_t rssiXbee;		// Best XBEE transmission quality (DB) for this data
	unsigned xbee_rssi : 8;
	//uint8_t rssiSIM;		// Last GSM communication quality indicator (CSQ) since X minutes (5 bits pour le RSSI/RxLev + 3 bits pour le BER/RxQual)
	unsigned gsm_rssi : 5;
	unsigned gsm_ber : 3;
	//uint8_t measureStatus;  // 1:Channel 1 (Paratronic) OK, 2:Channel 2 OK
	unsigned channel1_OK : 1;
	unsigned channel2_OK : 1;
	unsigned led1 : 1;  // 1: LED1
	unsigned door_always_closed : 1; // Door always closed since last measure
	unsigned led_always_OFF : 1; // LED panel always OFF since last measure
    unsigned dummyFiller3 : 3;
    uint8_t dummyFiller4; // Filler to get to int border (20 bytes)
	int waterLevel; 		// The goal of this sytem: water level on the river or the lake
	  // sera ramené au nombre de bits de l'ADC (16 ou 18?)
	int16_t temperature;	// Temperature inside the module
	  // sera ramené au nombre de bits de l'ADC (10)
	int16_t amp5V;
	  // sera ramené au nombre de bits de l'ADC (10)
	int16_t voltsBatt;		// Voltage of main battery
	  // sera ramené au nombre de bits de l'ADC (10)
	int16_t voltsSol;		// Voltage from solar panel
	  // sera ramené au nombre de bits de l'ADC (10)
} Measure;
								 */
								byte powerStatus = decoded.get();
								byte networkStatus = decoded.get();
								byte rssiXbee = decoded.get();
								byte rssiSIM = decoded.get();
								byte measureStatus = decoded.get();
								byte twentiethByte = decoded.get();

								int level = decoded.getInt(); 
								short temperature = decoded.getShort(); // temperature
								short amp5V = decoded.getShort();
								short voltsBatt = decoded.getShort();
								short voltsSol = decoded.getShort();

								Rawdata17 raw17 = (Rawdata17)context.newObject(Rawdata17.class);


								raw17.setXbeeaddr(macLsb);
								raw17.setStructid(recType);
								raw17.setTimevalidity(dateType);
								raw17.setCpumac(machineId);
								raw17.setTimestamp(whenMeasured);

								raw17.setSlot(thirtiethByte);
								raw17.setFamily(deviceType);
								fourthiethByte = (byte) (fourthiethByte >>> 4);
								raw17.setSentlan(1==(fourthiethByte & 1));
								fourthiethByte = (byte) (fourthiethByte >>> 1);
								raw17.setSendingwan(1==(fourthiethByte & 1));
								fourthiethByte = (byte) (fourthiethByte >>> 1);
								raw17.setRelayedgo(1==(fourthiethByte & 1));
								fourthiethByte = (byte) (fourthiethByte >>> 1);
								raw17.setRelayedreturn(1==(fourthiethByte & 1));

								raw17.setXbeerssi(rssiXbee);
								raw17.setGsmrssi((byte) (rssiSIM & 0x1F));
								raw17.setGsmber((byte) (rssiSIM >>> 5));

								raw17.setUsbnotconnect(1==(powerStatus & 1));
								powerStatus = (byte) (powerStatus >>> 1);
								raw17.setSolarpanelok(1==(powerStatus & 1));
								powerStatus = (byte) (powerStatus >>> 1);
								raw17.setPowerfilterok(1==(powerStatus & 1));
								powerStatus = (byte) (powerStatus >>> 1);
								raw17.setVoltsbattok(1==(powerStatus & 1));
								powerStatus = (byte) (powerStatus >>> 1);
								raw17.setLipochargingok(1==(powerStatus & 1));
								powerStatus = (byte) (powerStatus >>> 1);
								raw17.setLipocharging(1==(powerStatus & 1));

								raw17.setXbeeon(1==(networkStatus & 1));
								networkStatus = (byte) (networkStatus >>> 1);
								raw17.setXbeeassoc(1==(networkStatus & 1));
								networkStatus = (byte) (networkStatus >>> 1);
								raw17.setGsmon(1==(networkStatus & 1));
								networkStatus = (byte) (networkStatus >>> 1);
								raw17.setGsmlastOk(1==(networkStatus & 1));
								networkStatus = (byte) (networkStatus >>> 1);
								raw17.setGsmlastretries(networkStatus);

								raw17.setChannel1ok(1==(measureStatus & 1));
								measureStatus = (byte) (measureStatus >>> 1);
								raw17.setChannel2ok(1==(measureStatus & 1));
								measureStatus = (byte) (measureStatus >>> 1);
								raw17.setLed1(1==(measureStatus & 1));
								measureStatus = (byte) (measureStatus >>> 1);
								raw17.setDooralwaysclosed(1==(measureStatus & 1));
								measureStatus = (byte) (measureStatus >>> 1);
								raw17.setLedalwaysoff(1==(measureStatus & 1));

								raw17.setWaterlevel(level);
								raw17.setTemperature(temperature);
								raw17.setAmp5v(amp5V);
								raw17.setVoltsbatt(voltsBatt);
								raw17.setVoltssol(voltsSol);

								context.commitChanges();
								raw17.generateMeasures(currMachine);
								/*
							Feed.storeval(request, context, whenMeasured, currMachine,1,value);
							Feed.storeval(request, context, whenMeasured, currMachine,2,level);
							Feed.storeval(request, context, whenMeasured, currMachine,3,amp5V);
							Feed.storeval(request, context, whenMeasured, currMachine,4,voltsBatt);
							Feed.storeval(request, context, whenMeasured, currMachine,5,voltsSol);
							Feed.storeval(request, context, whenMeasured, currMachine,6,rssiXbee);
							Feed.storeval(request, context, whenMeasured, currMachine,7,rssiSIM & 0x1F); // UNSIGNED Shift  & 0x1F : 5 bits at the left!
							Feed.storeval(request, context, whenMeasured, currMachine,8,(networkStatus >>> 4) & 0x0F); // GSM Retries
							Feed.storeval(request, context, whenMeasured, currMachine,9,(networkStatus >>> 3) & 0x01); // GSM last OK?
							Feed.storeval(request, context, whenMeasured, currMachine,10,powerStatus & 0x01); // USB not connected?
								 */
								TreeSet<Integer> knownTimestamps = confirmations.get(xba);
								if (knownTimestamps == null) {
									knownTimestamps = new TreeSet<Integer>();
									confirmations.put(xba, knownTimestamps);
								}
								//This ONLY if some measures where NEW in the database...
								knownTimestamps.add(timestamp);
								//cumulErr += "OK: data stored.<br/>";
							} else {
								cumulErr += "Machine id." + Integer.toHexString(machineId) + ", timestamp="+whenMeasured+" already stored.<br/>";
							}
							break;
						}
						default: {
							cumulErr += "Unknown Record Type="+recType+"<br/>";
						}
						}
					} else {
						cumulErr += "Machine id." + Integer.toHexString(machineId) + ", XBee="+xba+" unknown.<br/>";
					}
				} else {
					cumulErr += "Machine id. is null.<br/>";
				}
			} // end while
		}
		LinkedList<String> confirmStrings = new LinkedList<String>(); 
		request.setAttribute("encodedConfirmations", confirmStrings);

		for (XBeeAddress xba: confirmations.keySet() ) {
		TreeSet<Integer> times = confirmations.get(xba);
		if (times.size() > 0) {
			byte[] answerBuf = new byte[PAYLOAD_SIZE];
			ByteBuffer answer = ByteBuffer.wrap(answerBuf).order(ByteOrder.LITTLE_ENDIAN);
			answer.putInt(xba.getLsb());
			answer.put((byte) ACK_MEASURE_ID); // ACK of records at a given timestamp + time validity
			if (thisMachine == null) { // the current machine is not known
				answer.put((byte) 0); // machineId
				answer.put((byte) 0); // "
				answer.put((byte) 0); // "
			} else {
				byte buff[] = new byte[4]; // to decode 24 bits without problems with signed bytes of Java!
				ByteBuffer.wrap(buff).order(ByteOrder.LITTLE_ENDIAN).putInt(thisMachine.getMachinemac());
				answer.put(buff[0]); // machineId
				answer.put(buff[1]); // "
				answer.put(buff[2]); // "				
			}
			answer.putInt(times.first());
			answer.put((byte) 0);
			answer.put((byte) 0);
			// end of header
			boolean first = true;
			for (int aTime : times) {
				if (first) {
					first = false;
					continue;
				}
				if (answer.position() >= (PAYLOAD_SIZE-8/*size of 2 int*/)) {
					break; // Do not make a too big payload
				}
				answer.putInt(aTime);
			}
			answer.limit(answer.position());
			ByteBuffer answerCompressed = NoZero.encode(answer);
			answerCompressed.rewind();
			byte[] to64 = new byte[answerCompressed.remaining()];
			answerCompressed.get(to64);
			String toSend = DatatypeConverter.printBase64Binary(to64);
			toSend = toSend.replace('=','-').replace('/','_').replace('+','.'); // Adjust Base64 for URL transmission
			confirmStrings.add(toSend);
		}
	}

	request.setAttribute("error", cumulErr);
	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/feed.jsp");
	dispatcher.forward(request,response);
}

}
