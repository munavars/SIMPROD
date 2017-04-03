package com.ytc.common.result;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


public class ErrorResult extends ListResult<Serializable>   {
	private String resultString;
	@JsonInclude(Include.NON_NULL)
	private String stackTrace;

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public ErrorResult() {
	}

	public ErrorResult(ResultCode code) {
		setResultCode(code);
	}

	public ErrorResult(ResultCode code, String resultString, Serializable... data) {
		this(code);
		this.resultString = resultString;
		if (data != null) {
			for (int i = 0; i < data.length; i++) {
				this.data.add(data[i]);
			}
		}
	}

	public ErrorResult(ResultCode code, Serializable[] data) {
		this(code, null, data);
		if (data != null) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < data.length; i++) {
				sb.append(data[i] == null ? "null" : data[i].toString());
				sb.append(";");
			}
			this.resultString = sb.toString();
		}
	}


	public ErrorResult(Exception e, String resultString) {
		this(ResultCode.GENERAL_ERROR, resultString, e);
	}


	public ErrorResult(ResultCode code, String resultString, Exception e) {
		this(code);
		this.resultString = resultString;
	}

	public ErrorResult(ErrorResult r) {
		this.resultString = r.resultString;
	}

	public String getResultString() {
		return resultString;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}

	@Override
	public String toString() {
		return super.toString() + ((resultString == null) ? "" : (" \"" + resultString + "\""));
	}
}
