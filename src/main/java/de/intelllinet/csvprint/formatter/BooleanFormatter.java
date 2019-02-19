package de.intelllinet.csvprint.formatter;

import de.intellinet.utils.BoolPattern;

public class BooleanFormatter implements Formatter {

	private BoolPattern pattern;

	public BooleanFormatter(BoolPattern pattern) {
		this.pattern = pattern;
	}

	@Override
	public String format(Object object) {
		switch (pattern) {
		case BOOL_1_0:
			return (Boolean) object ? "1" : "0";
		case YES_NO:
			return (Boolean) object ? "yes" : "no";
		case TRUE_FALSE:
			return (Boolean) object ? "true" : "false";
		default:
			return (Boolean) object ? "true" : "false";
		}
	}

}
