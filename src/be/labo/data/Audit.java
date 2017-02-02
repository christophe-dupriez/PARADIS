package be.labo.data;

import org.apache.cayenne.DataObjectUtils;

import be.labo.data.auto._Audit;

public class Audit extends _Audit {

	   /** Read-only access to PK */
	   public Long getPK() {
		   return DataObjectUtils.longPKForObject(this);
	   }

}
