/**
 * 
 */
package com.ytc.service.util;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ytc.constant.ProgramConstant;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalGLCode;
import com.ytc.dal.model.DalPaidType;
import com.ytc.dal.model.DalPricingType;
import com.ytc.dal.model.DalProgramDetAchieved;
import com.ytc.dal.model.DalProgramDetPaid;
import com.ytc.dal.model.DalProgramDetail;
import com.ytc.dal.model.DalProgramDetailTier;
import com.ytc.dal.model.DalTagItems;
import com.ytc.helper.ProgramServiceHelper;

/**
 * @author ArunP
 *
 */
public class PdfGenerator {

	/**
	 * @param args
	 * @return 
	 */
	
	public byte[] generatePdf(IDataAccessLayer baseDao, String id) {

		/*PdfWriter writer=new PdfGenerator().generatePdf();
		return writer;*/
		Document document = new Document();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
	      try
	      {
	         DalProgramDetail dalpgm=baseDao.getById(DalProgramDetail.class, Integer.parseInt(id));
	    	 //PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:/Customer page/Customer.pdf"));
	         PdfWriter writer = PdfWriter.getInstance(document, stream);
	         document.open();
	         Font f1 = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);
	         Font f2 = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);
	         f1.setColor(BaseColor.BLUE);
	         f2.setColor(BaseColor.DARK_GRAY);
	         document.add(new Paragraph("CUSTOMER DETAILS",f1));
	         PdfPTable custTable = new PdfPTable(3); // 3 columns.
	         custTable.setWidthPercentage(100); //Width 100%
	         custTable.setSpacingBefore(10f); //Space before table
	         custTable.setSpacingAfter(10f); //Space after table
	  
	         //Set Column widths
	         float[] columnWidths = {1f, 1f, 1f};
	         custTable.setWidths(columnWidths);
	         
	         PdfPCell cell = new PdfPCell(new Paragraph("Customer Name: "+dalpgm.getDalProgramHeader().getCustomer().getCustomerName()));
	         cell.setBorderColor(BaseColor.BLACK);
	         cell.setPaddingLeft(10);
	         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         custTable.addCell(cell);

	         
	         cell = new PdfPCell(new Paragraph("Customer Number: "+dalpgm.getDalProgramHeader().getCustomer().getCustomerNumber()));
	         cell.setBorderColor(BaseColor.BLACK);
	         cell.setPaddingLeft(10);
	         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         custTable.addCell(cell);
	         
	         cell = new PdfPCell(new Paragraph("Business Unit: "+dalpgm.getDalProgramHeader().getBu()));
	         cell.setBorderColor(BaseColor.BLACK);
	         cell.setPaddingLeft(10);
	         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         custTable.addCell(cell);
	  
	         cell = new PdfPCell(new Paragraph("Request  Id: "+dalpgm.getDalProgramHeader().getRequest().getId()));
	         cell.setBorderColor(BaseColor.BLACK);
	         cell.setPaddingLeft(10);
	         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         custTable.addCell(cell);

	         /*if(dalpgm.getZmAppById()!=null){
	         
	         cell = new PdfPCell(new Paragraph("Zone Manager Approved By: "+dalpgm.getZmAppById().getFIRST_NAME()+" "+dalpgm.getZmAppById().getLAST_NAME()));
	         }else{
	        	 cell = new PdfPCell(new Paragraph("Zone Manager Approved By: "));
	         }*/
	         
	         
	         cell = new PdfPCell(new Paragraph("Status: "+dalpgm.getStatus().getType()));
	         
	         cell.setBorderColor(BaseColor.BLACK);
	         cell.setPaddingLeft(10);
	         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         custTable.addCell(cell);
	         
	         cell = new PdfPCell(new Paragraph("Accrued Amount: 0")); //This should be changed once Accrual Data information is available.
	         cell.setBorderColor(BaseColor.BLACK);
	         cell.setPaddingLeft(10);
	         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         custTable.addCell(cell);
	         
