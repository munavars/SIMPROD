/**
 * 
 */
package com.ytc.service.util;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalProgramDetAchieved;
import com.ytc.dal.model.DalProgramDetPaid;
import com.ytc.dal.model.DalProgramDetail;
import com.ytc.dal.model.DalTagItems;

/**
 * @author Cognizant
 *
 */
public class ExcelGenerator {

/*	
	public static void main(String args[]){
		System.out.println("test");
		new ExcelGenerator().generateExcel(new BaseDao(), "");
	}*/
	/**
	 * @param args
	 * @return 
	 */
	
	@SuppressWarnings("resource")
	public byte[] generateExcel(IDataAccessLayer baseDao, Integer id) {

		int columnSize=3;
		int rownum = 0; 
		byte[] excel=null;
		ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
		String headerColumns[]={"TAG","INCLUDE","EXCLUDE"};
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Credit Memo");
		XSSFCellStyle headerStyle=workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
			
		
		
		DalProgramDetail dalpgm=baseDao.getById(DalProgramDetail.class, id);
		/*if(dalpgm.getDalProgramDetPaidList().isEmpty()&&dalpgm.getDalProgramDetAchievedList().isEmpty()){
			return null;
		}*/
        if(!dalpgm.getDalProgramDetPaidList().isEmpty()){     
        	Row headRow=sheet.createRow(rownum++);
    		Cell headerCell=headRow.createCell(1);
    		headerCell.setCellValue("Paid Based On");
    		headerCell.setCellStyle(headerStyle);
    		sheet.addMergedRegion(new CellRangeAddress(0,0,1,3));
    		Row titleRow=sheet.createRow(rownum++);
    		for (int i = 0; i <columnSize; i++) {
    			Cell cell=titleRow.createCell(i+1);	
    			cell.setCellValue(headerColumns[i]);
    		}
	        List<DalProgramDetPaid> dalProgramDetPaidList = dalpgm.getDalProgramDetPaidList().stream().sorted((e1, e2) -> e1.getTagId().compareTo(e2.getTagId())).collect(Collectors.toList());
	        for (Iterator<DalProgramDetPaid> iterator = dalProgramDetPaidList.iterator(); iterator.hasNext();) {
	        	DalProgramDetPaid type = (DalProgramDetPaid) iterator.next();
	        	Row row = sheet.createRow(rownum++);
	        	Cell cell1=row.createCell(1);
	        	cell1.setCellValue(baseDao.getEntityById(DalTagItems.class, type.getTagId()).getItem());
	        	Cell cell2=row.createCell(2);
	        	Cell cell3=row.createCell(3);
	        	
	        	 if("1".equalsIgnoreCase(type.getMethod())){
		        	 cell2.setCellValue(type.getDisplayValue());  
		         }else{
		        	 cell2.setCellValue(""); 
		         }
	        	 
	        	 if("2".equalsIgnoreCase(type.getMethod())){
	        		 cell3.setCellValue(type.getDisplayValue());
		         }else{
		        	 cell3.setCellValue("");
		         }
	        }
      
        
        }
        
        if(!dalpgm.getDalProgramDetAchievedList().isEmpty()){   
        rownum++;
        Row headRow=sheet.createRow(rownum++);
		Cell headerCell=headRow.createCell(1);
		headerCell.setCellValue("Achieve Based On");
		headerCell.setCellStyle(headerStyle);
		sheet.addMergedRegion(new CellRangeAddress(headRow.getRowNum(),headRow.getRowNum(),1,3));
		Row titleRow=sheet.createRow(rownum++);
		for (int i = 0; i <columnSize; i++) {
			Cell cell=titleRow.createCell(i+1);	
			cell.setCellValue(headerColumns[i]);
		}
		
		     	 
		 List<DalProgramDetAchieved> dalProgramDetAchieved = dalpgm.getDalProgramDetAchievedList().stream().sorted((e1, e2) -> e1.getAchTagId().compareTo(e2.getAchTagId())).collect(Collectors.toList());
	        for (Iterator<DalProgramDetAchieved> iterator = dalProgramDetAchieved.iterator(); iterator.hasNext();) {
	        	DalProgramDetAchieved type = (DalProgramDetAchieved) iterator.next();
	        	Row row = sheet.createRow(rownum++);
	        	Cell cell1=row.createCell(1);
	        	cell1.setCellValue(baseDao.getEntityById(DalTagItems.class, type.getAchTagId()).getItem());
	        	Cell cell2=row.createCell(2);
	        	Cell cell3=row.createCell(3);
	        	
	        	 if("1".equalsIgnoreCase(type.getAchMethod())){
		        	 cell2.setCellValue(type.getDisplayValue());  
		         }else{
		        	 cell2.setCellValue(""); 
		         }
	        	 
	        	 if("2".equalsIgnoreCase(type.getAchMethod())){
	        		 cell3.setCellValue(type.getDisplayValue());
		         }else{
		        	 cell3.setCellValue("");
		         }
	        }
      
        
        }
        
		
		try {
			/*FileOutputStream out = 
					new FileOutputStream(new File("C:/Excel/test.xlsx"));
			workbook.write(out);*/
			
			
			
			workbook.write(byteArrayOut);
			excel=byteArrayOut.toByteArray();
			 byteArrayOut.close();
			System.out.println("Excel written successfully..");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return excel;

	}
		

}
