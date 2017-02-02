package be.labo.data;

import org.apache.cayenne.DataObjectUtils;

import be.labo.data.auto._AuditUser;

public class AuditUser extends _AuditUser {

	   /** Read-only access to PK */
	   public Integer getPK() {
		   return DataObjectUtils.intPKForObject(this);
	   }
}
