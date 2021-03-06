package com.happy.util;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.happy.common.Constants;
import com.happy.logger.LoggerManage;
import com.happy.manage.MediaManage;
import com.happy.model.Category;
import com.happy.model.SongInfo;

//数据处理类
public class DataUtil {
    // 基本数据文件名
    private static String basedataFileName = "data.properties";
    // 播放列表数据文件名
    private static String playListDataFileName = "playlist.properties";
    // 歌曲列表名称
    private static String songListDataFileName = "songlist";
    // 播放列表保存数据路径
    private static String playListDataFilePath = Constants.PATH_PLAYLISTDATA + File.separator + playListDataFileName;
    // 基本数据保存路径
    private static String basedataFilePath = Constants.PATH_DATA + File.separator + basedataFileName;
    // 输出数据
    private static LoggerManage logger;

    // 数据初始化
    public static void init() {
	initFile();
	if (logger == null) {
	    logger = LoggerManage.getLJQLogger();
	}
	if (!readBaseData()) {
	    // 读取数据失败，设置基本的窗口数据
	    Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
	    // 默认窗口宽度
	    Constants.mainFrameWidth = screenDimension.width / 5 * 3 + 100;
	    // 默认窗口高度
	    Constants.mainFrameHeight = screenDimension.height / 4 * 3;
	}
    }

    // 初始化文件夹
    public static void initFile() {
	// 创建相关的文件夹
	File file = new File(Constants.PATH_AUDIO);
	if (!file.exists()) {
	    file.mkdirs();
	}
	file = new File(Constants.PATH_KSC);
	if (!file.exists()) {
	    file.mkdirs();
	}
	// file = new File(Constants.PATH_ARTIST);
	// if (!file.exists()) {
	// file.mkdirs();
	// }
	// file = new File(Constants.PATH_ALBUM);
	// if (!file.exists()) {
	// file.mkdirs();
	// }
	file = new File(Constants.PATH_LOGCAT);
	if (!file.exists()) {
	    file.mkdirs();
	}
	file = new File(Constants.PATH_SKIN);
	if (!file.exists()) {
	    file.mkdirs();
	}
	file = new File(Constants.PATH_SPLASH);
	if (!file.exists()) {
	    file.mkdirs();
	}
    }

    // 读取基本数据
    private static boolean readBaseData() {
	// 基本数据文件不存在，则返回false
	File baseDataFile = new File(basedataFilePath);
	if (!baseDataFile.getParentFile().exists()) {
	    baseDataFile.getParentFile().mkdirs();
	    return false;
	}
	if (!baseDataFile.exists()) {
	    return false;
	}
	// 基本数据配置文件
	Properties baseDataProperties = new Properties();
	try {
	    FileInputStream in = new FileInputStream(baseDataFile);
	    baseDataProperties.load(in);
	    in.close();
	} catch (IOException ex) {
	    logger.error("读取配置文件出错!!");
	    ex.printStackTrace();
	    return false;
	}
	try {
	    // 导入窗口的宽度
	    Constants.mainFrameWidth = Integer.parseInt(baseDataProperties.getProperty(Constants.mainFrameWidth_KEY));
	    // 导入窗口的高度
	    Constants.mainFrameHeight = Integer.parseInt(baseDataProperties.getProperty(Constants.mainFrameHeight_KEY));
	    // 导入窗口的X坐标
	    Constants.mainFramelocaltionX = Integer
		    .parseInt(baseDataProperties.getProperty(Constants.mainFramelocaltionX_KEY));
	    // 导入窗口的Y坐标
	    Constants.mainFramelocaltionY = Integer
		    .parseInt(baseDataProperties.getProperty(Constants.mainFramelocaltionY_KEY));
	    // 背景图片
	    Constants.backGroundName = baseDataProperties.getProperty(Constants.backGroundName_KEY);
	    // 是否显示桌面歌词
	    Constants.showDesktopLyrics = Boolean
		    .parseBoolean(baseDataProperties.getProperty(Constants.showDesktopLyrics_KEY));
	    // 播放模式
	    Constants.playModel = Integer.parseInt(baseDataProperties.getProperty(Constants.playModel_KEY));
	    // 获取当前播放歌曲的id
	    Constants.playInfoID = baseDataProperties.getProperty(Constants.playInfoID_KEY);
	    // 歌词索引颜色
	    Constants.lrcColorIndex = Integer.parseInt(baseDataProperties.getProperty(Constants.lrcColorIndex_KEY));
	    // 列表透明度
	    Constants.listViewAlpha = Integer.parseInt(baseDataProperties.getProperty(Constants.listViewAlpha_KEY));
	    // 音量
	    Constants.volumeSize = Integer.parseInt(baseDataProperties.getProperty(Constants.volumeSize_KEY));
	    // 歌词大小
	    Constants.lrcFontSize = Integer.parseInt(baseDataProperties.getProperty(Constants.lrcFontSize_KEY));
	    // 桌面歌词大小
	    Constants.desktopLrcFontSize = Integer
		    .parseInt(baseDataProperties.getProperty(Constants.desktopLrcFontSize_KEY));
	    // 桌面歌词索引
	    Constants.desktopLrcIndex = Integer.parseInt(baseDataProperties.getProperty(Constants.desktopLrcIndex_KEY));
	    // 锁歌词
	    Constants.desLrcIsLock = Boolean.parseBoolean(baseDataProperties.getProperty(Constants.desLrcIsLock_KEY));
	    return true;
	} catch (Exception e) {
	    logger.error("解析基本数据配置文件出错!!");
	    // baseDataFile.deleteOnExit();
	    e.printStackTrace();
	}
	return false;
    }

