package be.labo.data.auto;

import org.apache.cayenne.CayenneDataObject;

import be.labo.data.Audit;
import be.labo.data.Machine;

/**
 * Class _AuditMachine was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _AuditMachine extends CayenneDataObject {

    public static final String AUDIT_MACHINE_PROPERTY = "auditMachine";
    public static final String MACHINEAUDIT_PROPERTY = "machineaudit";

    public static final String ID_PK_COLUMN = "id";

    public void setAuditMachine(Machine auditMachine) {
        setToOneTarget(AUDIT_MACHINE_PROPERTY, auditMachine, true);
    }

    public Machine getAuditMachine() {
        return (Machine)readProperty(AUDIT_MACHINE_PROPERTY);
    }


    public void setMachineaudit(Audit machineaudit) {
        setToOneTarget(MACHINEAUDIT_PROPERTY, machineaudit, true);
    }

    public Audit getMachineaudit() {
        return (Audit)readProperty(MACHINEAUDIT_PROPERTY);
    }


}
