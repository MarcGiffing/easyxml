package easyxml.jdom2.example;

import org.jdom2.Element;

import easyxml.context.ParseContext;
import easyxml.jdom2.example.NoteReaderTest.NoteContext;
import easyxml.jdom2.example.domain.Note;
import easyxml.reader.item.ItemReader;

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
