package com.giffing.easyxml.jdom2.reader;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamReader;

import org.jdom2.Element;

import com.giffing.easyxml.context.ParseContext;
import com.giffing.easyxml.reader.Parser;
import com.giffing.easyxml.reader.item.ItemReader;

public class JDom2ReaderBuilder<R> {

	private JDom2Reader<R> reader;

	private Parser<Element, R> parser;

	private List<JDom2Reader<R>> readers = new ArrayList<>();
	
	private List<ItemReader<XMLStreamReader, Void>> staxItemReaders = new ArrayList<>();

	private JDom2ReaderBuilder() {
	}

	public static <R> JDom2ReaderBuilder<R> reader() {
		JDom2ReaderBuilder<R> builder = new JDom2ReaderBuilder<>();
		builder.reader = new JDom2Reader<>(new ArrayList<>());
		builder.readers.add(builder.reader);
		builder.parser = new Parser<>(builder.readers, builder.staxItemReaders, new ParseContext());
		return builder;
	}

	public Parser<Element, R> build() {
		return parser;
	}

	public JDom2ReaderBuilder<R> parseContext(ParseContext parseContext) {
		this.parser.setParseContext(parseContext);
		return this;
	}

	public JDom2ReaderBuilder<R> setSourceFile(File sourceFile) throws IOException {
		this.parser.setInputStream(Files.newInputStream(sourceFile.toPath()));
		return this;
	}

	public JDom2ReaderBuilder<R> setInputStream(InputStream inputStream) {
		this.parser.setInputStream(inputStream);
		return this;
	}

	public JDom2ReaderBuilder<R> setSourceEncoding(String encoding) {
		this.parser.setEncoding(encoding);
		return this;
	}

	public JDom2ReaderBuilder<R> addItemReader(ItemReader<Element, R> itemReader) {
		this.reader.getItemReaders().add(itemReader);
		return this;
	}
	
	public JDom2ReaderBuilder<R> addStaxItemReader(ItemReader<XMLStreamReader, Void> itemReader) {
		this.staxItemReaders.add(itemReader);
		return this;
	}

}
