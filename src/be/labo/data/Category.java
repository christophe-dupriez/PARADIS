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
import be.labo.data.auto._Category;

public class Category extends _Category implements NamedObject {

	/** Read-only access to PK */
	public Integer getPK() {
		return DataObjectUtils.intPKForObject(this);
	}

	public static Category findById(ObjectContext context, String id) {
		try {
			Integer idNum = Integer.valueOf(id);
			ObjectId idCategory = new ObjectId("Category", Category.ID_PK_COLUMN, idNum);

			// this constructor implicitly uses "CACHE_REFRESH" policy, so a fresh object will be returned
			ObjectIdQuery query = new ObjectIdQuery(idCategory);

			Category object = (Category) DataObjectUtils.objectForQuery(context, query);
			return object;
		} catch (NumberFormatException ne) {
			return null;
		}
	}

	public List<DataType> getDatatypes() {
		TreeSet<DataType> datatypes = new TreeSet<DataType>();
		List<DataType> dt = this.getCateg0();
		if (dt != null) datatypes.addAll(dt);
		dt = this.getCateg1();
		if (dt != null) datatypes.addAll(dt);
		dt = this.getCateg2();
		if (dt != null) datatypes.addAll(dt);
		dt = this.getCateg3();
		if (dt != null) datatypes.addAll(dt);
		dt = this.getCateg4();
		if (dt != null) datatypes.addAll(dt);
		dt = this.getCateg5();
		if (dt != null) datatypes.addAll(dt);
		dt = this.getCateg6();
		if (dt != null) datatypes.addAll(dt);
		dt = this.getCateg7();
		if (dt != null) datatypes.addAll(dt);
		dt = this.getCateg8();
		if (dt != null) datatypes.addAll(dt);
		dt = this.getCateg9();
		if (dt != null) datatypes.addAll(dt);
		return new LinkedList<DataType>(datatypes);
	}

	public void setName(String language, String name) {
		if (language ==  null) language = ""; // empty string if no language specified
		NameCategory aName = this.findName(language);
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
				aName = context.newObject(NameCategory.class);
				if (aName != null) {
					aName.setLanguage(language);
					aName.setName(name);
					aName.setSince(new Date());
					aName.setNamecategory(this);
					context.commitChanges();
				}
			}
		}

	}

	public NameCategory findName(String language) {
		if (language == null) return null;

		ObjectContext context = this.getObjectContext();
		List<Expression> list = new ArrayList<Expression>();
		list.add(ExpressionFactory.matchExp(NameCategory.NAMECATEGORY_PROPERTY, 
				this.getPK()));
		list.add(ExpressionFactory.matchExp(NameCategory.LANGUAGE_PROPERTY, 
				language));
		SelectQuery query = new SelectQuery(NameCategory.class,ExpressionFactory.joinExp(Expression.AND, list));
		List<NameCategory> names = context.performQuery(query);
		if (names == null || names.isEmpty()) return null;
		return names.get(0);   
	}

	public LinkedHashMap<String,String> getNamesMap() {
		ObjectContext context = this.getObjectContext();
		return AuxilServlet.getNamesMap(this, NameCategory.class, NameCategory.NAMECATEGORY_PROPERTY);
	}

	public String getBestName(HttpServletRequest request) {
		return AuxilServlet.chooseName(request, this.getCategoryname());
	}

	public WebColor getWebColor() {
		Short color = this.getColor();
		WebColor wColor;
		if (color == null) {
			wColor = new WebColor(0,true); // Black!
		} else {
			wColor = new WebColor(color,true);
		}
		return wColor;
	}

	public String getColorHex() {
		return "#"+getWebColor().toString();
	}

	@Override
	public int compareTo(NamedObject arg0) {
		if (arg0 == null) throw new NullPointerException();
		if (arg0.getClass() != Category.class) throw new InvalidParameterException();
		return this.getPK()-arg0.getPK();
	}

}
