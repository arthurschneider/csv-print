package de.intelllinet.csvprint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.csvprint.documents.Column;
import de.csvprint.documents.CsvDocumentPrinter;
import de.csvprint.documents.DocumentPrinter;
import de.csvprint.formatter.datetime.DateAndTimeFormatter;
import de.csvprint.formatter.datetime.DateFormatter;
import de.csvprint.formatter.datetime.LocalDateFormatter;
import de.csvprint.formatter.datetime.LocalDateTimeFormatter;

public class DateTimeFormatterTest {

	@Test
	public void testPrintDate() throws Exception {
		List<String> header = Arrays.asList("Birthday");
		List<Date> content = Arrays.asList(new Date());
		List<Column<Date>> functions = Arrays.asList(new Column<>(x -> x, new DateFormatter("dd.MM.yyyy")));

		DocumentPrinter dokument = new CsvDocumentPrinter.Builder<>(header, content, functions).build();
		String actualContent = new String(dokument.print());

		String dateNow = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		String expectedContent = "Birthday\n" //
				+ dateNow;

		assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testPrintLocalDate() throws Exception {
		List<String> header = Arrays.asList("Birthday");
		List<LocalDate> content = Arrays.asList(LocalDate.now());
		List<Column<LocalDate>> functions = Arrays.asList(new Column<>(x -> x, new LocalDateFormatter("dd.MM.yyyy")));

		DocumentPrinter dokument = new CsvDocumentPrinter.Builder<>(header, content, functions).build();
		String actualContent = new String(dokument.print());

		String dateNow = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		String expectedContent = "Birthday\n" //
				+ dateNow;

		assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testPrintLocalDateTime() throws Exception {
		List<String> header = Arrays.asList("Birthday");
		List<LocalDateTime> content = Arrays.asList(LocalDateTime.now());
		List<Column<LocalDateTime>> functions = Arrays
				.asList(new Column<>(x -> x, new LocalDateTimeFormatter("dd.MM.yyyy")));

		DocumentPrinter dokument = new CsvDocumentPrinter.Builder<>(header, content, functions).build();
		String actualContent = new String(dokument.print());

		String dateNow = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		String expectedContent = "Birthday\n" //
				+ dateNow;

		assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testPrintLocalDateAndTime() throws Exception {
		List<String> header = Arrays.asList("Birthday");
		List<LocalDateTime> content = Arrays.asList(LocalDateTime.now());
		List<Column<LocalDateTime>> functions = Arrays
				.asList(new Column<>(x -> x, new DateAndTimeFormatter("dd.MM.yyyy")));

		DocumentPrinter dokument = new CsvDocumentPrinter.Builder<>(header, content, functions).build();
		String actualContent = new String(dokument.print());

		String dateNow = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		String expectedContent = "Birthday\n" //
				+ dateNow;

		assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testPrintDateWithoutFormatter() throws Exception {
		List<String> header = Arrays.asList("Birthday");
		List<Date> content = Arrays.asList(new Date());
		List<Column<Date>> functions = Arrays.asList(new Column<>(x -> x));

		DocumentPrinter dokument = new CsvDocumentPrinter.Builder<>(header, content, functions).build();
		String actualContent = new String(dokument.print());

		String expectedContent = "Birthday\n" //
				+ new Date();

		assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testPrintLocalDateWithoutFormatter() throws Exception {
		List<String> header = Arrays.asList("Birthday");
		List<LocalDate> content = Arrays.asList(LocalDate.now());
		List<Column<LocalDate>> functions = Arrays.asList(new Column<>(x -> x));

		DocumentPrinter dokument = new CsvDocumentPrinter.Builder<>(header, content, functions).build();
		String actualContent = new String(dokument.print());

		String expectedContent = "Birthday\n" //
				+ LocalDate.now();

		assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testPrintLocalDateTimeWithoutFormatter() throws Exception {
		LocalDateTime now = LocalDateTime.now();

		List<String> header = Arrays.asList("Birthday");
		List<LocalDateTime> content = Arrays.asList(now);
		List<Column<LocalDateTime>> functions = Arrays.asList(new Column<>(x -> x));

		DocumentPrinter dokument = new CsvDocumentPrinter.Builder<>(header, content, functions).build();
		String actualContent = new String(dokument.print());

		String expectedContent = "Birthday\n" //
				+ now;

		assertEquals(expectedContent, actualContent);
	}
}
