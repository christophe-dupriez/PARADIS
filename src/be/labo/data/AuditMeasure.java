package be.labo.data;

import org.apache.cayenne.DataObjectUtils;

import be.labo.data.auto._AuditMeasure;

public class AuditMeasure extends _AuditMeasure {

	   /** Read-only access to PK */
	   public Long getPK() {
		   return DataObjectUtils.longPKForObject(this);
	   }
}
