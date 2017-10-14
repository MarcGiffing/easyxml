package com.giffing.easyxml.context;

/**
 * Holds the result an XML processing. E.g. it could be an XMLStreamReader, JAXB or an JDOM2 element.
 *
 * @param <T>
 */
public interface TransformResult<T> {
	
	/**
	 * @return the current result of an XML transformation
	 */
	T getContent();
	
}
