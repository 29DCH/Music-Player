package com.happy.datas;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Commentdb {

    String DBDriver = "com.mysql.jdbc.Driver";
    String DBURL = "jdbc:mysql://localhost:3306/info";
    String DBUser = "root";
    String DBPass = "LJQ19981028";
    private static java.sql.Connection conn = null;

    public void initParam() throws Exception {
	try {
	    Class.forName(DBDriver);
	    conn = DriverManager.getConnection(DBURL, DBUser, DBPass);
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}
    }

    public void insert2(String name, String praise, String time) throws Exception {
	java.sql.Connection conn = DriverManager.getConnection(DBURL, DBUser, DBPass);
	java.sql.PreparedStatement pstmt = conn.prepareStatement("insert into user information values(?,?,?)");
	pstmt.setString(1, name);
	pstmt.setString(2, praise);
	pstmt.setString(3, time);
	pstmt.executeUpdate();
    }

    public String select2(String name, String praise, String time) throws Exception {

	java.sql.Connection conn = DriverManager.getConnection(DBURL, DBUser, DBPass);
	java.sql.Statement statement = conn.createStatement();
	// 要执行的SQL语句
	String sql = "select *from user information";
	// 3.ResultSet类，用来存放获取的结果集！！
	ResultSet rs = statement.executeQuery(sql);
	while (rs.next()) {
	    // 获取stuname这列数据
	    name = rs.getString("name");
	    praise = rs.getString("Comment");
	    time = rs.getString("time");
	}
	rs.close();
	conn.close();
	return name + praise + time;
    }

    public String friendCircle() throws Exception {
	java.sql.Connection conn = DriverManager.getConnection(DBURL, DBUser, DBPass);
	java.sql.Statement statement = conn.createStatement();
	String sql = "select *from user information";
	ResultSet rs = statement.executeQuery(sql);
	String str = "";
	String name = null;
	String parise = null;
	String time = null;
	ArrayList<String> strArray = new ArrayList<String>();
	int j = 0;
	while (rs.next()) {
	    name = rs.getString("name");
	    parise = rs.getString("Comment");
	    time = rs.getString("time");
	    strArray.add(name + "\t" + parise + "\n" + time + "\n\n");
	    j++;
	}
	for (int i = j - 1; i >= 0; i--) {
	    str = str + strArray.get(i);
	}
	rs.close();
	conn.close();
	return str;
    }

}
