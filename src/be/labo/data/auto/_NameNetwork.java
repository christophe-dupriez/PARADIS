package be.labo.data.auto;

import be.labo.data.Names;
import be.labo.data.Network;

/**
 * Class _NameNetwork was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _NameNetwork extends Names {

    public static final String NAME_NETWORK_PROPERTY = "nameNetwork";

    public static final String ID_PK_COLUMN = "id";

    public void setNameNetwork(Network nameNetwork) {
        setToOneTarget(NAME_NETWORK_PROPERTY, nameNetwork, true);
    }

    public Network getNameNetwork() {
        return (Network)readProperty(NAME_NETWORK_PROPERTY);
    }


}
