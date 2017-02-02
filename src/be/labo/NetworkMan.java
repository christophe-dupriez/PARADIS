package be.labo;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.ObjectId;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.ObjectIdQuery;
import org.apache.cayenne.query.SelectQuery;

import be.labo.auxil.AuxilServlet;
import be.labo.auxil.Crumb;
import be.labo.data.DataType;
import be.labo.data.Event;
import be.labo.data.Graphconfig;
import be.labo.data.Machine;
import be.labo.data.NameNetwork;
import be.labo.data.Names;
import be.labo.data.Network;
import be.labo.data.Scaling;
import be.labo.data.SubType;
import be.labo.data.User;

/**
 * Servlet implementation class NetworkUpdate
 */
@WebServlet("/NetworkMan")
public class NetworkMan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NetworkMan() {
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
		request.setAttribute("section", "hardware");
		request.setAttribute("readonly", "");				

		ObjectContext context = AuxilServlet.init(request,response, Network.class);

		User currentUser = (User) request.getSession().getAttribute("currentUser");
		if (currentUser == null) { //401: not authenticated
			response.setStatus(401);
			return;
		}
		
		if (!(Boolean)request.getAttribute("userMayRead")) {
			response.setStatus(403); //403: not authorized
			return;			
		}

		String action = request.getParameter("action");
		String subaction = request.getParameter("subaction");
		Network network = null;
		String id = request.getParameter("id");
		if (id != null && !id.isEmpty()) {
			network = Network.findById(context,id);
			if (network == null) {
				response.setStatus(404); // Not found
				return;				
			}
			if (action == null || action.isEmpty()) {
				action = "display";
			}
		}
		if (action == null || action.isEmpty()) {
			action = "list";
		}
		String nextJSP = "/networks.jsp";
/*!!!!!!!!!!!!!!!
public static final String BASETEMPO_PROPERTY = "basetempo";
public static final String BEATMEASURE_PROPERTY = "beatmeasure";
public static final String BEATGSM_PROPERTY = "beatgsm";
public static final String SLEEPTIME_PROPERTY = "sleeptime";
public static final String WARMUP_PROPERTY = "warmup";
public static final String TIMESLICE_PROPERTY = "timeslice";
public static final String NETWORK_MACHINE_PROPERTY = "networkMachine";
public static final String NETWORK_NAME_PROPERTY = "networkName";
public static final String NETWORKAUDIT_PROPERTY = "networkaudit";
*/
		String name = request.getParameter("name");
		String baseTempo = request.getParameter("basetempo");
		String beatMeasure = request.getParameter("beatmeasure");
		String beatGSM = request.getParameter("beatgsm");
		String sleepTime = request.getParameter("sleeptime");
		String warmUp = request.getParameter("warmup");
		String timeSlice = request.getParameter("timeslice");

		if ("update".equalsIgnoreCase(action)) {
			if (network == null) action = "create";
			else {
				if (! (Boolean)request.getAttribute("userMayUpdate")) {
					// response.setStatus(405); // Not allowed
					action = "display";
				} else {
					if ("save".equalsIgnoreCase(subaction)) {
						if (baseTempo != null && !baseTempo.isEmpty()) network.setBasetempo(Byte.parseByte(baseTempo));
						if (beatMeasure != null && !beatMeasure.isEmpty()) network.setBeatmeasure(Short.parseShort(beatMeasure));
						if (beatGSM != null && !beatGSM.isEmpty()) network.setBeatgsm(Short.parseShort(beatGSM));
						if (sleepTime != null && !sleepTime.isEmpty()) network.setSleeptime(Integer.parseInt(sleepTime));
						if (warmUp != null && !warmUp.isEmpty()) network.setWarmup(Short.parseShort(warmUp));
						if (timeSlice != null && !timeSlice.isEmpty()) network.setTimeslice(Short.parseShort(timeSlice));
						context.commitChanges();
						for (String aLang : (String[]) request.getServletContext().getAttribute("supportedLocales") ) {
							String aName = request.getParameter("name_"+aLang);
							network.setName(aLang, aName);
						}
					}
					nextJSP = "/networkupdate.jsp";
				}
			}
		}
		if ("create".equalsIgnoreCase(action)) {
			if (! (Boolean)request.getAttribute("userMayCreate")) {
				// response.setStatus(405); // Not allowed
				action = "list";
			} else if (name != null && !name.isEmpty()) {
				SelectQuery queryNodeId = new SelectQuery(NameNetwork.class,ExpressionFactory.matchExp(Names.NAME_PROPERTY, name));
				List<NameNetwork> noNetwork = context.performQuery(queryNodeId);
				if (noNetwork == null || noNetwork.isEmpty()) {
					network = (Network)context.newObject(Network.class);
					context.commitChanges();
					NameNetwork nameNetwork = (NameNetwork)context.newObject(NameNetwork.class);
					nameNetwork.setName(name);
					nameNetwork.setLanguage(AuxilServlet.currentLanguage(request));
					nameNetwork.setNameNetwork(network);
					context.commitChanges();
				} else {
					network = noNetwork.get(0).getNameNetwork(); 
				}
				id = Integer.toString(network.getPK());
				nextJSP = "/networkupdate.jsp";
			}
		}
		if ("delete".equalsIgnoreCase(action)) {
			if (network == null) {
				action = "list";
			} else if (! (Boolean)request.getAttribute("userMayDelete")) {
				// response.setStatus(405); // Not allowed
				action = "display";
			} else {
				context.deleteObjects(network);
				context.commitChanges();
				action = "list";
				network = null;
				id = null;
			}
		}
		if ("list".equalsIgnoreCase(action)) {
			List<Network> networks = null;
			String machineId = (String)request.getAttribute("machineid");
			if (machineId!=null && !machineId.isEmpty()) {
				Machine machine = Machine.findById(context, machineId);
				network = machine.getMachineNetwork();
				if (network != null) {
					action = "display";
					id = network.getPK().toString();	
				} else {
					response.setStatus(400); // Bad request
					return;
				}
			} else {
				SelectQuery query = new SelectQuery(Network.class); // all networks!
				networks = context.performQuery(query);
				/*
				if (networks == null || networks.isEmpty()) {
					action = "error";
				} else if (networks.size() == 1) {
					action = "display";
					network = networks.get(0);
					id = network.getPK().toString();	
				} else {
					request.setAttribute("networks", networks);
				}*/
				request.setAttribute("networks", networks);
			}
		}
		if ("display".equalsIgnoreCase(action)) {
			if (network == null) {
				action = "error";
			} else {
				request.setAttribute("readonly", " readonly=\"readonly\"");				
				nextJSP = "/networkupdate.jsp"; // was network.jsp
			}
		}

		if ("error".equalsIgnoreCase(action)) {
			response.setStatus(401);
			return;
		}
		if (id != null) request.setAttribute("id", id);
		if (network != null) request.setAttribute("network", network);
		request.setAttribute("action",action);
		Crumb.addNewCrumb(request,Network.class,network);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
	}
}
