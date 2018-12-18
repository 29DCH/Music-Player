package com.happy.widget.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import com.happy.common.Constants;
import com.happy.manage.MediaManage;
import com.happy.model.Category;
import com.happy.model.EventIntent;
import com.happy.model.SongInfo;
import com.happy.model.SongMessage;
import com.happy.observable.ObserverManage;
import com.happy.ui.CommentFrame;
import com.happy.ui.MvFrame;
import com.happy.ui.ShareFrame;
import com.happy.util.MediaUtils;
import com.happy.widget.button.BaseButton;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

//歌曲列表item面板
public class ListViewItemComItemPanel extends JPanel {
    private static final long serialVersionUID = -3285370418675829685L;
    // 歌曲信息
    private SongInfo songInfo;
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
    // 鼠标经过
    private boolean isEnter = false;
    // 双选
    private boolean isDoubSelect = false;
    // 单选
    private boolean isSingleSelect = false;
    // 标题标签
    private JLabel titleNameJLabel;
    // 播放列表索引
    private int pindex = 0;
    // 歌曲列表索引
    private int sindex = 0;
    // 歌曲名称
    private JLabel songName;
    // 歌曲长度
    private JLabel songSize;
    // 播放进度
    private JLabel songProgress;
    // 歌手图片
    private JLabel singerIconLabel;
    // 删除按钮
    private BaseButton delButton;
    // 播放MV按钮
    private BaseButton mvButton;
    // 喜欢按钮
    private BaseButton is_likeButton;
    // 不喜欢按钮
    private BaseButton isnot_likeButton;
    // 收藏按钮
    private BaseButton collectionButton;
    // 分享按钮
    private BaseButton shareButton;
    // 下载按钮
    private BaseButton downloadButton;
    // 播放歌曲按钮
    private BaseButton playButton;
    // 暂停歌曲按钮
    private BaseButton pauseButton;
    // 评论按钮
    private BaseButton commentButton;
    private JPanel listViewPanel;
    // 面板鼠标事件
    private PanelMouseListener panelMouseListener = new PanelMouseListener();
    // 是否进入控件
    private boolean isEnterComponent = false;
    String iconPath = Constants.PATH_ICON + File.separator;
    String is_likeButtonBaseIconPath = iconPath + "like.png";
    String isnot_likeButtonBaseIconPath = iconPath + "click_on_like.png";
    private int counter = 1, counter1 = 1;
    private static int counter2 = 0;
    private static MvFrame frame;
    private static ShareFrame frame1;
    // private static LrcFrame frame2;
    private static CommentFrame frame3;
    private static List<SongInfo> newSongInfos = new ArrayList<SongInfo>();
    private static List<SongInfo> newSongInfos1 = new ArrayList<SongInfo>();
    private static List<SongInfo> newSongInfos2 = new ArrayList<SongInfo>();
    private static int size1 = 2, size2 = 2, size3 = 1;

    public ListViewItemComItemPanel(JPanel mplayListPanel, JPanel mlistViewPanel, int mpindex, int msindex,
	    SongInfo msongInfo, int mWidth) {
	this.pindex = mpindex;
	this.sindex = msindex;
	this.playListPanel = mplayListPanel;
	this.listViewPanel = mlistViewPanel;
	this.width = mWidth;
	this.songInfo = msongInfo;
	this.addMouseListener(panelMouseListener);
	initComponent();
	this.setOpaque(false);
    }

    // 初始化控件
    public void initComponent() {
	this.setLayout(null);

	if (songInfo.getSid().equals(Constants.playInfoID)) {
	    isDoubSelect = true;
	    initDoubSelectedComponent();
	} else {
	    isDoubSelect = false;
	    initDefComponent();
	}
    }

