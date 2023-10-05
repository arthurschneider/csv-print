package de.aschneider.csvprint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.aschneider.csvprint.document.Column;
import de.aschneider.csvprint.document.CsvBuilder;
import de.aschneider.csvprint.document.CsvPrinterFactory;
import de.aschneider.csvprint.document.Printer;
import de.aschneider.csvprint.formatter.bool.BooleanJaNeinFormatter;
import de.aschneider.csvprint.formatter.bool.BooleanOneZeroFormatter;
import de.aschneider.csvprint.formatter.bool.BooleanYesNoFormatter;

class BooleanFormatterTest {

	List<Boolean> content;
	List<Column<Boolean>> functions;

	@BeforeEach
	void setUpBeforeEachTest() throws Exception {

		content = Arrays.asList(true, false);

		functions = new ArrayList<>();
	}

	@Test
	@DisplayName("Printer a BooleanYesNoFormatter should print for Booleans: true->yes and false->no.")
	void testPrintBoolsPatternYesNo() throws Exception {
		functions.add(new Column<>("Is full Aged", x -> x, new BooleanYesNoFormatter()));

		Printer printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(content, functions));
		String actualContent = new String(printer.print());

		String expectedContent = """
				Is full Aged
				yes
				no""";

		assertEquals(expectedContent, actualContent);
	}

	@Test
	@DisplayName("Printer a BooleanJaNeinFormatter should print for Booleans: true->ja and false->nein.")
	void testPrintBoolsPatternJaNein() throws Exception {
		functions.add(new Column<>("Is full Aged", x -> x, new BooleanJaNeinFormatter()));

		Printer printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(content, functions));
		String actualContent = new String(printer.print());

		String expectedContent = """
				Is full Aged
				ja
				nein""";

		assertEquals(expectedContent, actualContent);
	}

	@Test
	@DisplayName("Printer a BooleanOneZeroFormatter should print for Booleans: true->1 and false->0.")
	void testPrintBoolsPatternOneZero() throws Exception {
		functions.add(new Column<>("Is full Aged", x -> x, new BooleanOneZeroFormatter()));

		Printer printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(content, functions));
		String actualContent = new String(printer.print());

		String expectedContent = """
				Is full Aged
				1
				0""";

		assertEquals(expectedContent, actualContent);
	}

	@Test
	@DisplayName("Printer a without any Formatter should print for Booleans: true->true and false->false.")
	void testPrintBoolsWithoutFormatter() throws Exception {
		functions.add(new Column<>("Is full Aged", x -> x));

		Printer printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(content, functions));
		String actualContent = new String(printer.print());

		String expectedContent = """
				Is full Aged
				true
				false""";

		assertEquals(expectedContent, actualContent);
	}

}
