package com.ytc.common.result;

import java.io.Serializable;

public class ResultException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private ErrorResult result =null;
	
	public ResultException(ResultCode resultCode, String resultString, Serializable... data) {
        this.result = new ErrorResult(resultCode, resultString, data);
    }

}
