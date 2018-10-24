package org.luvx.inter;

/**
 * AB#$C%D:反转字母顺序,符号顺序不变
 * 前提知识:
 * 小写字母:97~122
 * 大写字母:65~90
 * 符号:#$%(35~37)
 */
public class Q1 {
    /**
     * 假设字符串中仅有大写字母和上述3中字符
     *
     * @param str
     * @return
     */
    public static String reverse(String str) {
        char[] array = str.toCharArray();
        int length = array.length;
        for (int i = 0, j = length - 1; i < j; ) {
            char left = array[i];
            char right = array[j];
            if (isCapital(left) && isCapital(right)) {
                char temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            } else if (!isCapital(left)) {
                i++;
            } else if (!isCapital(right)) {
                j--;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(array[i]);
        }

        return sb.toString();
    }

    public static boolean isCapital(char c) {
        return (int) c >= 65 && (int) c <= 90;
    }


    public static void main(String[] args) {
        String str = "AB#$C%D";
        System.out.println(reverse(str));
    }
}
