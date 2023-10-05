package de.aschneider.csvprint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import de.aschneider.csvprint.document.Column;
import de.aschneider.csvprint.document.CsvBuilder;
import de.aschneider.csvprint.document.CsvPrinterFactory;
import de.aschneider.csvprint.document.Printer;
import de.aschneider.csvprint.formatter.Formatter;
import de.aschneider.csvprint.formatter.datetime.DateAndTimeFormatter;
import de.aschneider.csvprint.formatter.datetime.DateFormatter;
import de.aschneider.csvprint.formatter.datetime.LocalDateFormatter;
import de.aschneider.csvprint.formatter.datetime.LocalDateTimeFormatter;

class DateTimeFormatterTest {

	Formatter localDateTimeFormatter = new LocalDateTimeFormatter("dd.MM.yyyy");
	Formatter dateAndTimeFormatter = new DateAndTimeFormatter("dd.MM.yyyy");

	@Test
	@DisplayName("Printer with a custom DateFormatter should print Date in specified format.")
	void testPrintDate() throws Exception {
		var content = List.of(new Date());
		var functions = List.of(new Column<Date>("Birthday", x -> x, new DateFormatter("dd.MM.yyyy")));

		Printer printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(content, functions));
		String actualContent = new String(printer.print());

		String dateNow = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		String expectedContent = "Birthday\n" //
				+ dateNow;

		assertEquals(expectedContent, actualContent);
	}

	@Test
	@DisplayName("Printer with a custom LocalDateFormatter should print LocalDate in specified format.")
	void testPrintLocalDate() throws Exception {
		var content = List.of(LocalDate.now());
		var functions = List.of(new Column<LocalDate>("Birthday", x -> x, new LocalDateFormatter("dd.MM.yyyy")));

		Printer printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(content, functions));
		String actualContent = new String(printer.print());

		String dateNow = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		String expectedContent = "Birthday\n" //
				+ dateNow;

		assertEquals(expectedContent, actualContent);
	}

	@Test
	@DisplayName("Printer with a custom LocalDateTimeFormatter should print LocalDateTime in specified format.")
	void testPrintLocalDateTime() throws Exception {
		var content = List.of(LocalDateTime.now());
		var functions = List.of(new Column<LocalDateTime>("Birthday", x -> x, localDateTimeFormatter));

		Printer printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(content, functions));
		String actualContent = new String(printer.print());

		String dateNow = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		String expectedContent = "Birthday\n" //
				+ dateNow;

		assertEquals(expectedContent, actualContent);
	}

	@Test
	@DisplayName("Printer without a formatter should print Date as a simple String.")
	void testPrintDateWithoutFormatter() throws Exception {
		var content = List.of(new Date());
		var functions = List.of(new Column<Date>("Birthday", x -> x));

		Printer printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(content, functions));
		String actualContent = new String(printer.print());

		String expectedContent = "Birthday\n" //
				+ new Date().toString();

		assertEquals(expectedContent, actualContent);
	}

	@Test
	@DisplayName("Printer without a formatter should print LocalDate as a simple String.")
	void testPrintLocalDateWithoutFormatter() throws Exception {
		var content = List.of(LocalDate.now());
		var functions = List.of(new Column<LocalDate>("Birthday", x -> x));

		Printer printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(content, functions));
		String actualContent = new String(printer.print());

		String expectedContent = "Birthday\n" //
				+ LocalDate.now().toString();

		assertEquals(expectedContent, actualContent);
	}

	@RepeatedTest(3)
	@DisplayName("Printer without a formatter should print LocalDateTime as a simple String.")
	void testPrintLocalDateTimeWithoutFormatter() throws Exception {
		LocalDateTime now = LocalDateTime.now();

		var content = List.of(now);
		var functions = List.of(new Column<LocalDateTime>("Birthday", x -> x));

		Printer printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(content, functions));
		String actualContent = new String(printer.print());

		String expectedContent = "Birthday\n" //
				+ now.toString();

		assertEquals(expectedContent, actualContent);
	}

	@Nested
	@DisplayName("Printer with a custom DateAndTimeFormatter")
	class DateAndTime {

		@RepeatedTest(3)
		@DisplayName("should print LocalDateTime object in specified format.")
		void testPrintLocalDateAndTimeWithLocalDateTime() throws Exception {
			var content = List.of(LocalDateTime.of(2023, 1, 23, 12, 55));
			var functions = List.of(new Column<LocalDateTime>("Birthday", x -> x, dateAndTimeFormatter));

			Printer printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(content, functions));
			String actualContent = new String(printer.print());

			String expectedContent = """
					Birthday
					23.01.2023""";

			assertEquals(expectedContent, actualContent);
		}

		@Test
		@DisplayName("should print LocalDate object in specified format.")
		void testPrintLocalDateAndTimeWithLocalDate() throws Exception {
			var content = List.of(LocalDate.of(2021, 12, 31));
			var functions = List.of(new Column<LocalDate>("Birthday", x -> x, dateAndTimeFormatter));

			Printer printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(content, functions));
			String actualContent = new String(printer.print());

			String expectedContent = """
					Birthday
					31.12.2021""";

			assertEquals(expectedContent, actualContent);
		}
	}
}
