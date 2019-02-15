package de.intelllinet.csvprint.util;

import java.util.function.Function;

import de.intelllinet.csvprint.formatter.Formatter;

public class ColumnElement<T> {

	private Function<T, Object> function;
	private Formatter formatter;

	public ColumnElement(Function<T, Object> function, Formatter formatter) {
		super();
		this.function = function;
		this.formatter = formatter;
	}

	public Function<T, Object> getFunction() {
		return function;
	}

	public void setFunction(Function<T, Object> function) {
		this.function = function;
	}

	public Formatter getFormatter() {
		return formatter;
	}

	public void setFormatter(Formatter formatter) {
		this.formatter = formatter;
	}

}
