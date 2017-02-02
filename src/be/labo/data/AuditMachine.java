package be.labo.data;

import org.apache.cayenne.DataObjectUtils;

import be.labo.data.auto._AuditMachine;

public class AuditMachine extends _AuditMachine {

	   /** Read-only access to PK */
	   public Integer getPK() {
		   return DataObjectUtils.intPKForObject(this);
	   }
}
