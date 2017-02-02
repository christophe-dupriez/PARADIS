package be.labo.util;

public class StaticUtil {

	static public String niceDouble(double d) {
		if(d == (long) d)
			return String.format("%d",(long)d);
		else
			return String.format("%f",d);
	}
}
