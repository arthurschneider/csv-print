package de.aschneider.csvprint.document;

import java.util.Optional;
import java.util.function.Function;

import de.aschneider.csvprint.formatter.Formatter;

public class Column<T> {

	private final String header;
	private final Function<T, Object> function;
	private final Optional<Formatter> formatter;

	public Column(String header, Function<T, Object> function) {
		this.header = header;
		this.function = function;
		this.formatter = Optional.empty();
	}

	public Column(String header, Function<T, Object> function, Formatter formatter) {
		this.header = header;
		this.function = function;
		this.formatter = Optional.of(formatter);
	}

	public boolean hasFormatter() {
		return formatter.isPresent();
	}
	
	public String format(Object content) {
		return formatter.get().format(content);
	}

	public String getHeader() {
		return header;
	}

	public Function<T, Object> getFunction() {
		return function;
	}

	public Formatter getFormatter() {
		return formatter.get();
	}

}
