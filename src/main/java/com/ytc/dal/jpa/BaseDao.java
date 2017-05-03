/**
 * 
 */
package com.ytc.dal.jpa;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ytc.common.result.ResultCode;
import com.ytc.common.result.ResultException;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalAuditableModel;
import com.ytc.dal.model.DalModel;
import com.ytc.dal.model.DalUser;
import com.ytc.service.ServiceContext;


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
	@Transactional
	public <T extends DalModel> T create(T item) {
		return create(item, 6);
	}

	@Override
	@Transactional
	public <T extends DalModel> T create(T item, Integer userId) {
		if (item instanceof DalAuditableModel) {
			DalUser dbUser = getReference(DalUser.class, userId);
			DalAuditableModel aItem = (DalAuditableModel) item;
			aItem.setCreatedBy(dbUser);
			aItem.setModifiedBy(dbUser);
			Calendar createdDate = Calendar.getInstance();
			aItem.setCreatedDate(createdDate);

		}
		if (StringUtils.isEmpty(item.getId())) {
			//entityManager.persist(item);
			Session session= entityManager.unwrap(Session.class);
		    @SuppressWarnings("unused")
			int result = -1;
		    try {
		        Serializable ser = session.save(item);
		        if (ser != null) {
		            result = (Integer) ser;
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    item.setId(result);
		  entityManager.persist(item);
		} else {
			Session session = entityManager.unwrap(Session.class);
			session.save(item);
		}
		return item;
	}
	@Override
	public <E extends DalModel> E getReference(Class<E> clazz, Integer id) {
		return (StringUtils.isEmpty(id)) ? null : (E) entityManager.getReference(clazz, id);
	}

	@Override
	@Transactional
	public <T extends DalModel> T update(T item) {
		return update(item, 6);
	}

	@Override
	@Transactional
	public <T extends DalModel> T update(T item, Integer userId) {
		T mergedItem = updateItemToBeMerged(item, userId);
		T t = entityManager.merge(mergedItem);
		entityManager.flush();
		return t;
	}

	private <T extends DalModel> T updateItemToBeMerged(T item, Integer userId) {
		Integer objectId = item.getId();
		Class<? extends DalModel> entityClass = item.getClass();

		DalModel existingItem = entityManager.find(entityClass, objectId);
		if (existingItem == null) {
			throw new ResultException(ResultCode.NOT_FOUND, String.format("Object with id %s not found", objectId), "id");
		}

		if (existingItem instanceof DalAuditableModel) {
			DalUser dbUser = getReference(DalUser.class, userId);
			DalAuditableModel itemToUpdate = (DalAuditableModel) item;
			itemToUpdate.setModifiedBy(dbUser);
			entityManager.detach(existingItem);
		}
		return item;
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
	public <T extends DalModel> void delete(Class<T> clazz, Integer id) {
		T objectToDel = entityManager.find(clazz, id);
		entityManager.remove(objectToDel);
		//logger.trace("Delete Row from {}, id: {}", clazz, id);
		entityManager.flush();
	}


	@Override
	public <E extends DalModel> E getById(Class<E> clazz, Integer id) {
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
	public <T> List<T> getListFromNamedQuery(String namedQueryString) {
		Query query = null;
		List<T> returnList = null;
		if(namedQueryString != null){
			query = entityManager.createNamedQuery(namedQueryString);
			returnList = query.getResultList();
		}
		return returnList;
	}

	public <T> T getEntityById(Class<T> class1, Integer id){
		if (id == null) {
			throw new NoResultException(ResultCode.NOT_FOUND.getResultString("null"));
		}
		T entity = entityManager.find(class1, id);
		if (entity == null) {
			throw new NoResultException(ResultCode.NOT_FOUND.getResultString(id));
		}

		return entity;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getTagValue(String queryString) {
		List<String> returnList = null;
		Query query = null;
		if(queryString != null){
			query = entityManager.createQuery(queryString);
			returnList = query.getResultList();
		}
		return returnList;
	}


	@Override
	public <T extends DalAuditableModel> List<T> getlist(Class<T> resultClass, String qlString, Map<String, Object> queryParams) {
		Query query = entityManager.createNativeQuery(qlString,resultClass);
		for (Entry<String, Object> param : queryParams.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}
		@SuppressWarnings("unchecked")
		List<T> resultList = query.getResultList();
		return resultList;
	}
}
