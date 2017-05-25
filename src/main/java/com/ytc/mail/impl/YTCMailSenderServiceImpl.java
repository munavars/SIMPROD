package com.ytc.mail.impl;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.ytc.common.model.EmailDetails;

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
			message.setSubject(emailDetails.getSubject());
			
			Multipart multipart = new MimeMultipart("related");
			MimeBodyPart body = new MimeBodyPart();
			body.setText(emailDetails.getText(),"UTF-8", "html");
			multipart.addBodyPart(body);
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
