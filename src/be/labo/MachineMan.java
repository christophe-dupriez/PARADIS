package be.labo;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import be.labo.auxil.AuxilServlet;
import be.labo.auxil.Crumb;
import be.labo.auxil.FlotSymbol;
import be.labo.data.DataType;
import be.labo.data.Devicetype;
import be.labo.data.Machine;
import be.labo.data.Network;
import be.labo.data.Place;
import be.labo.data.RawDataFields;
import be.labo.data.RawTableField;
import be.labo.data.Rawtables;
import be.labo.data.Scaling;
import be.labo.data.ScalingSubType;
import be.labo.data.SubType;
import be.labo.data.User;

/**
 * Servlet implementation class MachineUpdate
 */
@WebServlet("/MachineMan")
public class MachineMan extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MachineMan() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectContext context = AuxilServlet.init(request,response, Machine.class);
		AuxilServlet jspDataAccessHelper = (AuxilServlet) request.getAttribute("auxil");
		request.setAttribute("section", "hardware");
		request.setAttribute("readonly", "");				

		User currentUser = (User) request.getSession().getAttribute("currentUser");
		if (currentUser == null) { //401: not authenticated
			response.sendError(401,"user not logged in");
			return;
		}

		if (!(Boolean)request.getAttribute("userMayRead")) {
			response.sendError(403,"user "+currentUser.getName()+" not authorized");
			return;			
		}

		String action = request.getParameter("action");
		String confirm = request.getParameter("confirm");
		if (confirm != null) request.setAttribute("confirm",confirm);

		Machine machine = null;
		String id = request.getParameter("id");
		if (id != null && !id.isEmpty()) {
			machine = Machine.findById(context,id);
			if (machine == null) {
				response.sendError(404,"machine "+id+" unknown");
				return;				
			}
			if (action == null || action.isEmpty()) {
				action = "display";
			}
		}
		if (action == null || action.isEmpty()) {
			action = "list";
		}
		String nextJSP = "/machines.jsp";
		String machineMAC = request.getParameter("machinemac");


		if ("interface".equalsIgnoreCase(action)) {
			if (machine == null) action = "create";
			else {
				if (! (Boolean)request.getAttribute("userMayUpdate")) {
					// response.setStatus(405); // Not allowed
					action = "display";
				} else {
					nextJSP = "/machineinterface.jsp";
					String rawfieldId = (String)request.getParameter("rawfieldid");
					RawTableField rawtablefield = null;
					if (rawfieldId!=null && !rawfieldId.isEmpty()) {
						rawtablefield = RawTableField.findById(context, rawfieldId);
						request.setAttribute("rawtablefield", rawtablefield);
					}

					String datatypeId = (String)request.getParameter("datatypeid");
					DataType datatype = null;
					if (datatypeId!=null && !datatypeId.isEmpty()) {
						datatype = DataType.findById(context, datatypeId);
					}
					if (datatype == null && rawtablefield != null) {
						RawDataFields rdf = rawtablefield.getTableField();
						if (rdf != null) {
							datatype = rdf.getFieldDataType();
							request.setAttribute("selTab",rdf.getInheader());
						}
					}

					SubType subtype = null;
					boolean datatypeLinked = false;
					if (datatype != null) {
						String subaction = request.getParameter("subaction");
						if ("delete".equalsIgnoreCase(subaction)) {
							if (! (Boolean)request.getAttribute("userMayDelete")) {
								subaction = null;
							} else if ( ! "confirmed".equalsIgnoreCase(confirm)) {
								subaction = null;
							}
						}
						List <Machine> machines = datatype.getMachines();
						datatypeLinked = machines.contains(machine);
						request.setAttribute("datatype", datatype);
						if (datatypeLinked) {
							List<SubType> stypMachine = machine.getMachineSubTypes();
							List<SubType> stypDataType = datatype.getSubtype();
							stypDataType.retainAll(stypMachine);
							if (! stypDataType.isEmpty()) {
								subtype = stypDataType.get(0);
							}
						} else { // New subtype?
							if (rawtablefield != null && subaction != null && (!"delete".equalsIgnoreCase(subaction)) ) {
								subaction = "reinit";
								subtype = (SubType)context.newObject(SubType.class);
								subtype.setOwner(datatype);
								subtype.setSubtypeMachine(machine);
								subtype.setSubtypeField(rawtablefield);
								context.commitChanges();
							}
						}
						if (subtype != null) {
							if ("delete".equalsIgnoreCase(subaction)) {
								context.deleteObjects(subtype);
								context.commitChanges();
							} else {
								request.setAttribute("subtype", subtype);
								nextJSP = "/machinesubtype.jsp";
								String unit = datatype.getUnit();
								if (unit == null) {
									request.setAttribute("scalings",jspDataAccessHelper.getAllScalings());
								} else {
									request.setAttribute("scalings",jspDataAccessHelper.findScalingByUnit(unit));
								}
								if ("reinit".equalsIgnoreCase(subaction)) {
									subtype.setActive(datatype.getActive());
									for (int i=0; i<10; i++) {
										subtype.writeProperty(SubType.MIN_PROPERTY+(i>0?""+i:""),datatype.readProperty(DataType.MIN_PROPERTY+(i>0?""+i:"")));
									}
									subtype.setMax(datatype.getMax());
									context.commitChanges();
								}
								if ("save".equalsIgnoreCase(subaction)) {
									String active = request.getParameter(SubType.ACTIVE_PROPERTY);
									String max = request.getParameter(SubType.MAX_PROPERTY);
									String min[] = new String[10];
									min[0] = request.getParameter(SubType.MIN_PROPERTY);
									min[1] = request.getParameter(SubType.MIN1_PROPERTY);
									min[2] = request.getParameter(SubType.MIN2_PROPERTY);
									min[3] = request.getParameter(SubType.MIN3_PROPERTY);
									min[4] = request.getParameter(SubType.MIN4_PROPERTY);
									min[5] = request.getParameter(SubType.MIN5_PROPERTY);
									min[6] = request.getParameter(SubType.MIN6_PROPERTY);
									min[7] = request.getParameter(SubType.MIN7_PROPERTY);
									min[8] = request.getParameter(SubType.MIN8_PROPERTY);
									min[9] = request.getParameter(SubType.MIN9_PROPERTY);
									String scalingid = request.getParameter("scalingid");

									subtype.setActive(AuxilServlet.isChecked(active));
									for (int i=0; i<10; i++) {
										if (min[i] != null && !min[i].isEmpty()) {
											try {
												subtype.writeProperty(SubType.MIN_PROPERTY+(i>0?""+i:""),Double.parseDouble(min[i]));
											} catch (Exception e ) {
												request.setAttribute("errors", request.getAttribute("errors")
														+"Min #"+i+"="+min[i]+", "+jspDataAccessHelper.message("error.numbersyntax")+", "+e.toString()+"<br/>");
											}
										}

									}
									if (max != null && !max.isEmpty()) {
										try {
											subtype.setMax(Double.parseDouble(max));
										} catch (Exception e ) {
											request.setAttribute("errors", request.getAttribute("errors")
													+"Maxr="+max+", "+jspDataAccessHelper.message("error.numbersyntax")+", "+e.toString()+"<br/>");
										}
									}
									context.commitChanges();
									if (scalingid!=null && !scalingid.isEmpty()) {
										Scaling scaling = Scaling.findById(context, scalingid);
										if (scaling != null) {
											List<ScalingSubType> sst = subtype.getSubtypeScaling();
											List<ScalingSubType> ssc = scaling.getScalingRaw();
											ScalingSubType scalingSubType = null;
											if (sst != null && !sst.isEmpty() && ssc != null && !ssc.isEmpty()) {
												ssc.retainAll(sst);
												if (!ssc.isEmpty()) {
													scalingSubType = ssc.get(0);
												}
											}
											if (scalingSubType == null) {
												scalingSubType = (ScalingSubType)context.newObject(ScalingSubType.class);
												Date since = new Date();
												scalingSubType.setSince(since);
												scalingSubType.setRawScaling(scaling);
												scalingSubType.setRawSubtype(subtype);
												context.commitChanges();
											}
										}
									}
								}
							}
						}
					}

					List<RawTableField> rdf = machine.getRawtablefields();
					Ordering ordering = new Ordering("tableField.inheader", SortOrder.ASCENDING);
					ordering.orderList(rdf);
					request.setAttribute("rawtablefields", rdf);
				}
			}
		}

		if ("update".equalsIgnoreCase(action)) {
			if (machine == null) action = "create";
			else {
				if (! (Boolean)request.getAttribute("userMayUpdate")) {
					// response.setStatus(405); // Not allowed
					action = "display";
				} else {
					String subaction = request.getParameter("subaction");
					if ("save".equalsIgnoreCase(subaction)) {
						String active = request.getParameter(Machine.ACTIVE_PROPERTY);
						String nodeid = request.getParameter(Machine.NODEID_PROPERTY);
						String descr = request.getParameter(Machine.DESCRIPTION_PROPERTY);
						String gsm = request.getParameter(Machine.GSM_PROPERTY);
						String deviceType = request.getParameter("devicetype");
						String timeSlot = request.getParameter(Machine.TIMESLOT_PROPERTY);
						String rawTable = request.getParameter("rawtableid");

						String networkId = (String)request.getParameter("networkid");
						if (networkId!=null && !networkId.isEmpty()) {
							Network network = Network.findById(context, networkId);
							if (network != null) {
								machine.setMachineNetwork(network);
							}
						}
						String placeId = (String)request.getParameter("placeid");
						if (placeId!=null && !placeId.isEmpty()) {
							Place place = Place.findById(context, placeId);
							if (place != null) {
								machine.setMachinePlace(place);
							}
						}
						if (nodeid != null && !nodeid.isEmpty()) machine.setNodeid(nodeid);
						machine.setActive(AuxilServlet.isChecked(active));

						if (descr != null) machine.setDescription(descr);
						if (gsm != null) machine.setGsm(gsm);
						if (deviceType != null && !deviceType.isEmpty()) {
							Devicetype dt = Devicetype.findByCode(context,deviceType,false);
							if (dt != null) {
								machine.setMachineDeviceType(dt);
							}
						}
						if (rawTable != null && !rawTable.isEmpty()) {
							Rawtables rt = Rawtables.findByCode(context,rawTable,false);
							if (rt != null) {
								machine.setMachineRawTable(rt);
							}
						}
						try {
							if (machineMAC != null && !machineMAC.isEmpty()) machine.setMachinemac(Integer.parseInt(machineMAC,16));
						} catch (Exception e ) {
							request.setAttribute("errors", request.getAttribute("errors")
									+"MAC="+machineMAC+", "+jspDataAccessHelper.message("error.number16syntax")+", "+e.toString()+"<br/>");
						}
						try {
							if (timeSlot != null && !timeSlot.isEmpty()) machine.setTimeslot(Short.parseShort(timeSlot));
						} catch (Exception e ) {
							request.setAttribute("errors", request.getAttribute("errors")
									+"TimeSlotr="+timeSlot+", "+jspDataAccessHelper.message("error.numbersyntax")+", "+e.toString()+"<br/>");
						}
						for (String aLang : (String[]) request.getServletContext().getAttribute("supportedLocales") ) {
							String aName = request.getParameter("name_"+aLang);
							machine.setName(aLang, aName);
						}
						context.commitChanges();
						request.setAttribute("infos",jspDataAccessHelper.message("success.update"));
					}
					nextJSP = "/machineupdate.jsp";
				}
			}
		}
		if ("create".equalsIgnoreCase(action)) {
			if (! (Boolean)request.getAttribute("userMayCreate")) {
				// response.setStatus(405); // Not allowed
				action = "list";
			} else if (machineMAC != null && !machineMAC.isEmpty()) {
				machine = Machine.findByMAC(context,machineMAC,true);
				if (machine == null) {
					action = "list";
				} else {
					id = Integer.toString(machine.getPK());
					String networkId = (String)request.getParameter("networkid");
					if (networkId != null && !networkId.isEmpty() && machine.getMachineNetwork() == null) {
						Network network = Network.findById(context, networkId);
						if (network != null) {
							machine.setMachineNetwork(network);
							context.commitChanges();
						}
					}
					nextJSP = "/machineupdate.jsp";
				}
			} else {
				action = "list";
			}
		}
		if ("delete".equalsIgnoreCase(action)) {
			if (machine == null) {
				action = "list";
			} else if (! (Boolean)request.getAttribute("userMayDelete")) {
				// response.setStatus(405); // Not allowed
				action = "display";
			} else {
				if ("confirmed".equalsIgnoreCase(confirm)) {
					context.deleteObjects(machine);
					context.commitChanges();
					action = "list";
					machine = null;
					id = null;
				} else {
					action = "display";
				}
			}
		}
		if ("list".equalsIgnoreCase(action)) {
			List<Machine> machines = null;
			String networkId = (String)request.getParameter("networkid");
			if (networkId!=null && !networkId.isEmpty()) {
				Network network = Network.findById(context, networkId);
				machines = network.getNetworkMachine();
				request.setAttribute("network", network);
			} else {
				String placeId = (String)request.getParameter("placeid");
				if (placeId!=null && !placeId.isEmpty()) {
					Place place = Place.findById(context, placeId);
					machines = place.getMachines();
					List<Machine> others = place.getPlacemachine();
					if (machines == null) machines = place.getPlacemachine();
					else if (others != null){
						machines.addAll(others);
					}
					request.setAttribute("place", place);
				} else {
					String datatypeId = (String)request.getParameter("datatypeid");
					if (datatypeId!=null && !datatypeId.isEmpty()) {
						DataType datatype = DataType.findById(context, datatypeId);
						machines = datatype.getMachines();
						request.setAttribute("datatype", datatype);
					} else {

						String scalingId = (String)request.getParameter("scalingid");
						if (scalingId!=null && !scalingId.isEmpty()) {
							Scaling scaling = Scaling.findById(context, scalingId);
							machines = scaling.getMachines();
							request.setAttribute("scaling", scaling);
						} else {
							SelectQuery query = new SelectQuery(Machine.class); // all machines!
							machines = context.performQuery(query);
						}
					}
				}
			}
			/*
			if (machines == null || machines.isEmpty()) {
				response.sendError(400,"no machines"); // Bad request
				return;
			} else if (machines.size() == 1) {
				action = "display";
				machine = machines.get(0);
				id = machine.getPK().toString();	
			} else {
				request.setAttribute("machines", machines);
			}*/			
			request.setAttribute("machines", machines);
		}
		if ("display".equalsIgnoreCase(action)) {
			if (machine == null) action = "error";
			else {
				request.setAttribute("readonly", " readonly=\"readonly\"");				
				nextJSP = "/machineupdate.jsp"; // was machine.jsp
			}
		}

		if ("error".equalsIgnoreCase(action)) {
			response.sendError(401,"invalid request"); // Bad request
			return;
		}
		if (id != null) request.setAttribute("id", id);
		if (machine != null) {
			request.setAttribute("machine", machine);
			request.setAttribute("symbols",FlotSymbol.values());
		}
		request.setAttribute("action",action);
		Crumb.addNewCrumb(request,Machine.class,machine);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
	}
}
