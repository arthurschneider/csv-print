package de.csvprint.formatter.number;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import de.csvprint.formatter.Formatter;

public class FloatFormatter implements Formatter {

	private static final String STANDARD_PATTERN = "#0.00";
	private final Locale locale;
	private final String pattern;

	public FloatFormatter() {
		this.locale = Locale.GERMANY;
		this.pattern = STANDARD_PATTERN;
	}

	public FloatFormatter(Locale locale) {
		this.locale = locale;
		this.pattern = STANDARD_PATTERN;
	}

	public FloatFormatter(String pattern) {
		this.locale = Locale.GERMANY;
		this.pattern = pattern;
	}

	public FloatFormatter(Locale locale, String pattern) {
		this.locale = locale;
		this.pattern = pattern;
	}

	@Override
	public String format(Object object) {
		NumberFormat formatter = new DecimalFormat(pattern, DecimalFormatSymbols.getInstance(locale));
		return formatter.format(object);
	}

}
