package de.intelllinet.csvprint;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import de.intelllinet.csvprint.util.ColumnElement;

public class FormattedCSVDokument<T> implements CSVDokument {

	private List<String> header;
	private List<T> content;
	private List<ColumnElement<T>> functions;

	private String escape;
	private String quote;
	private String delimiter;

	public static class Builder<T> {
		// Required parameters
		List<String> header;
		List<T> content;
		List<ColumnElement<T>> functions;

		// Optional
		private String escape = "\n";
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

		public Builder<T> escape(String escape) {
			this.escape = escape;
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
		this.escape = builder.escape;
		this.delimiter = builder.delimiter;
		this.quote = builder.quote;
	}

	@Override
	public byte[] print() {
		StringBuilder sb = new StringBuilder();

		sb.append(buildHeader());

		if (validArguments()) {
			sb.append(buildContent());
		}

		return sb.toString().getBytes();
	}

	private boolean validArguments() {
		return !content.isEmpty() && !functions.isEmpty();
	}

	public String buildHeader() {
		return header.stream().collect(Collectors.joining(delimiter)) + escape;
	}

	private String buildContent() {
		return content.stream().map(this::buildLine).collect(Collectors.joining(escape));
	}

	private String buildLine(T bean) {
		return (functions.stream().map(f -> buildCell(bean, f)).collect(Collectors.joining(delimiter)));
	}

	private String buildCell(T bean, ColumnElement<T> element) {
		return extractCellContent(bean, element);
	}

	private String extractCellContent(T bean, ColumnElement<T> element) {
		Object extractedContent = extractContentByFunction(bean, element);
		String foramttedContent = formattContentByFormatter(extractedContent, element);
		return addQuotes(foramttedContent);
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

	private String addQuotes(Object fieldContent) {
		return quote + fieldContent.toString() + quote;
	}
}
