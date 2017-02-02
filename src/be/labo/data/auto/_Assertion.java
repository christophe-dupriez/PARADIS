package be.labo.data.auto;

import java.util.List;

import org.apache.cayenne.CayenneDataObject;

import be.labo.data.DataType;
import be.labo.data.Event;
import be.labo.data.Place;

/**
 * Class _Assertion was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Assertion extends CayenneDataObject {

    public static final String ASSERTED_PROPERTY = "asserted";
    public static final String CATEGORYMASK_PROPERTY = "categorymask";
    public static final String CONDITION_EVENT_PROPERTY = "conditionEvent";
    public static final String CONDITION_PLACE_PROPERTY = "conditionPlace";
    public static final String CONDITION_TYPE_PROPERTY = "conditionType";

    public static final String ID_PK_COLUMN = "id";

    public void setAsserted(Byte asserted) {
        writeProperty(ASSERTED_PROPERTY, asserted);
    }
    public Byte getAsserted() {
        return (Byte)readProperty(ASSERTED_PROPERTY);
    }

    public void setCategorymask(Integer categorymask) {
        writeProperty(CATEGORYMASK_PROPERTY, categorymask);
    }
    public Integer getCategorymask() {
        return (Integer)readProperty(CATEGORYMASK_PROPERTY);
    }

    public void setConditionEvent(Event conditionEvent) {
        setToOneTarget(CONDITION_EVENT_PROPERTY, conditionEvent, true);
    }

    public Event getConditionEvent() {
        return (Event)readProperty(CONDITION_EVENT_PROPERTY);
    }


    public void addToConditionPlace(Place obj) {
        addToManyTarget(CONDITION_PLACE_PROPERTY, obj, true);
    }
    public void removeFromConditionPlace(Place obj) {
        removeToManyTarget(CONDITION_PLACE_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Place> getConditionPlace() {
        return (List<Place>)readProperty(CONDITION_PLACE_PROPERTY);
    }


    public void setConditionType(DataType conditionType) {
        setToOneTarget(CONDITION_TYPE_PROPERTY, conditionType, true);
    }

    public DataType getConditionType() {
        return (DataType)readProperty(CONDITION_TYPE_PROPERTY);
    }


}
