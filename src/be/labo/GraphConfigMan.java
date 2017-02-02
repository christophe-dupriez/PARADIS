package be.labo;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cayenne.ObjectContext;

import be.labo.auxil.AuxilServlet;
import be.labo.auxil.FlotSymbol;
import be.labo.data.DataType;
import be.labo.data.Graphconfig;
import be.labo.data.GroupOfUsers;
import be.labo.data.Place;
import be.labo.data.User;

/**
 * Servlet implementation class MachineUpdate
 */
@WebServlet("/GraphConfigMan")
public class GraphConfigMan extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GraphConfigMan() {
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
		ObjectContext context = AuxilServlet.init(request,response, Graphconfig.class);
		AuxilServlet jspDataAccessHelper = (AuxilServlet) request.getAttribute("auxil");
		request.setAttribute("section", "data");
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

		Graphconfig graph = null;
		String id = request.getParameter("id");
		if (id != null && !id.isEmpty()) {
			graph = Graphconfig.findById(context,id);
			if (graph == null) {
				response.sendError(404,"graph "+id+" unknown");
				return;				
			}
			if (action == null || action.isEmpty()) {
				action = "display";
			}
		}
		if (action == null || action.isEmpty()) {
			action = "list";
		}
		String nextJSP = "/graphs.jsp";

