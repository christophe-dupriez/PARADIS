package be.labo.data;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.ObjectId;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.ObjectIdQuery;
import org.apache.cayenne.query.SelectQuery;

import be.labo.auxil.AuxilServlet;
import be.labo.data.auto._GroupOfUsers;

public class GroupOfUsers extends _GroupOfUsers implements NamedObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -85967820503268119L;

	/** Read-only access to PK */
	public Integer getPK() {
		return DataObjectUtils.intPKForObject(this);
	}

	public void setName(String language, String name) {
		if (language ==  null) language = ""; // empty string if no language specified
		NameGroup aName = this.findName(language);
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
				aName = context.newObject(NameGroup.class);
				if (aName != null) {
					aName.setLanguage(language);
					aName.setName(name);
					aName.setSince(new Date());
					aName.setNamegroup(this);
					context.commitChanges();
				}
			}
		}

	}

	public NameGroup findName(String language) {
		if (language == null) return null;

		ObjectContext context = this.getObjectContext();
		List<Expression> list = new ArrayList<Expression>();
		list.add(ExpressionFactory.matchExp(NameGroup.NAMEGROUP_PROPERTY, 
				this.getPK()));
		list.add(ExpressionFactory.matchExp(NameGroup.LANGUAGE_PROPERTY, 
				language));
		SelectQuery query = new SelectQuery(NameGroup.class,ExpressionFactory.joinExp(Expression.AND, list));
		List<NameGroup> names = context.performQuery(query);
		if (names == null || names.isEmpty()) return null;
		return names.get(0);   
	}

	@Override
	public LinkedHashMap<String,String> getNamesMap() {
		return AuxilServlet.getNamesMap(this,NameGroup.class, NameGroup.NAMEGROUP_PROPERTY);
	}

	@Override
	public String getBestName(HttpServletRequest request) {
		return AuxilServlet.chooseName(request, this.getGroupname());
	}

	public static GroupOfUsers findById(ObjectContext context, String id) {
		try {
			Integer idNum = Integer.valueOf(id);
			ObjectId idGroupeOfUsers = new ObjectId("GroupOfUsers", GroupOfUsers.ID_PK_COLUMN, idNum);

			// this constructor implicitly uses "CACHE_REFRESH" policy, so a fresh object will be returned
			ObjectIdQuery query = new ObjectIdQuery(idGroupeOfUsers);

			GroupOfUsers object = (GroupOfUsers) DataObjectUtils.objectForQuery(context, query);
			return object;
		} catch (NumberFormatException ne) {
			return null;
		}
	}
	
	@Override
	public int compareTo(NamedObject arg0) {
		// TODO Auto-generated method stub
		if (arg0 == null) throw new NullPointerException();
		if (arg0.getClass() != GroupOfUsers.class) throw new InvalidParameterException();
		return this.getPK()-arg0.getPK();
	}

}
