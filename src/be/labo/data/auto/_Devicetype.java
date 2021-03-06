package be.labo.data.auto;

import java.util.List;

import org.apache.cayenne.CayenneDataObject;

import be.labo.data.Machine;

/**
 * Class _Devicetype was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Devicetype extends CayenneDataObject {

    public static final String DESCRIPTION_PROPERTY = "description";
    public static final String DEVICETYPE_PROPERTY = "devicetype";
    public static final String DEVICE_TYPE_MACHINE_PROPERTY = "deviceTypeMachine";

    public static final String ID_PK_COLUMN = "id";

    public void setDescription(String description) {
        writeProperty(DESCRIPTION_PROPERTY, description);
    }
    public String getDescription() {
        return (String)readProperty(DESCRIPTION_PROPERTY);
    }

    public void setDevicetype(Short devicetype) {
        writeProperty(DEVICETYPE_PROPERTY, devicetype);
    }
    public Short getDevicetype() {
        return (Short)readProperty(DEVICETYPE_PROPERTY);
    }

    public void addToDeviceTypeMachine(Machine obj) {
        addToManyTarget(DEVICE_TYPE_MACHINE_PROPERTY, obj, true);
    }
    public void removeFromDeviceTypeMachine(Machine obj) {
        removeToManyTarget(DEVICE_TYPE_MACHINE_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Machine> getDeviceTypeMachine() {
        return (List<Machine>)readProperty(DEVICE_TYPE_MACHINE_PROPERTY);
    }


}
