package de.intelllinet.csvprint;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CSVDokument<T> {

	private String escape;
	private String quote;
	private String delimiter;
	private List<String> header;
	private List<T> content;
	private List<Function<T, Object>> functions;

	public static class Builder<T> {
		// Required parameters
		List<String> header;
		List<T> content;
		List<Function<T, Object>> functions;

		// Optional
		private String escape = "\n";
		private String delimiter = ";";
		private String quote = "";

		public Builder(List<String> header, List<T> content, List<Function<T, Object>> functions) {
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

		public CSVDokument<T> build() {
			return new CSVDokument<>(this);
		}
	}

	private CSVDokument(Builder<T> builder) {
		this.header = builder.header;
		this.content = builder.content;
		this.functions = builder.functions;
		this.escape = builder.escape;
		this.delimiter = builder.delimiter;
		this.quote = builder.quote;
	}

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

	private String buildCell(T bean, Function<T, Object> function) {
		return addQuotes(function.apply(bean));
	}

	private String addQuotes(Object fieldContent) {
		return quote + fieldContent.toString() + quote;
	}

}
