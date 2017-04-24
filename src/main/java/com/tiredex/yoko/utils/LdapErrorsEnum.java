package com.tiredex.yoko.utils;


/**
 */
public enum LdapErrorsEnum {
	
	
	LDAP_SUCCESS                      	(0x00, null, "Sucessful request."),
	LDAP_OPERATIONS_ERROR             	(0x01, null, "Intialization of LDAP library failed."),
	LDAP_PROTOCOL_ERROR               	(0x02, null, "Protocol error occurred."),
	LDAP_TIMELIMIT_EXCEEDED           	(0x03, null, "Time limit has exceeded."),
	LDAP_SIZELIMIT_EXCEEDED           	(0x04, null, "Size limit has exceeded."),
	LDAP_COMPARE_FALSE                	(0x05, null, "Compare yielded FALSE."),
	LDAP_COMPARE_TRUE                 	(0x06, null, "Compare yielded TRUE."),
	LDAP_AUTH_METHOD_NOT_SUPPORTED    	(0x07, null, "The authentication method is not supported."),
	LDAP_STRONG_AUTH_REQUIRED         	(0x08, null, "Strong authentication is required."),
	LDAP_REFERRAL_V2                  	(0x09, null, "LDAP version 2 referral."),
	LDAP_PARTIAL_RESULTS              	(0x09, null, "Partial results and referrals received."),
	LDAP_REFERRAL                     	(0x0a, null, "Referral occurred."),
	LDAP_ADMIN_LIMIT_EXCEEDED         	(0x0b, null, "Administration limit on the server has exceeded."),
	LDAP_UNAVAILABLE_CRIT_EXTENSION   	(0x0c, null, "Critical extension is unavailable."),
	LDAP_CONFIDENTIALITY_REQUIRED     	(0x0d, null, "Confidentiality is required."),
	LDAP_NO_SUCH_ATTRIBUTE            	(0x10, null, "Requested attribute does not  exist."),
	LDAP_UNDEFINED_TYPE               	(0x11, null, "The type is not defined.  "),
	LDAP_INAPPROPRIATE_MATCHING       	(0x12, null, "An inappropriate matching  occurred. "),
	LDAP_CONSTRAINT_VIOLATION         	(0x13, null, "A constraint violation occurred."),
	LDAP_ATTRIBUTE_OR_VALUE_EXISTS    	(0x14, null, "The attribute exists or the value has been assigned."),
	LDAP_INVALID_SYNTAX               	(0x15, null, "The syntax is invalid."),
	LDAP_NO_SUCH_OBJECT               	(0x20, null, "Object does not exist."),
	LDAP_ALIAS_PROBLEM                	(0x21, null, "The alias is invalid."),
	LDAP_INVALID_DN_SYNTAX            	(0x22, null, "The distinguished name has an invalid syntax."),
	LDAP_IS_LEAF                      	(0x23, null, "The object is a leaf."),
	LDAP_ALIAS_DEREF_PROBLEM          	(0x24, null, "Cannot de-reference the alias."),
	LDAP_INAPPROPRIATE_AUTH           	(0x30, null, "Authentication is inappropriate."),
	LDAP_INVALID_CREDENTIALS          	(0x31, null, "The supplied credential is invalid."),
	ERR_49_525							(0x31, "525", "The specified account does not exist."),
	ERR_49_52e							(0x31, "52e", "Logon failure: unknown user name or bad password."),
	ERR_49_530							(0x31, "530", "Logon failure: account logon time restriction violation."),
	ERR_49_531							(0x31, "531", "Logon failure: user not allowed to log on to this computer."),
	ERR_49_532							(0x31, "532", "Logon failure: the specified account password has expired."),
	ERR_49_533							(0x31, "533", "Logon failure: account currently disabled."),
	ERR_49_701							(0x31, "701", "The user's account has expired."),
	ERR_49_773							(0x31, "773", "The user's password must be changed."),
	ERR_49_775							(0x31, "775", "The referenced account is currently locked out and may not be logged on to."),
	LDAP_INSUFFICIENT_RIGHTS          	(0x32, null, "The user has insufficient access rights."),
	LDAP_BUSY                         	(0x33, null, "The server is busy."),
	LDAP_UNAVAILABLE                  	(0x34, null, "The server is unavailable."),
	LDAP_UNWILLING_TO_PERFORM         	(0x35, null, "The server does not handle directory requests."),
	LDAP_LOOP_DETECT                  	(0x36, null, "The chain of referrals has looped back to a referring server."),
	LDAP_NAMING_VIOLATION             	(0x40, null, "There was a naming violation."),
	LDAP_OBJECT_CLASS_VIOLATION       	(0x41, null, "There was an object class violation."),
	LDAP_NOT_ALLOWED_ON_NONLEAF       	(0x42, null, "Operation is not allowed on a non-leaf object."),
	LDAP_NOT_ALLOWED_ON_RDN           	(0x43, null, "Operation is not allowed on RDN."),
	LDAP_ALREADY_EXISTS               	(0x44, null, "The object already exists."),
	LDAP_NO_OBJECT_CLASS_MODS         	(0x45, null, "Cannot modify object class."),
	LDAP_RESULTS_TOO_LARGE            	(0x46, null, "Results returned are too large."),
	LDAP_AFFECTS_MULTIPLE_DSAS        	(0x47, null, "Multiple directory service agents are affected."),
	LDAP_OTHER                        	(0x50, null, "Unknown error occurred."),
	LDAP_SERVER_DOWN                  	(0x51, null, "Cannot contact the LDAP server."),
	LDAP_LOCAL_ERROR                  	(0x52, null, "Local error occurred."),
	LDAP_ENCODING_ERROR               	(0x53, null, "Encoding error occurred."),
	LDAP_DECODING_ERROR               	(0x54, null, "Decoding error occurred."),
	LDAP_TIMEOUT                      	(0x55, null, "The search was timed out."),
	LDAP_AUTH_UNKNOWN                 	(0x56, null, "Unknown authentication error occurred."),
	LDAP_FILTER_ERROR                 	(0x57, null, "The search filter is incorrect."),
	LDAP_USER_CANCELLED               	(0x58, null, "The user has canceled the operation."),
	LDAP_PARAM_ERROR                  	(0x59, null, "An incorrect parameter was passed to a routine."),
	LDAP_NO_MEMORY                    	(0x5a, null, "The system is out of memory."),
	LDAP_CONNECT_ERROR                	(0x5b, null, "Cannot establish a connection to the server."),
	LDAP_NOT_SUPPORTED                	(0x5c, null, "The feature is not supported."),
	LDAP_CONTROL_NOT_FOUND            	(0x5d, null, "The ldap function did not find the specified control."),
	LDAP_NO_RESULTS_RETURNED          	(0x5e, null, "The feature is not supported."),
	LDAP_MORE_RESULTS_TO_RETURN       	(0x5f, null, "Additional results are to be returned."),
	LDAP_CLIENT_LOOP                  	(0x60, null, "Client loop was detected."),
	LDAP_REFERRAL_LIMIT_EXCEEDED      	(0x61, null, "The referral limit was exceeded."),
	LDAP_SASL_BIND_IN_PROGRESS        	(0x0E, null, "Intermediary bind result for multi-stage binds");

	
	
