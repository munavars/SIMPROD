package com.ytc.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ytc.common.model.Employee;
import com.ytc.common.model.User;
import com.ytc.common.result.ListResult;
import com.ytc.common.result.ResultCode;
import com.ytc.common.result.ResultException;
import com.ytc.controller.UserController;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalEmployee;
import com.ytc.dal.model.DalUser;
import com.ytc.service.IEmployeeService;

public class EmployeeService implements IEmployeeService {
	
	private org.slf4j.Logger logger = LoggerFactory.getLogger(EmployeeService.class);

	
	@PersistenceContext
	protected EntityManager entityManager;

	@Autowired
	private IDataAccessLayer baseDao;



	public EmployeeService() {

	}


	
	@Override
	@Transactional(readOnly = true)
	public User authenticateUIUser(String userId) {
		if (StringUtils.isBlank(userId)) {
			throw new ResultException(ResultCode.INVALID_LOGON, ResultCode.INVALID_LOGON.getResultString());
		}
		DalUser dalUser = baseDao.getById(DalUser.class, userId);
		if(dalUser.getStatus()!="ACTIVE"){
			throw new ResultException(ResultCode.NOT_ACTIVE, ResultCode.NOT_ACTIVE.getResultString());
		}
		//Need to add logic here to verify with LDAP
		User user = new User();
		user.setUserName(dalUser.getUserName());
		return user;
	}
	
	@Override
	public Employee getDetail(String loginId) {
		logger.info("inside com.ytc.service.impl.EmployeeService.getDetail loginId: "+loginId);

		DalEmployee dalEmployee = baseDao.getById(DalEmployee.class, loginId);
		logger.info("inside com.ytc.service.impl.EmployeeService.getDetail created dalEmployee");
		ModelMapper modelMapper = new ModelMapper();
		logger.info("inside com.ytc.service.impl.EmployeeService.getDetail created modelMapper");
		Employee employee = modelMapper.map(dalEmployee, Employee.class);
		logger.info("inside com.ytc.service.impl.EmployeeService.getDetail created employee: "+employee);
		return employee;

	}
	

//	SELECT * FROM [INTERFACE].[dbo].[W_YTC_EMPLOYEE_DH]
//			where BASE_EMP_ID = 101 or LVL1_EMP_ID = 101 or LVL2_EMP_ID = 101 or LVL3_EMP_ID = 101 or LVL4_EMP_ID = 101 or LVL5_EMP_ID = 101 -- Andrew Martin Example

	@SuppressWarnings("unchecked")
	public List<String> getQueryResult(String queryString) {
		Query query = entityManager.createNativeQuery(queryString);
		List<String> results = query.getResultList();
		return results;	                
	}
}


