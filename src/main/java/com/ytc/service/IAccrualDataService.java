/**
 * 
 */
package com.ytc.service;

import java.util.List;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;

import com.ytc.common.model.BookList;
import com.ytc.dal.model.DalBookList;

/**
 * @author 164919
 *
 */
public interface IAccrualDataService {

	void callStoreProcedures() throws SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException;

	List<BookList> getBookList();
	
	String createBookList(DalBookList dalBookList);

	String deleteBookList(List<Integer> bookIdList);

}
