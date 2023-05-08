package com.giffing.easyxml.jdom2.writer;

import com.giffing.easyxml.context.ParseContext;
import com.giffing.easyxml.jdom2.writer.context.Jdom2WriterContext;
import com.giffing.easyxml.jdom2.writer.processor.CustomStAXStreamProcessor;
import com.giffing.easyxml.reader.item.ItemReader;
import com.giffing.easyxml.stax.reader.context.StaxTransformerResult;
import com.giffing.easyxml.util.ReaderToWriter;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.support.StAXStreamProcessor;
import org.jdom2.transform.JDOMResult;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stax.StAXSource;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class Writer {

	private InputStream inputStream;

	private OutputStream outputStream;
	
	private ParseContext parseContext = new ParseContext();

	private String namespace;
	
	private String encoding = "UTF-8";
	
	private Format format = Format.getPrettyFormat();

	private List<Jdom2ItemWriter> itemWriter = new ArrayList<>();
	
	private List<ItemReader<XMLStreamReader, Void>> staxItemReaders = new ArrayList<>();

	public void writeAll() throws Exception {

		XMLInputFactory xif = XMLInputFactory.newInstance();
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		TransformerFactory tf = TransformerFactory.newInstance();
		StAXStreamProcessor processor = new CustomStAXStreamProcessor(namespace);
		XMLStreamReader xsr = null;
		XMLStreamWriter writer = null;

		xsr = xif.createXMLStreamReader(inputStream, encoding);
		writer = outputFactory.createXMLStreamWriter(outputStream, encoding);
		
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
						Document document = result.getDocument();
						Element rootElement = document.getRootElement();
						rootElement.addNamespaceDeclaration(Namespace.getNamespace(namespace));
						Jdom2WriterContext context = new Jdom2WriterContext(parseContext, rootElement);
						if(!itemWriter.shouldRemove(context)) {
							itemWriter.handle(context);
							processor.process(writer, format, rootElement);
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

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
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

	public Format getFormat() {
		return format;
	}

	public void setFormat(Format format) {
		this.format = format;
	}

	
}
