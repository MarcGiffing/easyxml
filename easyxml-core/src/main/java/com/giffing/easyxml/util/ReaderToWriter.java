package com.giffing.easyxml.util;

/*   Copyright 2004 BEA Systems, Inc.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import javax.xml.stream.*;

import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;

/**
 * <p>
 * Automatically write a reader.
 * </p>
 */

public class ReaderToWriter {

	private XMLStreamWriter writer;

	public ReaderToWriter() {
	}

	public ReaderToWriter(XMLStreamWriter xmlw) {
		writer = xmlw;
	}

	public void setStreamWriter(XMLStreamWriter xmlw) {
		writer = xmlw;
	}

	public void write(XMLStreamReader xmlr) throws XMLStreamException {
		switch (xmlr.getEventType()) {
		case START_ELEMENT:
			String prefix = xmlr.getPrefix();
			String namespaceURI = xmlr.getNamespaceURI();
			if (namespaceURI != null) {
				if (prefix != null)
					writer.writeStartElement(xmlr.getPrefix(), xmlr.getLocalName(), xmlr.getNamespaceURI());
				else
					writer.writeStartElement(xmlr.getNamespaceURI(), xmlr.getLocalName());
			} else {
				writer.writeStartElement(xmlr.getLocalName());
			}

			for (int i = 0; i < xmlr.getNamespaceCount(); i++) {
				writer.writeNamespace(xmlr.getNamespacePrefix(i), xmlr.getNamespaceURI(i));
			}
			for (int i = 0; i < xmlr.getAttributeCount(); i++) {

				if (xmlr.getAttributePrefix(i).equals("xsi")) {
					writer.writeAttribute(xmlr.getAttributePrefix(i), xmlr.getAttributeNamespace(i), xmlr.getAttributeLocalName(i),
							xmlr.getAttributeValue(i));
				} else {
					writer.writeAttribute(xmlr.getAttributeLocalName(i), xmlr.getAttributeValue(i));
				}
			}

			break;
		case XMLStreamConstants.END_ELEMENT:
			writer.writeEndElement();
			break;
		case XMLStreamConstants.SPACE:
		case XMLStreamConstants.CHARACTERS:
			writer.writeCharacters(xmlr.getTextCharacters(), xmlr.getTextStart(), xmlr.getTextLength());
			break;
		case XMLStreamConstants.PROCESSING_INSTRUCTION:
			writer.writeProcessingInstruction(xmlr.getPITarget(), xmlr.getPIData());
			break;
		case XMLStreamConstants.CDATA:
			writer.writeCData(xmlr.getText());
			break;

		case XMLStreamConstants.COMMENT:
			writer.writeComment(xmlr.getText());
			break;
		case XMLStreamConstants.ENTITY_REFERENCE:
			writer.writeEntityRef(xmlr.getLocalName());
			break;
		case XMLStreamConstants.START_DOCUMENT:
			String encoding = xmlr.getCharacterEncodingScheme();
			String version = xmlr.getVersion();

			if (encoding != null && version != null)
				writer.writeStartDocument(encoding, version);
			else if (version != null)
				writer.writeStartDocument(xmlr.getVersion());
			break;
		case XMLStreamConstants.END_DOCUMENT:
			writer.writeEndDocument();
			break;
		case XMLStreamConstants.DTD:
			writer.writeDTD(xmlr.getText());
			break;
		}
	}

	public XMLStreamWriter writeAll(XMLStreamReader xmlr) throws XMLStreamException {
		while (xmlr.hasNext()) {
			write(xmlr);
			xmlr.next();
		}
		writer.flush();
		return writer;
	}

}
