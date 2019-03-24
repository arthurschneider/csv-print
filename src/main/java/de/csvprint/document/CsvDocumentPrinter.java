package de.csvprint.document;

import static java.util.stream.Collectors.joining;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CsvDocumentPrinter<T> implements CsvPrinter {

	private static final Logger LOGGER = LoggerFactory.getLogger(CsvDocumentPrinter.class);

	private final List<T> contents;
	private final List<String> header;
	private final List<Column<T>> functions;

	private final String quote;
	private final String delimiter;
	private final String lineBreak;

	CsvDocumentPrinter(CsvBuilder<T> builder) {
		this.header = builder.getHeader();
		this.contents = builder.getContents();
		this.functions = builder.getFunctions();

		this.quote = builder.getQuote();
		this.delimiter = builder.getDelimiter();
		this.lineBreak = builder.getLineBreak();
	}

	@Override
	public byte[] print() {
		return new StringJoiner(lineBreak) //
				.add(buildHead()) //
				.add(buildBody()) //
				.toString() //
				.getBytes();
	}

	private String buildHead() {
		return header.stream().map(this::addQuotes).collect(joining(delimiter));
	}

	private String buildBody() {
		return contents.stream().map(this::buildLine).filter(line -> !line.isEmpty()).collect(joining(lineBreak));
	}

	private String buildLine(T content) {
		return functions.stream().map(function -> buildCell(content, function)).collect(joining(delimiter));
	}

	private String buildCell(T content, Column<T> column) {
		Object extractCellContent = extractCellContent(content, column);
		String formattedCellContent = formattContentByFormatter(extractCellContent, column);
		return addQuotes(formattedCellContent);
	}

	private Object extractCellContent(T content, Column<T> column) {
		return column.getFunction().apply(content);
	}

	private String formattContentByFormatter(Object content, Column<T> column) {
		if (Objects.isNull(content)) {
			LOGGER.debug("Der Inhalt der Zelle hatte ein Null Wert");
			return "";
		}

		if (column.hasFormatter()) {
			return column.getFormatter().format(content);
		}

		return content.toString();
	}

	private String addQuotes(String fieldContent) {
		return quote + fieldContent + quote;
	}
}
