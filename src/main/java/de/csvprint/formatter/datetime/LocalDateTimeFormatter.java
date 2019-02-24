package de.csvprint.formatter.datetime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import de.csvprint.formatter.Formatter;

public class LocalDateTimeFormatter implements Formatter {

	private String pattern;

	public LocalDateTimeFormatter(String pattern) {
		this.pattern = pattern;
	}

	@Override
	public String format(Object object) {
		return ((LocalDateTime) object).format(DateTimeFormatter.ofPattern(pattern));
	}
}
