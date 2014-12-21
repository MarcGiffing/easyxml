package easyxml.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import easyxml.context.TransformContext;
import easyxml.context.TransformResult;
import easyxml.jaxb.context.JaxbTransformerResult;
import easyxml.reader.Reader;
import easyxml.reader.item.ItemReader;

public class JaxbReader<R> implements Reader<JAXBElement<R>, R> {

	private List<ItemReader<JAXBElement<R>, R>> readers = new ArrayList<>();

	private Unmarshaller jaxbUnmarshaller;

	public JaxbReader(List<ItemReader<JAXBElement<R>, R>> readers, String jaxbContextPath) {
		this.readers = readers;
		try {
			jaxbUnmarshaller = JAXBContext.newInstance(jaxbContextPath).createUnmarshaller();
		} catch (JAXBException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public List<ItemReader<JAXBElement<R>, R>> getItemReaders() {
		return readers;
	}

	@Override
	public TransformResult<JAXBElement<R>> transform(TransformContext<JAXBElement<R>, R> context) {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = null;
		XMLStreamReader streamReader = context.getStreamReader();
		JaxbItemReader<JAXBElement<R>, R> itemReader = (JaxbItemReader<JAXBElement<R>, R>) context
			.getItemReader();
		try {
			t = tf.newTransformer();
			JAXBElement<R> jaxbElement;
			try {
				jaxbElement = jaxbUnmarshaller.unmarshal(streamReader, itemReader.getJaxbClass());
			} catch (JAXBException e) {
				throw new IllegalStateException(e);
			}
			return new JaxbTransformerResult<R>(jaxbElement);
		} catch (TransformerException e) {
			throw new IllegalStateException(e);
		}
	}
}