	         cell = new PdfPCell(new Paragraph("Requested By: "+dalpgm.getDalProgramHeader().getRequest().getFIRST_NAME()+" "+dalpgm.getDalProgramHeader().getRequest().getLAST_NAME()));
	         cell.setBorderColor(BaseColor.BLACK);
	         cell.setPaddingLeft(10);
	         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         custTable.addCell(cell);
	         /*if(dalpgm.getZmAppDate()!=null){
	         cell = new PdfPCell(new Paragraph("Zone Manager Approved Date: "+ProgramServiceHelper.convertDateToString(dalpgm.getZmAppDate().getTime(), ProgramConstant.DATE_FORMAT)));
	         }else{
	        	 cell = new PdfPCell(new Paragraph("Zone Manager Approved Date: "));
	         }*/
	         
	         cell = new PdfPCell(new Paragraph(" "));
	         
	         
	         cell.setBorderColor(BaseColor.BLACK);
	         cell.setPaddingLeft(10);
	         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         custTable.addCell(cell);
	         
	         cell = new PdfPCell(new Paragraph("Paid Amount: 0")); //This should be changed once Accrual Data information is available.
	         cell.setBorderColor(BaseColor.BLACK);
	         cell.setPaddingLeft(10);
	         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         custTable.addCell(cell);
	         if(dalpgm.getDalProgramHeader()!=null && dalpgm.getDalProgramHeader().getRequestDate() !=null ){
	         cell = new PdfPCell(new Paragraph("Requested Date: "+ProgramServiceHelper.convertDateToString(dalpgm.getDalProgramHeader().getRequestDate().getTime(), ProgramConstant.DATE_FORMAT)));
	         }else{
	        	 cell = new PdfPCell(new Paragraph("Requested Date: "));
	         }
	         cell.setBorderColor(BaseColor.BLACK);
	         cell.setPaddingLeft(10);
	         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         custTable.addCell(cell);
	         /*if(dalpgm.getTbpAppById()!=null){
	         cell = new PdfPCell(new Paragraph("TBP Approved By: "+dalpgm.getTbpAppById().getFIRST_NAME()+" "+dalpgm.getTbpAppById().getLAST_NAME()));
	         }else{
	        	 cell = new PdfPCell(new Paragraph("TBP Approved By: "));
	         }*/
	         
	         cell = new PdfPCell(new Paragraph(" "));
	         
	         
	         cell.setBorderColor(BaseColor.BLACK);
	         cell.setPaddingLeft(10);
	         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         custTable.addCell(cell);
	         
	         cell = new PdfPCell(new Paragraph("Balance: 0")); //This should be changed once Accrual Data information is available.
	         cell.setBorderColor(BaseColor.BLACK);
	         cell.setPaddingLeft(10);
	         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         custTable.addCell(cell);
	         
	         cell = new PdfPCell(new Paragraph("Created By: "+dalpgm.getCreatedBy().getFIRST_NAME()+" "+dalpgm.getCreatedBy().getLAST_NAME()));
	         cell.setBorderColor(BaseColor.BLACK);
	         cell.setPaddingLeft(10);
	         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         custTable.addCell(cell);
	         
	         /*if(dalpgm.getTbpAppDate()!=null){
	         cell = new PdfPCell(new Paragraph("TBP Approved Date: "+ProgramServiceHelper.convertDateToString(dalpgm.getTbpAppDate().getTime(), ProgramConstant.DATE_FORMAT)));
	         }else{
	        	 cell = new PdfPCell(new Paragraph("TBP Approved Date: "));
	         }*/
	         
	         cell = new PdfPCell(new Paragraph(" "));
	         
	         
	         cell.setBorderColor(BaseColor.BLACK);
	         cell.setPaddingLeft(10);
	         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         custTable.addCell(cell);
	         
