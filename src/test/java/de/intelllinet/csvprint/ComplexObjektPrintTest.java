package de.intelllinet.csvprint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.csvprint.document.Column;
import de.csvprint.document.CsvBuilder;
import de.csvprint.document.CsvPrinter;
import de.csvprint.document.CsvPrinterFactory;
import de.csvprint.formatter.bool.BooleanYesNoFormatter;
import de.csvprint.formatter.datetime.LocalDateFormatter;
import de.csvprint.formatter.number.FloatFormatter;
import de.intelllinet.csvprint.mock.models.Family;
import de.intelllinet.csvprint.mock.models.Person;

@DisplayName("When testing the CsvPrinter with complex Objects")
public class ComplexObjektPrintTest {

	private static List<String> header;
	private static List<Family> content;
	private static List<Column<Family>> functions;

	@BeforeEach
	public void setUpBeforeEachTest() throws Exception {
		header = new ArrayList<>();
		header.add("Living together ?");
		header.add("Living together since");
		header.add("Age Father");
		header.add("Firstname Father");
		header.add("Lastname Father");
		header.add("Married ? Father");
		header.add("Birthday Father");
		header.add("Income in € Father");
		header.add("Age Mother");
		header.add("Firstname Mother");
		header.add("Lastname Mother");
		header.add("Married ? Mother");
		header.add("Birthday Mother");
		header.add("Income in € Mother");

		Person father = new Person(39, "Maik", "Muster", false, LocalDate.of(1980, 1, 12), 1200.21);
		Person mother = new Person(38, "Lisa", "Schuster", true, LocalDate.of(1981, 1, 15), 3010.45);

		content = new ArrayList<>();
		content.add(new Family(father, mother, true, LocalDate.of(2014, 3, 14)));

		functions = new ArrayList<>();
		functions.add(new Column<>(f -> f.getLivingTogether(), new BooleanYesNoFormatter()));
		functions.add(new Column<>(f -> f.getLivingTogetherSince(), new LocalDateFormatter("dd.MM.yyyy")));
		functions.add(new Column<>(f -> f.getFather().getAge()));
		functions.add(new Column<>(f -> f.getFather().getFirstname()));
		functions.add(new Column<>(f -> f.getFather().getLastname()));
		functions.add(new Column<>(f -> f.getFather().isMarried(), new BooleanYesNoFormatter()));
		functions.add(new Column<>(f -> f.getFather().getBirthday(), new LocalDateFormatter("dd.MM.yyyy")));
		functions.add(new Column<>(f -> f.getFather().getIncome(), new FloatFormatter()));
		functions.add(new Column<>(f -> f.getMother().getAge()));
		functions.add(new Column<>(f -> f.getMother().getFirstname()));
		functions.add(new Column<>(f -> f.getMother().getLastname()));
		functions.add(new Column<>(f -> f.getMother().isMarried(), new BooleanYesNoFormatter()));
		functions.add(new Column<>(f -> f.getMother().getBirthday(), new LocalDateFormatter("dd.MM.yyyy")));
		functions.add(new Column<>(f -> f.getMother().getIncome(), new FloatFormatter()));

	}

	@Test
	@DisplayName("the csv file should be printed successfully")
	public void testPrintFamilySuccessfully() throws Exception {
		CsvPrinter dokument = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));

		String actualContent = new String(dokument.print());

		String expectedContent = "Living together ?;Living together since;Age Father;Firstname Father;Lastname Father;"
				+ "Married ? Father;Birthday Father;Income in € Father;Age Mother;Firstname Mother;Lastname Mother;"
				+ "Married ? Mother;Birthday Mother;Income in € Mother\n" //
				+ "yes;14.03.2014;39;Maik;Muster;no;12.01.1980;1200,21;38;Lisa;Schuster;yes;15.01.1981;3010,45";

		assertEquals(expectedContent, actualContent);

	}

}
