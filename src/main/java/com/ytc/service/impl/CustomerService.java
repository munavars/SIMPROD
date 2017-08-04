
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
import com.ytc.dal.model.DalPricingHeader;
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
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("customerId", customerId);
		List<String> channelCodeList =  baseDao.getListFromNativeQuery(QueryConstant.GET_CHANNEL_CODE_FOR_CUSTOMER_ID, queryParams);
		if(channelCodeList != null && !channelCodeList.isEmpty()){
			customer.setChannelCode(channelCodeList.get(0));
		}
		return customer;

	}
	
	@Override
	public List<ProgramDetail> getCustomerDetailDashboard(Integer loginId) {
		
		List<ProgramDetail> dashboardDetailList= new ArrayList<ProgramDetail>();

		String sql = null;
		Map<String, Object> queryParams = new HashMap<>();
		String tbpQuery = QueryConstant.TBP_QUERY;
		List<String> userIdList = null;
		List<String> tbpList = baseDao.getListFromNativeQuery(tbpQuery, new HashMap<String, Object>());
		if(tbpList.contains(loginId)){
			sql = QueryConstant.TBP_CUSTOMER_LIST;
		}
		else{
			String queryString=QueryConstant.SIM_EMPLOYEE_HIER_LIST;
			
			queryParams.put("loginId", loginId);
			userIdList=baseDao.getListFromNativeQuery(queryString,queryParams);
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
				/*programDetail.setZmAppStatus(null!=dalProgramDetail.getZmAppStatus()?dalProgramDetail.getZmAppStatus().getId().toString():"0");
				programDetail.setZmAppDate(null!=dalProgramDetail.getZmAppDate()?ProgramServiceHelper.convertDateToString(dalProgramDetail.getZmAppDate().getTime(), ProgramConstant.DATE_FORMAT):"");
				programDetail.setTbpAppStatus(null!=dalProgramDetail.getTbAppStatus()?dalProgramDetail.getTbAppStatus().getId().toString():"0");
				programDetail.setTbpAppDate(null!=dalProgramDetail.getTbpAppDate()?ProgramServiceHelper.convertDateToString(dalProgramDetail.getTbpAppDate().getTime(), ProgramConstant.DATE_FORMAT):"");*/
				programDetail.setModifiedDate(null!=dalProgramDetail.getModifiedDate()?ProgramServiceHelper.convertDateToString(dalProgramDetail.getModifiedDate().getTime(), ProgramConstant.DATE_FORMAT):"");
				programDetail.setProgramStatus((dalProgramDetail.getStatus() != null) ? dalProgramDetail.getStatus().getId().toString() : "0" );
				programDetail.setProgramType(dalProgramDetail.getDalProgramType().getType());
				programDetail.setStatusHistory(getProgramStatusHistory(dalProgramDetail));
				programDetail.setActionReqByName(getActionRequiredByName(dalProgramDetail));
				dashboardDetailList.add(programDetail);
			}
		}
		getPricingDetails(dashboardDetailList, tbpList, userIdList, loginId);
		return dashboardDetailList;

	}
	
	private String getActionRequiredByName(DalProgramDetail dalProgramDetail) {
		String actionReqdByName = "";
		String approvalStatus = "";
		if(dalProgramDetail != null & dalProgramDetail.getDalWorkflowStatusList() != null){
			for(DalWorkflowStatus dalWorkflowStatus : dalProgramDetail.getDalWorkflowStatusList()){
				actionReqdByName=ProgramServiceHelper.getName(dalWorkflowStatus.getApprover());
				approvalStatus=dalWorkflowStatus.getApprovalStatus().getType();
			}
		}
		if((ProgramConstant.REJECTED_STATUS.equalsIgnoreCase(approvalStatus))||("".equalsIgnoreCase(approvalStatus)))
		{
			actionReqdByName=ProgramServiceHelper.getName(dalProgramDetail.getCreatedBy());
		}
		return actionReqdByName;
	}


	private void getPricingDetails(List<ProgramDetail> dashboardDetailList, List<String> tbpList,
			List<String> userIdList, Integer loginId) {
		String sql = null;
		Map<String, Object> queryParams = new HashMap<>();
		if(tbpList != null && tbpList.contains(loginId)){
			sql = QueryConstant.TBP_CUSTOMER_PRICING_LIST;
		}
		else if(userIdList != null && !userIdList.isEmpty()){
			sql=QueryConstant.PRICING_LIST;
			queryParams = new HashMap<>();
			queryParams.put("requestId", userIdList);
		}
		List<DalPricingHeader> resultList=baseDao.getlist(DalPricingHeader.class, sql, queryParams);
		if(resultList != null && !resultList.isEmpty()){
			if(dashboardDetailList == null){
				dashboardDetailList = new ArrayList<ProgramDetail>();
			}
			for(DalPricingHeader dalPricingHeader : resultList){
				ProgramDetail programDetail=new ProgramDetail();				
				programDetail.setCustomerNumber(dalPricingHeader.getCustomer().getCustomerNumber());
				programDetail.setCustomerName(dalPricingHeader.getCustomer().getCustomerName());
				programDetail.setProgramName(ProgramConstant.PRICING_FORM_TYPE);
				programDetail.setProgramId(dalPricingHeader.getId());
				programDetail.setBu(dalPricingHeader.getCreatedBy().getBUSINESS_UNIT());
				programDetail.setSubmitDate(ProgramServiceHelper.convertDateToString(dalPricingHeader.getCreatedDate().getTime(), ProgramConstant.DATE_FORMAT));
				programDetail.setModifiedDate(null!=dalPricingHeader.getModifiedDate()?ProgramServiceHelper.convertDateToString(dalPricingHeader.getModifiedDate().getTime(), ProgramConstant.DATE_FORMAT):"");
				programDetail.setProgramStatus((dalPricingHeader.getDalStatus() != null) ? dalPricingHeader.getDalStatus().getId().toString() : "0" );
				programDetail.setProgramType(dalPricingHeader.getDalProgramType().getType());
				programDetail.setStatusHistory(getPricingStatusHistory(dalPricingHeader));
				programDetail.setActionReqByName(getActionRequiredByName(dalPricingHeader));
				dashboardDetailList.add(programDetail);
			}
		}
	}


	private String getProgramStatusHistory(DalProgramDetail dalProgramDetail) {
		String statusHistory = null;
		StringBuilder statusBuilder = null;
		String approvalStatus="";
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
				approvalStatus=dalWorkflowStatus.getApprovalStatus().getType();
			}
			if(ProgramConstant.REJECTED_STATUS.equalsIgnoreCase(approvalStatus))
			{
				statusBuilder.append(ProgramConstant.BREAK_NEW_LINE_CODE);
				statusBuilder.append(ProgramServiceHelper.getName(dalProgramDetail.getCreatedBy()));
				statusBuilder.append(ProgramConstant.COLON_WITH_SPACE);
				statusBuilder.append(dalProgramDetail.getCreatedBy().getTITLE().getTitle());
				statusBuilder.append(ProgramConstant.COLON_WITH_SPACE);
				statusBuilder.append(ProgramConstant.PENDING_STATUS);
			}
			statusHistory = (statusBuilder != null) ? statusBuilder.toString() : ProgramConstant.STATUS_HISTORY_DATA_MESSAGE;
		}
		return statusHistory;
	}
	
	private String getActionRequiredByName(DalPricingHeader dalPricingHeader) {
		String actionReqdByName = "";
		String approvalStatus = "";
		if(dalPricingHeader != null & dalPricingHeader.getDalWorkflowStatusForPricingList() != null){
			for(DalWorkflowStatus dalWorkflowStatus : dalPricingHeader.getDalWorkflowStatusForPricingList()){
				actionReqdByName=ProgramServiceHelper.getName(dalWorkflowStatus.getApprover());
				approvalStatus=dalWorkflowStatus.getApprovalStatus().getType();
			}
		}
		if((ProgramConstant.REJECTED_STATUS.equalsIgnoreCase(approvalStatus))||("".equalsIgnoreCase(approvalStatus)))
		{
			actionReqdByName=ProgramServiceHelper.getName(dalPricingHeader.getCreatedBy());
		}
		return actionReqdByName;
	}

	private String getPricingStatusHistory(DalPricingHeader dalPricingHeader) {
		String statusHistory = null;
		StringBuilder statusBuilder = null;
		String approvalStatus="";
		if(dalPricingHeader != null & dalPricingHeader.getDalWorkflowStatusForPricingList() != null){
			Set<Integer> workflowIdSet = new HashSet<Integer>();
			for(DalWorkflowStatus dalWorkflowStatus : dalPricingHeader.getDalWorkflowStatusForPricingList()){
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
				approvalStatus=dalWorkflowStatus.getApprovalStatus().getType();
			}
			if(ProgramConstant.REJECTED_STATUS.equalsIgnoreCase(approvalStatus))
			{
				statusBuilder.append(ProgramConstant.BREAK_NEW_LINE_CODE);
				statusBuilder.append(ProgramServiceHelper.getName(dalPricingHeader.getCreatedBy()));
				statusBuilder.append(ProgramConstant.COLON_WITH_SPACE);
				statusBuilder.append(dalPricingHeader.getCreatedBy().getTITLE().getTitle());
				statusBuilder.append(ProgramConstant.COLON_WITH_SPACE);
				statusBuilder.append(ProgramConstant.PENDING_STATUS);
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