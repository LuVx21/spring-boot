package org.luvx.api.string;

/**
 * string -> int
 * asiii:0~9(48~57)
 * char -> int: Character.getNumericValue(c);
 * int -> char: Character.forDigit(i, 10);
 */
public class StringToInt {

    /**
     * @param str
     * @return
     */
    public static int parseInt(String str) {
        char[] chars = str.toCharArray();
        int length = chars.length;
        int result = 0;

        char firstChar = chars[0];
        int index = (firstChar == '-' || firstChar == '+') ? 1 : 0;

        for (; index <= length - 1; index++) {
            char current = chars[index];
            if ((int) current < 48 || (int) current > 57) {
                throw new NumberFormatException(index + ":" + chars[index]);
            }

            result += Character.getNumericValue(current) * Math.pow(10, length - index - 1);
        }
        return firstChar == '-' ? 0 - result : result;
    }

    public static void main(String[] args) {
        System.out.println(parseInt("123"));
        System.out.println(parseInt("+123"));
        System.out.println(parseInt("-123"));
        System.out.println(parseInt("00123"));
    }
}