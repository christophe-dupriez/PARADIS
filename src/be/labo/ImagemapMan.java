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
import be.labo.data.DataType;
import be.labo.data.Imagemap;
import be.labo.data.Measure;
import be.labo.data.NameImage;
import be.labo.data.Place;
import be.labo.data.Placemap;
import be.labo.data.User;

/**
 * Servlet implementation class ImagemapUpdate
 */
@WebServlet("/ImagemapMan")
public class ImagemapMan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImagemapMan() {
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
		ObjectContext context = AuxilServlet.init(request,response, Imagemap.class);
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

		Imagemap imagemap = null;
		String id = request.getParameter("id");
		if (id != null && !id.isEmpty()) {
			imagemap = Imagemap.findById(context,id);
			if (imagemap == null) {
				response.sendError(404,"imagemap "+id+" unknown");
				return;				
			}
			if (action == null || action.isEmpty()) {
				action = "display";
			}
		}
		if (action == null || action.isEmpty()) {
			action = "list";
		}
		String nextJSP = "/imagemaps.jsp";

		if ("update".equalsIgnoreCase(action)) {
			if (imagemap == null) action = "create";
			else {
				if (! (Boolean)request.getAttribute("userMayUpdate")) {
					// response.setStatus(405); // Not allowed
					action = "display";
				} else {
					if ("save".equalsIgnoreCase(subaction)) {
						// XMAX and YMAX collected from uploaded file...
						for (String aLang : (String[]) request.getServletContext().getAttribute("supportedLocales") ) {
							String aName = request.getParameter("name_"+aLang);
							imagemap.setName(aLang, aName);
						}
						String xMax = request.getParameter(Imagemap.XMAX_PROPERTY);
						String yMax = request.getParameter(Imagemap.YMAX_PROPERTY);
						if (xMax != null && !xMax.isEmpty()) {
							try {
								imagemap.setXmax(Double.parseDouble(xMax));
							} catch (Exception e ) {
								request.setAttribute("errors", request.getAttribute("errors")
										+"X Max"+xMax+", "+jspDataAccessHelper.message("error.numbersyntax")+", "+e.toString()+"<br/>");
							}
						}
						if (yMax != null && !yMax.isEmpty()) {
							try {
								imagemap.setYmax(Double.parseDouble(yMax));
							} catch (Exception e ) {
								request.setAttribute("errors", request.getAttribute("errors")
										+"Y ax="+yMax+", "+jspDataAccessHelper.message("error.numbersyntax")+", "+e.toString()+"<br/>");
							}
						}
						for (Placemap mapPlace : imagemap.getMapPlace()) {
							String xPos = request.getParameter(Placemap.XPOS_PROPERTY+mapPlace.getMapplace().getPK());
							String yPos = request.getParameter(Placemap.YPOS_PROPERTY+mapPlace.getMapplace().getPK());
							if (xPos != null && !xPos.isEmpty()) {
								try {
									mapPlace.setXpos(Double.parseDouble(xPos));
								} catch (Exception e ) {
									request.setAttribute("errors", request.getAttribute("errors")
											+"X Pos="+xPos+", "+jspDataAccessHelper.message("error.numbersyntax")+", "+e.toString()+"<br/>");
								}
							}
							if (yPos != null && !yPos.isEmpty()) {
								try {
									mapPlace.setYpos(Double.parseDouble(yPos));
								} catch (Exception e ) {
									request.setAttribute("errors", request.getAttribute("errors")
											+"Y Pos="+yPos+", "+jspDataAccessHelper.message("error.numbersyntax")+", "+e.toString()+"<br/>");
								}
							}
						}
						context.commitChanges();
					}
					nextJSP = "/imagemapupdate.jsp";
				}
			}
		}
		if ("create".equalsIgnoreCase(action)) {
			if (! (Boolean)request.getAttribute("userMayCreate")) {
				// response.setStatus(405); // Not allowed
				action = "list";
			} else {
				imagemap = (Imagemap)context.newObject(Imagemap.class);
				String aName = request.getParameter(NameImage.NAME_PROPERTY);
				if (aName != null && !aName.isEmpty()) {
					String userLang = AuxilServlet.currentLanguage(request);
					imagemap.setName(userLang, aName);
				}
				context.commitChanges();
				nextJSP = "/imagemapupdate.jsp";
			}
		}
		if ("delete".equalsIgnoreCase(action)) {
			if (imagemap == null) {
				action = "list";
			} else if (! (Boolean)request.getAttribute("userMayDelete")) {
				// response.setStatus(405); // Not allowed
				action = "display";
			} else {
				if ("confirmed".equalsIgnoreCase(confirm)) {
					context.deleteObjects(imagemap);
					context.commitChanges();
					action = "list";
					imagemap = null;
					id = null;
				} else {
					action = "display";
				}
			}
		}
		if ("list".equalsIgnoreCase(action)) {
			List<Imagemap> imagemaps = null;
			String placeId = (String)request.getParameter("placeid");
			if (placeId!=null && !placeId.isEmpty()) {
				Place place = Place.findById(context, placeId);
				imagemaps = place.getImagemaps();
				request.setAttribute("place", place);
			} else {
				SelectQuery query = new SelectQuery(Imagemap.class); // all imagemaps!
				imagemaps = (List<Imagemap>) context.performQuery(query);
			}
			/*
			if (imagemaps == null || imagemaps.size() == 0) {
				response.sendError(400,"no imagemaps"); // Bad request
				return;
			} else if (imagemaps.size() == 1) {
				action = "display";
				imagemap = imagemaps.get(0);
				id = imagemap.getPK().toString();	
			} else {
				request.setAttribute("imagemaps", imagemaps);
			}*/			
			request.setAttribute("imagemaps", imagemaps);
		}
		if ("display".equalsIgnoreCase(action)) {
			if (imagemap == null) action = "error";
			else {
				request.setAttribute("readonly", " readonly=\"readonly\"");				
				nextJSP = "/imagemapupdate.jsp"; // imagemap.jsp
				String datatypeId = (String)request.getParameter("datatypeid");
				List<Measure> mdt = null;
				if (datatypeId!=null && !datatypeId.isEmpty()) {
					DataType datatype = DataType.findById(context, datatypeId);
					request.setAttribute("datatype", datatype);
					mdt = datatype.getTypeMeasures();
				}
				String placeId = (String)request.getParameter("placeid");
				List<Measure> mpl = null;
				if (placeId!=null && !placeId.isEmpty()) {
					Place place = Place.findById(context, placeId);
					request.setAttribute("place", place);
					mpl = place.getPlaceMeasure();
				}
				if (mdt == null) mdt = mpl;
				else if (mpl != null) {
					mdt.retainAll(mpl);
				}
				request.setAttribute("measures", mdt);
			}
		}

		if ("error".equalsIgnoreCase(action)) {
			response.sendError(401,"invalid request"); // Bad request
			return;
		}
		if (id != null) request.setAttribute("id", id);
		if (imagemap != null) {
			request.setAttribute("imagemap", imagemap);
			request.setAttribute("symbols",FlotSymbol.values());
		}
		request.setAttribute("action",action);
		Crumb.addNewCrumb(request,Imagemap.class,imagemap);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
	}
}
