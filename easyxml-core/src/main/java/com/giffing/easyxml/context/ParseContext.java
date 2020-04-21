package com.giffing.easyxml.context;

/**
 * The parse context holds the current state of the parsed context.
 * 
 * It can be extended to hold additional parse information. If you want to
 * parse all note elements but you also need the group id you can use use
 * The {@link ParseContext} to hold these additional information. 
 * 
 * You may can't parse the group directly as a dom element cause there may 
 * be thousands of note elements in each group. You can read the group id with
 * Stax put it in the custom ParseContext and read each dom element
 * 
 * <pre>
 * {@code
 * <notes>
 *     <group id="1">
 *         <note>a text</note>
 *     </group>
 *     <group id="2">
 *         <note>another text</note>
 *     </group>
 * </notes>
 * }
 * </pre>
 */
public class ParseContext {

	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