	         cell = new PdfPCell(new Paragraph("Program Type: "+dalpgm.getDalProgramType().getType()));
	         cell.setBorderColor(BaseColor.BLACK);
	         cell.setPaddingLeft(10);
	         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         custTable.addCell(cell);
	         
	         cell = new PdfPCell(new Paragraph("Created Date: "+ProgramServiceHelper.convertDateToString(dalpgm.getCreatedDate().getTime(), ProgramConstant.DATE_FORMAT)));
	         cell.setBorderColor(BaseColor.BLACK);
	         cell.setPaddingLeft(10);
	         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         custTable.addCell(cell);
	         
	         cell = new PdfPCell(new Paragraph(" "));
	         
	         cell.setBorderColor(BaseColor.BLACK);
	         cell.setPaddingLeft(10);
	         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         custTable.addCell(cell);
	         
	         cell = new PdfPCell(new Paragraph(""));
	         cell.setBorderColor(BaseColor.BLACK);
	         cell.setPaddingLeft(10);
	         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         custTable.addCell(cell);
	         
	         document.add(custTable);
	        
	         
	         document.add(new Paragraph("PROGRAM DETAILS",f1));
	         PdfPTable table = new PdfPTable(3); // 3 columns.
	         table.setWidthPercentage(100); //Width 100%
	         table.setSpacingBefore(10f); //Space before table
	         table.setSpacingAfter(10f); //Space after table
	  
	         //Set Column widths
	         //float[] columnWidths = {1f, 1f, 1f};
	         table.setWidths(columnWidths);
	  
	         PdfPCell cell1 = new PdfPCell(new Paragraph("Program Name: "+dalpgm.getProgramMaster().getProgram()));
	         cell1.setBorderColor(BaseColor.BLACK);
	         cell1.setPaddingLeft(10);
	         //cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         table.addCell(cell1);

	         cell1 = new PdfPCell(new Paragraph("Program Id: "+dalpgm.getId()));
	         cell1.setBorderColor(BaseColor.BLACK);
	         cell1.setPaddingLeft(10);
	         //cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         table.addCell(cell1);
	         
	         cell1 = new PdfPCell(new Paragraph("Payout Frequency: "+dalpgm.getPaidFrequency().getFrequency()));
	         cell1.setBorderColor(BaseColor.BLACK);
	         cell1.setPaddingLeft(10);
	         //cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         table.addCell(cell1);
	  
	         cell1 = new PdfPCell(new Paragraph("Begin/End Date: "+ProgramServiceHelper.convertDateToString(dalpgm.getProgramStartDate().getTime(), ProgramConstant.DATE_FORMAT)+" - "+ProgramServiceHelper.convertDateToString(dalpgm.getProgramEndDate().getTime(), ProgramConstant.DATE_FORMAT)));
	         cell1.setBorderColor(BaseColor.BLACK);
	         cell1.setPaddingLeft(10);
	         //cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         table.addCell(cell1);
	         
	         if(ProgramConstant.CALCULATED_PROGRAM_TYPE.equalsIgnoreCase(dalpgm.getDalProgramType().getType())){
	        	 cell1 = new PdfPCell(new Paragraph("Behind The Line (BTL): "+("Y".equalsIgnoreCase(dalpgm.getBTL())?ProgramConstant.YES:ProgramConstant.NO)));
		         cell.setBorderColor(BaseColor.BLACK);
		         cell1.setPaddingLeft(10);
		         //cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		         cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		         table.addCell(cell1);
		  
		         if(dalpgm.getPricingType()!=null){
		         cell1 = new PdfPCell(new Paragraph("Pricing Type: "+baseDao.getById(DalPricingType.class, dalpgm.getPricingType()).getType()));
		         }else{
		        	 cell1 = new PdfPCell(new Paragraph("Pricing Type: "));
		         }
		         cell1.setBorderColor(BaseColor.BLACK);
		         cell1.setPaddingLeft(10);
		         //cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		         cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		         table.addCell(cell1);	 
	         }

