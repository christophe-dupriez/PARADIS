package be.labo.data;

import org.apache.cayenne.DataObjectUtils;

import be.labo.data.auto._ScalingSubType;

public class ScalingSubType extends _ScalingSubType {

	   /** Read-only access to PK */
	   public Integer getPK() {
		   return DataObjectUtils.intPKForObject(this);
	   }
}
