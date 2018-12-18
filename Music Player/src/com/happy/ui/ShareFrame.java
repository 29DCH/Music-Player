package com.happy.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.happy.common.Constants;
import com.happy.manage.MediaManage;
import com.happy.model.Category;
import com.happy.model.SongInfo;

public class ShareFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    public JFrame frame;

    public void initialize() {
	String iconPath = Constants.PATH_ICON + File.separator;
	String windowiconIconPath = iconPath + "ic_launcher.png";
	ImageIcon windowicon = new ImageIcon(windowiconIconPath);
	String bkiconIconPath = iconPath + "sharebkimg.png";
	ImageIcon bkicon = new ImageIcon(bkiconIconPath);
	String qqiconIconPath = iconPath + "QQ空间.png";
	ImageIcon qqicon = new ImageIcon(qqiconIconPath);
	String dbiconIconPath = iconPath + "豆瓣.png";
	ImageIcon dbicon = new ImageIcon(dbiconIconPath);
	String rriconIconPath = iconPath + "renren.png";
	ImageIcon rricon = new ImageIcon(rriconIconPath);
	String tencentblogiconIconPath = iconPath + "腾讯微博.png";
	ImageIcon tencentblog = new ImageIcon(tencentblogiconIconPath);
	String sinaiconIconPath = iconPath + "新浪微博.png";
	ImageIcon sinaicon = new ImageIcon(sinaiconIconPath);
	int currentPIndex = MediaManage.getMediaManage().getPindex();
	int currentSIndex = MediaManage.getMediaManage().getSindex();
	List<Category> categorys = MediaManage.getMediaManage().getmCategorys();
	Category category = categorys.get(currentPIndex);
	List<SongInfo> songInfos = category.getmCategoryItem();
	SongInfo songInfo = songInfos.get(currentSIndex);
	final String mvpath = songInfo.getDisplayName();
	frame = new JFrame("一键分享音乐" + "  " + mvpath);
	frame.setIconImage(windowicon.getImage());
	frame.setBounds(500, 200, 495, 300);
	frame.getContentPane().setBackground(Color.WHITE);
	frame.getContentPane().setLayout(null);
	JButton btnNewButton = new JButton();// qq空间
	btnNewButton.setIcon(qqicon);
	btnNewButton.setBounds(89, 45, 70, 70);
	// btnNewButton.setFont(new java.awt.Font("宋体", 2, 20));
	frame.getContentPane().add(btnNewButton);
	btnNewButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		BareBonesBrowserLaunch.openURL(
			"http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url=http://music.163.com/&title="+mvpath+"分享到QQ空间");
	    }
	});
	JButton btnNewButton_1 = new JButton();// 豆瓣
	btnNewButton_1.setIcon(dbicon);
	btnNewButton_1.setBounds(292, 45, 70, 70);
	frame.getContentPane().add(btnNewButton_1);
	btnNewButton_1.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		BareBonesBrowserLaunch
			.openURL("http://shuo.douban.com/!service/share?href=http://music.163.com/&name="+mvpath+"分享到豆瓣");
	    }
	});
	JButton btnNewButton_2 = new JButton();// 人人网
	btnNewButton_2.setIcon(rricon);
	btnNewButton_2.setBounds(21, 134, 70, 70);
	frame.getContentPane().add(btnNewButton_2);
	btnNewButton_2.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		BareBonesBrowserLaunch.openURL(
			"http://widget.renren.com/dialog/share?url=http://music.163.com/&amp;title="+mvpath+"分享到人人网&amp;pic=http://music.163.com/wp-content/uploads/file/jimixiu-pi.jpg&amp;description="+mvpath+"分享的详细描述");
	    }
	});
	JButton btnNewButton_3 = new JButton();// 腾讯
	btnNewButton_3.setBounds(185, 134, 70, 70);
	btnNewButton_3.setIcon(tencentblog);
	frame.getContentPane().add(btnNewButton_3);
	btnNewButton_3.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		BareBonesBrowserLaunch.openURL(
			"http://share.v.t.qq.com/index.php?c=share&amp;a=index&amp;url=http://music.163.com/&amp;title="+mvpath+"分享到腾讯微博&amp;");
	    }
	});
	JButton btnNewButton_4 = new JButton();// 新浪
	btnNewButton_4.setBounds(349, 134, 70, 70);
	btnNewButton_4.setIcon(sinaicon);
	frame.getContentPane().add(btnNewButton_4);
	btnNewButton_4.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		BareBonesBrowserLaunch.openURL(
			"http://v.t.sina.com.cn/share/share.php?url=http://music.163.com/index.html&amp;title="+mvpath+"分享到新浪微博&amp;");
	    }
	});

	// 返回
	JButton button = new JButton("返回");
	button.setBounds(394, 228, 80, 23);
	button.setFont(new java.awt.Font("宋体", 2, 20));
	frame.getContentPane().add(button);
	button.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		// Community a = new Community();
		frame.dispose();
	    }
	});

	JLabel pic = new JLabel();
	pic.setIcon(bkicon);
	pic.setBounds(1, -155, 750, 600);
	frame.getContentPane().add(pic);
	frame.setVisible(true);
    }
}
