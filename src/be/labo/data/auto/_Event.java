package be.labo.data.auto;

import java.util.Date;
import java.util.List;

import org.apache.cayenne.CayenneDataObject;

import be.labo.data.Alert;
import be.labo.data.Assertion;
import be.labo.data.AuditEvent;
import be.labo.data.DataType;
import be.labo.data.GroupOfUsers;
import be.labo.data.NameEvent;
import be.labo.data.Place;

/**
 * Class _Event was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Event extends CayenneDataObject {

    public static final String ACTIVE_PROPERTY = "active";
    public static final String HYSTERESIS_PROPERTY = "hysteresis";
    public static final String PRIORITY_PROPERTY = "priority";
    public static final String SINCE_PROPERTY = "since";
    public static final String UPTO_PROPERTY = "upto";
    public static final String DATATYPES_PROPERTY = "datatypes";
    public static final String EVENT_ALERT_PROPERTY = "eventAlert";
    public static final String EVENT_CONDITION_PROPERTY = "eventCondition";
    public static final String EVENT_GROUP_PROPERTY = "eventGroup";
    public static final String EVENT_NAME_PROPERTY = "eventName";
    public static final String EVENTAUDIT_PROPERTY = "eventaudit";
    public static final String PLACES_PROPERTY = "places";

    public static final String ID_PK_COLUMN = "id";

    public void setActive(Boolean active) {
        writeProperty(ACTIVE_PROPERTY, active);
    }
    public Boolean getActive() {
        return (Boolean)readProperty(ACTIVE_PROPERTY);
    }

    public void setHysteresis(Short hysteresis) {
        writeProperty(HYSTERESIS_PROPERTY, hysteresis);
    }
    public Short getHysteresis() {
        return (Short)readProperty(HYSTERESIS_PROPERTY);
    }

    public void setPriority(Byte priority) {
        writeProperty(PRIORITY_PROPERTY, priority);
    }
    public Byte getPriority() {
        return (Byte)readProperty(PRIORITY_PROPERTY);
    }

    public void setSince(Date since) {
        writeProperty(SINCE_PROPERTY, since);
    }
    public Date getSince() {
        return (Date)readProperty(SINCE_PROPERTY);
    }

    public void setUpto(Date upto) {
        writeProperty(UPTO_PROPERTY, upto);
    }
    public Date getUpto() {
        return (Date)readProperty(UPTO_PROPERTY);
    }

    public void addToDatatypes(DataType obj) {
        addToManyTarget(DATATYPES_PROPERTY, obj, true);
    }
    public void removeFromDatatypes(DataType obj) {
        removeToManyTarget(DATATYPES_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<DataType> getDatatypes() {
        return (List<DataType>)readProperty(DATATYPES_PROPERTY);
    }


    public void addToEventAlert(Alert obj) {
        addToManyTarget(EVENT_ALERT_PROPERTY, obj, true);
    }
    public void removeFromEventAlert(Alert obj) {
        removeToManyTarget(EVENT_ALERT_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Alert> getEventAlert() {
        return (List<Alert>)readProperty(EVENT_ALERT_PROPERTY);
    }


    public void addToEventCondition(Assertion obj) {
        addToManyTarget(EVENT_CONDITION_PROPERTY, obj, true);
    }
    public void removeFromEventCondition(Assertion obj) {
        removeToManyTarget(EVENT_CONDITION_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Assertion> getEventCondition() {
        return (List<Assertion>)readProperty(EVENT_CONDITION_PROPERTY);
    }


    public void setEventGroup(GroupOfUsers eventGroup) {
        setToOneTarget(EVENT_GROUP_PROPERTY, eventGroup, true);
    }

    public GroupOfUsers getEventGroup() {
        return (GroupOfUsers)readProperty(EVENT_GROUP_PROPERTY);
    }


    public void addToEventName(NameEvent obj) {
        addToManyTarget(EVENT_NAME_PROPERTY, obj, true);
    }
    public void removeFromEventName(NameEvent obj) {
        removeToManyTarget(EVENT_NAME_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<NameEvent> getEventName() {
        return (List<NameEvent>)readProperty(EVENT_NAME_PROPERTY);
    }


    public void addToEventaudit(AuditEvent obj) {
        addToManyTarget(EVENTAUDIT_PROPERTY, obj, true);
    }
    public void removeFromEventaudit(AuditEvent obj) {
        removeToManyTarget(EVENTAUDIT_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<AuditEvent> getEventaudit() {
        return (List<AuditEvent>)readProperty(EVENTAUDIT_PROPERTY);
    }


    @SuppressWarnings("unchecked")
    public List<Place> getPlaces() {
        return (List<Place>)readProperty(PLACES_PROPERTY);
    }


}
