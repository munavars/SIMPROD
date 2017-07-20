package com.ytc.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.Transactional;

import com.ytc.service.IAccrualDataService;

public class AccrualCalcServiceImpl implements IAccrualDataService{

	
	@PersistenceContext
	protected EntityManager entityManager;
	
	/*@Inject
	UserTransaction ut;*/

	@Override
	@Transactional
	public void callStoreProcedures() throws SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
	
	
		
			StoredProcedureQuery query =entityManager.createNamedStoredProcedureQuery("sp_CcmBillToCreditMemoForPandT");
			query.execute();
			List<String> output = query.getResultList();
		
		
		
	}


	

	
	/*private void callStoreProcedure(String procName){
		StoredProcedureQuery query =entityManager.createNamedStoredProcedureQuery(procName);
		query.executeUpdate();	
		//query.getResultList();
		
		//System.out.println(query);
		
	}*/


	

	

}
