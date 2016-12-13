package com.giffing.easyxml.jdom2.writer;

import java.io.InputStream;
import java.io.OutputStream;

public class Jdom2WriterBuilder<R> {

	private Writer writer;

	public static <R> Jdom2WriterBuilder<R> writer() {
		Jdom2WriterBuilder<R> builder = new Jdom2WriterBuilder<>();
		builder.writer = new Writer();
		return builder;
	}

	public Writer build() {
		return writer;
	}

	public Jdom2WriterBuilder<R> setInputStream(InputStream inputStream) {
		this.writer.setInputStream(inputStream);
		return this;
	}

	public Jdom2WriterBuilder<R> setOutputStream(OutputStream outputStream) {
		this.writer.setOutputStream(outputStream);
		return this;
	}

	public Jdom2WriterBuilder<R> setNamespace(String namespace) {
		this.writer.setNamespace(namespace);
		return this;
	}

	public Jdom2WriterBuilder<R> addItemWriter(Jdom2ItemWriter itemWriter) {
		this.writer.getItemWriter().add(itemWriter);
		return this;
	}

}
