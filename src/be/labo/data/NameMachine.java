package be.labo.data;

import org.apache.cayenne.DataObjectUtils;

import be.labo.data.auto._NameMachine;

public class NameMachine extends _NameMachine {

	   /** Read-only access to PK */
	   public Integer getPK() {
		   return DataObjectUtils.intPKForObject(this);
	   }
}
