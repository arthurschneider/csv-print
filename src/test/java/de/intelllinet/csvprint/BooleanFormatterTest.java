package de.intelllinet.csvprint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.csvprint.documents.DocumentPrinter;
import de.csvprint.documents.Column;
import de.csvprint.documents.CsvDocumentPrinter;
import de.csvprint.formatter.bool.BooleanJaNeinFormatter;
import de.csvprint.formatter.bool.BooleanOneZeroFormatter;
import de.csvprint.formatter.bool.BooleanYesNoFormatter;

public class BooleanFormatterTest {

	@Test
	public void testPrintBoolsPatternYesNo() throws Exception {
		List<String> header = new ArrayList<>();
		header.add("Is full Aged");

		List<Boolean> contentList = new ArrayList<>();
		contentList.add(true);
		contentList.add(false);

		List<Column<Boolean>> functions = new ArrayList<>();
		functions.add(new Column<>(x -> x, new BooleanYesNoFormatter()));

		DocumentPrinter dokument = new CsvDocumentPrinter.Builder<>(header, contentList, functions).build();
		String content = new String(dokument.print());

		String expectedContent = "Is full Aged\n" //
				+ "yes\n" //
				+ "no";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintBoolsPatternJaNein() throws Exception {
		List<String> header = new ArrayList<>();
		header.add("Is full Aged");

		List<Boolean> contentList = new ArrayList<>();
		contentList.add(true);
		contentList.add(false);

		List<Column<Boolean>> functions = new ArrayList<>();
		functions.add(new Column<>(x -> x, new BooleanJaNeinFormatter()));

		DocumentPrinter dokument = new CsvDocumentPrinter.Builder<>(header, contentList, functions).build();
		String content = new String(dokument.print());

		String expectedContent = "Is full Aged\n" //
				+ "ja\n" //
				+ "nein";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintBoolsPatternOneZero() throws Exception {
		List<String> header = new ArrayList<>();
		header.add("Is full Aged");

		List<Boolean> contentList = new ArrayList<>();
		contentList.add(true);
		contentList.add(false);

		List<Column<Boolean>> functions = new ArrayList<>();
		functions.add(new Column<>(x -> x, new BooleanOneZeroFormatter()));

		DocumentPrinter dokument = new CsvDocumentPrinter.Builder<>(header, contentList, functions).build();
		String content = new String(dokument.print());

		String expectedContent = "Is full Aged\n" //
				+ "1\n" //
				+ "0";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintBoolsWithoutFormatter() throws Exception {
		List<String> header = new ArrayList<>();
		header.add("Is full Aged");

		List<Boolean> contentList = new ArrayList<>();
		contentList.add(true);
		contentList.add(false);

		List<Column<Boolean>> functions = new ArrayList<>();
		functions.add(new Column<>(x -> x));

		DocumentPrinter dokument = new CsvDocumentPrinter.Builder<>(header, contentList, functions).build();
		String content = new String(dokument.print());

		String expectedContent = "Is full Aged\n" //
				+ "true\n" //
				+ "false";

		assertEquals(expectedContent, content);
	}
}
