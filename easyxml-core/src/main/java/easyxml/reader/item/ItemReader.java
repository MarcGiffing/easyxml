package easyxml.reader.item;

import easyxml.context.ParseContext;

public interface ItemReader<T, R> {

	/**
	 * @param transformResult
	 * @return
	 */
	R read(T t);

	/**
	 * Indicates if the reader is responsible for the current context
	 * 
	 * @param contxt
	 * @return
	 */
	boolean shouldHandle(ParseContext contxt);

}
