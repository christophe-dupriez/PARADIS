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
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import be.labo.auxil.AuxilServlet;
import be.labo.auxil.Crumb;
import be.labo.auxil.FlotSymbol;
import be.labo.data.DataType;
import be.labo.data.Graphconfig;
import be.labo.data.Machine;
import be.labo.data.Scaling;
import be.labo.data.User;

/**
 * Servlet implementation class ScalingUpdate
 */
@WebServlet("/ScalingMan")
public class ScalingMan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScalingMan() {
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
		ObjectContext context = AuxilServlet.init(request,response, Scaling.class);
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

		Scaling scaling = null;
		String id = request.getParameter("id");
		if (id != null && !id.isEmpty()) {
			scaling = Scaling.findById(context,id);
			if (scaling == null) {
				response.sendError(404,"scaling "+id+" unknown");
				return;				
			}
			if (action == null || action.isEmpty()) {
				action = "display";
			}
		}
		if (action == null || action.isEmpty()) {
			action = "list";
		}
		String nextJSP = "/scalings.jsp";

		if ("interface".equalsIgnoreCase(action)) {
			if (scaling == null) action = "create";
			else {
				if (! (Boolean)request.getAttribute("userMayUpdate")) {
					// response.setStatus(405); // Not allowed
					action = "display";
				} else {
					List<Machine> machines = scaling.getMachines();
					Ordering ordering = new Ordering("machine.unit", SortOrder.ASCENDING);
					ordering.orderList(machines);
					request.setAttribute("machines", machines);
					if ("save".equalsIgnoreCase(subaction)) {
						//scaling.getRawtablefields().get(0).getFieldSubtype().
					}
					nextJSP = "/scalinginterface.jsp";
				}
			}
		}

