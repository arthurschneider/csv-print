package de.intelllinet.csvprint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import de.csvprint.document.CsvBuilder;
import de.csvprint.document.CsvPrinterFactory;
import de.csvprint.document.Column;
import de.csvprint.document.CsvPrinter;
import de.csvprint.formatter.number.FloatFormatter;

public class FloatFormatterTest {

	@Test
	public void testPrintStandardFloat() throws Exception {
		List<String> header = new ArrayList<>();
		header.add("Sales 2019");

		List<Double> contentList = new ArrayList<>();
		contentList.add(12.0);
		contentList.add(0.7);
		contentList.add(12000.0);

		List<Column<Double>> functions = new ArrayList<>();
		functions.add(new Column<>(x -> x, new FloatFormatter()));

		CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, contentList, functions));
		String content = new String(dokument.print());

		String expectedContent = "Sales 2019\n" //
				+ "12,00\n" //
				+ "0,70\n" //
				+ "12000,00";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintFloatWithCustomLocale() throws Exception {
		List<String> header = new ArrayList<>();
		header.add("Sales 2019");

		List<Double> contentList = new ArrayList<>();
		contentList.add(12.0);
		contentList.add(0.7);
		contentList.add(12000.0);

		List<Column<Double>> functions = new ArrayList<>();
		functions.add(new Column<>(x -> x, new FloatFormatter(Locale.US)));

		CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, contentList, functions));
		String content = new String(dokument.print());

		String expectedContent = "Sales 2019\n" //
				+ "12.00\n" //
				+ "0.70\n" //
				+ "12000.00";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintFloatWithCustomPattern() throws Exception {
		List<String> header = new ArrayList<>();
		header.add("Sales 2019");

		List<Double> contentList = new ArrayList<>();
		contentList.add(12.0);
		contentList.add(0.7);
		contentList.add(12000.0);

		List<Column<Double>> functions = new ArrayList<>();
		functions.add(new Column<>(x -> x, new FloatFormatter("#0.000")));

		CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, contentList, functions));
		String content = new String(dokument.print());

		String expectedContent = "Sales 2019\n" //
				+ "12,000\n" //
				+ "0,700\n" //
				+ "12000,000";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintFloatWithCustomLocaleAndPattern() throws Exception {
		List<String> header = new ArrayList<>();
		header.add("Sales 2019");

		List<Double> contentList = new ArrayList<>();
		contentList.add(12.0);
		contentList.add(0.7);
		contentList.add(12000.0);

		List<Column<Double>> functions = new ArrayList<>();
		functions.add(new Column<>(x -> x, new FloatFormatter(Locale.US, "#0.000")));

		CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, contentList, functions));
		String content = new String(dokument.print());

		String expectedContent = "Sales 2019\n" //
				+ "12.000\n" //
				+ "0.700\n" //
				+ "12000.000";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintFloatWithCustomLocaleAndPatternLongerDecimal() throws Exception {
		List<String> header = new ArrayList<>();
		header.add("Sales 2019");

		List<Double> contentList = new ArrayList<>();
		contentList.add(12.34567);
		contentList.add(0.7);
		contentList.add(12000.0);

		List<Column<Double>> functions = new ArrayList<>();
		functions.add(new Column<>(x -> x, new FloatFormatter(Locale.US, "#0.000")));

		CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, contentList, functions));
		String content = new String(dokument.print());

		String expectedContent = "Sales 2019\n" //
				+ "12.346\n" //
				+ "0.700\n" //
				+ "12000.000";

		assertEquals(expectedContent, content);
	}
}
