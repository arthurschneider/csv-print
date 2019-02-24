package de.csvprint.formatter.bool;

import de.csvprint.formatter.Formatter;

public class BooleanYesNoFormatter implements Formatter {

	@Override
	public String format(Object object) {
		return (Boolean) object ? "yes" : "no";
	}

}
