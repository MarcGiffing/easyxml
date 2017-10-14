package com.giffing.easyxml.reader.item;

import java.util.function.Function;

import com.giffing.easyxml.context.ParseContext;

/**
 * Each 
 *
 * @param <T>
 * @param <R>
 */
public abstract class ItemReaderBuilder<T, R> {

	protected Function<ParseContext, Boolean> shouldHandle;

	protected Function<T, R> function;

	public ItemReaderBuilder<T, R> shouldHandle(Function<ParseContext, Boolean> shouldHandle) {
		this.shouldHandle = shouldHandle;
		return this;
	}

	public ItemReaderBuilder<T, R> shouldHandle(String path) {
		return this.shouldHandle((p) -> p.getPath().equals(path));
	}

	public ItemReaderBuilder<T, R> handle(Function<T, R> function) {
		this.function = function;
		return this;
	}

	public ItemReader<T, R> build() {
		return new ItemReader<T, R>() {

			@Override
			public R read(T element) {
				return function.apply(element);
			}

			@Override
			public boolean shouldHandle(ParseContext context) {
				return shouldHandle.apply(context);
			}
		};
	}
}
