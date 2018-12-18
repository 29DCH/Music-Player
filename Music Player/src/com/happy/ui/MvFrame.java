package com.happy.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.happy.common.Constants;
import com.happy.manage.MediaManage;
import com.happy.model.Category;
import com.happy.model.SongInfo;
import com.happy.widget.panel.ListViewItemComItemPanel;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class MvFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane; // 顶层容器，整个播放页面的容器
    private JMenuBar menuBar; // 菜单栏
    private JMenu mnFile, mnSetting, mnHelp; // 文件菜单
    private JMenuItem mnOpenVideo, mnExit; // 文件菜单子目录，打开视频、退出
    private JPanel panel; // 控制区域容器
    private JProgressBar progress; // 进度条
    private JPanel progressPanel; // 进度条容器
    private JPanel controlPanel; // 控制按钮容器
    private static JButton btnStop, btnPlay, btnPause, btn1, btn2, btn3; // 控制按钮，停止、播放、暂停、窗口最小化、最大化、关闭、还原
    private JLabel jb1;
    private JSlider slider; // 声音控制块
    String iconPath = Constants.PATH_ICON + File.separator;
    String Has_been_praisediconIconPath = iconPath + "Has been praised.png";
    String fabulousiconIconPath = iconPath + "fabulous.png";
    String windowiconIconPath = iconPath + "ic_launcher.png";
    ImageIcon fabulous = new ImageIcon(fabulousiconIconPath);
    ImageIcon Has_been_praised = new ImageIcon(Has_been_praisediconIconPath);
    EmbeddedMediaPlayerComponent playerComponent; // 媒体播放器组件
    private int flag = 1, flag1 = 1;

    // MvFrame构造方法，创建视频播放的主界面
    public MvFrame() {
	int currentPIndex = MediaManage.getMediaManage().getPindex();
	int currentSIndex = MediaManage.getMediaManage().getSindex();
	List<Category> categorys = MediaManage.getMediaManage().getmCategorys();
	Category category = categorys.get(currentPIndex);
	List<SongInfo> songInfos = category.getmCategoryItem();
	SongInfo songInfo = songInfos.get(currentSIndex);
	String mvpath = songInfo.getDisplayName();
	setTitle(mvpath);
	ImageIcon windowicon = new ImageIcon(windowiconIconPath);
	this.setIconImage(windowicon.getImage());
	// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(920, 135, 820, 760);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	contentPane.setLayout(new BorderLayout(0, 0));
	setContentPane(contentPane);
	// 视频播放窗口中的菜单栏
	menuBar = new JMenuBar();
	setJMenuBar(menuBar);
	mnFile = new JMenu("文件"); // 设置菜单名
	menuBar.add(mnFile);
	mnSetting = new JMenu("设置");
	menuBar.add(mnSetting);
	mnHelp = new JMenu("帮助");
	menuBar.add(mnHelp);
	mnOpenVideo = new JMenuItem("打开文件"); // 设置文件菜单子目录打开文件
	mnFile.add(mnOpenVideo);
	mnExit = new JMenuItem("退出"); // 设置文件菜单子目录退出
	mnFile.add(mnExit);
	jb1 = new JLabel();
	if (flag1 % 2 == 1) {
	    init1();
	    initfabulousButtonEvent();
	} else {
	    init2();
	    initnotfabulousButtonEvent();
	}
	// 打开文件
	mnOpenVideo.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		ListViewItemComItemPanel.openVideo();
	    }
	});

	// 退出
	mnExit.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		ListViewItemComItemPanel.exit();
	    }
	});

	// 视频窗口中播放界面部分
	JPanel videoPane = new JPanel();
	contentPane.add(videoPane, BorderLayout.CENTER);
	videoPane.setLayout(new BorderLayout(0, 0));

	playerComponent = new EmbeddedMediaPlayerComponent();
	videoPane.add(playerComponent);

	// 视频窗口中控制部分
	panel = new JPanel(); // 实例化控制区域容器
	videoPane.add(panel, BorderLayout.SOUTH);

	progressPanel = new JPanel(); // 实例化进度条容器
	panel.add(progressPanel, BorderLayout.NORTH);

	// 添加进度条
	progress = new JProgressBar();
	progressPanel.add(progress);
	// panel.add(progress,BorderLayout.NORTH);
	progress.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) { // 点击进度条调整视频播放进度
		int x = e.getX();
		ListViewItemComItemPanel.jumpTo((float) x / progress.getWidth());
	    }
	});
	progress.setStringPainted(true);

	controlPanel = new JPanel(); // 实例化控制按钮容器
	panel.add(controlPanel, BorderLayout.SOUTH);

	// 添加停止按钮
	btnStop = new JButton("停止");
	btnStop.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		ListViewItemComItemPanel.stop();
	    }
	});
	controlPanel.add(btnStop);
	// 添加播放按钮
	btnPlay = new JButton("播放");
	btnPlay.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		ListViewItemComItemPanel.play();
	    }
	});
	if (flag % 2 == 1) {
	    btnPlay.addKeyListener(new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
		    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			ListViewItemComItemPanel.pause();
			flag++;
		    }
		}
	    });
	} else {
	    btnPause.addKeyListener(new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
		    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			ListViewItemComItemPanel.play();
			flag++;
		    }
		}
	    });
	}
	controlPanel.add(btnPlay);

	// 添加暂停按钮
	btnPause = new JButton("暂停");
	btnPause.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		ListViewItemComItemPanel.pause();
	    }
	});
	controlPanel.add(btnPause);

	// 添加声音控制块
	slider = new JSlider();
	slider.setValue(80);
	slider.setMaximum(100);
	slider.addChangeListener(new ChangeListener() {

	    @Override
	    public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		ListViewItemComItemPanel.setVol(slider.getValue());
	    }
	});
	controlPanel.add(slider);
    }

    private void init1() {
	btn1 = new JButton();
	btn1.setContentAreaFilled(false);
	btn1.setFocusPainted(false);
	btn1.setBorderPainted(false);
	btn1.setToolTipText("点赞");
	this.add(btn1);
	btn1.setBounds(32, 712, 44, 27);
	btn1.setIcon(fabulous);
	String s = String.valueOf(ListViewItemComItemPanel.getCounter2());
	jb1.setText(s);
	jb1.setBounds(120, 712, 30, 30);
	this.add(jb1);
    }

    private void init2() {
	btn2 = new JButton();
	btn2.setContentAreaFilled(false);
	btn2.setFocusPainted(false);
	btn2.setBorderPainted(false);
	btn2.setToolTipText("取消点赞");
	this.add(btn2);
	btn2.setBounds(32, 712, 55, 27);
	btn2.setIcon(Has_been_praised);
	String s = String.valueOf(ListViewItemComItemPanel.getCounter2());
	jb1.setText(s);
	jb1.setBounds(120, 712, 30, 30);
	this.add(jb1);
    }

    // 初始化点赞按钮事件
    private void initfabulousButtonEvent() {
	btn1.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		ListViewItemComItemPanel.addCounter2();
		flag1++;
	    }
	});
    }

    // 初始化取消点赞按钮事件
    private void initnotfabulousButtonEvent() {
	btn2.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		ListViewItemComItemPanel.minusCounter2();
		flag1++;
	    }
	});
    }

    // 获取播放媒体实例（某个视频）
    public EmbeddedMediaPlayer getMediaPlayer() {
	return playerComponent.getMediaPlayer();
    }

    // 获取进度条实例
    public JProgressBar getProgressBar() {
	return progress;
    }

}