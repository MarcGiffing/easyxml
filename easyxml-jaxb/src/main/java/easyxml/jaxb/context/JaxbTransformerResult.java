package easyxml.jaxb.context;

import javax.xml.bind.JAXBElement;

import easyxml.context.TransformResult;

public class JaxbTransformerResult<R> implements TransformResult<JAXBElement<R>> {

	private final JAXBElement<R> element;

	public JaxbTransformerResult(JAXBElement<R> element) {
		this.element = element;
	}

	@Override
	public JAXBElement<R> getContent() {
		return element;
	}

}
