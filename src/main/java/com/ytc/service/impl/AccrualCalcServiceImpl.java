package com.ytc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytc.common.model.AccrualDropDown;
import com.ytc.common.model.BookList;
import com.ytc.common.model.DropDown;
import com.ytc.constant.ProgramConstant;
import com.ytc.constant.QueryConstant;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalBookList;
import com.ytc.helper.ProgramServiceHelper;
import com.ytc.service.IAccrualDataService;

public class AccrualCalcServiceImpl implements IAccrualDataService{
	
	private org.slf4j.Logger logger = LoggerFactory.getLogger(AccrualCalcServiceImpl.class);
	
	@Autowired
	private IDataAccessLayer baseDao;
	
	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public String calculateLiability(Integer periodId) {
		logger.info("Enter in to the calculate liability store procedure");
		String status="fail";
		try{
		StoredProcedureQuery query =entityManager.createStoredProcedureQuery("sp_00_CalculateAccural");
		query.registerStoredProcedureParameter("PARAM_PERIOD_ID", Integer.class, ParameterMode.IN);
		query.setParameter("PARAM_PERIOD_ID", periodId);
		query.execute();		
		status="success";
		logger.info("Successfully completed calculate liability store procedure");
		}catch (Exception e){
			logger.info("Exception occured in calculateLiability"+e.getMessage());
			
		}

		return status;
	}
	
	public AccrualDropDown getAccrualDropDown(){
		AccrualDropDown accrualDropDown=new AccrualDropDown();
		accrualDropDown.setBookLabelList(getBookLabelDropDownList());
		accrualDropDown.setPeriodList(getPeriodDropDownList());
		return accrualDropDown;
		
	}
	
	public List<DropDown> getPeriodDropDownList(){
		List<DropDown> dropdownList = null;
		String sql=QueryConstant.CCM_PERIOD;
		Map<String, Object> queryParams = new HashMap<>();
		List<Object> resultList =baseDao.getListFromNativeQuery(sql, queryParams);
		if(resultList != null){
			for (Iterator<Object> iterator = resultList.iterator(); iterator.hasNext();) {
				Object[] obj = (Object[]) iterator.next();
				DropDown dropDown = new DropDown();
				dropDown.setKey(obj[1].toString());
				dropDown.setValue(obj[0].toString());
				if(dropdownList == null){
					dropdownList = new ArrayList<DropDown>();
				}
				dropdownList.add(dropDown);
			}

		}


		return dropdownList;
	}
	
