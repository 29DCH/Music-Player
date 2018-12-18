//package com.happy.widget.panel;
//
//import java.sql.Time;
//
//import com.happy.widget.panel.ListViewItemComItemPanel;
//
//public class ThreadTime implements Runnable {
//
//    public ThreadTime() {
//
//    }
//
//    @Override
//    public void run() {
//	while (true) {
//	    synchronized (this) {
//		if (Plays.player != null) {
//		    if (ListViewItemComItemPanel.getIspressing() == 0) {
//			if (ListViewItemComItemPanel.getIschanging() == 1) {
//			    ListViewItemComItemPanel.setNewtime(ListViewItemComItemPanel.getSlider().getValue());
//			    Plays.player.setMediaTime(
//				    new Time(((long) ListViewItemComItemPanel.getNewtime()) * 1000000000));
//			    ListViewItemComItemPanel.setIschanging(0);
//			} else {
//			    ListViewItemComItemPanel.setNewtime((int) Plays.player.getMediaTime().getSeconds());
//			    ListViewItemComItemPanel.getSlider().setValue(ListViewItemComItemPanel.getNewtime());
//			}
//		    }
//		}
//	    }
//	}
//    }
//}