		if ("update".equalsIgnoreCase(action)) {
			if (graph == null) action = "create";
			else {
				if (! (Boolean)request.getAttribute("userMayUpdate")) {
					// response.setStatus(405); // Not allowed
					action = "display";
				} else {
					String subaction = request.getParameter("subaction");
					if ("save".equalsIgnoreCase(subaction)) {
						/*
						String active = request.getParameter(GraphConfig.ACTIVE_PROPERTY);
						String colorhex = request.getParameter(GraphConfig.COLOR_PROPERTY);
						String symbol = request.getParameter(GraphConfig.SYMBOL_PROPERTY);
						String nodeid = request.getParameter(GraphConfig.NODEID_PROPERTY);
						String descr = request.getParameter(GraphConfig.DESCRIPTION_PROPERTY);
						String gsm = request.getParameter(GraphConfig.GSM_PROPERTY);
						String deviceType = request.getParameter("devicetype");
						String timeSlot = request.getParameter(GraphConfig.TIMESLOT_PROPERTY);
						String rawTable = request.getParameter("rawtableid");
						String latString = request.getParameter(GraphConfig.LATITUDE_PROPERTY);
						String longString = request.getParameter(GraphConfig.LONGITUDE_PROPERTY);
						String elevString = request.getParameter(GraphConfig.ELEVATION_PROPERTY);

						String networkId = (String)request.getParameter("networkid");
						if (networkId!=null && !networkId.isEmpty()) {
							Network network = Network.findById(context, networkId);
							if (network != null) {
								graph.setMachineNetwork(network);
							}
						}
						if (nodeid != null && !nodeid.isEmpty()) graph.setNodeid(nodeid);
						if (colorhex != null && colorhex.length() > 1) {
							if (colorhex.charAt(0)=='#') {
								colorhex = colorhex.substring(1);
							}
							int tinyCol = 0;
							try {
								tinyCol = new WebColor(Integer.parseInt(colorhex, 16)).getTinyColor();
								graph.setColor((byte)tinyCol);
							} catch (Exception e ) {
								request.setAttribute("errors", request.getAttribute("errors")
										+"Color="+colorhex+", "+jspDataAccessHelper.message("error.number16syntax")+", "+e.toString()+"<br/>");
							}
						}
						if (symbol != null && !symbol.isEmpty()) {
							try {
								graph.setSymbol(Byte.parseByte(symbol));
							} catch (Exception e ) {
								request.setAttribute("errors", request.getAttribute("errors")
										+"Symbol code="+symbol+", "+jspDataAccessHelper.message("error.numbersyntax")+", "+e.toString()+"<br/>");
							}
						}
						if (latString != null && !latString.isEmpty()) {
							try {
								graph.setLatitude(Double.parseDouble(latString));
							} catch (Exception e ) {
								request.setAttribute("errors", request.getAttribute("errors")
										+"Latitude="+latString+", "+jspDataAccessHelper.message("error.numbersyntax")+", "+e.toString()+"<br/>");
							}
						}
						if (longString != null && !longString.isEmpty()) {
							try {
								graph.setLongitude(Double.parseDouble(longString));
							} catch (Exception e ) {
								request.setAttribute("errors", request.getAttribute("errors")
										+"Longitude="+longString+", "+jspDataAccessHelper.message("error.numbersyntax")+", "+e.toString()+"<br/>");
							}
						}
						if (elevString != null && !elevString.isEmpty()) {
							try {
								graph.setElevation(Double.parseDouble(elevString));
							} catch (Exception e ) {
								request.setAttribute("errors", request.getAttribute("errors")
										+"Elevation="+elevString+", "+jspDataAccessHelper.message("error.numbersyntax")+", "+e.toString()+"<br/>");
							}
						}
						Boolean isActive = AuxilServlet.isChecked(active);
						if (isActive != null) {
							graph.setActive( isActive );
						}
						if (descr != null) graph.setDescription(descr);
						if (gsm != null) graph.setGsm(gsm);
						if (deviceType != null && !deviceType.isEmpty()) {
							Devicetype dt = Devicetype.findByCode(context,deviceType,false);
							if (dt != null) {
								graph.setMachineDeviceType(dt);
							}
						}
						if (rawTable != null && !rawTable.isEmpty()) {
							Rawtables rt = Rawtables.findByCode(context,rawTable,false);
							if (rt != null) {
								graph.setMachineRawTable(rt);
							}
						}
						try {
							if (machineMAC != null && !machineMAC.isEmpty()) graph.setMachinemac(Integer.parseInt(machineMAC,16));
						} catch (Exception e ) {
							request.setAttribute("errors", request.getAttribute("errors")
									+"MAC="+machineMAC+", "+jspDataAccessHelper.message("error.number16syntax")+", "+e.toString()+"<br/>");
						}
						try {
							if (timeSlot != null && !timeSlot.isEmpty()) graph.setTimeslot(Short.parseShort(timeSlot));
						} catch (Exception e ) {
							request.setAttribute("errors", request.getAttribute("errors")
									+"TimeSlotr="+timeSlot+", "+jspDataAccessHelper.message("error.numbersyntax")+", "+e.toString()+"<br/>");
						}
						for (String aLang : (String[]) request.getServletContext().getAttribute("supportedLocales") ) {
							String aName = request.getParameter("name_"+aLang);
							graph.setName(aLang, aName);
						}
						*/
						context.commitChanges();
						request.setAttribute("infos",jspDataAccessHelper.message("success.update"));
					}
					nextJSP = "/graphupdate.jsp";
				}
			}
		}
		if ("create".equalsIgnoreCase(action)) {
			if (! (Boolean)request.getAttribute("userMayCreate")) {
				// response.setStatus(405); // Not allowed
				action = "list";
			} else {
				action = "list";
			}
		}
		if ("delete".equalsIgnoreCase(action)) {
			if (graph == null) {
				action = "list";
			} else if (! (Boolean)request.getAttribute("userMayDelete")) {
				// response.setStatus(405); // Not allowed
				action = "display";
			} else {
				if ("confirmed".equalsIgnoreCase(confirm)) {
					context.deleteObjects(graph);
					context.commitChanges();
					action = "list";
					graph = null;
					id = null;
				} else {
					action = "display";
				}
			}
		}
		if ("list".equalsIgnoreCase(action)) {
			List<Graphconfig> graphs = null;
			String datatypeId = (String)request.getParameter("datatypeid");
			if (datatypeId!=null && !datatypeId.isEmpty()) {
				DataType datatype = DataType.findById(context, datatypeId);
				graphs = datatype.getGraphconfigs();
				request.setAttribute("datatype", datatype);
			} else {
				String placeId = (String)request.getParameter("placeid");
				if (placeId!=null && !placeId.isEmpty()) {
					Place place = Place.findById(context, placeId);
					graphs = place.getGraphconfigs();
					request.setAttribute("place", place);
				} else { // 
					Graphconfig graphConf = currentUser.getUserGraph();
					if (graphConf == null) {
						List<GroupOfUsers> groups = currentUser.getGroups();
						for (GroupOfUsers group : groups) {
							graphConf = group.getGroupgraph();
							if (graphConf != null) {
								if (!graphs.contains(graphConf)) {
									graphs.add(graphConf);
								}
							}
						}
					} else {
						graphs.add(graphConf);
					}
				}
			}
			if (graphs == null || graphs.isEmpty()) {
				response.sendError(400,"no graphs"); // Bad request
				return;
			} else if (graphs.size() == 1) {
				action = "display";
				graph = graphs.get(0);
				id = graph.getPK().toString();	
			} else {
				request.setAttribute("graphs", graphs);
			}
		}
		if ("display".equalsIgnoreCase(action)) {
			if (graph == null) action = "error";
			else {
				request.setAttribute("readonly", " readonly=\"readonly\"");				
				nextJSP = "/graph.jsp"; // AND NOT graphupdate.jsp !
			}
		}

		if ("error".equalsIgnoreCase(action)) {
			response.sendError(401,"invalid request"); // Bad request
			return;
		}
		if (id != null) request.setAttribute("id", id);
		if (graph != null) {
			request.setAttribute("graph", graph);
			request.setAttribute("symbols",FlotSymbol.values());
		}
		request.setAttribute("action",action);
		//Crumb.addNewCrumb(request,GraphConfig.class,graph);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
	}
}
