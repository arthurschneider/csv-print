package de.intelllinet.csvprint;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.csvprint.documents.CSVDokument;
import de.csvprint.documents.ColumnElement;
import de.csvprint.documents.FormattedCSVDokument;
import de.csvprint.formatter.BooleanFormatter;
import de.csvprint.formatter.DateAndTimeFormatter;
import de.csvprint.formatter.FloatFormatter;
import de.csvprint.utils.BoolPattern;
import de.intelllinet.csvprint.mock.models.Person;

public class FormattedCSVDokumentTest {

	private static List<String> header;
	private static List<Person> people;
	private static List<ColumnElement<Person>> functions;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		header = new ArrayList<>();
		header.add("Age");
		header.add("Firstname");
		header.add("Lastname");
		header.add("Has Car");
		header.add("Birthday");
		header.add("Income in €");

		people = new ArrayList<>();
		people.add(new Person(18, "Maik", "Muster", false, LocalDate.of(2001, 1, 12), 120.21));
		people.add(new Person(38, "Lisa", "Schuster", true, LocalDate.of(1981, 1, 15), 3010.45));

		functions = new ArrayList<>();
		functions.add(new ColumnElement<>(Person::getAge));
		functions.add(new ColumnElement<>(Person::getFirstname));
		functions.add(new ColumnElement<>(Person::getLastname));
		functions.add(new ColumnElement<>(Person::isHasCar, new BooleanFormatter(BoolPattern.YES_NO)));
		functions.add(new ColumnElement<>(Person::getBirthday, new DateAndTimeFormatter("dd.MM.yyyy")));
		functions.add(new ColumnElement<>(Person::getIncome, new FloatFormatter()));
	}

	@Test
	public void testBuilderWithoutHeader() throws Exception {
		assertThrows(NullPointerException.class, () -> {
			new FormattedCSVDokument.Builder<>(null, people, functions);
		});
	}

	@Test
	public void testBuilderWithoutContent() throws Exception {
		assertThrows(NullPointerException.class, () -> {
			new FormattedCSVDokument.Builder<>(header, null, functions);
		});
	}

	@Test
	public void testBuilderWithoutFuntions() throws Exception {
		assertThrows(NullPointerException.class, () -> {
			new FormattedCSVDokument.Builder<>(header, people, null);
		});
	}

	@Test
	public void testPrintWithEmpyContent() throws Exception {
		CSVDokument dokument = new FormattedCSVDokument.Builder<>(header, emptyList(), functions).build();

		String content = new String(dokument.print());

		String expectedContent = "Age;Firstname;Lastname;Has Car;Birthday;Income in €\n";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintWithEmpyFuntions() throws Exception {
		CSVDokument dokument = new FormattedCSVDokument.Builder<>(header, people, emptyList()).build();

		String content = new String(dokument.print());

		String expectedContent = "Age;Firstname;Lastname;Has Car;Birthday;Income in €\n";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintPeopleSuccessfully() throws Exception {
		CSVDokument dokument = new FormattedCSVDokument.Builder<>(header, people, functions).build();

		String content = new String(dokument.print());

		String expectedContent = "Age;Firstname;Lastname;Has Car;Birthday;Income in €\n" //
				+ "18;Maik;Muster;no;12.01.2001;120,21\n" //
				+ "38;Lisa;Schuster;yes;15.01.1981;3010,45";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintPeopleSuccessfullyWithCustomDelimiter() throws Exception {
		CSVDokument dokument = new FormattedCSVDokument.Builder<>(header, people, functions).delimiter(":").build();

		String content = new String(dokument.print());

		String expectedContent = "Age:Firstname:Lastname:Has Car:Birthday:Income in €\n" //
				+ "18:Maik:Muster:no:12.01.2001:120,21\n" //
				+ "38:Lisa:Schuster:yes:15.01.1981:3010,45";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintPeopleSuccessfullyWithCustomQuotes() throws Exception {
		CSVDokument dokument = new FormattedCSVDokument.Builder<>(header, people, functions).quote("\"").build();

		String content = new String(dokument.print());

		String expectedContent = "Age;Firstname;Lastname;Has Car;Birthday;Income in €\n" //
				+ "\"18\";\"Maik\";\"Muster\";\"no\";\"12.01.2001\";\"120,21\"\n" //
				+ "\"38\";\"Lisa\";\"Schuster\";\"yes\";\"15.01.1981\";\"3010,45\"";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintPeopleSuccessfullyWithCustomLineBreaks() throws Exception {
		CSVDokument dokument = new FormattedCSVDokument.Builder<>(header, people, functions).lineBreak("\r\n").build();

		String content = new String(dokument.print());

		String expectedContent = "Age;Firstname;Lastname;Has Car;Birthday;Income in €\r\n" //
				+ "18;Maik;Muster;no;12.01.2001;120,21\r\n" //
				+ "38;Lisa;Schuster;yes;15.01.1981;3010,45";

		assertEquals(expectedContent, content);
	}

}
