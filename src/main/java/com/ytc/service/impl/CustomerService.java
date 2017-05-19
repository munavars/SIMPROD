package com.ytc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytc.common.model.Customer;
import com.ytc.common.model.CustomerDetail;
import com.ytc.common.model.EmployeeHierarchy;
import com.ytc.common.model.ProgramDetail;
import com.ytc.constant.ProgramConstant;
import com.ytc.constant.QueryConstant;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalCustomer;
import com.ytc.dal.model.DalEmployeeHierarchy;
import com.ytc.dal.model.DalProgramDetail;
import com.ytc.dal.model.DalProgramHeader;
import com.ytc.helper.ProgramServiceHelper;
import com.ytc.service.ICustomerService;

public class CustomerService implements ICustomerService {

	@Autowired
	private IDataAccessLayer baseDao;



	public CustomerService() {

	}


	@Override
	public CustomerDetail getCustomerDetail(Integer customerId) {
		DalCustomer dalCustomer = baseDao.getById(DalCustomer.class, customerId);
		ModelMapper modelMapper = new ModelMapper();
		CustomerDetail customer = modelMapper.map(dalCustomer, CustomerDetail.class);
		DalEmployeeHierarchy dalEmployeeHierarchy = baseDao.getEntityById(DalEmployeeHierarchy.class, Integer.parseInt(dalCustomer.getAccountManager()));
		EmployeeHierarchy employeeHierarchy=modelMapper.map(dalEmployeeHierarchy,EmployeeHierarchy.class);
		customer.setEmployeeHierarchy(employeeHierarchy);
		return customer;

	}
	
	@Override
	public List<ProgramDetail> getCustomerDetailDashboard(Integer loginId) {
		
		List<ProgramDetail> dashboardDetailList= new ArrayList<ProgramDetail>();

		String queryString=QueryConstant.EMPLOYEE_HIER_LIST;
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("loginId", loginId);
		List<String> userIdList=baseDao.getListFromNativeQuery(queryString,queryParams);
		if(userIdList.isEmpty()){
			return dashboardDetailList;
		}
		String sql=QueryConstant.CUSTOMER_LIST;
		queryParams = new HashMap<>();
		queryParams.put("requestId", userIdList);
		List<DalProgramHeader> resultList=baseDao.getlist(DalProgramHeader.class, sql, queryParams);
		for (Iterator<DalProgramHeader> iterator = resultList.iterator(); iterator.hasNext();) {
			DalProgramHeader dalProgramHeader = (DalProgramHeader) iterator.next();
			for (Iterator<DalProgramDetail> pgmiterator = dalProgramHeader.getDalProgramDetailList().iterator(); pgmiterator.hasNext();) {
				DalProgramDetail dalProgramDetail = (DalProgramDetail) pgmiterator.next();
				ProgramDetail programDetail=new ProgramDetail();				
				programDetail.setCustomerNumber(dalProgramHeader.getCustomer().getCustomerNumber());
				programDetail.setCustomerName(dalProgramHeader.getCustomer().getCustomerName());
				programDetail.setProgramName(dalProgramDetail.getProgramMaster().getProgram());
				programDetail.setProgramId(dalProgramDetail.getId());
				programDetail.setBu(dalProgramHeader.getCustomer().getBu());
				programDetail.setSubmitDate(ProgramServiceHelper.convertDateToString(dalProgramDetail.getCreatedDate().getTime(), ProgramConstant.DATE_FORMAT));
				programDetail.setZmAppStatus(null!=dalProgramDetail.getZmAppStatus()?dalProgramDetail.getZmAppStatus().toString():"0");
				programDetail.setZmAppDate(null!=dalProgramDetail.getZmAppDate()?ProgramServiceHelper.convertDateToString(dalProgramDetail.getZmAppDate().getTime(), ProgramConstant.DATE_FORMAT):"");
				programDetail.setTbpAppStatus(null!=dalProgramDetail.getTbAppStatus()?dalProgramDetail.getTbAppStatus().toString():"0");
				programDetail.setTbpAppDate(null!=dalProgramDetail.getTbpAppDate()?ProgramServiceHelper.convertDateToString(dalProgramDetail.getTbpAppDate().getTime(), ProgramConstant.DATE_FORMAT):"");
				programDetail.setProgramType(dalProgramDetail.getDalProgramType().getType());
				dashboardDetailList.add(programDetail);
			}
		}
		return dashboardDetailList;

	}
	
	@Override
	public Customer getDetail(Integer customerId) {
		DalCustomer dalCustomer = baseDao.getById(DalCustomer.class, customerId);
		ModelMapper modelMapper = new ModelMapper();
		Customer customer = modelMapper.map(dalCustomer, Customer.class);
		return customer;

	}
}


