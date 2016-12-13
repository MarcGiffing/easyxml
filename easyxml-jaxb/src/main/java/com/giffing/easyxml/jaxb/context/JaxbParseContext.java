package com.giffing.easyxml.jaxb.context;

import com.giffing.easyxml.context.ParseContext;

public class JaxbParseContext extends ParseContext {

	private Class jaxbClass;

	public Class getJaxbClass() {
		return jaxbClass;
	}

	public void setJaxbClass(Class jaxbClass) {
		this.jaxbClass = jaxbClass;
	}
	
}
