package com.happy.widget.panel;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.happy.common.Constants;
import com.happy.model.MessageIntent;
import com.happy.observable.ObserverManage;
import com.happy.service.MediaPlayerService;
import com.happy.util.DataUtil;
import com.happy.widget.button.BaseButton;

/**
 * 主菜单面板-最小按钮、最大按钮
 * 
 * 
 * 
 */
public class MainMenuPanel extends JPanel implements Observer {
    /**
     * 
     */
    private static final long serialVersionUID = -3285370418675829685L;

    /**
     * 基本图标路径
     */
    private String iconPath = Constants.PATH_ICON + File.separator;

    /**
     * 左右边距
     */
    private int rightPad = 10;
    /**
     * 皮肤按钮
     */
    private BaseButton skinButton;

    private int buttonSize = 0;

    public MainMenuPanel() {
	initComponent();
	this.setOpaque(false);
	// this.setBackground(Color.red);
	ObserverManage.getObserver().addObserver(this);
    }

    /**
     * 初始化组件
     */
    private void initComponent() {
	this.setLayout(null);

	int mainMenuPanelWidth = Constants.mainFrameWidth - Constants.mainPanelWidth;
	int mainMenuPanelHeight = Constants.mainPanelHeight / 4 / 3;

	// 关闭按钮
	String closeButtonBaseIconPath = iconPath + "close_normal.png";
	String closeButtonOverIconPath = iconPath + "close_hot.png";
	String closeButtonPressedIconPath = iconPath + "close_down.png";

	buttonSize = mainMenuPanelHeight / 5 * 3;

	int buttonY = (mainMenuPanelHeight - buttonSize) / 2;

	BaseButton closeButton = new BaseButton(closeButtonBaseIconPath, closeButtonOverIconPath,
		closeButtonPressedIconPath, buttonSize, buttonSize);
	closeButton.setBounds(mainMenuPanelWidth - buttonSize - rightPad, buttonY, buttonSize, buttonSize);
	closeButton.setToolTipText("关闭");
	closeButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		// 关闭
		MessageIntent messageIntent = new MessageIntent();
		messageIntent.setAction(MessageIntent.FRAME_CLOSE);
		ObserverManage.getObserver().setMessage(messageIntent);
	    }
	});

	// 最小化按钮
	String minButtonBaseIconPath = iconPath + "min_def.png";
	String minButtonOverIconPath = iconPath + "min_hot.png";
	String minButtonPressedIconPath = iconPath + "min_down.png";

	BaseButton minButton = new BaseButton(minButtonBaseIconPath, minButtonOverIconPath, minButtonPressedIconPath,
		buttonSize, buttonSize);
	minButton.setBounds(mainMenuPanelWidth - buttonSize - closeButton.getWidth() - rightPad, buttonY - 5,
		buttonSize, buttonSize);
	minButton.setToolTipText("最小化");
	minButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		// 最小化窗口
		MessageIntent messageIntent = new MessageIntent();
		messageIntent.setAction(MessageIntent.FRAME_MIN);
		ObserverManage.getObserver().setMessage(messageIntent);
	    }
	});

	// 皮肤
	String skinButtonBaseIconPath = iconPath + "skin_normal.png";
	String skinButtonOverIconPath = iconPath + "skin_hot.png";
	String skinButtonPressedIconPath = iconPath + "skin_down.png";

	skinButton = new BaseButton(skinButtonBaseIconPath, skinButtonOverIconPath, skinButtonPressedIconPath,
		buttonSize, buttonSize);
	skinButton.setBounds(minButton.getX() - minButton.getWidth() - rightPad * 2, buttonY, buttonSize, buttonSize);
	skinButton.setToolTipText("皮肤");
	skinButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {

		changeSkinButtonUI(buttonSize);
		MessageIntent messageIntent = new MessageIntent();
		messageIntent.setAction(MessageIntent.OPEN_SKINDIALOG);
		ObserverManage.getObserver().setMessage(messageIntent);
	    }
	});

	this.add(minButton);
	this.add(closeButton);
	this.add(skinButton);
    }

    /**
     * 修改皮肤按钮图标
     * 
     * @param buttonSize
     */
    protected void changeSkinButtonUI(int buttonSize) {
	skinButton.setRolloverIcon(null);
	skinButton.setPressedIcon(null);
    }

    @Override
    public void update(Observable o, final Object data) {
	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		updateUI(data);
	    }
	});
    }

    /**
     * 
     * @param data
     */
    protected void updateUI(Object data) {
	if (data instanceof MessageIntent) {
	    MessageIntent messageIntent = (MessageIntent) data;
	    if (messageIntent.getAction().equals(MessageIntent.CLOSE_SKINDIALOG)) {
		changeDefSkinButtonUI(buttonSize);
	    }
	}
    }

    /**
     * 修改默认图标
     * 
     * @param buttonSize
     */
    private void changeDefSkinButtonUI(int buttonSize) {
	String skinButtonOverIconPath = iconPath + "skin_hot.png";
	String skinButtonPressedIconPath = iconPath + "skin_down.png";

	ImageIcon overIcon = new ImageIcon(skinButtonOverIconPath);
	overIcon.setImage(overIcon.getImage().getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH));

	ImageIcon pressedIcon = new ImageIcon(skinButtonPressedIconPath);
	pressedIcon.setImage(pressedIcon.getImage().getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH));
	skinButton.setRolloverIcon(overIcon);
	skinButton.setPressedIcon(pressedIcon);
    }
}
