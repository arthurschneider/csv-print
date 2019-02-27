package de.intelllinet.csvprint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.csvprint.document.CsvBuilder;
import de.csvprint.document.CsvPrinterFactory;
import de.csvprint.document.Column;
import de.csvprint.document.CsvPrinter;
import de.csvprint.formatter.bool.BooleanJaNeinFormatter;
import de.csvprint.formatter.bool.BooleanOneZeroFormatter;
import de.csvprint.formatter.bool.BooleanYesNoFormatter;

public class BooleanFormatterTest {

	@Test
	public void testPrintBoolsPatternYesNo() throws Exception {
		List<String> header = new ArrayList<>();
		header.add("Is full Aged");

		List<Boolean> content = new ArrayList<>();
		content.add(true);
		content.add(false);

		List<Column<Boolean>> functions = new ArrayList<>();
		functions.add(new Column<>(x -> x, new BooleanYesNoFormatter()));

		CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));
		String actualContent = new String(dokument.print());

		String expectedContent = "Is full Aged\n" //
				+ "yes\n" //
				+ "no";

		assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testPrintBoolsPatternJaNein() throws Exception {
		List<String> header = new ArrayList<>();
		header.add("Is full Aged");

		List<Boolean> content = new ArrayList<>();
		content.add(true);
		content.add(false);

		List<Column<Boolean>> functions = new ArrayList<>();
		functions.add(new Column<>(x -> x, new BooleanJaNeinFormatter()));

		CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));
		String actualContent = new String(dokument.print());

		String expectedContent = "Is full Aged\n" //
				+ "ja\n" //
				+ "nein";

		assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testPrintBoolsPatternOneZero() throws Exception {
		List<String> header = new ArrayList<>();
		header.add("Is full Aged");

		List<Boolean> content = new ArrayList<>();
		content.add(true);
		content.add(false);

		List<Column<Boolean>> functions = new ArrayList<>();
		functions.add(new Column<>(x -> x, new BooleanOneZeroFormatter()));

		CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));
		String actualContent = new String(dokument.print());

		String expectedContent = "Is full Aged\n" //
				+ "1\n" //
				+ "0";

		assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testPrintBoolsWithoutFormatter() throws Exception {
		List<String> header = new ArrayList<>();
		header.add("Is full Aged");

		List<Boolean> content = new ArrayList<>();
		content.add(true);
		content.add(false);

		List<Column<Boolean>> functions = new ArrayList<>();
		functions.add(new Column<>(x -> x));

		CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));
		String actualContent = new String(dokument.print());

		String expectedContent = "Is full Aged\n" //
				+ "true\n" //
				+ "false";

		assertEquals(expectedContent, actualContent);
	}
}
