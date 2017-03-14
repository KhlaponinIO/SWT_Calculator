package swt.calculator;

import org.eclipse.swt.widgets.Event;

public class Verifier {
	
	public static void verifyDigits(Event e) {

		String string = e.text;
		char[] chars = new char[string.length()];
		string.getChars(0, chars.length, chars, 0);
		for (int i = 0; i < chars.length; i++) {
			if (!('0' <= chars[i] && chars[i] <= '9' || chars[i] == '.' || chars[i] == '-')) {
				e.doit = false;
				return;
			}
		}
	}
	
	@SuppressWarnings("unused")
	public static boolean isDigit(String value) {
		if (value.isEmpty()) return false;
		try {
			double number = Double.parseDouble(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}