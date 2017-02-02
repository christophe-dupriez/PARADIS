package be.labo.data;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.ObjectId;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.ObjectIdQuery;
import org.apache.cayenne.query.SelectQuery;

import be.labo.auxil.AuxilServlet;
import be.labo.auxil.WebColor;
import be.labo.data.auto._Imagemap;

public class Imagemap extends _Imagemap implements NamedObject {

	/** Read-only access to PK */
	public Integer getPK() {
		return DataObjectUtils.intPKForObject(this);
	}

	public static Imagemap findById(ObjectContext context, String id) {
		try {
			Integer idNum = Integer.valueOf(id);
			ObjectId idImagemap = new ObjectId("Imagemap", Imagemap.ID_PK_COLUMN, idNum);

			// this constructor implicitly uses "CACHE_REFRESH" policy, so a fresh object will be returned
			ObjectIdQuery query = new ObjectIdQuery(idImagemap);

			Imagemap object = (Imagemap) DataObjectUtils.objectForQuery(context, query);
			return object;
		} catch (NumberFormatException ne) {
			return null;
		}
	}

	public void setName(String language, String name) {
		if (language ==  null) language = ""; // empty string if no language specified
		NameImage aName = this.findName(language);
		if (aName != null) {
			ObjectContext context = aName.getObjectContext();
			if (name == null || name.isEmpty()) { // no name, delete it! 
				context.deleteObject(aName);
				context.commitChanges();
			} else {
				if (name != aName.getName()) {
					aName.setName(name);
					aName.setSince(new Date());
					context.commitChanges();
				}
			}
		} else { // no existing name
			if (name != null && !name.isEmpty()) {
				ObjectContext context = this.getObjectContext();
				aName = context.newObject(NameImage.class);
				if (aName != null) {
					aName.setLanguage(language);
					aName.setName(name);
					aName.setSince(new Date());
					aName.setNameImage(this);
					context.commitChanges();
				}
			}
		}

	}

	public NameImage findName(String language) {
		if (language == null) return null;

		ObjectContext context = this.getObjectContext();
		List<Expression> list = new ArrayList<Expression>();
		list.add(ExpressionFactory.matchExp(NameImage.NAME_IMAGE_PROPERTY, 
				this.getPK()));
		list.add(ExpressionFactory.matchExp(NameImage.LANGUAGE_PROPERTY, 
				language));
		SelectQuery query = new SelectQuery(NameImage.class,ExpressionFactory.joinExp(Expression.AND, list));
		List<NameImage> names = context.performQuery(query);
		if (names == null || names.isEmpty()) return null;
		return names.get(0);   
	}

	public LinkedHashMap<String,String> getNamesMap() {
		ObjectContext context = this.getObjectContext();
		return AuxilServlet.getNamesMap(this, NameImage.class, NameImage.NAME_IMAGE_PROPERTY);
	}

	public String getBestName(HttpServletRequest request) {
		return AuxilServlet.chooseName(request, this.getImagename());
	}

	@Override
	public int compareTo(NamedObject arg0) {
		if (arg0 == null) throw new NullPointerException();
		if (arg0.getClass() != Imagemap.class) throw new InvalidParameterException();
		return this.getPK()-arg0.getPK();
	}

	public int getXmaxInt() {
		return (int) ((double)this.getXmax());
	}

	public int getYmaxInt() {
		return (int) ((double)this.getYmax());
	}

}
