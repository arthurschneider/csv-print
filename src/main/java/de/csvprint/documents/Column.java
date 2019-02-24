package de.csvprint.documents;

import java.util.function.Function;

import de.csvprint.formatter.Formatter;

public class Column<T> {

	private Function<T, Object> function;
	private Formatter formatter;

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
