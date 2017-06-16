package com.ytc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ytc.common.model.Employee;
import com.ytc.common.model.User;
import com.ytc.common.result.ResultCode;
import com.ytc.common.result.ResultException;
import com.ytc.constant.QueryConstant;
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
	public List<Employee> getDetail(String loginId) {
		logger.info("inside com.ytc.service.impl.EmployeeService.getDetail loginId: "+loginId);
		CriteriaQuery<DalEmployee> criteria = entityManager.getCriteriaBuilder().createQuery(DalEmployee.class);
		Root<DalEmployee> data = criteria.from(DalEmployee.class); 
		criteria.select(data);
		criteria.where(entityManager.getCriteriaBuilder().equal(data.get("LOGIN_ID"), loginId));
		List<DalEmployee> dalEmployee = entityManager.createQuery(criteria).getResultList();
		List<Employee> employeeList = new ArrayList<>();
		
		for(DalEmployee dalEmp : dalEmployee){
			Employee employee = new Employee();
			employee.setACTIVE(dalEmp.getACTIVE());
			employee.setBUSINESS_UNIT(dalEmp.getBUSINESS_UNIT());
			employee.setEMAIL(dalEmp.getEMAIL());
			employee.setEMP_ID(dalEmp.getId());
			employee.setFIRST_NAME(dalEmp.getFIRST_NAME());
			employee.setLAST_NAME(dalEmp.getLAST_NAME());
			employee.setLOGIN_ID(dalEmp.getLOGIN_ID());
			employee.setMANAGER_ID(dalEmp.getMANAGER_ID());
			employee.setTITLE_ID(String.valueOf(dalEmp.getTITLE().getId()));
			employee.setSECURITY(dalEmp.getSECURITY());
			employee.setROLE_ID(dalEmp.getROLE_ID());
			employeeList.add(employee);

		}
		return employeeList;
	}
	
	@Override
	public DalEmployee getEmployeeDetail(Integer loginId) {
		DalEmployee dalEmployee = baseDao.getEntityById(DalEmployee.class,loginId);
		return dalEmployee;
	}
	

//	SELECT * FROM [INTERFACE].[dbo].[W_YTC_EMPLOYEE_DH]
//			where BASE_EMP_ID = 101 or LVL1_EMP_ID = 101 or LVL2_EMP_ID = 101 or LVL3_EMP_ID = 101 or LVL4_EMP_ID = 101 or LVL5_EMP_ID = 101 -- Andrew Martin Example

	@SuppressWarnings("unchecked")
	public List<String> getQueryResult(String queryString) {
		Query query = entityManager.createNativeQuery(queryString);
		List<String> results = query.getResultList();
		return results;	                
	}
	
	@Override
	public boolean isTbpUser(Integer loginId) {
		Map<String, Object> queryParams = new HashMap<>();
		boolean tbpUser=false;
		queryParams.put("empId",loginId);
		String sql=QueryConstant.TBP_USERS;
		List<Object> resultList =baseDao.getListFromNativeQuery(sql, queryParams);
		if(!resultList.isEmpty()){
			tbpUser=true;
		}
		return tbpUser;
	}
}