	         DecimalFormat format1 = new DecimalFormat("#,###.00");
	         if(ProgramConstant.ZERO.equalsIgnoreCase(dalpgm.getIsTiered())){
	        	 if(dalpgm.getAccrualAmount()==0)	{
	        		 cell1 = new PdfPCell(new Paragraph("Amount: 0"+dalpgm.getAccrualType()));
	        	 }else{
	    	         cell1 = new PdfPCell(new Paragraph("Amount: "+ format1.format(dalpgm.getAccrualAmount())+dalpgm.getAccrualType()));
	        	 }

	         }else{
	        	 if(!ProgramConstant.CALCULATED_PROGRAM_TYPE.equalsIgnoreCase(dalpgm.getDalProgramType().getType())){
		        	 if(dalpgm.getAccrualAmount()==0)	{
		        		 cell1 = new PdfPCell(new Paragraph("Amount: 0"+dalpgm.getAccrualType()));
		        	 }else{
		    	         cell1 = new PdfPCell(new Paragraph("Amount: "+ format1.format(dalpgm.getAccrualAmount())+dalpgm.getAccrualType()));
		        	 }
	        	 }
	        	 else{
	        		 cell1 = new PdfPCell(new Paragraph("Amount: "));	 
	        	 }
	         }

	         cell1.setBorderColor(BaseColor.BLACK);
	         cell1.setPaddingLeft(10);
	         //cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         table.addCell(cell1);
	         
	         cell1 = new PdfPCell(new Paragraph("Pay To: "+ (dalpgm.getPayTo()==null?"":dalpgm.getPayTo())));
	         cell1.setBorderColor(BaseColor.BLACK);
	         cell1.setPaddingLeft(10);
	         //cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         table.addCell(cell1);
	  
	         //cell1 = new PdfPCell(new Paragraph("Paid Type: "+(dalpgm.getPaidType())!=null?baseDao.getById(DalPaidType.class, dalpgm.getPaidType()).getType():""));
	         if(null!=dalpgm.getPaidType()){
	        	 cell1 = new PdfPCell(new Paragraph("Paid Type: "+baseDao.getById(DalPaidType.class, dalpgm.getPaidType()).getType()));
	         }else{
	        	 cell1 = new PdfPCell(new Paragraph("Paid Type: "));
	         }
	        
	         cell1.setBorderColor(BaseColor.BLACK);
	         cell1.setPaddingLeft(10);
	         //cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         table.addCell(cell1);
	  
	         if(null!=dalpgm.getPaidBasedOn()){
	        	 cell1 = new PdfPCell(new Paragraph("Paid Based On: "+dalpgm.getPaidBasedOn().getBaseItem())); 
	         }else{
	        	 cell1 = new PdfPCell(new Paragraph("Paid Based On: "));
	         }
	         
	         cell1.setBorderColor(BaseColor.BLACK);
	         cell1.setPaddingLeft(10);
	         //cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         table.addCell(cell1);
	         
