package com.hwadee.bookstore.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.hwadee.bookstore.exception.DBException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/*
 * 实现从c3p0获取数据库连接
 */
public class JDBCUtils {

	/*
	 * 实现从数据库连接池中获取数据库连接
	 */
	private static ComboPooledDataSource dataSource;

	// 数据库连接池应该只被初始化一次
	static {
		dataSource = new ComboPooledDataSource();	
	}

	// 从数据库连接池中获取数据库连接
	public static Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("数据库连接错误");
		}
	}

	// 关闭数据库连接
	/*
	 * 使用c3p0数据库管理数据库连接时，并没有关闭数据库的物理连接 只是将数据库连接释放于数据库连接池中
	 */
	public static void release(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DBException("数据库连接错误");
			}
		}
	}

	public static void release(ResultSet rs, PreparedStatement pst) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DBException("数据库连接错误");
			}
		}
		if (pst != null) {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DBException("数据库连接错误");
			}
		}
	}
	
	
	public static void release(Connection conn ,PreparedStatement pst,ResultSet rs){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DBException("数据库连接错误");
			}
		}
		if(pst != null){
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DBException("数据库连接错误");
			}
		}
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DBException("数据库连接错误");
			}
		}
	}
}
