package be.labo.auxil;

public class WebColor {
	int tinyColor = 0;
	int color = 0;
	
	public static final WebColor black = new WebColor(0x000000);
	public static final WebColor gray = new WebColor(0x808080);
	public static final WebColor silver = new WebColor(0xC0C0C0);
	public static final WebColor white = new WebColor(0xFFFFFF);
	public static final WebColor maroon = new WebColor(0x800000);
	public static final WebColor red = new WebColor(0xFF0000);
	public static final WebColor olive = new WebColor(0x808000);
	public static final WebColor yellow = new WebColor(0xFFFF00);
	public static final WebColor green = new WebColor(0x008000);
	public static final WebColor lime = new WebColor(0x00FF00);
	public static final WebColor teal = new WebColor(0x008080);
	public static final WebColor aqua = new WebColor(0x00FFFF);
	public static final WebColor navy = new WebColor(0x000080);
	public static final WebColor blue = new WebColor(0x0000FF);
	public static final WebColor purple = new WebColor(0x800080);
	public static final WebColor fuchsia = new WebColor(0xFF00FF);

	public WebColor(int rgb) {
		setColor(rgb);
	}
	
	public WebColor(int rgb,boolean tiny) {
		if (tiny) {
			setTinyColor(rgb & 0xFF);
		} else {
			setColor(rgb);			
		}
	}
	
	public String toString() {
		return Integer.toHexString(color+0x1000000).substring(1);
	}

	/**
	 * @return the tinyColor
	 */
	public int getTinyColor() {
		return tinyColor;
	}

	/**
	 * @param tinyColor the tinyColor to set
	 */
	public void setTinyColor(int tinyColor) {
		this.tinyColor = tinyColor;
		String base6 = Integer.toString(tinyColor,6);
		char[] base666 = ("000" + base6).substring(base6.length(),base6.length()+3).toCharArray();
		this.color = ((base666[0]-'0')*0x330000)
				+((base666[1]-'0')*0x003300)
				+((base666[2]-'0')*0x000033);
	}

	/**
	 * @return the color
	 */
	public int getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(int color) {
		this.color = color & 0xFFFFFF;
		int red = (((color & 0xFF0000) >>> 16)+0x19) / 0x33;
		int green = (((color & 0x00FF00) >>> 8)+0x19) / 0x33;
		int blue = (((color & 0x0000FF))+0x19) / 0x33;
		this.tinyColor = (byte) ((red*36) + (green*6) + blue) & 0xFF; 
	}
}
