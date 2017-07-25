package com.ytc.common.params;

import java.util.Calendar;

public class BookParams {

	private String bookLabel;
	private Calendar bookDate;
	private String bookOfRecord;
	private String createdBy;
	public String getBookLabel() {
		return bookLabel;
	}
	public void setBookLabel(String bookLabel) {
		this.bookLabel = bookLabel;
	}
	public Calendar getBookDate() {
		return bookDate;
	}
	public void setBookDate(Calendar bookDate) {
		this.bookDate = bookDate;
	}
	public String getBookOfRecord() {
		return bookOfRecord;
	}
	public void setBookOfRecord(String bookOfRecord) {
		this.bookOfRecord = bookOfRecord;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
}
