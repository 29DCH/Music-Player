package com.happy.ui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Insets;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.happy.common.Constants;
import com.happy.event.PanelMoveFrame;
import com.happy.manage.MakeLrcDialogManage;
import com.happy.manage.MediaManage;
import com.happy.model.Category;
import com.happy.model.EventIntent;
import com.happy.model.MessageIntent;
import com.happy.model.SongInfo;
import com.happy.model.SongMessage;
import com.happy.observable.ObserverManage;
import com.happy.service.MediaPlayerService;
import com.happy.util.DataUtil;
import com.happy.widget.button.BaseButton;
import com.happy.widget.dialog.DesLrcDialog;
import com.happy.widget.dialog.SkinDialog;
import com.happy.widget.dialog.TrayDialog;
import com.happy.widget.panel.MainLrcOperatePanel;
import com.happy.widget.panel.MainLrcPanel;
import com.happy.widget.panel.MainMenuPanel;
import com.happy.widget.panel.MainPanel;
import javax.swing.plaf.basic.BasicComboBoxUI;

// 主界面窗口
public class MainFrame extends JFrame implements Observer, ActionListener, ItemListener {

    private static final long serialVersionUID = 1L;// 序列化运行时使用serialVersionUID与每个可序列化类
    // 相关联，该序列号在反序列化过程中用于验证序列化对象的发送者和接收者是否为该对象加载了与序列化兼容的类
    // 判断是否展开
    private boolean isShow = false;
    // 歌曲信息
    private SongInfo songInfo;
    // 歌曲长度
    private JLabel songSize;
    // 默认高度
    private int defHeight = 40;
    // 点击高度
    private int selectHeight = 60;
    // 高度
    private int height = defHeight;
    // 宽度
    private int width = 0;
    // 播放列表面板
    private JPanel playListPanel;
    // 播放列表索引
    private int pindex = 0;
    // 歌曲列表索引
    private int sindex = 0;
    // 背景图片
    private JLabel bgJLabel;
    private static JButton jb1, jb2, jb3, jb4;
    private String title;// 标题
    // 皮肤窗口
    private SkinDialog skinDialog;
    // 桌面歌词窗口
    protected static DesLrcDialog desktopLrcDialog;
    // 系统托盘
    private TrayIcon trayIcon;
    // 是否支持托盘
    private boolean trayIsSupported = false;
    // 歌曲总进度
    private JLabel songSizeLabel;
    // private MvFrame frame;
    private TrayDialog trayFrame;
    private JTextField txt;
    private int flag = 0;
    String searchiconPath = Constants.PATH_ICON + File.separator + "search.png";
    ImageIcon search = new ImageIcon(searchiconPath);
    // 歌词按钮
    private BaseButton lyricButton;
    private static LrcFrame frame2;
    private String[] s = new String[10];
    private static JComboBox<String> listcomboBox;
    private static JComboBox<String> jbBox;

    public MainFrame() {
	ObserverManage.getObserver().addObserver(this);
	init();
	initDesktopLrcDialog();
	// 初始化组件
	initComponent();
	initSkin();
	initSkinDialogData();
	openDesLrcDialog();
	initTray();
    }

