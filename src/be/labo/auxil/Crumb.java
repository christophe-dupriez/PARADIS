package be.labo.auxil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.cayenne.Cayenne;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.CayenneRuntime;
import org.apache.cayenne.configuration.web.WebUtil;
import org.apache.cayenne.map.ObjEntity;

import be.labo.data.NamedObject;

/*
 * Crumb of a breadcrumb!
 */
public class Crumb {
	String href = null;
	String type = null;
	String name="";
	String className = null;
	NamedObject object = null;
	int quantity = 0;

	/*
	 * This function add in the HttpServletRequest, a crumb to the current breadcrumb string
	 */
	public static void addNewCrumb(HttpServletRequest request, Class<? extends NamedObject> objectClass, NamedObject object) {
		Crumb newCrumb = new Crumb(request,objectClass,object);
		List<Crumb> crumbs = (List<Crumb>)request.getAttribute("crumbs");
		String newBreadCrumb = "";
		for (Crumb crumb : crumbs) {
			String href = crumb.getHref();
			if ( href != null && !href.equals(newCrumb.getHref()) ) {
				if (newBreadCrumb == null || newBreadCrumb.isEmpty()) {
					newBreadCrumb = crumb.getClassName();
				} else {
					newBreadCrumb += ','+crumb.getClassName();
				}
				if (crumb.getObject() != null) {
					newBreadCrumb += ':'+Integer.toString(crumb.getObject().getPK());			
				}				
			}
		}
		if (newBreadCrumb == null || newBreadCrumb.isEmpty()) {
			newBreadCrumb = objectClass.getSimpleName();
		} else {
			newBreadCrumb += ','+objectClass.getSimpleName();
		}
		request.setAttribute("newcrumb",newCrumb);
		if (object != null) {
			newBreadCrumb += ':'+Integer.toString(object.getPK());			
		}
		request.setAttribute("breadcrumb",newBreadCrumb);
	}
	
	Crumb(HttpServletRequest request, String crumb) {
		int i = crumb.indexOf(':');
		String entity = crumb;
		String key = null;
		if (i >= 0) {
			entity = crumb.substring(0,i);
			key = crumb.substring(i+1);
		}
		CayenneRuntime runtime = WebUtil.getCayenneRuntime(request.getServletContext());
		if (runtime != null) {
			ObjectContext context = runtime.getContext();
			ObjEntity objectEntity = context.getEntityResolver().getObjEntity(entity);
			if (objectEntity != null) {
				String aClassName = objectEntity.getJavaClass().getSimpleName();
				this.className = aClassName;
				this.type = aClassName.toLowerCase();
				if (key != null && !key.isEmpty()) {
					this.object = (NamedObject)Cayenne.objectForPK(context,objectEntity.getJavaClass(),Integer.valueOf(key));
					if (this.object != null) {
						this.name = this.object.getBestName(request);
						this.href = aClassName+"Man?id="+this.object.getPK();
					}
				} else {
					this.href = aClassName+"Man?";
				}
			}
		}
	}

	Crumb(HttpServletRequest request, Class crumbClass, NamedObject crumbObject) {
		this.object = crumbObject;
		if (crumbClass != null) {
			String aClassName = crumbClass.getSimpleName();
			this.className = aClassName;
			this.type = aClassName.toLowerCase();
			if (this.object != null) {
				this.name = this.object.getBestName(request);
				this.href = aClassName+"Man?id="+this.object.getPK();
			} else {
				this.href = aClassName+"Man?";
			}
		}
	}

	/**
	 * @return the href
	 */
	public String getHref() {
		return href;
	}
	/**
	 * @param href the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the object
	 */
	public NamedObject getObject() {
		return object;
	}

	/**
	 * @param object the object to set
	 */
	public void setObject(NamedObject object) {
		this.object = object;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @return the quantity
	 */
	public String getMultiples() {
		if (quantity <= 1) return "";
		else return Integer.toString(quantity);
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
