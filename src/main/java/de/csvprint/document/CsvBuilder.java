package de.csvprint.document;

import java.util.List;
import java.util.Objects;

public class CsvBuilder<T> {
	// Required parameters
	private final List<T> contents;
	private final List<String> header;
	private final List<Column<T>> functions;

	// Optional parameters
	private String quote = "";
	private String delimiter = ";";
	private String lineBreak = "\n";

	public CsvBuilder(List<String> header, List<T> contents, List<Column<T>> functions) {
		this.header = Objects.requireNonNull(header);
		this.contents = Objects.requireNonNull(contents);
		this.functions = Objects.requireNonNull(functions);
	}

	public CsvBuilder<T> quote(String quote) {
		this.quote = quote;
		return this;
	}

	public CsvBuilder<T> delimiter(String delimiter) {
		this.delimiter = delimiter;
		return this;
	}

	public CsvBuilder<T> lineBreak(String lineBreak) {
		this.lineBreak = lineBreak;
		return this;
	}

	public List<T> getContents() {
		return contents;
	}

	public List<String> getHeader() {
		return header;
	}

	public List<Column<T>> getFunctions() {
		return functions;
	}

	public String getQuote() {
		return quote;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public String getLineBreak() {
		return lineBreak;
	}

}
