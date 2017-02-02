package be.labo.data.auto;

import org.apache.cayenne.CayenneDataObject;

import be.labo.data.Imagemap;
import be.labo.data.Place;

/**
 * Class _Placemap was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Placemap extends CayenneDataObject {

    public static final String XPOS_PROPERTY = "xpos";
    public static final String YPOS_PROPERTY = "ypos";
    public static final String MAPPLACE_PROPERTY = "mapplace";
    public static final String PLACEMAP_PROPERTY = "placemap";

    public static final String ID_PK_COLUMN = "id";

    public void setXpos(Double xpos) {
        writeProperty(XPOS_PROPERTY, xpos);
    }
    public Double getXpos() {
        return (Double)readProperty(XPOS_PROPERTY);
    }

    public void setYpos(Double ypos) {
        writeProperty(YPOS_PROPERTY, ypos);
    }
    public Double getYpos() {
        return (Double)readProperty(YPOS_PROPERTY);
    }

    public void setMapplace(Place mapplace) {
        setToOneTarget(MAPPLACE_PROPERTY, mapplace, true);
    }

    public Place getMapplace() {
        return (Place)readProperty(MAPPLACE_PROPERTY);
    }


    public void setPlacemap(Imagemap placemap) {
        setToOneTarget(PLACEMAP_PROPERTY, placemap, true);
    }

    public Imagemap getPlacemap() {
        return (Imagemap)readProperty(PLACEMAP_PROPERTY);
    }


}