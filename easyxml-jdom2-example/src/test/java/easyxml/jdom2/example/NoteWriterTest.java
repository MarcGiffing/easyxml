package easyxml.jdom2.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.stream.XMLStreamException;

import org.jdom2.Element;
import org.junit.Test;

import easyxml.jdom2.writer.Jdom2ItemWriterBuilder;
import easyxml.jdom2.writer.Jdom2WriterBuilder;
import easyxml.jdom2.writer.Writer;

public class NoteWriterTest {

	@Test
	public void foo() throws XMLStreamException, Exception {
		try (
			InputStream inputStream = new FileInputStream(new File("src/main/resources/note.xml"));
			OutputStream outputStream = new FileOutputStream(new File("target/note.xml"))) {
			Writer writer = Jdom2WriterBuilder.writer()
				.setInputStream(inputStream)
				.setOutputStream(outputStream)
				.addItemWriter(
					new Jdom2ItemWriterBuilder()
						.shouldHandle("notes/note")
						.withFunction((c) -> {
							Element contentElement = c.getElement().getChild("content");
							contentElement.setText("content: " + contentElement.getText());
						})
						.build())

				.build();

			writer.writeAll();
		}
	}
}
