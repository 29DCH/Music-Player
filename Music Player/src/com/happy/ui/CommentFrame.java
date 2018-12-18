package com.happy.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.happy.common.Constants;
import com.happy.manage.MediaManage;
import com.happy.model.Category;
import com.happy.model.SongInfo;

import java.util.Date;
import java.text.SimpleDateFormat;

public class CommentFrame extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    public static JFrame frame;
    public static JLabel jb, jb1, jb2, jb3;
    public static JButton cbtn;
    public static int counter = 0;
    public static int flag = 0;
    public static int y = 0;
    public static JTextArea jtf,jtf1;
    String commenticonPath = Constants.PATH_ICON + File.separator + "commentbtn.png";
    ImageIcon comment = new ImageIcon(commenticonPath);
    private JList<String> list;
    private static Vector<String> v = new Vector<String>();
    private static JPanel[] panels = new JPanel[100];
    
    private void JList(Vector<String> listData)
    {
	
    }

    
    public void initialize() {
	String iconPath = Constants.PATH_ICON + File.separator;
	// String backgroundPath = Constants.PATH_BACKGROUND + File.separator;
	String windowiconIconPath = iconPath + "ic_launcher.png";
	ImageIcon windowicon = new ImageIcon(windowiconIconPath);
	// String bkiconIconPath1 = backgroundPath + "13.jpg";
	// ImageIcon bkicon1 = new ImageIcon(bkiconIconPath1);
	int currentPIndex = MediaManage.getMediaManage().getPindex();
	int currentSIndex = MediaManage.getMediaManage().getSindex();
	List<Category> categorys = MediaManage.getMediaManage().getmCategorys();
	Category category = categorys.get(currentPIndex);
	List<SongInfo> songInfos = category.getmCategoryItem();
	SongInfo songInfo = songInfos.get(currentSIndex);
	final String mvpath = songInfo.getDisplayName();
	frame = new JFrame("评论音乐" + "  " + mvpath);
	frame.setIconImage(windowicon.getImage());
	frame.setBounds(775, 100, 820, 820);
	// frame.getContentPane().setBackground(new Color(0, 0, 0, 0));
	frame.getContentPane().setLayout(null);
	jb = new JLabel();
	jb.setFont(new Font("微软雅黑", Font.PLAIN, 20));
	jb.setText("听友评论");
	jb.setBounds(10, 10, 120, 20);
	String count = String.valueOf(counter);
	jb1 = new JLabel();
	jb1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
	jb1.setText("(已有" + count + "条评论)");
	jb1.setBounds(130, 15, 300, 15);
	frame.getContentPane().add(jb);
	frame.getContentPane().add(jb1);
	jtf = new JTextArea();
	jtf.setOpaque(false);
	jtf.setText("点击发表评论");
	jtf.setLineWrap(true);// 如果内容过长,自动换行
	// jtf.setBounds(160, 60, 300, 70);
	jtf.addKeyListener(new KeyAdapter() {
	    public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {

		}
	    }
	});
	jtf.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseClicked(java.awt.event.MouseEvent e) {
		jtf.setText("");
	    }
	});
	// jtf.addActionListener(this);
	JScrollPane jsp1 = new JScrollPane(jtf);
	jsp1.setOpaque(false);
	jsp1.setBackground(new Color(0, 0, 0, 0));
	jsp1.setBounds(160, 60, 300, 70);
	// 分别设置水平和垂直滚动条自动出现
	jsp1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	jsp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	// frame.getContentPane().add(jtf);
	frame.getContentPane().add(jsp1);
	cbtn = new JButton();
	cbtn.setIcon(comment);
	cbtn.setBounds(500, 80, 80, 30);
	cbtn.setContentAreaFilled(false);
	cbtn.setFocusPainted(false);
	cbtn.setBorderPainted(false);
	cbtn.setToolTipText("点击评论完成");
	cbtn.addActionListener(this);
	frame.getContentPane().add(cbtn);
	jb2 = new JLabel();
	jb2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
	jb2.setText("精彩评论");
	jb2.setBounds(10, 135, 80, 20);
	frame.getContentPane().add(jb2);
//	list =new JList<String>();
//	JScrollPane jsp = new JScrollPane();
//	jsp.setViewportView(list);
	//jsp.setBounds(0, 0, 750, 750);
//	frame.getContentPane().add(jsp);
	//list.setListData(v);
	//显示评论框
	jtf1= new JTextArea();
	jtf1.setLineWrap(true);
	JScrollPane jsp2 = new JScrollPane(jtf1);	
	jsp2.setBounds(2,160, 796, 580);
	frame.getContentPane().add(jsp2);
     

	JButton button = new JButton("返回");
	button.setBounds(720, 750, 80, 23);
	button.setFont(new Font("微软雅黑", Font.PLAIN, 15));
	frame.getContentPane().add(button);
	button.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		frame.dispose();
	    }
	});
	// JLabel pic = new JLabel();
	// pic.setIcon(bkicon1);
	// pic.setBounds(0, 0, 820, 820);
	// frame.getContentPane().add(pic);
	frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
	if (flag == 0) {
	    if (e.getSource() == jtf) {
		jtf.setText(""); // 在文本框按下回车键，将文本框内容清空
	    }
	    flag++;
	}
	if (e.getSource() == cbtn) {
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式 
	    jtf1.setText(df.format(new Date())+"\r\n"+jtf.getText());
//	    label1[counter].setText(df.format(new Date()));
//	    label1[counter].setBounds(10, 140 + 25 * y, 200, 15);
//	    label2[counter].setText(jtf.getText());
//	    label2[counter].setBounds(10, 160 + 25 * y, 790, 20);
//	    frame.getContentPane().add(label1[counter]);
//	    frame.getContentPane().add(label2[counter]);
	    //v.add(df.format(new Date()));
	        JScrollPane jsp2 = new JScrollPane(jtf1);	
	        jsp2.setBounds(2,160, 796, 580);
		frame.getContentPane().add(jsp2);
		String count = String.valueOf(counter);
		jb1.setText("(已有" + count + "条评论)");
		jb1.setBounds(130, 15, 300, 15);
		frame.getContentPane().add(jb);
	    counter++;
	  //  y++;
	}
    }
}