	private List<DropDown> getBookLabelDropDownList(){
		List<DropDown> dropdownList = null;
			List<DalBookList> dalBookList =  baseDao.getListFromNamedQuery("DalBookList.getAllDetailsWithSort");
			if(dalBookList != null){
				for(DalBookList dalBook : dalBookList){
					DropDown dropDown = new DropDown();
					dropDown.setKey(dalBook.getBookLabel());
					dropDown.setValue(dalBook.getBookLabel());
					if(dropdownList == null){
						dropdownList = new ArrayList<DropDown>();
					}
					dropdownList.add(dropDown);
				}
			}
			
		
		
		return dropdownList;
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
		queryParams.put("book",dalBookList.getBookLabel());
		List<DalBookList> dalBokList =  baseDao.getListFromNamedQueryWithParameter("DalBookList.getAllDetailsForLabel", queryParams);
		if(!dalBokList.isEmpty()){
			return "duplicate";
		}
		queryParams = new HashMap<>();
		queryParams.put("user",dalBookList.getCreatedUser());
		queryParams.put("booklabel",dalBookList.getBookLabel());
		queryParams.put("bookdate",dalBookList.getBookDate());
		queryParams.put("bookrecord",dalBookList.getBookRecord());
		baseDao.updateNative(sql, queryParams);		
		status="success";
		}catch(Exception e){
			logger.info("exception occured in createBookList"+e.getMessage());
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
		status=deleteAccrualDataBook(bookIdList);
		}catch(Exception e){
			logger.info("Exception occured in deleteBookList"+e.getMessage());
		}
		return status;
	}
	
	public String deleteAccrualDataBook(List<Integer> bookIdList){
		String status="fail";
		String sql=QueryConstant.DELETE_ACCURAL_BOOK;	
		try{
		Map<String, Object> queryParams = new HashMap<>();			
		queryParams.put("id",bookIdList);
		baseDao.updateNative(sql, queryParams);		
		status="success";
		}catch(Exception e){
			logger.info("Exception occured in deleteAccrualDataBook"+e.getMessage());
		}
		return status;
	}
	
	@Override
	public String reviewedLiabilityCCM(Integer periodId) {
		logger.info("Enter in to the reviewed liability CCM store procedure");
		String status="fail";
		try{
		StoredProcedureQuery query =entityManager.createStoredProcedureQuery("sp_MoveAccrualDataToCCM");
		query.registerStoredProcedureParameter("PARAM_PERIOD_ID", Integer.class, ParameterMode.IN);
		query.setParameter("PARAM_PERIOD_ID", periodId);
		query.execute();	
		status="success";
		logger.info("Successfully completed reviewed liability CCM store procedure");
		}catch (Exception e){
			logger.info("Exception occured in reviewedLiabilityCCM"+e.getMessage());
		}

		return status;
		
	}
	
	@Override
	public String reviewedLiabilityBook(String bookLabel) {

		logger.info("Enter in to the reviewed liability Book store procedure");
		String status="fail";
		try{
			StoredProcedureQuery query =entityManager.createStoredProcedureQuery("sp_MoveAccrualDataToBook");
			query.registerStoredProcedureParameter("PARAM_BOOK_LABEL", String.class, ParameterMode.IN);

			query.setParameter("PARAM_BOOK_LABEL", bookLabel);

			query.execute();
			status="success";
			logger.info("Successfully completed reviewed liability Book store procedure");
		}catch (Exception e){
			logger.info("Exception occured in reviewedLiabilityBook"+e.getMessage());
		}

		return status;
	}

	@Override
	public String updatePYTD() {
		logger.info("Enter in to the move accrual data CYTD to PYTD store procedure");
		String status="fail";
		try{
		StoredProcedureQuery query =entityManager.createStoredProcedureQuery("sp_MoveAccrualDataCYTDToPYTD");
		query.execute();	
		status="success";
		logger.info("Successfully completed move accrual data CYTD to PYTD store procedure");
		}catch (Exception e){
			logger.info("Exception occured in updatePYTD"+e.getMessage());
		}
		return status;
	}
	
	@Override
	public String updatePYTDBook(String bookLabel) {
		logger.info("Enter in to the updatePYTDBook store procedure");
		String status="fail";
		try{
		StoredProcedureQuery query =entityManager.createStoredProcedureQuery("sp_MoveBookLabelToPYTD");
		query.registerStoredProcedureParameter("PARAM_BOOK_LABEL", String.class, ParameterMode.IN);
		query.setParameter("PARAM_BOOK_LABEL", bookLabel);
		query.execute();	
		status="success";
		logger.info("Successfully completed updatePYTDBook store procedure");
		}catch (Exception e){
			logger.info("Exception occured in updatePYTDBook"+e.getMessage());
		}
		return status;
	}

	@Override
	public String updateCYTD() {
		logger.info("Enter in to the updateCYTD store procedure");
		String status="fail";
		try{
		StoredProcedureQuery query =entityManager.createStoredProcedureQuery("sp_MoveAccrualDataToCYTD");
		query.execute();		
		status="success";
		logger.info("Successfully completed updateCYTD store procedure");
		}catch (Exception e){
			logger.info("Exception occured in updateCYTD"+e.getMessage());
		}
		return status;
	}
	
	@Override
	public String updateCYTDBook(String bookLabel) {
		logger.info("Enter in to the updateCYTDBook store procedure");
		String status="fail";
		try{
		StoredProcedureQuery query =entityManager.createStoredProcedureQuery("sp_MoveBookLabelToCYTD");
		query.registerStoredProcedureParameter("PARAM_BOOK_LABEL", String.class, ParameterMode.IN);
		query.setParameter("PARAM_BOOK_LABEL", bookLabel);
		query.execute();	
		status="success";
		logger.info("Successfully completed updateCYTDBook store procedure");
		}catch (Exception e){
			logger.info("Exception occured in updateCYTDBook"+e.getMessage());
		}
		return status;
	}

	
	
}
