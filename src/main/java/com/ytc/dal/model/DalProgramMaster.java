package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "PROGRAM_MASTER")
@NamedQueries({
	@NamedQuery(name="DalProgramMaster.getAllDetails", query = "select o from DalProgramMaster o order by o.program ")
})
public class DalProgramMaster extends DalAuditableModel {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	
	private String program;
	
	@Column(name = "PROGRAM")
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
}
