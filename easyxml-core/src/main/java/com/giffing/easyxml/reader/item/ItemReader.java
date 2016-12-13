package com.giffing.easyxml.reader.item;

import com.giffing.easyxml.context.ParseContext;

public interface ItemReader<T, R> {

	/**
	 * @param transformResult
	 * @return
	 */
	R read(T t);

	/**
	 * Indicates if the reader is responsible for the current context
	 * 
	 * @param context
	 * @return
	 */
	boolean shouldHandle(ParseContext context);

}
