/**
 * 
 */
package com.ytc.service;

import java.util.List;

import com.ytc.common.model.AccrualDropDown;
import com.ytc.common.model.BookList;
import com.ytc.dal.model.DalBookList;

/**
 * @author CTS
 *
 */
public interface IAccrualDataService {

	void calculateLiability(Integer periodId);

	void reviewedLiabilityCCM(Integer periodId);
	
	void reviewedLiabilityBook(String bookLabel);

	void updatePYTD();

	void updateCYTD();

	List<BookList> getBookList();
	
	String createBookList(DalBookList dalBookList);

	String deleteBookList(List<Integer> bookIdList);

	void updatePYTDBook(String bookLabel);

	void updateCYTDBook(String bookLabel);

	AccrualDropDown getAccrualDropDown();

}
