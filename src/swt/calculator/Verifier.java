package swt.calculator;

import org.eclipse.swt.widgets.Event;

/**
 * This is the util class that contains the methods for SWTCalculator digits verifications
 * 
 * @author Igor Khlaponin
 */
public class Verifier {

    /**
     * Verifies that we input only digits in the text field
     * 
     * @param e - event that contains text value from the field
     */
    public static void verifyDigits(Event e) {

        // TODO: modify it using Regexp's
        String textValue = e.text;
        char[] chars = new char[textValue.length()];
        textValue.getChars(0, chars.length, chars, 0);
        for (int i = 0; i < chars.length; i++) {
            if (!('0' <= chars[i] && chars[i] <= '9' || chars[i] == '.' || chars[i] == '-')) {
                e.doit = false;
                return;
            }
        }
    }

    /**
     * Check if the received value is digit. Returns <code>true</code> if the String value is digit and
     * <code>false</code> if not.
     * 
     * @param value - input String value
     * @return true if received String value is digit
     */
    @SuppressWarnings("unused")
    public static boolean isDigit(String value) {
        if (value.isEmpty())
            return false;
        try {
            double number = Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Method returns count of digits in the number
     * 
     * @param number - double value of the number
     * @return count of digits in the number
     */
    public static int numLenght(double number) {

        String numberValue = String.valueOf(Math.abs(number));
        if (numberValue.contains(".")) {
            int firstPart = numberValue.substring(0, numberValue.indexOf(".")).length();
            int secondPart = numberValue.substring(numberValue.indexOf(".") + 1).length();
            return firstPart + secondPart;
        } else {
            return numberValue.length();
        }

    }

}
