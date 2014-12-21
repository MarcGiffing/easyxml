package easyxml.jaxb;

import easyxml.reader.item.ItemReader;

public interface JaxbItemReader<T, R> extends ItemReader<T, R> {
	Class getJaxbClass();
}
