package de.intelllinet.csvprint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.intelllinet.csvprint.mock.models.Adress;

public class CSVDokumentTest {

	private static List<String> header;
	private static List<Adress> content;
	private static List<Function<Adress, String>> functions;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		header = new ArrayList<>();
		header.add("Zip");
		header.add("City");
		header.add("Street");
		header.add("Nr.");

		content = new ArrayList<>();
		content.add(new Adress("50226", "Frechen", "Freiheitsring", "14 a"));
		content.add(new Adress("80331", "München", "Kloßaue", "9"));

		functions = new ArrayList<>();
		functions.add(Adress::getZip);
		functions.add(Adress::getCity);
		functions.add(Adress::getStreet);
		functions.add(Adress::getHouseNumber);
	}

	@Test
	public void testBuilderWithoutHeader() throws Exception {
		assertThrows(NullPointerException.class, () -> {
			new CSVDokument.Builder<>(null, content, functions);
		});
	}

	@Test
	public void testBuilderWithoutContent() throws Exception {
		assertThrows(NullPointerException.class, () -> {
			new CSVDokument.Builder<>(header, null, functions);
		});
	}

	@Test
	public void testBuilderWithoutFuntions() throws Exception {
		assertThrows(NullPointerException.class, () -> {
			new CSVDokument.Builder<>(header, content, null);
		});
	}

	@Test
	public void testPrintSuccessfully() throws Exception {

		CSVDokument<Adress> dokument = new CSVDokument.Builder<>(header, content, functions).build();
		byte[] actualOutput = dokument.print();

		String content = new String(actualOutput);

		String expectedContent = "Zip;City;Street;Nr.\n" //
				+ "50226;Frechen;Freiheitsring;14 a\n" //
				+ "80331;München;Kloßaue;9";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintWithEmpyContent() throws Exception {

		CSVDokument<Adress> dokument = new CSVDokument.Builder<>(header, Collections.emptyList(), functions).build();
		byte[] actualOutput = dokument.print();

		String content = new String(actualOutput);

		String expectedContent = "Zip;City;Street;Nr.\n";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testPrintWithEmpyFuntions() throws Exception {

		CSVDokument<Adress> dokument = new CSVDokument.Builder<>(header, content, Collections.emptyList()).build();
		byte[] actualOutput = dokument.print();

		String content = new String(actualOutput);

		String expectedContent = "Zip;City;Street;Nr.\n";

		assertEquals(expectedContent, content);
	}

}
