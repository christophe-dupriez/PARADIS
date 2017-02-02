package be.labo.data;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.ObjectId;
import org.apache.cayenne.query.ObjectIdQuery;

import be.labo.data.auto._Alert;

public class Alert extends _Alert {
	   /** Read-only access to PK */
	   public Integer getPK() {
		   return DataObjectUtils.intPKForObject(this);
	   }

	   public static Alert findById(ObjectContext context, String id) {
		   try {
			   Integer idNum = Integer.valueOf(id);
			   ObjectId idAlert = new ObjectId("Alert", Alert.ID_PK_COLUMN, idNum);

			   // this constructor implicitly uses "CACHE_REFRESH" policy, so a fresh object will be returned
			   ObjectIdQuery query = new ObjectIdQuery(idAlert);

			   Alert object = (Alert) DataObjectUtils.objectForQuery(context, query);
			   return object;
		   } catch (NumberFormatException ne) {
			   return null;
		   }
	   }
}
