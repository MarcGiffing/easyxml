package com.giffing.easyxml.context;

import javax.xml.stream.XMLStreamReader;

import com.giffing.easyxml.reader.item.ItemReader;

public class TransformContext<T, R> {

	private XMLStreamReader streamReader;

	private ItemReader<T, R> itemReader;

	public TransformContext(XMLStreamReader streamReader, ItemReader<T, R> itemReader) {
		this.streamReader = streamReader;
		this.itemReader = itemReader;
	}

	public XMLStreamReader getStreamReader() {
		return streamReader;
	}

	public void setStreamReader(XMLStreamReader streamReader) {
		this.streamReader = streamReader;
	}

	public ItemReader<T, R> getItemReader() {
		return itemReader;
	}

	public void setItemReader(ItemReader<T, R> itemReader) {
		this.itemReader = itemReader;
	}
}
