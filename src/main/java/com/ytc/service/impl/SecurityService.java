package com.ytc.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ytc.common.model.User;
import com.ytc.common.result.ResultCode;
import com.ytc.common.result.ResultException;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalUser;
import com.ytc.service.ISecurityService;

public class SecurityService implements ISecurityService {

	@Autowired
	private IDataAccessLayer baseDao;



	public SecurityService() {

	}


	@Override
	public User getUser(String customerName) {
		User user = new User();
		user.setUserName("hihihihihi");
		return user;

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
}


