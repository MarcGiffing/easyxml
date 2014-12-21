package easyxml.reader;

import java.util.List;

import easyxml.context.TransformContext;
import easyxml.context.TransformResult;
import easyxml.reader.item.ItemReader;

public interface Reader<T, R> {

	List<ItemReader<T, R>> getItemReaders();

	TransformResult<T> transform(TransformContext<T, R> transformContext);

}
