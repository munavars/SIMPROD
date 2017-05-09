package com.ytc.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ytc.service.IEmployeeHierarchyService;

public class EmployeeHierarchyService implements IEmployeeHierarchyService {


	@PersistenceContext
	protected EntityManager entityManager;

	

	public EmployeeHierarchyService() {

	}
	
	
	@SuppressWarnings("unchecked")
	public List<String> getDetail(String colName) {
		Query query = entityManager.createNativeQuery("SELECT " + colName + " FROM vwBILL_TO_ALL where "+ colName+"");
		List<String> results = query.getResultList();
		return results;	                
	}




}


