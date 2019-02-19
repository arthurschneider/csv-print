package de.intelllinet.csvprint;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.intelllinet.csvprint.formatter.DateAndTimeFormatter;
import de.intelllinet.csvprint.formatter.NumberFormatter;
import de.intelllinet.csvprint.mock.models.Adress;
import de.intelllinet.csvprint.mock.models.Person;
import de.intelllinet.csvprint.util.ColumnElement;

public class FormattedCSVDokumentTest {

	private static List<String> adressHeader;
	private static List<String> peopleHeader;
	private static List<Adress> adresses;
	private static List<Person> people;
	private static List<ColumnElement<Adress>> adressFunctions;
	private static List<ColumnElement<Person>> peopleFunctions;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		adressHeader = new ArrayList<>();
		adressHeader.add("Zip");
		adressHeader.add("City");
		adressHeader.add("Street");
		adressHeader.add("Nr.");

		adresses = new ArrayList<>();
		adresses.add(new Adress("50226", "Frechen", "Freiheitsring", "14 a"));
		adresses.add(new Adress("80331", "München", "Kloßaue", "9"));

		adressFunctions = new ArrayList<>();
		adressFunctions.add(new ColumnElement<>(Adress::getZip, null));
		adressFunctions.add(new ColumnElement<>(Adress::getCity, null));
		adressFunctions.add(new ColumnElement<>(Adress::getStreet, null));
		adressFunctions.add(new ColumnElement<>(Adress::getHouseNumber, null));

		peopleHeader = new ArrayList<>();
		peopleHeader.add("Age");
		peopleHeader.add("Firstname");
		peopleHeader.add("Lastname");
		peopleHeader.add("Has Car");
		peopleHeader.add("Birthday");
		peopleHeader.add("Income in €");

		people = new ArrayList<>();
		people.add(new Person(18, "Maik", "Muster", false, LocalDate.of(2001, 1, 12), 120.21));
		people.add(new Person(38, "Lisa", "Schuster", true, LocalDate.of(1981, 1, 15), 3010.45));

		peopleFunctions = new ArrayList<>();
		peopleFunctions.add(new ColumnElement<>(Person::getAge, null));
		peopleFunctions.add(new ColumnElement<>(Person::getFirstname, null));
		peopleFunctions.add(new ColumnElement<>(Person::getLastname, null));
		peopleFunctions.add(new ColumnElement<>(Person::isHasCar, null));
		peopleFunctions.add(new ColumnElement<>(Person::getBirthday, new DateAndTimeFormatter("dd.MM.yyyy")));
		peopleFunctions.add(new ColumnElement<>(Person::getIncome, new NumberFormatter()));

	}

	@Test
	public void testBuilderWithoutHeader() throws Exception {
		assertThrows(NullPointerException.class, () -> {
			new FormattedCSVDokument.Builder<>(null, adresses, adressFunctions);
		});
	}

	@Test
	public void testBuilderWithoutContent() throws Exception {
		assertThrows(NullPointerException.class, () -> {
			new FormattedCSVDokument.Builder<>(adressHeader, null, adressFunctions);
		});
	}

	@Test
	public void testBuilderWithoutFuntions() throws Exception {
		assertThrows(NullPointerException.class, () -> {
			new FormattedCSVDokument.Builder<>(adressHeader, adresses, null);
		});
	}

	@Test
	public void testPrintAdressesSuccessfully() throws Exception {
		CSVDokument dokument = new FormattedCSVDokument.Builder<>(adressHeader, adresses, adressFunctions).build();

		String content = new String(dokument.print());

		String expectedContent = "Zip;City;Street;Nr.\n" //
				+ "50226;Frechen;Freiheitsring;14 a\n" //
				+ "80331;München;Kloßaue;9";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintPeopleSuccessfully() throws Exception {
		CSVDokument dokument = new FormattedCSVDokument.Builder<>(peopleHeader, people, peopleFunctions).build();

		String content = new String(dokument.print());

		String expectedContent = "Age;Firstname;Lastname;Has Car;Birthday;Income in €\n" //
				+ "18;Maik;Muster;false;12.01.2001;120,21\n" //
				+ "38;Lisa;Schuster;true;15.01.1981;3.010,45";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintWithEmpyContent() throws Exception {
		CSVDokument dokument = new FormattedCSVDokument.Builder<>(adressHeader, emptyList(), adressFunctions).build();

		String content = new String(dokument.print());

		String expectedContent = "Zip;City;Street;Nr.\n";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintWithEmpyFuntions() throws Exception {
		CSVDokument dokument = new FormattedCSVDokument.Builder<>(adressHeader, adresses, emptyList()).build();

		String content = new String(dokument.print());

		String expectedContent = "Zip;City;Street;Nr.\n";

		assertEquals(expectedContent, content);
	}

}
