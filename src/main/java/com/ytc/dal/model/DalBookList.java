/**
 * 
 */
package com.ytc.dal.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Cognizant
 *
 */
@Entity
@Table(name = "BOOK_LIST")
public class DalBookList extends DalModel{
	
	private static final long serialVersionUID = 4482936117713996795L;	
    private String bookLabel;
    private String bookRecord;
    private Calendar bookDate;
    private String createdUser;
    private Calendar createdDate;
	/**
	 * @return the bookLabel
	 */
    @Column(name = "BOOK_LABEL")
	public String getBookLabel() {
		return bookLabel;
	}
	/**
	 * @param bookLabel the bookLabel to set
	 */
	public void setBookLabel(String bookLabel) {
		this.bookLabel = bookLabel;
	}
	/**
	 * @return the bookRecord
	 */
	@Column(name = "BOOK_OF_RECORD")
	public String getBookRecord() {
		return bookRecord;
	}
	/**
	 * @param bookRecord the bookRecord to set
	 */
	public void setBookRecord(String bookRecord) {
		this.bookRecord = bookRecord;
	}
	/**
	 * @return the bookDate
	 */
	@Column(name = "BOOK_DATE")
	public Calendar getBookDate() {
		return bookDate;
	}
	/**
	 * @param bookDate the bookDate to set
	 */
	public void setBookDate(Calendar bookDate) {
		this.bookDate = bookDate;
	}
	/**
	 * @return the createdUser
	 */
	@Column(name = "CREATE_USER")
	public String getCreatedUser() {
		return createdUser;
	}
	/**
	 * @param createdUser the createdUser to set
	 */
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	/**
	 * @return the createdDate
	 */
	@Column(name = "CREATE_SYSDATE")
	public Calendar getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}
    

	

}
