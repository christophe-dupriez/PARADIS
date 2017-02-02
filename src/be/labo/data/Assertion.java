package be.labo.data;

import org.apache.cayenne.DataObjectUtils;

import be.labo.data.auto._Assertion;

public class Assertion extends _Assertion {
	   /** Read-only access to PK */
	   public Integer getPK() {
		   return DataObjectUtils.intPKForObject(this);
	   }
}
