/**
 * 
 */
package be.labo.auxil;

/**
 * @author Christophe
 *
 */
public enum FlotSymbol {
	circle(9679,9675),   //0 &#9679; &#9675;
	square(9632,9633),   //1 &#9632; &#9633;
	diamond(9670,9671),  //2 &#9670; &#9671;
	triangle(9650,9651), //3 &#9650; &#9651;
	cross(10006,10006);    //4 &#10006; &#10006;
	
	public int[] unicode;
	
	FlotSymbol(int black, int white) {
		unicode = new int[2];
		unicode[0] = black;
		unicode[1] = white;
	}
	
	public String getBlackEntity() {
		return "&#"+Integer.toString(unicode[0])+';';
	}

	public String getWhiteEntity() {
		return "&#"+Integer.toString(unicode[1])+';';
	}

	public int getOrdinal() {
		return ordinal(); 
	}
}
