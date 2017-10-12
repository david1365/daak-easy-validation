package model;

import java.io.Serializable;

/**
 * Base interface which will be extended by entities constants.
 *
 */
public interface BaseConstants extends Serializable {

	String ID = "id";
	//String DOT = StringUtil.DOT;
	/**
	 * This property mainly will be used in suggestions of items (in SuggestBox) based on their code and their title or displaying code-title pair in Anchor
	 * title. In displaying items, this separator will separates code and title of each item.
	 */
	String CODE_TITLE_SEPARATOR = " : ";
	/**
	 * see ir.jalal.core.server.gwt.Converter.BASIC_PROPERTIES.
	 */
	String BASIC_PROPERTIES = "/BasicProperties";
	/**
	 * This property is used as separator in treePath attribute of tree-like entities:
	 */
	String TREE_PATH_SEPERATOR = "$";

}
