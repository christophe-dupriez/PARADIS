package be.labo.data;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.ObjectId;
import org.apache.cayenne.query.ObjectIdQuery;

import be.labo.data.auto._RawDataFields;

public class RawDataFields extends _RawDataFields {

	   /** Read-only access to PK */
	   public Integer getPK() {
		   return DataObjectUtils.intPKForObject(this);
	   }
	   
		public static RawDataFields findById(ObjectContext context, String id) {
			try {
				Integer idNum = Integer.valueOf(id);
				ObjectId idDataType = new ObjectId("RawDataFields", RawDataFields.ID_PK_COLUMN, idNum);

				// this constructor implicitly uses "CACHE_REFRESH" policy, so a fresh object will be returned
				ObjectIdQuery query = new ObjectIdQuery(idDataType);

				RawDataFields object = (RawDataFields) DataObjectUtils.objectForQuery(context, query);
				return object;
			} catch (NumberFormatException ne) {
				return null;
			}
		}
		
	   public boolean getFixedDataType() {
		   return this.getInheader() > 4; // Tabs 1 to 4 are for assignable data types, 5 to 9 are for fields with FIXED datatypes (cannot be edited)
	   }
}
