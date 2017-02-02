package be.labo;

import java.io.IOException;
import java.util.LinkedList;
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
import be.labo.data.Category;
import be.labo.data.DataType;
import be.labo.data.NameCategory;
import be.labo.data.User;

/**
 * Servlet implementation class CategoryUpdate
 */
@WebServlet("/CategoryMan")
public class CategoryMan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryMan() {
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
		ObjectContext context = AuxilServlet.init(request,response, Category.class);
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

		Category category = null;
		String id = request.getParameter("id");
		if (id != null && !id.isEmpty()) {
			category = Category.findById(context,id);
			if (category == null) {
				response.sendError(404,"category "+id+" unknown");
				return;				
			}
			if (action == null || action.isEmpty()) {
				action = "display";
			}
		}
		if (action == null || action.isEmpty()) {
			action = "list";
		}
		String nextJSP = "/categories.jsp";

		if ("update".equalsIgnoreCase(action)) {
			if (category == null) action = "create";
			else {
				if (! (Boolean)request.getAttribute("userMayUpdate")) {
					// response.setStatus(405); // Not allowed
					action = "display";
				} else {
					if ("save".equalsIgnoreCase(subaction)) {
						String color = request.getParameter(Category.COLOR_PROPERTY);
						if (color != null && !color.isEmpty()) {
							if (color != null && color.length() > 1) {
								if (color.charAt(0)=='#') {
									color = color.substring(1);
								}
								int tinyCol = 0;
								try {
									tinyCol = new WebColor(Integer.parseInt(color, 16)).getTinyColor();
								} catch (Exception e ) { }
								category.setColor((byte)tinyCol);
							}
						}
						for (String aLang : (String[]) request.getServletContext().getAttribute("supportedLocales") ) {
							String aName = request.getParameter("name_"+aLang);
							category.setName(aLang, aName);
						}
						context.commitChanges();
					}
					nextJSP = "/categoryupdate.jsp";
				}
			}
		}
		if ("create".equalsIgnoreCase(action)) {
			if (! (Boolean)request.getAttribute("userMayCreate")) {
				// response.setStatus(405); // Not allowed
				action = "list";
			} else {
				category = (Category)context.newObject(Category.class);
				String aName = request.getParameter(NameCategory.NAME_PROPERTY);
				if (aName != null && !aName.isEmpty()) {
					String userLang = AuxilServlet.currentLanguage(request);
					category.setName(userLang, aName);
				}
				context.commitChanges();
				nextJSP = "/categoryupdate.jsp";
			}
		}
		if ("delete".equalsIgnoreCase(action)) {
			if (category == null) {
				action = "list";
			} else if (! (Boolean)request.getAttribute("userMayDelete")) {
				// response.setStatus(405); // Not allowed
				action = "display";
			} else {
				if ("confirmed".equalsIgnoreCase(confirm)) {
					context.deleteObjects(category);
					context.commitChanges();
					action = "list";
					category = null;
					id = null;
				} else {
					action = "display";
				}
			}
		}
		if ("list".equalsIgnoreCase(action)) {
			List<Category> categories = null;
			String datatypeId = (String)request.getParameter("datatypeid");
			if (datatypeId!=null && !datatypeId.isEmpty()) {
				DataType datatype = DataType.findById(context, datatypeId);
				Category[] categoriesArray = datatype.getCategories();
				if (categoriesArray != null) {
					categories = new LinkedList<Category>();
					for (int i=0; i < categoriesArray.length; i++) {
						if (categoriesArray[i] != null) {
							categories.add(categoriesArray[i]);
						}
					}
				}
				request.setAttribute("datatype", datatype);
			} else {
				SelectQuery query = new SelectQuery(Category.class); // all categories!
				categories = (List<Category>) context.performQuery(query);
			}
			/*
			if (categories == null || categories.size() == 0) {
				response.sendError(400,"no categories"); // Bad request
				return;
			} else if (categories.size() == 1) {
				action = "display";
				category = categories.get(0);
				id = category.getPK().toString();	
			} else {
				request.setAttribute("categories", categories);
			}*/			
			request.setAttribute("categories", categories);
		}
		if ("display".equalsIgnoreCase(action)) {
			if (category == null) action = "error";
			else {
				request.setAttribute("readonly", " readonly=\"readonly\"");				
				nextJSP = "/categoryupdate.jsp"; // category.jsp
			}
		}

		if ("error".equalsIgnoreCase(action)) {
			response.sendError(401,"invalid request"); // Bad request
			return;
		}
		if (id != null) request.setAttribute("id", id);
		if (category != null) {
			request.setAttribute("category", category);
			request.setAttribute("symbols",FlotSymbol.values());
		}
		request.setAttribute("action",action);
		Crumb.addNewCrumb(request,Category.class,category);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
	}
}
