package be.labo.util;

import java.util.Calendar;
import java.util.Date;

public class RTCvalue extends Date {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	byte validity = 0;
	
	@SuppressWarnings("deprecation")
	public void setInt(int data) {
		this.setTime((long)0); // ensures 0 millis

		// Java does not know about unsigned int!
		int datePart = (int)((data >> 1) / (30*60*24));
		this.setDate((datePart % 31)+1) ;
		datePart = datePart / 31;
		this.setMonth(datePart % 12);
		this.setYear((datePart / 12)+100);
		
		datePart = data % (60*60*24);
		if (datePart < 0) datePart += 60*60*24;
		this.setSeconds(datePart % 60);
		datePart = datePart / 60;
		this.setMinutes(datePart % 60);
		this.setHours(datePart / 60);
	}
	
	public void setValidity(byte val) {
		validity = val;
	}
	
	public byte getValidity() {
		return validity;
	}
	
	@SuppressWarnings("deprecation")
	public int getInt() {
		if (this.getYear()<2000) return 0;
		return this.getSeconds()
				+(this.getMinutes()*60)
				+(this.getHours()*3600)
				+(this.getDate()*3600*24)
				+(this.getMonth()*3600*24*31)
				+((this.getYear()-2000)*3600*24*31*12);
	}
}
