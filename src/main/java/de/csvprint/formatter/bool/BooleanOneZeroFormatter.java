package de.csvprint.formatter.bool;

import de.csvprint.formatter.Formatter;

public class BooleanOneZeroFormatter implements Formatter {

	@Override
	public String format(Object object) {
		return (Boolean) object ? "1" : "0";
	}

}
