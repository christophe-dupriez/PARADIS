package be.labo.data;

import org.apache.cayenne.DataObjectUtils;

import be.labo.data.auto._GraphType;

public class GraphType extends _GraphType {

	   /** Read-only access to PK */
	   public Integer getPK() {
		   return DataObjectUtils.intPKForObject(this);
	   }
}
