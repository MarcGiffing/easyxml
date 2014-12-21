package easyxml.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.lang3.StringUtils;

import easyxml.context.ParseContext;
import easyxml.context.TransformContext;
import easyxml.reader.item.ItemReader;

public class Parser<T, R> implements Iterable<R> {

	private XMLInputFactory xif = XMLInputFactory.newInstance();
	private XMLStreamReader streamReader = null;
	private InputStream inputStream = null;

	private List<String> currentElementPath = new ArrayList<>();

	private List<? extends Reader<T, R>> readers = new ArrayList<>();

	private ParseContext context;

	private Reader<T, R> currentReader;

	private ItemReader<T, R> currentItemReader = null;

	public Parser(List<? extends Reader<T, R>> readers, ParseContext context) {
		this.readers = readers;
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
					if (currentElementPath.size() > 0) {
						int lastElementIndex = currentElementPath.size() - 1;
						if (currentElementPath.get(lastElementIndex).equals(streamReader.getLocalName())) {
							currentElementPath.remove(lastElementIndex);

						}
					}
				}
				if (streamReader.isStartElement()) {
					String localName = streamReader.getLocalName();
					currentElementPath.add(localName);
					for (Reader<T, R> reader : readers) {
						String path = StringUtils.join(currentElementPath, "/");
						for (ItemReader<T, R> itemReader : reader.getItemReaders()) {
							context.setPath(path);
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
