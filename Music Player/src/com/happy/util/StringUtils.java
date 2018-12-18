package com.happy.util;

//字符串处理类
public class StringUtils {
    public static final String EMPTY = "";
    public static final int INDEX_NOT_FOUND = -1;
    private static final int PAD_LIMIT = 8192;
    private static final String[] PADDING = new String[Character.MAX_VALUE];

    static {
	// space padding is most common, start with 64 chars
	PADDING[32] = "                                                                ";
    }

    public StringUtils() {
	// no init.
    }

    public static boolean isEmpty(String str) {
	return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
	return str != null && str.length() > 0;
    }

    public static boolean isBlank(String str) {
	int strLen;
	if (str == null || (strLen = str.length()) == 0) {
	    return true;
	}
	for (int i = 0; i < strLen; i++) {
	    if ((Character.isWhitespace(str.charAt(i)) == false)) {
		return false;
	    }
	}
	return true;
    }

    public static boolean isNotBlank(String str) {
	int strLen;
	if (str == null || (strLen = str.length()) == 0) {
	    return false;
	}
	for (int i = 0; i < strLen; i++) {
	    if ((Character.isWhitespace(str.charAt(i)) == false)) {
		return true;
	    }
	}
	return false;
    }

    public static String clean(String str) {
	return str == null ? EMPTY : str.trim();
    }

    public static String trim(String str) {
	return str == null ? null : str.trim();
    }

    public static String trimToNull(String str) {
	String ts = trim(str);
	return isEmpty(ts) ? null : ts;
    }

    public static String trimToEmpty(String str) {
	return str == null ? EMPTY : str.trim();
    }

    public static boolean isNumeric(String str) {
	if (str == null) {
	    return false;
	}
	int sz = str.length();
	for (int i = 0; i < sz; i++) {
	    if (Character.isDigit(str.charAt(i)) == false) {
		return false;
	    }
	}
	return true;
    }

    public static boolean isNumericSpace(String str) {
	if (str == null) {
	    return false;
	}
	int sz = str.length();
	for (int i = 0; i < sz; i++) {
	    if ((Character.isDigit(str.charAt(i)) == false) && (str.charAt(i) != ' ')) {
		return false;
	    }
	}
	return true;
    }

    public static String center(String str, int size) {
	return center(str, size, ' ');
    }

    public static String center(String str, int size, char padChar) {
	if (str == null || size <= 0) {
	    return str;
	}
	int strLen = str.length();
	int pads = size - strLen;
	if (pads <= 0) {
	    return str;
	}
	str = leftPad(str, strLen + pads / 2, padChar);
	str = rightPad(str, size, padChar);
	return str;
    }

    public static String center(String str, int size, String padStr) {
	if (str == null || size <= 0) {
	    return str;
	}
	if (isEmpty(padStr)) {
	    padStr = " ";
	}
	int strLen = str.length();
	int pads = size - strLen;
	if (pads <= 0) {
	    return str;
	}
	str = leftPad(str, strLen + pads / 2, padStr);
	str = rightPad(str, size, padStr);
	return str;
    }

    private static String padding(int repeat, char padChar) {
	String pad = PADDING[padChar];
	if (pad == null) {
	    pad = String.valueOf(padChar);
	}
	while (pad.length() < repeat) {
	    pad = pad.concat(pad);
	}
	PADDING[padChar] = pad;
	return pad.substring(0, repeat);
    }

    public static String rightPad(String str, int size) {
	return rightPad(str, size, ' ');
    }

    public static String rightPad(String str, int size, char padChar) {
	if (str == null) {
	    return null;
	}
	int pads = size - str.length();
	if (pads <= 0) {
	    return str;
	}
	if (pads > PAD_LIMIT) {
	    return rightPad(str, size, String.valueOf(padChar));
	}
	return str.concat(padding(pads, padChar));
    }

    public static String rightPad(String str, int size, String padStr) {
	if (str == null) {
	    return null;
	}
	if (isEmpty(padStr)) {
	    padStr = " ";
	}
	int padLen = padStr.length();
	int strLen = str.length();
	int pads = size - strLen;
	if (pads <= 0) {
	    return str;
	}
	if (padLen == 1 && pads <= PAD_LIMIT) {
	    return rightPad(str, size, padStr.charAt(0));
	}

	if (pads == padLen) {
	    return str.concat(padStr);
	} else if (pads < padLen) {
	    return str.concat(padStr.substring(0, pads));
	} else {
	    char[] padding = new char[pads];
	    char[] padChars = padStr.toCharArray();
	    for (int i = 0; i < pads; i++) {
		padding[i] = padChars[i % padLen];
	    }
	    return str.concat(new String(padding));
	}
    }

    public static String leftPad(String str, int size) {
	return leftPad(str, size, ' ');
    }

    public static String leftPad(String str, int size, char padChar) {
	if (str == null) {
	    return null;
	}
	int pads = size - str.length();
	if (pads <= 0) {
	    return str; // returns original String when possible
	}
	if (pads > PAD_LIMIT) {
	    return leftPad(str, size, String.valueOf(padChar));
	}
	return padding(pads, padChar).concat(str);
    }

    public static String leftPad(String str, int size, String padStr) {
	if (str == null) {
	    return null;
	}
	if (isEmpty(padStr)) {
	    padStr = " ";
	}
	int padLen = padStr.length();
	int strLen = str.length();
	int pads = size - strLen;
	if (pads <= 0) {
	    return str;
	}
	if (padLen == 1 && pads <= PAD_LIMIT) {
	    return leftPad(str, size, padStr.charAt(0));
	}

	if (pads == padLen) {
	    return padStr.concat(str);
	} else if (pads < padLen) {
	    return padStr.substring(0, pads).concat(str);
	} else {
	    char[] padding = new char[pads];
	    char[] padChars = padStr.toCharArray();
	    for (int i = 0; i < pads; i++) {
		padding[i] = padChars[i % padLen];
	    }
	    return new String(padding).concat(str);
	}
    }

    public static String defaultString(String str) {
	return str == null ? EMPTY : str;
    }

    public static String defaultString(String str, String defaultStr) {
	return str == null ? defaultStr : str;
    }

    public static String defaultIfEmpty(String str, String defaultStr) {
	return StringUtils.isEmpty(str) ? defaultStr : str;
    }
}
