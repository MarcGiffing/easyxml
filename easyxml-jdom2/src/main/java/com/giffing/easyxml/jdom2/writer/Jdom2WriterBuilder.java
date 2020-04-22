package com.giffing.easyxml.jdom2.writer;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.stream.XMLStreamReader;

import org.jdom2.output.Format;

import com.giffing.easyxml.context.ParseContext;
import com.giffing.easyxml.reader.item.ItemReader;

public class Jdom2WriterBuilder<C extends ParseContext> {

	private Writer writer;

	public static <C extends ParseContext> Jdom2WriterBuilder<C> writer() {
		Jdom2WriterBuilder<C> builder = new Jdom2WriterBuilder<>();
		builder.writer = new Writer();
		return builder;
	}

	public Writer build() {
		return writer;
	}

	public Jdom2WriterBuilder<C> setFormat(Format format) {
		this.writer.setFormat(format);
		return this;
	}
	
	public Jdom2WriterBuilder<C> setInputStream(InputStream inputStream) {
		this.writer.setInputStream(inputStream);
		return this;
	}

	public Jdom2WriterBuilder<C> setOutputStream(OutputStream outputStream) {
		this.writer.setOutputStream(outputStream);
		return this;
	}

	public Jdom2WriterBuilder<C> setNamespace(String namespace) {
		this.writer.setNamespace(namespace);
		return this;
	}

	public Jdom2WriterBuilder<C> addItemWriter(Jdom2ItemWriter<C> itemWriter) {
		this.writer.getItemWriter().add(itemWriter);
		return this;
	}

	public Jdom2WriterBuilder<C> addStaxItemReader(ItemReader<XMLStreamReader, Void> itemReader) {
		this.writer.getStaxItemReaders().add(itemReader);
		return this;
	}
	
	public Jdom2WriterBuilder<C> setParseContext(ParseContext parseContext) {
		this.writer.setParseContext(parseContext);
		return this;
	}
	
	public Jdom2WriterBuilder<C> setEncoding(String encoding) {
		this.writer.setEncoding(encoding);
		return this;
	}

}