    // 读取播放列表数据
    public static List<Category> readPlayListData() {
	File playListDataFile = new File(playListDataFilePath);
	if (!playListDataFile.getParentFile().exists()) {
	    playListDataFile.getParentFile().mkdirs();
	}
	if (!playListDataFile.exists()) {
	    return null;
	}
	// 数据配置文件
	Properties playListDataProperties = new Properties();
	try {
	    FileInputStream in = new FileInputStream(playListDataFile);
	    playListDataProperties.load(in);
	    in.close();
	} catch (IOException ex) {
	    logger.error("读取列表数据配置文件失败!!");
	    ex.printStackTrace();
	    playListDataFile.deleteOnExit();
	    return null;
	}
	try {
	    int listNum = Integer.parseInt(playListDataProperties.getProperty("playlistnum"));
	    List<Category> categorys = new ArrayList<Category>();
	    for (int i = 0; i < listNum; i++) {
		String mCategroyName = playListDataProperties.getProperty("listname" + i);
		Category category = new Category(mCategroyName);
		List<SongInfo> songInfos = getCategorySongInfosByIndex(i);
		category.setmCategoryItem(songInfos);
		categorys.add(category);
	    }
	    return categorys;
	} catch (Exception e) {
	    e.printStackTrace();
	    logger.error("解析列表数据配置文件出错!!");
	    playListDataFile.deleteOnExit();
	}
	return null;
    }

    // 通过索引获取播放列表里面的歌曲列表
    private static List<SongInfo> getCategorySongInfosByIndex(int index) {
	List<SongInfo> songInfos = new ArrayList<SongInfo>();
	String filePath = Constants.PATH_PLAYLISTDATA + File.separator + "songlist" + index + ".properties";
	File songDataFile = new File(filePath);
	if (!songDataFile.getParentFile().exists()) {
	    songDataFile.getParentFile().mkdirs();
	}
	if (!songDataFile.exists()) {
	    return songInfos;
	}
	Properties songDataProperties = new Properties();
	try {
	    FileInputStream in = new FileInputStream(songDataFile);
	    songDataProperties.load(in);
	    in.close();

	} catch (IOException ex) {
	    logger.error("没有找到歌曲列表数据配置文件!!文件索引为:" + index);
	    ex.printStackTrace();
	    return songInfos;
	}

	try {
	    int songnum = Integer.parseInt(songDataProperties.getProperty("songnum"));
	    for (int j = 0; j < songnum; j++) {
		SongInfo mSongInfo = getSongInfoByPropertiesAndIndex(songDataProperties, j);
		if (mSongInfo != null) {
		    mSongInfo.setPlayProgress(0);
		    songInfos.add(mSongInfo);
		}
	    }
	} catch (Exception e) {
	    logger.error("解析歌曲列表数据配置文件出错!!文件索引为:" + index);
	    e.printStackTrace();
	    songDataFile.deleteOnExit();
	}
	return songInfos;
    }

