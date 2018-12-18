//package com.happy.widget.panel;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import cn.music.Plays;
//import cn.music.Songs;
//import com.happy.widget.panel.ListViewItemComItemPanel;
//
//public class ThreadLrc implements Runnable {
//    private String path;
//    private List<Map<Long, String>> list;
//    public static boolean wait = false;
//    public static boolean finished = false;
//
//    public ThreadLrc(String path) {
//	this.path = "music_lyrics//" + path + ".lrc";
//    }
//
//    @Override
//    public void run() {
//	long time = 0;
//	long maxtime = 0;
//	Entry<Long, String> e = null;
//	wait = false;
//	finished = false;
//	LrcLyric lrc = new LrcLyric();
//	list = lrc.parse(path);
//	// System.out.println(Songs.getPlayingSong());
//	while (!finished) {
//	    maxtime = 0;
//	    if (!wait) {
//		synchronized (this) {
//		    for (int i = 0; i < list.size(); i++) {
//			for (Entry<Long, String> entry : list.get(i).entrySet()) {
//			    if (entry.getKey() > maxtime
//				    && entry.getKey() < (long) Plays.player.getMediaTime().getSeconds() * 1000) {
//				maxtime = entry.getKey();
//				e = entry;
//			    }
//			}
//		    }
//		    if (maxtime != time) {
//			time = maxtime;
//			if (e != null) {
//			    System.out.println(e.getValue());
//			}
//		    }
//		}
//	    }
//	}
//	ListViewItemComItemPanel.setRuntime(0);
//    }
//}
