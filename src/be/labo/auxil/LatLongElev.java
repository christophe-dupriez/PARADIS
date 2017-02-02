package be.labo.auxil;

public class LatLongElev {
	double latitude = 0d;
	double longitude = 0d;
	double elevation = 0d;
	
	public LatLongElev(Double lati, Double longi, Double elev) {
		if (lati != null) latitude = lati;
		if (longi != null) longitude = longi;
		if (elev != null) elevation = elev;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the elevation
	 */
	public double getElevation() {
		return elevation;
	}
	/**
	 * @param elevation the elevation to set
	 */
	public void setElevation(double elevation) {
		this.elevation = elevation;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (latitude >= 0) {
			sb.append('+');
		}
		sb.append(latitude);
		if (longitude >= 0) {
			sb.append('+');
		}
		sb.append(longitude);
		if (elevation == 0d) {
			//sb.append('/');
		} else {
			if (elevation >= 0) {
				sb.append('+');
			}
			sb.append(elevation);
			//sb.append("CRSETRS89"); 
		}
		return sb.toString();
	}
}
