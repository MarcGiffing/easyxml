package com.giffing.easyxml.reader;

import java.util.List;

import com.giffing.easyxml.context.TransformContext;
import com.giffing.easyxml.context.TransformResult;
import com.giffing.easyxml.reader.item.ItemReader;

public interface Reader<T, R> {

	List<ItemReader<T, R>> getItemReaders();

	TransformResult<T> transform(TransformContext<T, R> transformContext);

}
