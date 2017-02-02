package be.labo.data;

import org.apache.cayenne.DataObjectUtils;

import be.labo.data.auto._AssertionMachine;

public class AssertionMachine extends _AssertionMachine {
	   /** Read-only access to PK */
	   public Integer getPK() {
		   return DataObjectUtils.intPKForObject(this);
	   }
}
