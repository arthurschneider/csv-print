package de.aschneider.csvprint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.aschneider.csvprint.document.Column;
import de.aschneider.csvprint.document.CsvBuilder;
import de.aschneider.csvprint.document.CsvPrinter;
import de.aschneider.csvprint.document.CsvPrinterFactory;
import de.aschneider.csvprint.formatter.bool.BooleanJaNeinFormatter;
import de.aschneider.csvprint.formatter.bool.BooleanOneZeroFormatter;
import de.aschneider.csvprint.formatter.bool.BooleanYesNoFormatter;

public class BooleanFormatterTest {

	List<String> header;
	List<Boolean> content;
	List<Column<Boolean>> functions;

	@BeforeEach
	public void setUpBeforeEachTest() throws Exception {
		header = Arrays.asList("Is full Aged");

		content = Arrays.asList(true, false);

		functions = new ArrayList<>();
	}

	@Test
	@DisplayName("Printer a BooleanYesNoFormatter should print for Booleans: true->yes and false->no.")
	public void testPrintBoolsPatternYesNo() throws Exception {
		functions.add(new Column<>(x -> x, new BooleanYesNoFormatter()));

		CsvPrinter printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));
		String actualContent = new String(printer.print());

		String expectedContent = "Is full Aged\n" //
				+ "yes\n" //
				+ "no";

		assertEquals(expectedContent, actualContent);
	}

	@Test
	@DisplayName("Printer a BooleanJaNeinFormatter should print for Booleans: true->ja and false->nein.")
	public void testPrintBoolsPatternJaNein() throws Exception {
		functions.add(new Column<>(x -> x, new BooleanJaNeinFormatter()));

		CsvPrinter printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));
		String actualContent = new String(printer.print());

		String expectedContent = "Is full Aged\n" //
				+ "ja\n" //
				+ "nein";

		assertEquals(expectedContent, actualContent);
	}

	@Test
	@DisplayName("Printer a BooleanOneZeroFormatter should print for Booleans: true->1 and false->0.")
	public void testPrintBoolsPatternOneZero() throws Exception {
		functions.add(new Column<>(x -> x, new BooleanOneZeroFormatter()));

		CsvPrinter printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));
		String actualContent = new String(printer.print());

		String expectedContent = "Is full Aged\n" //
				+ "1\n" //
				+ "0";

		assertEquals(expectedContent, actualContent);
	}

	@Test
	@DisplayName("Printer a without any Formatter should print for Booleans: true->true and false->false.")
	public void testPrintBoolsWithoutFormatter() throws Exception {
		functions.add(new Column<>(x -> x));

		CsvPrinter printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));
		String actualContent = new String(printer.print());

		String expectedContent = "Is full Aged\n" //
				+ "true\n" //
				+ "false";

		assertEquals(expectedContent, actualContent);
	}

}
