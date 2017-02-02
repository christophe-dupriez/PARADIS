package be.labo.data;

import org.apache.cayenne.DataObjectUtils;

import be.labo.data.auto._AuditAlert;

public class AuditAlert extends _AuditAlert {

	   /** Read-only access to PK */
	   public Integer getPK() {
		   return DataObjectUtils.intPKForObject(this);
	   }
}