	         if(ProgramConstant.CALCULATED_PROGRAM_TYPE.equalsIgnoreCase(dalpgm.getDalProgramType().getType())){
	        	 cell1 = new PdfPCell(new Paragraph("Achieve Based On: "+ ((dalpgm.getAchBasedMetric() != null) ? dalpgm.getAchBasedMetric().getBaseItem() : ProgramConstant.BLANK)));
		         cell1.setBorderColor(BaseColor.BLACK);
		         cell1.setPaddingLeft(10);
		         //cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
		         cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		         table.addCell(cell1);
		         
		         /*cell1 = new PdfPCell(new Paragraph("Is True Up: "+("Y".equalsIgnoreCase(dalpgm.getTrueUp())?ProgramConstant.YES:ProgramConstant.NO)));*/
		         cell1 = new PdfPCell(new Paragraph(""));
		         cell1.setBorderColor(BaseColor.BLACK);
		         cell1.setPaddingLeft(10);
		         //cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
		         cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		         table.addCell(cell1);
		         
		         cell1 = new PdfPCell(new Paragraph("Tiered Program: "+(ProgramConstant.ZERO.equalsIgnoreCase(dalpgm.getIsTiered())?ProgramConstant.NO:ProgramConstant.YES)));
		         cell1.setBorderColor(BaseColor.BLACK);
		         cell1.setPaddingLeft(10);
		         //cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
		         cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		         table.addCell(cell1);
		         if(ProgramConstant.ZERO.equalsIgnoreCase(dalpgm.getIsTiered()) && (dalpgm.getSchdTierMarker() != null && dalpgm.getSchdTierMarker() == 0) ){
		        	 cell1 = new PdfPCell(new Paragraph(""));
		         }else{
		        	 
		        	 if((null!=dalpgm.getPgmDetailTier())&&(null!=dalpgm.getPgmDetailTier().getLevel())&&(null!=dalpgm.getPgmDetailTier().getTierType())){
		        		 cell1 = new PdfPCell(new Paragraph("Marker: "+dalpgm.getPgmDetailTier().getLevel()+", Tier Type: "+dalpgm.getPgmDetailTier().getTierType())); 
		        	 }else{
		        		 cell1 = new PdfPCell(new Paragraph(""));
		        	 }
		         }
		         
		         cell1.setBorderColor(BaseColor.BLACK);
		         cell1.setPaddingLeft(10);
		         //cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
		         cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		         table.addCell(cell1);
	         }
	         int glcode=0;
	         if((null!=dalpgm.getGlCode())&&(!"".equalsIgnoreCase(dalpgm.getGlCode()))&&(!"0".equalsIgnoreCase(dalpgm.getGlCode()))){
	        	 glcode=Integer.parseInt(dalpgm.getGlCode());
	        	 cell1 = new PdfPCell(new Paragraph("Legacy GL #: "+ baseDao.getById(DalGLCode.class,glcode).getGlNo()));
	         }else{
	        	 cell1 = new PdfPCell(new Paragraph("Legacy GL #: "));
	         }
	         cell1.setBorderColor(BaseColor.BLACK);
	         cell1.setPaddingLeft(10);
	         //cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         table.addCell(cell1);
	         

	         //To avoid having the cell border and the content overlap, if you are having thick cell borders
	         //cell1.setUserBorderPadding(true);
	         //cell2.setUserBorderPadding(true);
	         //cell3.setUserBorderPadding(true);
	        
	         cell1 = new PdfPCell(new Paragraph("Achieved Based Frequency: "+(null!=dalpgm.getAchBasedFreq()?dalpgm.getAchBasedFreq().getFrequency():"")));
	         cell1.setBorderColor(BaseColor.BLACK);
	         cell1.setPaddingLeft(10);
	         //cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         table.addCell(cell1);
	  
	         cell1 = new PdfPCell(new Paragraph("Program Description: "+dalpgm.getLongDesc()));
	         cell1.setBorderColor(BaseColor.BLACK);
	         cell1.setPaddingLeft(10);
	         //cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         table.addCell(cell1);
	         
	         if((null!=dalpgm.getTbpCheck())){
	        	 cell1 = new PdfPCell(new Paragraph("TBP Special Handling: "+(ProgramConstant.ZERO.equalsIgnoreCase(dalpgm.getTbpCheck().toString())?ProgramConstant.NO:ProgramConstant.YES)));
	         }else{
	        	 cell1 = new PdfPCell(new Paragraph("TBP Special Handling: ")); 
	         }
	         
