package de.csvprint.formatter.datetime;

import java.text.SimpleDateFormat;

import de.csvprint.formatter.Formatter;

public class DateFormatter implements Formatter {

	private String pattern;

	public DateFormatter(String pattern) {
		this.pattern = pattern;
	}

	@Override
	public String format(Object object) {
		return new SimpleDateFormat(pattern).format(object);
	}
}