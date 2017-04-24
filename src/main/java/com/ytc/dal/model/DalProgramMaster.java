package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "PROGRAM_MASTER")
@NamedQueries({
	@NamedQuery(name="DalProgramMaster.getAllDetails", query = "select o from DalProgramMaster o")
})
public class DalProgramMaster extends DalAuditableModel {

	private String program;
	
	@Column(name = "PROGRAM")
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
}
