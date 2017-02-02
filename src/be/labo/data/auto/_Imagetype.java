package be.labo.data.auto;

import org.apache.cayenne.CayenneDataObject;

import be.labo.data.DataType;
import be.labo.data.Imagemap;

/**
 * Class _Imagetype was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Imagetype extends CayenneDataObject {

    public static final String DATATYPE_IMAGE_PROPERTY = "datatypeImage";
    public static final String IMAGE_TYPE_PROPERTY = "imageType";

    public static final String ID_PK_COLUMN = "id";

    public void setDatatypeImage(Imagemap datatypeImage) {
        setToOneTarget(DATATYPE_IMAGE_PROPERTY, datatypeImage, true);
    }

    public Imagemap getDatatypeImage() {
        return (Imagemap)readProperty(DATATYPE_IMAGE_PROPERTY);
    }


    public void setImageType(DataType imageType) {
        setToOneTarget(IMAGE_TYPE_PROPERTY, imageType, true);
    }

    public DataType getImageType() {
        return (DataType)readProperty(IMAGE_TYPE_PROPERTY);
    }


}
