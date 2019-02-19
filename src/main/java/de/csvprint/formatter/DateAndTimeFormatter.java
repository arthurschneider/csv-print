package de.csvprint.formatter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateAndTimeFormatter implements Formatter {

	private String pattern;

	public DateAndTimeFormatter(String pattern) {
		this.pattern = pattern;
	}

	@Override
	public String format(Object object) {
		if (object == null) {
			return "";
		}

		if (object instanceof Date) {
			return new SimpleDateFormat(pattern).format(object);
		} else if (object instanceof LocalDate) {
			return ((LocalDate) object).format(DateTimeFormatter.ofPattern(pattern));
		} else if (object instanceof LocalDateTime) {
			return ((LocalDateTime) object).format(DateTimeFormatter.ofPattern(pattern));
		}

		return object.toString();
	}

}
