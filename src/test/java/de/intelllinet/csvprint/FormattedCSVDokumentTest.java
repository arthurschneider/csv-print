package de.intelllinet.csvprint;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.csvprint.document.Column;
import de.csvprint.document.CsvBuilder;
import de.csvprint.document.CsvPrinter;
import de.csvprint.document.CsvPrinterFactory;
import de.csvprint.formatter.bool.BooleanYesNoFormatter;
import de.csvprint.formatter.datetime.LocalDateFormatter;
import de.csvprint.formatter.number.FloatFormatter;
import de.intelllinet.csvprint.mock.models.Person;

@DisplayName("When testing the CsvPrinter with")
public class FormattedCSVDokumentTest {

	List<String> header;
	List<Person> content;
	List<Column<Person>> functions;

	@BeforeEach
	public void setUpBeforeEachTest() throws Exception {
		header = new ArrayList<>();
		header.add("Age");
		header.add("Firstname");
		header.add("Lastname");
		header.add("Married ?");
		header.add("Birthday");
		header.add("Income in €");

		content = new ArrayList<>();
		content.add(new Person(18, "Maik", "Muster", false, LocalDate.of(2001, 1, 12), 120.21));
		content.add(new Person(38, "Lisa", "Schuster", true, LocalDate.of(1981, 1, 15), 3010.45));

		functions = new ArrayList<>();
		functions.add(new Column<>(Person::getAge));
		functions.add(new Column<>(Person::getFirstname));
		functions.add(new Column<>(Person::getLastname));
		functions.add(new Column<>(Person::isMarried, new BooleanYesNoFormatter()));
		functions.add(new Column<>(Person::getBirthday, new LocalDateFormatter("dd.MM.yyyy")));
		functions.add(new Column<>(Person::getIncome, new FloatFormatter()));
	}

	@Nested
	@DisplayName("a Builder that has")
	public class NullValueBuilder {

		@Test
		@DisplayName("a null header list, than it should throw a NullPointerException.")
		public void testBuilderWithoutHeader() throws Exception {
			assertThrows(NullPointerException.class, () -> new CsvBuilder<>(null, content, functions));
		}

		@Test
		@DisplayName("a null content list, than it should throw a NullPointerException.")
		public void testBuilderWithoutContent() throws Exception {
			assertThrows(NullPointerException.class, () -> new CsvBuilder<>(header, null, functions));
		}

		@Test
		@DisplayName("a null function list, than it should throw a NullPointerException.")
		public void testBuilderWithoutFuntions() throws Exception {
			assertThrows(NullPointerException.class, () -> new CsvBuilder<>(header, content, null));
		}
	}

	@Nested
	@DisplayName("a Printer that has")
	public class Printer {

		@Test
		@DisplayName(" a content list that contains null objects, than it should print succesfully a CSV file.")
		public void testPrintPeopleWithNullBean() throws Exception {
			content = new ArrayList<>();
			content.add(new Person(18, "Maik", "Muster", false, LocalDate.of(2001, 1, 12), 120.21));
			content.add(null);

			CsvPrinter printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));

			String actualContent = new String(printer.print());

			String expectedContent = "Age;Firstname;Lastname;Married ?;Birthday;Income in €\n" //
					+ "18;Maik;Muster;no;12.01.2001;120,21";

