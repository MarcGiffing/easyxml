package easyxml.jdom2.reader;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stax.StAXSource;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.transform.JDOMResult;

import easyxml.context.TransformContext;
import easyxml.context.TransformResult;
import easyxml.jdom2.reader.context.JDom2TransformerResult;
import easyxml.reader.Reader;
import easyxml.reader.item.ItemReader;

public class JDom2Reader<R> implements Reader<Element, R> {

	private List<ItemReader<Element, R>> readers = new ArrayList<>();

	public JDom2Reader(List<ItemReader<Element, R>> readers) {
		this.readers = readers;
	}

	@Override
	public List<ItemReader<Element, R>> getItemReaders() {
		return readers;
	}

	@Override
	public TransformResult<Element> transform(TransformContext<Element, R> transformContext) {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = null;
		try {
			t = tf.newTransformer();
			JDOMResult result = new JDOMResult();
			t.transform(new StAXSource(transformContext.getStreamReader()), result);
			Document document = result.getDocument();
			return new JDom2TransformerResult(document.getRootElement());
		} catch (TransformerException e) {
			throw new IllegalStateException(e);
		}
	}

}
