package easyxml.context;

@FunctionalInterface
@Deprecated
public interface ParseResult<R> {

	R getContent();
}
