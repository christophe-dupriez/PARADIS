package be.labo.data;

import org.apache.cayenne.DataObjectUtils;

import be.labo.data.auto._ScalingLine;

public class ScalingLine extends _ScalingLine {

	   /** Read-only access to PK */
	   public Integer getPK() {
		   return DataObjectUtils.intPKForObject(this);
	   }
}
