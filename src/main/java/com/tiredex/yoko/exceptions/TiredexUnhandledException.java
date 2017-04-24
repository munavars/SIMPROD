package com.tiredex.yoko.exceptions;

import java.util.HashMap;
import java.util.Map;

public class TiredexUnhandledException extends Exception {
    public String errorId;

    public String message;

    public Map errorMap = new HashMap();

    /**
     * TiredexAppException constructor comment.
     */
    public TiredexUnhandledException () {
        super();
    }

    public TiredexUnhandledException (String errorMessage) {
        super(errorMessage);
        this.message = errorMessage;
    }

    /**
     * Insert the method's description here. Creation date: (1/29/2001 7:56:03
     * PM)
     * 
     * @param error_Id
     *            String
     * @param errorMessage
     *            String
     * @param errorMap
     *            java.util.Map
     */
    public TiredexUnhandledException (String anErrorId,
            String anErrorMessage,
            Map anErrorMap) {
        this.errorId = anErrorId;
        this.message = anErrorMessage;
        this.errorMap = anErrorMap;
    }

    /**
     * Insert the method's description here. Creation date: (1/29/2001 7:54:25
     * PM)
     * 
     * @param error_Id
     *            String
     * @param errorMap
     *            java.util.Map
     */
    public TiredexUnhandledException (String anErrorId, String anErrorMessage) {
        this.errorId = anErrorId;
        this.message = anErrorMessage;
    }

    public String getErrorId () {
        return this.errorId;
    }

    public String getMessage () {
        return this.message;
    }

    public Map getErrorMap () {
        return this.errorMap;
    }

}