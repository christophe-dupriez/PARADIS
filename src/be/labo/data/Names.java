package be.labo.data;

import java.util.Locale;

import sun.org.mozilla.javascript.internal.Context;
import be.labo.data.auto._Names;

public class Names extends _Names {
	
	public static Locale[] supportedLocales = {
			new Locale("nl"),
		    Locale.FRENCH,
		    Locale.GERMAN,
		    Locale.ENGLISH
		};
}
