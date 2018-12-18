package com.happy.ui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.happy.common.Constants;
import com.sun.awt.AWTUtilities;

public class SplashFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    // 背景图片
    private JLabel bgJLabel;
    private String title;// 标题

    public SplashFrame() {
	init();
	// 初始化组件
	initComponent();
	AWTUtilities.setWindowOpaque(this, false);// 设置窗体透明
    }

    private void init() {
	// 设置窗口监听
	this.addWindowListener(new java.awt.event.WindowAdapter() {
	    public void windowClosing(java.awt.event.WindowEvent evt) {
		System.exit(0);
	    }
	});
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 使用
							    // System.exit(0)方法退出应用程序。仅在应用程序中使用。
	title = Constants.APPTITLE;
	this.setTitle(title);
	this.setUndecorated(true);// 禁用任何边框和标题栏,必须在setVisible之前被执行
	// this.setAlwaysOnTop(true);
	// 读取数据失败，设置基本的窗口数据
	Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
	this.setSize(screenDimension.width / 3, screenDimension.height / 2);
	// this.setLocation(Constants.mainFramelocaltionX,
	// Constants.mainFramelocaltionY);
	this.setLocationRelativeTo(null);// 窗口将置于屏幕的中央
	// 状态栏图标
	String iconNamePath = Constants.PATH_ICON + File.separator + Constants.iconName;
	this.setIconImage(new ImageIcon(iconNamePath).getImage());
    }

    private void initComponent() {
	this.getContentPane().setLayout(null);// 控件进行自由（按照点位置）进行布局
	bgJLabel = new JLabel(getBackgroundImageIcon());// 把背景图片显示在一个标签里面
	// 把标签的大小位置设置为图片刚好填充整个面板
	bgJLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
	this.getContentPane().add(bgJLabel);
    }

    // 获取背景图片
    private ImageIcon getBackgroundImageIcon() {
	String backgroundPath = Constants.PATH_SPLASH + File.separator + "splash_bg.png";
	ImageIcon background = new ImageIcon(backgroundPath);// 背景图片
	background.setImage(
		background.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH));
	// Image.SCALE_SMOOTH的缩略算法
	// 生成缩略图片的平滑度的
	// 优先级比速度高
	// 生成的图片质量比较好
	// 但速度慢
	return background;
    }
}
