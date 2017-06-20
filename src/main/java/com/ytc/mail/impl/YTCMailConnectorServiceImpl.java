package com.ytc.mail.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.ytc.common.model.EmailDetails;
import com.ytc.mail.intf.IYTCMailConnectorService;

@Component
@Configuration
@PropertySource("classpath:/config/sim.properties")
public class YTCMailConnectorServiceImpl implements IYTCMailConnectorService{
	
	@Autowired
	private Environment env;

	@Autowired
	private YTCMailSenderServiceImpl ytcMailSenderService;
	
	public void sendEmail(EmailDetails emailDetails){
		if(emailDetails != null && env != null){
			emailDetails.setHost(env.getProperty("mail.smtp.host"));
			emailDetails.setPort(env.getProperty("mail.smtp.port"));
			emailDetails.setEnvironment(env.getProperty("mail.environment"));
				
			ytcMailSenderService.sendMail(emailDetails);
		}
	}
}
