package de.aschneider.csvprint.formatter.bool;

import static org.apache.commons.lang3.BooleanUtils.isTrue;

import de.aschneider.csvprint.formatter.Formatter;

public class BooleanJaNeinFormatter implements Formatter {

	@Override
	public String format(Object object) {
		return isTrue((Boolean) object) ? "ja" : "nein";
	}

}
