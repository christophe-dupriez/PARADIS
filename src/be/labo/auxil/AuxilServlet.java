package be.labo.auxil;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.cayenne.CayenneDataObject;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.CayenneRuntime;
import org.apache.cayenne.configuration.web.WebUtil;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import be.labo.data.Category;
import be.labo.data.DataType;
import be.labo.data.Devicetype;
import be.labo.data.Imagemap;
import be.labo.data.Machine;
import be.labo.data.MetaClass;
import be.labo.data.NamedObject;
import be.labo.data.Names;
import be.labo.data.Network;
import be.labo.data.Permission;
import be.labo.data.Place;
import be.labo.data.RawDataFields;
import be.labo.data.Rawtables;
import be.labo.data.Scaling;
import be.labo.data.User;
import be.labo.data.UserGroup;

public class AuxilServlet {

	private final static Logger LOGGER = Logger.getLogger(AuxilServlet.class.getName());
	
	final static String defaultLanguage = "en";

	ObjectContext context;
	HttpServletRequest request;
	
	AuxilServlet (ObjectContext oc, HttpServletRequest or) {
		context = oc;
		request = or;
	}

	public static ObjectContext init(HttpServletRequest request, HttpServletResponse response, Class classAccessed) {
		//Set encoding to UTF-8, if not set yet
        //This avoids problems of using the HttpServletRequest
        //in the getSpecialGroups() for an AuthenticationMethod,  
        //which causes the HttpServletRequest to default to 
        //non-UTF-8 encoding.
        try
        {
            if(request.getCharacterEncoding()==null)
                request.setCharacterEncoding("UTF-8");
        }
        catch(Exception e)
        {
            LOGGER.info("Unable to set encoding to UTF-8: "+e.toString());
        }
        /*
         * To allow redirection to the page where the user was de-authenticated
         * THIS CONVEYS POSTed DATA !!! (see footer.jsp)
         */
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
        	requestUri = (String) request.getParameter("erroruri");
        }
        if (requestUri != null) {
        	request.setAttribute("erroruri", requestUri);
        	Enumeration<String> names = request.getParameterNames();
        	if (names != null) {
        		LinkedList<String> paramNames = new LinkedList<String>();
        		while (names.hasMoreElements()) {
        			String name = names.nextElement();
        			paramNames.add(name);
            		request.setAttribute(name, request.getParameter(name));
        		}
        		request.setAttribute("errorparameternames", paramNames);
        		request.setAttribute("errorparametercount", paramNames.size());
        	}
        }
       
