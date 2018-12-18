package com.happy.datas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DBOperation {
    private MyDBConnection myDB = null;
    private Connection conn = null;
    private Statement stmt = null;
    private String name;
    private String password;

    public DBOperation(MyDBConnection myDB) {
	conn = myDB.getMyConnection();
	stmt = myDB.getMyStatement();
    }

    public void insertData(String name, String password) {
	try {
	    String newType1 = new String(name.getBytes(), "GBK");// 字节转码
	    String newType2 = new String(password.getBytes(), "GBK");
	    System.out.println(newType1 + " " + newType2);
	    String sql = "INSERT INTO user_information(name,password)VALUES('" + newType1 + "','" + newType2 + "')";
	    stmt.executeUpdate(sql);// 更新语句
	} catch (Exception e1) {
	    e1.printStackTrace();
	}
    }

    public void updateData(String name, String password) {// 修改
	String sql = "name='" + name + "',password='" + password + "&&name='" + name + "'&&password='" + password + "'";
	try {
	    stmt.executeUpdate(sql);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    public boolean selectPassword(String mpassword) {
	String sql = "SELECT name,password FROM user_information";
	try {
	    ResultSet rs = stmt.executeQuery(sql);// 返回结果集
	    while (rs.next()) {// 指针向后移动
		password = rs.getString("password");
		if (password.equals(mpassword)) {
		    return true;
		}
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return false;
    }

    public boolean selectName(String mname) {
	String sql = "SELECT name,password FROM user_information";
	try {
	    ResultSet rs = stmt.executeQuery(sql);// 返回结果集
	    while (rs.next()) {// 指针向后移动
		name = rs.getString("name");
		if (name.equals(mname)) {
		    return true;
		}
	    }
	} catch (Exception e) {
	}
	return false;
    }
}