    // 初始化默认布局
    private void initDefComponent() {
	this.removeAll();
	height = defHeight;
	this.setPreferredSize(new Dimension(width, height));
	this.setMaximumSize(new Dimension(width, height));
	this.setMinimumSize(new Dimension(width, height));
	songName = new JLabel(songInfo.getDisplayName());
	songSize = new JLabel(songInfo.getDurationStr());
	songName.setBounds(10, 0, width / 2, height);
	songSize.setBounds(width - 60 - 15, 0, 60, height);
	String iconPath = Constants.PATH_ICON + File.separator;
	String delButtonBaseIconPath = iconPath + "del1.png";
	String delButtonOverIconPath = iconPath + "del2.png";
	String delButtonPressedIconPath = iconPath + "del3.png";
	String is_likeButtonBaseIconPath = iconPath + "like.png";
	String isnot_likeButtonBaseIconPath = iconPath + "click_on _like.png";
	String collectionButtonBaseIconPath = iconPath + "collection.png";
	String downloadButtonBaseIconPath = iconPath + "download.png";
	String mvButtonBaseIconPath = iconPath + "mv.png";
	String playButtonBaseIconPath = iconPath + "play.png";
	String pauseButtonBaseIconPath = iconPath + "pause.png";
	String shareButtonBaseIconPath = iconPath + "share.png";
	String commentButtonBaseIconPath = iconPath + "comment.png";

	delButton = new BaseButton(delButtonBaseIconPath, delButtonOverIconPath, delButtonPressedIconPath,
		defHeight / 2 + 5, defHeight / 2);
	delButton.setBounds(songSize.getX() - (defHeight / 2 + 10) + 50, (defHeight - defHeight / 2) / 2,
		defHeight / 2 + 5, defHeight / 2);
	delButton.setToolTipText("删除");
	delButton.setVisible(false);
	mvButton = new BaseButton(mvButtonBaseIconPath, defHeight / 2 + 5, defHeight / 2);
	mvButton.setBounds(songSize.getX() - (defHeight / 2 + 10) + 20,
		height / 2 + (defHeight - defHeight / 2) / 2 - 2, defHeight / 2 + 5, defHeight / 2);
	mvButton.setToolTipText("播放mv");
	mvButton.setVisible(false);
	if (counter % 2 == 1) {
	    is_likeButton = new BaseButton(is_likeButtonBaseIconPath, defHeight / 2 + 5, defHeight / 2);
	    is_likeButton.setBounds(songSize.getX() - (defHeight / 2 + 10) - 10,
		    height / 2 + (defHeight - defHeight / 2) / 2 - 2, defHeight / 2 + 5, defHeight / 2);
	    is_likeButton.setToolTipText("喜欢");
	    is_likeButton.setVisible(false);
	} else {
	    isnot_likeButton = new BaseButton(isnot_likeButtonBaseIconPath, defHeight / 2 + 5, defHeight / 2);
	    isnot_likeButton.setBounds(songSize.getX() - (defHeight / 2 + 10) - 10,
		    height / 2 + (defHeight - defHeight / 2) / 2 - 2, defHeight / 2 + 5, defHeight / 2);
	    isnot_likeButton.setVisible(false);
	}
	collectionButton = new BaseButton(collectionButtonBaseIconPath, defHeight / 2 + 5, defHeight / 2);
	collectionButton.setBounds(songSize.getX() - (defHeight / 2 + 10) - 40,
		height / 2 + (defHeight - defHeight / 2) / 2 - 2, defHeight / 2 + 5, defHeight / 2);
	collectionButton.setToolTipText("收藏");
	collectionButton.setVisible(false);
	shareButton = new BaseButton(shareButtonBaseIconPath, defHeight / 2 + 5, defHeight / 2);
	shareButton.setBounds(songSize.getX() - (defHeight / 2 + 10) - 70,
		height / 2 + (defHeight - defHeight / 2) / 2 - 2, defHeight / 2 + 5, defHeight / 2);
	shareButton.setToolTipText("分享");
	shareButton.setVisible(false);
	downloadButton = new BaseButton(downloadButtonBaseIconPath, defHeight / 2 + 5, defHeight / 2);
	downloadButton.setBounds(songSize.getX() - (defHeight / 2 + 10) - 100,
		height / 2 + (defHeight - defHeight / 2) / 2 - 2, defHeight / 2 + 5, defHeight / 2);
	downloadButton.setToolTipText("下载");
	downloadButton.setVisible(false);
	commentButton = new BaseButton(commentButtonBaseIconPath, defHeight / 2 + 5, defHeight / 2);
	commentButton.setBounds(songSize.getX() - (defHeight / 2 + 10) - 160,
		height / 2 + (defHeight - defHeight / 2) / 2 - 2, defHeight / 2 + 5, defHeight / 2);
	commentButton.setToolTipText("评论歌曲");
	commentButton.setVisible(false);
	if (counter1 % 2 == 1) {
	    playButton = new BaseButton(playButtonBaseIconPath, defHeight / 2 + 5, defHeight / 2);
	    playButton.setBounds(songSize.getX() - (defHeight / 2 + 10) - 130,
		    height / 2 + (defHeight - defHeight / 2) / 2 - 2, defHeight / 2 + 5, defHeight / 2);
	    playButton.setToolTipText("播放");
	    playButton.setVisible(false);
	} else {
	    pauseButton = new BaseButton(pauseButtonBaseIconPath, defHeight / 2 + 5, defHeight / 2);
	    pauseButton.setBounds(songSize.getX() - (defHeight / 2 + 10) - 130,
		    height / 2 + (defHeight - defHeight / 2) / 2 - 2, defHeight / 2 + 5, defHeight / 2);
	    pauseButton.setToolTipText("暂停");
	    pauseButton.setVisible(false);
	}
	initDelButtonEvent();
	initmvButtonEvent();
	initshareButtonEvent();
	initcollectionButtonEvent();
	initdownloadButtonEvent();
	initcommentButtonEvent();
	this.add(delButton);
	this.add(mvButton);
	if (counter % 2 == 1) {
	    this.add(is_likeButton);
	    initIs_likeButtonEvent();
	} else {
	    this.add(isnot_likeButton);
	    initIsnot_likeButtonEvent();
	}
	if (counter1 % 2 == 1) {
	    this.add(playButton);
	    initplayButtonEvent();
	} else {
	    this.add(pauseButton);
	    initpauseButtonEvent();
	}
	this.add(collectionButton);
	this.add(shareButton);
	this.add(downloadButton);
	this.add(commentButton);
	this.add(songName);
	this.add(songSize);
	playListPanel.updateUI();
    }

