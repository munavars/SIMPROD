package com.ytc.dal.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER_COMMENTS")
public class DalUserComments extends DalAuditableModel {

	/**
	 * Default Serial version number.
	 */
	private static final long serialVersionUID = 1L;
	
    private DalProgramDetail dalProgramDetailForComment;
    
    private DalEmployee user;
    
    private String userComments;
    
    private Calendar commentedDate;

    @OneToOne
	@JoinColumn(name= "PGM_DETAIL_ID", referencedColumnName = "ID")
	public DalProgramDetail getDalProgramDetailForComment() {
		return dalProgramDetailForComment;
	}

	public void setDalProgramDetailForComment(DalProgramDetail dalProgramDetailForComment) {
		this.dalProgramDetailForComment = dalProgramDetailForComment;
	}

    @OneToOne(targetEntity = com.ytc.dal.model.DalEmployee.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
	public DalEmployee getUser() {
		return user;
	}

	public void setUser(DalEmployee user) {
		this.user = user;
	}

	@Column(name = "USER_COMMENTS")
	public String getUserComments() {
		return userComments;
	}

	public void setUserComments(String userComments) {
		this.userComments = userComments;
	}

	@Column(name = "COMMENTED_DATE")
	public Calendar getCommentedDate() {
		return commentedDate;
	}

	public void setCommentedDate(Calendar commentedDate) {
		this.commentedDate = commentedDate;
	}
}
