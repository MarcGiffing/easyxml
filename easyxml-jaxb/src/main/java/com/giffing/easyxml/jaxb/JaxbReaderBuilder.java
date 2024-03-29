package com.giffing.easyxml.jaxb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.stream.XMLStreamReader;

import com.giffing.easyxml.context.ParseContext;
import com.giffing.easyxml.reader.Parser;
import com.giffing.easyxml.reader.item.ItemReader;

public class JaxbReaderBuilder<R> {

	private JaxbReader<R> reader;

	private Parser<JAXBElement<R>, R> parser;

	private List<JaxbReader<R>> readers = new ArrayList<>();
	
	private List<ItemReader<XMLStreamReader, Void>> staxItemReaders = new ArrayList<>();

	private JaxbReaderBuilder() {
	}

	public static <R> JaxbReaderBuilder<R> reader(String jaxbContextPath) {
		JaxbReaderBuilder<R> builder = new JaxbReaderBuilder<>();
		builder.reader = new JaxbReader<>(new ArrayList<>(), jaxbContextPath);
		builder.readers.add(builder.reader);
		builder.parser = new Parser<>(builder.readers, builder.staxItemReaders, new ParseContext());
		return builder;
	}

	public Parser<JAXBElement<R>, R> build() {
		return parser;
	}

	public JaxbReaderBuilder<? extends R> parseContext(ParseContext parseContext) {
		this.parser.setParseContext(parseContext);
		return this;
	}

	public JaxbReaderBuilder<R> setSourceFile(File sourceFile) throws FileNotFoundException {
		this.parser.setInputStream(new FileInputStream(sourceFile));
		return this;
	}

	public JaxbReaderBuilder<R> setInputStream(InputStream inputStream) {
		this.parser.setInputStream(inputStream);
		return this;
	}

	public JaxbReaderBuilder<R> setSourceEncoding(String encoding) {
		this.parser.setEncoding(encoding);
		return this;
	}

	public JaxbReaderBuilder<R> addItemReader(JaxbItemReader<JAXBElement<R>, R> itemReader) {
		this.reader.getItemReaders().add(itemReader);
		return this;
	}
	
	public JaxbReaderBuilder<R> addStaxItemReader(ItemReader<XMLStreamReader, Void> itemReader) {
		this.staxItemReaders.add(itemReader);
		return this;
	}

}
