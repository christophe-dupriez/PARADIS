package be.labo;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.TreeMap;

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
import be.labo.auxil.Seconds;
import be.labo.auxil.WebColor;
import be.labo.data.Category;
import be.labo.data.DataType;
import be.labo.data.Event;
import be.labo.data.Graphconfig;
import be.labo.data.Machine;
import be.labo.data.NameType;
import be.labo.data.Scaling;
import be.labo.data.User;

/**
 * Servlet implementation class DataTypeUpdate
 */
@WebServlet("/DataTypeMan")
public class DataTypeMan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataTypeMan() {
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
		ObjectContext context = AuxilServlet.init(request,response, DataType.class);
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
		String subaction = request.getParameter("subaction");
		String confirm = request.getParameter("confirm");
		if (confirm != null) request.setAttribute("confirm",confirm);

		DataType dataType = null;
		String id = request.getParameter("id");
		if (id != null && !id.isEmpty()) {
			dataType = DataType.findById(context,id);
			if (dataType == null) {
				response.sendError(404,"dataType "+id+" unknown");
				return;				
			}
			request.setAttribute("datatype", dataType);
			if (action == null || action.isEmpty()) {
				action = "display";
			}
		}
		if (action == null || action.isEmpty()) {
			action = "list";
		}
		String nextJSP = "/datatypes.jsp";

		if ("interface".equalsIgnoreCase(action)) {
			if (dataType == null) action = "create";
			else {
				if (! (Boolean)request.getAttribute("userMayUpdate")) {
					// response.setStatus(405); // Not allowed
					action = "display";
				} else {
					nextJSP = "/datatypeinterface.jsp";
				}
			}
		}

