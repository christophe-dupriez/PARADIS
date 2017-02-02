package be.labo.data.auto;

import org.apache.cayenne.CayenneDataObject;

import be.labo.data.Audit;
import be.labo.data.User;

/**
 * Class _AuditUser was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _AuditUser extends CayenneDataObject {

    public static final String AUDITUSER_PROPERTY = "audituser";
    public static final String USERAUDIT_PROPERTY = "useraudit";

    public static final String ID_PK_COLUMN = "id";

    public void setAudituser(User audituser) {
        setToOneTarget(AUDITUSER_PROPERTY, audituser, true);
    }

    public User getAudituser() {
        return (User)readProperty(AUDITUSER_PROPERTY);
    }


    public void setUseraudit(Audit useraudit) {
        setToOneTarget(USERAUDIT_PROPERTY, useraudit, true);
    }

    public Audit getUseraudit() {
        return (Audit)readProperty(USERAUDIT_PROPERTY);
    }


}
