package com.ytc.dal.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public abstract class DalAuditableModel extends DalModel {


    protected DalUser createdBy;
    protected Calendar createdDate;
    protected DalUser modifiedBy;
    protected Calendar modifiedDate;
    
    public DalAuditableModel() {
        super();
        long curTimeMillis = System.currentTimeMillis();
        createdDate = Calendar.getInstance();
        createdDate.clear();
        createdDate.setTimeInMillis(curTimeMillis);
        modifiedDate= Calendar.getInstance();
        modifiedDate.clear();
        modifiedDate.setTimeInMillis(curTimeMillis);
    }

    public DalAuditableModel(DalAuditableModel m) {
        super();
        this.modifiedBy = m.modifiedBy;
        this.createdBy = m.createdBy;
        this.modifiedDate = m.modifiedDate;
        this.createdDate = m.createdDate;
    }

    @ManyToOne(targetEntity = com.ytc.dal.model.DalUser.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", updatable = false, insertable = true)
    public DalUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(DalUser createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "CREATED_DATE", columnDefinition = "timestamp", updatable = false, insertable = true)
    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    @ManyToOne(targetEntity = com.ytc.dal.model.DalUser.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "MODIFIED_BY")
    public DalUser getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(DalUser modifiedBy) {
        this.modifiedBy = modifiedBy;
    }


    //@Version
    @Column(name = "MODIFIED_DATE", columnDefinition = "timestamp",updatable = true, insertable = true)
    public Calendar getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Calendar modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

   
   
}
