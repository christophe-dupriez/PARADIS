package be.labo.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.exp.ExpressionParameter;
import org.apache.cayenne.query.SelectQuery;

import be.labo.auxil.CategoryInterval;
import be.labo.data.auto._Rawdata17;

public class Rawdata17 extends _Rawdata17 {

	   /**
	 * 
	 */
	private static final long serialVersionUID = -1550794729938583093L;
	
	static public final byte recordType = 17;
	   static public final String description = "Water Level Data";
	   static public Rawtables rawTable = null;
	   static private SelectQuery select17 = null; // Predefined SQL query to get existing measures for a raw record
	   
	   static public TreeMap<String,RawDataFields> rawFields = new TreeMap<String,RawDataFields>();
	   
	   private synchronized void initStaticData17(ObjectContext context) {
		    rawTable = Rawtables.ensure(context,recordType,description);

		    rawFields.put(Rawdata17.XBEEADDR_PROPERTY,rawTable.ensureField((byte)9,(byte)32,Rawdata17.XBEEADDR_PROPERTY));
			rawFields.put(Rawdata17.STRUCTID_PROPERTY,rawTable.ensureField((byte)9,(byte)5,Rawdata17.STRUCTID_PROPERTY));
			rawFields.put(Rawdata17.TIMEVALIDITY_PROPERTY,rawTable.ensureField((byte)9,(byte)3,Rawdata17.TIMEVALIDITY_PROPERTY));
			rawFields.put(Rawdata17.CPUMAC_PROPERTY,rawTable.ensureField((byte)9,(byte)24,Rawdata17.CPUMAC_PROPERTY));
			rawFields.put(Rawdata17.TIMESTAMP_PROPERTY,rawTable.ensureField((byte)9,(byte)32,Rawdata17.TIMESTAMP_PROPERTY));
			rawFields.put(Rawdata17.SLOT_PROPERTY,rawTable.ensureField((byte)9,(byte)8,Rawdata17.SLOT_PROPERTY));
			rawFields.put(Rawdata17.FAMILY_PROPERTY,rawTable.ensureField((byte)9,(byte)4,Rawdata17.FAMILY_PROPERTY));
			rawFields.put(Rawdata17.SENTLAN_PROPERTY,rawTable.ensureField((byte)9,(byte)1,Rawdata17.SENTLAN_PROPERTY));
			rawFields.put(Rawdata17.SENDINGWAN_PROPERTY,rawTable.ensureField((byte)9,(byte)1,Rawdata17.SENDINGWAN_PROPERTY));
			rawFields.put(Rawdata17.RELAYEDGO_PROPERTY,rawTable.ensureField((byte)9,(byte)1,Rawdata17.RELAYEDGO_PROPERTY));
			rawFields.put(Rawdata17.RELAYEDRETURN_PROPERTY,rawTable.ensureField((byte)9,(byte)1,Rawdata17.RELAYEDRETURN_PROPERTY));

			rawFields.put(Rawdata17.XBEERSSI_PROPERTY,rawTable.ensureField((byte)7,(byte)8,Rawdata17.XBEERSSI_PROPERTY));
			rawFields.put(Rawdata17.GSMRSSI_PROPERTY,rawTable.ensureField((byte)8,(byte)5,Rawdata17.GSMRSSI_PROPERTY)); // "gsmrssi";
			rawFields.put(Rawdata17.GSMBER_PROPERTY,rawTable.ensureField((byte)8,(byte)3,Rawdata17.GSMBER_PROPERTY)); // "gsmber";
			rawFields.put(Rawdata17.USBNOTCONNECT_PROPERTY,rawTable.ensureField((byte)6,(byte)1,Rawdata17.USBNOTCONNECT_PROPERTY)); // "usbnotconnect";
			rawFields.put(Rawdata17.SOLARPANELOK_PROPERTY,rawTable.ensureField((byte)6,(byte)1,Rawdata17.SOLARPANELOK_PROPERTY)); // "solarpanelok";
			rawFields.put(Rawdata17.POWERFILTEROK_PROPERTY,rawTable.ensureField((byte)6,(byte)1,Rawdata17.POWERFILTEROK_PROPERTY)); // "powerfilterok";
			rawFields.put(Rawdata17.VOLTSBATTOK_PROPERTY,rawTable.ensureField((byte)6,(byte)1,Rawdata17.VOLTSBATTOK_PROPERTY)); // "voltsbattok";
			rawFields.put(Rawdata17.LIPOCHARGINGOK_PROPERTY,rawTable.ensureField((byte)6,(byte)1,Rawdata17.LIPOCHARGINGOK_PROPERTY)); // "lipochargingok";
			rawFields.put(Rawdata17.LIPOCHARGING_PROPERTY,rawTable.ensureField((byte)6,(byte)1,Rawdata17.LIPOCHARGING_PROPERTY)); // "lipocharging";

			rawFields.put(Rawdata17.XBEEON_PROPERTY,rawTable.ensureField((byte)7,(byte)1,Rawdata17.XBEEON_PROPERTY)); // "xbeeon";
			rawFields.put(Rawdata17.XBEEASSOC_PROPERTY,rawTable.ensureField((byte)7,(byte)1,Rawdata17.XBEEASSOC_PROPERTY)); // "xbeeassoc";
			rawFields.put(Rawdata17.GSMON_PROPERTY,rawTable.ensureField((byte)8,(byte)1,Rawdata17.GSMON_PROPERTY)); // "gsmon";
			rawFields.put(Rawdata17.GSMLAST_OK_PROPERTY,rawTable.ensureField((byte)8,(byte)1,Rawdata17.GSMLAST_OK_PROPERTY)); // "gsmlastOk";
			rawFields.put(Rawdata17.GSMLASTRETRIES_PROPERTY,rawTable.ensureField((byte)8,(byte)4,Rawdata17.GSMLASTRETRIES_PROPERTY)); // "gsmlastretries";

			rawFields.put(Rawdata17.CHANNEL1OK_PROPERTY,rawTable.ensureField((byte)2,(byte)1,Rawdata17.CHANNEL1OK_PROPERTY)); // "channel1ok";
			rawFields.put(Rawdata17.CHANNEL2OK_PROPERTY,rawTable.ensureField((byte)2,(byte)1,Rawdata17.CHANNEL2OK_PROPERTY)); // "channel2ok";
			rawFields.put(Rawdata17.LED1_PROPERTY,rawTable.ensureField((byte)2,(byte)2,Rawdata17.LED1_PROPERTY)); // "led1";
			rawFields.put(Rawdata17.DOORALWAYSCLOSED_PROPERTY,rawTable.ensureField((byte)2,(byte)1,Rawdata17.DOORALWAYSCLOSED_PROPERTY)); // "dooralwaysclosed";
			rawFields.put(Rawdata17.LEDALWAYSOFF_PROPERTY,rawTable.ensureField((byte)2,(byte)1,Rawdata17.LEDALWAYSOFF_PROPERTY)); // "ledalwaysoff";

			rawFields.put(Rawdata17.WATERLEVEL_PROPERTY,rawTable.ensureField((byte)1,(byte)32,Rawdata17.WATERLEVEL_PROPERTY)); // "waterlevel";
			rawFields.put(Rawdata17.TEMPERATURE_PROPERTY,rawTable.ensureField((byte)1,(byte)16,Rawdata17.TEMPERATURE_PROPERTY)); // "temperature";
			rawFields.put(Rawdata17.AMP5V_PROPERTY,rawTable.ensureField((byte)6,(byte)16,Rawdata17.AMP5V_PROPERTY)); // "amp5v";
			rawFields.put(Rawdata17.VOLTSBATT_PROPERTY,rawTable.ensureField((byte)6,(byte)16,Rawdata17.VOLTSBATT_PROPERTY)); // "voltsbatt";
			rawFields.put(Rawdata17.VOLTSSOL_PROPERTY,rawTable.ensureField((byte)6,(byte)16,Rawdata17.VOLTSSOL_PROPERTY)); // "voltssol";

			List<Expression> listF = new ArrayList<Expression>();
			for (RawDataFields aField : rawFields.values()) {
				listF.add(ExpressionFactory.matchExp(Measure.MEASURE_FIELD_PROPERTY, 
				         aField.getPK()));
			}
			Expression fields = ExpressionFactory.joinExp(Expression.OR, listF);
			List<Expression> list = new ArrayList<Expression>();
			list.add(fields);
			list.add(ExpressionFactory.matchExp(Measure.RAWRECORD_PROPERTY, 
			         new ExpressionParameter("rawrec")));
			Expression selectExp = ExpressionFactory.joinExp(Expression.AND, list);
			select17 = new SelectQuery(Measure.class, selectExp);
	   }
	   
