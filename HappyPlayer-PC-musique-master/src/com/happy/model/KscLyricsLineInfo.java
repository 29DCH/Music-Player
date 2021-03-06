package com.happy.model;

/**
 * @功能 歌词实体类
 */
public class KscLyricsLineInfo {
    /**
     * 歌词开始时间字符串
     */
    private String startTimeStr = "";
    /**
     * 歌词开始时间
     */
    private int startTime = 0;
    /**
     * 歌词结束时间
     */
    private int endTime = 0;

    /**
     * 歌词结束时间字符串
     */
    private String endTimeStr = "";
    /**
     * 该行歌词
     */
    private String lineLyrics = "";
    /**
     * 歌词数组，用来分隔每个歌词
     */
    public String[] lyricsWords = null;
    /**
     * 数组，用来存放每个歌词的时间
     */
    public int[] wordsDisInterval = null;

    public String getStartTimeStr() {
	return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
	this.startTimeStr = startTimeStr;
    }

    public int getStartTime() {
	return startTime;
    }

    public void setStartTime(int startTime) {
	this.startTime = startTime;
    }

    public int getEndTime() {
	return endTime;
    }

    public void setEndTime(int endTime) {
	this.endTime = endTime;
    }

    public String getEndTimeStr() {
	return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
	this.endTimeStr = endTimeStr;
    }

    public String getLineLyrics() {
	return lineLyrics;
    }

    public void setLineLyrics(String lineLyrics) {
	this.lineLyrics = lineLyrics;
    }

    public String[] getLyricsWords() {
	return lyricsWords;
    }

    public void setLyricsWords(String[] lyricsWords) {
	this.lyricsWords = lyricsWords;
    }

    public int[] getWordsDisInterval() {
	return wordsDisInterval;
    }

    public void setWordsDisInterval(int[] wordsDisInterval) {
	this.wordsDisInterval = wordsDisInterval;
    }

    /**
     * 获取歌词格式
     */
    public String getKscString() {
	String wordsDisIntervalStr = "";
	for (int i = 0; i < wordsDisInterval.length; i++) {
	    if (i != 0) {
		wordsDisIntervalStr += ",";
	    }
	    wordsDisIntervalStr += wordsDisInterval[i];
	}
	String ksc = "karaoke.add('" + startTimeStr + "', '" + endTimeStr + "', '" + lineLyrics + "', '"
		+ wordsDisIntervalStr + "');";
	return ksc;
    }
}