		if ("update".equalsIgnoreCase(action)) {
			if (dataType == null) action = "create";
			else {
				if (! (Boolean)request.getAttribute("userMayUpdate")) {
					// response.setStatus(405); // Not allowed
					action = "display";
				} else {
					if ("save".equalsIgnoreCase(subaction)) {
						String symbol = request.getParameter(DataType.SYMBOL_PROPERTY);
						String isaletter = request.getParameter(DataType.ISALETTER_PROPERTY);
						String presentation = request.getParameter(DataType.PRESENTATION_PROPERTY);
						String descr = request.getParameter(DataType.DESCRIPTION_PROPERTY);
						String unit = request.getParameter(DataType.UNIT_PROPERTY);
						String defaulttimespan = request.getParameter(DataType.DEFAULTTIMESPAN_PROPERTY);
						String active = request.getParameter( DataType.ACTIVE_PROPERTY);
						String inheader = request.getParameter( DataType.INHEADER_PROPERTY);
						String decimals = request.getParameter( DataType.DECIMALS_PROPERTY);
						String nbcategories = request.getParameter( DataType.NBCATEGORIES_PROPERTY);
						String max = request.getParameter( DataType.MAX_PROPERTY);
						String min[] = new String[10];
						min[0] = request.getParameter( DataType.MIN_PROPERTY);
						min[1] = request.getParameter( DataType.MIN1_PROPERTY);
						min[2] = request.getParameter( DataType.MIN2_PROPERTY);
						min[3] = request.getParameter( DataType.MIN3_PROPERTY);
						min[4] = request.getParameter( DataType.MIN4_PROPERTY);
						min[5] = request.getParameter( DataType.MIN5_PROPERTY);
						min[6] = request.getParameter( DataType.MIN6_PROPERTY);
						min[7] = request.getParameter( DataType.MIN7_PROPERTY);
						min[8] = request.getParameter( DataType.MIN8_PROPERTY);
						min[9] = request.getParameter( DataType.MIN9_PROPERTY);
						String categ[] = new String[10];
						categ[0] = request.getParameter( DataType.CATEG0_PROPERTY);
						categ[1] = request.getParameter( DataType.CATEG1_PROPERTY);
						categ[2] = request.getParameter( DataType.CATEG2_PROPERTY);
						categ[3] = request.getParameter( DataType.CATEG3_PROPERTY);
						categ[4] = request.getParameter( DataType.CATEG4_PROPERTY);
						categ[5] = request.getParameter( DataType.CATEG5_PROPERTY);
						categ[6] = request.getParameter( DataType.CATEG6_PROPERTY);
						categ[7] = request.getParameter( DataType.CATEG7_PROPERTY);
						categ[8] = request.getParameter( DataType.CATEG8_PROPERTY);
						categ[9] = request.getParameter( DataType.CATEG9_PROPERTY);
						String scalingid = request.getParameter("scalingid");

						dataType.setActive(AuxilServlet.isChecked(active));

						if (presentation != null && !presentation.isEmpty()) {
							Byte valPresentation = null;
							try {
								valPresentation = Byte.parseByte(presentation);
							} catch (Exception e ) { }
							if (valPresentation != null) {
								dataType.setPresentation(valPresentation);
								if (valPresentation == 1/*Boolean*/) {
									dataType.setNbcategories((byte)2);  // true and false if stored (may be also missing)
									nbcategories = null;
									dataType.setDecimals((byte)0);   // no decimals for booleans!
									decimals = null;
									dataType.setUnit(null); // no unit of measure
									unit = null;
								} else if (valPresentation == 2 /*Identifier*/) {
									if (dataType.getNbcategories() == null) {
										dataType.setNbcategories((byte)1); // One category by default
									}
									dataType.setDecimals((byte)0);   // identifiers are INTEGER but categories of identifiers are allowed
									decimals = null;
									dataType.setUnit(null); // no unit of measure
									unit = null;
								} // else a measure: units, decimals, categories...
							}
						}
						if (decimals != null && !decimals.isEmpty()) {
							try {
								dataType.setDecimals(Byte.parseByte(decimals));
							} catch (Exception e ) { }
						}
						if (inheader != null && !inheader.isEmpty()) {
							try {
								dataType.setInheader(Byte.parseByte(inheader));
							} catch (Exception e ) { }
						}
						if (defaulttimespan != null && !defaulttimespan.isEmpty()) {
							Seconds ts = Seconds.parse(defaulttimespan);
							if (ts != null) dataType.setDefaulttimespan(ts.getTicks());
						}
						if (max != null && !max.isEmpty()) {
							try {
								dataType.setMax(Double.parseDouble(max));
							} catch (Exception e ) { }
						}
						for (int i=0; i<10; i++) {
							if (min[i] != null && !min[i].isEmpty()) {
								try {
									dataType.writeProperty(DataType.MIN_PROPERTY+(i>0?""+i:""),Double.parseDouble(min[i]));
								} catch (Exception e ) { }
							}

							if (categ[i] != null && !categ[i].isEmpty()) {
								Category category = Category.findById(context, categ[i]);
								if (category != null) {
									dataType.writeProperty("categ"+i,category);
								}
							}
						}
						 // This also resets Categories:
						if (nbcategories != null && !nbcategories.isEmpty()) {
							try {
								dataType.setNbcategories(Byte.parseByte(nbcategories));
							} catch (Exception e ) { }
						} else {
							Byte nbc = dataType.getNbcategories();
							if (nbc != null) {
								dataType.setNbcategories(nbc);
							}
						}

/*
    public static final String DEFAULTTIMESPAN_PROPERTY = "defaulttimespan";
    public static final String DATA_TYPE_FIELD_PROPERTY = "dataTypeField";
    public static final String EVENTS_PROPERTY = "events";
    public static final String MACHINES_PROPERTY = "machines";
    public static final String SINGLE_BIT_MACHINES_PROPERTY = "singleBitMachines";
    public static final String SUBTYPE_PROPERTY = "subtype";
    public static final String TYPE_AUDIT_PROPERTY = "typeAudit";
    public static final String TYPE_CONDITION_PROPERTY = "typeCondition";
 */
						if (unit != null && !unit.isEmpty()) { 
							dataType.setUnit(unit);
						}
						if (isaletter != null && !isaletter.isEmpty()) { 
							dataType.setIsaletter(isaletter);
						}
						if (symbol != null && !symbol.isEmpty()) {
							try {
								dataType.setSymbol(Byte.parseByte(symbol));
							} catch (Exception e ) { }
						}
						if (descr != null) dataType.setDescription(descr);
						for (String aLang : (String[]) request.getServletContext().getAttribute("supportedLocales") ) {
							String aName = request.getParameter("name_"+aLang);
							dataType.setName(aLang, aName);
						}
						context.commitChanges();
					}
					nextJSP = "/datatypeupdate.jsp";
				}
			}
		}
		if ("create".equalsIgnoreCase(action)) {
			if (! (Boolean)request.getAttribute("userMayCreate")) {
				// response.setStatus(405); // Not allowed
				action = "list";
			} else {
				dataType = (DataType)context.newObject(DataType.class);
				dataType.setActive(true);
				String aName = request.getParameter(NameType.NAME_PROPERTY);
				if (aName != null && !aName.isEmpty()) {
					String userLang = AuxilServlet.currentLanguage(request);
					dataType.setName(userLang, aName);
				}
				context.commitChanges();
				nextJSP = "/datatypeupdate.jsp";
			}
		}
		if ("delete".equalsIgnoreCase(action)) {
			if (dataType == null) {
				action = "list";
			} else if (! (Boolean)request.getAttribute("userMayDelete")) {
				// response.setStatus(405); // Not allowed
				action = "display";
			} else {
				if ("confirmed".equalsIgnoreCase(confirm)) {
					context.deleteObjects(dataType);
					context.commitChanges();
					action = "list";
					dataType = null;
					id = null;
				} else {
					action = "display";
				}
			}
		}
		if ("list".equalsIgnoreCase(action)) {
			List<DataType> dataTypes = null;
			String eventId = (String)request.getParameter("eventid");
			if (eventId!=null && !eventId.isEmpty()) {
				Event event = Event.findById(context, eventId);
				dataTypes = event.getDatatypes();
				request.setAttribute("event", event);
			} else {
				String categoryId = (String)request.getParameter("categoryid");
				if (categoryId!=null && !categoryId.isEmpty()) {
					Category category = Category.findById(context, categoryId);
					dataTypes = category.getDatatypes();
					request.setAttribute("category", category);
				} else {
					String machineId = (String)request.getParameter("machineid");
					if (machineId!=null && !machineId.isEmpty()) {
						Machine machine = Machine.findById(context, machineId);
						dataTypes = machine.getDatatypes();
						request.setAttribute("machine", machine);
					} else {
						String graphconfigId = (String)request.getParameter("graphconfigid");
						if (graphconfigId!=null && !graphconfigId.isEmpty()) {
							Graphconfig graphconfig = Graphconfig.findById(context, graphconfigId);
							dataTypes = graphconfig.getDatatypes();
							request.setAttribute("graphconfig", graphconfig);
						} else {
							String scalingId = (String)request.getParameter("scalingid");
							if (scalingId!=null && !scalingId.isEmpty()) {
								Scaling scaling = Scaling.findById(context, scalingId);
								dataTypes = scaling.getDatatypes();
								request.setAttribute("scaling", scaling);
							} else {
								SelectQuery query = new SelectQuery(DataType.class); // all dataTypes!
								dataTypes = context.performQuery(query);
							}
						}
					}
				}
			}
			/*
			if (dataTypes == null || dataTypes.isEmpty()) {
				response.sendError(400,"no datatypes"); // Bad request
				return;
			} else if (dataTypes.size() == 1) {
				action = "display";
				dataType = dataTypes.get(0);
				id = dataType.getPK().toString();	
			} else {
				request.setAttribute("datatypes", dataTypes);
			}*/			
			request.setAttribute("datatypes", dataTypes);
		}
		if ("display".equalsIgnoreCase(action)) {
			if (dataType == null) action = "error";
			else {
				request.setAttribute("readonly", " readonly=\"readonly\"");				
				nextJSP = "/datatypeupdate.jsp"; // was datatype.jsp
			}
		}

