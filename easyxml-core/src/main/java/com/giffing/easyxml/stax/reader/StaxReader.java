package com.giffing.easyxml.stax.reader;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamReader;

import com.giffing.easyxml.context.TransformContext;
import com.giffing.easyxml.context.TransformResult;
import com.giffing.easyxml.reader.Reader;
import com.giffing.easyxml.reader.item.ItemReader;
import com.giffing.easyxml.stax.reader.context.StaxTransformerResult;

public abstract class StaxReader<R> implements Reader<XMLStreamReader, R> {

	private List<ItemReader<XMLStreamReader, R>> readers = new ArrayList<>();
	
	public StaxReader(List<ItemReader<XMLStreamReader, R>> readers) {
		this.readers = readers;
	}

	@Override
	public List<ItemReader<XMLStreamReader, R>> getItemReaders() {
		return readers;
	}

	@Override
	public TransformResult<XMLStreamReader> transform(TransformContext<XMLStreamReader, R> transformContext) {
		return new StaxTransformerResult(transformContext.getStreamReader());
	}

	
}