    private static final String CLASS_NAME = LdapErrorsEnum.class.getSimpleName();

    private int errorCode;
	private String dataCode;
	private String description;

    
	
	private LdapErrorsEnum(int anErrorCode, String aDataCode, String aDescription) {
		errorCode = anErrorCode;
		dataCode = aDataCode;
		description = aDescription;
	}



	public int getErrorCode() {
    	return errorCode;
    }

	
	
	public String getDataCode() {
    	return dataCode;
    }

	
	
	public String getDescription() {
    	return description;
    }

	

	/**
	 * Get the LdapErrorsEnum object for the given error code
	 * @param errCode
	 * @return the LdapErrorsEnum object for the given error code
	 */
	public static LdapErrorsEnum getLdapError(int errCode, String datCode) {
		final String METHOD_NAME = "getLdapError(int, String)";
		
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method.");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    arguments:");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        errCode = [" + errCode + ']');
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        datCode = [" + datCode + ']');

        
        
		LdapErrorsEnum err = null;
		
		for (LdapErrorsEnum s : LdapErrorsEnum.values()) {
			if (s.getErrorCode() == errCode) {
				if (s.getDataCode() == null) {
					err = s;
				}
				
				if (datCode != null && !datCode.equalsIgnoreCase("") &&
						s.getDataCode() != null && s.getDataCode().equalsIgnoreCase(datCode)) {
					err = s;	
				}
			}
		} // for
		
		if (err == null) {
            TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "***** POSSIBLE BUG *****  Invalid error code = [" + errCode + "] and data code = [" + datCode + "]. Returning null. No exceptions thrown. Continuing...");
		}

        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method.");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "   return value = [" + err + "] = [" + 
        		(err != null ? err.getDescription() : "null") +
        		']');

        return err;
	} 
	/**
	 * @param errCode
	 * @return
	 */
	public static LdapErrorsEnum getLdapError(String errCode, String datCode) {
		final String METHOD_NAME = "getLdapError(String, String)";

        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method.");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    arguments:");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        errCode = [" + errCode + ']');
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        datCode = [" + datCode + ']');

        
        
        LdapErrorsEnum err = null;
		
		try {
			if (errCode != null && !errCode.trim().equalsIgnoreCase("")) {
				int cd = Integer.valueOf(errCode.trim());
				err = getLdapError(cd, datCode);
			}
		}
		catch (Exception e) {
            TiredexLogger.logException(CLASS_NAME, METHOD_NAME, "***** POSSIBLE BUG *****  Invalid error code = [" + errCode + "] and data code = [" + datCode + "]. Returning null. No exceptions thrown. Continuing...", e );
		}

		if (err == null) {
            TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "***** POSSIBLE BUG *****  Invalid error code = [" + errCode + "] and data code = [" + datCode + "]. Returning null. No exceptions thrown. Continuing...");
		}

        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method.");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "   return value = [" + err + "] = [" + 
        		(err != null ? err.getDescription() : "null") +
        		']');

        return err;
	} 
	
	
	
