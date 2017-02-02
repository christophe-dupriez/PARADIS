package be.labo.data;

import java.security.InvalidParameterException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.ObjectId;
import org.apache.cayenne.query.ObjectIdQuery;

import be.labo.auxil.CategoryInterval;
import be.labo.auxil.Spline;
import be.labo.data.auto._Scaling;

public class Scaling extends _Scaling implements NamedObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1327839749080369614L;

	private final static Logger LOGGER = Logger.getLogger(Scaling.class.getName());


	/** Read-only access to PK */
	public Integer getPK() {
		return DataObjectUtils.intPKForObject(this);
	}

	public Double scale (Double value) {
		if (value == null) return null;
		byte interpol = getInterpolation();
		if (interpol == 1) return value;
		else {
			List<ScalingLine> scaLines = this.getLine();
			if (scaLines == null || scaLines.isEmpty()) return value; // No scaling lines = leave value as is
			if (interpol == 3) { // Monotonic Cubic Interpolation
				Integer scalingId = this.getPK();
				Spline aSpline = Spline.scalingSpline.get(scalingId);
				if (aSpline == null) synchronized(this) {
					aSpline = Spline.scalingSpline.get(scalingId);
					if (aSpline == null) {
						// Sort X in increasing order
						TreeMap<Double,Double> sortX = new TreeMap<Double,Double>();
						for (ScalingLine aLine : scaLines) {
							sortX.put(aLine.getRawmin(),aLine.getMin());
							sortX.put(aLine.getRawmax(),aLine.getMax());
						}

						// Y = f(X)
						double[] x = new double[sortX.size()];
						double[] y = new double[sortX.size()];
						int i = 0;
						for (Map.Entry<Double, Double> e : sortX.entrySet()) {
							x[i] = e.getKey();
							y[i] = e.getValue();
							i++ ;
						}
						// Cache the result
						try {
							aSpline = Spline.createMonotoneCubicSpline(x, y);
							Spline.scalingSpline.put(scalingId, aSpline);
						}
						catch (IllegalArgumentException iae) {
							LOGGER.severe(this.toString()+":"+iae.toString()+" Scale#"+scalingId+", mappings="+sortX.toString());
						}
					}
				}
				return aSpline.interpolate(value);
			} else {
				for (ScalingLine aLine : scaLines) {
					if (value >= aLine.getRawmin() && value < aLine.getRawmax()) {
						switch (interpol) {
						case 2: { // Linear Interpolation (FILTERING!)
							return aLine.getMin()
									+( (aLine.getMax()-aLine.getMin())
											* (value - aLine.getRawmin())/(aLine.getRawmax()-aLine.getRawmin()) );
						}
						default: { // 0 or other values: Discrete, stored value is the Min
							return aLine.getMin();					   
						}
						}
					}
				}
			}
		}
		return null; // Scaling not found: out of bound value: NULLIFIED !
	}

	public static Scaling findById(ObjectContext context, String id) {
		try {
			Integer idNum = Integer.valueOf(id);
			ObjectId idScaling = new ObjectId("Scaling", Scaling.ID_PK_COLUMN, idNum);

			// this constructor implicitly uses "CACHE_REFRESH" policy, so a fresh object will be returned
			ObjectIdQuery query = new ObjectIdQuery(idScaling);

			Scaling object = (Scaling) DataObjectUtils.objectForQuery(context, query);
			return object;
		} catch (NumberFormatException ne) {
			return null;
		}
	}

	public void setName(String language, String name) {
		this.setDescription(name);
	}

	public LinkedHashMap<String,String> getNamesMap() {
		LinkedHashMap<String,String> result = new LinkedHashMap<String,String>();
		result.put ("",this.getDescription());
		return result;
	}

	public String getBestName(HttpServletRequest request) {
		return this.getDescription();
	}

	@Override
	public int compareTo(NamedObject arg0) {
		// TODO Auto-generated method stub
		if (arg0 == null) throw new NullPointerException();
		if (arg0.getClass() != Scaling.class) throw new InvalidParameterException();
		return this.getPK()-arg0.getPK();
	}

}