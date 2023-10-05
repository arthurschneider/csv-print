package de.aschneider.csvprint;

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

import de.aschneider.csvprint.document.Column;
import de.aschneider.csvprint.document.CsvBuilder;
import de.aschneider.csvprint.document.CsvPrinterFactory;
import de.aschneider.csvprint.formatter.bool.BooleanYesNoFormatter;
import de.aschneider.csvprint.formatter.datetime.LocalDateFormatter;
import de.aschneider.csvprint.formatter.number.FloatFormatter;
import de.aschneider.csvprint.mock.models.Person;

@DisplayName("When testing the CsvPrinter with")
class FormattedCSVDokumentTest {

	private List<Person> content;
	private List<Column<Person>> functions;

	@BeforeEach
	void setUpBeforeEachTest() throws Exception {
		content = new ArrayList<>();
		content.add(new Person(18, "Maik", "Muster", false, LocalDate.of(2001, 1, 12), 120.21));
		content.add(new Person(38, "Lisa", "Schuster", true, LocalDate.of(1981, 1, 15), 3010.45));

		functions = new ArrayList<>();
		functions.add(new Column<>("Age", Person::getAge));
		functions.add(new Column<>("Firstname", Person::getFirstname));
		functions.add(new Column<>("Lastname", Person::getLastname));
		functions.add(new Column<>("Married ?", Person::isMarried, new BooleanYesNoFormatter()));
		functions.add(new Column<>("Birthday", Person::getBirthday, new LocalDateFormatter("dd.MM.yyyy")));
		functions.add(new Column<>("Income in €", Person::getIncome, new FloatFormatter()));
	}

	@Nested
	@DisplayName("a Builder that has")
	class NullValueBuilder {

		@Test
		@DisplayName("a null content list, than it should throw a NullPointerException.")
		void testBuilderWithoutContent() throws Exception {
			assertThrows(NullPointerException.class, () -> new CsvBuilder<>(null, functions));
		}

		@Test
		@DisplayName("a null function list, than it should throw a NullPointerException.")
		void testBuilderWithoutFuntions() throws Exception {
			assertThrows(NullPointerException.class, () -> new CsvBuilder<>(content, null));
		}
	}

	@Nested
	@DisplayName("a Printer that has")
	class Printer {

		@Test
		@DisplayName(" a content list that contains null objects, than it should print succesfully a CSV file.")
		void testPrintPeopleWithNullBean() throws Exception {
			content = new ArrayList<>();
			content.add(new Person(18, "Maik", "Muster", false, LocalDate.of(2001, 1, 12), 120.21));
			content.add(null);

			de.aschneider.csvprint.document.Printer printer = CsvPrinterFactory
					.getInstance(new CsvBuilder<>(content, functions));

			String actualContent = new String(printer.print());

			String expectedContent = """
					Age;Firstname;Lastname;Married ?;Birthday;Income in €
					18;Maik;Muster;no;12.01.2001;120,21""";

			assertEquals(expectedContent, actualContent);
		}

		@Test
		@DisplayName("an empty content list, than it should only print the header.")
		void testPrintWithEmpyContent() throws Exception {
			de.aschneider.csvprint.document.Printer printer = CsvPrinterFactory
					.getInstance(new CsvBuilder<>(emptyList(), functions));
			String actualContent = new String(printer.print());

			String expectedContent = "Age;Firstname;Lastname;Married ?;Birthday;Income in €\n";

			assertEquals(expectedContent, actualContent);
		}

		@Test
		@DisplayName("an empty function list, than it should only print the header.")
		void testPrintWithEmpyFuntions() throws Exception {
			de.aschneider.csvprint.document.Printer printer = CsvPrinterFactory
					.getInstance(new CsvBuilder<>(content, emptyList()));

			String actualContent = new String(printer.print());

			String expectedContent = "\n";

			assertEquals(expectedContent, actualContent);
		}

		@Test
		@DisplayName("a correct parameter list, than it should print succesfully a CSV file.")
		void testPrintPeopleSuccessfully() throws Exception {
			de.aschneider.csvprint.document.Printer printer = CsvPrinterFactory
					.getInstance(new CsvBuilder<>(content, functions));

			String actualContent = new String(printer.print());

			String expectedContent = """
					Age;Firstname;Lastname;Married ?;Birthday;Income in €
					18;Maik;Muster;no;12.01.2001;120,21
					38;Lisa;Schuster;yes;15.01.1981;3010,45""";

			assertEquals(expectedContent, actualContent);
		}