        if (response != null) {
			response.setContentType("text/html; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
		}

		Date now = new Date();
		long timestamp = now.getTime();
		
		String logoff = request.getParameter("_logout_");
		HttpSession session = request.getSession();

		/*
		 * Not really needed...
		String from = request.getParameter("from");
		request.setAttribute("from", from);
		 */

		if (logoff!=null && !logoff.isEmpty()) {
			session.removeAttribute("currentUser");
		}

		ServletContext servletContext = request.getServletContext();
		CayenneRuntime runtime = WebUtil.getCayenneRuntime(servletContext);
		ObjectContext aContext = runtime.getContext();

		String email = request.getParameter("_username_");
		String password = request.getParameter("_password_");
		if (email!=null && !email.isEmpty() && password != null) {
			SelectQuery queryMail = new SelectQuery(User.class,ExpressionFactory.matchExp(User.EMAIL_PROPERTY, email));
			List<User> logUser = aContext.performQuery(queryMail);
			if (!logUser.isEmpty()) {
				for (User anUser : logUser) {
					if (password.equals(anUser.getPassword())) {
						session.setAttribute("currentUser", anUser);
					}
				}
			}
		}
		User currentUser = (User) session.getAttribute("currentUser");
		//TODO:VALIDATE User Cayenne object and LOGOFF if INVALID
		//TODO:This validation is necessary for any Cayenne object stored in a Session attribute...
		boolean fullPower = classAccessed == null; // This a BAD TRICK to allow data feed to come in
		// CRUD permissions...
		boolean createPower = false;
		boolean readPower = false;
		boolean updatePower = false;
		boolean deletePower = false;
		boolean isActive = false;
		if (currentUser != null) {
			// Groups to which the user really pertains
			HashSet<Integer> selUserGroups = new HashSet<Integer>();
			request.setAttribute("userGroups", selUserGroups);
			List<UserGroup> userGroups = currentUser.getUsergroup();
			if (userGroups != null) {
				for (UserGroup ug : userGroups) {
					Date since = ug.getSince();
					if (since == null || since.getTime() <= timestamp) {
						Date upto = ug.getUpto();
						if (upto == null) {
							long uptoTime = upto.getTime();
							if ( uptoTime > 0 && uptoTime > timestamp) {
								selUserGroups.add(ug.getUsergroup().getPK());
							}
						}
					}
				}
			}
			
		    // CRUD privileges of the user for the current Class
			Boolean active =  currentUser.getActive();
			if (active != null && active.booleanValue() ) {
				isActive =  true;
				Short isAdmin =  currentUser.getAdmin();
				if (isAdmin != null && isAdmin.shortValue() != 0) {
					fullPower =  true;
					createPower = true;
					readPower = true;
					updatePower = true;
					deletePower = true;
				} else if (!selUserGroups.isEmpty()) { // Analyze permissions
					   SelectQuery queryMeta = new SelectQuery(MetaClass.class,ExpressionFactory.matchExp(MetaClass.JAVA_NAME_PROPERTY,classAccessed.getSimpleName()));
					   List<MetaClass> metaClasses = aContext.performQuery(queryMeta);
					   if (metaClasses != null && !metaClasses.isEmpty()) {
						   MetaClass metaClass = metaClasses.get(0);
						   SelectQuery queryPerm = new SelectQuery(Permission.class,ExpressionFactory.matchExp(Permission.PERMISSION_META_PROPERTY,metaClass.getPK()));
						   List<Permission> permissions = aContext.performQuery(queryPerm);
						   if (permissions != null && !permissions.isEmpty()) {
							   for (Permission perm : permissions) {
								   if (selUserGroups.contains(perm.getPermissionGroup().getPK())) {
									   // || is way better here than |= !!!
									   createPower = createPower || perm.getMayCreate();
									   readPower = readPower || perm.getMayRead();
									   updatePower = updatePower || perm.getMayUpdate();
									   deletePower = deletePower || perm.getMayDelete();
								   }
							   }
						   }
					   }
				}
			} else { // Logoff!
				session.removeAttribute("currentUser");
			}
		}
		request.setAttribute("userIsAdmin", fullPower);
		request.setAttribute("userMayCreate", createPower);
		request.setAttribute("userMayRead", readPower);
		request.setAttribute("userMayUpdate", updatePower);
		request.setAttribute("userMayDelete", deletePower);

        Locale requestLocale = request.getLocale();
        String desiredLanguage = (String) request.getParameter("locale");
        if (desiredLanguage != null && !desiredLanguage.isEmpty())
            requestLocale = new Locale(desiredLanguage);

        if (session != null) {
            if (desiredLanguage != null && !desiredLanguage.isEmpty()) {
                session.setAttribute("locale",requestLocale);
            } else {
                if (session.getAttribute("locale") != null) {
                    requestLocale = (Locale) session.getAttribute("locale");
                }
            }
            Config.set(session, Config.FMT_LOCALE, requestLocale);
        }
        // Session locale is not always set!
        request.setAttribute(Config.FMT_LOCALE, requestLocale);
        request.setAttribute("currentLocale", requestLocale);
		if (response != null) {
		    response.setLocale(requestLocale);
		}

		LinkedList<Crumb> crumbsList = new LinkedList<Crumb>(); 
		String baseCrumbs = request.getParameter("breadcrumb");
		if (baseCrumbs != null && !baseCrumbs.isEmpty()) {
			String[] crumbs = baseCrumbs.split(",");
			for (String crumb : crumbs) {
				Crumb aCrumb = new Crumb(request, crumb);
				if (aCrumb.getClassName() != null) {
					crumbsList.add(aCrumb);
				}
			}
		}
		request.setAttribute("breadcrumb", baseCrumbs);
		request.setAttribute("crumbs", crumbsList);
		request.setAttribute("currentTab", request.getParameter("currenttab"));

		// Ensure access to the ordered list of supported languages:
		if (servletContext.getAttribute("supportedLocales") == null) {
			/*
			 *  Configuration of languages in web.xml:
			 *    <context-param>
					<param-name>javax.servlet.jsp.jstl.fmt.supportedLocales</param-name>
					<param-value>fr,nl,de,en</param-value>
				  </context-param>
			 */
			String supLangs = servletContext.getInitParameter("javax.servlet.jsp.jstl.fmt.supportedLocales");
			if (supLangs != null && !supLangs.isEmpty()) {
				String[] paramLocales = supLangs.split(",");
				LinkedList<Locale> newLocales = new LinkedList<Locale>();
				for (String aLang : paramLocales) {
					aLang = aLang.trim();
					if (!aLang.isEmpty()) {
						newLocales.add(new Locale(aLang));
					}
				}
				if (newLocales.size() > 0) {
					Names.supportedLocales = newLocales.toArray(new Locale[0]);
				}
			}

			String[] supportedLocales = new String[Names.supportedLocales.length];
			for (int i=0; i < Names.supportedLocales.length; i++) {
				supportedLocales[i] = Names.supportedLocales[i].toString();
			}
			servletContext.setAttribute("supportedLocales", supportedLocales);
		}

		AuxilServlet jspDataAccessHelper = new AuxilServlet(aContext, request);
		request.setAttribute("auxil", jspDataAccessHelper);
		
		return aContext;
	}

	public static boolean isChecked(String attributeValue) {
		if (attributeValue != null && !attributeValue.isEmpty()) {
			char c = Character.toLowerCase(attributeValue.charAt(0));
			return ( (c>'0' && c<='9') || c=='a' || c=='c' || c=='s' || c=='t' || c=='v' );
		} else return false; // NULL means FALSE...
	}
	
