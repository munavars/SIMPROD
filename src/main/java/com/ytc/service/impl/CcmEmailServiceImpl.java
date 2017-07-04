package com.ytc.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.ytc.common.model.EmailDetails;
import com.ytc.mail.intf.IYTCMailConnectorService;
import com.ytc.service.ICcmEmailService;

/**
 * Class : CcmEmailServiceImpl
 * Purpose : This class prepare the data for the mail to be send and calls the email connector class.
 * @author Cognizant.
 *
 */
@Component
@Configuration
@PropertySource("classpath:/config/sim.properties")
public class CcmEmailServiceImpl implements ICcmEmailService {
	@Autowired
	private Environment env;
	
	@Autowired
	private IYTCMailConnectorService ytcMailConnectorService;
	
	
	/**
	 * This method is used to prepare the email data and calls the appropriate method to send the details. 
	 */
	public void sendEmailData(byte[] excelByte) {
		
			EmailDetails emailDetails = new EmailDetails();
			List<String> toEmailIdList = new ArrayList<String>();			
			toEmailIdList.add(env.getProperty("mail.ccm.to"));
			//toEmailIdList.add("ArunThomas.Purushothaman@yokohamatire.com");
			//toEmailIdList.add("Munavar.SheikAmeer@yokohamatire.com");
			emailDetails.setFromAddress(env.getProperty("mail.ccm.from"));
			emailDetails.setToAddress(toEmailIdList);
			emailDetails.setSubject("Credit Memo");
			emailDetails.setText("Sample Content. To be replaced with actual body");
			emailDetails.setAttachment(excelByte);
			
			ytcMailConnectorService.sendEmail(emailDetails);
		
	}

		
	
}
