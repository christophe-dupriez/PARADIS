package be.labo.auxil;

import java.util.concurrent.ConcurrentHashMap;

import be.labo.data.Category;
import be.labo.util.StaticUtil;

public class CategoryInterval {
	
	public static ConcurrentHashMap<Integer,CategoryInterval[]> typeCategories = new ConcurrentHashMap<Integer,CategoryInterval[]>();
	public static ConcurrentHashMap<Integer,CategoryInterval[]> subTypeCategories = new ConcurrentHashMap<Integer,CategoryInterval[]>();

	public double min = Long.MIN_VALUE;
	public double max = Long.MAX_VALUE;
	Category label;
	public double minMin = Long.MIN_VALUE; // Full range minimum
	public double maxMax = Long.MAX_VALUE; // Full range maximum

	public CategoryInterval(Category newCateg, Double newMin, Double newMax) {
		this.label = newCateg;
		this.min = (newMin==null? Long.MIN_VALUE : newMin);
		this.max = (newMax==null? Long.MAX_VALUE : newMax);
	}

	public CategoryInterval(Category newCateg, Double newMin, Double newMax, CategoryInterval fullRange) {
		this.label = newCateg;
		this.min = (newMin==null? Long.MIN_VALUE : newMin);
		this.max = (newMax==null? Long.MAX_VALUE : newMax);
		this.minMin = fullRange.getMin();
		this.maxMax = fullRange.getMax();
	}

	public CategoryInterval(CategoryInterval base, Double newMin, Double newMax) {
		this.label = base.label;
		if (newMin != null) this.min = newMin;
		else this.min = base.min;
		if (newMax != null) this.max = newMax;
		else this.max = base.max;
	}

	public CategoryInterval(CategoryInterval base, Double newMin, Double newMax, CategoryInterval fullRange) {
		this.label = base.label;
		if (newMin != null) this.min = newMin;
		else this.min = base.min;
		if (newMax != null) this.max = newMax;
		else this.max = base.max;
		this.minMin = fullRange.getMin();
		this.maxMax = fullRange.getMax();
	}

	public static byte find(CategoryInterval[] categs, double value) 
	{
		if (categs == null) return -1;
		if (categs.length == 0) return -1;
		for (byte i=0; i < categs.length; i++) {
			if (value >= categs[i].min && value < categs[i].max) return i;
		}
		return -1;
	}

	public static CategoryInterval findInterval(CategoryInterval[] categs, double value) 
	{
		if (categs == null) return null;
		if (categs.length == 0) return null;
		for (byte i=0; i < categs.length; i++) {
			if (value >= categs[i].min && value < categs[i].max) return categs[i];
		}
		return null;
	}

	public static CategoryInterval get(CategoryInterval[] categs, byte aCateg) 
	{
		if (categs == null) return null;
		if (aCateg < 0) return null;
		if (categs.length <= aCateg) return null;
		return categs[aCateg];
	}

	/**
	 * @return the min
	 */
	public double getMin() {
		return min;
	}

	public String getMinString() {
		return StaticUtil.niceDouble(min);
	}

	/**
	 * @param min the min to set
	 */
	public void setMin(double min) {
		this.min = min;
	}

	/**
	 * @return the max
	 */
	public double getMax() {
		return max;
	}
	public String getMaxString() {
		return StaticUtil.niceDouble(max);
	}


	/**
	 * @param max the max to set
	 */
	public void setMax(double max) {
		this.max = max;
	}

	/**
	 * @return the color
	 */
	public int getTinyColor() {
		if (label == null) return 0;
		return label.getWebColor().getTinyColor();
	}

	/**
	 * @return the color (32bits)
	 */
	public int getColor() {
		if (label == null) return 0;
		return label.getColor();
	}

	/**
	 * @return the color (32bits)
	 */
	public WebColor getWebColor() {
		if (label == null) return WebColor.black;
		return label.getWebColor();
	}

	/**
	 * @return the color (32bits)
	 */
	public String getColorHex() {
		if (label == null) return "";
		return label.getColorHex();
	}

	public void setLabel(Category aLabel) {
		this.label = aLabel;
	}

	/**
	 * @return the label
	 */
	public Category getLabel() {
		return label;
	}
	
	public String getTableWidthPercent() {
		long result = Math.round(((max-min)*100.0d) / (maxMax-minMin));
		if (result < 0 || result > 100) {
			return "";
		} else {
			return " width=\""+result+"%\"";
		}
	}

	/**
	 * @return the minMin
	 */
	public double getMinMin() {
		return minMin;
	}

	/**
	 * @param minMin the minMin to set
	 */
	public void setMinMin(double minMin) {
		this.minMin = minMin;
	}

	/**
	 * @return the maxMax
	 */
	public double getMaxMax() {
		return maxMax;
	}

	/**
	 * @param maxMax the maxMax to set
	 */
	public void setMaxMax(double maxMax) {
		this.maxMax = maxMax;
	}
}