			assertEquals(expectedContent, actualContent);

		}

		@Test
		@DisplayName("an empty content list, than it should only print the header.")
		public void testPrintWithEmpyContent() throws Exception {
			CsvPrinter printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, emptyList(), functions));
			String actualContent = new String(printer.print());

			String expectedContent = "Age;Firstname;Lastname;Married ?;Birthday;Income in €\n";

			assertEquals(expectedContent, actualContent);
		}

		@Test
		@DisplayName("an empty function list, than it should only print the header.")
		public void testPrintWithEmpyFuntions() throws Exception {
			assertThrows(IllegalArgumentException.class, () -> new CsvBuilder<>(header, content, emptyList()));
		}

		@Test
		@DisplayName("a correct parameter list, than it should print succesfully a CSV file.")
		public void testPrintPeopleSuccessfully() throws Exception {
			CsvPrinter printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));

			String actualContent = new String(printer.print());

			String expectedContent = "Age;Firstname;Lastname;Married ?;Birthday;Income in €\n" //
					+ "18;Maik;Muster;no;12.01.2001;120,21\n" //
					+ "38;Lisa;Schuster;yes;15.01.1981;3010,45";

			assertEquals(expectedContent, actualContent);
		}

		@Test
		@DisplayName("a correct parameter list and with a custom delimiter, than it should print succesfully a CSV file.")
		public void testPrintPeopleSuccessfullyWithCustomDelimiter() throws Exception {
			CsvPrinter printer = CsvPrinterFactory
					.getInstance(new CsvBuilder<>(header, content, functions).delimiter(":"));

			String actualContent = new String(printer.print());

			String expectedContent = "Age:Firstname:Lastname:Married ?:Birthday:Income in €\n" //
					+ "18:Maik:Muster:no:12.01.2001:120,21\n" //
					+ "38:Lisa:Schuster:yes:15.01.1981:3010,45";

			assertEquals(expectedContent, actualContent);
		}

		@Test
		@DisplayName("a correct parameters and with a custom quote, than it should print succesfully a CSV file.")
		public void testPrintPeopleSuccessfullyWithCustomQuotes() throws Exception {
			CsvPrinter printer = CsvPrinterFactory
					.getInstance(new CsvBuilder<>(header, content, functions).quote("\""));
			String actualContent = new String(printer.print());

			String expectedContent = "\"Age\";\"Firstname\";\"Lastname\";\"Married ?\";\"Birthday\";\"Income in €\"\n" //
					+ "\"18\";\"Maik\";\"Muster\";\"no\";\"12.01.2001\";\"120,21\"\n" //
					+ "\"38\";\"Lisa\";\"Schuster\";\"yes\";\"15.01.1981\";\"3010,45\"";

			assertEquals(expectedContent, actualContent);
		}

		@Test
		@DisplayName("a correct parameters and with a custom line break, than it should print succesfully a CSV file.")
		public void testPrintPeopleSuccessfullyWithCustomLineBreaks() throws Exception {
			CsvPrinter printer = CsvPrinterFactory
					.getInstance(new CsvBuilder<>(header, content, functions).lineBreak("\r\n"));
			String actualContent = new String(printer.print());

			String expectedContent = "Age;Firstname;Lastname;Married ?;Birthday;Income in €\r\n" //
					+ "18;Maik;Muster;no;12.01.2001;120,21\r\n" //
					+ "38;Lisa;Schuster;yes;15.01.1981;3010,45";

			assertEquals(expectedContent, actualContent);
		}

		@Test
		@DisplayName("a content list objects have null values,that it should print empty strings in CSV file.")
		public void testPrintPeopleWithNullValues() throws Exception {
			content = new ArrayList<>();
			content.add(new Person(18, "Maik", "Muster", false, null, 120.21));
			content.add(new Person(38, null, "Schuster", true, LocalDate.of(1981, 1, 15), 3010.45));

			CsvPrinter printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));
			String actualContent = new String(printer.print());

			String expectedContent = "Age;Firstname;Lastname;Married ?;Birthday;Income in €\n" //
					+ "18;Maik;Muster;no;;120,21\n" //
					+ "38;;Schuster;yes;15.01.1981;3010,45";

			assertEquals(expectedContent, actualContent);
		}

		@Test
		@DisplayName("a header list and function list have different lengths. Function list has fewer items.")
		public void testPrintPeopleWithFunctionListFewerItems() throws Exception {
			functions = new ArrayList<>();
			functions.add(new Column<>(Person::getAge));
			functions.add(new Column<>(Person::isMarried, new BooleanYesNoFormatter()));
			functions.add(new Column<>(Person::getBirthday, new LocalDateFormatter("dd.MM.yyyy")));
			functions.add(new Column<>(Person::getIncome, new FloatFormatter()));

			assertThrows(IllegalArgumentException.class, () -> new CsvBuilder<>(header, content, functions));

		}

		@Test
		@DisplayName("a line break in a cell should be printed in file as content.")
		public void testPrintPeopleSuccessfullyWithLinebreakInContent() throws Exception {
			content = new ArrayList<>();
			content.add(new Person(18, "Maik", "Must\ner", false, LocalDate.of(2001, 1, 12), 120.21));
			content.add(new Person(38, "Lisa", "Schuster", true, LocalDate.of(1981, 1, 15), 3010.45));
			CsvPrinter printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));

			String actualContent = new String(printer.print());

			String expectedContent = "Age;Firstname;Lastname;Married ?;Birthday;Income in €\n" //
					+ "18;Maik;Must\ner;no;12.01.2001;120,21\n" //
					+ "38;Lisa;Schuster;yes;15.01.1981;3010,45";

			assertEquals(expectedContent, actualContent);
		}

		@Test
		@DisplayName("a line break in a cell and custom quotes should be printed as content.")
		public void testPrintPeopleSuccessfullyWithLinebreakInContentWithCustomQuotes() throws Exception {
			content = new ArrayList<>();
			content.add(new Person(18, "Maik", "Must\ner", false, LocalDate.of(2001, 1, 12), 120.21));
			content.add(new Person(38, "Lisa", "Schuster", true, LocalDate.of(1981, 1, 15), 3010.45));
			CsvPrinter printer = CsvPrinterFactory
					.getInstance(new CsvBuilder<>(header, content, functions).quote("\""));

			String actualContent = new String(printer.print());

			String expectedContent = "\"Age\";\"Firstname\";\"Lastname\";\"Married ?\";\"Birthday\";\"Income in €\"\n" //
					+ "\"18\";\"Maik\";\"Must\ner\";\"no\";\"12.01.2001\";\"120,21\"\n" //
					+ "\"38\";\"Lisa\";\"Schuster\";\"yes\";\"15.01.1981\";\"3010,45\"";

			assertEquals(expectedContent, actualContent);
		}

	}

}
