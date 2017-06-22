package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "GL_CODE")
@NamedQueries({
	@NamedQuery(name="DalGLCode.getAllDetails", query = "select o from DalGLCode o")
})
public class DalGLCode extends DalModel {
private static final long serialVersionUID = 1L;
	
	
	private String glNo;
	
	private String glBucket;
	
	private String pcs;

	/**
	 * @return the glNo
	 */
	@Column(name="NEW_GLNo")
	public String getGlNo() {
		return glNo;
	}

	/**
	 * @param glNo the glNo to set
	 */
	public void setGlNo(String glNo) {
		this.glNo = glNo;
	}

	/**
	 * @return the glBucket
	 */

	@Column(name="GLBucket")
	public String getGlBucket() {
		return glBucket;
	}

	/**
	 * @param glBucket the glBucket to set
	 */

	public void setGlBucket(String glBucket) {
		this.glBucket = glBucket;
	}

	/**
	 * @return the pcs
	 */
	@Column(name="PCS")
	public String getPcs() {
		return pcs;
	}

	/**
	 * @param pcs the pcs to set
	 */
	public void setPcs(String pcs) {
		this.pcs = pcs;
	}

	
	
	
}
