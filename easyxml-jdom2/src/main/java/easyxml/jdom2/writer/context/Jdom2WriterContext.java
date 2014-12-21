package easyxml.jdom2.writer.context;

import org.jdom2.Element;

public class Jdom2WriterContext {

	private Element element;

	public Jdom2WriterContext(Element element) {
		this.setElement(element);
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

}
