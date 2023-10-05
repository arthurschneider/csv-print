package de.aschneider.csvprint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.aschneider.csvprint.document.Column;
import de.aschneider.csvprint.document.CsvBuilder;
import de.aschneider.csvprint.document.CsvPrinterFactory;
import de.aschneider.csvprint.document.Printer;
import de.aschneider.csvprint.formatter.bool.BooleanYesNoFormatter;
import de.aschneider.csvprint.formatter.datetime.LocalDateFormatter;
import de.aschneider.csvprint.formatter.number.FloatFormatter;
import de.aschneider.csvprint.mock.models.Family;
import de.aschneider.csvprint.mock.models.Person;

@DisplayName("When testing the CsvPrinter with complex Objects")
class ComplexObjektPrintTest {

	List<Family> content;
	List<Column<Family>> functions;

	@BeforeEach
	void setUpBeforeEachTest() throws Exception {

		Person father = new Person(39, "Maik", "Muster", false, LocalDate.of(1980, 1, 12), 1200.21);
		Person mother = new Person(38, "Lisa", "Schuster", true, LocalDate.of(1981, 1, 15), 3010.45);

		content = new ArrayList<>();
		content.add(new Family(father, mother, true, LocalDate.of(2014, 3, 14)));

		functions = new ArrayList<>();
		functions.add(new Column<>("Living together ?", f -> f.getLivingTogether(), new BooleanYesNoFormatter()));
		functions.add(new Column<>("Living together since", f -> f.getLivingTogetherSince(),
				new LocalDateFormatter("dd.MM.yyyy")));
		functions.add(new Column<>("Age Father", f -> f.getFather().getAge()));
		functions.add(new Column<>("Firstname Father", f -> f.getFather().getFirstname()));
		functions.add(new Column<>("Lastname Father", f -> f.getFather().getLastname()));
		functions.add(new Column<>("Married ? Father", f -> f.getFather().isMarried(), new BooleanYesNoFormatter()));
		functions.add(new Column<>("Birthday Father", f -> f.getFather().getBirthday(),
				new LocalDateFormatter("dd.MM.yyyy")));
		functions.add(new Column<>("Income in € Father", f -> f.getFather().getIncome(), new FloatFormatter()));
		functions.add(new Column<>("Age Mother", f -> f.getMother().getAge()));
		functions.add(new Column<>("Firstname Mother", f -> f.getMother().getFirstname()));
		functions.add(new Column<>("Lastname Mother", f -> f.getMother().getLastname()));
		functions.add(new Column<>("Married ? Mother", f -> f.getMother().isMarried(), new BooleanYesNoFormatter()));
		functions.add(new Column<>("Birthday Mother", f -> f.getMother().getBirthday(),
				new LocalDateFormatter("dd.MM.yyyy")));
		functions.add(new Column<>("Income in € Mother", f -> f.getMother().getIncome(), new FloatFormatter()));

	}

	@Test
	@DisplayName("the csv file should be printed successfully.")
	void testPrintFamilySuccessfully() throws Exception {
		Printer printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(content, functions));

		String actualContent = new String(printer.print());

		String expectedContent = """
				Living together ?;Living together since;Age Father;Firstname Father;Lastname Father;Married ? Father;Birthday Father;Income in € Father;Age Mother;Firstname Mother;Lastname Mother;Married ? Mother;Birthday Mother;Income in € Mother
				yes;14.03.2014;39;Maik;Muster;no;12.01.1980;1200,21;38;Lisa;Schuster;yes;15.01.1981;3010,45""";

		assertEquals(expectedContent, actualContent);
	}

}
