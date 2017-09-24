package com.hwadee.bookstore.dao;

import java.util.ArrayList;
import java.util.List;

/*
 * Dao�ӿڣ�����Dao�Ļ�����������BaseDao�ṩʵ��
 * <T> Daoʵ�ʲ����ķ�������
 */
public interface Dao<T> {

	/*
	 * ִ�� INSERT ����, ���ز����ļ�¼�� ID
	 * @param sql: ��ִ�е� SQL ���
	 * @param args: ���ռλ���Ĳ�������
	 * @return: �����¼�¼�� id
	 */
	int insert(String sql, Object...args);
	
	/*
	 * ִ��update����������insert(û�з���ֵ) update��delete
	 * @param sql: ��ִ�е� SQL ���
	 * @param args: ���ռλ���Ĳ�������
	 */
	void update(String sql, Object...args);
	
	/*
	 * ִ�е�����¼�Ĳ�ѯ�������������¼��Ӧ�����һ������
	 * @param sql: ��ִ�е� SQL ���
	 * @param args: ���ռλ���Ĳ�������
	 * @param clazz: �������������
	 * @return: ���¼��Ӧ�����һ������
	 */
	T query( String sql, Object...args);
	
	/*
	 * ִ�ж�����¼�Ĳ�ѯ����, �������¼��Ӧ�����һ�� List
	 * @param clazz: �������������
	 * @param sql: ��ִ�е� SQL ���
	 * @param args: ���ռλ���Ŀɱ����
	 * @return: ���¼��Ӧ�����һ�� List
	 */
	List<T> queryForList(String sql, Object...args);

	/*
	 * ִ��һ�����Ի�ֵ�ò�ѯ������ �����ѯĳһ����¼��һ���ֶ�, ���ѯĳ��ͳ����Ϣ, ����Ҫ��ѯ��ֵ
	 * @param sql: ��ִ�е� SQL ���
	 * @param args: ���ռλ���Ŀɱ����
	 * @return: ִ��һ�����Ի�ֵ�Ĳ�ѯ����, �����ѯĳһ����¼��һ���ֶ�, ���ѯĳ��ͳ����Ϣ, ����Ҫ��ѯ��ֵ
	 */
	<E> E getForValue(String sql,Object ...args);
	
	
	/*
	 * ִ���������²���
	 * @param sql: ��ִ�е� SQL ���
	 * @param args: ���ռλ���Ŀɱ����
	 */
	void batch(String sql, Object[]... params);

}
