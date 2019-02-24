package de.csvprint.formatter.datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import de.csvprint.formatter.Formatter;

public class DateAndTimeFormatter implements Formatter {

	private String pattern;

	public DateAndTimeFormatter(String pattern) {
		this.pattern = pattern;
	}

	@Override
	public String format(Object object) {

		if (object instanceof Date) {
			return new DateFormatter(pattern).format(object);
		} else if (object instanceof LocalDate) {
			return new LocalDateFormatter(pattern).format(object);
		} else if (object instanceof LocalDateTime) {
			return new LocalDateTimeFormatter(pattern).format(object);
		}
		return object.toString();
	}

}
