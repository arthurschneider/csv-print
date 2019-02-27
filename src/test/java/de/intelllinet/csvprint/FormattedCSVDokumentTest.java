package de.intelllinet.csvprint;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.csvprint.document.CsvBuilder;
import de.csvprint.document.CsvPrinterFactory;
import de.csvprint.document.Column;
import de.csvprint.document.CsvPrinter;
import de.csvprint.formatter.bool.BooleanYesNoFormatter;
import de.csvprint.formatter.datetime.LocalDateFormatter;
import de.csvprint.formatter.number.FloatFormatter;
import de.intelllinet.csvprint.mock.models.Person;

public class FormattedCSVDokumentTest {

	private static List<String> header;
	private static List<Person> content;
	private static List<Column<Person>> functions;

	@BeforeEach
	public void setUpBeforeEachTest() throws Exception {
		header = new ArrayList<>();
		header.add("Age");
		header.add("Firstname");
		header.add("Lastname");
		header.add("Has Car");
		header.add("Birthday");
		header.add("Income in €");

		content = new ArrayList<>();
		content.add(new Person(18, "Maik", "Muster", false, LocalDate.of(2001, 1, 12), 120.21));
		content.add(new Person(38, "Lisa", "Schuster", true, LocalDate.of(1981, 1, 15), 3010.45));

		functions = new ArrayList<>();
		functions.add(new Column<>(Person::getAge));
		functions.add(new Column<>(Person::getFirstname));
		functions.add(new Column<>(Person::getLastname));
		functions.add(new Column<>(Person::isHasCar, new BooleanYesNoFormatter()));
		functions.add(new Column<>(Person::getBirthday, new LocalDateFormatter("dd.MM.yyyy")));
		functions.add(new Column<>(Person::getIncome, new FloatFormatter()));
	}

	@Test
	public void testBuilderWithoutHeader() throws Exception {
		assertThrows(NullPointerException.class, () -> {
			CsvPrinterFactory.getInstance(new CsvBuilder<>(null, content, functions));
		});
	}

	@Test
	public void testBuilderWithoutContent() throws Exception {
		assertThrows(NullPointerException.class, () -> {
			CsvPrinterFactory.getInstance(new CsvBuilder<>(header, null, functions));
		});
	}

	@Test
	public void testBuilderWithoutFuntions() throws Exception {
		assertThrows(NullPointerException.class, () -> {
			CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, null));
		});
	}

	@Test
	public void testPrintWithEmpyContent() throws Exception {
		CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, emptyList(), functions));

		String content = new String(dokument.print());

		String expectedContent = "Age;Firstname;Lastname;Has Car;Birthday;Income in €\n";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintWithEmpyFuntions() throws Exception {

		CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, emptyList()));

		String content = new String(dokument.print());

		String expectedContent = "Age;Firstname;Lastname;Has Car;Birthday;Income in €\n";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintPeopleSuccessfully() throws Exception {

		CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));

		String content = new String(dokument.print());

		String expectedContent = "Age;Firstname;Lastname;Has Car;Birthday;Income in €\n" //
				+ "18;Maik;Muster;no;12.01.2001;120,21\n" //
				+ "38;Lisa;Schuster;yes;15.01.1981;3010,45";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintPeopleSuccessfullyWithCustomDelimiter() throws Exception {

		CsvPrinter dokument = CsvPrinterFactory
				.getInstance(new CsvBuilder<>(header, content, functions).delimiter(":"));

		String content = new String(dokument.print());

		String expectedContent = "Age:Firstname:Lastname:Has Car:Birthday:Income in €\n" //
				+ "18:Maik:Muster:no:12.01.2001:120,21\n" //
				+ "38:Lisa:Schuster:yes:15.01.1981:3010,45";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintPeopleSuccessfullyWithCustomQuotes() throws Exception {

		CsvPrinter dokument = CsvPrinterFactory
				.getInstance(new CsvBuilder<>(header, content, functions).quote("\""));

		String content = new String(dokument.print());

		String expectedContent = "Age;Firstname;Lastname;Has Car;Birthday;Income in €\n" //
				+ "\"18\";\"Maik\";\"Muster\";\"no\";\"12.01.2001\";\"120,21\"\n" //
				+ "\"38\";\"Lisa\";\"Schuster\";\"yes\";\"15.01.1981\";\"3010,45\"";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintPeopleSuccessfullyWithCustomLineBreaks() throws Exception {

		CsvPrinter dokument = CsvPrinterFactory
				.getInstance(new CsvBuilder<>(header, content, functions).lineBreak("\r\n"));

		String content = new String(dokument.print());

		String expectedContent = "Age;Firstname;Lastname;Has Car;Birthday;Income in €\r\n" //
				+ "18;Maik;Muster;no;12.01.2001;120,21\r\n" //
				+ "38;Lisa;Schuster;yes;15.01.1981;3010,45";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintPeopleWithNullValues() throws Exception {
		content = new ArrayList<>();
		content.add(new Person(18, "Maik", "Muster", false, null, 120.21));
		content.add(new Person(38, null, "Schuster", true, LocalDate.of(1981, 1, 15), 3010.45));

		CsvPrinter doc = CsvPrinterFactory
				.getInstance(new CsvBuilder<>(header, content, functions).lineBreak("\r\n"));

		String content = new String(doc.print());

		String expectedContent = "Age;Firstname;Lastname;Has Car;Birthday;Income in €\r\n" //
				+ "18;Maik;Muster;no;;120,21\r\n" //
				+ "38;;Schuster;yes;15.01.1981;3010,45";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintPeopleWithNullBean() throws Exception {
		content = new ArrayList<>();
		content.add(new Person(18, "Maik", "Muster", false, LocalDate.of(2001, 1, 12), 120.21));
		content.add(null);

		CsvPrinter doc = CsvPrinterFactory
				.getInstance(new CsvBuilder<>(header, content, functions).lineBreak("\r\n"));

		assertThrows(NullPointerException.class, () -> {
			doc.print();
		});
	}

}
