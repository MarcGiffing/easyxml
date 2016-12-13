package com.giffing.easyxml.jdom2.example;

import org.jdom2.Element;

import com.giffing.easyxml.context.ParseContext;
import com.giffing.easyxml.jdom2.example.domain.Note;
import com.giffing.easyxml.reader.item.ItemReader;

import easyxml.jdom2.example.NoteReaderTest.NoteContext;

public class NoteItemReader implements ItemReader<Element, Note> {

	private NoteContext context;
	
	@Override
	public Note read(Element element) {
		Note note = new Note();

		note.setId(Long.valueOf(element.getChildTextTrim("id")));
		note.setContent(element.getChildTextTrim("content"));
		
		note.setGroupId(context.latestGroupId);
		
		return note;
	}

	@Override
	public boolean shouldHandle(ParseContext context) {
		this.context = (NoteContext) context;
		return context.getPath().equals("notes/group/note");
	}

}
