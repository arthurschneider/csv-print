package de.intelllinet.csvprint;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.intelllinet.csvprint.mock.models.Adress;
import de.intelllinet.csvprint.mock.models.Person;

public class NormCSVDokumentTest {

	private static List<String> adressHeader;
	private static List<String> peopleHeader;
	private static List<Adress> adresses;
	private static List<Person> people;
	private static List<Function<Adress, Object>> adressFunctions;
	private static List<Function<Person, Object>> peopleFunctions;

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
		adressFunctions.add(Adress::getZip);
		adressFunctions.add(Adress::getCity);
		adressFunctions.add(Adress::getStreet);
		adressFunctions.add(Adress::getHouseNumber);

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
		peopleFunctions.add(Person::getAge);
		peopleFunctions.add(Person::getFirstname);
		peopleFunctions.add(Person::getLastname);
		peopleFunctions.add(Person::isHasCar);
		peopleFunctions.add(Person::getBirthday);
		peopleFunctions.add(Person::getIncome);

	}

	@Test
	public void testBuilderWithoutHeader() throws Exception {
		assertThrows(NullPointerException.class, () -> {
			new NormCSVDokument.Builder<>(null, adresses, adressFunctions);
		});
	}

	@Test
	public void testBuilderWithoutContent() throws Exception {
		assertThrows(NullPointerException.class, () -> {
			new NormCSVDokument.Builder<>(adressHeader, null, adressFunctions);
		});
	}

	@Test
	public void testBuilderWithoutFuntions() throws Exception {
		assertThrows(NullPointerException.class, () -> {
			new NormCSVDokument.Builder<>(adressHeader, adresses, null);
		});
	}

	@Test
	public void testPrintAdressesSuccessfully() throws Exception {
		NormCSVDokument<Adress> dokument = new NormCSVDokument.Builder<>(adressHeader, adresses, adressFunctions).build();
		byte[] actualOutput = dokument.print();

		String content = new String(actualOutput);

		String expectedContent = "Zip;City;Street;Nr.\n" //
				+ "50226;Frechen;Freiheitsring;14 a\n" //
				+ "80331;München;Kloßaue;9";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintPeopleSuccessfully() throws Exception {
		NormCSVDokument<Person> dokument = new NormCSVDokument.Builder<>(peopleHeader, people, peopleFunctions).build();
		byte[] actualOutput = dokument.print();

		String content = new String(actualOutput);

		String expectedContent = "Age;Firstname;Lastname;Has Car;Birthday;Income in €\n" //
				+ "18;Maik;Muster;false;2001-01-12;120.21\n" //
				+ "38;Lisa;Schuster;true;1981-01-15;3010.45";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintWithEmpyContent() throws Exception {
		NormCSVDokument<Adress> dokument = new NormCSVDokument.Builder<>(adressHeader, emptyList(), adressFunctions).build();
		byte[] actualOutput = dokument.print();

		String content = new String(actualOutput);

		String expectedContent = "Zip;City;Street;Nr.\n";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintWithEmpyFuntions() throws Exception {
		NormCSVDokument<Adress> dokument = new NormCSVDokument.Builder<>(adressHeader, adresses, emptyList()).build();
		byte[] actualOutput = dokument.print();

		String content = new String(actualOutput);

		String expectedContent = "Zip;City;Street;Nr.\n";

		assertEquals(expectedContent, content);
	}

}
