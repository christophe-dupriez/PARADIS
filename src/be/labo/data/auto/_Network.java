package be.labo.data.auto;

import java.util.List;

import org.apache.cayenne.CayenneDataObject;

import be.labo.data.AuditNetwork;
import be.labo.data.Machine;
import be.labo.data.NameNetwork;

/**
 * Class _Network was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Network extends CayenneDataObject {

    public static final String BASETEMPO_PROPERTY = "basetempo";
    public static final String BEATGSM_PROPERTY = "beatgsm";
    public static final String BEATMEASURE_PROPERTY = "beatmeasure";
    public static final String SLEEPTIME_PROPERTY = "sleeptime";
    public static final String TIMESLICE_PROPERTY = "timeslice";
    public static final String WARMUP_PROPERTY = "warmup";
    public static final String NETWORK_MACHINE_PROPERTY = "networkMachine";
    public static final String NETWORK_NAME_PROPERTY = "networkName";
    public static final String NETWORKAUDIT_PROPERTY = "networkaudit";

    public static final String ID_PK_COLUMN = "id";

    public void setBasetempo(Byte basetempo) {
        writeProperty(BASETEMPO_PROPERTY, basetempo);
    }
    public Byte getBasetempo() {
        return (Byte)readProperty(BASETEMPO_PROPERTY);
    }

    public void setBeatgsm(Short beatgsm) {
        writeProperty(BEATGSM_PROPERTY, beatgsm);
    }
    public Short getBeatgsm() {
        return (Short)readProperty(BEATGSM_PROPERTY);
    }

    public void setBeatmeasure(Short beatmeasure) {
        writeProperty(BEATMEASURE_PROPERTY, beatmeasure);
    }
    public Short getBeatmeasure() {
        return (Short)readProperty(BEATMEASURE_PROPERTY);
    }

    public void setSleeptime(Integer sleeptime) {
        writeProperty(SLEEPTIME_PROPERTY, sleeptime);
    }
    public Integer getSleeptime() {
        return (Integer)readProperty(SLEEPTIME_PROPERTY);
    }

    public void setTimeslice(Short timeslice) {
        writeProperty(TIMESLICE_PROPERTY, timeslice);
    }
    public Short getTimeslice() {
        return (Short)readProperty(TIMESLICE_PROPERTY);
    }

    public void setWarmup(Short warmup) {
        writeProperty(WARMUP_PROPERTY, warmup);
    }
    public Short getWarmup() {
        return (Short)readProperty(WARMUP_PROPERTY);
    }

    public void addToNetworkMachine(Machine obj) {
        addToManyTarget(NETWORK_MACHINE_PROPERTY, obj, true);
    }
    public void removeFromNetworkMachine(Machine obj) {
        removeToManyTarget(NETWORK_MACHINE_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Machine> getNetworkMachine() {
        return (List<Machine>)readProperty(NETWORK_MACHINE_PROPERTY);
    }


    public void addToNetworkName(NameNetwork obj) {
        addToManyTarget(NETWORK_NAME_PROPERTY, obj, true);
    }
    public void removeFromNetworkName(NameNetwork obj) {
        removeToManyTarget(NETWORK_NAME_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<NameNetwork> getNetworkName() {
        return (List<NameNetwork>)readProperty(NETWORK_NAME_PROPERTY);
    }


    public void addToNetworkaudit(AuditNetwork obj) {
        addToManyTarget(NETWORKAUDIT_PROPERTY, obj, true);
    }
    public void removeFromNetworkaudit(AuditNetwork obj) {
        removeToManyTarget(NETWORKAUDIT_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<AuditNetwork> getNetworkaudit() {
        return (List<AuditNetwork>)readProperty(NETWORKAUDIT_PROPERTY);
    }


}
