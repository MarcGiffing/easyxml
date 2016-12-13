package com.giffing.easyxml.jdom2.reader;

import org.jdom2.Element;

import com.giffing.easyxml.reader.item.ItemReaderBuilder;

public class JDom2ItemReaderBuilder<R> extends ItemReaderBuilder<Element, R> {

	private JDom2ItemReaderBuilder() {
	}

	public static <R> JDom2ItemReaderBuilder<R> itemReader() {
		JDom2ItemReaderBuilder<R> builder = new JDom2ItemReaderBuilder<R>();
		return builder;
	}

}
