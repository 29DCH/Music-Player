package com.happy.widget.panel;

import javax.swing.JPanel;

import com.happy.common.Constants;
import com.happy.ui.MainFrame;
import com.happy.widget.dialog.DesLrcDialog;

/**
 * 主要面板-播放列表、进度条、上一首、下一首等
 */
public class MainPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = -3285370418675829685L;

    private DesLrcDialog desLrcDialog;

    private MainFrame mainFrame;
    /**
     * 
     */
    private PlayListPanel playListPanel;

    public MainPanel(MainFrame mainFrame, DesLrcDialog mdesktopLrcDialog) {
	this.mainFrame = mainFrame;
	this.desLrcDialog = mdesktopLrcDialog;
	// 初始化组件
	initComponent();
	this.setOpaque(false);
	// this.setBackground(Color.black);
    }

    /**
     * 初始化组件
     */
    private void initComponent() {
	this.setLayout(null);

	int operatePanelWidth = Constants.mainPanelWidth;
	int operatePanelHeight = Constants.mainPanelHeight / 4;

	// 操作面板
	OperatePanel operatePanel = new OperatePanel(desLrcDialog);
	operatePanel.setBounds(0, 0, operatePanelWidth, operatePanelHeight);

	// 播放列表面板
	playListPanel = new PlayListPanel(this, mainFrame, operatePanelWidth - 20);
	playListPanel.setBounds(10, operatePanelHeight, operatePanelWidth - 20,
		Constants.mainPanelHeight - operatePanelHeight - 10);

	this.add(operatePanel);
	this.add(playListPanel);
    }

    public PlayListPanel getPlayListPanel() {
	return playListPanel;
    }

}
