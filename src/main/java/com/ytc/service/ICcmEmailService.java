package com.ytc.service;

import com.ytc.dal.model.DalCcmAccrualData;

public interface ICcmEmailService {
	
	void sendEmailData(byte[] excelByte);

	void sendEmailData(DalCcmAccrualData dalCcmAccrualData);

	
}
