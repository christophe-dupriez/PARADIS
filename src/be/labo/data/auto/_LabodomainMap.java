package be.labo.data.auto;

import java.util.List;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.NamedQuery;

import be.labo.data.Measure;

/**
 * This class was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public class _LabodomainMap {

    public static final String LAST_ALERTS_QUERYNAME = "LastAlerts";

    public static final String LAST_MEASURES_QUERYNAME = "lastMeasures";

    public static final String MEASURE_FOR_MACHINE_DATE_QUERYNAME = "measureForMachineDate";

    public List<Measure> performLastMeasures(ObjectContext context ) {
        return context.performQuery(new NamedQuery("lastMeasures"));
    }
}