		@Test
		@DisplayName("a correct parameter list and with a custom delimiter, than it should print succesfully a CSV file.")
		void testPrintPeopleSuccessfullyWithCustomDelimiter() throws Exception {
			de.aschneider.csvprint.document.Printer printer = CsvPrinterFactory
					.getInstance(new CsvBuilder<>(content, functions).delimiter(":"));

			String actualContent = new String(printer.print());

			String expectedContent = """
					Age:Firstname:Lastname:Married ?:Birthday:Income in €
					18:Maik:Muster:no:12.01.2001:120,21
					38:Lisa:Schuster:yes:15.01.1981:3010,45""";

			assertEquals(expectedContent, actualContent);
		}

		@Test
		@DisplayName("a correct parameters and with a custom quote, than it should print succesfully a CSV file.")
		void testPrintPeopleSuccessfullyWithCustomQuotes() throws Exception {
			de.aschneider.csvprint.document.Printer printer = CsvPrinterFactory
					.getInstance(new CsvBuilder<>(content, functions).quote("\""));
			String actualContent = new String(printer.print());

			String expectedContent = """
					"Age";"Firstname";"Lastname";"Married ?";"Birthday";"Income in €"
					"18";"Maik";"Muster";"no";"12.01.2001";"120,21"
					"38";"Lisa";"Schuster";"yes";"15.01.1981";"3010,45\"""";

			assertEquals(expectedContent, actualContent);
		}

		@Test
		@DisplayName("a correct parameters and with a custom line break, than it should print succesfully a CSV file.")
		void testPrintPeopleSuccessfullyWithCustomLineBreaks() throws Exception {
			de.aschneider.csvprint.document.Printer printer = CsvPrinterFactory
					.getInstance(new CsvBuilder<>(content, functions).lineBreak("\r\n"));
			String actualContent = new String(printer.print());

			String expectedContent = """
					Age;Firstname;Lastname;Married ?;Birthday;Income in €\r
					18;Maik;Muster;no;12.01.2001;120,21\r
					38;Lisa;Schuster;yes;15.01.1981;3010,45""";

			assertEquals(expectedContent, actualContent);
		}

		@Test
		@DisplayName("a content list objects have null values,that it should print empty strings in CSV file.")
		void testPrintPeopleWithNullValues() throws Exception {
			content = new ArrayList<>();
			content.add(new Person(18, "Maik", "Muster", false, null, 120.21));
			content.add(new Person(38, null, "Schuster", true, LocalDate.of(1981, 1, 15), 3010.45));

			de.aschneider.csvprint.document.Printer printer = CsvPrinterFactory
					.getInstance(new CsvBuilder<>(content, functions));
			String actualContent = new String(printer.print());

			String expectedContent = """
					Age;Firstname;Lastname;Married ?;Birthday;Income in €
					18;Maik;Muster;no;;120,21
					38;;Schuster;yes;15.01.1981;3010,45""";

			assertEquals(expectedContent, actualContent);
		}

		@Test
		@DisplayName("a line break in a cell should be printed in file as content.")
		void testPrintPeopleSuccessfullyWithLinebreakInContent() throws Exception {
			content = new ArrayList<>();
			content.add(new Person(18, "Maik", "Must\ner", false, LocalDate.of(2001, 1, 12), 120.21));
			content.add(new Person(38, "Lisa", "Schuster", true, LocalDate.of(1981, 1, 15), 3010.45));
			de.aschneider.csvprint.document.Printer printer = CsvPrinterFactory
					.getInstance(new CsvBuilder<>(content, functions));

			String actualContent = new String(printer.print());

			String expectedContent = """
					Age;Firstname;Lastname;Married ?;Birthday;Income in €
					18;Maik;Must
					er;no;12.01.2001;120,21
					38;Lisa;Schuster;yes;15.01.1981;3010,45""";

			assertEquals(expectedContent, actualContent);
		}

		@Test
		@DisplayName("a line break in a cell and custom quotes should be printed as content.")
		void testPrintPeopleSuccessfullyWithLinebreakInContentWithCustomQuotes() throws Exception {
			content = new ArrayList<>();
			content.add(new Person(18, "Maik", "Must\ner", false, LocalDate.of(2001, 1, 12), 120.21));
			content.add(new Person(38, "Lisa", "Schuster", true, LocalDate.of(1981, 1, 15), 3010.45));

			de.aschneider.csvprint.document.Printer printer = CsvPrinterFactory
					.getInstance(new CsvBuilder<>(content, functions).quote("\""));

			String actualContent = new String(printer.print());

			String expectedContent = """
					"Age";"Firstname";"Lastname";"Married ?";"Birthday";"Income in €"
					"18";"Maik";"Must
					er";"no";"12.01.2001";"120,21"
					"38";"Lisa";"Schuster";"yes";"15.01.1981";"3010,45\"""";

			assertEquals(expectedContent, actualContent);
		}

	}

}
