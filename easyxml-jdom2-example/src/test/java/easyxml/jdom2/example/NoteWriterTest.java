package easyxml.jdom2.example;

import com.giffing.easyxml.jdom2.example.GroupItemReader;
import com.giffing.easyxml.jdom2.example.NoteContext;
import com.giffing.easyxml.jdom2.writer.Jdom2ItemWriterBuilder;
import com.giffing.easyxml.jdom2.writer.Jdom2WriterBuilder;
import com.giffing.easyxml.jdom2.writer.Writer;
import org.jdom2.Element;
import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.nio.file.Files;

class NoteWriterTest {

	@Test
	void foo() throws Exception {
		try (
				InputStream inputStream = Files.newInputStream(new File("src/main/resources/note.xml").toPath());
				OutputStream outputStream = Files.newOutputStream(new File("target/note.xml").toPath())) {
			Writer writer = Jdom2WriterBuilder.<NoteContext>writer()
				.setInputStream(inputStream)
				.setOutputStream(outputStream)
				.setParseContext(new NoteContext())
				.addStaxItemReader(new GroupItemReader())
				.addItemWriter(
						Jdom2ItemWriterBuilder.<NoteContext>newBuilder()
						.shouldHandle(p -> p.getPath().equals("notes/group/note") && p.latestGroupId == 2)
						.remove()
						.build())
				.addItemWriter(
					Jdom2ItemWriterBuilder.<NoteContext>newBuilder()
						.shouldHandle(p -> p.getPath().equals("notes/group/note"))
						.handle((c) -> {
							NoteContext noteContext = (NoteContext) c.getContext();
							Element contentElement = c.getElement().getChild("content", c.getElement().getNamespace());
							contentElement.setText("content: " + contentElement.getText()  + " group " + noteContext.latestGroupId);
						})
						.build())
				.build();

				writer.writeAll();
		}
	}
	
	@Test
	public void weather() throws Exception {
		try (
				InputStream inputStream = Files.newInputStream(new File("src/main/resources/weather.xml").toPath());
				OutputStream outputStream = Files.newOutputStream(new File("target/weather.xml").toPath())) {
			Writer writer = Jdom2WriterBuilder.writer()
				.setInputStream(inputStream)
				.setOutputStream(outputStream)
				.addItemWriter(
					Jdom2ItemWriterBuilder
						.newBuilder()
						.shouldHandle(p -> p.getPath().equals("wx_station_index/station"))
						.handle((c) -> {
							String stationId = c.getElement().getChildTextTrim("station_id");
							 c.getElement().getChild("station_id").setText("whoohooo " + stationId);
						})
						.build())
				.build();

			writer.writeAll();
		}
	}
	
}