	         cell1.setBorderColor(BaseColor.BLACK);
	         cell1.setPaddingLeft(10);
	         //cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         table.addCell(cell1);
	         //To avoid having the cell border and the content overlap, if you are having thick cell borders
	         //cell1.setUserBorderPadding(true);
	         //cell2.setUserBorderPadding(true);
	         //cell3.setUserBorderPadding(true);
	        
	  
	         document.add(table);
	         
	         if(!dalpgm.getDalProgramDetPaidList().isEmpty()){
	         
	         document.add(new Paragraph("PAID BASED ON",f1));
	         PdfPTable paidBaseTable = new PdfPTable(3); // 3 columns.
	         paidBaseTable.setWidthPercentage(100); //Width 100%
	         paidBaseTable.setSpacingBefore(10f); //Space before table
	         paidBaseTable.setSpacingAfter(10f); //Space after table
	  
	         //Set Column widths
	         paidBaseTable.setWidths(columnWidths);
	  
	         cell = new PdfPCell(new Paragraph("TAG"));
	         cell.setBorderColor(BaseColor.BLACK);
	         cell.setPaddingLeft(10);
	         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         paidBaseTable.addCell(cell);

	         
	         cell = new PdfPCell(new Paragraph("INCLUDE"));
	         cell.setBorderColor(BaseColor.BLACK);
	         cell.setPaddingLeft(10);
	         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         paidBaseTable.addCell(cell);
	         
	         cell = new PdfPCell(new Paragraph("EXCLUDE"));
	         cell.setBorderColor(BaseColor.BLACK);
	         cell.setPaddingLeft(10);
	         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         paidBaseTable.addCell(cell);
	         
	         List<DalProgramDetPaid> dalProgramDetPaidList = dalpgm.getDalProgramDetPaidList().stream().sorted((e1, e2) -> e1.getTagId().compareTo(e2.getTagId())).collect(Collectors.toList());
	         for (Iterator<DalProgramDetPaid> iterator = dalProgramDetPaidList.iterator(); iterator.hasNext();) {
	        	 DalProgramDetPaid type = (DalProgramDetPaid) iterator.next();
	        	 cell = new PdfPCell(new Paragraph(baseDao.getEntityById(DalTagItems.class, type.getTagId()).getItem()));
		         cell.setBorderColor(BaseColor.BLACK);
		         cell.setPaddingLeft(10);
		         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		         paidBaseTable.addCell(cell);
		         if("1".equalsIgnoreCase(type.getMethod())){
		        	 cell = new PdfPCell(new Paragraph(type.getDisplayValue())); 
		         }else{
		        	 cell = new PdfPCell(new Paragraph("")); 
		         }
	        	 
		         cell.setBorderColor(BaseColor.BLACK);
		         cell.setPaddingLeft(10);
		         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		         paidBaseTable.addCell(cell);
		         
		         if("2".equalsIgnoreCase(type.getMethod())){
		        	 cell = new PdfPCell(new Paragraph(type.getDisplayValue())); 
		         }else{
		        	 cell = new PdfPCell(new Paragraph("")); 
		         }
		         cell.setBorderColor(BaseColor.BLACK);
		         cell.setPaddingLeft(10);
		         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		         paidBaseTable.addCell(cell);
				
			}
	       
	         
	         document.add(paidBaseTable);
	         }
	         
