package de.aschneider.csvprint.document;

import java.util.function.Function;

import de.aschneider.csvprint.formatter.Formatter;

public class Column<T> {

	private final Function<T, Object> function;
	private final Formatter formatter;

	public Column(Function<T, Object> function) {
		this.function = function;
		this.formatter = null;
	}

	public Column(Function<T, Object> function, Formatter formatter) {
		this.function = function;
		this.formatter = formatter;
	}

	public boolean hasFormatter() {
		return formatter != null;
	}

	public Function<T, Object> getFunction() {
		return function;
	}

	public Formatter getFormatter() {
		return formatter;
	}

}
