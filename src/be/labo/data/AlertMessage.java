package be.labo.data;

import org.apache.cayenne.DataObjectUtils;

import be.labo.data.auto._AlertMessage;

public class AlertMessage extends _AlertMessage {
	   /** Read-only access to PK */
	   public Integer getPK() {
		   return DataObjectUtils.intPKForObject(this);
	   }
}
