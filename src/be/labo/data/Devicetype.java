package be.labo.data;

import java.util.List;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;

import be.labo.data.auto._Devicetype;

public class Devicetype extends _Devicetype {
	   /** Read-only access to PK */
	   public Integer getPK() {
		   return DataObjectUtils.intPKForObject(this);
	   }

		public static Devicetype findByCode (ObjectContext context, String code, boolean createIfMissing) {
			try {
				if (code == null || code.isEmpty()) return null;
				
				Short typeNum = Short.parseShort(code);
				SelectQuery queryDeviceTypeid = new SelectQuery(Devicetype.class,ExpressionFactory.matchExp(Devicetype.DEVICETYPE_PROPERTY, typeNum));
				List<Devicetype> deviceTypes = context.performQuery(queryDeviceTypeid);
				if (deviceTypes == null || deviceTypes.isEmpty()) {
					if (createIfMissing) {
						Devicetype deviceType = (Devicetype)context.newObject(Devicetype.class);
						deviceType.setDevicetype(typeNum);
						context.commitChanges();
						return deviceType;
					} else {
						return null;
					}
				} else {
					return deviceTypes.get(0);
				}
			} catch (NumberFormatException ne) {
				return null;
			}
		}
		
}
