package com.ytc.dal;

import java.util.List;
import java.util.Map;

import com.ytc.dal.model.DalModel;
import com.ytc.dal.model.DalPaidBasedOn;


public interface IDataAccessLayer {

	public <T extends DalModel> T getById(Class<T> clazz, String id);

	public int updateNative(String sql, Map<String, Object> queryParams);

	public <T extends DalModel> void delete(Class<T> clazz, String id);
	
	public <T extends DalModel> List<T> list(Class<T> clazz, String hql, Map<String, Object> queryParams);
	
	public List<DalPaidBasedOn> list(Class<DalPaidBasedOn> class1, StringBuilder jpqlQuery,	Map<String, Object> queryParams);


	void flush();

	

}