    // 初始化双击布局
    private void initDoubSelectedComponent() {
	this.removeAll();
	height = selectHeight;
	this.setPreferredSize(new Dimension(width, height));
	this.setMaximumSize(new Dimension(width, height));
	this.setMinimumSize(new Dimension(width, height));
	songName = new JLabel(songInfo.getDisplayName());
	songSize = new JLabel(songInfo.getDurationStr());
	String progressTime = "00:00";
	SongInfo tempSongInfo = MediaManage.getMediaManage().getSongInfo();
	if (tempSongInfo != null) {
	    progressTime = MediaUtils.formatTime((int) tempSongInfo.getPlayProgress());
	}
	songProgress = new JLabel(progressTime + "/" + songInfo.getDurationStr());
	String singerIconPath = Constants.PATH_ICON + File.separator + "ic_launcher.png";
	ImageIcon singerIcon = new ImageIcon(singerIconPath);
	singerIcon
		.setImage(singerIcon.getImage().getScaledInstance(height * 3 / 4, height * 3 / 4, Image.SCALE_SMOOTH));
	singerIconLabel = new JLabel(singerIcon);
	singerIconLabel.setBounds(10, (height - height * 3 / 4) / 2, height * 3 / 4, height * 3 / 4);
	songName.setBounds(10 + singerIconLabel.getX() + singerIconLabel.getWidth(),
		singerIconLabel.getHeight() / 4 - 5, width - 10, height / 2);
	songSize.setBounds(width - 60 - 15, 0, 60, height);
	songSize.setVisible(false);
	songProgress.setBounds(singerIconLabel.getX() + singerIconLabel.getWidth() + 10, songName.getY() + 25, 100,
		height / 2);
	String iconPath = Constants.PATH_ICON + File.separator;
	String delButtonBaseIconPath = iconPath + "del1.png";
	String delButtonOverIconPath = iconPath + "del2.png";
	String delButtonPressedIconPath = iconPath + "del3.png";
	String is_likeButtonBaseIconPath = iconPath + "like.png";
	String isnot_likeButtonBaseIconPath = iconPath + "click_on _like.png";
	String collectionButtonBaseIconPath = iconPath + "collection.png";
	String downloadButtonBaseIconPath = iconPath + "download.png";
	String mvButtonBaseIconPath = iconPath + "mv.png";
	String playButtonBaseIconPath = iconPath + "play.png";
	String pauseButtonBaseIconPath = iconPath + "pause.png";
	String shareButtonBaseIconPath = iconPath + "share.png";
	String commentButtonBaseIconPath = iconPath + "comment.png";

	delButton = new BaseButton(delButtonBaseIconPath, delButtonOverIconPath, delButtonPressedIconPath,
		defHeight / 2 + 5, defHeight / 2);
	delButton.setBounds(songSize.getX() - (defHeight / 2 + 10) + 50,
		height / 2 + (defHeight - defHeight / 2) / 2 - 2, defHeight / 2 + 5, defHeight / 2);
	delButton.setToolTipText("删除");
	mvButton = new BaseButton(mvButtonBaseIconPath, defHeight / 2 + 5, defHeight / 2);
	mvButton.setBounds(songSize.getX() - (defHeight / 2 + 10) + 20,
		height / 2 + (defHeight - defHeight / 2) / 2 - 2, defHeight / 2 + 5, defHeight / 2);
	mvButton.setToolTipText("播放mv");
	if (counter % 2 == 1) {
	    is_likeButton = new BaseButton(is_likeButtonBaseIconPath, defHeight / 2 + 5, defHeight / 2);
	    is_likeButton.setBounds(songSize.getX() - (defHeight / 2 + 10) - 10,
		    height / 2 + (defHeight - defHeight / 2) / 2 - 2, defHeight / 2 + 5, defHeight / 2);
	    is_likeButton.setToolTipText("喜欢");
	} else {
	    isnot_likeButton = new BaseButton(isnot_likeButtonBaseIconPath, defHeight / 2 + 5, defHeight / 2);
	    isnot_likeButton.setBounds(songSize.getX() - (defHeight / 2 + 10) - 10,
		    height / 2 + (defHeight - defHeight / 2) / 2 - 2, defHeight / 2 + 5, defHeight / 2);
	}
	collectionButton = new BaseButton(collectionButtonBaseIconPath, defHeight / 2 + 5, defHeight / 2);
	collectionButton.setBounds(songSize.getX() - (defHeight / 2 + 10) - 40,
		height / 2 + (defHeight - defHeight / 2) / 2 - 2, defHeight / 2 + 5, defHeight / 2);
	collectionButton.setToolTipText("收藏");
	shareButton = new BaseButton(shareButtonBaseIconPath, defHeight / 2 + 5, defHeight / 2);
	shareButton.setBounds(songSize.getX() - (defHeight / 2 + 10) - 70,
		height / 2 + (defHeight - defHeight / 2) / 2 - 2, defHeight / 2 + 5, defHeight / 2);
	shareButton.setToolTipText("分享");
	downloadButton = new BaseButton(downloadButtonBaseIconPath, defHeight / 2 + 5, defHeight / 2);
	downloadButton.setBounds(songSize.getX() - (defHeight / 2 + 10) - 100,
		height / 2 + (defHeight - defHeight / 2) / 2 - 2, defHeight / 2 + 5, defHeight / 2);
	downloadButton.setToolTipText("下载");
	commentButton = new BaseButton(commentButtonBaseIconPath, defHeight / 2 + 5, defHeight / 2);
	commentButton.setBounds(songSize.getX() - (defHeight / 2 + 10) - 160,
		height / 2 + (defHeight - defHeight / 2) / 2 - 2, defHeight / 2 + 5, defHeight / 2);
	commentButton.setToolTipText("评论歌曲");
	if (counter1 % 2 == 1) {
	    playButton = new BaseButton(playButtonBaseIconPath, defHeight / 2 + 5, defHeight / 2);
	    playButton.setBounds(songSize.getX() - (defHeight / 2 + 10) - 130,
		    height / 2 + (defHeight - defHeight / 2) / 2 - 2, defHeight / 2 + 5, defHeight / 2);
	    playButton.setToolTipText("播放");
	} else {
	    pauseButton = new BaseButton(pauseButtonBaseIconPath, defHeight / 2 + 5, defHeight / 2);
	    pauseButton.setBounds(songSize.getX() - (defHeight / 2 + 10) - 130,
		    height / 2 + (defHeight - defHeight / 2) / 2 - 2, defHeight / 2 + 5, defHeight / 2);
	    pauseButton.setToolTipText("暂停");
	}
	initDelButtonEvent();
	initmvButtonEvent();
	initshareButtonEvent();
	initcollectionButtonEvent();
	initdownloadButtonEvent();
	initcommentButtonEvent();
	this.add(delButton);
	this.add(mvButton);
	if (counter % 2 == 1) {
	    this.add(is_likeButton);
	    initIs_likeButtonEvent();
	} else {
	    this.add(isnot_likeButton);
	    initIsnot_likeButtonEvent();
	}
	if (counter1 % 2 == 1) {
	    this.add(playButton);
	    initplayButtonEvent();
	} else {
	    this.add(pauseButton);
	    initpauseButtonEvent();
	}
	this.add(collectionButton);
	this.add(shareButton);
	this.add(downloadButton);
	this.add(commentButton);
	this.add(singerIconLabel);
	this.add(songName);
	this.add(songSize);
	this.add(songProgress);
	recently_played(pindex, sindex);
	playListPanel.updateUI();
    }

