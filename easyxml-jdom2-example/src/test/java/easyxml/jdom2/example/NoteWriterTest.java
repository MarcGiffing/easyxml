package easyxml.jdom2.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.stream.XMLStreamException;

import org.jdom2.Element;
import org.junit.Test;

import com.giffing.easyxml.jdom2.example.GroupItemReader;
import com.giffing.easyxml.jdom2.example.NoteContext;
import com.giffing.easyxml.jdom2.writer.Jdom2ItemWriterBuilder;
import com.giffing.easyxml.jdom2.writer.Jdom2WriterBuilder;
import com.giffing.easyxml.jdom2.writer.Writer;

public class NoteWriterTest {

	@Test
	public void foo() throws XMLStreamException, Exception {
		try (
			InputStream inputStream = new FileInputStream(new File("src/main/resources/note.xml"));
			OutputStream outputStream = new FileOutputStream(new File("target/note.xml"))) {
			Writer writer = Jdom2WriterBuilder.writer()
				.setInputStream(inputStream)
				.setOutputStream(outputStream)
				.setParseContext(new NoteContext())
				.addStaxItemReader(new GroupItemReader())
				.addItemWriter(
					new Jdom2ItemWriterBuilder()
						.shouldHandle(p -> p.getPath().equals("notes/group/note"))
						.withFunction((c) -> {
							NoteContext noteContext = (NoteContext) c.getContext();
							Element contentElement = c.getElement().getChild("content");
							contentElement.setText("content: " + contentElement.getText()  + " group " + noteContext.latestGroupId);
						})
						.build())

				.build();

			writer.writeAll();
		}
	}
}
