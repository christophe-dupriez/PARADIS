package be.labo.data;

import org.apache.cayenne.DataObjectUtils;

import be.labo.data.auto._NameMeasure;

public class NameMeasure extends _NameMeasure {

	   /** Read-only access to PK */
	   public Long getPK() {
		   return DataObjectUtils.longPKForObject(this);
	   }
}
