package easyxml.jaxb.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.giffing.easyxml.jaxb.JaxbItemReaderBuilder;
import com.giffing.easyxml.jaxb.JaxbReaderBuilder;
import com.giffing.easyxml.reader.Parser;

import generated.easyxml.example.domain.Note;
import generated.easyxml.example.domain.ObjectFactory;
import org.junit.jupiter.api.Test;

class NoteReaderGeneratedTest {

	@Test
	void foo() throws Exception {

		try (InputStream inputStream = Files.newInputStream(new File("src/main/resources/note2.xml").toPath())) {

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
