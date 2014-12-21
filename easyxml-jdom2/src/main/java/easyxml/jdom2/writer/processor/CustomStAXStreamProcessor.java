package easyxml.jdom2.writer.processor;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.jdom2.Namespace;
import org.jdom2.output.support.AbstractStAXStreamProcessor;
import org.jdom2.output.support.FormatStack;

public class CustomStAXStreamProcessor extends AbstractStAXStreamProcessor {

	private String namespace = "";

	public CustomStAXStreamProcessor(String namespace) {
		this.namespace = namespace;
	}

	@Override
	protected void printNamespace(final XMLStreamWriter out, final FormatStack fstack, final Namespace ns)
		throws XMLStreamException {
		final String prefix = ns.getPrefix();
		final String uri = ns.getURI();
		// TODO handle namespace
		if (!ns.equals(namespace)) {
			out.writeNamespace(prefix, uri);
		}

	}
}