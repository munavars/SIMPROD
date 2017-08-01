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

	String calculateLiability(Integer periodId);

	String reviewedLiabilityCCM(Integer periodId);
	
	String reviewedLiabilityBook(String bookLabel);

	String updatePYTD();

	String updateCYTD();

	List<BookList> getBookList();
	
	String createBookList(DalBookList dalBookList);

	String deleteBookList(List<Integer> bookIdList);

	String updatePYTDBook(String bookLabel);

	String updateCYTDBook(String bookLabel);

	AccrualDropDown getAccrualDropDown();

}