    // 通过配置文件和文件的索引获取歌曲的信息数据
    private static SongInfo getSongInfoByPropertiesAndIndex(Properties songDataProperties, int index) {
	try {
	    SongInfo songInfo = new SongInfo();
	    Class<SongInfo> songInfoClazz = SongInfo.class;
	    Field[] personInfoFields = songInfoClazz.getDeclaredFields();
	    Field.setAccessible(personInfoFields, true);
	    // 获取个人信息类里面的字段内容
	    for (Field field : personInfoFields) {
		String name = field.getName();
		String className = field.getGenericType().toString();
		PropertyDescriptor pd = null;
		try {
		    pd = new PropertyDescriptor(field.getName(), songInfoClazz);
		} catch (Exception e) {
		    continue;
		}
		Method wM = pd.getWriteMethod();// 获得写方法
		wM.setAccessible(true);// 因为写成private所以这里必须设置
		if (className.equals("class java.lang.String")) {
		    String value = songDataProperties.getProperty(name + index);
		    if (value == null || value.equals("")) {
			continue;
		    }
		    wM.invoke(songInfo, value);
		} else if (className.equals("class java.lang.Integer") || className.equals("int")) {
		    String valueStr = songDataProperties.getProperty(name + index);
		    if (valueStr == null || valueStr.equals("")) {
			wM.invoke(songInfo, 0);
			continue;
		    }
		    int value = Integer.parseInt(valueStr);
		    wM.invoke(songInfo, value);
		} else if (className.equals("class java.util.Long") || className.equals("long")) {
		    String valueStr = songDataProperties.getProperty(name + index);
		    if (valueStr == null || valueStr.equals("")) {
			wM.invoke(songInfo, 0);
			continue;
		    }
		    long value = Long.parseLong(valueStr);
		    wM.invoke(songInfo, value);
		}
	    }
	    return songInfo;
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }

    // 保存数据
    public static void saveData() {
	saveBaseData();
	savePlayListData();
    }

    // 保存基本数据
    private static void saveBaseData() {
	File baseDataFile = new File(basedataFilePath);
	if (!baseDataFile.getParentFile().exists()) {
	    baseDataFile.getParentFile().mkdirs();
	}
	// 基本数据配置文件
	Properties baseDataProperties = new Properties();
	baseDataProperties.put(Constants.mainFrameWidth_KEY, Constants.mainFrameWidth + "");
	baseDataProperties.put(Constants.mainFrameHeight_KEY, Constants.mainFrameHeight + "");
	baseDataProperties.put(Constants.mainFramelocaltionX_KEY, Constants.mainFramelocaltionX + "");
	baseDataProperties.put(Constants.mainFramelocaltionY_KEY, Constants.mainFramelocaltionY + "");
	baseDataProperties.put(Constants.backGroundName_KEY, Constants.backGroundName);
	baseDataProperties.put(Constants.showDesktopLyrics_KEY, String.valueOf(Constants.showDesktopLyrics));
	baseDataProperties.put(Constants.playModel_KEY, String.valueOf(Constants.playModel));
	baseDataProperties.put(Constants.playInfoID_KEY, Constants.playInfoID);
	baseDataProperties.put(Constants.lrcColorIndex_KEY, String.valueOf(Constants.lrcColorIndex));
	baseDataProperties.put(Constants.listViewAlpha_KEY, String.valueOf(Constants.listViewAlpha));
	baseDataProperties.put(Constants.volumeSize_KEY, String.valueOf(Constants.volumeSize));
	baseDataProperties.put(Constants.lrcFontSize_KEY, String.valueOf(Constants.lrcFontSize));
	baseDataProperties.put(Constants.desktopLrcFontSize_KEY, String.valueOf(Constants.desktopLrcFontSize));
	baseDataProperties.put(Constants.desktopLrcIndex_KEY, String.valueOf(Constants.desktopLrcIndex));
	baseDataProperties.put(Constants.desLrcIsLock_KEY, String.valueOf(Constants.desLrcIsLock));
	try {
	    FileOutputStream out = new FileOutputStream(baseDataFile);
	    baseDataProperties.store(out, basedataFileName);
	    out.close();
	} catch (IOException ex) {
	    logger.error("基本数据配置文件保存出错!!");
	    ex.printStackTrace();
	    return;
	}
    }

    // 保存播放列表数据
    private static void savePlayListData() {
	File playListDataFile = new File(playListDataFilePath);
	if (!playListDataFile.getParentFile().exists()) {
	    playListDataFile.getParentFile().mkdirs();
	} else {
	    File[] files = playListDataFile.getParentFile().listFiles();
	    for (int i = 0; i < files.length; i++) {
		files[i].delete();
	    }
	}

	// 获取播放列表数据
	List<Category> mCategorys = MediaManage.getMediaManage().getmCategorys();
	Properties playlistProperties = new Properties();
	int size = 0;
	for (int i = 0; i < mCategorys.size(); i++) {
	    Category category = mCategorys.get(i);
	    if (category.getStatus() == Category.DEL)
		continue;
	    playlistProperties.put("listname" + size, category.getmCategoryName());
	    List<SongInfo> mSongInfo = category.getmCategoryItem();
	    Properties songlistProperties = new Properties();
	    String songlistFilePath = Constants.PATH_PLAYLISTDATA + File.separator + songListDataFileName + size
		    + ".properties";
	    File songlistFile = new File(songlistFilePath);
	    int sSize = 0;
	    for (int j = 0; j < mSongInfo.size(); j++) {
		SongInfo songInfo = mSongInfo.get(j);
		if (songInfo.getStatus() == SongInfo.DEL)
		    continue;
		try {
		    Class<SongInfo> songInfoClazz = SongInfo.class;
		    Field[] personInfoFields = songInfoClazz.getDeclaredFields();
		    Field.setAccessible(personInfoFields, true);
		    // 获取个人信息类里面的字段内容
		    for (Field field : personInfoFields) {
			String name = field.getName();
			Object data = field.get(songInfo);
			if (data != null) {
			    if (data instanceof String) {
				String value = (String) data;
				songlistProperties.put(name + sSize, value);
			    } else if (data instanceof Integer) {
				Integer valueTemp = (Integer) data;
				int value = valueTemp.intValue();
				songlistProperties.put(name + sSize, String.valueOf(value));
			    } else if (data instanceof Long) {
				Long valueTemp = (Long) data;
				long value = valueTemp.longValue();
				songlistProperties.put(name + sSize, String.valueOf(value));
			    }
			}
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
		sSize++;
	    }
	    songlistProperties.put("songnum", String.valueOf(sSize));
	    try {
		FileOutputStream out = new FileOutputStream(songlistFile);
		songlistProperties.store(out, songListDataFileName + size + ".properties");
		out.close();
	    } catch (Exception e) {
		e.printStackTrace();
		logger.error("歌曲列表数据配置文件保存出错!!");
		songlistFile.deleteOnExit();
	    }
	    size++;
	}
	// 保存列表数目
	playlistProperties.put("playlistnum", String.valueOf(size));
	try {
	    FileOutputStream out = new FileOutputStream(playListDataFilePath);
	    playlistProperties.store(out, playListDataFileName);
	    out.close();
	} catch (IOException ex) {
	    logger.error("播放列表数据配置文件保存出错!!");
	    playListDataFile.deleteOnExit();
	    ex.printStackTrace();
	}
    }
}