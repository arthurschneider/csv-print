package de.intelllinet.csvprint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import de.csvprint.document.Column;
import de.csvprint.document.CsvBuilder;
import de.csvprint.document.CsvPrinter;
import de.csvprint.document.CsvPrinterFactory;
import de.csvprint.formatter.datetime.DateAndTimeFormatter;
import de.csvprint.formatter.datetime.DateFormatter;
import de.csvprint.formatter.datetime.LocalDateFormatter;
import de.csvprint.formatter.datetime.LocalDateTimeFormatter;

public class DateTimeFormatterTest {

	LocalDateTimeFormatter localDateTimeFormatter = new LocalDateTimeFormatter("dd.MM.yyyy");
	DateAndTimeFormatter dateAndTimeFormatter = new DateAndTimeFormatter("dd.MM.yyyy");

	@Test
	@DisplayName("Printer with a custom DateFormatter should print Date in specified format")
	public void testPrintDate() throws Exception {
		List<String> header = Arrays.asList("Birthday");
		List<Date> content = Arrays.asList(new Date());
		List<Column<Date>> functions = Arrays.asList(new Column<>(x -> x, new DateFormatter("dd.MM.yyyy")));

		CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));
		String actualContent = new String(dokument.print());

		String dateNow = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		String expectedContent = "Birthday\n" //
				+ dateNow;

		assertEquals(expectedContent, actualContent);
	}

	@Test
	@DisplayName("Printer with a custom LocalDateFormatter should print LocalDate in specified format")
	public void testPrintLocalDate() throws Exception {
		List<String> header = Arrays.asList("Birthday");
		List<LocalDate> content = Arrays.asList(LocalDate.now());
		List<Column<LocalDate>> functions = Arrays.asList(new Column<>(x -> x, new LocalDateFormatter("dd.MM.yyyy")));

		CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));
		String actualContent = new String(dokument.print());

		String dateNow = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		String expectedContent = "Birthday\n" //
				+ dateNow;

		assertEquals(expectedContent, actualContent);
	}

	@Test
	@DisplayName("Printer with a custom LocalDateTimeFormatter should print LocalDateTime in specified format")
	public void testPrintLocalDateTime() throws Exception {
		List<String> header = Arrays.asList("Birthday");
		List<LocalDateTime> content = Arrays.asList(LocalDateTime.now());
		List<Column<LocalDateTime>> functions = Arrays.asList(new Column<>(x -> x, localDateTimeFormatter));

		CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));
		String actualContent = new String(dokument.print());

		String dateNow = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		String expectedContent = "Birthday\n" //
				+ dateNow;

		assertEquals(expectedContent, actualContent);
	}

	@Test
	@DisplayName("Printer without a formatter should print Date as a simple String")
	public void testPrintDateWithoutFormatter() throws Exception {
		List<String> header = Arrays.asList("Birthday");
		List<Date> content = Arrays.asList(new Date());
		List<Column<Date>> functions = Arrays.asList(new Column<>(x -> x));

		CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));
		String actualContent = new String(dokument.print());

		String expectedContent = "Birthday\n" //
				+ new Date().toString();

		assertEquals(expectedContent, actualContent);
	}

	@Test
	@DisplayName("Printer without a formatter should print LocalDate as a simple String")
	public void testPrintLocalDateWithoutFormatter() throws Exception {
		List<String> header = Arrays.asList("Birthday");
		List<LocalDate> content = Arrays.asList(LocalDate.now());
		List<Column<LocalDate>> functions = Arrays.asList(new Column<>(x -> x));

		CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));
		String actualContent = new String(dokument.print());

		String expectedContent = "Birthday\n" //
				+ LocalDate.now().toString();

		assertEquals(expectedContent, actualContent);
	}

	@Test
	@RepeatedTest(3)
	@DisplayName("Printer without a formatter should print LocalDateTime as a simple String")
	public void testPrintLocalDateTimeWithoutFormatter() throws Exception {
		LocalDateTime now = LocalDateTime.now();

		List<String> header = Arrays.asList("Birthday");
		List<LocalDateTime> content = Arrays.asList(now);
		List<Column<LocalDateTime>> functions = Arrays.asList(new Column<>(x -> x));

		CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));
		String actualContent = new String(dokument.print());

		String expectedContent = "Birthday\n" //
				+ now.toString();

		assertEquals(expectedContent, actualContent);
	}

	@Nested
	@DisplayName("Printer with a custom DateAndTimeFormatter")
	public class DateAndTime {

		@Test
		@RepeatedTest(3)
		@DisplayName("should print LocalDateTime object in specified format")
		public void testPrintLocalDateAndTimeWithLocalDateTime() throws Exception {
			List<String> header = Arrays.asList("Birthday");
			List<LocalDateTime> content = Arrays.asList(LocalDateTime.now());
			List<Column<LocalDateTime>> functions = Arrays.asList(new Column<>(x -> x, dateAndTimeFormatter));

			CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));
			String actualContent = new String(dokument.print());

			String expectedContent = "Birthday\n" //
					+ LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

			assertEquals(expectedContent, actualContent);
		}

		@Test
		@DisplayName("should print Date object in specified format")
		public void testPrintLocalDateAndTimeWithDate() throws Exception {
			List<String> header = Arrays.asList("Birthday");
			List<Date> content = Arrays.asList(new Date());
			List<Column<Date>> functions = Arrays.asList(new Column<>(x -> x, dateAndTimeFormatter));

			CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));
			String actualContent = new String(dokument.print());

			String expectedContent = "Birthday\n" //
					+ new DateFormatter("dd.MM.yyyy").format(new Date());

			assertEquals(expectedContent, actualContent);
		}

		@Test
		@DisplayName("should print LocalDate object in specified format")
		public void testPrintLocalDateAndTimeWithLocalDate() throws Exception {
			List<String> header = Arrays.asList("Birthday");
			List<LocalDate> content = Arrays.asList(LocalDate.now());
			List<Column<LocalDate>> functions = Arrays.asList(new Column<>(x -> x, dateAndTimeFormatter));

			CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));
			String actualContent = new String(dokument.print());

			String expectedContent = "Birthday\n" //
					+ LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

			assertEquals(expectedContent, actualContent);
		}
	}
}
