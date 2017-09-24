package com.hwadee.bookstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.hwadee.bookstore.dao.Dao;
import com.hwadee.bookstore.util.JDBCUtils;
import com.hwadee.bookstore.util.ReflectionUtils;
import com.hwadee.bookstore.web.ConnectionContext;
import com.mysql.jdbc.Statement;

public class BaseDao<T> implements Dao<T> {

	// ����QueryRunner���ʵ�� DBUtils
	private QueryRunner queryRunner = null;

	//ʹ�÷��䶯̬����bean
	private Class<T> clazz;
	
	public BaseDao() {
		queryRunner = new QueryRunner();
		clazz = ReflectionUtils.getSuperGenericType(getClass());
	}
	
	/*
	 * ��ȡ�����ļ�¼ID ��:ȡ�����ݿ��Զ����ɵ�����
	 */
	@Override
	public int insert(String sql, Object...args) {
		int id = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionContext.getInstance().get();
			// �÷���ֻ���Mysql���Ի�ȡ���ݿ��Զ����ɵ�����������Oracle��������
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					preparedStatement.setObject(i + 1, args[i]);
				}
			}
			
			preparedStatement.executeUpdate();
			//��ResultSet�������ֻ��һ�У��������ݿ��Զ����ɵ�����
			resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()){
				id = resultSet.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			JDBCUtils.release(null, preparedStatement, resultSet);
		}
		return id;
	}

	/*
	 * ʹ��DBUtilsִ�и��²���
	 */
	@Override
	public void update(String sql, Object...args) {
		Connection connection = null;
		try {
			connection = ConnectionContext.getInstance().get();
			queryRunner.update(connection, sql, args);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	/*
	 * ʹ��DSUtils��ѯ��������
	 */
	@Override
	public T query(String sql, Object...args) {
		Connection connection = null;
		T t = null;
		try{
			connection = ConnectionContext.getInstance().get();
			//��������ĵ�һ�����ݷ�װ��һ����Ӧ��JavaBeanʵ����
			return queryRunner.query(connection, sql, new BeanHandler<>(clazz),args);
//			connection.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<T> queryForList(String sql, Object...args) {
		Connection connection = null;
		List<T> list = new ArrayList<T>();
		try {
			connection = ConnectionContext.getInstance().get();
			return queryRunner.query(connection, sql, new BeanListHandler<>(clazz), args);
//			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> E getForValue(String sql, Object... args) {
		Connection connection = null;
		E e=null;
		try {
			connection = ConnectionContext.getInstance().get();
			return (E) queryRunner.query(connection, sql,new ScalarHandler(),args);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public void batch(String sql, Object[]... params) {
		Connection connection = null;
		try {
			connection = ConnectionContext.getInstance().get();
			queryRunner.batch(connection, sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