		if ("update".equalsIgnoreCase(action)) {
			if (scaling == null) action = "create";
			else {
				if (! (Boolean)request.getAttribute("userMayUpdate")) {
					// response.setStatus(405); // Not allowed
					action = "display";
				} else {
					if ("save".equalsIgnoreCase(subaction)) {
						String descr = request.getParameter(Scaling.DESCRIPTION_PROPERTY);
						String unit = request.getParameter(Scaling.UNIT_PROPERTY);
						String interpolation = request.getParameter( Scaling.INTERPOLATION_PROPERTY);
						if (descr != null) scaling.setDescription(descr);
						if (unit != null && !unit.isEmpty()) { 
							scaling.setUnit(unit);
						}
						if (interpolation != null && !interpolation.isEmpty()) {
							try {
								scaling.setInterpolation(Byte.parseByte(interpolation));
							} catch (Exception e ) { }
						}
/*SUPPORTER JUSQUA 100 SCALING LINES EXPORTEES DANS CATEGORIES ET RAMENEE DANS MIN00 MIN01 MIN02....
    public static final String DESCRIPTION_PROPERTY = "description";
    public static final String INTERPOLATION_PROPERTY = "interpolation";
    public static final String UNIT_PROPERTY = "unit";
    
    Lines:
    public static final String MAX_PROPERTY = "max";
    public static final String MIN_PROPERTY = "min";
    public static final String RAWMAX_PROPERTY = "rawmax";
    public static final String RAWMIN_PROPERTY = "rawmin";
    public static final String OWNER_PROPERTY = "owner";

    public static final String DATATYPES_PROPERTY = "datatypes";
    public static final String LINE_PROPERTY = "line";
    public static final String MACHINES_PROPERTY = "machines";
    public static final String SCALING_AUDIT_PROPERTY = "scalingAudit";
    public static final String SCALING_RAW_PROPERTY = "scalingRaw";
    public static final String SUBTYPES_PROPERTY = "subtypes";
 */
						/*
						Boolean isActive = AuxilServlet.isChecked(active);
						if (isActive != null) {
							scaling.setActive(isActive);
						}
						if (decimals != null && !decimals.isEmpty()) {
							try {
								scaling.setDecimals(Byte.parseByte(decimals));
							} catch (Exception e ) { }
						}
						if (defaulttimespan != null && !defaulttimespan.isEmpty()) {
							Seconds ts = Seconds.parse(defaulttimespan);
							if (ts != null) scaling.setDefaulttimespan(ts.getTicks());
						}
						if (max != null && !max.isEmpty()) {
							try {
								scaling.setMax(Double.parseDouble(max));
							} catch (Exception e ) { }
						}
						for (int i=0; i<10; i++) {
							if (min[i] != null && !min[i].isEmpty()) {
								try {
									scaling.writeProperty(Scaling.MIN_PROPERTY+(i>0?""+i:""),Double.parseDouble(min[i]));
								} catch (Exception e ) { }
							}

							if (color[i] != null && !color[i].isEmpty()) {
								if (color[i] != null && color[i].length() > 1) {
									if (color[i].charAt(0)=='#') {
										color[i] = color[i].substring(1);
									}
									int tinyCol = 0;
									try {
										tinyCol = new WebColor(Integer.parseInt(color[i], 16)).getTinyColor();
									} catch (Exception e ) { }
									scaling.writeProperty("color"+i,(byte)tinyCol);
								}
							}
						}
						 // This also resets Categories:
						if (nbcategories != null && !nbcategories.isEmpty()) {
							try {
								scaling.setNbcategories(Byte.parseByte(nbcategories));
							} catch (Exception e ) { }
						} else {
							Byte nbc = scaling.getNbcategories();
							if (nbc != null) {
								scaling.setNbcategories(nbc);
							}
						}

						if (isaletter != null && !isaletter.isEmpty()) { 
							scaling.setIsaletter(isaletter);
						}
						if (symbol != null && !symbol.isEmpty()) {
							try {
								scaling.setSymbol(Byte.parseByte(symbol));
							} catch (Exception e ) { }
						}
						for (String aLang : (String[]) request.getServletContext().getAttribute("supportedLocales") ) {
							String aName = request.getParameter("name_"+aLang);
							scaling.setName(aLang, aName);
						}
*/
						context.commitChanges();
					}
					nextJSP = "/scalingupdate.jsp";
				}
			}
		}
		if ("create".equalsIgnoreCase(action)) {
			if (! (Boolean)request.getAttribute("userMayCreate")) {
				// response.setStatus(405); // Not allowed
				action = "list";
			} else {
				scaling = (Scaling)context.newObject(Scaling.class);
				//scaling.setActive(true);
				context.commitChanges();
				nextJSP = "/scalingupdate.jsp";
			}
		}
		if ("delete".equalsIgnoreCase(action)) {
			if (scaling == null) {
				action = "list";
			} else if (! (Boolean)request.getAttribute("userMayDelete")) {
				// response.setStatus(405); // Not allowed
				action = "display";
			} else {
				if ("confirmed".equalsIgnoreCase(confirm)) {
					context.deleteObjects(scaling);
					context.commitChanges();
					action = "list";
					scaling = null;
					id = null;
				} else {
					action = "display";
				}
			}
		}
		if ("list".equalsIgnoreCase(action)) {
			List<Scaling> scalings = null;
			String datatypeId = (String)request.getParameter("datatypeid");
			if (datatypeId!=null && !datatypeId.isEmpty()) {
				DataType datatype = DataType.findById(context, datatypeId);
				scalings = datatype.getScalings();
				request.setAttribute("datatype", datatype);
			} else {
				String machineId = (String)request.getParameter("machineid");
				if (machineId!=null && !machineId.isEmpty()) {
					Machine machine = Machine.findById(context, machineId);
					scalings = machine.getScalings();
					request.setAttribute("machine", machine);
				}
			}
			/*
			if (scalings == null || scalings.isEmpty()) {
				response.sendError(400,"no scalings"); // Bad request
				return;
			} else if (scalings.size() == 1) {
				action = "display";
				scaling = scalings.get(0);
				id = scaling.getPK().toString();	
			} else {
				request.setAttribute("scalings", scalings);
			}*/			
			request.setAttribute("scalings", scalings);
		}
		if ("display".equalsIgnoreCase(action)) {
			if (scaling == null) action = "error";
			else {
				request.setAttribute("readonly", " readonly=\"readonly\"");				
				nextJSP = "/scaling.jsp"; // NOT scalingupdate.jsp !
			}
		}

		if ("error".equalsIgnoreCase(action)) {
			response.sendError(401,"invalid request"); // Bad request
			return;
		}
		if (id != null) request.setAttribute("id", id);
		if (scaling != null) {
			request.setAttribute("scaling", scaling);
			request.setAttribute("symbols",FlotSymbol.values());
		}
		request.setAttribute("action",action);
		Crumb.addNewCrumb(request,Scaling.class,scaling);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
	}
}
