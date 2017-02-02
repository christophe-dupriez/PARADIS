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
import be.labo.data.DataType;
import be.labo.data.Machine;
import be.labo.data.Measure;

/**
 * Servlet implementation class MeasureUpdate
 */
@WebServlet("/MeasureMan")
public class MeasureMan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MeasureMan() {
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
		ObjectContext context = AuxilServlet.init(request,response, Measure.class);

		String action = request.getParameter("action");
		if (action == null || action.isEmpty()) action = "display";

		String measureParam = request.getParameter("measure");
		Integer idNum = new Integer(0);
		Measure measure = null;
		if (measureParam != null && !measureParam.isEmpty()) {
			try {
				idNum = Integer.valueOf(measureParam);
				ObjectId idMeasure = new ObjectId("Measure", Measure.ID_PK_COLUMN, idNum);

				// this constructor implicitly uses "CACHE_REFRESH" policy, so a fresh object will be returned
				ObjectIdQuery query = new ObjectIdQuery(idMeasure);

				measure = (Measure) DataObjectUtils.objectForQuery(context, query);
			} catch (NumberFormatException ne) {
			}
		}
		request.setAttribute("section", "section.people");
		request.setAttribute("title", "section.people.alerts");

		String machineParam = request.getParameter("machine");
		Integer idMac = new Integer(0);
		Machine machine = null;

		if (machineParam != null && !machineParam.isEmpty()) {
			try {
				idMac = Integer.valueOf(machineParam);
				ObjectId idMachine = new ObjectId("Machine", Machine.ID_PK_COLUMN, idMac);

				// this constructor implicitly uses "CACHE_REFRESH" policy, so a fresh object will be returned
				ObjectIdQuery query = new ObjectIdQuery(idMachine);

				machine = (Machine) DataObjectUtils.objectForQuery(context, query);
				request.setAttribute("machine", machine);
			} catch (NumberFormatException ne) {
			}
		}

		String typeParam = request.getParameter("type");
		Integer idTyp = new Integer(0);
		DataType type = null;

		if (typeParam != null && !typeParam.isEmpty()) {
			try {
				idTyp = Integer.valueOf(typeParam);
				ObjectId idType = new ObjectId("Type", DataType.ID_PK_COLUMN, idTyp);

				// this constructor implicitly uses "CACHE_REFRESH" policy, so a fresh object will be returned
				ObjectIdQuery query = new ObjectIdQuery(idType);

				type = (DataType) DataObjectUtils.objectForQuery(context, query);
				request.setAttribute("type", type);
			} catch (NumberFormatException ne) {
			}
		}

		String value = request.getParameter("value");
		String from = request.getParameter("from");

		String nextJSP = "/index.jsp";

		if ("display".equalsIgnoreCase(action)) {
			if (measure == null) action = "new";
			else {
				nextJSP = "/measureupdate.jsp";
				if (from != null && !from.isEmpty()) nextJSP = "/"+from+".jsp";
			}
		}
		if ("new".equalsIgnoreCase(action)) {
			nextJSP = "/measurenew.jsp";
		}
		if ("update".equalsIgnoreCase(action)) {
			if (measure == null) action = "create";
			if (value != null && !value.isEmpty()) measure.setValue(new Double(value));
			if (type != null) measure.setMeasureType(type);
			if (machine != null) measure.setMeasureMachine(machine);
			measure.getObjectContext().commitChanges();
			nextJSP = "/measureupdate.jsp";
			if (from != null && !from.isEmpty()) nextJSP = "/"+from+".jsp";
		}
		if ("create".equalsIgnoreCase(action)) {
			request.setAttribute("title", "Cr�ation d'une mesure");
			measure = (Measure)context.newObject(Measure.class);
			if (type != null) {
				measure.setMeasureType(type);
			}
			if (value != null && !value.isEmpty()) measure.setValue(new Double(value));
			if (machine != null) measure.setMeasureMachine(machine);
			measure.getObjectContext().commitChanges();
			nextJSP = "/measureupdate.jsp";
			if (from != null && !from.isEmpty()) nextJSP = "/"+from+".jsp";
		}
		if ("remove".equalsIgnoreCase(action)&&measure != null) {
			context.deleteObject(measure);
			context.commitChanges();
			SelectQuery query = new SelectQuery(Measure.class);
			List<Measure> measures = context.performQuery(query);
			request.setAttribute("measures", measures);
			nextJSP = "/index.jsp";
			if (from != null && !from.isEmpty()) nextJSP = "/"+from+".jsp";
		}
		if ("list".equalsIgnoreCase(action)) {
			int size = 0;
			nextJSP = "/measures.jsp";
			if (machine != null && type != null) {
				SelectQuery queryMeasure
				    = new SelectQuery(Measure.class,
				    		          ExpressionFactory.matchExp(Measure.MEASURE_MACHINE_PROPERTY, machine.getPK())
				    		          .andExp(ExpressionFactory.matchExp(Measure.MEASURE_TYPE_PROPERTY, type.getPK())));
				List<Measure> searchMeasures = context.performQuery(queryMeasure);
				request.setAttribute("measures", searchMeasures);
				size = searchMeasures.size();
			}
			request.setAttribute("title", size+" mesure"+(size==1?"":"s"));
		}
		if ("error".equalsIgnoreCase(action)) {
			request.setAttribute("error", "Erreur!");
		}
		if (measure != null) request.setAttribute("measure", measure);

		if ("/machines.jsp".equals(nextJSP)) {
			if (machine != null) {
				request.setAttribute("id",machine.getPK());
			}
		}
		if ("/types.jsp".equals(nextJSP)) {
			if (type != null) {
				request.setAttribute("id",type.getPK());
			}
		}

		SelectQuery query = new SelectQuery(Machine.class);
		List<Machine> machines = context.performQuery(query);
		request.setAttribute("machines", machines);

		query = new SelectQuery(DataType.class);
		List<DataType> types = context.performQuery(query);
		request.setAttribute("types", types);

		request.setAttribute("action",action);
		request.setAttribute("from", from);
		request.setAttribute("jsp", nextJSP);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
	}
}
