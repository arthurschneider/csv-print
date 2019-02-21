package de.intelllinet.csvprint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.csvprint.documents.CSVDokument;
import de.csvprint.documents.ColumnElement;
import de.csvprint.documents.FormattedCSVDokument;
import de.csvprint.formatter.BooleanFormatter;
import de.csvprint.utils.BoolPattern;

public class FormattedCSVBoolTest {

	@Test
	public void testPrintBoolsPatternYesNo() throws Exception {
		List<String> header = new ArrayList<>();
		header.add("Is full Aged");

		List<Boolean> contentList = new ArrayList<>();
		contentList.add(true);
		contentList.add(false);

		List<ColumnElement<Boolean>> functions = new ArrayList<>();
		functions.add(new ColumnElement<>(x -> x, new BooleanFormatter(BoolPattern.YES_NO)));

		CSVDokument dokument = new FormattedCSVDokument.Builder<>(header, contentList, functions).build();
		String content = new String(dokument.print());

		String expectedContent = "Is full Aged\n" //
				+ "yes\n" //
				+ "no";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintBoolsPatternOneZero() throws Exception {
		List<String> header = new ArrayList<>();
		header.add("Is full Aged");

		List<Boolean> contentList = new ArrayList<>();
		contentList.add(true);
		contentList.add(false);

		List<ColumnElement<Boolean>> functions = new ArrayList<>();
		functions.add(new ColumnElement<>(x -> x, new BooleanFormatter(BoolPattern.BOOL_1_0)));

		CSVDokument dokument = new FormattedCSVDokument.Builder<>(header, contentList, functions).build();
		String content = new String(dokument.print());

		String expectedContent = "Is full Aged\n" //
				+ "1\n" //
				+ "0";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintBoolsPatternTrueFalse() throws Exception {
		List<String> header = new ArrayList<>();
		header.add("Is full Aged");

		List<Boolean> contentList = new ArrayList<>();
		contentList.add(true);
		contentList.add(false);

		List<ColumnElement<Boolean>> functions = new ArrayList<>();
		functions.add(new ColumnElement<>(x -> x, new BooleanFormatter(BoolPattern.TRUE_FALSE)));

		CSVDokument dokument = new FormattedCSVDokument.Builder<>(header, contentList, functions).build();
		String content = new String(dokument.print());

		String expectedContent = "Is full Aged\n" //
				+ "true\n" //
				+ "false";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintBoolsPatternStandard() throws Exception {
		List<String> header = new ArrayList<>();
		header.add("Is full Aged");

		List<Boolean> contentList = new ArrayList<>();
		contentList.add(true);
		contentList.add(false);

		List<ColumnElement<Boolean>> functions = new ArrayList<>();
		functions.add(new ColumnElement<>(x -> x, null));

		CSVDokument dokument = new FormattedCSVDokument.Builder<>(header, contentList, functions).build();
		String content = new String(dokument.print());

		String expectedContent = "Is full Aged\n" //
				+ "true\n" //
				+ "false";

		assertEquals(expectedContent, content);
	}
}
