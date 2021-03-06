package be.labo.data.auto;

import java.util.List;

import org.apache.cayenne.CayenneDataObject;

import be.labo.data.Assertion;
import be.labo.data.AuditType;
import be.labo.data.Category;
import be.labo.data.Event;
import be.labo.data.GraphType;
import be.labo.data.Graphconfig;
import be.labo.data.Imagetype;
import be.labo.data.Machine;
import be.labo.data.Measure;
import be.labo.data.NameType;
import be.labo.data.Place;
import be.labo.data.RawDataFields;
import be.labo.data.Scaling;
import be.labo.data.SubType;

/**
 * Class _DataType was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _DataType extends CayenneDataObject {

    public static final String ACTIVE_PROPERTY = "active";
    public static final String DECIMALS_PROPERTY = "decimals";
    public static final String DEFAULTTIMESPAN_PROPERTY = "defaulttimespan";
    public static final String DESCRIPTION_PROPERTY = "description";
    public static final String INHEADER_PROPERTY = "inheader";
    public static final String ISALETTER_PROPERTY = "isaletter";
    public static final String MAX_PROPERTY = "max";
    public static final String MIN_PROPERTY = "min";
    public static final String MIN1_PROPERTY = "min1";
    public static final String MIN2_PROPERTY = "min2";
    public static final String MIN3_PROPERTY = "min3";
    public static final String MIN4_PROPERTY = "min4";
    public static final String MIN5_PROPERTY = "min5";
    public static final String MIN6_PROPERTY = "min6";
    public static final String MIN7_PROPERTY = "min7";
    public static final String MIN8_PROPERTY = "min8";
    public static final String MIN9_PROPERTY = "min9";
    public static final String NBCATEGORIES_PROPERTY = "nbcategories";
    public static final String PRESENTATION_PROPERTY = "presentation";
    public static final String SYMBOL_PROPERTY = "symbol";
    public static final String UNIT_PROPERTY = "unit";
    public static final String CATEG0_PROPERTY = "categ0";
    public static final String CATEG1_PROPERTY = "categ1";
    public static final String CATEG2_PROPERTY = "categ2";
    public static final String CATEG3_PROPERTY = "categ3";
    public static final String CATEG4_PROPERTY = "categ4";
    public static final String CATEG5_PROPERTY = "categ5";
    public static final String CATEG6_PROPERTY = "categ6";
    public static final String CATEG7_PROPERTY = "categ7";
    public static final String CATEG8_PROPERTY = "categ8";
    public static final String CATEG9_PROPERTY = "categ9";
    public static final String DATA_TYPE_FIELD_PROPERTY = "dataTypeField";
    public static final String DATATYPE_IMAGE_PROPERTY = "datatypeImage";
    public static final String EVENTS_PROPERTY = "events";
    public static final String GRAPHCONFIGS_PROPERTY = "graphconfigs";
    public static final String MACHINES_PROPERTY = "machines";
    public static final String PLACES_PROPERTY = "places";
    public static final String SCALINGS_PROPERTY = "scalings";
    public static final String SINGLE_BIT_MACHINES_PROPERTY = "singleBitMachines";
    public static final String SUBTYPE_PROPERTY = "subtype";
    public static final String TYPE_AUDIT_PROPERTY = "typeAudit";
    public static final String TYPE_CONDITION_PROPERTY = "typeCondition";
    public static final String TYPE_GRAPH_PROPERTY = "typeGraph";
    public static final String TYPE_MEASURES_PROPERTY = "typeMeasures";
    public static final String TYPE_NAME_PROPERTY = "typeName";

    public static final String ID_PK_COLUMN = "id";

    public void setActive(Boolean active) {
        writeProperty(ACTIVE_PROPERTY, active);
    }
    public Boolean getActive() {
        return (Boolean)readProperty(ACTIVE_PROPERTY);
    }

    public void setDecimals(Byte decimals) {
        writeProperty(DECIMALS_PROPERTY, decimals);
    }
    public Byte getDecimals() {
        return (Byte)readProperty(DECIMALS_PROPERTY);
    }

    public void setDefaulttimespan(Integer defaulttimespan) {
        writeProperty(DEFAULTTIMESPAN_PROPERTY, defaulttimespan);
    }
    public Integer getDefaulttimespan() {
        return (Integer)readProperty(DEFAULTTIMESPAN_PROPERTY);
    }

    public void setDescription(String description) {
        writeProperty(DESCRIPTION_PROPERTY, description);
    }
    public String getDescription() {
        return (String)readProperty(DESCRIPTION_PROPERTY);
    }

    public void setInheader(Byte inheader) {
        writeProperty(INHEADER_PROPERTY, inheader);
    }
    public Byte getInheader() {
        return (Byte)readProperty(INHEADER_PROPERTY);
    }

    public void setIsaletter(String isaletter) {
        writeProperty(ISALETTER_PROPERTY, isaletter);
    }
    public String getIsaletter() {
        return (String)readProperty(ISALETTER_PROPERTY);
    }

    public void setMax(Double max) {
        writeProperty(MAX_PROPERTY, max);
    }
    public Double getMax() {
        return (Double)readProperty(MAX_PROPERTY);
    }

    public void setMin(Double min) {
        writeProperty(MIN_PROPERTY, min);
    }
    public Double getMin() {
        return (Double)readProperty(MIN_PROPERTY);
    }

    public void setMin1(Double min1) {
        writeProperty(MIN1_PROPERTY, min1);
    }
    public Double getMin1() {
        return (Double)readProperty(MIN1_PROPERTY);
    }

    public void setMin2(Double min2) {
        writeProperty(MIN2_PROPERTY, min2);
    }
    public Double getMin2() {
        return (Double)readProperty(MIN2_PROPERTY);
    }

    public void setMin3(Double min3) {
        writeProperty(MIN3_PROPERTY, min3);
    }
    public Double getMin3() {
        return (Double)readProperty(MIN3_PROPERTY);
    }

    public void setMin4(Double min4) {
        writeProperty(MIN4_PROPERTY, min4);
    }
    public Double getMin4() {
        return (Double)readProperty(MIN4_PROPERTY);
    }

    public void setMin5(Double min5) {
        writeProperty(MIN5_PROPERTY, min5);
    }
    public Double getMin5() {
        return (Double)readProperty(MIN5_PROPERTY);
    }

    public void setMin6(Double min6) {
        writeProperty(MIN6_PROPERTY, min6);
    }
    public Double getMin6() {
        return (Double)readProperty(MIN6_PROPERTY);
    }

    public void setMin7(Double min7) {
        writeProperty(MIN7_PROPERTY, min7);
    }
    public Double getMin7() {
        return (Double)readProperty(MIN7_PROPERTY);
    }

    public void setMin8(Double min8) {
        writeProperty(MIN8_PROPERTY, min8);
    }
    public Double getMin8() {
        return (Double)readProperty(MIN8_PROPERTY);
    }

    public void setMin9(Double min9) {
        writeProperty(MIN9_PROPERTY, min9);
    }
    public Double getMin9() {
        return (Double)readProperty(MIN9_PROPERTY);
    }

    public void setNbcategories(Byte nbcategories) {
        writeProperty(NBCATEGORIES_PROPERTY, nbcategories);
    }
    public Byte getNbcategories() {
        return (Byte)readProperty(NBCATEGORIES_PROPERTY);
    }

    public void setPresentation(Byte presentation) {
        writeProperty(PRESENTATION_PROPERTY, presentation);
    }
    public Byte getPresentation() {
        return (Byte)readProperty(PRESENTATION_PROPERTY);
    }

    public void setSymbol(Byte symbol) {
        writeProperty(SYMBOL_PROPERTY, symbol);
    }
    public Byte getSymbol() {
        return (Byte)readProperty(SYMBOL_PROPERTY);
    }

    public void setUnit(String unit) {
        writeProperty(UNIT_PROPERTY, unit);
    }
    public String getUnit() {
        return (String)readProperty(UNIT_PROPERTY);
    }

    public void setCateg0(Category categ0) {
        setToOneTarget(CATEG0_PROPERTY, categ0, true);
    }

    public Category getCateg0() {
        return (Category)readProperty(CATEG0_PROPERTY);
    }


    public void setCateg1(Category categ1) {
        setToOneTarget(CATEG1_PROPERTY, categ1, true);
    }

    public Category getCateg1() {
        return (Category)readProperty(CATEG1_PROPERTY);
    }


    public void setCateg2(Category categ2) {
        setToOneTarget(CATEG2_PROPERTY, categ2, true);
    }

    public Category getCateg2() {
        return (Category)readProperty(CATEG2_PROPERTY);
    }


    public void setCateg3(Category categ3) {
        setToOneTarget(CATEG3_PROPERTY, categ3, true);
    }

    public Category getCateg3() {
        return (Category)readProperty(CATEG3_PROPERTY);
    }


    public void setCateg4(Category categ4) {
        setToOneTarget(CATEG4_PROPERTY, categ4, true);
    }

    public Category getCateg4() {
        return (Category)readProperty(CATEG4_PROPERTY);
    }


    public void setCateg5(Category categ5) {
        setToOneTarget(CATEG5_PROPERTY, categ5, true);
    }

    public Category getCateg5() {
        return (Category)readProperty(CATEG5_PROPERTY);
    }


    public void setCateg6(Category categ6) {
        setToOneTarget(CATEG6_PROPERTY, categ6, true);
    }

    public Category getCateg6() {
        return (Category)readProperty(CATEG6_PROPERTY);
    }


    public void setCateg7(Category categ7) {
        setToOneTarget(CATEG7_PROPERTY, categ7, true);
    }

    public Category getCateg7() {
        return (Category)readProperty(CATEG7_PROPERTY);
    }


    public void setCateg8(Category categ8) {
        setToOneTarget(CATEG8_PROPERTY, categ8, true);
    }

    public Category getCateg8() {
        return (Category)readProperty(CATEG8_PROPERTY);
    }


    public void setCateg9(Category categ9) {
        setToOneTarget(CATEG9_PROPERTY, categ9, true);
    }

    public Category getCateg9() {
        return (Category)readProperty(CATEG9_PROPERTY);
    }


    public void addToDataTypeField(RawDataFields obj) {
        addToManyTarget(DATA_TYPE_FIELD_PROPERTY, obj, true);
    }
    public void removeFromDataTypeField(RawDataFields obj) {
        removeToManyTarget(DATA_TYPE_FIELD_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<RawDataFields> getDataTypeField() {
        return (List<RawDataFields>)readProperty(DATA_TYPE_FIELD_PROPERTY);
    }


    public void addToDatatypeImage(Imagetype obj) {
        addToManyTarget(DATATYPE_IMAGE_PROPERTY, obj, true);
    }
    public void removeFromDatatypeImage(Imagetype obj) {
        removeToManyTarget(DATATYPE_IMAGE_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Imagetype> getDatatypeImage() {
        return (List<Imagetype>)readProperty(DATATYPE_IMAGE_PROPERTY);
    }


    public void addToEvents(Event obj) {
        addToManyTarget(EVENTS_PROPERTY, obj, true);
    }
    public void removeFromEvents(Event obj) {
        removeToManyTarget(EVENTS_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Event> getEvents() {
        return (List<Event>)readProperty(EVENTS_PROPERTY);
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


    @SuppressWarnings("unchecked")
    public List<Scaling> getScalings() {
        return (List<Scaling>)readProperty(SCALINGS_PROPERTY);
    }


    @SuppressWarnings("unchecked")
    public List<Machine> getSingleBitMachines() {
        return (List<Machine>)readProperty(SINGLE_BIT_MACHINES_PROPERTY);
    }


    public void addToSubtype(SubType obj) {
        addToManyTarget(SUBTYPE_PROPERTY, obj, true);
    }
    public void removeFromSubtype(SubType obj) {
        removeToManyTarget(SUBTYPE_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<SubType> getSubtype() {
        return (List<SubType>)readProperty(SUBTYPE_PROPERTY);
    }


    public void addToTypeAudit(AuditType obj) {
        addToManyTarget(TYPE_AUDIT_PROPERTY, obj, true);
    }
    public void removeFromTypeAudit(AuditType obj) {
        removeToManyTarget(TYPE_AUDIT_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<AuditType> getTypeAudit() {
        return (List<AuditType>)readProperty(TYPE_AUDIT_PROPERTY);
    }


    public void addToTypeCondition(Assertion obj) {
        addToManyTarget(TYPE_CONDITION_PROPERTY, obj, true);
    }
    public void removeFromTypeCondition(Assertion obj) {
        removeToManyTarget(TYPE_CONDITION_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Assertion> getTypeCondition() {
        return (List<Assertion>)readProperty(TYPE_CONDITION_PROPERTY);
    }


    public void addToTypeGraph(GraphType obj) {
        addToManyTarget(TYPE_GRAPH_PROPERTY, obj, true);
    }
    public void removeFromTypeGraph(GraphType obj) {
        removeToManyTarget(TYPE_GRAPH_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<GraphType> getTypeGraph() {
        return (List<GraphType>)readProperty(TYPE_GRAPH_PROPERTY);
    }


    public void addToTypeMeasures(Measure obj) {
        addToManyTarget(TYPE_MEASURES_PROPERTY, obj, true);
    }
    public void removeFromTypeMeasures(Measure obj) {
        removeToManyTarget(TYPE_MEASURES_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Measure> getTypeMeasures() {
        return (List<Measure>)readProperty(TYPE_MEASURES_PROPERTY);
    }


    public void addToTypeName(NameType obj) {
        addToManyTarget(TYPE_NAME_PROPERTY, obj, true);
    }
    public void removeFromTypeName(NameType obj) {
        removeToManyTarget(TYPE_NAME_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<NameType> getTypeName() {
        return (List<NameType>)readProperty(TYPE_NAME_PROPERTY);
    }


}
