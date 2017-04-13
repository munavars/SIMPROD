package com.tiredex.yoko.utils;

import java.io.Serializable;

/**
 * This class is used to save data retrieved from an Active Directory user.
 * 
 * @author AlainG
 * @since 5/14/2015
 *
 */
public class LdapRecord implements Serializable {
	
    private static final String CLASS_NAME = LdapRecord.class.getSimpleName();

    private static final String CONFIG_MGMT_VERSION = "$Revision: 2 $";
    // use SourceSafe revision number for the serialVersionUID
    private static final int firstSpace = CONFIG_MGMT_VERSION.indexOf(' ');
    private static final int lastSpace = CONFIG_MGMT_VERSION.lastIndexOf(' ');
    static final long serialVersionUID = Integer.parseInt(CONFIG_MGMT_VERSION.substring(firstSpace + 1, lastSpace));

    
    
    /**
     * For Mike Baggett, National Sales Manager for OTR, Active Directory example: cnValue = Baggett\. Mike
     */
    
    private String cnValue = null;
    
    /**
     * For Mike Baggett, National Sales Manager for OTR, Active Directory example: firstName = Mike
     */
    private String firstName = null;
    
    /**
     * For Mike Baggett, National Sales Manager for OTR, Active Directory example: lastName = Baggett
     */
    private String lastName = null;
    
    /**
     * For Mike Baggett, National Sales Manager for OTR, Active Directory example: acctName = Baggett, Mike
     */
    private String acctName = null;
    
    /**
     * For Mike Baggett, National Sales Manager for OTR, Active Directory example: mailNickname = MikeB
     */
    private String mailNickname = null;
    
    /**
     * For Mike Baggett, National Sales Manager for OTR, Active Directory example: email = mike.baggett@yokohamatire.com
     */
    private String email = null;
    
    /**
     * For Mike Baggett, National Sales Manager for OTR, Active Directory example: department = Sales - OTR]
     */
    private String department = null;
    
    /**
     * For Mike Baggett, National Sales Manager for OTR, Active Directory example: title = National Sales Manager]
     */
    private String title = null;

    /**
     * For Mike Baggett, National Sales Manager for OTR, Active Directory example: mobilePhone = 850.925.5589]
     */
    private String mobilePhone = null;

    /**
     * For Mike Baggett, National Sales Manager for OTR, Active Directory example: officePhone = +1 (800) 423-4544, 8711]
     */
    private String officePhone = null;

    /**
     * For Mike Baggett, National Sales Manager for OTR, Active Directory example: managerInAD = CN=Baggett \ , Mike,OU=OTR,OU=CA Users,DC=CA,DC=NA,DC=YTC]
     */
    private String managerInAD = null;

    
    
    public LdapRecord() {
        super();
    }
    /**
     * @param aCnValue
     * @param aFirstName
     * @param aLastName
     * @param anAcctName
     * @param aMailNickname
     * @param anEmail
     * @param aDepartment
     * @param aTitle
     * @param aMobilePhone
     * @param anOfficePhone
     * @param aManagerInAD
     */
    public LdapRecord(
    		String aCnValue, 
    		String aFirstName, 
    		String aLastName, 
    		String anAcctName, 
    		String aMailNickname,
    		String anEmail, 
    		String aDepartment, 
    		String aTitle,
    		String aMobilePhone,
    		String anOfficePhone,
    		String aManagerInAD) {
        super();
        this.cnValue = aCnValue;
        this.firstName = aFirstName;
        this.lastName = aLastName;
        this.acctName = anAcctName;
        this.mailNickname = aMailNickname;
        this.email = anEmail;
        this.department = aDepartment;
        this.title = aTitle;
        this.mobilePhone = aMobilePhone;
        this.officePhone = anOfficePhone;
        this.managerInAD = aManagerInAD;
    }



	public String getCnValue() {
    	return cnValue;
    }
	public void setCnValue(String aCnValue) {
    	this.cnValue = aCnValue;
    }



	public String getFirstName() {
    	return firstName;
    }
	public void setFirstName(String aFirstName) {
    	this.firstName = aFirstName;
    }



	public String getLastName() {
    	return lastName;
    }
	public void setLastName(String aLastName) {
    	this.lastName = aLastName;
    }



	public String getAcctName() {
    	return acctName;
    }
	public void setAcctName(String anAcctName) {
    	this.acctName = anAcctName;
    }



	public String getMailNickname() {
    	return mailNickname;
    }
	public void setMailNickname(String aMailNickname) {
    	this.mailNickname = aMailNickname;
    }



	public String getEmail() {
    	return email;
    }
	public void setEmail(String anEmail) {
    	this.email = anEmail;     
    }

	
	
	public String getDepartment() {
    	return department;
    }
	public void setDepartment(String aDepartment) {
    	this.department = aDepartment;
    }

	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String aTitle) {
		this.title = aTitle;
	}

	
	
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	
	public String getOfficePhone() {
		return officePhone;
	}
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}
	
	
	public String getManagerInAD() {
		return managerInAD;
	}
	public void setManagerInAD(String managerInAD) {
		this.managerInAD = managerInAD;
	}
    

	
	public String toString() {
        return 
			CLASS_NAME + ": " + 
			"cnValue = [" + cnValue + "], " +
			"firstName = [" + firstName + "], " +
			"lastName = [" + lastName + "], " +
			"acctName = [" + acctName + "], " +
        	"mailNickname = [" + mailNickname + "], " +
        	"email = [" + email + "], " +
        	"department = [" + department + "], " +
        	"title = [" + title + "], " +
        	"mobilePhone = [" + mobilePhone + "], " +
        	"officePhone = [" + officePhone + "], " +
        	"managerInAD = [" + managerInAD + "], " +
			"object hashCode = [" + this.hashCode() + ']';
    }
}
