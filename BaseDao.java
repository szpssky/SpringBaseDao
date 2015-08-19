package com.es.hibernateUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 通用dao模板,实现泛型,可与BaseImpl打包带走作为其他项目jar使用
 * 
 * @author mqn
 * @version 1.0 2015-05-22
 */
public interface BaseDao<T> {
	/**
	 * 根据主键id获取唯一对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	T get(Serializable id);

	/**
	 * 根据hql语句获取唯一对象，若返回多个值，默认取第一个
	 * 
	 * @param hql
	 *            hql语句
	 * @param values
	 *            hql中参数对应的值
	 * @return
	 */
	T getByHql(String hql, Object... values);

	/**
	 * 根据sql语句获取唯一对象，若返回多个值，默认取第一个
	 * 
	 * @param sql
	 *            sql语句
	 * @param values
	 *            sql中参数对应的值
	 * @return
	 */
	T getBySql(String sql, Object... values);

	/**
	 * 根据hql查询列表
	 * 
	 * @param hql
	 *            hql语句
	 * @param values
	 *            hql中参数对应的值
	 * @return
	 */
	List<T> getList(String hql, Object... values);

	/**
	 * 根据sql查询列表
	 * 
	 * @param sql
	 *            sql语句
	 * @param values
	 *            sql中参数对应的值
	 * @return
	 */
	List<T> getListBySql(String sql, Object... values);

	/**
	 * 根据hql分页查询
	 * 
	 * @param pageNO
	 * @param pageSize
	 * @param hql
	 *            hql语句
	 * @param values
	 *            hql中参数对应的值
	 * @return
	 */
	List<T> getPage(int pageNO, int pageSize, String hql, Object... values);

	/**
	 * 根据sql分页查询
	 * 
	 * @param pageNO
	 * @param pageSize
	 * @param sql
	 *            sql语句
	 * @param values
	 *            sql中参数对应的值
	 * @return
	 */
	List<T> getPageBySql(int pageNO, int pageSize, String sql, Object... values);

	/**
	 * 根据hql查询列表
	 * 
	 * @param hql
	 *            hql语句，若hql未指定查询的列，则Object[]会变为Entity实体对象
	 * @param values
	 *            hql中参数对应的值
	 * @return
	 */
	List<Object[]> getArray(String hql, Object... values);

	/**
	 * 根据sql查询列表
	 * 
	 * @param sql
	 *            sql语句
	 * @param values
	 *            sql中参数对应的值
	 * @return
	 */
	List<Object[]> getArrayBySql(String sql, Object... values);

	/**
	 * 根据hql分页查询
	 * 
	 * @param pageNO
	 * @param pageSize
	 * @param hql
	 *            hql语句，若hql未指定查询的列，则Object[]会变为Entity实体对象
	 * @param values
	 *            hql中参数对应的值
	 * @return
	 */
	List<Object[]> getArrayPage(int pageNO, int pageSize, String hql, Object... values);

	/**
	 * 根据sql分页查询
	 * 
	 * @param pageNO
	 * @param pageSize
	 * @param sql
	 *            sql语句
	 * @param values
	 *            sql中参数对应的值
	 * @return
	 */
	List<Object[]> getArrayPageBySql(int pageNO, int pageSize, String sql, Object... values);

	/**
	 * 根据hql获取唯一结果
	 * 
	 * @param hql
	 *            hql语句
	 * @param values
	 *            hql中参数对应的值
	 * @return <pre>
	 * 使用hql返回的为java.util.Long，则可以直接(Long)result返回
	 * </pre>
	 */
	Object getUniqueResult(String hql, Object... values);

	/**
	 * 根据sql获取唯一结果
	 * 
	 * @param sql
	 *            sql语句
	 * @param values
	 *            sql中参数对应的值
	 * @return <pre>
	 *         使用sql返回的为java.math.BigInteger，需要使用Long.parseLong(result.toString())来获得
	 * </pre>
	 */
	Object getUniqueResultBySql(String sql, Object... values);

	/**
	 * 添加对象
	 * 
	 * @param t
	 */
	void add(T t);

	/**
	 * 更新对象
	 * 
	 * @param t
	 */
	void update(T t);

	/**
	 * 保存或更新对象
	 * 如对象存在则更新对象，对象不存在则添加对象
	 *
	 * @param t
	 */
	void saveOrUpdate(T t);

	/**
	 * 删除对象
	 * 
	 * @param t
	 */
	void delete(T t);

	/**
	 * 执行hql更新
	 * 
	 * @param hql
	 *            hql语句
	 * @param values
	 *            hql中参数对应的值
	 * @return
	 */
	void executeUpdate(String hql, Object... values);

	/**
	 * 执行sql更新
	 * 
	 * @param sql
	 *            sql语句
	 * @param values
	 *            sql中参数对应的值
	 * @return
	 */
	void executeUpdateBySql(String sql, Object... values);
}
