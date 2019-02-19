package de.intelllinet.csvprint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.intellinet.utils.BoolPattern;
import de.intelllinet.csvprint.formatter.BooleanFormatter;
import de.intelllinet.csvprint.mock.models.BoolAnswer;
import de.intelllinet.csvprint.util.ColumnElement;

public class FormattedCSVBoolTest {

	private static List<BoolAnswer> answers;
	private static List<String> answerHeader;
	private static List<ColumnElement<BoolAnswer>> answerFunctions;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		answerHeader = new ArrayList<>();
		answerHeader.add("Is full Aged");
		answerHeader.add("Is a child");
		answerHeader.add("Is living");
		answerHeader.add("Is working");

		answers = new ArrayList<>();
		answers.add(new BoolAnswer(true, true, false, true));
		answers.add(new BoolAnswer(false, false, true, false));

		answerFunctions = new ArrayList<>();
		answerFunctions.add(new ColumnElement<>(BoolAnswer::isFullAged, new BooleanFormatter(BoolPattern.YES_NO)));
		answerFunctions.add(new ColumnElement<>(BoolAnswer::isChild, new BooleanFormatter(BoolPattern.BOOL_1_0)));
		answerFunctions.add(new ColumnElement<>(BoolAnswer::isLiving, new BooleanFormatter(BoolPattern.TRUE_FALSE)));
		answerFunctions.add(new ColumnElement<>(BoolAnswer::isWorking, null));
	}

	@Test
	public void testPrintAnswersSuccessfully() throws Exception {
		CSVDokument dokument = new FormattedCSVDokument.Builder<>(answerHeader, answers, answerFunctions).build();

		String content = new String(dokument.print());

		String expectedContent = "Is full Aged;Is a child;Is living;Is working\n" //
				+ "yes;1;false;true\n" //
				+ "no;0;true;false";

		assertEquals(expectedContent, content);
	}
}
