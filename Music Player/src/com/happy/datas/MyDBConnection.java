package com.happy.datas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MyDBConnection {
    private String DBDriver;// 连接类
    private String DBURL;
    private String DBUser;
    private String DBPass;
    private Connection conn = null;
    private Statement stmt = null;

    public MyDBConnection(String driver, String dburl, String user, String pass) {
	DBDriver = driver;
	DBURL = dburl;
	DBUser = user;
	DBPass = pass;
	try {
	    Class.forName(DBDriver);// 加载驱动程序
	    System.out.println("数据库驱动程序加载成功");
	} catch (Exception e) {
	    e.printStackTrace();
	}
	try {
	    conn = DriverManager.getConnection(DBURL, DBUser, DBPass);// 取得连接对象
	    stmt = conn.createStatement();// 取得SQL语句对象
	    System.out.println("连接数据库成功");
	    System.out.println(conn.toString());
	    System.out.println(stmt.toString());
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public Connection getMyConnection() {
	return conn;
    }

    public Statement getMyStatement() {
	return stmt;
    }

    public void closeMyConnection() {
	try {
	    stmt.close();
	    conn.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    public String toString() {
	return "数据库驱动程序" + DBDriver + "，链接地址" + DBURL + "，用户名" + DBUser + "，密码" + DBPass;
    }
}