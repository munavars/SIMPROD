package com.ytc.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.ytc.common.model.EmailDetails;
import com.ytc.constant.ProgramConstant;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalCcmAccrualData;
import com.ytc.dal.model.DalProgramDetAchieved;
import com.ytc.dal.model.DalProgramDetPaid;
import com.ytc.dal.model.DalProgramDetail;
import com.ytc.dal.model.DalTagItems;
import com.ytc.helper.ProgramServiceHelper;
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

	/**
	 * This method is used to prepare the email data and calls the appropriate method to send the details. 
	 */
	public void sendEmailData(DalCcmAccrualData dalCcmAccrualData, String comments, DalProgramDetail dalProgramDetail, IDataAccessLayer baseDao) {
		
			EmailDetails emailDetails = new EmailDetails();
			List<String> toEmailIdList = new ArrayList<String>();
			List<String> ccEmailIdList = new ArrayList<String>();
			String paidInclude="";
			String paidExclude="";
			String achInclude="";
			String achExclude="";
			 List<DalProgramDetPaid> dalProgramDetPaidList = dalProgramDetail.getDalProgramDetPaidList().stream().sorted((e1, e2) -> e1.getTagId().compareTo(e2.getTagId())).collect(Collectors.toList());
			for (Iterator<DalProgramDetPaid> iterator = dalProgramDetPaidList.iterator(); iterator.hasNext();) {
				DalProgramDetPaid dalProgramDetPaid = (DalProgramDetPaid) iterator.next();
				 if("1".equalsIgnoreCase(dalProgramDetPaid.getMethod())){
					 paidInclude=paidInclude+baseDao.getEntityById(DalTagItems.class, dalProgramDetPaid.getTagId()).getItem()+"  :  "+dalProgramDetPaid.getDisplayValue()+"<br>";
		         }
				 if("2".equalsIgnoreCase(dalProgramDetPaid.getMethod())){
					 paidExclude=paidExclude+baseDao.getEntityById(DalTagItems.class, dalProgramDetPaid.getTagId()).getItem()+"  :  "+dalProgramDetPaid.getDisplayValue()+"<br>";
		         }
				
			}
			List<DalProgramDetAchieved> dalProgramDetAchieved = dalProgramDetail.getDalProgramDetAchievedList().stream().sorted((e1, e2) -> e1.getAchTagId().compareTo(e2.getAchTagId())).collect(Collectors.toList());
				for (Iterator<DalProgramDetAchieved> iterator = dalProgramDetAchieved.iterator(); iterator.hasNext();) {
					DalProgramDetAchieved dalPgmDetAchieved = (DalProgramDetAchieved) iterator.next();
					 if("1".equalsIgnoreCase(dalPgmDetAchieved.getAchMethod())){
						 achInclude=achInclude+baseDao.getEntityById(DalTagItems.class, dalPgmDetAchieved.getAchTagId()).getItem()+"  :  "+dalPgmDetAchieved.getDisplayValue()+"<br>";
			         }
					 if("2".equalsIgnoreCase(dalPgmDetAchieved.getAchMethod())){
						 achExclude=achExclude+baseDao.getEntityById(DalTagItems.class, dalPgmDetAchieved.getAchTagId()).getItem()+"  :  "+dalPgmDetAchieved.getDisplayValue()+"<br>";
			         }
					
				}
			//toEmailIdList.add(env.getProperty("mail.ccm.to"));
			//toEmailIdList.add("ArunThomas.Purushothaman@yokohamatire.com");
			toEmailIdList.add(env.getProperty("mail.ccm.review.to"));
			ccEmailIdList.add(env.getProperty("mail.ccm.review.cc"));
			emailDetails.setFromAddress(env.getProperty("mail.ccm.from"));
			emailDetails.setToAddress(toEmailIdList);
			emailDetails.setCcAddress(ccEmailIdList);
			emailDetails.setSubject("CCM Approval");
			//emailDetails.setText("Sample Content. To be replaced with actual body");
			String accStartDate=ProgramServiceHelper.convertDateToString(dalCcmAccrualData.getAccrualStartDate().getTime(), ProgramConstant.DATE_FORMAT);
			String accEndDate=ProgramServiceHelper.convertDateToString(dalCcmAccrualData.getAccrualEndDate().getTime(), ProgramConstant.DATE_FORMAT);
			String to="";
			if("Consumer".equalsIgnoreCase(dalCcmAccrualData.getBu())){
				to="consumerfinance@yokohamatire.com";
			}else{
				to="commercialfinance@yokohamatire.com";
			}
			String body="\n"+
					"<body>\n"+
					"\t<table style=\'width: 400px\'>\n"+
					"\t\t<tr style=\'height: 36px\' >\n"+
					"\t\t\t<td colspan=\'2\' align=\'center\' \n"+
					" style=\'background-color: #C0C0C0; font-size: medium ; font-weight: bold\' > CREDIT MEMO APPROVAL</td>\n"+
					"\t\t</tr> \n"+
					"\t\t<tr>\n"+
					"\t\t\t<td style='width:40%'>Account Number</td>\n"+
					"\t\t\t<td >"+dalCcmAccrualData.getCustomerNumber()+"</td>\t<!-- bill-to -->\n"+
					"\t\t</tr> \n"+
					"\t\t<tr>\n"+
					"\t\t\t<td >Ship To Number</td>\n"+
					"\t\t\t<td> "+dalCcmAccrualData.getBillToNo()+" </td>\t<!-- ship-to -->\n"+
					"\t\t</tr>\n"+
					"\t\t<tr> \n"+
					"\t\t\t<tr>\n"+
					"\t\t\t\t<td >Account Name</td>\n"+
					"\t\t\t\t<td> "+dalCcmAccrualData.getCustomerName()+" </td>\t<!-- bill-to name -->\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr>\n"+
					"\t\t\t\t<td >Credit Amt$</td>\n"+
					"\t\t\t\t<td> "+dalCcmAccrualData.getCreditEarned()+" </td>\t<!-- amount to pay -->\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr>\n"+
					"\t\t\t\t<td>Pay Out Freq</td>\n"+
					"\t\t\t\t<td> "+dalCcmAccrualData.getFrequency()+" </td>\t\t<!-- frequency -->\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr>\n"+
					"\t\t\t\t<td >Acct Period</td>\n"+
					"\t\t\t\t<td> "+accStartDate+" to "+accEndDate+" </td>\t<!-- year month in yymm format -->\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr>\n"+
					"\t\t\t\t<td >Analyst</td>\n"+
					"\t\t\t\t<td> Analyst </td>\t<!-- TBP analyst -->\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr>\n"+
					"\t\t\t\t<td >RequestBy</td>\n"+
					"\t\t\t\t<td> "+dalCcmAccrualData.getAdjustedUser()+" </td>\t<!-- ZM Mgr -->\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr>\n"+
					"\t\t\t\t<td >GL Code</td>\n"+
					"\t\t\t\t<td> "+dalCcmAccrualData.getGlCode()+" </td>\t\t<!-- GL -->\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr>\n"+
					"\t\t\t\t<td >SAP GL Code</td>\n"+
					"\t\t\t\t<td> "+dalCcmAccrualData.getSapGlCode()+" </td>\t\t<!-- GL -->\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr>\n"+
					"\t\t\t\t<td >SendTo</td>\n"+
					"\t\t\t\t<td> "+to+" </td>\t\t<!-- ZM email -->\n"+
					"\t\t\t</tr> \n"+
					"\t\t</table>\n"+
					"\n"+
					"\t\t<table style=\'width: 400px\'> \n"+
					"\t\t\t<tr style=\'height: 28px\' >\n"+
					"\t\t\t\t<td colspan=\'3\' align=\'center\' style=\'background-color: #C0C0C0; font-weight: bold\'>PROGRAM DETAIL</td>\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr style=\'font-weight: bold\'>\n"+
					"\t\t\t\t<td >ID</td>\n"+
					"\t\t\t\t<td >Program Type</td>\n"+
					"\t\t\t\t<td style=\'text-align: right\'>$AMT</td>\n"+
					"\t\t\t</tr>\n"+
					"\n"+
					"\t\t\t\n"+
					"\t\t\t\n"+
					"\t\t\t\t<tr >\n"+
					"\t\t\t\t\t<td > "+dalCcmAccrualData.getProgramId()+" </td>\n"+
					"\t\t\t\t\t<td > "+dalProgramDetail.getDalProgramType().getType()+" </td>\n"+
					"\t\t\t\t\t<td style=\'text-align: right\' > "+dalCcmAccrualData.getAmount()+" </td>\n"+
					"\t\t\t\t</tr>\n"+
					"\n"+
					"\t\t\t\n"+
					"\t\t</table>\n"+
					"\n"+
					"\n"+
					
					"\t\t<table style=\'width: 400px\'>\n"+
					"\t\t\t<tr style=\'height: 36px\' >\n"+
					"\t\t\t\t<td colspan=\'2\' align=\'center\' style=\'background-color: #C0C0C0; font-size: medium ; font-weight: bold\'> DESCRIPTION</td>\n"+
					"\t\t\t</tr>\n"+
					"\t\t\t\t<td> Accounting Period </td>"+
					"\t\t\t\t<td> "+dalCcmAccrualData.getPeriodId()+" </td>\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr>\n"+
					"\t\t\t\t<td> Program Name </td>"+
					"\t\t\t\t<td> "+dalCcmAccrualData.getProgramName()+" </td>\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr>\n"+
					"\t\t\t\t<td> Program Description </td>"+
					"\t\t\t\t<td> "+dalCcmAccrualData.getDescription()+" </td>\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr>\n"+
					"\t\t\t\t<td colspan=\'2\' style=\' vertical-align:top \' > </td>\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t\t<td> Paid Based On </td>"+
					"\t\t\t\t<td> "+dalProgramDetail.getPaidBasedOn().getBaseItem()+" </td>\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr>\n"+
					"\t\t\t\t<td colspan=\'2\' style=\' vertical-align:top \' > Including : </td>\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr>\n"+
					"\t\t\t\t<td colspan=\'2\' style=\' vertical-align:top \' > "+paidInclude+" </td>\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr>\n"+
					"\t\t\t\t<td colspan=\'2\' style=\' vertical-align:top \' > </td>\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr>\n"+
					"\t\t\t\t<td colspan=\'2\' style=\' vertical-align:top \' > Excluding : </td>\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr>\n"+
					"\t\t\t\t<td colspan=\'2\' style=\' vertical-align:top \' > "+paidExclude+" </td>\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr>\n"+
					"\t\t\t<tr>\n"+
					"\t\t\t\t<td colspan=\'2\' style=\' vertical-align:top \' > </td>\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t\t<td> Achieve Based On </td>"+
					"\t\t\t\t<td> "+dalProgramDetail.getAchBasedMetric().getBaseItem()+" </td>\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr>\n"+
					"\t\t\t\t<td colspan=\'2\' style=\' vertical-align:top \' > Including :</td>\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr>\n"+
					"\t\t\t\t<td colspan=\'2\' style=\' vertical-align:top \' > "+achInclude+" </td>\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr>\n"+
					"\t\t\t\t<td colspan=\'2\' style=\' vertical-align:top \' > </td>\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr>\n"+
					"\t\t\t\t<td colspan=\'2\' style=\' vertical-align:top \' > Excluding :</td>\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr>\n"+
					"\t\t\t\t<td colspan=\'2\' style=\' vertical-align:top \' > "+achExclude+" </td>\n"+
					"\t\t\t</tr> \n"+
/*					"\t\t\t<tr>\n"+
					
					"\t\t\t\t<td colspan=\'2\' style=\' vertical-align:top \' > "+dalCcmAccrualData.getDescription()+" </td>\n"+
					"\t\t\t</tr> \n"+*/
					"\t\t</table> \n"+
					
					"\t\t<table style=\'width: 400px\'>\n"+
					"\t\t\t<tr style=\'height: 36px\' >\n"+
					"\t\t\t\t<td colspan=\'2\' align=\'center\' style=\'background-color: #C0C0C0; font-size: medium ; font-weight: bold\'> COMMENTS</td>\n"+
					"\t\t\t</tr>\n"+
					"\t\t\t<tr style=\'height: 96px\' >\n"+
					"\t\t\t\t<td colspan=\'2\' style=\' vertical-align:top \' > "+comments+" </td>\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr>\n"+
					"\t\t\t\t<td style=\'width: 115px\'> Report ID:</td>\n"+
					"\t\t\t\t<td> Report Id </td>\n"+
					"\t\t\t</tr>\n"+
					"\t\t\t<tr>\n"+
					"\t\t\t\t<td >Report Date:</td>\n"+
					"\t\t\t\t<td> ReportDate </td>\n"+
					"\t\t\t</tr>\n"+
					"\t\t</table> \n"+
					
										
					"\t\t<table style=\'width: 400px\'> \n"+
					"\t\t\t<tr style=\'height: 28px\' >\n"+
					"\t\t\t\t<td colspan=\'3\' align=\'center\' style=\'background-color: #C0C0C0; font-weight: bold\' >ROUTING</td>\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr style=\'font-weight: bold\'>\n"+
					"\t\t\t\t<td >Date Rcvd</td>\n"+
					"\t\t\t\t<td >Level</td>\n"+
					"\t\t\t\t<td >SIGNATURE</td>\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr >\n"+
					"\t\t\t\t<td />\n"+
					"\t\t\t\t<td >Finance Manager</td>\n"+
					"\t\t\t\t<td />\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr >\n"+
					"\t\t\t\t<td />\n"+
					"\t\t\t\t<td >Sr. Manager</td>\n"+
					"\t\t\t\t<td />\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr >\n"+
					"\t\t\t\t<td />\n"+
					"\t\t\t\t<td >Sr. Director</td>\n"+
					"\t\t\t\t<td />\n"+
					"\t\t\t</tr> \n"+
					"\t\t\t<tr >\n"+
					"\t\t\t\t<td />\n"+
					"\t\t\t\t<td >SVP, CSO & Deputy CFO</td>\n"+
					"\t\t\t\t<td />\n"+
					"\t\t\t</tr>\n"+
					"\t\t</table>\n"+
					"\t</body>\n"+
					"\n"+
					"\n"+
					"\n"+
					"\t";		
			emailDetails.setText(body);
			ytcMailConnectorService.sendEmail(emailDetails);
		
	}

		
	
}
