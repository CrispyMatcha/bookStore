package com.hwadee.bookstore.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.hwadee.bookstore.exception.DBException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/*
 * ʵ�ִ�c3p0��ȡ���ݿ�����
 */
public class JDBCUtils {

	/*
	 * ʵ�ִ����ݿ����ӳ��л�ȡ���ݿ�����
	 */
	private static ComboPooledDataSource dataSource;

	// ���ݿ����ӳ�Ӧ��ֻ����ʼ��һ��
	static {
		dataSource = new ComboPooledDataSource();	
	}

	// �����ݿ����ӳ��л�ȡ���ݿ�����
	public static Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("���ݿ����Ӵ���");
		}
	}

	// �ر����ݿ�����
	/*
	 * ʹ��c3p0���ݿ�������ݿ�����ʱ����û�йر����ݿ���������� ֻ�ǽ����ݿ������ͷ������ݿ����ӳ���
	 */
	public static void release(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DBException("���ݿ����Ӵ���");
			}
		}
	}

	public static void release(ResultSet rs, PreparedStatement pst) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DBException("���ݿ����Ӵ���");
			}
		}
		if (pst != null) {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DBException("���ݿ����Ӵ���");
			}
		}
	}
	
	
	public static void release(Connection conn ,PreparedStatement pst,ResultSet rs){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DBException("���ݿ����Ӵ���");
			}
		}
		if(pst != null){
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DBException("���ݿ����Ӵ���");
			}
		}
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DBException("���ݿ����Ӵ���");
			}
		}
	}
}
