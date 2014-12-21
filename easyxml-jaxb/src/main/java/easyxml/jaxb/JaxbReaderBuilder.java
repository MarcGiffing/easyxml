package easyxml.jaxb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import easyxml.context.ParseContext;
import easyxml.reader.Parser;

public class JaxbReaderBuilder<R> {

	private JaxbReader<R> reader;

	private Parser<JAXBElement<R>, R> parser;

	private List<JaxbReader<R>> readers = new ArrayList<>();

	private JaxbReaderBuilder() {
	}

	public static <R> JaxbReaderBuilder<R> reader(String jaxbContextPath) {
		JaxbReaderBuilder<R> builder = new JaxbReaderBuilder<R>();
		builder.reader = new JaxbReader<R>(new ArrayList<>(), jaxbContextPath);
		builder.readers.add(builder.reader);
		builder.parser = new Parser<JAXBElement<R>, R>(builder.readers, new ParseContext());
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

	public JaxbReaderBuilder<R> addItemReader(JaxbItemReader<JAXBElement<R>, R> itemReader) {
		this.reader.getItemReaders().add(itemReader);
		return this;
	}

}
