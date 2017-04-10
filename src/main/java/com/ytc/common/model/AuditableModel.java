package com.ytc.common.model;

import java.util.Calendar;


public abstract class AuditableModel extends Model {
    
	private static final long serialVersionUID = 1L;


	private String createdBy;

    
    private Calendar createdDate;

    private String modifiedBy;

    private Calendar modifiedDate;

    public AuditableModel() {
    }

    public AuditableModel(String id) {
        super(id);
    }

    public AuditableModel(AuditableModel m) {
        super(m);
        this.modifiedBy = m.modifiedBy;
        this.createdBy = m.createdBy;
        this.modifiedDate = m.modifiedDate;
        this.createdDate = m.createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Calendar getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Calendar modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

//test
}
