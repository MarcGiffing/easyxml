package com.giffing.easyxml.jaxb;

import com.giffing.easyxml.context.TransformContext;
import com.giffing.easyxml.context.TransformResult;
import com.giffing.easyxml.jaxb.context.JaxbTransformerResult;
import com.giffing.easyxml.reader.Reader;
import com.giffing.easyxml.reader.item.ItemReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;
import java.util.List;

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
		XMLStreamReader streamReader = context.getStreamReader();
		JaxbItemReader<JAXBElement<R>, R> itemReader = (JaxbItemReader<JAXBElement<R>, R>) context
			.getItemReader();
		JAXBElement<R> jaxbElement;
		try {
			jaxbElement = jaxbUnmarshaller.unmarshal(streamReader, itemReader.getJaxbClass());
		} catch (JAXBException e) {
			throw new IllegalStateException(e);
		}
		return new JaxbTransformerResult<>(jaxbElement);
	}
}
