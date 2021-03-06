package com.ytc.dal;

import java.util.List;
import java.util.Map;

import com.ytc.dal.model.DalAuditableModel;
import com.ytc.dal.model.DalModel;


public interface IDataAccessLayer {

	public <T extends DalModel> T create(T item);

	public <T extends DalModel> T create(T item, Integer userId);

	public <T extends DalModel> T getById(Class<T> clazz, String id);

	public int updateNative(String sql, Map<String, Object> queryParams);

	public <T extends DalModel> void delete(Class<T> clazz, Integer id);

	public <T extends DalModel> List<T> list(Class<T> clazz, String hql, Map<String, Object> queryParams);

	void flush();

	public <T extends DalModel> T getReference(Class<T> clazz, Integer id);

	public <T extends DalModel> T update(T item);

	public <T extends DalModel> T update(T item, Integer userId);

	public <T> List<T> getListFromNamedQuery(String namedQueryString);
	
	public <T extends DalModel> T getById(Class<T> clazz, Integer id);
	
	public <T> T getEntityById(Class<T> class1, Integer id);
	
	public List<String> getTagValue(String query);

	<T extends DalAuditableModel> List<T> getlist(Class<T> resultClass, String qlString, Map<String, Object> queryParams);
	
	public <T> List<T> getListFromNamedQueryWithParameter(String namedQueryString, Map<String, Object> parameters);

	public <T> List<T> getListFromNativeQuery(String namedQueryString, Map<String, Object> parameters);


	public <T> List<T> getByType(String namedQueryString, Map<String, Object> queryParams);

	public <T extends DalModel> T merge(T item);

	public <T> List<T>  validate(String qlString);

	<T> List<T> getByJPQL(Class<T> clazz, String jpql, Map<String, Object> queryParams);
	
}
