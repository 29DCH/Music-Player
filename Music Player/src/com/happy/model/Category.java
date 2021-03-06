package com.happy.model;

import java.util.ArrayList;
import java.util.List;

//分类

public class Category {
    // 基本状态
    public static final int DEF = 0;
    public static final int DEF1 = 0;
    // 删除
    public static final int DEL = 1;
    //喜欢
    public static final int LIKE = 1;
    // 状态
    private int status = DEF;
    private int status1 = DEF1;
    // 分类名
    private String mCategoryName;
    // 分类的内容
    private List<SongInfo> mCategoryItem = new ArrayList<SongInfo>();

    public Category(String mCategroyName) {
	this.mCategoryName = mCategroyName;
    }

    public String getmCategoryName() {
	return mCategoryName;
    }

    //添加歌曲
    public void addItem(SongInfo songInfo) {
	mCategoryItem.add(songInfo);
    }

    // 根据索引获取子内容
    public Object getItem(int pPosition) {
	if (mCategoryItem.size() == 0) {
	    return null;
	}
	return mCategoryItem.get(pPosition);
    }

    public int getmCategoryItemCount() {
	return mCategoryItem.size();
    }

    public List<SongInfo> getmCategoryItem() {
	return mCategoryItem;
    }

    public void setmCategoryItem(List<SongInfo> mCategoryItem) {
	this.mCategoryItem = mCategoryItem;
    }

    public int getStatus() {
	return status;
    }

    public void setStatus(int status) {
	this.status = status;
    }
    
    public int getStatus1() {
	return status1;
    }

    public void setStatus1(int status1) {
	this.status1 = status1;
    }
}