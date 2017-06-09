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
	
	@Column(name="GLBucket")
	private String glBucket;


	public String getGlBucket() {
		return glBucket;
	}

	public void setGlBucket(String glBucket) {
		this.glBucket = glBucket;
	}
}
