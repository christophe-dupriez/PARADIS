package be.labo.data;

import org.apache.cayenne.DataObjectUtils;

import be.labo.data.auto._AuditScaling;

public class AuditScaling extends _AuditScaling {

	   /** Read-only access to PK */
	   public Integer getPK() {
		   return DataObjectUtils.intPKForObject(this);
	   }
}
