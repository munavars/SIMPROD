package com.ytc.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.ytc.dal.IDataAccessLayer;

public class EmployeeHierarchyService implements com.ytc.service.EmployeeHierarchyService {


	@PersistenceContext
	protected EntityManager entityManager;

	@Autowired
	private IDataAccessLayer baseDao;


	public EmployeeHierarchyService() {

	}
	@SuppressWarnings("unchecked")
	public List<String> getDetail(String colName) {
		Query query = entityManager.createNativeQuery("SELECT " + colName + " FROM vwBILL_TO_ALL where "+ colName+"");
		List<String> results = query.getResultList();
		return results;	                
	}




}


