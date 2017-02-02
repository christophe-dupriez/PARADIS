package be.labo.data;

import be.labo.data.auto._LabodomainMap;

public class LabodomainMap extends _LabodomainMap {

    private static LabodomainMap instance;

    private LabodomainMap() {}

    public static LabodomainMap getInstance() {
        if(instance == null) {
            instance = new LabodomainMap();
        }

        return instance;
    }
}
