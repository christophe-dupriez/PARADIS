package be.labo.data.auto;

import java.util.List;

import org.apache.cayenne.CayenneDataObject;

import be.labo.data.Imagetype;
import be.labo.data.NameImage;
import be.labo.data.Place;
import be.labo.data.Placemap;

/**
 * Class _Imagemap was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Imagemap extends CayenneDataObject {

    public static final String XMAX_PROPERTY = "xmax";
    public static final String YMAX_PROPERTY = "ymax";
    public static final String IMAGE_TYPE_PROPERTY = "imageType";
    public static final String IMAGENAME_PROPERTY = "imagename";
    public static final String MAP_PLACE_PROPERTY = "mapPlace";
    public static final String PLACES_PROPERTY = "places";

    public static final String ID_PK_COLUMN = "id";

    public void setXmax(Double xmax) {
        writeProperty(XMAX_PROPERTY, xmax);
    }
    public Double getXmax() {
        return (Double)readProperty(XMAX_PROPERTY);
    }

    public void setYmax(Double ymax) {
        writeProperty(YMAX_PROPERTY, ymax);
    }
    public Double getYmax() {
        return (Double)readProperty(YMAX_PROPERTY);
    }

    public void addToImageType(Imagetype obj) {
        addToManyTarget(IMAGE_TYPE_PROPERTY, obj, true);
    }
    public void removeFromImageType(Imagetype obj) {
        removeToManyTarget(IMAGE_TYPE_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Imagetype> getImageType() {
        return (List<Imagetype>)readProperty(IMAGE_TYPE_PROPERTY);
    }


    public void addToImagename(NameImage obj) {
        addToManyTarget(IMAGENAME_PROPERTY, obj, true);
    }
    public void removeFromImagename(NameImage obj) {
        removeToManyTarget(IMAGENAME_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<NameImage> getImagename() {
        return (List<NameImage>)readProperty(IMAGENAME_PROPERTY);
    }


    public void addToMapPlace(Placemap obj) {
        addToManyTarget(MAP_PLACE_PROPERTY, obj, true);
    }
    public void removeFromMapPlace(Placemap obj) {
        removeToManyTarget(MAP_PLACE_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Placemap> getMapPlace() {
        return (List<Placemap>)readProperty(MAP_PLACE_PROPERTY);
    }


    public void addToPlaces(Place obj) {
        addToManyTarget(PLACES_PROPERTY, obj, true);
    }
    public void removeFromPlaces(Place obj) {
        removeToManyTarget(PLACES_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Place> getPlaces() {
        return (List<Place>)readProperty(PLACES_PROPERTY);
    }


}