    /**
     * If you get an exception such as:
     * 	javax.naming.AuthenticationException: [LDAP: error code 49 - 80090308: LdapErr: DSID-0C0903A9, comment: AcceptSecurityContext error, data 701, v1db0]
     * @param exceptionMessage
     * @return
     */
    public static LdapErrorsEnum getLdapErrorFromException(String exceptionMessage) {
		final String METHOD_NAME = "getLdapErrorFromException";

		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method.");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    arguments:");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        exceptionMessage = [" + exceptionMessage + ']');

        
        
    	LdapErrorsEnum err = null;
    	int errPos = exceptionMessage.indexOf("error code", 0);
    	
    	if (errPos > 0) {
    		errPos += 10;
    		
        	int dash = exceptionMessage.indexOf(" - ", errPos);

        	String errCode =  exceptionMessage.substring(errPos + 1, dash);
        	
        	int datPosStart = exceptionMessage.indexOf(", data", 0) + 7;
        	int datPosEnd = exceptionMessage.indexOf(",", datPosStart);
        	if (datPosEnd < 0) {
        		datPosEnd = exceptionMessage.length() - 1;
        	}
        	
        	String datCode =  exceptionMessage.substring(datPosStart, datPosEnd).trim();

        	err = getLdapError(errCode, datCode);
    	}
    	
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method.");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "   return value = [" + err + "] = [" + 
        		(err != null ? err.getDescription() : "null") +
        		']');

        return err;
	}
}
