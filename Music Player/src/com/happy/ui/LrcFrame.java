package com.happy.ui;

import com.happy.common.Constants;
import com.happy.widget.panel.MainLrcPanel;
import com.happy.widget.panel.MainPanel;

public class LrcFrame extends MainFrame {
    /**
     * 
     */
    private static MainFrame mainFrame = new MainFrame();
    private static final long serialVersionUID = 1L;

    public LrcFrame() {
	initLrcComponent();
    }

    private void initLrcComponent() {
	mainFrame.getContentPane().setLayout(null);
	// 主面板
	Constants.mainPanelWidth = Constants.mainFrameWidth / 5 * 2 - 50;
	Constants.mainPanelHeight = Constants.mainFrameHeight;
	MainPanel mainPanel = new MainPanel(this, desktopLrcDialog);

	int mainMenuPanelWidth = Constants.mainFrameWidth - Constants.mainPanelWidth;
	int mainMenuPanelHeight = Constants.mainPanelHeight / 4 / 3;
	int mainMenuPanelX = Constants.mainPanelWidth;
	// 歌词面板
	MainLrcPanel mainLrcPanel = new MainLrcPanel(mainMenuPanelWidth,
		Constants.mainFrameHeight - mainMenuPanelHeight, mainPanel, desktopLrcDialog);
	mainLrcPanel.setBounds(mainMenuPanelX, mainMenuPanelHeight + 10, mainMenuPanelWidth - 10,
		Constants.mainFrameHeight - mainMenuPanelHeight - 10);
	mainFrame.getContentPane().add(mainLrcPanel);
    }
}
