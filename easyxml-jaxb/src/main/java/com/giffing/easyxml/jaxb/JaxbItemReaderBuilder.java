package com.giffing.easyxml.jaxb;

import java.util.function.Function;

import javax.xml.bind.JAXBElement;

import com.giffing.easyxml.context.ParseContext;
import com.giffing.easyxml.reader.item.ItemReaderBuilder;

public class JaxbItemReaderBuilder<R> extends ItemReaderBuilder<JAXBElement<R>, R> {

	private Class<?> jaxbClass;

	public JaxbItemReaderBuilder() {
		super.handle(e -> e.getValue());
	}

	@Override
	public JaxbItemReaderBuilder<R> shouldHandle(
		Function<ParseContext, Boolean> shouldHandle) {
		return (JaxbItemReaderBuilder<R>) super.shouldHandle(shouldHandle);
	}

	@Override
	public JaxbItemReaderBuilder<R> shouldHandle(String path) {
		return (JaxbItemReaderBuilder<R>) super.shouldHandle(path);
	}

	@Override
	public JaxbItemReaderBuilder<R> handle(
		Function<JAXBElement<R>, R> function) {
		return (JaxbItemReaderBuilder<R>) super.handle(function);
	}

	public static <R> JaxbItemReaderBuilder<R> itemReader() {
		return new JaxbItemReaderBuilder<>();
	}

	public JaxbItemReaderBuilder<R> withJaxbClass(Class<?> jaxbClass) {
		this.jaxbClass = jaxbClass;
		return this;
	}

	@Override
	public JaxbItemReader<JAXBElement<R>, R> build() {
		return new JaxbItemReader<JAXBElement<R>, R>() {

			@Override
			public R read(JAXBElement<R> element) {
				return function.apply(element);
			}

			@Override
			public boolean shouldHandle(ParseContext context) {
				return shouldHandle.apply(context);
			}

			@Override
			public Class<?> getJaxbClass() {
				return jaxbClass;
			}

		};
	}

}