	         if((ProgramConstant.CALCULATED_PROGRAM_TYPE.equalsIgnoreCase(dalpgm.getDalProgramType().getType()))&&(!dalpgm.getDalProgramDetAchievedList().isEmpty())&&(!ProgramConstant.ZERO.equalsIgnoreCase(dalpgm.getIsTiered()))){        	 
	         
	                  
		         document.add(new Paragraph("ACHIEVED BASED ON",f1));
		         PdfPTable achievedBaseTable = new PdfPTable(3); // 3 columns.
		         achievedBaseTable.setWidthPercentage(100); //Width 100%
		         achievedBaseTable.setSpacingBefore(10f); //Space before table
		         achievedBaseTable.setSpacingAfter(10f); //Space after table
		  
		         //Set Column widths
		         achievedBaseTable.setWidths(columnWidths);
		  
		         cell = new PdfPCell(new Paragraph("TAG"));
		         cell.setBorderColor(BaseColor.BLACK);
		         cell.setPaddingLeft(10);
		         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		         achievedBaseTable.addCell(cell);

		         
		         cell = new PdfPCell(new Paragraph("INCLUDE"));
		         cell.setBorderColor(BaseColor.BLACK);
		         cell.setPaddingLeft(10);
		         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		         achievedBaseTable.addCell(cell);
		         
		         cell = new PdfPCell(new Paragraph("EXCLUDE"));
		         cell.setBorderColor(BaseColor.BLACK);
		         cell.setPaddingLeft(10);
		         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		         achievedBaseTable.addCell(cell);
		         List<DalProgramDetAchieved> dalProgramDetAchieved = dalpgm.getDalProgramDetAchievedList().stream().sorted((e1, e2) -> e1.getAchTagId().compareTo(e2.getAchTagId())).collect(Collectors.toList());
		         for (Iterator<DalProgramDetAchieved> iterator = dalProgramDetAchieved.iterator(); iterator.hasNext();) {
		        	 DalProgramDetAchieved type = (DalProgramDetAchieved) iterator.next();
		        	 cell = new PdfPCell(new Paragraph(baseDao.getEntityById(DalTagItems.class, type.getAchTagId()).getItem()));
			         cell.setBorderColor(BaseColor.BLACK);
			         cell.setPaddingLeft(10);
			         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			         achievedBaseTable.addCell(cell);
			         if("1".equalsIgnoreCase(type.getAchMethod())){
			        	 cell = new PdfPCell(new Paragraph(type.getDisplayValue())); 
			         }else{
			        	 cell = new PdfPCell(new Paragraph("")); 
			         }
		        	 
			         cell.setBorderColor(BaseColor.BLACK);
			         cell.setPaddingLeft(10);
			         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			         achievedBaseTable.addCell(cell);
			         
			         if("2".equalsIgnoreCase(type.getAchMethod())){
			        	 cell = new PdfPCell(new Paragraph(type.getDisplayValue())); 
			         }else{
			        	 cell = new PdfPCell(new Paragraph("")); 
			         }
			         cell.setBorderColor(BaseColor.BLACK);
			         cell.setPaddingLeft(10);
			         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			         achievedBaseTable.addCell(cell);
					
				}
		         
		         document.add(achievedBaseTable);
	         }
		         if(ProgramConstant.ONE.equalsIgnoreCase(dalpgm.getIsTiered())){
		        	 
		        
		         document.add(new Paragraph("PROGRAM SCHEDULE",f1));
		         PdfPTable programScheduleTable = new PdfPTable(4); // 4 columns.
		         programScheduleTable.setWidthPercentage(100); //Width 100%
		         programScheduleTable.setSpacingBefore(10f); //Space before table
		         programScheduleTable.setSpacingAfter(10f); //Space after table
		  
		         //Set Column widths
		         float[] columnWidthsTable = {1f, 1f, 1f, 1f};
		         programScheduleTable.setWidths(columnWidthsTable);
		  
		         cell = new PdfPCell(new Paragraph("MARKER"));
		         cell.setBorderColor(BaseColor.BLACK);
		         cell.setPaddingLeft(10);
		         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		         programScheduleTable.addCell(cell);

		         
		         cell = new PdfPCell(new Paragraph("ACCRUAL AMOUNT"));
		         cell.setBorderColor(BaseColor.BLACK);
		         cell.setPaddingLeft(10);
		         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		         programScheduleTable.addCell(cell);
		         
		         cell = new PdfPCell(new Paragraph("BEGIN RANGE"));
		         cell.setBorderColor(BaseColor.BLACK);
		         cell.setPaddingLeft(10);
		         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		         programScheduleTable.addCell(cell);     
		         
		         cell = new PdfPCell(new Paragraph("END RANGE"));
		         cell.setBorderColor(BaseColor.BLACK);
		         cell.setPaddingLeft(10);
		         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		         programScheduleTable.addCell(cell);    
		         
		         
		         Map<String, Object> parameters = new HashMap<String, Object>();
		         parameters.put("programDetailId", dalpgm.getId());
					List<DalProgramDetailTier> dalProgramTierList = baseDao.getListFromNamedQueryWithParameter("DalProgramDetailTier.getAllTierForProgramId", 
																	parameters);
					DecimalFormat format = new DecimalFormat("#,###.00");
					Map<Integer, String> endRangeDetails = getEndRangeDetails(dalProgramTierList);
					for (Iterator<DalProgramDetailTier> iterator = dalProgramTierList.iterator(); iterator.hasNext();) {
						DalProgramDetailTier dalProgramDetailTier = (DalProgramDetailTier) iterator.next();
						cell = new PdfPCell(new Paragraph(dalProgramDetailTier.getLevel().toString()));
				         cell.setBorderColor(BaseColor.BLACK);
				         cell.setPaddingLeft(10);
				         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				         programScheduleTable.addCell(cell);
				         
				         cell = new PdfPCell(new Paragraph(dalProgramDetailTier.getAmount()==0?"0":format.format(dalProgramDetailTier.getAmount())));
				         cell.setBorderColor(BaseColor.BLACK);
				         cell.setPaddingLeft(10);
				         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				         programScheduleTable.addCell(cell);
				         
				         DecimalFormat formatRange = new DecimalFormat("#,###");
				         cell = new PdfPCell(new Paragraph(formatRange.format(dalProgramDetailTier.getBeginRange())));
				         cell.setBorderColor(BaseColor.BLACK);
				         cell.setPaddingLeft(10);
				         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				         programScheduleTable.addCell(cell);
					
				         
				         String endRange = null;
				         if(endRangeDetails != null && endRangeDetails.get(dalProgramDetailTier.getLevel()) != null){
				        	 endRange = endRangeDetails.get(dalProgramDetailTier.getLevel());
				         }
				         
				         cell = new PdfPCell(new Paragraph(endRange));
				         cell.setBorderColor(BaseColor.BLACK);
				         cell.setPaddingLeft(10);
				         //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				         programScheduleTable.addCell(cell);
						
					}
					
		         
		         document.add(programScheduleTable);
		         
	         }

