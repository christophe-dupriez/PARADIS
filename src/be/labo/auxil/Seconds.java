package be.labo.auxil;

import java.util.StringTokenizer;

public class Seconds {

	public static final Seconds MinValue = new Seconds(Integer.MIN_VALUE);
	public static final Seconds MaxValue = new Seconds(Integer.MAX_VALUE);
	public static final int SecondsPerMinute = 60;
	public static final int SecondsPerHour = 3600;
	public static final int SecondsPerDay = 86400;
	public static final Seconds Zero = new Seconds(0);

	private int ticks;

	public int getTicks() {
		return ticks;
	}

	public Seconds(int ticks) {
		this.ticks = ticks;
	}

	public int getHours() {
		return (ticks / SecondsPerHour) % 24;
	}

	public int getMinutes() {
		return (ticks / SecondsPerMinute) % 60;
	}

	public int getSeconds() {
		return ticks % 60;
	}

	public int getDays() {
		return ticks / SecondsPerDay;
	}

	public Seconds(int hours, int minutes, int seconds) {
		this(0, hours, minutes, seconds);
	}

	public Seconds(int days, int hours, int minutes, int seconds) {
		this.ticks = days * SecondsPerDay + hours * SecondsPerHour + minutes * SecondsPerMinute + seconds;
	}

	public static Seconds Add(Seconds t1, Seconds t2)
	{
		return new Seconds(t1.ticks+t2.ticks);
	}

	public Seconds Add(Seconds t1)
	{
		return new Seconds(this.ticks + t1.ticks);
	}

	@Override
	public boolean equals(Object other){
		if(other == null) return false;
		if(other == this) return true;
		if(this.getClass() != other.getClass()) return false;
		Seconds otherClass = (Seconds) other;
		return (ticks==otherClass.getTicks());
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		int days = this.getDays();
		if(days>=1 || days<=-1)
			str.append(String.format("%02d.", days));

		str.append(String.format("%02d:", this.getHours()));
		str.append(String.format("%02d:", this.getMinutes()));
		str.append(String.format("%02d", this.getSeconds()));

		return str.toString();
	}

	/*
	 * Parses a timespan in the format days.hours:minutes:seconds
	 * where days are optionals or can be alone:
	 * days
	 * days.
	 * .hours
	 * hours:(minutes(:seconds))
	 */
	public static Seconds parse(String aString) {
		if (aString == null) return null;
		aString = aString.trim();
		if (aString.isEmpty()) return null;
		StringTokenizer st = new StringTokenizer(aString, ".:", true);
		if (!st.hasMoreElements()) return null;
		String token = st.nextToken();
		char delim = token.charAt(0);
		int day = 0;
		if (delim != '.' && delim != ':') {
			try {
				day = Integer.parseInt(token);
			} catch (Exception e) {
				return null;
			}
			if (!st.hasMoreElements()) return new Seconds(day,0,0,0);
			token = st.nextToken();
		}
		int hour = 0;
		delim = token.charAt(0);
		if (delim == '.') {
			if (!st.hasMoreElements()) return new Seconds(day,0,0,0);
			token = st.nextToken();
			try {
				hour = Integer.parseInt(token);
			} catch (Exception e) {
				return null;
			}
			if (!st.hasMoreElements()) return new Seconds(day,hour,0,0);
			token = st.nextToken();
			if (token.charAt(0) == ':') token = st.nextToken();
		} else if (delim == ':') {
			hour = day;
			if (!st.hasMoreElements()) return new Seconds(0,hour,0,0);
			token = st.nextToken();
			day = 0;
		}
		int minute = 0;
		try {
			minute = Integer.parseInt(token);
		} catch (Exception e) {
			return null;
		}
		if (!st.hasMoreElements()) return new Seconds(day,hour,minute,0);
		token = st.nextToken();
		if (token.charAt(0) == ':') token = st.nextToken();
		int second = 0;
		try {
			second = Integer.parseInt(token);
		} catch (Exception e) {
			return null;
		}
		return new Seconds(day,hour,minute,second);
	}

}
