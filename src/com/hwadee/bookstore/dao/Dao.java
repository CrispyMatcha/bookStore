package com.hwadee.bookstore.dao;

import java.util.ArrayList;
import java.util.List;

/*
 * Dao接口，定义Dao的基本操作，由BaseDao提供实现
 * <T> Dao实际操作的泛型类型
 */
public interface Dao<T> {

	/*
	 * 执行 INSERT 操作, 返回插入后的记录的 ID
	 * @param sql: 待执行的 SQL 语句
	 * @param args: 填充占位符的参数集合
	 * @return: 插入新记录的 id
	 */
	int insert(String sql, Object...args);
	
	/*
	 * 执行update操作，包括insert(没有返回值) update，delete
	 * @param sql: 待执行的 SQL 语句
	 * @param args: 填充占位符的参数集合
	 */
	void update(String sql, Object...args);
	
	/*
	 * 执行单条记录的查询操作，返回与记录对应的类的一个对象
	 * @param sql: 待执行的 SQL 语句
	 * @param args: 填充占位符的参数集合
	 * @param clazz: 描述对象的类型
	 * @return: 与记录对应的类的一个对象
	 */
	T query( String sql, Object...args);
	
	/*
	 * 执行多条记录的查询操作, 返回与记录对应的类的一个 List
	 * @param clazz: 描述对象的类型
	 * @param sql: 待执行的 SQL 语句
	 * @param args: 填充占位符的可变参数
	 * @return: 与记录对应的类的一个 List
	 */
	List<T> queryForList(String sql, Object...args);

	/*
	 * 执行一个属性或值得查询操作， 例如查询某一条记录的一个字段, 或查询某个统计信息, 返回要查询的值
	 * @param sql: 待执行的 SQL 语句
	 * @param args: 填充占位符的可变参数
	 * @return: 执行一个属性或值的查询操作, 例如查询某一条记录的一个字段, 或查询某个统计信息, 返回要查询的值
	 */
	<E> E getForValue(String sql,Object ...args);
	
	
	/*
	 * 执行批量更新操作
	 * @param sql: 待执行的 SQL 语句
	 * @param args: 填充占位符的可变参数
	 */
	void batch(String sql, Object[]... params);

}
