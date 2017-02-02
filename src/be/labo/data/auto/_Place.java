package be.labo.data.auto;

import java.util.List;

import org.apache.cayenne.CayenneDataObject;

import be.labo.data.Assertion;
import be.labo.data.Graphconfig;
import be.labo.data.Graphplace;
import be.labo.data.Imagemap;
import be.labo.data.Machine;
import be.labo.data.Measure;
import be.labo.data.NamePlace;
import be.labo.data.Placemap;
import be.labo.data.SubType;

/**
 * Class _Place was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Place extends CayenneDataObject {

    public static final String COLOR_PROPERTY = "color";
    public static final String ELEVATION_PROPERTY = "elevation";
    public static final String LATITUDE_PROPERTY = "latitude";
    public static final String LONGITUDE_PROPERTY = "longitude";
    public static final String SYMBOL_PROPERTY = "symbol";
    public static final String ASSERTIONS_PROPERTY = "assertions";
    public static final String GRAPHCONFIGS_PROPERTY = "graphconfigs";
    public static final String IMAGEMAPS_PROPERTY = "imagemaps";
    public static final String MACHINES_PROPERTY = "machines";
    public static final String PLACE_GRAPH_PROPERTY = "placeGraph";
    public static final String PLACE_MAP_PROPERTY = "placeMap";
    public static final String PLACE_MEASURE_PROPERTY = "placeMeasure";
    public static final String PLACEMACHINE_PROPERTY = "placemachine";
    public static final String PLACENAME_PROPERTY = "placename";
    public static final String PLACESUBTYPE_PROPERTY = "placesubtype";

    public static final String ID_PK_COLUMN = "id";

    public void setColor(Byte color) {
        writeProperty(COLOR_PROPERTY, color);
    }
    public Byte getColor() {
        return (Byte)readProperty(COLOR_PROPERTY);
    }

    public void setElevation(Double elevation) {
        writeProperty(ELEVATION_PROPERTY, elevation);
    }
    public Double getElevation() {
        return (Double)readProperty(ELEVATION_PROPERTY);
    }

    public void setLatitude(Double latitude) {
        writeProperty(LATITUDE_PROPERTY, latitude);
    }
    public Double getLatitude() {
        return (Double)readProperty(LATITUDE_PROPERTY);
    }

    public void setLongitude(Double longitude) {
        writeProperty(LONGITUDE_PROPERTY, longitude);
    }
    public Double getLongitude() {
        return (Double)readProperty(LONGITUDE_PROPERTY);
    }

    public void setSymbol(Byte symbol) {
        writeProperty(SYMBOL_PROPERTY, symbol);
    }
    public Byte getSymbol() {
        return (Byte)readProperty(SYMBOL_PROPERTY);
    }

    public void addToAssertions(Assertion obj) {
        addToManyTarget(ASSERTIONS_PROPERTY, obj, true);
    }
    public void removeFromAssertions(Assertion obj) {
        removeToManyTarget(ASSERTIONS_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Assertion> getAssertions() {
        return (List<Assertion>)readProperty(ASSERTIONS_PROPERTY);
    }


    public void addToGraphconfigs(Graphconfig obj) {
        addToManyTarget(GRAPHCONFIGS_PROPERTY, obj, true);
    }
    public void removeFromGraphconfigs(Graphconfig obj) {
        removeToManyTarget(GRAPHCONFIGS_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Graphconfig> getGraphconfigs() {
        return (List<Graphconfig>)readProperty(GRAPHCONFIGS_PROPERTY);
    }


    public void addToImagemaps(Imagemap obj) {
        addToManyTarget(IMAGEMAPS_PROPERTY, obj, true);
    }
    public void removeFromImagemaps(Imagemap obj) {
        removeToManyTarget(IMAGEMAPS_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Imagemap> getImagemaps() {
        return (List<Imagemap>)readProperty(IMAGEMAPS_PROPERTY);
    }


    public void addToMachines(Machine obj) {
        addToManyTarget(MACHINES_PROPERTY, obj, true);
    }
    public void removeFromMachines(Machine obj) {
        removeToManyTarget(MACHINES_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Machine> getMachines() {
        return (List<Machine>)readProperty(MACHINES_PROPERTY);
    }


    public void addToPlaceGraph(Graphplace obj) {
        addToManyTarget(PLACE_GRAPH_PROPERTY, obj, true);
    }
    public void removeFromPlaceGraph(Graphplace obj) {
        removeToManyTarget(PLACE_GRAPH_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Graphplace> getPlaceGraph() {
        return (List<Graphplace>)readProperty(PLACE_GRAPH_PROPERTY);
    }


    public void addToPlaceMap(Placemap obj) {
        addToManyTarget(PLACE_MAP_PROPERTY, obj, true);
    }
    public void removeFromPlaceMap(Placemap obj) {
        removeToManyTarget(PLACE_MAP_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Placemap> getPlaceMap() {
        return (List<Placemap>)readProperty(PLACE_MAP_PROPERTY);
    }


    public void addToPlaceMeasure(Measure obj) {
        addToManyTarget(PLACE_MEASURE_PROPERTY, obj, true);
    }
    public void removeFromPlaceMeasure(Measure obj) {
        removeToManyTarget(PLACE_MEASURE_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Measure> getPlaceMeasure() {
        return (List<Measure>)readProperty(PLACE_MEASURE_PROPERTY);
    }


    public void addToPlacemachine(Machine obj) {
        addToManyTarget(PLACEMACHINE_PROPERTY, obj, true);
    }
    public void removeFromPlacemachine(Machine obj) {
        removeToManyTarget(PLACEMACHINE_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Machine> getPlacemachine() {
        return (List<Machine>)readProperty(PLACEMACHINE_PROPERTY);
    }


    public void addToPlacename(NamePlace obj) {
        addToManyTarget(PLACENAME_PROPERTY, obj, true);
    }
    public void removeFromPlacename(NamePlace obj) {
        removeToManyTarget(PLACENAME_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<NamePlace> getPlacename() {
        return (List<NamePlace>)readProperty(PLACENAME_PROPERTY);
    }


    public void addToPlacesubtype(SubType obj) {
        addToManyTarget(PLACESUBTYPE_PROPERTY, obj, true);
    }
    public void removeFromPlacesubtype(SubType obj) {
        removeToManyTarget(PLACESUBTYPE_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<SubType> getPlacesubtype() {
        return (List<SubType>)readProperty(PLACESUBTYPE_PROPERTY);
    }


}
