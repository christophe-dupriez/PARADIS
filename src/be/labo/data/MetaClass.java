package be.labo.data;

import org.apache.cayenne.DataObjectUtils;

import be.labo.data.auto._MetaClass;

public class MetaClass extends _MetaClass {
	
	/** Read-only access to PK */
	public Integer getPK() {
		return DataObjectUtils.intPKForObject(this);
	}
}
