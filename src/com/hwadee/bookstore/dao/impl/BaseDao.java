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

	// 创建QueryRunner类的实例 DBUtils
	private QueryRunner queryRunner = null;

	//使用反射动态创建bean
	private Class<T> clazz;
	
	public BaseDao() {
		queryRunner = new QueryRunner();
		clazz = ReflectionUtils.getSuperGenericType(getClass());
	}
	
	/*
	 * 获取插入后的记录ID 即:取得数据库自动生成的主键
	 */
	@Override
	public int insert(String sql, Object...args) {
		int id = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionContext.getInstance().get();
			// 该方法只针对Mysql可以获取数据库自动生成的主键，而对Oracle不起作用
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					preparedStatement.setObject(i + 1, args[i]);
				}
			}
			
			preparedStatement.executeUpdate();
			//在ResultSet结果集中只有一列，就是数据库自动生成的主键
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
	 * 使用DBUtils执行更新操作
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
	 * 使用DSUtils查询单个对象
	 */
	@Override
	public T query(String sql, Object...args) {
		Connection connection = null;
		T t = null;
		try{
			connection = ConnectionContext.getInstance().get();
			//将结果集的第一行数据封装到一个对应的JavaBean实例中
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
