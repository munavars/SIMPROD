package com.ytc.dal.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "WORKFLOW_STATUS")
@NamedQueries({
	@NamedQuery(name="DalWorkflowStatus.getProgramWorkflowDetails", query = "select o from DalWorkflowStatus o "
																+ "where o.dalProgramDetailWf.id = :programDetailId order by id")
})
public class DalWorkflowStatus extends DalAuditableModel{

	/**
	 * Default serial version id.
	 */
	private static final long serialVersionUID = 1L;
	
	private DalProgramDetail dalProgramDetailWf;
	
	private DalPricingHeader dalPricingHeaderWf;
	
	private DalProgramType dalProgramType;
	
	private Calendar wfStatusDate;
	
	private DalStatus approvalStatus;
	
	private String approvalComment;
	
	private String decisionMade;

	private DalEmployee approver;
	
	private Integer wfMatrixId;
	
	@ManyToOne
	@JoinColumn(name = "PGM_DETAIL_ID", referencedColumnName = "ID")
	public DalProgramDetail getDalProgramDetailWf() {
		return dalProgramDetailWf;
	}

	public void setDalProgramDetailWf(DalProgramDetail dalProgramDetailWf) {
		this.dalProgramDetailWf = dalProgramDetailWf;
	}
	
	@ManyToOne
	@JoinColumn(name = "PRICING_HDR_ID", referencedColumnName = "ID")
	public DalPricingHeader getDalPricingHeaderWf() {
		return dalPricingHeaderWf;
	}

	public void setDalPricingHeaderWf(DalPricingHeader dalPricingHeaderWf) {
		this.dalPricingHeaderWf = dalPricingHeaderWf;
	}

	@Column(name = "WF_STATUS_DATE")
	public Calendar getWfStatusDate() {
		return wfStatusDate;
	}

	public void setWfStatusDate(Calendar wfStatusDate) {
		this.wfStatusDate = wfStatusDate;
	}

	@OneToOne
	@JoinColumn(name="APPROVAL_STATUS", referencedColumnName="ID")
	public DalStatus getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(DalStatus approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	@Column(name = "APPROVAL_COMMENT")
	public String getApprovalComment() {
		return approvalComment;
	}

	public void setApprovalComment(String approvalComment) {
		this.approvalComment = approvalComment;
	}

	@Column(name = "DECISION_MADE")
	public String getDecisionMade() {
		return decisionMade;
	}

	public void setDecisionMade(String decisionMade) {
		this.decisionMade = decisionMade;
	}
	
    @OneToOne(targetEntity = com.ytc.dal.model.DalEmployee.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "APPROVER_ID")
	public DalEmployee getApprover() {
		return approver;
	}

	public void setApprover(DalEmployee approver) {
		this.approver = approver;
	}

	@Column(name = "WF_MATRIX_ID")
	public Integer getWfMatrixId() {
		return wfMatrixId;
	}

	public void setWfMatrixId(Integer wfMatrixId) {
		this.wfMatrixId = wfMatrixId;
	}

	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PGM_TYPE_ID", referencedColumnName = "ID")
	public DalProgramType getDalProgramType() {
		return dalProgramType;
	}

	public void setDalProgramType(DalProgramType dalProgramType) {
		this.dalProgramType = dalProgramType;
	}	
}
