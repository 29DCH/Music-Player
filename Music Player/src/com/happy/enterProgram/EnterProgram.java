package com.happy.enterProgram;

import java.awt.Font;
import java.util.Enumeration;//枚举数据集合的方法
//Swing事件调度线程(Event DispatchThread，EDT)
import javax.swing.SwingUtilities;//使事件派发线程上的可运行对象排队
import javax.swing.SwingWorker;//程序能启动一个任务线程来异步查询，并马上返回EDT线程，允许EDT继续执行后续的UI事件,管理任务线程和EDT之间的交互
import javax.swing.UIManager;//Swing界面管理核心，管理Swing应用程序样式
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.FontUIResource;

import com.happy.common.Constants;
import com.happy.manage.MediaManage;
import com.happy.service.MediaPlayerService;
import com.happy.util.DataUtil;
import com.happy.util.FontsUtil;
import com.happy.ui.MainFrame;
import com.happy.ui.SplashFrame;

public class EnterProgram {
    // 程序入口
    // 应用启动窗口
    private static SplashFrame splashFrame;
    // 主窗口
    private static MainFrame mainFrame;

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
	    IllegalAccessException, UnsupportedLookAndFeelException {
	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());// 把外观设置成你所使用的平台的外观,也就是你这个程序在哪个平台运行,显示的窗口,对话框外观将是哪个平台的外观
	initGlobalFont(FontsUtil.getBaseFont(Constants.APPFONTSIZE));
	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		splashFrame = new SplashFrame();
		splashFrame.setVisible(true);
		init();
	    }
	});
    }

    // 匿名方法new出线程,只有doInBackground()方法,和普通的Thread一样,publish方法,通知
    // process方法处理相关的界面更新
    // 当前线程,在此线程上调用execute()方法
    // Worker线程：在此线程上调用doInBackground()方法。所有后台活动都应该在此线程上发生
    // 事件指派线程：所有与 Swing有关的活动都在此线程上发生。SwingWorker调用 process和 done()方法
    // 你要强行结束一个SwingWorker可以用cancel方法
    protected static void init() {
	new SwingWorker<Void, Void>() {
	    @Override
	    protected Void doInBackground() {
		initGlobalFont(FontsUtil.getBaseFont(Constants.APPFONTSIZE));
		// 先初始化数据
		DataUtil.init();
		// 初始化播放列表数据
		MediaManage.getMediaManage().initPlayListData();
		// 初始化播放器服务
		MediaPlayerService.getMediaPlayerService().init();
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
			mainFrame = new MainFrame();
			splashFrame.setVisible(false);
			mainFrame.setVisible(true);
		    }
		});
		return null;
	    }

	    @Override
	    protected void done() {
	    }
	}.execute();// execute方法是异步执行，它立即返回到调用者。在execute方法执行后，EDT立即继续执行
    }

    // 统一设置字体，父界面设置之后，所有由父界面进入的子界面都不需要再次设置字体,从父窗口触发打开的所有子窗口都有效
    private static void initGlobalFont(Font font) {
	FontUIResource fontRes = new FontUIResource(font);
	for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
	    Object key = keys.nextElement();
	    Object value = UIManager.get(key);
	    if (value instanceof FontUIResource) {
		UIManager.put(key, fontRes);
	    }
	}
    }

    // public static void initGlobalFontSetting(String fontName, int style, int
    // size) {
    // Font fnt = new Font(fontName, style, size);
    // FontUIResource fontRes = new FontUIResource(fnt);
    // for (Enumeration keys = UIManager.getDefaults().keys();
    // keys.hasMoreElements();) {
    // Object key = keys.nextElement();
    // Object value = UIManager.get(key);
    // if (value instanceof FontUIResource)
    // UIManager.put(key, fontRes);
    // }
    // }
    // initGlobalFontSetting("微软雅黑", Font.Plain, 14);
}