package de.intelllinet.csvprint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import de.csvprint.documents.CSVDokument;
import de.csvprint.documents.ColumnElement;
import de.csvprint.documents.FormattedCSVDokument;
import de.csvprint.formatter.FloatFormatter;

public class FormattedCSVFloatTest {

	@Test
	public void testPrintStandardFloat() throws Exception {
		List<String> header = new ArrayList<>();
		header.add("Sales 2019");

		List<Double> contentList = new ArrayList<>();
		contentList.add(12.0);
		contentList.add(0.7);
		contentList.add(12000.0);

		List<ColumnElement<Double>> functions = new ArrayList<>();
		functions.add(new ColumnElement<>(x -> x, new FloatFormatter()));

		CSVDokument dokument = new FormattedCSVDokument.Builder<>(header, contentList, functions).build();
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

		List<ColumnElement<Double>> functions = new ArrayList<>();
		functions.add(new ColumnElement<>(x -> x, new FloatFormatter(Locale.US)));

		CSVDokument dokument = new FormattedCSVDokument.Builder<>(header, contentList, functions).build();
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

		List<ColumnElement<Double>> functions = new ArrayList<>();
		functions.add(new ColumnElement<>(x -> x, new FloatFormatter("#0.000")));

		CSVDokument dokument = new FormattedCSVDokument.Builder<>(header, contentList, functions).build();
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

		List<ColumnElement<Double>> functions = new ArrayList<>();
		functions.add(new ColumnElement<>(x -> x, new FloatFormatter(Locale.US, "#0.000")));

		CSVDokument dokument = new FormattedCSVDokument.Builder<>(header, contentList, functions).build();
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

		List<ColumnElement<Double>> functions = new ArrayList<>();
		functions.add(new ColumnElement<>(x -> x, new FloatFormatter(Locale.US, "#0.000")));

		CSVDokument dokument = new FormattedCSVDokument.Builder<>(header, contentList, functions).build();
		String content = new String(dokument.print());

		String expectedContent = "Sales 2019\n" //
				+ "12.346\n" //
				+ "0.700\n" //
				+ "12000.000";

		assertEquals(expectedContent, content);
	}
}
