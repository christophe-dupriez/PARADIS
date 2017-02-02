package be.labo.data;

import org.apache.cayenne.DataObjectUtils;

import be.labo.data.auto._NameNetwork;

public class NameNetwork extends _NameNetwork {

	   /** Read-only access to PK */
	   public Integer getPK() {
		   return DataObjectUtils.intPKForObject(this);
	   }
}
