package be.labo.auxil;

public class XBeeAddress implements Comparable<XBeeAddress> {
	int msb = 0;
	int lsb = 0;
	
	public static final int COMMON_MSB = 0x0013A200;
	
	public XBeeAddress(int amsb, int alsb) {
		msb = amsb;
		if (msb == 0) msb = COMMON_MSB;
		lsb = alsb;
	}

	/**
	 * @return the msb
	 */
	public int getMsb() {
		return msb;
	}
	/**
	 * @param msb the msb to set
	 */
	public void setMsb(int msb) {
		this.msb = msb;
		if (this.msb == 0) this.msb = COMMON_MSB;
	}
	/**
	 * @return the lsb
	 */
	public int getLsb() {
		return lsb;
	}
	/**
	 * @param lsb the lsb to set
	 */
	public void setLsb(int lsb) {
		this.lsb = lsb;
	}

	public String toString() {
		return Integer.toHexString(msb)+"-"+Integer.toHexString(lsb);
	}

	@Override
	public int compareTo(XBeeAddress arg0) {
		// if (!(arg0 instanceof XBeeAddress)) throw new IllegalArgumentException();
		XBeeAddress xba = (XBeeAddress) arg0;
		if (this.msb == xba.getMsb())
		{
			return this.lsb-xba.getLsb();
		}
		return this.msb-xba.getMsb();
	}
	
	
}
