package com.happy.util;

import java.awt.Font;
import java.io.File;

//字体处理类
public class FontsUtil {
    // 根据字体文件获取字体
    public static Font getFontByFile(String filePath, int fontStyle, int fontSize) {
	Font font = null;
	try {
	    font = Font.createFont(Font.TRUETYPE_FONT, new File(filePath));
	    font = font.deriveFont(fontStyle, fontSize);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	if (font == null) {
	    font = getBaseFont(fontSize);
	}
	return font;
    }

    // 获取默认字体
    public static Font getBaseFont(int fontSize) {
	return new Font("微软雅黑", Font.PLAIN, fontSize);
    }
}