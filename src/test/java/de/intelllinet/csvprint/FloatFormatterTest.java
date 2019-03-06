package de.intelllinet.csvprint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.csvprint.document.Column;
import de.csvprint.document.CsvBuilder;
import de.csvprint.document.CsvPrinter;
import de.csvprint.document.CsvPrinterFactory;
import de.csvprint.formatter.number.FloatFormatter;

public class FloatFormatterTest {

	private static List<String> header;
	private static List<Double> content;
	private static List<Column<Double>> functions;

	@BeforeEach
	public void setUpBeforeEachTest() throws Exception {
		header = Arrays.asList("Sales 2019");

		content = Arrays.asList(12.0, 0.7, 69000.0);

		functions = new ArrayList<>();
	}

	@Test
	@DisplayName("Printer without a defalut FloatFormatter should print floating numbers in German notation")
	public void testPrintStandardFloat() throws Exception {
		functions = new ArrayList<>();
		functions.add(new Column<>(x -> x, new FloatFormatter()));

		CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));
		String actualContent = new String(dokument.print());

		String expectedContent = "Sales 2019\n" //
				+ "12,00\n" //
				+ "0,70\n" //
				+ "69000,00";

		assertEquals(expectedContent, actualContent);
	}

	@Test
	@DisplayName("Printer without a us locale FloatFormatter should print floating numbers in US notation")
	public void testPrintFloatWithCustomLocale() throws Exception {
		functions = new ArrayList<>();
		functions.add(new Column<>(x -> x, new FloatFormatter(Locale.US)));

		CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));
		String actualContent = new String(dokument.print());

		String expectedContent = "Sales 2019\n" //
				+ "12.00\n" //
				+ "0.70\n" //
				+ "69000.00";

		assertEquals(expectedContent, actualContent);
	}

	@Test
	@DisplayName("Printer without a custom pattern FloatFormatter should print floating numbers in specified format")
	public void testPrintFloatWithCustomPattern() throws Exception {
		functions = new ArrayList<>();
		functions.add(new Column<>(x -> x, new FloatFormatter("#0.000")));

		CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));
		String actualContent = new String(dokument.print());

		String expectedContent = "Sales 2019\n" //
				+ "12,000\n" //
				+ "0,700\n" //
				+ "69000,000";

		assertEquals(expectedContent, actualContent);
	}

	@Test
	@DisplayName("Printer without a custom pattern and a us locale FloatFormatter should print numbers as specified")
	public void testPrintFloatWithCustomLocaleAndPattern() throws Exception {
		functions = new ArrayList<>();
		functions.add(new Column<>(x -> x, new FloatFormatter(Locale.US, "#0.000")));

		CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));
		String actualContent = new String(dokument.print());

		String expectedContent = "Sales 2019\n" //
				+ "12.000\n" //
				+ "0.700\n" //
				+ "69000.000";

		assertEquals(expectedContent, actualContent);
	}

	@Test
	@DisplayName("Printer without a custom pattern and a us locale FloatFormatter should print numbers as specified")
	public void testPrintFloatWithCustomLocaleAndPatternLongerDecimal() throws Exception {
		content = Arrays.asList(12.34567, .7, 69000.0);

		functions = new ArrayList<>();
		functions.add(new Column<>(x -> x, new FloatFormatter(Locale.US, "#0.000")));

		CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));
		String actualContent = new String(dokument.print());

		String expectedContent = "Sales 2019\n" //
				+ "12.346\n" //
				+ "0.700\n" //
				+ "69000.000";

		assertEquals(expectedContent, actualContent);
	}

}
