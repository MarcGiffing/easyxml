package com.giffing.easyxml.jdom2.writer;

import java.util.function.Consumer;
import java.util.function.Function;

import com.giffing.easyxml.context.ParseContext;
import com.giffing.easyxml.jdom2.writer.context.Jdom2WriterContext;

public class Jdom2ItemWriterBuilder<T extends ParseContext> {

	protected Function<T, Boolean> shouldHandle;
	
	protected boolean shouldRemove;

	protected Consumer<Jdom2WriterContext> consumer;

	private Jdom2ItemWriterBuilder() {};
	
	public static <T extends ParseContext>Jdom2ItemWriterBuilder<T> newBuilder() {
		return new Jdom2ItemWriterBuilder<>();
	}
	
	public Jdom2ItemWriterBuilder<T> shouldHandle(Function<T, Boolean> shouldHandle) {
		this.shouldHandle = shouldHandle;
		return this;
	}

	public Jdom2ItemWriterBuilder<T> shouldHandle(T parseContext) {
		return this.shouldHandle((p) -> p.getPath().equals(parseContext.getPath()));
	}
	
	public Jdom2ItemWriterBuilder<T> shouldRemove(boolean remove) {
		this.shouldRemove = remove;
		return this;
	}
	
	public Jdom2ItemWriterBuilder<T> remove() {
		this.shouldRemove = true;
		return this;
	}
	
	public Jdom2ItemWriterBuilder<T> handle(Consumer<Jdom2WriterContext> consumer) {
		this.consumer = consumer;
		return this;
	}

	public Jdom2ItemWriter<T> build() {
		return new Jdom2ItemWriter<T>() {

			@Override
			public void handle(Jdom2WriterContext context) {
				consumer.accept(context);
			}

			@Override
			public boolean shouldHandle(T parseContext) {
				return shouldHandle.apply(parseContext);
			}

			@Override
			public boolean shouldRemove(Jdom2WriterContext context) {
				return shouldRemove;
			}
		};
	}

}
