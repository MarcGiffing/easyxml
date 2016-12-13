package easyxml.stax.reader.context;

import javax.xml.stream.XMLStreamReader;

import easyxml.context.TransformResult;

public class StaxTransformerResult implements TransformResult<XMLStreamReader> {

	private XMLStreamReader reader;

	public StaxTransformerResult(XMLStreamReader reader){
		this.reader = reader;
	}
	
	@Override
	public XMLStreamReader getContent() {
		return reader;
	}

}