	public static String getMessageFromBundle(HttpServletRequest request, String messageKey) {
		if (messageKey == null || messageKey.isEmpty()) return "";
        Locale loc = null;
        if (request != null) loc = (Locale) request.getAttribute("currentLocale");
		if (loc == null) loc = Locale.ENGLISH;
		ResourceBundle messageBundle = ResourceBundle.getBundle("Messages", loc);
		if (messageBundle == null) return messageKey;
		String message = messageBundle.getString(messageKey);
		if (message == null || message.isEmpty()) return messageKey;
		return message;
	}

	public String message(String messageKey) {
		/*
		 * Use <fmt:message instead!
		 */
		return getMessageFromBundle(request,messageKey);
	}

	public static String currentLanguage(HttpServletRequest request) {
        Locale loc = (Locale) request.getAttribute("currentLocale");
        String lang = defaultLanguage;
        if (loc != null) {
        	lang = loc.toString();
        	if (lang.length() > 2) {
        		lang = lang.substring(0,2);
        	} else if (lang.isEmpty()) {
        		lang = defaultLanguage;
        	}
        }
		return lang;
	}
	
	public String getAttribute(String name) {
		if (name == null || name.isEmpty()) return null;
		Object o = request.getAttribute(name);
		if (o == null) return null;
		return o.toString();
	}

	public String getCurrentLanguage() {
		return currentLanguage(request);
	}
	/*
	 * Returns the most suitable name available (user language, english then the first available language)
	 */
	public static String chooseName(HttpServletRequest request, List<? extends Names> nameList) {
		if (nameList == null || nameList.isEmpty()) return "";
        String lang = currentLanguage(request);
        String english = "";
		for (Names name : nameList) {
			if (name.getLanguage().equalsIgnoreCase(lang)) return name.getName();
			if (name.getLanguage().startsWith("en")) english = name.getName();
		}
		if (english.isEmpty()) return nameList.get(0).getName();
		else return english;
	}
	
	public static LinkedHashMap<String,String> getNamesMap(NamedObject namedObject, Class<? extends Names> classNames, String name2objectField) {
	   ObjectContext context = namedObject.getObjectContext();
	   SelectQuery query = new SelectQuery(classNames,ExpressionFactory.matchExp(name2objectField, 
			   namedObject.getPK()));
	   List<? extends Names> names = context.performQuery(query);
	   LinkedHashMap<String,String> result = new LinkedHashMap<String,String>();
	   for (Names aName : names) {
		   result.put(aName.getLanguage(),aName.getName());
	   }
	   return result;
	}
	
	public List<Network> getAllNetworks() {
		SelectQuery query = new SelectQuery(Network.class); // all networks!
		return context.performQuery(query);
	}
	
	public List<Place> getAllPlaces() {
		SelectQuery query = new SelectQuery(Place.class); // all networks!
		return context.performQuery(query);
	}
	
	public List<Machine> getAllMachines() {
		SelectQuery query = new SelectQuery(Machine.class); // all Machines!
		return context.performQuery(query);
	}
	
	public List<Category> getAllCategories() {
		SelectQuery query = new SelectQuery(Category.class); // all Categories!
		query.addOrdering(new Ordering("color", SortOrder.ASCENDING));
		return context.performQuery(query);
	}
	
	public List<DataType> getAllDataTypes() {
		SelectQuery query = new SelectQuery(DataType.class); // all DataTypes!
		return context.performQuery(query);
	}
	
	public List<DataType> findDataTypesByHeader(Byte currHeader) {
	   SelectQuery query = new SelectQuery(DataType.class,ExpressionFactory.matchExp(DataType.INHEADER_PROPERTY, currHeader));
	   return context.performQuery(query);
	}
	
	public List<Scaling> getAllScalings() {
		SelectQuery query = new SelectQuery(Scaling.class); // all possible record structures!
		return context.performQuery(query);
	}
	
	public List<DataType> findScalingByUnit(String unit) {
		   SelectQuery query = new SelectQuery(Scaling.class,ExpressionFactory.matchExp(Scaling.UNIT_PROPERTY, unit));
		   return context.performQuery(query);
		}
		
	public List<Rawtables> getAllRawTables() {
		SelectQuery query = new SelectQuery(Rawtables.class); // all possible record structures!
		return context.performQuery(query);
	}
	
	public List<RawDataFields> getAllRawDataFields() {
		SelectQuery query = new SelectQuery(RawDataFields.class); // all possible records fields!
		return context.performQuery(query);
	}
	
	public List<Devicetype> getAllDeviceTypes() {
		SelectQuery query = new SelectQuery(Devicetype.class); // all possible devicetypes!
		return context.performQuery(query);
	}
	
	public List<Imagemap> getAllImagemaps() {
		SelectQuery query = new SelectQuery(Imagemap.class); // all possible record structures!
		return context.performQuery(query);
	}
	
	public boolean removeAnItem(List<? extends CayenneDataObject> lcdo, CayenneDataObject cdo) {
		return lcdo.remove(cdo); // Avoid EL confusion with remove(int) 
	}
}
