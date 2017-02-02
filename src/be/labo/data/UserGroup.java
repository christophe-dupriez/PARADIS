package be.labo.data;

import org.apache.cayenne.DataObjectUtils;

import be.labo.data.auto._UserGroup;

public class UserGroup extends _UserGroup {

	   /** Read-only access to PK */
	   public Integer getPK() {
		   return DataObjectUtils.intPKForObject(this);
	   }
}
