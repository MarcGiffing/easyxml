package com.giffing.easyxml.jdom2.writer;

import java.util.function.Consumer;
import java.util.function.Function;

import com.giffing.easyxml.context.ParseContext;
import com.giffing.easyxml.jdom2.writer.context.Jdom2WriterContext;

public class Jdom2ItemWriterBuilder {

	protected Function<ParseContext, Boolean> shouldHandle;
	
	protected boolean shouldRemove;

	protected Consumer<Jdom2WriterContext> consumer;

	public Jdom2ItemWriterBuilder shouldHandle(Function<ParseContext, Boolean> shouldHandle) {
		this.shouldHandle = shouldHandle;
		return this;
	}

	public Jdom2ItemWriterBuilder shouldHandle(ParseContext parseContext) {
		return this.shouldHandle((p) -> p.getPath().equals(parseContext.getPath()));
	}
	
	public Jdom2ItemWriterBuilder shouldRemove(boolean remove) {
		this.shouldRemove = remove;
		return this;
	}
	
	public Jdom2ItemWriterBuilder remove() {
		this.shouldRemove = true;
		return this;
	}
	
	public Jdom2ItemWriterBuilder handle(Consumer<Jdom2WriterContext> consumer) {
		this.consumer = consumer;
		return this;
	}

	public Jdom2ItemWriter build() {
		return new Jdom2ItemWriter() {

			@Override
			public void handle(Jdom2WriterContext context) {
				consumer.accept(context);
			}

			@Override
			public boolean shouldHandle(ParseContext parseContext) {
				return shouldHandle.apply(parseContext);
			}

			@Override
			public boolean shouldRemove(Jdom2WriterContext context) {
				return shouldRemove;
			}
		};
	}

}