		if ("error".equalsIgnoreCase(action)) {
			response.sendError(401,"invalid request"); // Bad request
			return;
		}
		if (id != null) request.setAttribute("id", id);
		if (dataType != null) {
			request.setAttribute("datatype", dataType);
			request.setAttribute("symbols",FlotSymbol.values());
		}
		Map<String,String> presentations = new TreeMap<String,String>();
		try {
			ResourceBundle currBundle = ResourceBundle.getBundle("Messages",(Locale)request.getAttribute("currentLocale"));
			Enumeration<String> keys = currBundle.getKeys();
			while (keys.hasMoreElements()) {
				String currCode = keys.nextElement();
				if (currCode.startsWith("presentation.")) {
					presentations.put(currCode.substring("presentation.".length()), currBundle.getString(currCode));
				}
			}
		} catch (MissingResourceException e) {
		}
		request.setAttribute("presentations", presentations);
		Map<String,String> isaletters = new TreeMap<String,String>();
		try {
			ResourceBundle currBundle = ResourceBundle.getBundle("Messages",(Locale)request.getAttribute("currentLocale"));
			Enumeration<String> keys = currBundle.getKeys();
			while (keys.hasMoreElements()) {
				String currCode = keys.nextElement();
				if (currCode.startsWith("isa.")) {
					isaletters.put(currCode.substring("isa.".length()), currBundle.getString(currCode));
				}
			}
		} catch (MissingResourceException e) {
		}
		request.setAttribute("isaletters", isaletters);
		request.setAttribute("action",action);
		Crumb.addNewCrumb(request,DataType.class,dataType);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
	}
}
