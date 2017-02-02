package be.labo.data;

import org.apache.cayenne.DataObjectUtils;

import be.labo.data.auto._NameEvent;

public class NameEvent extends _NameEvent {

	   /** Read-only access to PK */
	   public Integer getPK() {
		   return DataObjectUtils.intPKForObject(this);
	   }
}
