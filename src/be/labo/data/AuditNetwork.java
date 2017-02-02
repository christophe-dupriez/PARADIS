package be.labo.data;

import org.apache.cayenne.DataObjectUtils;

import be.labo.data.auto._AuditNetwork;

public class AuditNetwork extends _AuditNetwork {

	   /** Read-only access to PK */
	   public Integer getPK() {
		   return DataObjectUtils.intPKForObject(this);
	   }
}