    // 单击
    public void setSingleSelect(boolean isSingleSelect) {
	this.isSingleSelect = isSingleSelect;
	playListPanel.revalidate();
	playListPanel.repaint();
    }

    // 双击
    public void setDoubSelect(boolean isDoubSelect) {
	this.isDoubSelect = isDoubSelect;
	if (isDoubSelect) {
	    isSingleSelect = false;
	    initDoubSelectedComponent();
	} else {
	    isEnter = false;
	    isSingleSelect = false;
	    initDefComponent();
	}
    }

    // 初始化播放按钮事件
    private void initplayButtonEvent() {
	playButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		SongMessage songMessage = new SongMessage();
		songMessage.setSongInfo(songInfo);
		songMessage.setType(SongMessage.PLAYINFOMUSIC);
		// 通知
		ObserverManage.getObserver().setMessage(songMessage);
		counter1++;
	    }
	});
	// 播放按钮鼠标事件
	playButton.addMouseListener(new MouseListener() {

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
		panelMouseListener.mouseClicked(e);
	    }
	});
    }

    // 初始化暂停按钮事件
    private void initpauseButtonEvent() {
	pauseButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		SongMessage songMessage = new SongMessage();
		songMessage.setSongInfo(songInfo);
		songMessage.setType(SongMessage.PAUSEMUSIC);
		// 通知
		ObserverManage.getObserver().setMessage(songMessage);
		counter1++;
	    }
	});
	// 暂停按钮鼠标事件
	pauseButton.addMouseListener(new MouseListener() {

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
		panelMouseListener.mouseClicked(e);
	    }
	});
    }

    // 绘制组件
    public void paintComponent(Graphics g) {
	Graphics2D g2d = (Graphics2D) g;
	// 消除线条锯齿
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	if (isDoubSelect) {
	    g2d.setPaint(new Color(0, 0, 0, 80));
	} else if (isSingleSelect) {
	    g2d.setPaint(new Color(0, 0, 0, 50));
	} else if (isEnter) {
	    g2d.setPaint(new Color(0, 0, 0, 20));
	} else {
	    g2d.setPaint(new Color(0, 0, 0, 0));
	}
	g2d.fillRect(0, 0, width, height);
    }

    // 初始化播放MV按钮事件
    private void initmvButtonEvent() {
	mvButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		// if(RuntimeUtil.isWindows()){}
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),
			"D:\\Program Files\\VideoLAN\\VLC\\sdk\\lib"); // 导入的路径是vlc的安装路径
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
		// System.out.println(LibVlc.INSTANCE.libvlc_get_version());
		EventQueue.invokeLater(new Runnable() {
		    @Override
		    public void run() {
			counter2++;
			SongMessage songMessage = new SongMessage();
			songMessage.setSongInfo(songInfo);
			songMessage.setType(SongMessage.PAUSEMUSIC);
			// 通知
			ObserverManage.getObserver().setMessage(songMessage);
			try {
			    frame = new MvFrame();
			    frame.setVisible(true);
			    List<Category> categorys = MediaManage.getMediaManage().getmCategorys();
			    Category category = categorys.get(pindex);
			    List<SongInfo> songInfos = category.getmCategoryItem();
			    SongInfo songInfo1 = songInfos.get(sindex);
			    String mvpath = songInfo1.getTitle();
			    // frame.getMediaPlayer().playMedia("D:\\CloudMusic\\MV\\"+mvpath+".mp4");
			    // 直接播放视频，参数是视频文件的绝对路径
			    frame.getMediaPlayer().prepareMedia("D:\\CloudMusic\\MV\\" + mvpath + ".mp4"); // 控制播放视频
			    new SwingWorker<String, Integer>() {
				@Override
				protected String doInBackground() throws Exception {
				    while (true) { // 获取视频播放进度并且按百分比显示
					long total = frame.getMediaPlayer().getLength();
					long curr = frame.getMediaPlayer().getTime();
					float percent = (float) curr / total;
					publish((int) (percent * 100));
					Thread.sleep(100);
				    }
				    // return null;
				}

				protected void process(java.util.List<Integer> chunks) {
				    for (int v : chunks) {
					frame.getProgressBar().setValue(v);
				    }
				}
			    }.execute();
			} catch (Exception e) {
			    e.printStackTrace();
			}
		    }
		});
	    }
	});
	// 播放MV按钮鼠标事件
	mvButton.addMouseListener(new MouseListener() {

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
		panelMouseListener.mouseClicked(e);
	    }
	});
    }

    // 初始化收藏按钮事件
    private void initcollectionButtonEvent() {
	collectionButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		new Thread() {
		    @Override
		    public void run() {
			CollectSongBySIndex(pindex, sindex);
		    }
		}.start();
	    }
	});
	// 收藏按钮鼠标事件
	collectionButton.addMouseListener(new MouseListener() {

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
		panelMouseListener.mouseClicked(e);
	    }
	});
    }

    // 初始化下载按钮事件
    private void initdownloadButtonEvent() {
	downloadButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		new Thread() {
		    @Override
		    public void run() {
		    }
		}.start();
	    }
	});
	// 下载按钮鼠标事件
	downloadButton.addMouseListener(new MouseListener() {

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
		panelMouseListener.mouseClicked(e);
	    }
	});
    }

    // 初始化分享按钮事件
    private void initshareButtonEvent() {
	shareButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
			SongMessage songMessage = new SongMessage();
			songMessage.setSongInfo(songInfo);
			songMessage.setType(SongMessage.PAUSEMUSIC);
			// 通知
			ObserverManage.getObserver().setMessage(songMessage);
			frame1 = new ShareFrame();
			frame1.initialize();
		    }
		});
	    }
	});
	// 分享按钮鼠标事件
	shareButton.addMouseListener(new MouseListener() {

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
		panelMouseListener.mouseClicked(e);
	    }

	});
    }

    // 初始化评论按钮事件
    private void initcommentButtonEvent() {
	commentButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		new Thread() {
		    @Override
		    public void run() {
			frame3 = new CommentFrame();
			try {
			    frame3.initialize();
			} catch (Exception e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
		    }
		}.start();
	    }
	});
	// 评论按钮鼠标事件
	commentButton.addMouseListener(new MouseListener() {

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
		panelMouseListener.mouseClicked(e);
	    }
	});
    }

    // 初始化删除按钮事件
    private void initDelButtonEvent() {
	delButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		new Thread() {
		    @Override
		    public void run() {
			int result = JOptionPane.showConfirmDialog(ListViewItemComItemPanel.this, "删除该歌曲?", "确认",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
			    removeSongBySIndex1(pindex, sindex);
			}
		    }
		}.start();
	    }
	});
	// 删除按钮鼠标事件
	delButton.addMouseListener(new MouseListener() {

	    @Override
	    public void mouseReleased(MouseEvent e) {
		panelMouseListener.mouseReleased(e);
	    }

	    @Override
	    public void mousePressed(MouseEvent e) {
		panelMouseListener.mousePressed(e);
	    }

	    @Override
	    public void mouseExited(MouseEvent e) {
		panelMouseListener.mouseExited(e);
		isEnterComponent = false;
	    }

	    @Override
	    public void mouseEntered(MouseEvent e) {
		isEnterComponent = true;
		panelMouseListener.mouseEntered(e);
	    }

	    @Override
	    public void mouseClicked(MouseEvent e) {
		panelMouseListener.mouseClicked(e);
	    }
	});
    }

    // 初始化喜欢按钮事件
    private void initIs_likeButtonEvent() {
	is_likeButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		ImageIcon likeicon = new ImageIcon(isnot_likeButtonBaseIconPath);
		is_likeButton.setIcon(likeicon);
		counter++;
		new Thread() {
		    @Override
		    public void run() {
			IlikeSongBySIndex(pindex, sindex);
		    }
		}.start();
	    }
	});
	// 喜欢按钮鼠标事件
	is_likeButton.addMouseListener(new MouseListener() {

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
		panelMouseListener.mouseClicked(e);
	    }
	});
    }

    // 初始化不喜欢按钮事件
    private void initIsnot_likeButtonEvent() {
	isnot_likeButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		ImageIcon notlikeicon = new ImageIcon(is_likeButtonBaseIconPath);
		isnot_likeButton.setIcon(notlikeicon);
		counter++;
		new Thread() {
		    @Override
		    public void run() {
			removeSongBySIndex2(pindex, sindex);
		    }
		}.start();
	    }
	});
	// 不喜欢按钮鼠标事件
	isnot_likeButton.addMouseListener(new MouseListener() {

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
		panelMouseListener.mouseClicked(e);
	    }
	});
    }

    // 最近播放歌曲
    private void recently_played(int pindex, int sindex) {
	if (pindex == -1 || pindex >= listViewPanel.getComponentCount()) {
	    return;
	}
	ListViewItemPanel itemPanel = (ListViewItemPanel) listViewPanel.getComponent(pindex);
	ListViewItemHeadPanel listViewItemHeadPanel = (ListViewItemHeadPanel) itemPanel.getComponent(0);
	ListViewItemComPanel listViewItemComPanel = (ListViewItemComPanel) itemPanel.getComponent(1);
	if (sindex == -1 || sindex >= listViewItemComPanel.getComponentCount()) {
	    return;
	}
	int currentPIndex = MediaManage.getMediaManage().getPindex();
	int currentSIndex = MediaManage.getMediaManage().getSindex();
	if (pindex == currentPIndex && sindex == currentSIndex) {
	    // 更新数据
	    List<Category> categorys = MediaManage.getMediaManage().getmCategorys();
	    Category category1 = categorys.get(pindex);
	    List<SongInfo> songInfos = category1.getmCategoryItem();
	    for (int i = 0; i < categorys.size(); i++) {
		Category category = categorys.get(i);
		if (category.getmCategoryName().equals("最近播放")) {
		    ListViewItemPanel itemPanel1 = (ListViewItemPanel) listViewPanel.getComponent(i);
		    ListViewItemHeadPanel listViewItemHeadPanel1 = (ListViewItemHeadPanel) itemPanel1.getComponent(0);
		    ListViewItemComPanel listViewItemComPanel1 = (ListViewItemComPanel) itemPanel1.getComponent(1);
		    SongInfo songInfo = songInfos.get(sindex);
		    SongInfo songInfo1 = MediaUtils
			    .getSongInfoByFile("D:\\CloudMusic\\" + songInfo.getDisplayName() + ".mp3");
		    // category.addItem(songInfo);
		    newSongInfos2.add(songInfo1);
		    category.setmCategoryItem(newSongInfos2);
		    categorys.add(i, category);
		    // 添加单首歌曲
		    refreshListViewItemComPanelUI(i, newSongInfos2.size() - 1, listViewItemComPanel1, songInfo1);
		    MediaManage.getMediaManage().setmCategorys(categorys);
		    listViewItemHeadPanel1.getTitleNameJLabel()
			    .setText(category.getmCategoryName() + "[" + size3++ + "]");
		    MediaManage.getMediaManage().upDateSongListData(i, newSongInfos2);
		    break;
		}
	    }
	}
	// 更新ui
	// this.validate();
	// this.repaint();
	// this.updateUI();
	playListPanel.updateUI();
    }

    // 根据播放列表索引和歌曲索引，增添用户喜欢歌曲
    // pindex播放列表索引
    // sindex歌曲索引
    private void IlikeSongBySIndex(int pindex, int sindex) {
	if (pindex == -1 || pindex >= listViewPanel.getComponentCount()) {
	    return;
	}
	ListViewItemPanel itemPanel = (ListViewItemPanel) listViewPanel.getComponent(pindex);
	ListViewItemHeadPanel listViewItemHeadPanel = (ListViewItemHeadPanel) itemPanel.getComponent(0);
	ListViewItemComPanel listViewItemComPanel = (ListViewItemComPanel) itemPanel.getComponent(1);
	if (sindex == -1 || sindex >= listViewItemComPanel.getComponentCount()) {
	    return;
	}
	int currentPIndex = MediaManage.getMediaManage().getPindex();
	int currentSIndex = MediaManage.getMediaManage().getSindex();
	if (pindex == currentPIndex && sindex == currentSIndex) {
	    // 更新数据
	    List<Category> categorys = MediaManage.getMediaManage().getmCategorys();
	    Category category1 = categorys.get(pindex);
	    List<SongInfo> songInfos = category1.getmCategoryItem();
	    for (int i = 0; i < categorys.size(); i++) {
		Category category = categorys.get(i);
		if (category.getmCategoryName().equals("我喜欢的歌")) {
		    ListViewItemPanel itemPanel1 = (ListViewItemPanel) listViewPanel.getComponent(i);
		    ListViewItemHeadPanel listViewItemHeadPanel1 = (ListViewItemHeadPanel) itemPanel1.getComponent(0);
		    ListViewItemComPanel listViewItemComPanel1 = (ListViewItemComPanel) itemPanel1.getComponent(1);
		    SongInfo songInfo = songInfos.get(sindex);
		    SongInfo songInfo1 = MediaUtils
			    .getSongInfoByFile("D:\\CloudMusic\\" + songInfo.getDisplayName() + ".mp3");
		    // category.addItem(songInfo);
		    newSongInfos.add(songInfo1);
		    category.setmCategoryItem(newSongInfos);
		    categorys.add(i, category);
		    // 添加单首歌曲
		    refreshListViewItemComPanelUI(i, newSongInfos.size() - 1, listViewItemComPanel1, songInfo1);
		    MediaManage.getMediaManage().setmCategorys(categorys);
		    listViewItemHeadPanel1.getTitleNameJLabel()
			    .setText(category.getmCategoryName() + "[" + size1++ + "]");
		    MediaManage.getMediaManage().upDateSongListData(i, newSongInfos);
		    break;
		}
	    }
	}
	// 更新ui
	this.validate();
	this.repaint();
	this.updateUI();
	playListPanel.updateUI();
    }

    // 根据播放列表索引和歌曲索引，删除歌曲
    // pindex
    // sindex
    private void removeSongBySIndex1(int pindex, int sindex) {
	if (pindex == -1 || pindex >= listViewPanel.getComponentCount()) {
	    return;
	}
	ListViewItemPanel itemPanel = (ListViewItemPanel) listViewPanel.getComponent(pindex);
	ListViewItemHeadPanel listViewItemHeadPanel = (ListViewItemHeadPanel) itemPanel.getComponent(0);
	ListViewItemComPanel listViewItemComPanel = (ListViewItemComPanel) itemPanel.getComponent(1);
	if (sindex == -1 || sindex >= listViewItemComPanel.getComponentCount()) {
	    return;
	}

	int currentPIndex = MediaManage.getMediaManage().getPindex();
	int currentSIndex = MediaManage.getMediaManage().getSindex();
	if (pindex == currentPIndex && sindex == currentSIndex) {
	    MediaManage.getMediaManage().stopToPlay();
	    MediaManage.getMediaManage().setSongInfo(null);
	    // 当前播放列表含有当前播放的歌曲
	    MediaManage.getMediaManage().setPindex(-1);
	    MediaManage.getMediaManage().setSindex(-1);
	}

	// 更新数据
	List<Category> categorys = MediaManage.getMediaManage().getmCategorys();
	Category category = categorys.get(pindex);
	List<SongInfo> songInfos = category.getmCategoryItem();
	List<SongInfo> newSongInfos = new ArrayList<SongInfo>();
	int size = 0;
	for (int i = 0; i < songInfos.size(); i++) {
	    SongInfo songInfo = songInfos.get(i);
	    if (i == sindex) {
		songInfo.setStatus(SongInfo.DEL);
	    }

	    if (songInfo.getStatus() != SongInfo.DEL) {
		size++;
	    }
	    newSongInfos.add(songInfo);
	}
	category.setmCategoryItem(newSongInfos);
	categorys.remove(pindex);
	categorys.add(pindex, category);
	MediaManage.getMediaManage().setmCategorys(categorys);
	listViewItemHeadPanel.getTitleNameJLabel().setText(category.getmCategoryName() + "[" + size + "]");
	// 更新ui
	this.setVisible(false);
	this.validate();
	this.repaint();
	this.updateUI();
	playListPanel.updateUI();
    }

    // 根据播放列表索引和歌曲索引，增添用户收藏歌曲
    // pindex播放列表索引
    // sindex歌曲索引
    private void CollectSongBySIndex(int pindex, int sindex) {
	if (pindex == -1 || pindex >= listViewPanel.getComponentCount()) {
	    return;
	}
	ListViewItemPanel itemPanel = (ListViewItemPanel) listViewPanel.getComponent(pindex);
	ListViewItemHeadPanel listViewItemHeadPanel = (ListViewItemHeadPanel) itemPanel.getComponent(0);
	ListViewItemComPanel listViewItemComPanel = (ListViewItemComPanel) itemPanel.getComponent(1);
	if (sindex == -1 || sindex >= listViewItemComPanel.getComponentCount()) {
	    return;
	}
	int currentPIndex = MediaManage.getMediaManage().getPindex();
	int currentSIndex = MediaManage.getMediaManage().getSindex();
	if (pindex == currentPIndex && sindex == currentSIndex) {
	    // 更新数据
	    List<Category> categorys = MediaManage.getMediaManage().getmCategorys();
	    Category category1 = categorys.get(pindex);
	    List<SongInfo> songInfos = category1.getmCategoryItem();
	    for (int i = 0; i < categorys.size(); i++) {
		Category category = categorys.get(i);
		if (category.getmCategoryName().equals("收藏列表")) {
		    ListViewItemPanel itemPanel1 = (ListViewItemPanel) listViewPanel.getComponent(i);
		    ListViewItemHeadPanel listViewItemHeadPanel1 = (ListViewItemHeadPanel) itemPanel1.getComponent(0);
		    ListViewItemComPanel listViewItemComPanel1 = (ListViewItemComPanel) itemPanel1.getComponent(1);
		    SongInfo songInfo = songInfos.get(sindex);
		    SongInfo songInfo1 = MediaUtils
			    .getSongInfoByFile("D:\\CloudMusic\\" + songInfo.getDisplayName() + ".mp3");
		    // category.addItem(songInfo);
		    newSongInfos1.add(songInfo1);
		    category.setmCategoryItem(newSongInfos1);
		    categorys.add(i, category);
		    // 添加单首歌曲
		    refreshListViewItemComPanelUI(i, newSongInfos1.size() - 1, listViewItemComPanel1, songInfo1);
		    MediaManage.getMediaManage().setmCategorys(categorys);
		    listViewItemHeadPanel1.getTitleNameJLabel()
			    .setText(category.getmCategoryName() + "[" + size2++ + "]");
		    MediaManage.getMediaManage().upDateSongListData(i, newSongInfos1);
		    break;
		}
	    }
	}
	// 更新ui
	this.validate();
	this.repaint();
	this.updateUI();
	playListPanel.updateUI();
    }

    // 根据播放列表索引和歌曲索引，在喜欢列表里删除歌曲
    // pindex
    // sindex
    private void removeSongBySIndex2(int pindex, int sindex) {
	if (pindex == -1 || pindex >= listViewPanel.getComponentCount()) {
	    return;
	}

	ListViewItemPanel itemPanel = (ListViewItemPanel) listViewPanel.getComponent(pindex);
	ListViewItemHeadPanel listViewItemHeadPanel = (ListViewItemHeadPanel) itemPanel.getComponent(0);
	ListViewItemComPanel listViewItemComPanel = (ListViewItemComPanel) itemPanel.getComponent(1);
	if (sindex == -1 || sindex >= listViewItemComPanel.getComponentCount()) {
	    return;
	}

	int currentPIndex = MediaManage.getMediaManage().getPindex();
	int currentSIndex = MediaManage.getMediaManage().getSindex();
	if (pindex == currentPIndex && sindex == currentSIndex) {
	    // 更新数据
	    List<Category> categorys = MediaManage.getMediaManage().getmCategorys();
	    Category category1 = categorys.get(pindex);
	    List<SongInfo> songInfos1 = category1.getmCategoryItem();
	    List<SongInfo> newSongInfos3 = new ArrayList<SongInfo>();
	    SongInfo songInfo1 = songInfos1.get(sindex);
	    int size = 0;
	    for (int i = 0; i < categorys.size(); i++) {
		Category category = categorys.get(i);
		if (category.getmCategoryName() == "我喜欢的歌") {
		    ListViewItemPanel itemPanel1 = (ListViewItemPanel) listViewPanel.getComponent(i);
		    ListViewItemHeadPanel listViewItemHeadPanel1 = (ListViewItemHeadPanel) itemPanel1.getComponent(0);
		    ListViewItemComPanel listViewItemComPanel1 = (ListViewItemComPanel) itemPanel1.getComponent(1);
		    List<SongInfo> songInfos = category.getmCategoryItem();
		    size = songInfos.size();
		    for (int j = 0; j < songInfos.size(); j++) {
			SongInfo songInfo = songInfos.get(j);
			if (songInfo.getDisplayName().equals(songInfo1.getDisplayName())) {
			    size--;
			} else {
			    newSongInfos3.add(songInfo);
			}
		    }
		    category.setmCategoryItem(newSongInfos3);
		    categorys.remove(i);
		    categorys.add(i, category);
		    MediaManage.getMediaManage().setmCategorys(categorys);
		    listViewItemHeadPanel1.getTitleNameJLabel().setText(category.getmCategoryName() + "[" + size + "]");
		    break;
		}
	    }
	}
	// 更新ui
	this.setVisible(false);
	this.validate();
	this.repaint();
	this.updateUI();
	playListPanel.updateUI();
    }

    // 面板鼠标事件
    class PanelMouseListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
	    if (isEnterComponent) {
		playListPanel.repaint();
	    }
	}

	@Override
	public void mousePressed(MouseEvent e) {
	    if (isEnterComponent) {
		playListPanel.repaint();
		return;
	    }

	    if (e.getClickCount() == 1) {
		// 如果当前歌曲正在播放，则对其它进行暂停操作
		if (Constants.sDoubleClickIndex == sindex && Constants.pDoubleClickIndex == pindex) {
		    if (songInfo.getSid().equals(Constants.playInfoID)) {
			if (MediaManage.getMediaManage().getPlayStatus() == MediaManage.PLAYING) {
			    // 当前正在播放，发送暂停
			    SongMessage msg = new SongMessage();
			    msg.setSongInfo(songInfo);
			    msg.setType(SongMessage.PAUSEMUSIC);
			    ObserverManage.getObserver().setMessage(msg);
			} else {

			    SongMessage songMessage = new SongMessage();
			    songMessage.setType(SongMessage.PLAYMUSIC);
			    // 通知
			    ObserverManage.getObserver().setMessage(songMessage);
			}
		    }
		}
		EventIntent eventIntent = new EventIntent();
		eventIntent.setType(EventIntent.SONGLIST);
		eventIntent.setpIndex(pindex);
		eventIntent.setsIndex(sindex);
		eventIntent.setEventType(EventIntent.SINGLECLICK);
		ObserverManage.getObserver().setMessage(eventIntent);
	    }
	    if (e.getClickCount() == 2) {
		// 如果当前歌曲正在播放，则对其它进行暂停操作
		if (Constants.sDoubleClickIndex == sindex && Constants.pDoubleClickIndex == pindex) {

		    // 双击，播放歌曲
		    if (songInfo.getSid().equals(Constants.playInfoID)) {
			return;
		    }
		} else {
		    EventIntent eventIntent = new EventIntent();
		    eventIntent.setType(EventIntent.SONGLIST);
		    eventIntent.setpIndex(pindex);
		    eventIntent.setsIndex(sindex);
		    eventIntent.setEventType(EventIntent.DOUBLECLICK);
		    ObserverManage.getObserver().setMessage(eventIntent);
		}

		Constants.playInfoID = songInfo.getSid();

		if (MediaManage.getMediaManage().getPindex() != pindex) {

		    // 设置播放时的索引
		    MediaManage.getMediaManage().setPindex(pindex);
		    // 更新歌曲列表
		    MediaManage.getMediaManage().upDateSongListData(pindex);
		}
		MediaManage.getMediaManage().setSindex(sindex);
		// 发送播放
		SongMessage msg = new SongMessage();
		msg.setSongInfo(songInfo);
		msg.setType(SongMessage.PLAYINFOMUSIC);
		ObserverManage.getObserver().setMessage(msg);
	    }
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	    if (isEnterComponent) {
		playListPanel.repaint();
	    }
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	    isEnter = true;
	    if (isEnterComponent) {
		delButton.setVisible(true);
		playListPanel.repaint();

	    } else {
		if (isDoubSelect) {
		    initDoubSelectedComponent();
		} else {
		    delButton.setVisible(true);
		    playListPanel.repaint();
		}
		EventIntent eventIntent = new EventIntent();
		eventIntent.setType(EventIntent.SONGLIST);
		eventIntent.setpIndex(pindex);
		eventIntent.setsIndex(sindex);
		eventIntent.setEventType(EventIntent.ENTERED);
		ObserverManage.getObserver().setMessage(eventIntent);
	    }
	}

	@Override
	public void mouseExited(MouseEvent e) {
	    if (isEnterComponent) {
		isEnter = false;
		playListPanel.repaint();
		if (!isDoubSelect) {
		    delButton.setVisible(false);
		}
	    } else {
		if (isDoubSelect) {
		    initDoubSelectedComponent();
		} else {
		    isEnter = false;
		    delButton.setVisible(false);
		    playListPanel.repaint();
		}
		EventIntent eventIntent = new EventIntent();
		eventIntent.setType(EventIntent.SONGLIST);
		eventIntent.setEventType(EventIntent.EXITED);
		ObserverManage.getObserver().setMessage(eventIntent);
	    }
	}

    }

    // 获取进度条控件
    public JLabel getSongProgress() {
	return songProgress;
    }

    // 获取歌曲信息控件
    public SongInfo getSongInfo() {
	return songInfo;
    }

    // 打开文件
    public static void openVideo() {
	JFileChooser chooser = new JFileChooser();
	int v = chooser.showOpenDialog(null);
	if (v == JFileChooser.APPROVE_OPTION) {
	    File file = chooser.getSelectedFile();
	    frame.getMediaPlayer().playMedia(file.getAbsolutePath());
	}
    }

    // 退出播放
    public static void exit() {
	frame.getMediaPlayer().release();
	System.exit(0);
    }

    // 实现播放按钮的方法
    public static void play() {
	frame.getMediaPlayer().play();
    }

    // 实现暂停按钮的方法
    public static void pause() {
	frame.getMediaPlayer().pause();
    }

    // 实现停止按钮的方法
    public static void stop() {
	frame.getMediaPlayer().stop();
    }

    // 实现点击进度条跳转的方法
    public static void jumpTo(float to) {
	frame.getMediaPlayer().setTime((long) (to * frame.getMediaPlayer().getLength()));
    }

    // 实现控制声音的方法
    public static void setVol(int v) {
	frame.getMediaPlayer().setVolume(v);
    }

    public static int getCounter2() {
	return counter2;
    }

    public static int addCounter2() {
	return counter2++;
    }

    public static int minusCounter2() {
	return counter2--;
    }

    // i
    // j
    // listViewItemComPanel
    // songInfo
    private void refreshListViewItemComPanelUI(int pindex, int sindex, ListViewItemComPanel listViewItemComPanel,
	    SongInfo songInfo) {
	ListViewItemComItemPanel listViewItemComItemPanel = new ListViewItemComItemPanel(playListPanel, listViewPanel,
		pindex, sindex, songInfo, width);
	listViewItemComPanel.add(listViewItemComItemPanel);
    }

    public JLabel getTitleNameJLabel() {
	return titleNameJLabel;
    }
}
