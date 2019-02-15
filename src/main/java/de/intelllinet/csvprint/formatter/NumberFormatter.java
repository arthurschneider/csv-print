package de.intelllinet.csvprint.formatter;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatter implements Formatter {

	Locale locale = Locale.GERMANY;

	public NumberFormatter() {
	}

	public NumberFormatter(Locale locale) {
		super();
		this.locale = locale;
	}

	@Override
	public String format(Object object) {
		return NumberFormat.getInstance(locale).format(object);
	}

}
