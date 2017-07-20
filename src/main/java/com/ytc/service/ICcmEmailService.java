package com.ytc.service;

import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalCcmAccrualData;
import com.ytc.dal.model.DalProgramDetail;

public interface ICcmEmailService {
	
	void sendEmailData(byte[] excelByte);

	void sendEmailData(DalCcmAccrualData dalCcmAccrualData, String comments, DalProgramDetail dalProgramDetail, IDataAccessLayer baseDao);

	
}