	         document.close();
	         writer.close();	        
	      } catch (DocumentException e)
	      {
	         e.printStackTrace();
	      } 
	   
	         return stream.toByteArray();

	}

	private Map<Integer, String> getEndRangeDetails(List<DalProgramDetailTier> dalProgramTierList) {
		Map<Integer, String> endRangeDetailsMap = null;
		DecimalFormat format = new DecimalFormat("#,###");
		if(dalProgramTierList != null){
			Integer previousBeginRange = null;
			endRangeDetailsMap = new HashMap<Integer, String>();
			for (ListIterator<DalProgramDetailTier> iterator = dalProgramTierList.listIterator(dalProgramTierList.size()); iterator.hasPrevious();) {
				DalProgramDetailTier dalProgramDetailTier = (DalProgramDetailTier) iterator.previous();
				if(previousBeginRange == null){
					/*endRangeDetailsMap.put(dalProgramDetailTier.getLevel(),  format.format(ProgramConstant.MAX_END_RANGE));*/
					endRangeDetailsMap.put(dalProgramDetailTier.getLevel(),  ProgramConstant.BLANK);
				}
				else{
					endRangeDetailsMap.put(dalProgramDetailTier.getLevel(), format.format(previousBeginRange - 1));
				}
				previousBeginRange = dalProgramDetailTier.getBeginRange();
			}
		}
		return endRangeDetailsMap;
	}
		
}
