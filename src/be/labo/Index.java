package be.labo;

import java.io.IOException;
import java.util.TreeSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.cayenne.ObjectContext;

import be.labo.auxil.AuxilServlet;
import be.labo.data.DataType;
import be.labo.data.Imagemap;
import be.labo.data.Machine;
import be.labo.data.Place;
import be.labo.data.SubType;
import be.labo.data.User;

/**
 * Servlet implementation class Index
 */
@WebServlet("/index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Index() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectContext context = AuxilServlet.init(request,response, User.class);
		AuxilServlet jspDataAccessHelper = (AuxilServlet) request.getAttribute("auxil");
		request.setAttribute("section", "application.name");
		request.setAttribute("readonly", "");				


		String nextJSP = "/index.jsp";

		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("currentUser");
		if (currentUser != null) { //401: not authenticated
			if (!(Boolean)request.getAttribute("userMayRead")) {
				response.sendError(403,"user "+currentUser.getName()+" not authorized");
				return;			
			}

			DataType datatype = DataType.findById(context, "1"); //1: temperature, 230 : niveau d'eau
			request.setAttribute("datatype", datatype);
			Imagemap imagemap = Imagemap.findById(context, "100");
			request.setAttribute("imagemap", imagemap);				
			request.setAttribute("readonly", " readonly=\"readonly\"");				
			nextJSP = "/splash.jsp"; // AND NOT graphupdate.jsp !
			/*
			List<Measure> lastAlerts = (List<Measure>) context.performQuery("LastAlerts", false);
			request.setAttribute("lastAlerts", lastAlerts);
			SelectQuery query = new SelectQuery(Machine.class);
			List<Machine> machines = context.performQuery(query);
			request.setAttribute("machines", machines);

			query = new SelectQuery(DataType.class);
			List<DataType> types = context.performQuery(query);
			request.setAttribute("types", types);
			 */

		}

		request.setAttribute("jsp", nextJSP);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