    // 初始化
    private void init() {
	this.addWindowListener(new java.awt.event.WindowAdapter() {
	    public void windowClosing(java.awt.event.WindowEvent evt) {
		close();
	    }
	});
	Font font = new Font("宋体", Font.BOLD, 20);
	// listcomboBox = new JComboBox<String>(s);
	// listcomboBox.setFont(new Font("微软雅黑", Font.PLAIN, 20));
	jbBox = new JComboBox<String>();
	jbBox.setOpaque(false);
	UIManager.put("ComboBox.background", new Color(0, 0, 0, 0));
	// jbBox.setUI(new BasicComboBoxUI() {
	// public void installUI(JComponent jbBox) {
	// super.installUI(jbBox);
	// listBox.setForeground(Color.WHITE);
	// listBox.setSelectionBackground(new Color(0,0,0,0));
	// listBox.setSelectionForeground(Color.BLACK);
	// }
	// protected JButton createArrowButton() {
	// return super.createArrowButton();
	// }
	// });
	jbBox.setBackground(new Color(0, 0, 0, 0));
	jbBox.setFont(new Font("微软雅黑", Font.PLAIN, 15));
	jbBox.setBounds(460, 80, 250, 25);
	List<Category> categorys = MediaManage.getMediaManage().getmCategorys();
	for (int i = 0; i < categorys.size(); i++) {
	    Category category = categorys.get(i);
	    if (!category.getmCategoryName().equals("最近播放") && !category.getmCategoryName().equals("收藏列表")
		    && !category.getmCategoryName().equals("我喜欢的歌")) {
		List<SongInfo> songInfos = category.getmCategoryItem();
		for (int j = 0; j < songInfos.size(); j++) {
		    SongInfo songInfo = songInfos.get(j);
		    jbBox.addItem(songInfo.getDisplayName());
		}
	    }
	}
	jbBox.addItemListener(this);
	JScrollPane jsp = new JScrollPane(jbBox);
	jsp.setOpaque(false);
	jsp.setBackground(new Color(0, 0, 0, 0));
	this.add(jbBox);
	this.add(jsp);
	jb1 = new JButton();
	jb1.setText("登录 ");
	jb2 = new JButton();
	jb3 = new JButton();
	jb4 = new JButton();
	jb3.setText("|注册");
	jb1.setFont(font);
	jb2.setFont(font);
	jb3.setFont(font);
	// jb.setForeground(Color.red);
	jb1.setContentAreaFilled(false);
	// 去掉聚焦线
	jb1.setFocusPainted(false);
	// 边框涂抹
	jb1.setBorderPainted(false);
	jb1.setToolTipText("点击登录");
	this.add(jb1);
	jb1.setBounds(860, 10, 100, 50);
	jb1.addActionListener(this);
	jb2.setContentAreaFilled(false);
	jb2.setFocusPainted(false);
	jb2.setBorderPainted(false);
	this.add(jb2);
	jb2.setBounds(950, 10, 100, 50);
	jb3.setContentAreaFilled(false);
	jb3.setFocusPainted(false);
	jb3.setBorderPainted(false);
	jb3.setToolTipText("点击注册");
	this.add(jb3);
	jb3.setBounds(920, 10, 100, 50);
	jb3.addActionListener(this);
	jb4.setToolTipText("点击搜索");
	jb4.setBounds(735, 23, 23, 23);
	jb4.setIcon(search);
	this.add(jb4);
	jb4.addActionListener(this);
	txt = new JTextField();
	txt.setOpaque(false);
	txt.setBounds(435, 23, 300, 25);
	txt.setText("搜索音乐,视频,歌词,歌手,电台");
	JScrollPane jsp1 = new JScrollPane(txt);
	this.add(jsp1);
	this.add(txt);
	txt.addActionListener(this);
	txt.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseClicked(java.awt.event.MouseEvent e) {
		txt.setText("");
	    }
	});
	txt.addKeyListener(new KeyAdapter() {
	    public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		    List<Category> categorys = MediaManage.getMediaManage().getmCategorys();
		    for (int i = 0; i < categorys.size(); i++) {
			Category category = categorys.get(i);
			List<SongInfo> songInfos = category.getmCategoryItem();
			for (int j = 0; j < songInfos.size(); j++) {
			    SongInfo songInfo = songInfos.get(j);
			    if (songInfo.getTitle().equals(txt.getText())) {
				EventIntent eventIntent2 = new EventIntent();
				eventIntent2.setType(EventIntent.PLAYLIST);
				eventIntent2.setpShowIndex(i);
				eventIntent2.setShow(isShow);
				ObserverManage.getObserver().setMessage(eventIntent2);
				// EventIntent eventIntent1 = new EventIntent();
				// eventIntent1.setType(EventIntent.PLAYLIST);
				// eventIntent1.setpIndex(i);
				// eventIntent1.setsIndex(j);
				// eventIntent1.setEventType(EventIntent.DOUBLECLICK);
				// ObserverManage.getObserver().setMessage(eventIntent1);
				EventIntent eventIntent = new EventIntent();
				eventIntent.setType(EventIntent.SONGLIST);
				eventIntent.setpIndex(i);
				eventIntent.setsIndex(j);
				eventIntent.setEventType(EventIntent.DOUBLECLICK);
				ObserverManage.getObserver().setMessage(eventIntent);
				// 设置播放时的索引
				MediaManage.getMediaManage().setPindex(i);
				// 更新歌曲列表
				MediaManage.getMediaManage().upDateSongListData(i);
				MediaManage.getMediaManage().setSindex(j);
				SongMessage songMessage = new SongMessage();
				songMessage.setSongInfo(songInfo);
				songMessage.setType(SongMessage.PAUSEMUSIC);
				// 通知
				ObserverManage.getObserver().setMessage(songMessage);
				// SongMessage songMessage = new SongMessage();
				// songMessage.setSongInfo(songInfo);
				// songMessage.setType(SongMessage.PLAYINFOMUSIC);
				// // 通知
				// ObserverManage.getObserver().setMessage(songMessage);
				// System.out.println(songInfo.getTitle()+"
				// "+txt.getText());
				break;
			    }
			}
		    }
		}
	    }
	});
	// jb.addActionListener(new ActionListener()
	// {
	// public void actionPerformed(ActionEvent e)
	// {
	// SwingUtilities.invokeLater(new Runnable() {
	// public void run() {
	// LoginInterface loginInterface = new LoginInterface();
	// loginInterface.showv();
	// }
	// });
	// }
	// });
	// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	title = Constants.APPTITLE;
	this.setTitle(title);
	this.setUndecorated(true);// 实现JFrame去默认边框和标题栏,关闭按钮
	// this.setAlwaysOnTop(true);
	this.setSize(Constants.mainFrameWidth, Constants.mainFrameHeight);
	this.setLocation(Constants.mainFramelocaltionX, Constants.mainFramelocaltionY);
	// 状态栏图标
	String iconNamePath = Constants.PATH_ICON + File.separator + Constants.iconName;
	this.setIconImage(new ImageIcon(iconNamePath).getImage());
    }

    public static JButton getButton1() {
	return jb1;
    }

    public static JButton getButton2() {
	return jb2;
    }

    public void actionPerformed(ActionEvent e) {
	if (e.getSource() == jb1 || e.getSource() == jb3) {
	    this.setVisible(false);
	    new Thread() {
		@Override
		public void run() {
		    LoginInterface loginInterface = new LoginInterface();
		    loginInterface.showv();
		}
	    }.start();
	}
	if (flag == 0) {
	    if (e.getSource() == txt) {
		txt.setText(""); // 在文本框按下回车键，将文本框内容清空
	    }
	    flag++;
	}
	// 搜索歌曲
	if (e.getSource() == jb4) {
	    List<Category> categorys = MediaManage.getMediaManage().getmCategorys();
	    for (int i = 0; i < categorys.size(); i++) {
		Category category = categorys.get(i);
		List<SongInfo> songInfos = category.getmCategoryItem();
		for (int j = 0; j < songInfos.size(); j++) {
		    SongInfo songInfo = songInfos.get(j);
		    if (songInfo.getTitle().equals(txt.getText())) {
			// EventIntent eventIntent1 = new EventIntent();
			// eventIntent1.setType(EventIntent.PLAYLIST);
			// eventIntent1.setpIndex(i);
			// eventIntent1.setsIndex(j);
			// eventIntent1.setEventType(EventIntent.DOUBLECLICK);
			// ObserverManage.getObserver().setMessage(eventIntent1);
			EventIntent eventIntent2 = new EventIntent();
			eventIntent2.setType(EventIntent.PLAYLIST);
			eventIntent2.setpShowIndex(i);
			eventIntent2.setShow(isShow);
			ObserverManage.getObserver().setMessage(eventIntent2);
			EventIntent eventIntent = new EventIntent();
			eventIntent.setType(EventIntent.SONGLIST);
			eventIntent.setpIndex(i);
			eventIntent.setsIndex(j);
			eventIntent.setEventType(EventIntent.DOUBLECLICK);
			ObserverManage.getObserver().setMessage(eventIntent);
			// 设置播放时的索引
			MediaManage.getMediaManage().setPindex(i);
			// 更新歌曲列表
			MediaManage.getMediaManage().upDateSongListData(i);
			MediaManage.getMediaManage().setSindex(j);
			SongMessage songMessage = new SongMessage();
			songMessage.setSongInfo(songInfo);
			songMessage.setType(SongMessage.PAUSEMUSIC);
			// 通知
			ObserverManage.getObserver().setMessage(songMessage);
			// SongMessage songMessage = new SongMessage();
			// songMessage.setSongInfo(songInfo);
			// songMessage.setType(SongMessage.PLAYINFOMUSIC);
			// // 通知
			// ObserverManage.getObserver().setMessage(songMessage);
			// System.out.println(songInfo.getTitle()+"
			// "+txt.getText());
			break;
		    }
		}
	    }
	}
    }

    public void itemStateChanged(ItemEvent e) {
	if (e.getStateChange() == ItemEvent.SELECTED) {
	    String s = (String) jbBox.getSelectedItem();
	    List<Category> categorys = MediaManage.getMediaManage().getmCategorys();
	    for (int i = 0; i < categorys.size(); i++) {
		Category category = categorys.get(i);
		List<SongInfo> songInfos = category.getmCategoryItem();
		for (int j = 0; j < songInfos.size(); j++) {
		    SongInfo songInfo = songInfos.get(j);
		    if (songInfo.getDisplayName().equals(s)) {
			// EventIntent eventIntent1 = new EventIntent();
			// eventIntent1.setType(EventIntent.PLAYLIST);
			// eventIntent1.setpIndex(i);
			// eventIntent1.setsIndex(j);
			// eventIntent1.setEventType(EventIntent.DOUBLECLICK);
			// ObserverManage.getObserver().setMessage(eventIntent1);
			EventIntent eventIntent2 = new EventIntent();
			eventIntent2.setType(EventIntent.PLAYLIST);
			eventIntent2.setpShowIndex(i);
			eventIntent2.setShow(isShow);
			ObserverManage.getObserver().setMessage(eventIntent2);
			EventIntent eventIntent = new EventIntent();
			eventIntent.setType(EventIntent.SONGLIST);
			eventIntent.setpIndex(i);
			eventIntent.setsIndex(j);
			eventIntent.setEventType(EventIntent.DOUBLECLICK);
			ObserverManage.getObserver().setMessage(eventIntent);
			// 设置播放时的索引
			MediaManage.getMediaManage().setPindex(i);
			// 更新歌曲列表
			MediaManage.getMediaManage().upDateSongListData(i);
			MediaManage.getMediaManage().setSindex(j);
			SongMessage songMessage = new SongMessage();
			songMessage.setSongInfo(songInfo);
			songMessage.setType(SongMessage.PAUSEMUSIC);
			// 通知
			ObserverManage.getObserver().setMessage(songMessage);
			// SongMessage songMessage = new SongMessage();
			// songMessage.setSongInfo(songInfo);
			// songMessage.setType(SongMessage.PLAYINFOMUSIC);
			// // 通知
			// ObserverManage.getObserver().setMessage(songMessage);
			// System.out.println(songInfo.getTitle()+"
			// "+txt.getText());
			break;
		    }
		}
	    }
	}
    }

    // 初始化系统托盘
    private void initTray() {
	// 判断系统是否支持系统托盘
	if (SystemTray.isSupported()) {
	    trayIsSupported = true;
	    String iconPath = Constants.PATH_ICON + File.separator + "trayIcon.png";
	    SystemTray tray = SystemTray.getSystemTray(); // 创建系统托盘
	    ImageIcon icon = new ImageIcon(iconPath);
	    trayIcon = new TrayIcon(icon.getImage());// 创建trayIcon
	    // 使托盘图片自动调整大小
	    trayIcon.setImageAutoSize(true);
	    trayIcon.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    setAlwaysOnTop(true);//// 设置显示在上面,最前端,这个窗口永远会挡住别的窗口
		    setExtendedState(Frame.NORMAL);// 自动最小化到托盘
		    setAlwaysOnTop(false);
		    setVisible(true);
		}
	    });
	    try {
		tray.add(trayIcon);
		trayIcon.addMouseListener(new MouseListener() {

		    @Override
		    public void mouseReleased(MouseEvent e) {

		    }

		    @Override
		    public void mousePressed(MouseEvent e) {

		    }

		    @Override
		    public void mouseExited(MouseEvent e) {

		    }

		    @Override
		    public void mouseEntered(MouseEvent e) {

		    }

		    @Override
		    public void mouseClicked(MouseEvent e) {
			// 右键
			if (e.getButton() == MouseEvent.BUTTON3) {
			    if (Constants.makeLrcDialogIsShow) {
				return;
			    }
			    if (trayFrame != null) {
				trayFrame.setVisible(false);
				trayFrame.dispose();// 销毁对象,释放非托管资源
			    }
			    trayFrame = new TrayDialog();
			    // 屏幕大小
			    Dimension sd = Toolkit.getDefaultToolkit().getScreenSize();
			    // 除边框(如任务栏)外的屏幕可用大小
			    Insets si = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
			    // x, y为坐标定位
			    int x = e.getX();
			    int y = sd.height - si.bottom - trayFrame.getMHeight();
			    trayFrame.setLocation(x, y);
			    trayFrame.setVisible(true);
			}
		    }
		});
		trayIcon.setToolTip(title);// 显示该控件功能的提示信息
	    } catch (AWTException e) {
		e.printStackTrace();
	    }
	}
    }

    // 初始化组件
    private void initComponent() {
	this.getContentPane().setLayout(null);
	// 主面板
	Constants.mainPanelWidth = Constants.mainFrameWidth / 5 * 2 - 50;
	Constants.mainPanelHeight = Constants.mainFrameHeight;
	MainPanel mainPanel = new MainPanel(this, desktopLrcDialog);
	// 添加拖动事件
	new PanelMoveFrame(mainPanel, this);
	mainPanel.setBounds(0, 0, Constants.mainPanelWidth, Constants.mainPanelHeight);

	// 主菜单面板
	MainMenuPanel mainMenuPanel = new MainMenuPanel();
	new PanelMoveFrame(mainMenuPanel, this);

	int mainMenuPanelWidth = Constants.mainFrameWidth - Constants.mainPanelWidth;
	int mainMenuPanelHeight = Constants.mainPanelHeight / 4 / 3;
	int mainMenuPanelX = Constants.mainPanelWidth;
	int mainMenuPanelY = 0;

	mainMenuPanel.setBounds(mainMenuPanelX, mainMenuPanelY, mainMenuPanelWidth, mainMenuPanelHeight);

	// 歌词面板
	MainLrcPanel mainLrcPanel = new MainLrcPanel(mainMenuPanelWidth,
		Constants.mainFrameHeight - mainMenuPanelHeight, mainPanel, desktopLrcDialog);
	mainLrcPanel.setBounds(mainMenuPanelX, mainMenuPanelHeight + 10, mainMenuPanelWidth - 10,
		Constants.mainFrameHeight - mainMenuPanelHeight - 10);

	// 歌词操作面板
	int loWidht = mainLrcPanel.getWidth() / 8 - 10;
	int loHeight = (loWidht - loWidht / 5 - 9) * 8;
	int loX = Constants.mainFrameWidth - loWidht;
	int loY = mainLrcPanel.getY() + (mainLrcPanel.getHeight() - loHeight) / 2;
	MainLrcOperatePanel mainLrcOperatePanel = new MainLrcOperatePanel(loWidht, loHeight, loX, loY);
	mainLrcOperatePanel.setBounds(loX + mainLrcOperatePanel.getSeekX(), loY, loWidht, loHeight);
	String iconPath = Constants.PATH_ICON + File.separator;
	String lyricButtonBaseIconPath = iconPath + "undeslrc_def.png";
	String lyricButtonOverIconPath = iconPath + "undeslrc_hot.png";
	String lyricButtonPressedIconPath = iconPath + "undeslrc_down.png";
	lyricButton = new BaseButton(lyricButtonBaseIconPath, lyricButtonOverIconPath, lyricButtonPressedIconPath,
		defHeight / 2 + 40, defHeight / 2 + 40);
	lyricButton.setBounds(480, 750, 50, 50);
	lyricButton.setToolTipText("显示歌词");
	initlyricButtonEvent();
	this.add(lyricButton);
	this.getContentPane().add(mainLrcOperatePanel);
	this.getContentPane().add(mainLrcPanel);
	this.getContentPane().add(mainMenuPanel);
	this.getContentPane().add(mainPanel);
	// this.getContentPane().add(frame);
	initlyricButtonEvent();
    }

    // 初始化歌词按钮事件
    private void initlyricButtonEvent() {
	lyricButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		new Thread() {
		    @Override
		    public void run() {
			// frame2 = new LrcFrame();
		    }
		}.start();
	    }
	});
    }

    // 初始化皮肤
    private void initSkin() {

	bgJLabel = new JLabel(getBackgroundImageIcon());// 把背景图片显示在一个标签里面
	// 把标签的大小位置设置为图片刚好填充整个面板
	bgJLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
	this.getContentPane().add(bgJLabel);
    }

    // 初始化皮肤数据
    protected void initSkinDialogData() {
	skinDialog = new SkinDialog();
    }

    // 桌面歌词窗口
    private void initDesktopLrcDialog() {
	desktopLrcDialog = new DesLrcDialog();
    }

    // 获取背景图片
    private ImageIcon getBackgroundImageIcon() {
	String backgroundPath = Constants.PATH_BACKGROUND + File.separator + Constants.backGroundName;
	ImageIcon background = new ImageIcon(backgroundPath);// 背景图片
	background.setImage(
		background.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH));
	return background;
    }

    @Override
    public void update(Observable o, final Object data) {
	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		updateUI(data);
	    }
	});
    }

    // data数据
    protected void updateUI(Object data) {
	if (data instanceof MessageIntent) {
	    MessageIntent messageIntent = (MessageIntent) data;
	    if (messageIntent.getAction().equals(MessageIntent.FRAME_MIN)) {
		this.setExtendedState(Frame.ICONIFIED);
	    } else if (messageIntent.getAction().equals(MessageIntent.FRAME_NORMAL)) {
		if (isVisible()) {
		    if (getState() != Frame.ICONIFIED)
			setVisible(false);
		    else if (getState() == Frame.ICONIFIED) {
			setAlwaysOnTop(true);
			setExtendedState(Frame.NORMAL);
			setAlwaysOnTop(false);
		    } else {
			setExtendedState(Frame.NORMAL);
			setVisible(false);
		    }
		} else {
		    setAlwaysOnTop(true);
		    setExtendedState(Frame.NORMAL);
		    setAlwaysOnTop(false);
		    setVisible(true);
		}
	    } else if (messageIntent.getAction().equals(MessageIntent.OPEN_SKINDIALOG)) {
		onpenSkinDialog();
	    } else if (messageIntent.getAction().equals(MessageIntent.UPDATE_SKIN)) {
		bgJLabel.setIcon(getBackgroundImageIcon());
	    } else if (messageIntent.getAction().equals(MessageIntent.OPENORCLOSEDESLRC)) {
		openDesLrcDialog();
	    } else if (messageIntent.getAction().equals(MessageIntent.CLOSEDESLRC)) {
		// desktopLrcDialog.setVisible(false);
		openDesLrcDialog();
	    } else if (messageIntent.getAction().equals(MessageIntent.FRAME_CLOSE)) {
		close();
	    } else if (messageIntent.getAction().equals(MessageIntent.OPEN_MAKELRCDIALOG)) {
		openMakeLrcDialog();
	    } else if (messageIntent.getAction().equals(MessageIntent.CLOSE_MAKELRCDIALOG)) {
		hideMakeLrcDialog();
	    }
	} else if (data instanceof SongMessage) {
	    SongMessage songMessage = (SongMessage) data;
	    if (songMessage.getType() == SongMessage.INITMUSIC) {
		SongInfo mSongInfo = songMessage.getSongInfo();
		if (mSongInfo != null) {
		    // 更新状态栏的标题
		    // this.setTitle(mSongInfo.getDisplayName());
		    title = mSongInfo.getDisplayName();
		} else {
		    // this.setTitle(Constants.APPTITLE);
		    title = Constants.APPTITLE;
		}
		setTitle(title);
		if (trayIcon != null) {
		    trayIcon.setToolTip(title);
		}
		// this.repaint();
	    }
	}
    }

    // 隐藏制作歌词窗口
    private void hideMakeLrcDialog() {
	MakeLrcDialogManage.hideMakeLrcDialog();
    }

    // 打开制作歌词窗口
    private void openMakeLrcDialog() {
	MakeLrcDialogManage.initMakeLrcDialog();

	int x = this.getX() + (this.getWidth() - MakeLrcDialogManage.getWidth()) / 2;
	int y = this.getY() + (this.getHeight() - MakeLrcDialogManage.getHeight()) / 2;

	MakeLrcDialogManage.showMakeLrcDialog(x, y);
    }

    // 打开皮肤窗口
    private void onpenSkinDialog() {

	int x = this.getX() + (this.getWidth() - skinDialog.getMWidth()) / 2;
	int y = this.getY() + (this.getHeight() - skinDialog.getMHeight()) / 2;

	skinDialog.setLocation(x, y);
	skinDialog.setModal(true);
	skinDialog.setVisible(true);
    }

    // 显示桌面窗口
    private void openDesLrcDialog() {
	if (Constants.showDesktopLyrics) {
	    // 获取屏幕边界
	    Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
	    // 取得底部边界高度，即任务栏高度
	    int taskHeight = screenInsets.bottom;
	    int y = desktopLrcDialog.getmY() - taskHeight - desktopLrcDialog.getmHeight();
	    desktopLrcDialog.setLocation(0, y);
	    desktopLrcDialog.setVisible(true);
	} else {
	    desktopLrcDialog.setVisible(false);
	}
    }

    // 关闭
    protected void close() {
	if (!trayIsSupported) {
	    exit();
	} else {
	    setVisible(false);
	}
    }

    // 退出
    private void exit() {
	// 关闭窗口时，保存数据
	DataUtil.saveData();
	MediaPlayerService.getMediaPlayerService().close();
	System.exit(0);
    }
}