package com.ytc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;

import com.ytc.common.model.BookList;
import com.ytc.common.params.BookParams;
import com.ytc.constant.ProgramConstant;
import com.ytc.constant.QueryConstant;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalBookList;
import com.ytc.helper.ProgramServiceHelper;
import com.ytc.service.IAccrualDataService;

public class AccrualCalcServiceImpl implements IAccrualDataService{
	@Autowired
	private IDataAccessLayer baseDao;
	
	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public void calculateLiability(Integer periodId) {
		StoredProcedureQuery query =entityManager.createNamedStoredProcedureQuery("sp_00_CalculateAccural");
		query.setParameter("PARAM_PERIOD_ID", periodId);
		query.execute();		
	}

	public List<BookList> getBookList(){	
		List<BookList> bookList = new ArrayList<BookList>();
		String sql=QueryConstant.BOOK_LIST;
		Map<String, Object> queryParams = new HashMap<>();	
		List<DalBookList> resultList =baseDao.list(DalBookList.class, sql, queryParams);
		for (Iterator<DalBookList> iterator = resultList.iterator(); iterator.hasNext();) {
			DalBookList dalBookList = (DalBookList) iterator.next();
			BookList book=new BookList();
			book.setBookDate(ProgramServiceHelper.convertDateToString(dalBookList.getBookDate().getTime(), ProgramConstant.DATE_FORMAT));
			book.setId(dalBookList.getId().toString());
			book.setBookLabel(dalBookList.getBookLabel());
			book.setBookRecord(dalBookList.getBookRecord());
			book.setCreatedUser(dalBookList.getCreatedUser());
			book.setCreatedDate(ProgramServiceHelper.convertDateToString(dalBookList.getCreatedDate().getTime(), ProgramConstant.DATE_FORMAT));
			book.setDelete("");
			bookList.add(book);
		}
		BookList emptybook=new BookList();
		emptybook.setBookLabel("<input type='text' id='booklabelnew' value=''>");
		emptybook.setBookDate("<input type='text' id='bookdatenew' value='' readonly='readonly' style='background:white;'>");
		emptybook.setDelete("add");
		emptybook.setBookRecord("new");
		bookList.add(emptybook);
		return bookList;
	}
	

	public String createBookList(DalBookList dalBookList){
		String status="fail";
		String sql=QueryConstant.CREATE_BOOK_LIST;	
		try{
		Map<String, Object> queryParams = new HashMap<>();			
		queryParams.put("user",dalBookList.getCreatedUser());
		queryParams.put("booklabel",dalBookList.getBookLabel());
		queryParams.put("bookdate",dalBookList.getBookDate());
		queryParams.put("bookrecord",dalBookList.getBookRecord());
		baseDao.updateNative(sql, queryParams);		
		status="success";
		}catch(Exception e){
			System.out.println("exception occured in createBookList"+e.getMessage());
		}
		return status;
	}
	
	public String deleteBookList(List<Integer> bookIdList){
		String status="fail";
		String sql=QueryConstant.DELETE_BOOK_LIST;	
		try{
		Map<String, Object> queryParams = new HashMap<>();			
		queryParams.put("id",bookIdList);
		baseDao.updateNative(sql, queryParams);		
		status="success";
		}catch(Exception e){
			System.out.println("exception occured in deleteBookList"+e.getMessage());
		}
		return status;
	}
	
	@Override
	public void reviewedLiabilityCCM(Integer periodId) {
		StoredProcedureQuery query =entityManager.createNamedStoredProcedureQuery("sp_MoveAccrualDataToCCM");
		query.setParameter("PARAM_PERIOD_ID", periodId);
		query.execute();	
		
	}
	
	@Override
	public void reviewedLiabilityBook(BookParams bookParams) {
		StoredProcedureQuery query =entityManager.createNamedStoredProcedureQuery("sp_MoveAccrualDataToBook");
		query.setParameter("PARAM_BOOK_LABEL", bookParams.getBookLabel());
		query.setParameter("PARAM_BOOK_DATE", bookParams.getBookDate());
		query.setParameter("PARAM_BOOK_OF_RECORD", bookParams.getBookOfRecord());
		query.setParameter("PARAM_CREATED_BY", bookParams.getCreatedBy());
		query.execute();	
		
	}

	@Override
	public void updatePYTD() {
		StoredProcedureQuery query =entityManager.createNamedStoredProcedureQuery("sp_MoveAccrualDataCYTDToPYTD");
		query.execute();	
	}
	
	@Override
	public void updatePYTDBook(String bookLabel) {
		StoredProcedureQuery query =entityManager.createNamedStoredProcedureQuery("sp_MoveBookLabelToPYTD");
		query.setParameter("PARAM_BOOK_LABEL", bookLabel);
		query.execute();	
	}

	@Override
	public void updateCYTD() {
		StoredProcedureQuery query =entityManager.createNamedStoredProcedureQuery("sp_MoveAccrualDataToCYTD");
		query.execute();		
	}
	
	@Override
	public void updateCYTDBook(String bookLabel) {
		StoredProcedureQuery query =entityManager.createNamedStoredProcedureQuery("sp_MoveBookLabelToCYTD");
		query.setParameter("PARAM_BOOK_LABEL", bookLabel);
		query.execute();		
	}

	
	
}
