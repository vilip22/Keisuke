package gui;

import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

public class TextFieldLimit extends PlainDocument {
	
 	/**
	 * @desc Randomly generated serial version UID.
	 */
	private static final long serialVersionUID = 4754982857009432584L;
	
	private int limit;

	TextFieldLimit(int limit) {
		super();
		this.limit = limit;
	}

	public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
		if (str == null) return;

		if ((getLength() + str.length()) <= limit) {
			super.insertString(offset, str, attr);
		}
	}
	
}