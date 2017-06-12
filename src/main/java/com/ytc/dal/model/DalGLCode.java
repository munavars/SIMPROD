package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "GL_BUCKET")
@NamedQueries({
	@NamedQuery(name="DalGLCode.getAllDetails", query = "select o from DalGLCode o")
})
public class DalGLCode extends DalModel {
private static final long serialVersionUID = 1L;
	
	@Column(name="GLNo")
	private String glNo;


	public String getGlNo() {
		return glNo;
	}

	public void setglNo(String glNo) {
		this.glNo = glNo;
	}
}
