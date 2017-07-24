package com.ytc.mail.impl;

import java.util.Map.Entry;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.ytc.common.model.EmailDetails;
import com.ytc.constant.EmailConstant;

public class YTCMailSenderServiceImpl{
	
	public void sendMail(EmailDetails emailDetails){

		Properties props = new Properties();
		props.put("mail.smtp.auth", "false");
//		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", emailDetails.getHost());
		props.put("mail.smtp.port", emailDetails.getPort());

		Session session = Session.getInstance(props, null);
		  /*new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });*/

		try {
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailDetails.getFromAddress()));
			if(emailDetails.getToAddress() != null){
				InternetAddress[] internetAddress = new InternetAddress[emailDetails.getToAddress().size()];
				int i = 0;
				for(String toAddress : emailDetails.getToAddress()){
					internetAddress[i] = new InternetAddress(toAddress);
					i++;
				}
				message.setRecipients(Message.RecipientType.TO, internetAddress);	
			}
			if(emailDetails.getCcAddress() != null){
				InternetAddress[] internetAddress = new InternetAddress[emailDetails.getCcAddress().size()];
				int i = 0;
				for(String toAddress : emailDetails.getCcAddress()){
					internetAddress[i] = new InternetAddress(toAddress);
					i++;
				}
				message.setRecipients(Message.RecipientType.CC, internetAddress);	
			}
			if(emailDetails.getSubject() != null){
				String subject = emailDetails.getEnvironment() + EmailConstant.COLON + emailDetails.getSubject();
				emailDetails.setSubject(subject);
			}
			message.setSubject(emailDetails.getSubject());
			
			//Multipart multipart = new MimeMultipart("related");
			Multipart multipart = new MimeMultipart();
			MimeBodyPart body = new MimeBodyPart();
			if(emailDetails.getText() != null){
				String text = emailDetails.getText() + String.format(EmailConstant.HTML_ENVIRONMENT_BODY, emailDetails.getEnvironment());
				emailDetails.setText(text);
			}
			body.setText(emailDetails.getText(),"UTF-8", "html");
			multipart.addBodyPart(body);
			
			for (Entry<String, byte[]> attach : emailDetails.getAttachment().entrySet()) {
			    
				if((attach.getKey()!=null)&&(attach.getValue()!=null)){
					MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
								  
					    String filename = attach.getKey()+".xlsx";//change accordingly  
					    DataSource ds = new ByteArrayDataSource(attach.getValue(), "application/excel");
					    messageBodyPart2.setDataHandler(new DataHandler(ds));  
					    messageBodyPart2.setFileName(filename); 
					    multipart.addBodyPart(messageBodyPart2);
					}
			}
			/*if((null!=emailDetails.getAttachment())&&(emailDetails.getAttachment().length>0)){
				MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
							  
				    String filename = "CreditMemo.xlsx";//change accordingly  
				    DataSource ds = new ByteArrayDataSource(emailDetails.getAttachment(), "application/excel");
				    messageBodyPart2.setDataHandler(new DataHandler(ds));  
				    messageBodyPart2.setFileName(filename); 
				    multipart.addBodyPart(messageBodyPart2);
				}*/
			message.setContent(multipart);
			
			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}
