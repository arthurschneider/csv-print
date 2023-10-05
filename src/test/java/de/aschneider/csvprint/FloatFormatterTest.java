package de.aschneider.csvprint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.aschneider.csvprint.document.Column;
import de.aschneider.csvprint.document.CsvBuilder;
import de.aschneider.csvprint.document.CsvPrinterFactory;
import de.aschneider.csvprint.document.Printer;
import de.aschneider.csvprint.formatter.number.FloatFormatter;

class FloatFormatterTest {

	List<Double> content;
	List<Column<Double>> functions;

	@BeforeEach
	void setUpBeforeEachTest() throws Exception {
		content = Arrays.asList(12.0, 0.7, 69_000.0);

		functions = new ArrayList<>();
	}

	@Test
	@DisplayName("Printer without a defalut FloatFormatter should print floating numbers in German notation.")
	void testPrintStandardFloat() throws Exception {
		functions.add(new Column<>("Sales 2019", x -> x, new FloatFormatter()));

		Printer printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(content, functions));
		String actualContent = new String(printer.print());

		String expectedContent = """
				Sales 2019
				12,00
				0,70
				69000,00""";

		assertEquals(expectedContent, actualContent);
	}

	@Test
	@DisplayName("Printer without a us locale FloatFormatter should print floating numbers in US notation.")
	void testPrintFloatWithCustomLocale() throws Exception {
		functions.add(new Column<>("Sales 2019", x -> x, new FloatFormatter(Locale.US)));

		Printer printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(content, functions));
		String actualContent = new String(printer.print());

		String expectedContent = """
				Sales 2019
				12.00
				0.70
				69000.00""";

		assertEquals(expectedContent, actualContent);
	}

	@Test
	@DisplayName("Printer without a custom pattern FloatFormatter should print floating numbers in specified format.")
	void testPrintFloatWithCustomPattern() throws Exception {
		functions.add(new Column<>("Sales 2019", x -> x, new FloatFormatter("#0.000")));

		Printer printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(content, functions));
		String actualContent = new String(printer.print());

		String expectedContent = """
				Sales 2019
				12,000
				0,700
				69000,000""";

		assertEquals(expectedContent, actualContent);
	}

	@Test
	@DisplayName("Printer without a custom pattern and a us locale FloatFormatter should print numbers as specified.")
	void testPrintFloatWithCustomLocaleAndPattern() throws Exception {
		functions.add(new Column<>("Sales 2019", x -> x, new FloatFormatter(Locale.US, "#0.000")));

		Printer printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(content, functions));
		String actualContent = new String(printer.print());

		String expectedContent = """
				Sales 2019
				12.000
				0.700
				69000.000""";

		assertEquals(expectedContent, actualContent);
	}

	@Test
	@DisplayName("Printer without a custom pattern and a us locale FloatFormatter should print numbers as specified.")
	void testPrintFloatWithCustomLocaleAndPatternLongerDecimal() throws Exception {
		content = List.of(12.34567, .7, 69_000.0);

		functions.add(new Column<>("Sales 2019", x -> x, new FloatFormatter(Locale.US, "#0.000")));

		Printer printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(content, functions));
		String actualContent = new String(printer.print());

		String expectedContent = """
				Sales 2019
				12.346
				0.700
				69000.000""";

		assertEquals(expectedContent, actualContent);
	}

}
