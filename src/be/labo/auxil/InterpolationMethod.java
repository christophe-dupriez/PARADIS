/**
 * 
 */
package be.labo.auxil;


/**
 * @author Christophe
 *
 */
public enum InterpolationMethod {
	discrete, // MIN is the scaled value
	raw,      // No scaling at all
	linear,   // Linear interpolation
	mono3;     // Monotonic Cubic Interpolation

	public int getOrdinal() {
		return ordinal(); 
	}

}
