/**
 * 
 */
package com.ytc.dal.jpa;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ytc.common.result.ResultCode;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalModel;
import com.ytc.dal.model.DalPaidBasedOn;

/**
 * Basic implementation of IDataAccessLayer
 */
@Repository
public class BaseDao implements IDataAccessLayer {
	//Logger logger = LoggerFactory.getLogger(BaseDao.class);

	public BaseDao() {
	}

	@PersistenceContext
	protected EntityManager entityManager;


	@Override
	public void flush() {
		entityManager.flush();
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}



	@Override
	public <E extends DalModel> E getById(Class<E> clazz, String id) {
		if (id == null) {
			throw new NoResultException(ResultCode.NOT_FOUND.getResultString("null"));
		}
		E entity = entityManager.find(clazz, id);
		if (entity == null) {
			throw new NoResultException(ResultCode.NOT_FOUND.getResultString(id));
		}

		return entity;
	}

	@Override
	@Transactional
	public int updateNative(String sql, Map<String, Object> queryParams) {
		Query query = entityManager.createNativeQuery(sql);
		if (queryParams == null || queryParams.size() == 0) {
			throw new RuntimeException("Update should have some condition");
		}
		for (Entry<String, Object> param : queryParams.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}
		return query.executeUpdate();
	}
	

    @Override
    public <T extends DalModel> List<T> list(Class<T> resultClass, String qlString, Map<String, Object> queryParams) {
        Query query = entityManager.createQuery(qlString);
        for (Entry<String, Object> param : queryParams.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }
        @SuppressWarnings("unchecked")
		List<T> resultList = query.getResultList();
        return resultList;
    }

	@Override
	@Transactional
	public <T extends DalModel> void delete(Class<T> clazz, String id) {
		T objectToDel = entityManager.find(clazz, id);
		entityManager.remove(objectToDel);
		//logger.trace("Delete Row from {}, id: {}", clazz, id);
		entityManager.flush();
	}

	@Override
	public List<DalPaidBasedOn> list(Class<DalPaidBasedOn> class1, StringBuilder jpqlQuery,
			Map<String, Object> queryParams) {
		// TODO Auto-generated method stub
		return null;
	}
	
	 
	
}
