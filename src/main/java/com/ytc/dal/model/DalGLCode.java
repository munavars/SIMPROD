package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "GL_CODE")
@NamedQueries({
	@NamedQuery(name="DalGLCode.getAllDetails", query = "select o from DalGLCode o order by o.glNo")
})
public class DalGLCode extends DalModel {
private static final long serialVersionUID = 1L;
	
	
	private String glNo;
	
	private String glBucket;
	
	private String pcs;
	
	private String bu;
	
	private String sapGlCode;

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

	/**
	 * @return the bu
	 */
	@Column(name="BU")
	public String getBu() {
		return bu;
	}

	/**
	 * @param bu the bu to set
	 */
	public void setBu(String bu) {
		this.bu = bu;
	}

	/**
	 * @return the sapGlCode
	 */
	@Column(name="SAP_GL")
	public String getSapGlCode() {
		return sapGlCode;
	}

	/**
	 * @param sapGlCode the sapGlCode to set
	 */
	public void setSapGlCode(String sapGlCode) {
		this.sapGlCode = sapGlCode;
	}

	
	
	
}
