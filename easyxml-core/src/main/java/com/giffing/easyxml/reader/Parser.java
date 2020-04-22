package com.giffing.easyxml.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.lang3.StringUtils;

import com.giffing.easyxml.context.ParseContext;
import com.giffing.easyxml.context.TransformContext;
import com.giffing.easyxml.reader.item.ItemReader;
import com.giffing.easyxml.stax.reader.context.StaxTransformerResult;


public class Parser<T, R> implements Iterable<R> {

	private XMLInputFactory xif = XMLInputFactory.newInstance();
	private XMLStreamReader streamReader = null;
	private InputStream inputStream = null;

	private List<String> currentElementPath = new ArrayList<>();

	private List<? extends Reader<T, R>> readers = new ArrayList<>();

	private ParseContext context;

	private Reader<T, R> currentReader;

	private ItemReader<T, R> currentItemReader = null;
	
	private List<? extends ItemReader<XMLStreamReader, Void>> staxItemReaders = new ArrayList<>();

	public Parser(List<? extends Reader<T, R>> readers, List<? extends ItemReader<XMLStreamReader, Void>> staxItemReaders, ParseContext context) {
		this.readers = readers;
		this.staxItemReaders = staxItemReaders;
		this.context = context;
	}

	public void setParseContext(ParseContext context) {
		this.context = context;
	}

	public R readNext() {
		return currentItemReader.read(currentReader.transform(
			new TransformContext<T, R>(streamReader, currentItemReader))
			.getContent());
	}

	public boolean hasNext() {
		if (streamReader == null) {
			doOpen();
		}
		try {
			while (streamReader.hasNext()) {
				if (streamReader.isEndElement()) {
					removePath();
				}
				if (streamReader.isStartElement()) {
					addPath();
					context.setPath(StringUtils.join(currentElementPath, "/"));
					
					for (ItemReader<XMLStreamReader, Void> staxItemReader : staxItemReaders) {
						if(staxItemReader.shouldHandle(context)) {
							staxItemReader.read(new StaxTransformerResult(streamReader).getContent());
						}
					}
					
					for (Reader<T, R> reader : readers) {
						for (ItemReader<T, R> itemReader : reader.getItemReaders()) {
							if ((itemReader).shouldHandle(context)) {
								if (currentElementPath.size() > 0) {
									currentElementPath.remove(currentElementPath.size() - 1);
								}
								currentReader = reader;
								currentItemReader = itemReader;
								return true;
							}
						}
					}

				}
				streamReader.next();
			}
		} catch (XMLStreamException e) {
			throw new IllegalStateException(e);
		}

		return false;
	}

	private void addPath() {
		String localName = streamReader.getLocalName();
		currentElementPath.add(localName);
	}

	private void removePath() {
		if (currentElementPath.size() > 0) {
			int lastElementIndex = currentElementPath.size() - 1;
			if (currentElementPath.get(lastElementIndex).equals(streamReader.getLocalName())) {
				currentElementPath.remove(lastElementIndex);

			}
		}
	}

	public void close() {
		if (streamReader != null) {
			try {
				streamReader.close();
			} catch (XMLStreamException e) {
			}
		}
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
			}
		}
	}

	private void doOpen() {
		try {
			streamReader = xif.createXMLStreamReader(inputStream, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Override
	public Iterator<R> iterator() {
		return new Iterator<R>() {

			@Override
			public boolean hasNext() {
				return Parser.this.hasNext();
			}

			@Override
			public R next() {
				return Parser.this.readNext();
			}
		};
	}

}
