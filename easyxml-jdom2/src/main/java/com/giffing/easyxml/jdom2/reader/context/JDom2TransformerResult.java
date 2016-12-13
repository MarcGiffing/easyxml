package com.giffing.easyxml.jdom2.reader.context;

import org.jdom2.Element;

import com.giffing.easyxml.context.TransformResult;

public class JDom2TransformerResult implements TransformResult<Element> {
	
	private final Element element;
	
	public JDom2TransformerResult(Element element){
		this.element = element;
	}
	
	@Override
	public Element getContent() {
		return element;
	}

}
