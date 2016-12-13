package com.giffing.easyxml.jdom2.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
	
	private List<ItemReader<XMLStreamReader, R>> staxItemReaders = new ArrayList<>();

	private JDom2ReaderBuilder() {
	}

	public static <R> JDom2ReaderBuilder<R> reader() {
		JDom2ReaderBuilder<R> builder = new JDom2ReaderBuilder<>();
		builder.reader = new JDom2Reader<R>(new ArrayList<>());
		builder.readers.add(builder.reader);
		builder.parser = new Parser<Element, R>(builder.readers, builder.staxItemReaders, new ParseContext());
		return builder;
	}

	public Parser<Element, R> build() {
		return parser;
	}

	public JDom2ReaderBuilder<R> parseContext(ParseContext parseContext) {
		this.parser.setParseContext(parseContext);
		return this;
	}

	public JDom2ReaderBuilder<R> setSourceFile(File sourceFile) throws FileNotFoundException {
		this.parser.setInputStream(new FileInputStream(sourceFile));
		return this;
	}

	public JDom2ReaderBuilder<R> setInputStream(InputStream inputStream) {
		this.parser.setInputStream(inputStream);
		return this;
	}

	public JDom2ReaderBuilder<R> addItemReader(ItemReader<Element, R> itemReader) {
		this.reader.getItemReaders().add(itemReader);
		return this;
	}
	
	public JDom2ReaderBuilder<R> addStaxItemReader(ItemReader<XMLStreamReader, R> itemReader) {
		this.staxItemReaders.add(itemReader);
		return this;
	}

}
