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
import org.apache.cayenne.query.SelectQuery;

import be.labo.auxil.AuxilServlet;
import be.labo.auxil.Crumb;
import be.labo.auxil.FlotSymbol;
import be.labo.auxil.WebColor;
import be.labo.data.DataType;
import be.labo.data.Event;
import be.labo.data.Imagemap;
import be.labo.data.Machine;
import be.labo.data.NamePlace;
import be.labo.data.Place;
import be.labo.data.User;

@WebServlet("/PlaceMan")
public class PlaceMan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlaceMan() {
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
		ObjectContext context = AuxilServlet.init(request,response, Place.class);
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
		String subaction = request.getParameter("subaction");
		String confirm = request.getParameter("confirm");
		if (confirm != null) request.setAttribute("confirm",confirm);

		Place place = null;
		String id = request.getParameter("id");
		if (id != null && !id.isEmpty()) {
			place = Place.findById(context,id);
			if (place == null) {
				response.sendError(404,"place "+id+" unknown");
				return;				
			}
			if (action == null || action.isEmpty()) {
				action = "display";
			}
		}
		if (action == null || action.isEmpty()) {
			action = "list";
		}
		String nextJSP = "/places.jsp";

		if ("update".equalsIgnoreCase(action)) {
			if (place == null) action = "create";
			else {
				if (! (Boolean)request.getAttribute("userMayUpdate")) {
					// response.setStatus(405); // Not allowed
					action = "display";
				} else {
					if ("save".equalsIgnoreCase(subaction)) {
						String colorhex = request.getParameter(Place.COLOR_PROPERTY);
						String symbol = request.getParameter(Place.SYMBOL_PROPERTY);
						String latString = request.getParameter(Place.LATITUDE_PROPERTY);
						String longString = request.getParameter(Place.LONGITUDE_PROPERTY);
						String elevString = request.getParameter(Place.ELEVATION_PROPERTY);
						if (colorhex != null && colorhex.length() > 1) {
							if (colorhex.charAt(0)=='#') {
								colorhex = colorhex.substring(1);
							}
							int tinyCol = 0;
							try {
								tinyCol = new WebColor(Integer.parseInt(colorhex, 16)).getTinyColor();
								place.setColor((byte)tinyCol);
							} catch (Exception e ) {
								request.setAttribute("errors", request.getAttribute("errors")
										+"Color="+colorhex+", "+jspDataAccessHelper.message("error.number16syntax")+", "+e.toString()+"<br/>");
							}
						}
						if (symbol != null && !symbol.isEmpty()) {
							try {
								place.setSymbol(Byte.parseByte(symbol));
							} catch (Exception e ) {
								request.setAttribute("errors", request.getAttribute("errors")
										+"Symbol code="+symbol+", "+jspDataAccessHelper.message("error.numbersyntax")+", "+e.toString()+"<br/>");
							}
						}
						if (latString != null && !latString.isEmpty()) {
							try {
								place.setLatitude(Double.parseDouble(latString));
							} catch (Exception e ) {
								request.setAttribute("errors", request.getAttribute("errors")
										+"Latitude="+latString+", "+jspDataAccessHelper.message("error.numbersyntax")+", "+e.toString()+"<br/>");
							}
						}
						if (longString != null && !longString.isEmpty()) {
							try {
								place.setLongitude(Double.parseDouble(longString));
							} catch (Exception e ) {
								request.setAttribute("errors", request.getAttribute("errors")
										+"Longitude="+longString+", "+jspDataAccessHelper.message("error.numbersyntax")+", "+e.toString()+"<br/>");
							}
						}
						if (elevString != null && !elevString.isEmpty()) {
							try {
								place.setElevation(Double.parseDouble(elevString));
							} catch (Exception e ) {
								request.setAttribute("errors", request.getAttribute("errors")
										+"Elevation="+elevString+", "+jspDataAccessHelper.message("error.numbersyntax")+", "+e.toString()+"<br/>");
							}
						}
						for (String aLang : (String[]) request.getServletContext().getAttribute("supportedLocales") ) {
							String aName = request.getParameter("name_"+aLang);
							place.setName(aLang, aName);
						}
						context.commitChanges();
					}
					nextJSP = "/placeupdate.jsp";
				}
			}
		}
		if ("create".equalsIgnoreCase(action)) {
			if (! (Boolean)request.getAttribute("userMayCreate")) {
				// response.setStatus(405); // Not allowed
				action = "list";
			} else {
				place = (Place)context.newObject(Place.class);
				String aName = request.getParameter(NamePlace.NAME_PROPERTY);
				if (aName != null && !aName.isEmpty()) {
					String userLang = AuxilServlet.currentLanguage(request);
					place.setName(userLang, aName);
				}
				context.commitChanges();
				nextJSP = "/placeupdate.jsp";
			}
		}
		if ("delete".equalsIgnoreCase(action)) {
			if (place == null) {
				action = "list";
			} else if (! (Boolean)request.getAttribute("userMayDelete")) {
				// response.setStatus(405); // Not allowed
				action = "display";
			} else {
				if ("confirmed".equalsIgnoreCase(confirm)) {
					context.deleteObjects(place);
					context.commitChanges();
					action = "list";
					place = null;
					id = null;
				} else {
					action = "display";
				}
			}
		}
		if ("list".equalsIgnoreCase(action)) {
			List<Place> places = null;
			String imagemapId = (String)request.getParameter("imagemapid");
			if (imagemapId!=null && !imagemapId.isEmpty()) {
				Imagemap imagemap = Imagemap.findById(context, imagemapId);
				places = imagemap.getPlaces();
				request.setAttribute("imagemap", imagemap);
			} else {
				String datatypeId = (String)request.getParameter("datatypeid");
				if (datatypeId!=null && !datatypeId.isEmpty()) {
					DataType datatype = DataType.findById(context, datatypeId);
					places = datatype.getPlaces();
					request.setAttribute("datatype", datatype);
				} else {
					String eventId = (String)request.getParameter("eventid");
					if (eventId!=null && !eventId.isEmpty()) {
						Event event = Event.findById(context, eventId);
						places = event.getPlaces();
						request.setAttribute("event", event);
					} else {
						String machineId = (String)request.getParameter("machineid");
						if (machineId!=null && !machineId.isEmpty()) {
							Machine machine = Machine.findById(context, machineId);
							place = machine.getMachinePlace();
							places = machine.getPlaces();
							if (place != null) {
								if (!places.contains(place)) places.add(place);
							}
							request.setAttribute("machine", machine);
						} else {
							SelectQuery query = new SelectQuery(Place.class); // all places!
							places = (List<Place>) context.performQuery(query);
						}
					}
				}
			}
			/*
			if (places == null || places.size() == 0) {
				response.sendError(400,"no places"); // Bad request
				return;
			} else if (places.size() == 1) {
				action = "display";
				place = places.get(0);
				id = place.getPK().toString();	
			} else {
				request.setAttribute("places", places);
			}*/
			request.setAttribute("places", places);			
		}
		if ("display".equalsIgnoreCase(action)) {
			if (place == null) action = "error";
			else {
				request.setAttribute("readonly", " readonly=\"readonly\"");				
				nextJSP = "/placeupdate.jsp"; // was place.jsp
			}
		}

		if ("error".equalsIgnoreCase(action)) {
			response.sendError(401,"invalid request"); // Bad request
			return;
		}
		if (id != null) request.setAttribute("id", id);
		if (place != null) {
			request.setAttribute("place", place);
			request.setAttribute("symbols",FlotSymbol.values());
		}
		request.setAttribute("action",action);
		Crumb.addNewCrumb(request,Place.class,place);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
	}
}
