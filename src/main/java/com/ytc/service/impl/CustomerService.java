package com.ytc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.ytc.dal.model.DalWorkflowStatus;
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

		String sql = null;
		Map<String, Object> queryParams = new HashMap<>();
		String tbpQuery = QueryConstant.TBP_QUERY;
		List<String> tbpList = baseDao.getListFromNativeQuery(tbpQuery, new HashMap<String, Object>());
		if(tbpList.contains(loginId)){
			sql = QueryConstant.TBP_CUSTOMER_LIST;
		}
		else{
			String queryString=QueryConstant.EMPLOYEE_HIER_LIST;
			
			queryParams.put("loginId", loginId);
			List<String> userIdList=baseDao.getListFromNativeQuery(queryString,queryParams);
			if(userIdList.isEmpty()){
				return dashboardDetailList;
			}
			sql=QueryConstant.CUSTOMER_LIST;
			queryParams = new HashMap<>();
			queryParams.put("requestId", userIdList);
		}

		List<DalProgramDetail> resultList=baseDao.getlist(DalProgramDetail.class, sql, queryParams);
		if(resultList != null){
			for(DalProgramDetail dalProgramDetail : resultList){
				ProgramDetail programDetail=new ProgramDetail();				
				programDetail.setCustomerNumber(dalProgramDetail.getDalProgramHeader().getCustomer().getCustomerNumber());
				programDetail.setCustomerName(dalProgramDetail.getDalProgramHeader().getCustomer().getCustomerName());
				programDetail.setProgramName(dalProgramDetail.getProgramMaster().getProgram());
				programDetail.setProgramId(dalProgramDetail.getId());
				programDetail.setBu(dalProgramDetail.getDalProgramHeader().getCustomer().getBu());
				programDetail.setSubmitDate(ProgramServiceHelper.convertDateToString(dalProgramDetail.getCreatedDate().getTime(), ProgramConstant.DATE_FORMAT));
				programDetail.setZmAppStatus(null!=dalProgramDetail.getZmAppStatus()?dalProgramDetail.getZmAppStatus().getId().toString():"0");
				programDetail.setZmAppDate(null!=dalProgramDetail.getZmAppDate()?ProgramServiceHelper.convertDateToString(dalProgramDetail.getZmAppDate().getTime(), ProgramConstant.DATE_FORMAT):"");
				programDetail.setTbpAppStatus(null!=dalProgramDetail.getTbAppStatus()?dalProgramDetail.getTbAppStatus().getId().toString():"0");
				programDetail.setTbpAppDate(null!=dalProgramDetail.getTbpAppDate()?ProgramServiceHelper.convertDateToString(dalProgramDetail.getTbpAppDate().getTime(), ProgramConstant.DATE_FORMAT):"");
				programDetail.setModifiedDate(null!=dalProgramDetail.getModifiedDate()?ProgramServiceHelper.convertDateToString(dalProgramDetail.getModifiedDate().getTime(), ProgramConstant.DATE_FORMAT):"");
				programDetail.setProgramStatus((dalProgramDetail.getStatus() != null) ? dalProgramDetail.getStatus().getId().toString() : "0" );
				programDetail.setProgramType(dalProgramDetail.getDalProgramType().getType());
				programDetail.setStatusHistory(getProgramStatusHistory(dalProgramDetail));
				dashboardDetailList.add(programDetail);
			}
		}
		return dashboardDetailList;

	}
	
	private String getProgramStatusHistory(DalProgramDetail dalProgramDetail) {
		String statusHistory = null;
		StringBuilder statusBuilder = null;
		if(dalProgramDetail != null & dalProgramDetail.getDalWorkflowStatusList() != null){
			Set<Integer> workflowIdSet = new HashSet<Integer>();
			for(DalWorkflowStatus dalWorkflowStatus : dalProgramDetail.getDalWorkflowStatusList()){
				boolean isUnique = workflowIdSet.add(dalWorkflowStatus.getId());
				if(!isUnique){
					continue;
				}
				if(statusBuilder == null){
					statusBuilder = new StringBuilder();
				}
				else{
					//Add break;
					statusBuilder.append(ProgramConstant.BREAK_NEW_LINE_CODE);
				}
				statusBuilder.append(ProgramServiceHelper.getName(dalWorkflowStatus.getApprover()));
				statusBuilder.append(ProgramConstant.COLON_WITH_SPACE);
				statusBuilder.append(dalWorkflowStatus.getApprover().getTITLE().getTitle());
				statusBuilder.append(ProgramConstant.COLON_WITH_SPACE);
				statusBuilder.append(dalWorkflowStatus.getApprovalStatus().getType());
			}
			
			statusHistory = (statusBuilder != null) ? statusBuilder.toString() : ProgramConstant.STATUS_HISTORY_DATA_MESSAGE;
		}
		return statusHistory;
	}


	@Override
	public Customer getDetail(Integer customerId) {
		DalCustomer dalCustomer = baseDao.getById(DalCustomer.class, customerId);
		ModelMapper modelMapper = new ModelMapper();
		Customer customer = modelMapper.map(dalCustomer, Customer.class);
		return customer;
	}
}


