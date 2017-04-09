package com.ytc.common.result;

import org.apache.commons.httpclient.HttpStatus;

public enum ResultCode {
    OK(HttpStatus.SC_OK),

    GENERAL_ERROR("Unexpected error condition encountered. if this problem persists, please contact support."),

   
     
    NOT_FOUND(HttpStatus.SC_NOT_FOUND, "%s not found."),

   
    VALIDATION_ERROR(HttpStatus.SC_BAD_REQUEST),

    
   
    INVALID_LOGON(HttpStatus.SC_UNAUTHORIZED, "Invalid credentials or permission denied."),
	
	NOT_ACTIVE(HttpStatus.SC_UNAUTHORIZED, "user not in active state."), 
	
	INVALID_DATA("Please verify the data. It sould be invalid");

   
    
    private final int httpStatusCode;
    private final String resultString;

    private ResultCode() {
        this.httpStatusCode = HttpStatus.SC_INTERNAL_SERVER_ERROR;
        this.resultString = "";
    }

    private ResultCode(String resultString) {
        this.httpStatusCode = HttpStatus.SC_INTERNAL_SERVER_ERROR;
        this.resultString = resultString;
    }

    private ResultCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
        this.resultString = "";
    }

    private ResultCode(int httpStatusCode, String resultString) {
        this.httpStatusCode = httpStatusCode;
        this.resultString = resultString;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getResultString(Object... args) {
        if (args != null && args.length > 0) {
            return String.format(resultString, args);
        } else {
            return resultString;
        }
    }
}
