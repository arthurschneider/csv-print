package de.aschneider.csvprint.formatter.datetime;

import java.text.SimpleDateFormat;

import de.aschneider.csvprint.formatter.Formatter;

public class DateFormatter implements Formatter {

	private final String pattern;

	public DateFormatter(String pattern) {
		this.pattern = pattern;
	}

	@Override
	public String format(Object object) {
		return new SimpleDateFormat(pattern).format(object);
	}
}
