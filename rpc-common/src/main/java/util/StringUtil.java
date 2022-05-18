package util;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/12 17:13
 * @description:判断字符串是否有字符
 */
public class StringUtil {

    public static boolean isBlank(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
