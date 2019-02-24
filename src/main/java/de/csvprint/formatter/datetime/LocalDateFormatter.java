package de.csvprint.formatter.datetime;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import de.csvprint.formatter.Formatter;

public class LocalDateFormatter implements Formatter {

	private String pattern;

	public LocalDateFormatter(String pattern) {
		this.pattern = pattern;
	}

	@Override
	public String format(Object object) {
		return ((LocalDate) object).format(DateTimeFormatter.ofPattern(pattern));
	}

}
