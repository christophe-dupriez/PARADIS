package be.labo.data.auto;

import be.labo.data.Category;
import be.labo.data.Names;

/**
 * Class _NameCategory was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _NameCategory extends Names {

    public static final String NAMECATEGORY_PROPERTY = "namecategory";

    public static final String ID_PK_COLUMN = "id";

    public void setNamecategory(Category namecategory) {
        setToOneTarget(NAMECATEGORY_PROPERTY, namecategory, true);
    }

    public Category getNamecategory() {
        return (Category)readProperty(NAMECATEGORY_PROPERTY);
    }


}
