package be.labo.data;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.ObjectId;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.ObjectIdQuery;
import org.apache.cayenne.query.SelectQuery;

import be.labo.auxil.AuxilServlet;
import be.labo.data.auto._Network;

public class Network extends _Network implements NamedObject {

	/** Read-only access to PK */
	public Integer getPK() {
		return DataObjectUtils.intPKForObject(this);
	}

	public void setName(String language, String name) {
		if (language ==  null) language = ""; // empty string if no language specified
		NameNetwork aName = this.findName(language);
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
				aName = context.newObject(NameNetwork.class);
				if (aName != null) {
					aName.setLanguage(language);
					aName.setName(name);
					aName.setSince(new Date());
					aName.setNameNetwork(this);
					context.commitChanges();
				}
			}
		}

	}

	public NameNetwork findName(String language) {
		if (language == null) return null;

		ObjectContext context = this.getObjectContext();
		List<Expression> list = new ArrayList<Expression>();
		list.add(ExpressionFactory.matchExp(NameNetwork.NAME_NETWORK_PROPERTY, 
				this.getPK()));
		list.add(ExpressionFactory.matchExp(NameNetwork.LANGUAGE_PROPERTY, 
				language));
		SelectQuery query = new SelectQuery(NameNetwork.class,ExpressionFactory.joinExp(Expression.AND, list));
		List<NameNetwork> names = context.performQuery(query);
		if (names == null || names.isEmpty()) return null;
		return names.get(0);   
	}

	public LinkedHashMap<String,String> getNamesMap() {
		ObjectContext context = this.getObjectContext();
		return AuxilServlet.getNamesMap(this, NameNetwork.class, NameNetwork.NAME_NETWORK_PROPERTY);
	}

	public String getBestName(HttpServletRequest request) {
		return AuxilServlet.chooseName(request, this.getNetworkName());
	}

	public static Network findById(ObjectContext context, String id) {
		try {
			Integer idNum = Integer.valueOf(id);
			ObjectId idNetwork = new ObjectId("Network", Network.ID_PK_COLUMN, idNum);

			// this constructor implicitly uses "CACHE_REFRESH" policy, so a fresh object will be returned
			ObjectIdQuery query = new ObjectIdQuery(idNetwork);

			Network object = (Network) DataObjectUtils.objectForQuery(context, query);
			return object;
		} catch (NumberFormatException ne) {
			return null;
		}
	}
	
	@Override
	public int compareTo(NamedObject arg0) {
		// TODO Auto-generated method stub
		if (arg0 == null) throw new NullPointerException();
		if (arg0.getClass() != Network.class) throw new InvalidParameterException();
		return this.getPK()-arg0.getPK();
	}

}
