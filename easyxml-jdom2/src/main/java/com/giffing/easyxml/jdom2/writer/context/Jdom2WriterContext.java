package com.giffing.easyxml.jdom2.writer.context;

import org.jdom2.Element;

import com.giffing.easyxml.context.ParseContext;

public class Jdom2WriterContext {

	private Element element;
	private ParseContext context;

	public Jdom2WriterContext(ParseContext context, Element element) {
		this.setContext(context);
		this.setElement(element);
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public ParseContext getContext() {
		return context;
	}

	public void setContext(ParseContext context) {
		this.context = context;
	}

}
