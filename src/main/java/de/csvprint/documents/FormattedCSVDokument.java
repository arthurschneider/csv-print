package de.csvprint.documents;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FormattedCSVDokument<T> implements CSVDokument {

	private List<String> header;
	private List<T> content;
	private List<ColumnElement<T>> functions;

	private String lineBreak;
	private String quote;
	private String delimiter;

	public static class Builder<T> {
		// Required parameters
		List<String> header;
		List<T> content;
		List<ColumnElement<T>> functions;

		// Optional
		private String lineBreak = "\n";
		private String delimiter = ";";
		private String quote = "";

		public Builder(List<String> header, List<T> content, List<ColumnElement<T>> functions) {
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

		public FormattedCSVDokument<T> build() {
			return new FormattedCSVDokument<>(this);
		}
	}

	private FormattedCSVDokument(Builder<T> builder) {
		this.header = builder.header;
		this.content = builder.content;
		this.functions = builder.functions;
		this.lineBreak = builder.lineBreak;
		this.delimiter = builder.delimiter;
		this.quote = builder.quote;
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

	private String buildCell(T bean, ColumnElement<T> element) {
		String extractCellContent = extractCellContent(bean, element);
		return addQuotes(extractCellContent);
	}

	private String extractCellContent(T bean, ColumnElement<T> element) {
		Object extractedContent = extractContentByFunction(bean, element);
		return formattContentByFormatter(extractedContent, element);
	}

	private Object extractContentByFunction(T bean, ColumnElement<T> element) {
		return element.getFunction().apply(bean);
	}

	private String formattContentByFormatter(Object content, ColumnElement<T> element) {
		if (element.hasFormatter()) {
			return element.getFormatter().format(content);
		}

		return content.toString();
	}

	private String addQuotes(String fieldContent) {
		return quote + fieldContent + quote;
	}
}
