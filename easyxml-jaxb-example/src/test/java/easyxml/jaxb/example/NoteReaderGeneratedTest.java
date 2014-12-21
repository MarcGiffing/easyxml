package easyxml.jaxb.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import easyxml.jaxb.JaxbItemReaderBuilder;
import easyxml.jaxb.JaxbReaderBuilder;
import easyxml.reader.Parser;
import generated.easyxml.example.domain.Note;
import generated.easyxml.example.domain.ObjectFactory;

public class NoteReaderGeneratedTest {

	@Test
	public void foo() throws Exception {

		try (InputStream inputStream = new FileInputStream(new File("src/main/resources/note2.xml"))) {

			Parser<JAXBElement<Note>, Note> parser = JaxbReaderBuilder
				.<Note> reader(ObjectFactory.class.getPackage().getName())
				.setInputStream(inputStream)
				.addItemReader(
					new JaxbItemReaderBuilder<Note>()
						.withJaxbClass(Note.class)
						.shouldHandle((p) -> p.getPath().equals("notes/note"))
						.build()
				)
				.build();

			for (Note note : parser) {
				System.out.println(ToStringBuilder.reflectionToString(note, ToStringStyle.SHORT_PREFIX_STYLE));
				System.out.println("Free memory (mb): " +
					Runtime.getRuntime().freeMemory() / 1_000_000);
			}
		}

	}
}
