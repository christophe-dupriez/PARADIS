package be.labo.data;

import org.apache.cayenne.DataObjectUtils;

import be.labo.data.auto._NameType;

public class NameType extends _NameType {

	   /** Read-only access to PK */
	   public Integer getPK() {
		   return DataObjectUtils.intPKForObject(this);
	   }
}
