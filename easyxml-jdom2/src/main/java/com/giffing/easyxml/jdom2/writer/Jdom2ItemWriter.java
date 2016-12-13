package com.giffing.easyxml.jdom2.writer;

import com.giffing.easyxml.jdom2.writer.context.Jdom2WriterContext;

public interface Jdom2ItemWriter {

	boolean shouldHandle(String join);

	void write(Jdom2WriterContext context);

}
