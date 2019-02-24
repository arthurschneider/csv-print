package de.csvprint.documents;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CsvDocumentPrinter<T> implements DocumentPrinter {

	private List<T> content;
	private List<String> header;
	private List<Column<T>> functions;

	private String quote;
	private String delimiter;
	private String lineBreak;

	public static class Builder<T> {
		// Required parameters
		List<T> content;
		List<String> header;
		List<Column<T>> functions;

		// Optional parameters
		private String quote = "";
		private String delimiter = ";";
		private String lineBreak = "\n";

		public Builder(List<String> header, List<T> content, List<Column<T>> functions) {
			this.header = Objects.requireNonNull(header);
			this.content = Objects.requireNonNull(content);
			this.functions = Objects.requireNonNull(functions);
		}

		public Builder<T> quote(String quote) {
			this.quote = quote;
			return this;
		}

		public Builder<T> delimiter(String delimiter) {
			this.delimiter = delimiter;
			return this;
		}

		public Builder<T> lineBreak(String lineBreak) {
			this.lineBreak = lineBreak;
			return this;
		}

		public CsvDocumentPrinter<T> build() {
			return new CsvDocumentPrinter<>(this);
		}
	}

	private CsvDocumentPrinter(Builder<T> builder) {
		this.header = builder.header;
		this.content = builder.content;
		this.functions = builder.functions;

		this.quote = builder.quote;
		this.delimiter = builder.delimiter;
		this.lineBreak = builder.lineBreak;
	}

	@Override
	public byte[] print() {
		return new StringJoiner(lineBreak) //
				.add(buildHeader()) //
				.add(buildContent()) //
				.toString() //
				.getBytes();
	}

	public String buildHeader() {
		return header.stream().collect(Collectors.joining(delimiter));
	}

	private String buildContent() {
		Predicate<String> notEmptyLines = line -> !line.isEmpty();
		return content.stream().map(this::buildLine).filter(notEmptyLines).collect(Collectors.joining(lineBreak));
	}

	private String buildLine(T bean) {
		return (functions.stream().map(function -> buildCell(bean, function)).collect(Collectors.joining(delimiter)));
	}

	private String buildCell(T bean, Column<T> element) {
		String extractCellContent = extractCellContent(bean, element);
		return addQuotes(extractCellContent);
	}

	private String extractCellContent(T bean, Column<T> element) {
		Object extractedContent = extractContentByFunction(bean, element);
		return formattContentByFormatter(extractedContent, element);
	}

	private Object extractContentByFunction(T bean, Column<T> element) {
		return element.getFunction().apply(bean);
	}

	private String formattContentByFormatter(Object content, Column<T> element) {
		if (content == null) {
			return "";
		}

		if (element.hasFormatter()) {
			return element.getFormatter().format(content);
		}

		return content.toString();
	}

	private String addQuotes(String fieldContent) {
		return quote + fieldContent + quote;
	}
}
