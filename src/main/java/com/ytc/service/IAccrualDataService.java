/**
 * 
 */
package com.ytc.service;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;

/**
 * @author 164919
 *
 */
public interface IAccrualDataService {

	void callStoreProcedures() throws SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException;

}
