package com.ytc.common.model;

public class ProgramMaster extends Model{
	
	/**
	 * 
	 */
	/*private static final long serialVersionUID = 1L;*/
	
	private ProgramHeader programHeader;
	
	private ProgramDetail programDetail;
	
	private ProgramPaidOn programPaidOn;
	
	private ProgramAchieveOn programAchieveOn;
	
	public ProgramHeader getProgramHeader() {
		return programHeader;
	}
	public void setProgramHeader(ProgramHeader programHeader) {
		this.programHeader = programHeader;
	}
	public ProgramDetail getProgramDetail() {
		return programDetail;
	}
	public void setProgramDetail(ProgramDetail programDetail) {
		this.programDetail = programDetail;
	}
	public ProgramPaidOn getProgramPaidOn() {
		return programPaidOn;
	}
	public void setProgramPaidOn(ProgramPaidOn programPaidOn) {
		this.programPaidOn = programPaidOn;
	}
	public ProgramAchieveOn getProgramAchieveOn() {
		return programAchieveOn;
	}
	public void setProgramAchieveOn(ProgramAchieveOn programAchieveOn) {
		this.programAchieveOn = programAchieveOn;
	}	
}
