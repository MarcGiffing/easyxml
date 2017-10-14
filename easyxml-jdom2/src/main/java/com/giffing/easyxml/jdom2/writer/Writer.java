package com.giffing.easyxml.jdom2.writer;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stax.StAXSource;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.support.StAXStreamProcessor;
import org.jdom2.transform.JDOMResult;

import com.giffing.easyxml.context.ParseContext;
import com.giffing.easyxml.jdom2.writer.context.Jdom2WriterContext;
import com.giffing.easyxml.jdom2.writer.processor.CustomStAXStreamProcessor;
import com.giffing.easyxml.reader.item.ItemReader;
import com.giffing.easyxml.stax.reader.context.StaxTransformerResult;
import com.giffing.easyxml.util.ReaderToWriter;

public class Writer {

	private InputStream inputStream;

	private OutputStream outputStream;
	
	private ParseContext parseContext = new ParseContext();

	private String namespace = "";

	private List<Jdom2ItemWriter> itemWriter = new ArrayList<>();
	
	private List<ItemReader<XMLStreamReader, Void>> staxItemReaders = new ArrayList<>();

	public void writeAll()
		throws XMLStreamException,
		Exception {

		// Assert.notNull(documentFileResult);
		// Assert.notNull(outputFile);
		// Assert.notNull(packetDirection);

		XMLInputFactory xif = XMLInputFactory.newInstance();
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		TransformerFactory tf = TransformerFactory.newInstance();
		StAXStreamProcessor processor = new CustomStAXStreamProcessor(namespace);

		XMLStreamReader xsr = null;
		XMLStreamWriter writer = null;

		xsr = xif.createXMLStreamReader(inputStream, "UTF-8");

		writer = outputFactory.createXMLStreamWriter(outputStream, "UTF-8");

		ReaderToWriter readToWrite = new ReaderToWriter();
		readToWrite.setStreamWriter(writer);

		xsr.nextTag();

		Transformer t = tf.newTransformer();
		List<String> currentElementPath = new ArrayList<>();

		while (xsr.hasNext()) {
			if (xsr.isEndElement()) {
				if (currentElementPath.size() > 0)
					currentElementPath.remove(currentElementPath.size() - 1);
			}
			if (xsr.isStartElement()) {
				String localName = xsr.getLocalName();
				currentElementPath.add(localName);
				boolean handled = false;
				String joinPath = StringUtils.join(currentElementPath, "/");
				parseContext.setPath(joinPath);
				
				for (ItemReader<XMLStreamReader, Void> staxItemReader : staxItemReaders) {
					if(staxItemReader.shouldHandle(parseContext)) {
						staxItemReader.read(new StaxTransformerResult(xsr).getContent());
					}
				}
				
				for (Jdom2ItemWriter itemWriter : this.itemWriter) {
					if (itemWriter.shouldHandle(parseContext)) {
						JDOMResult result = new JDOMResult();
						t.transform(new StAXSource(xsr), result);
						if(!itemWriter.shouldRemove()) {
							Document document = result.getDocument();
							Element rootElement = document.getRootElement();
	
							Jdom2WriterContext context = new Jdom2WriterContext(parseContext, rootElement);
							
							itemWriter.handle(context);
							processor.process(writer, Format.getPrettyFormat(), rootElement);
						}
						handled = true;
						break;
					}
				}
				if (handled) {
					if (currentElementPath.size() > 0)
						currentElementPath.remove(currentElementPath.size() - 1);
				} else {
					readToWrite.write(xsr);
				}
			} else {
				readToWrite.write(xsr);
			}
			if (xsr.hasNext()) {
				try {
					xsr.next();
				} catch (Exception e) {
					throw e;
				}
			}
		}
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public List<Jdom2ItemWriter> getItemWriter() {
		return itemWriter;
	}

	public void setItemWriter(List<Jdom2ItemWriter> itemWriter) {
		this.itemWriter = itemWriter;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public List<ItemReader<XMLStreamReader, Void>> getStaxItemReaders() {
		return staxItemReaders;
	}

	public void setStaxItemReaders(List<ItemReader<XMLStreamReader, Void>> staxItemReaders) {
		this.staxItemReaders = staxItemReaders;
	}
	
	public void setParseContext(ParseContext parseContext) {
		this.parseContext = parseContext;
	}

}