	   /** Read-only access to PK */
	   public Long getPK() {
		   return DataObjectUtils.longPKForObject(this);
	   }
	   
	   static public Rawdata17 find(Date time, Machine aMachine) {
		   if (aMachine == null) return null;
		   if (time == null) return null;

		   ObjectContext context = aMachine.getObjectContext();
		   List<Expression> list = new ArrayList<Expression>();
		   list.add(ExpressionFactory.matchExp(CPUMAC_PROPERTY, 
				   aMachine.getMachinemac()));
		   list.add(ExpressionFactory.matchExp(TIMESTAMP_PROPERTY, 
				   time));
		   SelectQuery queryMeasures = new SelectQuery(Rawdata17.class,ExpressionFactory.joinExp(Expression.AND, list));
		   List<Rawdata17> measures = context.performQuery(queryMeasures);
		   if (measures == null || measures.isEmpty()) return null;
		   return measures.get(0);   
	   }
	   
	   public int generateMeasures(Machine aMachine) {
		   ObjectContext context = this.getObjectContext();

		   if (rawTable == null) initStaticData17(context);

		   // Remove any previously generated measures
		   Map<String,Object> params = new HashMap<String,Object>();
		   params.put("rawrec", this.getPK());
		   SelectQuery queryOldMeasures = select17.queryWithParameters(params);
		   List<Measure> measures = context.performQuery(queryOldMeasures);
		   boolean someDeletes = false;
		   if (measures != null && !measures.isEmpty()) { // Remove old measures
			   context.deleteObjects(measures);
			   someDeletes = true;
		   }
		   Place machinePlace = aMachine.getMachinePlace();

		   List<SubType> stm = aMachine.getMachineSubTypes();
		   int stored = 0;
		   for (SubType aSubType: stm) {
			   RawTableField aSTField = aSubType.getSubtypeField();
			   if (aSTField == null) continue; // In case SubType not linked to a data structure
			   String fieldName = aSTField.getTableField().getDescription();
			   if (rawFields.keySet().contains(fieldName)) {
				   Number aNumber = null;
				   Double aValue = null;
				   Object o = this.readProperty(fieldName);
				   if (o instanceof Boolean) {
					   aNumber = (Boolean)o ? 1 : 0 ;					   
				   } else {
					   aNumber = (Number) this.readProperty(fieldName);
					   if (aNumber instanceof Long) aValue = 0.0d + (Long)aNumber;
					   else if (aNumber instanceof Integer) aValue = 0.0d + (Integer)aNumber;
					   else if (aNumber instanceof Short) aValue = 0.0d + (Short)aNumber;
					   else if (aNumber instanceof Byte) aValue = 0.0d + (Byte)aNumber;
					   else if (aNumber instanceof Double) aValue = (Double)aNumber; 
					   else if (aNumber instanceof Float) aValue = 0.0d + (Float)aNumber;
				   }
				   if (aValue == null) continue; // Desired value is not available: skip
				   Date theTimestamp = this.getTimestamp();
				   Scaling theScale = aSubType.getSubtypeScaling(theTimestamp);
				   if (theScale != null) {
					   aValue = theScale.scale(aValue);
					   if (aValue == null) continue; // Value is not scalable: skip
				   }
				   DataType aType = aSubType.getOwner();
				   if (aType != null) {
					   Byte nbDecimals = aType.getDecimals();
					   if (nbDecimals != null && nbDecimals < 10) {
						   double factor = Math.pow(10, nbDecimals);
						   long trunquer = (long)(aValue*factor);
						   aValue = ((double)trunquer)/factor;
					   }
				   }

				   //byte catNum = CategoryInterval.find(aSubType.getCategories(), aValue);
				   CategoryInterval ci = CategoryInterval.findInterval(aSubType.getCategories(), aValue);
				   if (ci != null) { // valid measure
					   stored++;
					   Measure aMeasure = (Measure)context.newObject(Measure.class);
					   aMeasure.setWhenmeasured(theTimestamp);

					   aMeasure.setMeasureMachine(aMachine);
					   Place aPlace = aSubType.getSubtypePlace();
					   if (aPlace == null) aPlace = machinePlace;
					   aMeasure.setMeasurePlace(aPlace);

					   //aMeasure.setCategory(catNum);
					   aMeasure.setMeasureCategory(ci.getLabel());
					   aMeasure.setMeasureField(aSTField);
					   aMeasure.setRawrecord(this.getPK());
					   aMeasure.setMeasureType(aSubType.getOwner());
					   aMeasure.setValue(aValue);
				   }
			   }
		   }
		   if (stored > 0 || someDeletes) {
			   context.commitChanges();   
		   }
		   return stored;
	   }
}
