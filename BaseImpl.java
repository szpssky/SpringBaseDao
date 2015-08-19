package com.es.hibernateUtils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * 通用基类实现
 * 
 * @author mqn
 * @version 1.0 2015-05-22
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class BaseImpl<T> implements BaseDao<T> {
	/**
	 * Hiberante4没有HibernateTemplate和HibernateDaoSupport，因此直接注入SessionFactory，
	 * 使用原生Session操作
	 */
	@Resource
	private SessionFactory sessionFactory;

	/**
	 * 获取currentSession,只有currentSession才能被spring所管理<br/>
	 * 不需要打开、关闭session，也不需要处理事务
	 * 
	 * @return
	 */
	protected final Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 创建一个Class的对象来获取泛型的class
	 */
	private Class<T> clz;

	private Class<T> getClz() {
		if (clz == null) {
			clz = ((Class<T>) (((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
		}
		return clz;
	}

	/**
	 * 自动填充查询语句的参数值
	 * 
	 * @param query
	 * @param values
	 */
	private void setQueryParameters(Query query, Object... values) {
		if (values != null) {
			int count = values.length;
			for (int i = 0; i < count; i++) {
				query.setParameter(i, values[i]);
			}
		}
	}

	/**
	 * 自动对查询结果进行分页
	 * 
	 * @param query
	 * @param pageNO
	 * @param pageSize
	 */
	private void setPageParameter(Query query, int pageNO, int pageSize) {
		query.setFirstResult(pageSize * (pageNO - 1));
		query.setMaxResults(pageSize);
	}

	/**
	 * 创建HQL Query
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	private Query createQuery(String hql, Object... values) {
		Query query = getSession().createQuery(hql);

		setQueryParameters(query, values);

		return query;
	}

	/**
	 * 创建SQL Query
	 * 
	 * @param sql
	 * @param values
	 * @return
	 */
	private SQLQuery createSQLQuery(String sql, Object... values) {
		SQLQuery query = getSession().createSQLQuery(sql);

		setQueryParameters(query, values);

		return query;
	}

	@Override
	public T get(Serializable id) {
		return (T) getSession().get(getClz(), id);
	}

	@Override
	public T getByHql(String hql, Object... values) {
		List<T> list = createQuery(hql, values).list();

		return list == null || list.size() == 0 ? null : list.get(0);
	}

	@Override
	public T getBySql(String sql, Object... values) {
		SQLQuery query = createSQLQuery(sql, values);

		// 设置实体对象
		query.addEntity(getClz());

		List<T> list = query.list();

		return list == null || list.size() == 0 ? null : list.get(0);
	}

	@Override
	public List<T> getList(String hql, Object... values) {
		return createQuery(hql, values).list();
	}

	@Override
	public List<T> getListBySql(String sql, Object... values) {
		SQLQuery query = createSQLQuery(sql, values);

		// 设置实体对象
		query.addEntity(getClz());

		return query.list();
	}

	@Override
	public List<T> getPage(int pageNO, int pageSize, String hql, Object... values) {
		Query query = createQuery(hql, values);

		// 设置分页
		setPageParameter(query, pageNO, pageSize);

		return query.list();
	}

	@Override
	public List<T> getPageBySql(int pageNO, int pageSize, String sql, Object... values) {
		SQLQuery query = createSQLQuery(sql, values);

		// 设置实体对象
		query.addEntity(getClz());

		// 设置分页
		setPageParameter(query, pageNO, pageSize);

		return query.list();
	}

	@Override
	public List<Object[]> getArray(String hql, Object... values) {
		return (List<Object[]>) createQuery(hql, values).list();
	}

	@Override
	public List<Object[]> getArrayBySql(String sql, Object... values) {
		return (List<Object[]>) createSQLQuery(sql, values).list();
	}

	@Override
	public List<Object[]> getArrayPage(int pageNO, int pageSize, String hql, Object... values) {
		Query query = createQuery(hql, values);

		// 设置分页
		setPageParameter(query, pageNO, pageSize);

		return (List<Object[]>) query.list();
	}

	@Override
	public List<Object[]> getArrayPageBySql(int pageNO, int pageSize, String sql, Object... values) {
		SQLQuery query = createSQLQuery(sql, values);

		// 设置分页
		setPageParameter(query, pageNO, pageSize);

		return (List<Object[]>) query.list();
	}

	@Override
	public Object getUniqueResult(String hql, Object... values) {
		return createQuery(hql, values).uniqueResult();
	}

	@Override
	public Object getUniqueResultBySql(String sql, Object... values) {
		return createSQLQuery(sql, values).uniqueResult();
	}

	@Override
	public void add(T t) {
		getSession().save(t);
	}

	@Override
	public void delete(T t) {
		getSession().delete(t);
	}

	@Override
	public void update(T t) {
		getSession().update(t);
	}

	@Override
	public void saveOrUpdate(T t){
		getSession().saveOrUpdate(t);
	}

	@Override
	public void executeUpdate(String hql, Object... values) {
		createQuery(hql, values).executeUpdate();
	}

	@Override
	public void executeUpdateBySql(String sql, Object... values) {
		createSQLQuery(sql, values).executeUpdate();
	}
}
