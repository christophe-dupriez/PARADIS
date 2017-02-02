package be.labo.data.auto;

import java.util.Date;

import org.apache.cayenne.CayenneDataObject;

import be.labo.data.Scaling;
import be.labo.data.SubType;

/**
 * Class _ScalingSubType was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _ScalingSubType extends CayenneDataObject {

    public static final String SINCE_PROPERTY = "since";
    public static final String RAW_SCALING_PROPERTY = "rawScaling";
    public static final String RAW_SUBTYPE_PROPERTY = "rawSubtype";

    public static final String ID_PK_COLUMN = "id";

    public void setSince(Date since) {
        writeProperty(SINCE_PROPERTY, since);
    }
    public Date getSince() {
        return (Date)readProperty(SINCE_PROPERTY);
    }

    public void setRawScaling(Scaling rawScaling) {
        setToOneTarget(RAW_SCALING_PROPERTY, rawScaling, true);
    }

    public Scaling getRawScaling() {
        return (Scaling)readProperty(RAW_SCALING_PROPERTY);
    }


    public void setRawSubtype(SubType rawSubtype) {
        setToOneTarget(RAW_SUBTYPE_PROPERTY, rawSubtype, true);
    }

    public SubType getRawSubtype() {
        return (SubType)readProperty(RAW_SUBTYPE_PROPERTY);
    }


}
