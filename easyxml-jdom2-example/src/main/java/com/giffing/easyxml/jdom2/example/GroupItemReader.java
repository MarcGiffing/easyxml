package com.giffing.easyxml.jdom2.example;

import javax.xml.stream.XMLStreamReader;

import com.giffing.easyxml.context.ParseContext;
import com.giffing.easyxml.reader.item.ItemReader;

public class GroupItemReader implements ItemReader<XMLStreamReader, Void> {
	
	private NoteContext context;
	
	@Override
	public boolean shouldHandle(ParseContext context) {
		this.context = (NoteContext) context;
		return context.getPath().equals("notes/group");
	}
	
	@Override
	public Void read(XMLStreamReader t) {
		Long groupId = Long.valueOf(t.getAttributeValue(null, "id"));
		this.context.latestGroupId = groupId;
		System.out.println(groupId);
		return null;
	}

}
