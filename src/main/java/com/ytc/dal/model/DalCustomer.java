package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;



@Entity
@Table(name = "CUSTMSRTDX")
public class DalCustomer extends DalAuditableModel {


	public DalCustomer() {

	}	

	private String CMBLTOID;
	private String CMBLTONM;
	private String CMDBANM;
	private String CMCSTTYP;
	private String CMBUSNUT;
	private String CMBLAD1;
	private String CMBLAD2;
	private String CMCSTCTY;
	private String CMCSTSTE;
	private String CMCSTZIP;
	private String CMCSTPH;
	private String CMSLSCHL;
	private String CM_IS_ACTIVE;
	private String CM_CREDIT_STATUS;
	private String CM_IS_GOOD_CREDIT_STANDING;

	public DalCustomer(DalCustomer c) {
		this.CMBLTOID = c.CMBLTOID;
		this.CMBLTONM = c.CMBLTONM;
		this.CMDBANM = c.CMDBANM;
		this.CMCSTTYP = c.CMCSTTYP;
		this.CMBUSNUT = c.CMBUSNUT;
		this.CMBLAD1 = c.CMBLAD1;
		this.CMBLAD2 = c.CMBLAD2;
		this.CMCSTCTY = c.CMCSTCTY;
		this.CMCSTSTE = c.CMCSTSTE;
		this.CMCSTZIP = c.CMCSTZIP;
		this.CMCSTPH = c.CMCSTPH;
		this.CMSLSCHL = c.CMSLSCHL;
		this.CM_IS_ACTIVE = c.CM_IS_ACTIVE;
		this.CM_CREDIT_STATUS = c.CM_CREDIT_STATUS;
		this.CM_IS_GOOD_CREDIT_STANDING = c.CM_IS_GOOD_CREDIT_STANDING;


	}

	//@Id
	@Column(name = "CMBLTOID")
	public String getCMBLTOID() {
		return CMBLTOID;
	}
	public void setCMBLTOID(String cMBLTOID) {
		CMBLTOID = cMBLTOID;
	}	
	
	@Column(name = "CMBLTONM")
	public String getCMBLTONM() {
		return CMBLTONM;
	}
	public void setCMBLTONM(String cMBLTONM) {
		CMBLTONM = cMBLTONM;
	}
	
	@Column(name = "CMDBANM")
	public String getCMDBANM() {
		return CMDBANM;
	}
	public void setCMDBANM(String cMDBANM) {
		CMDBANM = cMDBANM;
	}
	
	@Column(name = "CMCSTTYP")
	public String getCMCSTTYP() {
		return CMCSTTYP;
	}
	public void setCMCSTTYP(String cMCSTTYP) {
		CMCSTTYP = cMCSTTYP;
	}
	
	@Column(name = "CMBUSNUT")
	public String getCMBUSNUT() {
		return CMBUSNUT;
	}
	public void setCMBUSNUT(String cMBUSNUT) {
		CMBUSNUT = cMBUSNUT;
	}
	
	@Column(name = "CMBLAD1")
	public String getCMBLAD1() {
		return CMBLAD1;
	}
	public void setCMBLAD1(String cMBLAD1) {
		CMBLAD1 = cMBLAD1;
	}
	public String getCMBLAD2() {
		return CMBLAD2;
	}
	
	@Column(name = "CMBLAD2")
	public void setCMBLAD2(String cMBLAD2) {
		CMBLAD2 = cMBLAD2;
	}
	public String getCMCSTCTY() {
		return CMCSTCTY;
	}
	public void setCMCSTCTY(String cMCSTCTY) {
		CMCSTCTY = cMCSTCTY;
	}
	
	@Column(name = "CMCSTSTE")
	public String getCMCSTSTE() {
		return CMCSTSTE;
	}
	public void setCMCSTSTE(String cMCSTSTE) {
		CMCSTSTE = cMCSTSTE;
	}
	
	@Column(name = "CMCSTZIP")
	public String getCMCSTZIP() {
		return CMCSTZIP;
	}
	public void setCMCSTZIP(String cMCSTZIP) {
		CMCSTZIP = cMCSTZIP;
	}
	
	@Column(name = "CMCSTPH")
	public String getCMCSTPH() {
		return CMCSTPH;
	}
	public void setCMCSTPH(String cMCSTPH) {
		CMCSTPH = cMCSTPH;
	}
	
	@Column(name = "CMSLSCHL")
	public String getCMSLSCHL() {
		return CMSLSCHL;
	}
	public void setCMSLSCHL(String cMSLSCHL) {
		CMSLSCHL = cMSLSCHL;
	}
	
	@Column(name = "CM_IS_ACTIVE")
	public String getCM_IS_ACTIVE() {
		return CM_IS_ACTIVE;
	}
	public void setCM_IS_ACTIVE(String cM_IS_ACTIVE) {
		CM_IS_ACTIVE = cM_IS_ACTIVE;
	}
	
	@Column(name = "CM_CREDIT_STATUS")
	public String getCM_CREDIT_STATUS() {
		return CM_CREDIT_STATUS;
	}
	public void setCM_CREDIT_STATUS(String cM_CREDIT_STATUS) {
		CM_CREDIT_STATUS = cM_CREDIT_STATUS;
	}
	
	@Column(name = "CM_IS_GOOD_CREDIT_STANDING")
	public String getCM_IS_GOOD_CREDIT_STANDING() {
		return CM_IS_GOOD_CREDIT_STANDING;
	}
	public void setCM_IS_GOOD_CREDIT_STANDING(String cM_IS_GOOD_CREDIT_STANDING) {
		CM_IS_GOOD_CREDIT_STANDING = cM_IS_GOOD_CREDIT_STANDING;
	}



}
