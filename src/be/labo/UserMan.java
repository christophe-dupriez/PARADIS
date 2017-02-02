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
import be.labo.data.Network;
import be.labo.data.User;

/**
 * Servlet implementation class UserUpdate
 */
@WebServlet("/UserMan")
public class UserMan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserMan() {
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
		ObjectContext context = AuxilServlet.init(request,response, User.class);

		request.setAttribute("section", "people");

		String action = request.getParameter("action");

		User currentUser = (User) request.getSession().getAttribute("currentUser");
		boolean fullPower = (Boolean)request.getAttribute("userIsAdmin");
		
		String id = request.getParameter("id");
		Integer idNum = new Integer(0);
		User user = null;

		if (id != null && !id.isEmpty()) {
			try {
				idNum = Integer.valueOf(id);
				ObjectId idUser = new ObjectId("User", User.ID_PK_COLUMN, idNum);

				// this constructor implicitly uses "CACHE_REFRESH" policy, so a fresh object will be returned
				ObjectIdQuery query = new ObjectIdQuery(idUser);

				user = (User) DataObjectUtils.objectForQuery(context, query);
				if (action == null || action.isEmpty()) action = "display";
			} catch (NumberFormatException ne) {
			}
		}
		if (action == null || action.isEmpty()) action = "list";

		String nextJSP = "/users.jsp";
		
		if (currentUser != null) {
			SelectQuery query = new SelectQuery(User.class);
			List<User> users = context.performQuery(query);
			request.setAttribute("users", users);
		} else {
			action = "error";
			nextJSP = "/index.jsp";
		}

		String email = request.getParameter("useremail");
		String admin = request.getParameter("admin");
		if (admin==null) admin="0";
		String name = request.getParameter("name");
		String gsm = request.getParameter("gsm");
		String lang = request.getParameter("language");
		String password = request.getParameter("userpassword");

		if ("display".equalsIgnoreCase(action)) {
			if (user == null) action = "error";
			else {
				nextJSP = "/userupdate.jsp";
			}
		}

		if ("update".equalsIgnoreCase(action)) {
			if (user == null) action = "create";
			if (email != null && !email.isEmpty()) user.setEmail(email);
			if (name != null && !name.isEmpty()) user.setName(name);
			if (gsm != null && !gsm.isEmpty()) user.setGsm(gsm);
			if (password != null && !password.isEmpty()) user.setPassword(password);
			if (admin != null) {
				user.setAdmin(new Short ((short) (admin.equalsIgnoreCase("yes")
						||admin.equalsIgnoreCase("true")
						||admin.equalsIgnoreCase("1")?1:0)));
			}
			user.getObjectContext().commitChanges();
			nextJSP = "/userupdate.jsp";
		}
		if ("create".equalsIgnoreCase(action)) {
			if (email != null && !email.isEmpty()) {
				SelectQuery queryMail = new SelectQuery(User.class,ExpressionFactory.matchExp(User.EMAIL_PROPERTY, email));
				List<User> noUser = context.performQuery(queryMail);
				if (noUser.isEmpty()) {
					user = (User)context.newObject(User.class);
					user.setEmail(email);
					user.setLanguage((String)request.getAttribute("lang"));
					if (admin != null) {
						user.setAdmin(new Short ((short) (admin.equalsIgnoreCase("yes")
								||admin.equalsIgnoreCase("true")
								||admin.equalsIgnoreCase("1")?1:0)));
					}
					if (name != null && !name.isEmpty()) user.setName(name);
					else user.setName(email);
					user.getObjectContext().commitChanges();
					request.setAttribute("title", "Utilisateur "+user.getName());
					nextJSP = "/userupdate.jsp";
				}
			}
		}
		if ("error".equalsIgnoreCase(action)) {
			// Erreur: ne surtout rien faire!
		}
		if (user != null) request.setAttribute("user", user);
		request.setAttribute("action",action);
		Crumb.addNewCrumb(request,User.class,user);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
	}
}
