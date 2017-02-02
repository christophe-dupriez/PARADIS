package be.labo.data;

import java.util.Date;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectContext;

import be.labo.data.auto._NameGroup;

public class NameGroup extends _NameGroup {

	   /** Read-only access to PK */
	   public Integer getPK() {
		   return DataObjectUtils.intPKForObject(this);
	   }
}
