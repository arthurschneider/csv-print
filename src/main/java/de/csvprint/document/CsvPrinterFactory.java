package de.csvprint.document;

public class CsvPrinterFactory {

	private CsvPrinterFactory() {
		throw new AssertionError();
	}

	public static <T> CsvPrinter getInstance(CsvBuilder<T> builder) {
		return new CsvDocumentPrinter<>(builder);
	}

}
