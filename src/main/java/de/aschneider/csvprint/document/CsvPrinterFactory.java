package de.aschneider.csvprint.document;

public class CsvPrinterFactory {

	private CsvPrinterFactory() {
		throw new AssertionError();
	}

	public static <T> Printer getInstance(CsvBuilder<T> builder) {
		return new CsvDocumentPrinter<>(builder);
	}

}
