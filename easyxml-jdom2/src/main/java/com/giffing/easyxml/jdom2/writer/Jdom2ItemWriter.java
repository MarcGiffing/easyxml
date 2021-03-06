package com.giffing.easyxml.jdom2.writer;

import com.giffing.easyxml.context.ParseContext;
import com.giffing.easyxml.jdom2.writer.context.Jdom2WriterContext;

public interface Jdom2ItemWriter<T extends ParseContext> {

	boolean shouldHandle(T parseContext);

	void handle(Jdom2WriterContext context);

	boolean shouldRemove(Jdom2WriterContext context);
	
}
