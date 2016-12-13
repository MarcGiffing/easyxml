package easyxml.jaxb.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import com.giffing.easyxml.jaxb.JaxbItemReaderBuilder;
import com.giffing.easyxml.jaxb.JaxbReaderBuilder;
import com.giffing.easyxml.jaxb.example.domain.JaxbNote;
import com.giffing.easyxml.jaxb.example.domain.JaxbSpecialNote;
import com.giffing.easyxml.jaxb.example.domain.ParseNote;
import com.giffing.easyxml.reader.Parser;

public class NoteReaderTest {

	@Test
	public void foo() throws Exception {
		try (InputStream inputStream = new FileInputStream(new File("src/main/resources/note.xml"))) {

			Parser<JAXBElement<ParseNote>, ParseNote> parser = JaxbReaderBuilder
				.<ParseNote> reader(ParseNote.class.getPackage().getName())
				.setInputStream(inputStream)
				.addItemReader(
					JaxbItemReaderBuilder
						.<ParseNote> itemReader()
						.withJaxbClass(JaxbNote.class)
						.shouldHandle("notes/note")
						.build())
				.addItemReader(
					JaxbItemReaderBuilder
						.<ParseNote> itemReader()
						.withJaxbClass(JaxbSpecialNote.class)
						.shouldHandle((p) -> p.getPath().equals("notes/specialNote"))
						.build())
				.build();

			for (ParseNote readNext : parser) {
				System.out.println(ToStringBuilder
					.reflectionToString(
						readNext,
						ToStringStyle.SHORT_PREFIX_STYLE));
				System.out.println("Free memory (mb): " + Runtime.getRuntime().freeMemory() / 1_000_000);
			}
		}

	}
}
