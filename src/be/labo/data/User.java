package be.labo.data;

import java.security.InvalidParameterException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.ObjectId;
import org.apache.cayenne.query.ObjectIdQuery;
import org.apache.cayenne.query.SelectQuery;

import be.labo.data.auto._User;

public class User extends _User implements NamedObject {

	/** Read-only access to PK */
	public Integer getPK() {
		return DataObjectUtils.intPKForObject(this);
	}

	public void setName(String language, String name) {
		this.setName(name);
	}

	public Names findName(String language) {
		return null;
	}

	public LinkedHashMap<String,String> getNamesMap() {
		LinkedHashMap<String,String> result = new LinkedHashMap<String,String>();
		result.put("en", this.getName());
		return result;
	}

	public String getBestName(HttpServletRequest request) {
		return this.getName();
	}

	public static User findById(ObjectContext context, String id) {
		try {
			Integer idNum = Integer.valueOf(id);
			ObjectId idUser = new ObjectId("User", User.ID_PK_COLUMN, idNum);

			// this constructor implicitly uses "CACHE_REFRESH" policy, so a fresh object will be returned
			ObjectIdQuery query = new ObjectIdQuery(idUser);

			User object = (User) DataObjectUtils.objectForQuery(context, query);
			return object;
		} catch (NumberFormatException ne) {
			return null;
		}
	}

	@Override
	public int compareTo(NamedObject arg0) {
		// TODO Auto-generated method stub
		if (arg0 == null) throw new NullPointerException();
		if (arg0.getClass() != User.class) throw new InvalidParameterException();
		return this.getPK()-arg0.getPK();
	}

}
