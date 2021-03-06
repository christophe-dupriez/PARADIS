package be.labo.data.auto;

import org.apache.cayenne.CayenneDataObject;

import be.labo.data.Audit;
import be.labo.data.DataType;

/**
 * Class _AuditType was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _AuditType extends CayenneDataObject {

    public static final String AUDIT_TYPE_PROPERTY = "auditType";
    public static final String TYPEAUDIT_PROPERTY = "typeaudit";

    public static final String ID_PK_COLUMN = "id";

    public void setAuditType(DataType auditType) {
        setToOneTarget(AUDIT_TYPE_PROPERTY, auditType, true);
    }

    public DataType getAuditType() {
        return (DataType)readProperty(AUDIT_TYPE_PROPERTY);
    }


    public void setTypeaudit(Audit typeaudit) {
        setToOneTarget(TYPEAUDIT_PROPERTY, typeaudit, true);
    }

    public Audit getTypeaudit() {
        return (Audit)readProperty(TYPEAUDIT_PROPERTY);
    }


}
