package com.tiredex.yoko.utils;

/*****************************************************************************
Program Name		: TiredexLogger
Program Description	: See JavaDoc Comments

Program History		: This class contains methods which log messages in text files.

 *
 * $History: TiredexLogger.java $
 * 
 * *****************  Version 38  *****************
 * User: Alaing       Date: 8/01/13    Time: 11:32a
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * SSR 3489: Add support for Tire Registration website and web service.
 * 
 * *****************  Version 37  *****************
 * User: Alaing       Date: 8/27/12    Time: 12:09p
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * Make performance enhancement recommended by Java 6 compiler.
 * 
 * *****************  Version 36  *****************
 * User: Alaing       Date: 6/05/12    Time: 1:56p
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * SSR 3435: upgrade to Java 6.
 * 
 * *****************  Version 35  *****************
 * User: Alaing       Date: 8/30/10    Time: 3:38p
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * SSR 3080: add line items notes to wty claim.
 * SSR 3252: add Rotation field.
 * 
 * *****************  Version 34  *****************
 * User: Alaing       Date: 4/08/10    Time: 3:50p
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * WO 20617: Optimize logging.
 * 
 * *****************  Version 33  *****************
 * User: Alaing       Date: 3/18/10    Time: 5:50p
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * WO 20600: http session logging is interfering with the retrieval of
 * http parameters.
 * 
 * 
 * *****************  Version 32  *****************
 * User: Alaing       Date: 3/15/10    Time: 2:42p
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * WO 20600: reduce logging of HttpRequest
 * 
 * *****************  Version 31  *****************
 * User: Alaing       Date: 3/15/10    Time: 11:01a
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * WO 20600 - Change logging so that it does not interfere with concurrent
 * threads.  Remove synchorization where it is not needed.  Improve
 * logging performance.
 * 
 * *****************  Version 30  *****************
 * User: Alaing       Date: 1/13/10    Time: 5:00p
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * SSR 3184: add methods to help debug.
 * 
 * *****************  Version 29  *****************
 * User: Alaing       Date: 11/12/08   Time: 1:38p
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * SSR 3004: add convenience methods to reduce logging of large objects.
 * 
 * *****************  Version 28  *****************
 * User: Alaing       Date: 11/10/08   Time: 1:30p
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * SSR 3004: add logging message.
 * 
 * *****************  Version 27  *****************
 * User: Alaing       Date: 11/04/08   Time: 4:13p
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * SSR 3004: remove "synchronized" keyword from 5 method due to the
 * potential for threads to deadlock.
 * 

Programmer		Date			Description
Hemant Gaur		29-01-01		NEW
*****************************************************************************/
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class TiredexLogger implements Serializable {
    
    private static final String CONFIG_MGMT_VERSION = "$Revision: 38 $";
    // use SourceSafe revision number for the serialVersionUID
    private static final int firstSpace = CONFIG_MGMT_VERSION.indexOf(' ');
    private static final int lastSpace = CONFIG_MGMT_VERSION.lastIndexOf(' ');
    static final long serialVersionUID = Integer.parseInt(CONFIG_MGMT_VERSION.substring(firstSpace + 1, lastSpace));
	
    private static final String CLASS_NAME = "TiredexLogger";
    private static final String ERROR_LOG_FILE_NAME = "TiredexErrorLogFile";
    private static final String MESSAGE_LOG_FILE_NAME = "TiredexMessageLogFile";
    private static final String FILE_TYPE = ".txt";

    private static String errorLogFile = null;
    private static String messageLogFile = null;

    private static FileWriter errorWriter = null;
    private static FileWriter messageWriter = null;

    private static int errorLogDayInt = 0;
    private static int errorLogHourInt = 0;
    private static int messageLogDayInt = 0;
    private static int messageLogHourInt = 0;

    private static boolean isDebugMode = true;
        
    private static String errMutex = "";	// used to make checkErrorWriter method thread-safe; a String is used because it's a singleton class.
    private static String msgMutex = "";	// used to make checkMessageWriter method thread-safe; a String is used because it's a singleton class.

    /**
     * TiredexLogger constructor comment.
     */
    public TiredexLogger() {
        super();
    }


    private static void checkErrorWriter() throws IOException {
    	try {
            GregorianCalendar gc = new GregorianCalendar();
            int dayNow = gc.get(Calendar.DAY_OF_MONTH);
            int hourNow = gc.get(Calendar.HOUR_OF_DAY);

            if (hourNow != errorLogHourInt || dayNow != errorLogDayInt || errorWriter == null) {
                // it's a new day; it's time to close the current file and open a new one with a new name
                if (errorWriter != null) {
                    errorWriter.write(
                        TiredexUtils.getTimeStamp() 
                        + ">  " 
                        + Thread.currentThread().getName() 
                        + ">  Error log file [" 
                        + errorLogFile 
                        + "] closed.\r\n");
                    errorWriter.close();
                    errorWriter = null;
                }

                String year = "" + (gc.get(Calendar.YEAR));

                int monthInt = gc.get(Calendar.MONTH) + 1;
                String month = (monthInt < 10) ? "0" + monthInt : "" + monthInt;

                errorLogDayInt = dayNow;
                String day = (dayNow < 10) ? "0" + dayNow : "" + dayNow;
                
                errorLogHourInt = hourNow;
                String hour = (hourNow < 10) ? "0" + hourNow : "" + hourNow;

                synchronized (errMutex) {  // must guarantee this block is thread-safe.
                	errorLogFile = ERROR_LOG_FILE_NAME + '_' + year + '_' + month + '_' + day + '_' + hour + "00" + FILE_TYPE;
                }
            }

            if (errorWriter == null) {
                synchronized (errMutex) {  // must guarantee this block is thread-safe.
                	/* 
                	 * we have to check "if errorWriter == null" again because multiple threads might have checked
                	 * the first "if errorWriter == null" above but one thread made it past the "synchronized (errMutex)"
                	 * but the other threads are waiting there.  As soon as the first thread finishes the 
                	 * synchronized block, errorWriter will already have a new value.
                	 */
                    if (errorWriter == null) {
                    	errorWriter = new FileWriter(errorLogFile, true); // boolean append
                    }
                } // synchronized
                
                errorWriter.write(
                    TiredexUtils.getTimeStamp() 
                    + ">  " 
                    + Thread.currentThread().getName() 
                    + ">  Error log file [" 
                    + errorLogFile 
                    + "] opened.\r\n");
            }
    	}
        catch (IOException e) {
            System.out.println(
                CLASS_NAME
                    + ".checkErrorWriter; "
                    + ErrorMessageInterface.REMOTE_UNABLE_TO_WRITE
                    + "; "
                    + e
                    + "; re-raising exception.");
			throw e;
		}
		catch (Exception e) {
			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".checkErrorWriter; "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
		}
    }

    /**
     * This method writes the string passed to it into a text file named(TiredexErrorLogFile.TXT) 
     * @author Hemant Gaur
     * @param messageToBeLogged String
     * @return void
     * @throws RemoteException
     */
    public static void logError(String messageToBeLogged) {
    	
        logMessage(messageToBeLogged);

        try {
        	checkErrorWriter();
            
        	String timeStamp = TiredexUtils.getTimeStamp();
        	String threadName = Thread.currentThread().getName();

			StringBuffer s = 
                new StringBuffer(timeStamp.length() + 3 + threadName.length() + 3 + messageToBeLogged.length() + 2)
                .append(timeStamp)
                .append('>')
                .append(' ')
                .append(' ')
                .append(threadName)
                .append('>')
                .append(' ')
                .append(' ')
                .append(messageToBeLogged)
                .append('\r')
                .append('\n');
            errorWriter.write(s.toString());
            errorWriter.flush(); // close();
        }
        catch (IOException e) {
            System.out.println(
                CLASS_NAME
                    + ".logError; "
                    + ErrorMessageInterface.REMOTE_UNABLE_TO_WRITE
                    + "; "
                    + e
                    + "; error writing message = "
                    + messageToBeLogged);
        }
		catch (Exception e) {
			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".logError(messageToBeLogged); "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
		}
    }
    /**
     * This method writes the concatenates the strings passed to it using '=' as a delimeiter
     * into a text file named (TiredexErrorLogFile.TXT) 
     * @author Hemant Gaur
     * @param messageIdentifier String
     * @param messageToBeLogged String
     * @return void
     */
    public static void logError(String messageIdentifier, String messageToBeLogged) {
		try {
	        // use StringBuffer to maximize performance
	        StringBuffer s = new StringBuffer(messageIdentifier.length() + 1 + messageToBeLogged.length())	// add all the string lengths of the "appends" below
	        	.append(messageIdentifier)
	        	.append('=')
	        	.append(messageToBeLogged);
	        logError(s.toString());
		}
		catch (Exception e) {
			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".logError(messageIdentifier, messageToBeLogged); "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
		}
    }
    /**
     * Insert the method's description here.
     * Creation date: (10/8/2002 3:13:56 PM)
     * @param className java.lang.String
     * @param methodName java.lang.String
     * @param messageToRecord java.lang.String
     */
    public static void logError(String className, String methodName, String messageToRecord) {
		try {
	        // use StringBuffer to maximize performance
	        StringBuffer s = new StringBuffer(className.length() + 1 + methodName.length() + 2 + messageToRecord.length())	// add all the string lengths of the "appends" below
	            .append(className)
	            .append('.')
	            .append(methodName)
	            .append(';')
	            .append(' ')
	            .append(messageToRecord);
	        logError(s.toString());
		}
		catch (Exception e) {
			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".logError(className, methodName, messageToRecord); "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
		}
    }
    /**
     * Records a message and the exception trace to stderr
     * Creation date: (10/16/2003 11:00 AM)
     * @param messageToRecord java.lang.String
     * @param exception java.lang.Exception
     */
    public static void logException(String messageToBeLogged, Exception ex) {

		try {
	        // use StringBuffer to maximize performance
	
	    	String timeStamp = TiredexUtils.getTimeStamp();
	    	String threadName = Thread.currentThread().getName();
	
			StringBuffer s = 
	            new StringBuffer(timeStamp.length() + 3 + threadName.length() + 3 + messageToBeLogged.length() + 1 + ex.toString().length() + 2)
	            .append(timeStamp)
	            .append('>')
	            .append(' ')
	            .append(' ')
	            .append(threadName)
	            .append('>')
	            .append(' ')
	            .append(' ')
	            .append(messageToBeLogged)
	            .append(' ')
	            .append(ex.toString());
	        System.err.println(s.toString());
	        logError(s.toString());
	        StackTraceElement trace[] = ex.getStackTrace();
	        for (int j = 0; j < trace.length; j++) {
	            logError("    " + trace[j].toString());
	        }
//	        ex.printStackTrace(messageWriter.);
        }
		catch (Exception e) {
			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".logException(messageToRecord, ex); "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
		}
    }
    /**
     * Records a message and the exception trace to stderr
     * Creation date: (10/16/2003 11:00 AM)
     * @param className java.lang.String
     * @param methodName java.lang.String
     * @param messageToRecord java.lang.String
     * @param exception java.lang.Exception
     */
    public static void logException(String className, String methodName, String messageToRecord, Exception ex) {
		try {
	        // use StringBuffer to maximize performance
	        StringBuffer s = new StringBuffer(className.length() + 1 + methodName.length() + 2 + messageToRecord.length() + 2 + ex.toString().length() + 45)	// add all the string lengths of the "appends" below
	            .append(className)
	            .append('.')
	            .append(methodName)
	            .append(';')
	            .append(' ')
	            .append(messageToRecord)
	            .append(' ')
	            .append(' ')
	            .append(ex.toString())
	            .append("  See stderr file for exception stack trace."); // length = 45
	        logException(s.toString(), ex);
        }
		catch (Exception e) {
			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".logException(className, methodName, messageToRecord, ex); "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
		}
    }
    
    /**
     * Record the contents of the parameters and attributes of the request object
     * 
     * @param className The name of the class calling this method.
     * @param methodName The name of the method calling this method.
     * @param req The HttpServletRequest object
     */
    public static void logHttpRequestParametersAndAttributes(String className, String methodName, HttpServletRequest req) {
		try {
	        if (req != null) {
	        	logHttpRequestParameters(className, methodName, req);

	        	logHttpRequestAttributes(className, methodName, req);
		    }
		}
		catch (Exception e) {
			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".logHttpRequest; "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
		}
    }
    
    
    
    /**
     * Get the contents of req.getHeaderNames from the HttpServletRequest object, 
     * call the corresponding getHeader(), and log the results.
     * 
     * @param className
     * @param methodName
     * @param req
     */
    public static void logHttpRequestHeader(String className, String methodName, HttpServletRequest req) {

    	try {
		    if (req != null) {
		        String attrName;
		        
		        // use StringBuffer to optimize performance since this method gets called alot.
				StringBuffer msg = new StringBuffer(256);
				
		        logMessage(className, methodName, "calling req.getHeaderNames...");
		        Enumeration enu = req.getHeaderNames();
		        logMessage(className, methodName, "request object header list:");
		        while (enu.hasMoreElements()) {
		            attrName = (String) enu.nextElement();
		            msg.append("    ");
		            msg.append(attrName);
		            msg.append(" = [");
		            msg.append(req.getHeader(attrName));	// get header value
		            msg.append(']');
		            logMessage(className, methodName,  msg.toString());
		            msg.delete(0, msg.length());
		        } // while
		    } // if req != null    		
		    else {
		        logMessage(className, methodName, "request = [null]");
		    }
    	}
		catch (Exception e) {
			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".logHttpRequestHeader; "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
		}
    }
    
    
    
    /**
     * Get the contents of req.getAttributeNames from the HttpServletRequest object, 
     * call the corresponding req.getAttribute(), and log the results.
     * 
     * @param className
     * @param methodName
     * @param req
     */
    public static void logHttpRequestAttributes(String className, String methodName, HttpServletRequest req) {
    	
    	try {
    		if (req != null) {
    	        String attrName;
    	        
    	        // use StringBuffer to optimize performance since this method gets called alot.
    			StringBuffer msg = new StringBuffer(256);
    			
    	
    	        logMessage(className, methodName, "calling req.getAttributeNames...");
    	        Enumeration enu = req.getAttributeNames();
    	        logMessage(className, methodName, "request object attribute list:");
    	        while (enu.hasMoreElements()) {
    	            attrName = (String) enu.nextElement();
    	            msg.append("    ");
    	            msg.append(attrName);
    	            msg.append(" = [");
    	            msg.append(req.getAttribute(attrName));	// get attribute value
    	            msg.append(']');
    	            logMessage(className, methodName,  msg.toString());
    	            msg.delete(0, msg.length());
    	        } // while
    		} // if req != null
		    else {
		        logMessage(className, methodName, "request = [null]");
		    }
    	}
		catch (Exception e) {
			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".logHttpRequestAttributes; "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
		}
    }
    
    
    
    /**
     * Get the contents of req.getParameterNames from the HttpServletRequest object, 
     * call the corresponding req.getParameterValues(), and log the results.
     * 
     * @param className
     * @param methodName
     * @param req
     */
    public static void logHttpRequestParameters(String className, String methodName, HttpServletRequest req) {

    	try {
    		if (req != null) {
		        String attrName;
    			
		        // use StringBuffer to optimize performance since this method gets called alot.
				StringBuffer msg = new StringBuffer(256);
				
		        logMessage(className, methodName, "calling req.getParameterNames...");
		        Enumeration enu = req.getParameterNames();
		        logMessage(className, methodName, "request object parameter list:");
		        while (enu.hasMoreElements()) {
		            attrName = (String) enu.nextElement();
		            String val[] = req.getParameterValues(attrName);
		            
		            if (val != null) {
		            	if (val.length == 1) {
		            		msg.append("    ");
		            		msg.append(attrName);
		            		msg.append(" = [");
		            		msg.append(val[0]);
		            		msg.append(']');
		            	} // if
		            	else if (val.length > 1) {
		            		for (int j = 0; j < val.length; j++) {
		            			msg.append("    ");
								msg.append(attrName);
								msg.append('[');
								msg.append(j);
								msg.append("] = [");
								msg.append(val[j]);
								msg.append("]\r\n");	
		            		} // for
		            	}
		            	else {
		            		// this should never happen
		            		msg.append("***** ");
		            		msg.append(attrName);
		            		msg.append(" *****  array size = 0 and it should not.");
		            	}
		            } // if val != null
		            else {
		            	msg.append("    ");
		            	msg.append(attrName);
		            	msg.append(" = [null]");
		            }
		            
		            logMessage(className, methodName,  msg.toString());
		            msg.delete(0, msg.length());
		        } // while
    		} // if req != null
		    else {
		        logMessage(className, methodName, "request = [null]");
		    }
    	}
		catch (Exception e) {
			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".logHttpRequestParameters; "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
		}
    }

    
    
    /**
     * Get the contents of various req.get...() from the HttpServletRequest object 
     * and log the results.
     * 
     * @param className
     * @param methodName
     * @param req
     */
    public static void logHttpRequestMiscAttributes(String className, String methodName, HttpServletRequest req) {
    	try {
    		if (req != null) {
				// Put these messages separately from the array below so we can
				// tell if there is a problem with any of the "gets".
	            logMessage(className, methodName, "request additional attributes :");
	            logMessage(className, methodName, "    authType = [" + req.getAuthType() + ']');
	            logMessage(className, methodName, "    contextPath = [" + req.getContextPath() + ']');
	            logMessage(className, methodName, "    pathTranslated = [" + req.getPathTranslated() + ']');
	            logMessage(className, methodName, "    protocol = [" + req.getProtocol() + ']');
	            logMessage(className, methodName, "    queryString = [" + req.getQueryString() + ']');
	            logMessage(className, methodName, "    requestURI = [" + req.getRequestURI() + ']');
	            logMessage(className, methodName, "    remoteAddr = [" + req.getRemoteAddr() + ']');
	            logMessage(className, methodName, "    remoteUser = [" + req.getRemoteUser() + ']');
	            logMessage(className, methodName, "    scheme = [" + req.getScheme() + ']');
	            logMessage(className, methodName, "    servletPath = [" + req.getServletPath() + ']');
	            logMessage(className, methodName, "    selectedURL = [" + req.getPathInfo() + ']');
	            logMessage(className, methodName, "    serverName = [" + req.getServerName() + ']');
	            logMessage(className, methodName, "    serverPort = [" + req.getServerPort() + ']');
	            logMessage(className, methodName, "    isSecure = [" + req.isSecure() + ']');
    		} // if req != null
		    else {
		        logMessage(className, methodName, "request = [null]");
		    }
    	}
		catch (Exception e) {
			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".logHttpRequestMiscAttributes; "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
    	}
    }
    

    
    /**
     * Get the contents of req.getCookies from the HttpServletRequest object, 
     * call the various get...(), and log the results.
     * 
     * @param className
     * @param methodName
     * @param req
     */
    public static void logHttpRequestCookies(String className, String methodName, HttpServletRequest req) {
    	try {
    		if (req != null) {
		        logMessage(className, methodName, "calling req.getCookies...");
		        Cookie cookies[] = req.getCookies();
		        if (cookies != null) {
			        logMessage(className, methodName, "request.cookies; number of cookies found: [" + cookies.length + ']');
			        for (int j = 0; j < cookies.length; j++) {
						String messages[] = {
			                "    cookies[" + j + "] =",
			                "        comment = [" + cookies[j].getComment() + ']',
			                "        domain = [" + cookies[j].getDomain() + ']',
			                "        maxAge = [" + cookies[j].getMaxAge() + ']',
			                "        name = [" + cookies[j].getName() + ']',
			                "        path = [" + cookies[j].getPath() + ']',
			                "        secure = [" + cookies[j].getSecure() + ']',
			                "        value = [" + cookies[j].getValue() + ']',
			                "        version = [" + cookies[j].getVersion() + ']'
				        	};
				        logMessages(className, methodName, messages);
			        } // for
		        } // if cookies != null
		        else {
			        logMessage(className, methodName, "request.cookies; cookies = [null]");
		        }
    		} // if req != null
		    else {
		        logMessage(className, methodName, "request = [null]");
		    }
    	}
		catch (Exception e) {
			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".logHttpRequestMiscAttributes; "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
    	}
    }

    

	/**
     * Record the contents of the HttpServletRequest object.
     * Creation date: (10/9/2002 2:00:33 PM)
     * @param className java.lang.String
     * @param methodName java.lang.String
     * @param request javax.servlet.http.HttpServletRequest
     */
    public static void logHttpRequest(String className, String methodName, HttpServletRequest req, boolean isLoggingAllAttr) {
    
    	try {
    		logHttpRequest(className, methodName, req, false, false);
        }
    	catch (Exception e) {
    		// we don't want exceptions within our debug code to propagate to the users
        	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".logHttpRequest(4 args); "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
           	e.printStackTrace(System.err);
    	}
        
    }

    
    
    /**
     * Record the contents of the HttpServletRequest object.
     * Creation date: (10/9/2002 2:00:33 PM)
     * @param className java.lang.String
     * @param methodName java.lang.String
     * @param request javax.servlet.http.HttpServletRequest
     * @param isLoggingReqParams if TRUE, the HttpServletRequest request parameters will be logged.
     * @param isLoggingSession if TRUE, the HttpServletRequest session attributes will be logged.
     */
    public static void logHttpRequest(String className, String methodName, HttpServletRequest req, boolean isLoggingReqParams, boolean isLoggingSession) {

		try {
		    if (req != null) {
		    	logHttpRequestHeader(className, methodName, req);
		    	
		    	logHttpRequestAttributes(className, methodName, req);
				
		    	if (isLoggingReqParams) {
		    		logHttpRequestParameters(className, methodName, req);	
		    	}
		    	else {
			        logMessage(className, methodName, "skipping req.getParameters...");
		    	}
		    	
		        logHttpRequestCookies(className, methodName, req);
		
		    	if (isLoggingSession) {
			        logMessage(className, methodName, "calling req.getSession...");
			        HttpSession session = req.getSession();
			        logMessage(className, methodName, "request.session = [" + session + ']');
			        logHttpSession(className, methodName, session);
		    	}
		    	else {
			        logMessage(className, methodName, "skipping req.getSession...");
		    	}
		    }
		    else {
		        logMessage(className, methodName, "request = [null]");
		    }
        }
		catch (Exception e) {
			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".logHttpRequest(5 args); "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
		}
        
    }
    

    
    /**
     * Record the contents of the HttpServletRequest object.
     * Creation date: (10/9/2002 2:00:33 PM)
     * @param className java.lang.String
     * @param methodName java.lang.String
     * @param request javax.servlet.http.HttpServletRequest
     */
    public static void logHttpRequest(String className, String methodName, HttpServletRequest req) {

		try {
			logHttpRequest(className, methodName, req, true, false);
        }
		catch (Exception e) {
			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".logHttpRequest(3 args); "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
		}
        
    }

    
    
    /**
     * Record the contents of the HttpSession object which is in HttpServletRequest.
     * 
     * @param className
     * @param methodName
     * @param req
     */
    public static void logHttpSession(String className, String methodName, HttpServletRequest req) {
    	if (req != null) {
    		HttpSession session = req.getSession();
    		logHttpSession(className, methodName, session);
    	} // if
	    else {
	        logMessage(className, methodName, "request = [null]");
	    }
    }
    
    
    
    /**
     * Record the contents of the HttpSession object.
     * 
     * @param className java.lang.String
     * @param methodName java.lang.String
     * @param session javax.servlet.http.HttpSession
     */
    public static void logHttpSession(String className, String methodName, HttpSession session) {
    	
    	try {
    	    if (session != null) {
		        String attrName;
				StringBuffer msg = new StringBuffer(256);
				final String header = "    ";

		        logMessage(className, methodName, "request.session object attributes :");
		        logMessage(className, methodName, "    id = [" + session.getId() + ']');
		        logMessage(className, methodName, "    creationTime = [" + new java.util.Date(session.getCreationTime()) + ']');
		        logMessage(className, methodName, "    lastAccessedTime = [" + new java.util.Date(session.getLastAccessedTime()) + ']');
		        logMessage(className, methodName, "    maxInactiveInterval = [" + session.getMaxInactiveInterval() + ']');
		        logMessage(className, methodName, "    isNew = [" + session.isNew() + ']');
		        logMessage(className, methodName, "    hashCode = [" + session.hashCode() + ']');

		        logMessage(className, methodName, "calling session.getAttributeNames...");
		        Enumeration enu = session.getAttributeNames();
		        logMessage(className, methodName, "request.session object additional attribute list:");
		        while (enu.hasMoreElements()) {
		            attrName = (String) enu.nextElement();
				    Object value = session.getAttribute(attrName);
				    String valueClassName = value.getClass().getName();
				
					String valueInfo = " value class = [" + valueClassName + "] value(s):";
					String attrInfo = "attribute [" + attrName.toString() + "] class = [" + attrName.getClass().getName() + ']';
	
			        if (valueClassName.equalsIgnoreCase("java.util.Map") || valueClassName.equalsIgnoreCase("java.util.HashMap")) {
						logMessage(className, methodName, header + attrInfo + valueInfo);
			        	logMapDetails(className, methodName, header + "    ", (Map)value);
			        }
			        else if (valueClassName.equalsIgnoreCase("java.util.ArrayList")) {
						logMessage(className, methodName, header + attrInfo + valueInfo);
			        	logArrayListDetails(className, methodName, header + "    ", (ArrayList)value);
			        }
			        else if (valueClassName.equalsIgnoreCase("java.util.Set") || valueClassName.equalsIgnoreCase("java.util.HashSet")) {
						logMessage(className, methodName, header + attrInfo + valueInfo);
			        	logSetDetails(className, methodName, header + "    ", (Set)value);
			        }
                    else if (valueClassName.equalsIgnoreCase("java.util.Hashtable")) {
                        logMessage(className, methodName, header + attrInfo + valueInfo);
                        logHashtableDetails(className, methodName, header + "    ", (Hashtable)value);
                    }
			        else if (value.getClass().isArray()) {
			        	logMessage(className, methodName, header + attrInfo + valueInfo);
			        	logArrayDetails(className, methodName, header + "    ", (Object[]) value);
			        }
			        else {
			            msg.append(header);
			            msg.append(attrInfo);
			            msg.append("; value = [");
			            msg.append(value);
			            msg.append("] class = [");
			            msg.append(valueClassName);
			            msg.append(']');
			            logMessage(className, methodName,  msg.toString());
			            msg.delete(0, msg.length());
			        }	// else
		        }	// while
		    }
		    else {
		        logMessage(className, methodName, "session = [null]");
		    }
        }
		catch (Exception e) {
			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".logHttpSession; "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
		}
    }

    private static void checkDebugMode() {
    	try {
	        String debug = TiredexUtils.getCommonFnBdlProperty("DEBUG");
  	        isDebugMode = debug.equalsIgnoreCase("true");
        }
        catch (Exception e) {
            isDebugMode = true;		// default value if property value is not set

			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".checkDebugMode; "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
        }
    }
    
    private static void checkMessageWriter() throws IOException {
    	try {
            GregorianCalendar gc = new GregorianCalendar();
            int dayNow = gc.get(Calendar.DAY_OF_MONTH);
			int hourNow = gc.get(Calendar.HOUR_OF_DAY);
			
            if (hourNow != messageLogHourInt || dayNow != messageLogDayInt || messageWriter == null) {
                // it's a new day; it's time to close the current file and open a new one with a new name
                if (messageWriter != null) {
                    messageWriter.write(
                        TiredexUtils.getTimeStamp()
                        + ">  "
                        + Thread.currentThread().getName()
                        + ">  Message log file ["
                        + messageLogFile
                        + "] closed.\r\n");
                    messageWriter.close();
                    messageWriter = null;
                }

                String year = "" + (gc.get(Calendar.YEAR));

                int monthInt = gc.get(Calendar.MONTH) + 1;
                String month = (monthInt < 10) ? "0" + monthInt : "" + monthInt;

                messageLogDayInt = dayNow;
                String day = (dayNow < 10) ? "0" + dayNow : "" + dayNow;

 	            messageLogHourInt = hourNow;
                String hour = (hourNow < 10) ? "0" + hourNow : "" + hourNow;

                synchronized (msgMutex) {  // must guarantee this block is thread-safe.
                	messageLogFile = MESSAGE_LOG_FILE_NAME + '_' + year + '_' + month + '_' + day + '_' + hour + "00" + FILE_TYPE;
                }
            }

            if (messageWriter == null) {
                synchronized (msgMutex) {  // must guarantee this block is thread-safe.
                	/* 
                	 * we have to check "if messageWriter == null" again because multiple threads might have checked
                	 * the first "if messageWriter == null" above but one thread made it past the "synchronized (msgMutex)"
                	 * but the other threads are waiting there.  As soon as the first thread finishes the 
                	 * synchronized block, messageWriter will already have a new value.
                	 */
                    if (messageWriter == null) {
                        messageWriter = new FileWriter(messageLogFile, true); // boolean append
                    }
                } // synchronized
                
                messageWriter.write(
                    TiredexUtils.getTimeStamp()
                    + ">  "
                    + Thread.currentThread().getName()
                    + ">  Message log file ["
                    + messageLogFile
                    + "] opened.\r\n");
            }
        }
        catch (IOException e) {
            System.out.println(
                CLASS_NAME
                    + ".checkMessageWriter; "
                    + ErrorMessageInterface.REMOTE_UNABLE_TO_WRITE
                    + "; "
                    + e
                    + "; re-raising exception.");
			throw e;
        }
		catch (Exception e) {
			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".checkMessageWriter; "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
		}
    }

    /**
     * This method writes the strings passed to it into the message file.  Each string in the array
     * is written to a separate line and timestamped.  The difference between using this method
     * and calling logMessage several times is that in this method all the output lines will have
     * the same timestamp and thread name.  The logMessage routine will acquire a new timestamp and
     * thread name for each call.
     * @author Alain Graziani
     * @param messagesToBeLogged String[]
     * @return void
     */
	public static void logMessages(String messagesToBeLogged[]) {

		// Try to optimize for best performance.
		
		String timeStamp = TiredexUtils.getTimeStamp();
		String threadName = Thread.currentThread().getName();
		
        try {
			checkDebugMode();
            if (isDebugMode) {
            	checkMessageWriter();
            	
            	// add all the string lengths of the "appends" below
            	final int minMessageSize = timeStamp.length() + 3 + threadName.length() + 3 + 2;
            	
                StringBuffer buff = new StringBuffer(minMessageSize + 256);

            	int numMessages = messagesToBeLogged.length;
                
				for (int j = 0; j < numMessages; j++) {
					buff.ensureCapacity(minMessageSize + messagesToBeLogged[j].length());
					buff.setLength(0);
	                buff.append(timeStamp)
	                        .append('>')
	                        .append(' ')
	                        .append(' ')
	                        .append(threadName)
	                        .append('>')
	                        .append(' ')
	                        .append(' ')
	                        .append(messagesToBeLogged[j])
	                        .append('\r')
	                        .append('\n');
	                messageWriter.write(buff.toString());
	                messageWriter.flush();
				}
            }
            else { // debug = false
                if (messageWriter != null) {
                    messageWriter.write(
                        timeStamp
                        + ">  "
                        + threadName
                        + ">  Message log file ["
                        + messageLogFile
                        + "] closed.\r\n");
                    messageWriter.close();
                    messageWriter = null;
                }
            }
        }
        catch (IOException e) {
            System.out.println(
                CLASS_NAME
                    + ".logMessages; "
                    + ErrorMessageInterface.REMOTE_UNABLE_TO_WRITE
                    + "; "
                    + e
                    + "; error writing messages: ["
                    + messagesToBeLogged
                    + ']');
        }
		catch (Exception e) {
			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".logMessages(messagesToBeLogged); "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
		}
	}

    /**
     * This method writes the string passed to it into a text file named(TiredexMessageLogFile.TXT) 
     * @author Hemant Gaur
     * @param messageToBeLogged String
     * @return void
     */
    private static void writeMessage(String messageToWrite) {

        try {
			checkDebugMode();
            if (isDebugMode) {
            	checkMessageWriter();
            	
                messageWriter.write(messageToWrite);
                messageWriter.flush();
            }
            else { // debug = false
                if (messageWriter != null) {
                    messageWriter.write(
                        TiredexUtils.getTimeStamp()
                        + ">  "
                        + Thread.currentThread().getName()
                        + ">  Message log file ["
                        + messageLogFile
                        + "] closed.\r\n");
                    messageWriter.close();
                    messageWriter = null;
                }
            }
        }
        catch (IOException e) {
            System.out.println(
                CLASS_NAME
                    + ".writeMessage; "
                    + ErrorMessageInterface.REMOTE_UNABLE_TO_WRITE
                    + "; "
                    + e
                    + "; error writing message: ["
                    + messageToWrite
                    + ']');
        }
		catch (Exception e) {
			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".writeMessage; "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
		}
    }

    /**
     * This method writes the string passed to it but inserts the timestamp and thread name first; 
     * the caller should use a StringBuffer with room for at least 60 extra characters.
     * @author Alain Graziani
     * @param messageBufferToBeLogged StringBuffer
     * @return void
     */
    public static void logMessageBuffer(StringBuffer messageBufferToBeLogged) {
            	
        try {
	    	String timeStamp = TiredexUtils.getTimeStamp();
 		   	String threadName = Thread.currentThread().getName();

			// reuse the existing StringBuffer to avoid generating a new object and hopefully
			// improve the efficiency of the logging
	        messageBufferToBeLogged.insert(0, timeStamp + ">  " + threadName + ">  ")
	            .append('\r')
	            .append('\n');
	            
			writeMessage(messageBufferToBeLogged.toString());
        }
		catch (Exception e) {
			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".logMessageBuffer; "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
		}
    }
    
    
    /**
     * This method writes the string passed to it but inserts the timestamp and thread name first; the caller should
     * use a StringBuffer with a length of at least 60 extra characters.
     * @author Hemant Gaur
     * @param messageToBeLogged String
     * @return void
     */
    public static void logMessage(String messageToBeLogged) {

		try {
	        StringBuffer buff = new StringBuffer(messageToBeLogged.length() + 60)
            	.append(messageToBeLogged);
                
			logMessageBuffer(buff);
        }
		catch (Exception e) {
			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".logMessage; "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
		}
    }



    /**
     * This method writes the strings passed to it into the message file.  Each string in the array
     * is written to a separate line and timestamped.  The difference between using this method
     * and calling logMessage several times is that in this method all the output lines will have
     * the same timestamp and thread name.  The logMessage routine will acquire a new timestamp and
     * thread name for each call.
     * @author Alain Graziani
     * @param className java.lang.String
     * @param methodName java.lang.String
     * @param messagesToBeLogged String[]
     * @return void
     */
    public static void logMessages(String className, String methodName, String messagesToRecord[]) {

		try {
			String messages[] = new String[messagesToRecord.length];
			
			// Try to optimize for best performance.
			
			int numMessages = messagesToRecord.length;
	
			// add all the string lengths of the "appends" below
			final int minMessageSize = className.length() + 1 + methodName.length() + 2;
			
	        StringBuffer s = new StringBuffer(minMessageSize + 256);
			for (int j = 0; j < numMessages; j++) {
				s.ensureCapacity(minMessageSize + messagesToRecord[j].length());
				s.setLength(0);
		        s.append(className)
		        	.append('.')
		        	.append(methodName)
		        	.append(';')
		        	.append(' ')
		        	.append(messagesToRecord[j]);
	        	messages[j] = s.toString();
			}
			logMessages(messages);
        }
		catch (Exception e) {
			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".logMessages(className, methodName, messagesToRecord); "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
		}
    }
    /**
     * Insert the method's description here.
     * Creation date: (10/8/2002 3:12:48 PM)
     * @param className java.lang.String
     * @param methodName java.lang.String
     * @param messageToRecord java.lang.String
     */
    public static void logMessage(String className, String methodName, String messageToRecord) {
		try {
	        // use StringBuffer to maximize performance
	        StringBuffer s = new StringBuffer(className.length() + 1 + methodName.length() + 2 + messageToRecord.length() + 60)	// add all the string lengths of the "appends" below plus room for timestamp and thread name
	        	.append(className)
	        	.append('.')
	        	.append(methodName)
	        	.append(';')
	        	.append(' ')
	        	.append(messageToRecord);
	        	
	        logMessageBuffer(s);
        }
		catch (Exception e) {
			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".logMessage(className, methodName, messageToRecord); "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
		}
    }

	/**
	 * Utility to log the contents of a java.util.Map object in a more readable format.
     * @param className java.lang.String
     * @param methodName java.lang.String
     * @param header java.lang.String
     * @param map java.lang.Map
	 */
	public static void logMapDetails(String className, String methodName, String header, Map map) {
		
		try {
			if (map != null && !map.isEmpty()) {	
                logMessage(className, methodName, header + "map class = [" + map.getClass().getName() + ']');
                logMessage(className, methodName, header + "map.size() = [" + map.size() + ']');

		        Set keySet = map.keySet();
				Iterator it = keySet.iterator();
		
		        for ( ; it.hasNext(); ) {
			        Object key = it.next();
			        Object value = map.get(key);
			        String valueStr = null;
			        String valueClassName = null;
			        
					String keyInfo = "key = [" + key.toString() + "] class = [" + key.getClass().getName() + ']';
		
			        if (value != null) {
			        	valueClassName = value.getClass().getName();
						String valueInfo = " value class = [" + valueClassName + "] value(s):";
			
				        if (valueClassName.equalsIgnoreCase("java.util.Map") || valueClassName.equalsIgnoreCase("java.util.HashMap")) {
							logMessage(className, methodName, header + keyInfo + valueInfo);
				        	logMapDetails(className, methodName, header + "    ", (Map)value);
				        }
				        else if (valueClassName.equalsIgnoreCase("java.util.ArrayList")) {
							logMessage(className, methodName, header + keyInfo + valueInfo);
				        	logArrayListDetails(className, methodName, header + "    ", (ArrayList)value);
				        }
				        else if (valueClassName.equalsIgnoreCase("java.util.Set") || valueClassName.equalsIgnoreCase("java.util.HashSet")) {
							logMessage(className, methodName, header + keyInfo + valueInfo);
				        	logSetDetails(className, methodName, header + "    ", (Set)value);
				        }
    			        else if (valueClassName.equalsIgnoreCase("java.util.Hashtable")) {
    						logMessage(className, methodName, header + keyInfo + valueInfo);
    			        	logHashtableDetails(className, methodName, header + "    ", (Hashtable)value);
    			        }
    			        else if (value.getClass().isArray()) {
    			        	logMessage(className, methodName, header + keyInfo + valueInfo);
    			        	logArrayDetails(className, methodName, header + "    ", (Object[]) value);
    			        }
				        else {
				        	valueStr = value.toString();
							logMessage(className, methodName, header + keyInfo + "; value = [" + valueStr + "] class = [" + valueClassName + ']');
				        }
			        }
			        else {
						logMessage(className, methodName, header + keyInfo + " value = [null]");
			        }
		        }	// for
			}
			else if (map == null) {
				logMessage(className, methodName, header + " map is null");
			}
			else {	// map.isEmpty() == true
				logMessage(className, methodName, header + " map is empty");
			}
        }
		catch (Exception e) {
			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".logMapDetails; "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
		}
	}

	/**
	 * Utility to log the contents of a java.util.Map object in a more readable format.
     * @param className java.lang.String
     * @param methodName java.lang.String
     * @param header java.lang.String
     * @param set java.lang.Set
	 */
	public static void logSetDetails(String className, String methodName, String header, Set set) {
		
		try {
			if (set != null && !set.isEmpty()) {	
                logMessage(className, methodName, header + "set class = [" + set.getClass().getName() + ']');
                logMessage(className, methodName, header + "set.size() = [" + set.size() + ']');

				Iterator it = set.iterator();
		
		        for ( ; it.hasNext(); ) {
			        Object setItem = it.next();
			        String setItemStr = null;
			        String setItemClassName = null;
			        
			        if (setItem != null) {
			        	setItemClassName = setItem.getClass().getName();
			
						String setItemInfo = " item class = [" + setItemClassName + "] item(s):";

						if (setItemClassName.equalsIgnoreCase("java.util.Map") || setItemClassName.equalsIgnoreCase("java.util.HashMap")) {
							logMessage(className, methodName, header + setItemInfo);
				        	logMapDetails(className, methodName, header + "    ", (Map)setItem);
				        }
				        else if (setItemClassName.equalsIgnoreCase("java.util.ArrayList")) {
							logMessage(className, methodName, header + setItemInfo);
				        	logArrayListDetails(className, methodName, header + "    ", (ArrayList)setItem);
				        }
				        else if (setItemClassName.equalsIgnoreCase("java.util.Set") || setItemClassName.equalsIgnoreCase("java.util.HashSet")) {
							logMessage(className, methodName, header + setItemInfo);
				        	logSetDetails(className, methodName, header + "    ", (Set)setItem);
				        }
    			        else if (setItemClassName.equalsIgnoreCase("java.util.Hashtable")) {
    						logMessage(className, methodName, header + setItemInfo);
    			        	logHashtableDetails(className, methodName, header + "    ", (Hashtable)setItem);
    			        }
    			        else if (setItem.getClass().isArray()) {
    			        	logMessage(className, methodName, header + setItemInfo);
    			        	logArrayDetails(className, methodName, header + "    ", (Object[]) setItem);
    			        }
				        else {
				        	setItemStr = setItem.toString();
							logMessage(className, methodName, header + " item = [" + setItemStr + "] class = [" + setItemClassName + ']');
				        }
			        }
			        else {
						logMessage(className, methodName, header + " item = [null]");
			        }
		        }	// for
			}
			else if (set == null) {
				logMessage(className, methodName, header + " set is null");
			}
			else {	// set.isEmpty() == true
				logMessage(className, methodName, header + " set is empty");
			}
        }
		catch (Exception e) {
			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".logSetDetails; "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
		}
	}
    
    
    
    /**
     * This is a convenience method which will return the toString of a Collection object if it does not exceed
     * the size limit specified.  If it does exceed the limit, then only the size and hashcode is returned.
     * 
     * @param coll Object in the Collection class such as AbstractCollection, AbstractList, AbstractSet, 
     * ArrayList, BeanContextServicesSupport, BeanContextSupport, HashSet, LinkedHashSet, LinkedList, 
     * TreeSet, Vector 
     * @param sizeLimit If sizeLimit is <= 0 or null, there is no limit.
     * @return Collection.toString() if it does not exceed sizeLimit.
     */
    public static String collectionToString(Collection coll, int sizeLimit) {
        String aString = null;
        if (coll == null) {
            aString = "null";
        }
        else if (sizeLimit <= 0 || coll.size() < sizeLimit) {   
            // implied arrayList != null
            aString = coll.toString();
        }
        else {
            // implied arrayList != null && (sizeLimit != null || sizeLimit.intValue() > 0)
            aString = "Actual value not logged to improve performance.  size() = [" + coll.size() + "]; hashcode() = [" + coll.hashCode() + ']';
        }
        
        return aString;
    }


    
    /**
     * This is a convenience method which will return the toString of a Map object if it does not exceed
     * the size limit specified.  If it does exceed the limit, then only the size and hashcode is returned.
     * 
     * @param map Any object that implements the Map interface which includes AbstractMap, Attributes, HashMap, Hashtable, 
     * IdentityHashMap, RenderingHints, TreeMap, WeakHashMap  
     * @param sizeLimit If sizeLimit is <= 0 or null, there is no limit.
     * @return Map.toString() if it does not exceed sizeLimit.
     */
    public static String mapToString(Map map, int sizeLimit) {
        String aString = null;
        if (map == null) {
            aString = "null";
        }
        else if (sizeLimit <= 0 || map.size() < sizeLimit) {   
            // implied arrayList != null
            aString = map.toString();
        }
        else {
            // implied arrayList != null && (sizeLimit != null || sizeLimit.intValue() > 0)
            aString = "Actual value not logged to improve performance.  size() = [" + map.size() + "]; hashcode() = [" + map.hashCode() + ']';
        }
        
        return aString;
    }
    
    
    
    /**
     * This is a convenience method which will return the toString of a Map object if it does not exceed
     * the size limit specified in the VALIDATION_LOGGING_SIZE_LIMIT parameter which is defined in the 
     * TiredexUtilsBundle.properties file.  If it does exceed the limit, then only the size and hashcode 
     * is returned.
     * 
     * @param map Any object that implements the Map interface which includes AbstractMap, Attributes, HashMap, Hashtable, 
     * IdentityHashMap, RenderingHints, TreeMap, WeakHashMap.  
     * @return Map.toString() if it does not exceed VALIDATION_LOGGING_SIZE_LIMIT defined in the 
     * TiredexUtilsBundle.properties file.
     */
    public static String mapToString(Map map) {
        String aString = null;
		int size = 10;
		String sizeStr = null;
		try {
			sizeStr = TiredexUtils.getCommonFnBdlProperty("VALIDATION_LOGGING_SIZE_LIMIT");
	    	if (sizeStr != null && !sizeStr.trim().equalsIgnoreCase("")) {
	    		size = (new Integer(sizeStr)).intValue();
	    	}
		}
		catch (Exception e) {
		}

		aString = mapToString(map, size);

    	return aString;
    }
    

    
    
    /**
     * This is a convenience method which returns true if the IS_FORMATTING_COLLECTION_OBJECT flag is set
     * to true in the TiredexUtilsBundle.properties file.
     * 
     * @return true 
     */
    public static boolean isCollectionObjectLogFormatted() {
        boolean answer = false;
		String isFmtStr = null;
		try {
			isFmtStr = TiredexUtils.getCommonFnBdlProperty("IS_FORMATTING_COLLECTION_OBJECT");
	    	if (isFmtStr != null && !isFmtStr.trim().equalsIgnoreCase("")) {
	    		isFmtStr = isFmtStr.trim().toLowerCase();
	    		answer = isFmtStr.equalsIgnoreCase("true") || isFmtStr.equalsIgnoreCase("t");
	    	}
		}
		catch (Exception e) {
		}

    	return answer;
    }
    

    
    
    /**
     * This is a convenience method which will return the toString of an array of objects if it does not exceed
     * the size limit specified.  If it does exceed the limit, then only the size and hashcode is returned.
     * 
     * @param obj
     * @param sizeLimit
     * @return
     */
    public static String arrayToString(Object obj[], int sizeLimit) {
        StringBuffer buff = new StringBuffer();
        
        if (obj == null) {
            buff.append("null");
        }
        else if (sizeLimit <= 0 || obj.length < sizeLimit) {   
            // implied arrayList != null
            for (int j = 0; j < obj.length; j++) {
                buff.append('[');
                buff.append(j);
                buff.append("] = [");
                buff.append(obj[j].toString());
                buff.append(']');
                if (j < obj.length - 1) {
                    buff.append(',');
                    buff.append(' ');
                }
            }
        }
        else {
            // implied arrayList != null && (sizeLimit != null || sizeLimit.intValue() > 0)
            buff.append("Actual value not logged to improve performance.  length = [");
            buff.append(obj.length);
            buff.append("]; hashcode() = [");
            buff.append(obj.hashCode());
            buff.append(']');
        }
        
        return buff.toString();
    }
    
    
    
    /**
	 * Utility to log the contents of a java.util.ArrayList object in a more readable format.
     * @param className java.lang.String
     * @param methodName java.lang.String
     * @param header java.lang.String
     * @param arrayList java.util.List
	 */
	public static void logArrayListDetails(String className, String methodName, String header, List arrayList) {

		try {	
			if (arrayList != null && !arrayList.isEmpty()) {	
		
                logMessage(className, methodName, header + "arrayList class = [" + arrayList.getClass().getName() + ']');
                int arraySize = arrayList.size();
                logMessage(className, methodName, header + "arrayList.size() = [" + arraySize + ']');

		        for (int j = 0; j < arraySize; j++) {
			        Object value = arrayList.get(j);
			        String valueClassName = null;
			        String valueStr = null;
		
					String keyInfo = "index = [" + j + ']';
		
			        if (value != null) {
			        	valueClassName = value.getClass().getName();
			
						String valueInfo = " value class = [" + valueClassName + "] value(s):";

						if (valueClassName.equalsIgnoreCase("java.util.Map") || valueClassName.equalsIgnoreCase("java.util.HashMap")) {
							logMessage(className, methodName, header + keyInfo + valueInfo);
				        	logMapDetails(className, methodName, header + "    ", (Map)value);
				        }
				        else if (valueClassName.equalsIgnoreCase("java.util.ArrayList")) {
							logMessage(className, methodName, header + keyInfo + valueInfo);
				        	logArrayListDetails(className, methodName, header + "    ", (ArrayList)value);
				        }
				        else if (valueClassName.equalsIgnoreCase("java.util.Set") || valueClassName.equalsIgnoreCase("java.util.HashSet")) {
							logMessage(className, methodName, header + keyInfo + valueInfo);
				        	logSetDetails(className, methodName, header + "    ", (Set)value);
				        }
    			        else if (valueClassName.equalsIgnoreCase("java.util.Hashtable")) {
    						logMessage(className, methodName, header + keyInfo + valueInfo);
    			        	logHashtableDetails(className, methodName, header + "    ", (Hashtable)value);
    			        }
    			        else if (value.getClass().isArray()) {
    			        	logMessage(className, methodName, header + keyInfo + valueInfo);
    			        	logArrayDetails(className, methodName, header + "    ", (Object[]) value);
    			        }
				        else {
				        	valueStr = value.toString();
							logMessage(className, methodName, header + keyInfo + " value = [" + valueStr + "] value class = [" + valueClassName + ']');
				        }
			        }
			        else {
						logMessage(className, methodName, header + keyInfo + " value = [null]");
			        }
		        }	// for
			}
			else if (arrayList == null) {
				logMessage(className, methodName, header + " arrayList is null");
			}
			else {	// arrayList.isEmpty() == true
				logMessage(className, methodName, header + " arrayList is empty");
			}
		}
		catch (Exception e) {
			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".logArrayListDetails; "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
		}
		
	}
	/**
	 * Utility to log the contents of an array of objects in a more readable format.
     * @param className java.lang.String
     * @param methodName java.lang.String
     * @param header java.lang.String
     * @param array[] Object
	 */
	public static void logArrayDetails(String className, String methodName, String header, Object array[]) {

		try {
			if (array != null && array.length > 0 ) {	

			    logMessage(className, methodName, header + "array class = [" + array.getClass().getName() + ']');
                logMessage(className, methodName, header + "array.length = [" + array.length + ']');
		
		        for (int j = 0; j < array.length; j++) {
			        Object value = array[j];
			        String valueClassName = null;
			        String valueStr = null;
		
					String keyInfo = "index = [" + j + ']';
		
			        if (value != null) {
			        	valueClassName = value.getClass().getName();
			
						String valueInfo = " value class = [" + valueClassName + "] value(s):";

						if (valueClassName.equalsIgnoreCase("java.util.Map") || valueClassName.equalsIgnoreCase("java.util.HashMap")) {
							logMessage(className, methodName, header + keyInfo + valueInfo);
				        	logMapDetails(className, methodName, header + "    ", (Map)value);
				        }
				        else if (valueClassName.equalsIgnoreCase("java.util.ArrayList")) {
							logMessage(className, methodName, header + keyInfo + valueInfo);
				        	logArrayListDetails(className, methodName, header + "    ", (ArrayList)value);
				        }
				        else if (valueClassName.equalsIgnoreCase("java.util.Set") || valueClassName.equalsIgnoreCase("java.util.HashSet")) {
							logMessage(className, methodName, header + keyInfo + valueInfo);
				        	logSetDetails(className, methodName, header + "    ", (Set)value);
				        }
    			        else if (valueClassName.equalsIgnoreCase("java.util.Hashtable")) {
    						logMessage(className, methodName, header + keyInfo + valueInfo);
    			        	logHashtableDetails(className, methodName, header + "    ", (Hashtable)value);
    			        }
    			        else if (value.getClass().isArray()) {
    			        	logMessage(className, methodName, header + keyInfo + valueInfo);
    			        	logArrayDetails(className, methodName, header + "    ", (Object[]) value);
    			        }
				        else {
				        	valueStr = value.toString();
							logMessage(className, methodName, header + keyInfo + " value = [" + valueStr + "] value class = [" + valueClassName + ']');
				        }
			        }
			        else {
						logMessage(className, methodName, header + keyInfo + " value = [null]");
			        }
		        }	// for
			}
			else if (array == null) {
				logMessage(className, methodName, header + " array is null");
			}
			else {	// array.length == 0
				logMessage(className, methodName, header + " array is empty (length = 0)");
			}
		}
		catch (Exception e) {
			// we don't want exceptions within our debug code to propagate to the users
	    	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".logArrayDetails; "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
 	       	e.printStackTrace(System.err);
		}
		
	}


    /**
     * Utility to log the contents of a java.util.Map object in a more readable format.
     * @param className java.lang.String
     * @param methodName java.lang.String
     * @param header java.lang.String
     * @param map java.lang.Map
     */
    public static void logHashtableDetails(String className, String methodName, String header, Hashtable hashtable) {
    	
		try {
    		if (hashtable != null && !hashtable.isEmpty()) {	
                logMessage(className, methodName, header + "hashtable class = [" + hashtable.getClass().getName() + ']');
                logMessage(className, methodName, header + "hashtable.size() = [" + hashtable.size() + ']');

    	        Set keySet = hashtable.keySet();
    			Iterator it = keySet.iterator();
    	
    	        for ( ; it.hasNext(); ) {
    		        Object key = it.next();
    		        Object value = hashtable.get(key);
    		        String valueStr = null;
    		        String valueClassName = null;
    		        
    				String keyInfo = "key = [" + key.toString() + "] class = [" + key.getClass().getName() + ']';
    	
    		        if (value != null) {
    		        	valueClassName = value.getClass().getName();
    		
						String valueInfo = " value class = [" + valueClassName + "] value(s):";

						if (valueClassName.equalsIgnoreCase("java.util.Map") || valueClassName.equalsIgnoreCase("java.util.HashMap")) {
    						logMessage(className, methodName, header + keyInfo + valueInfo);
    			        	logMapDetails(className, methodName, header + "    ", (Map)value);
    			        }
    			        else if (valueClassName.equalsIgnoreCase("java.util.ArrayList")) {
    						logMessage(className, methodName, header + keyInfo + valueInfo);
    			        	logArrayListDetails(className, methodName, header + "    ", (ArrayList)value);
    			        }
    			        else if (valueClassName.equalsIgnoreCase("java.util.Set") || valueClassName.equalsIgnoreCase("java.util.HashSet")) {
    						logMessage(className, methodName, header + keyInfo + valueInfo);
    			        	logSetDetails(className, methodName, header + "    ", (Set)value);
    			        }
    			        else if (valueClassName.equalsIgnoreCase("java.util.Hashtable")) {
    						logMessage(className, methodName, header + keyInfo + valueInfo);
    			        	logHashtableDetails(className, methodName, header + "    ", (Hashtable)value);
    			        }
    			        else if (value.getClass().isArray()) {
    			        	logMessage(className, methodName, header + keyInfo + valueInfo);
    			        	logArrayDetails(className, methodName, header + "    ", (Object[]) value);
    			        }
    			        else {
    			        	valueStr = value.toString();
    						logMessage(className, methodName, header + keyInfo + "; value = [" + valueStr + "] class = [" + valueClassName + ']');
    			        }
    		        }
    		        else {
    					logMessage(className, methodName, header + keyInfo + " value = [null]");
    		        }
    	        }	// for
    		}
    		else if (hashtable == null) {
    			logMessage(className, methodName, header + " hashtable is null");
    		}
    		else {	// hashtable() == true
    			logMessage(className, methodName, header + " hashtable is empty");
    		}
        }
    	catch (Exception e) {
    		// we don't want exceptions within our debug code to propagate to the users
        	String msg = TiredexUtils.getTimeStamp() 
            		+ ">  "
            		+ Thread.currentThread().getName()
            		+ ">  "
                	+ CLASS_NAME
                    + ".logHashtableDetails; "
                    + "Exception caught but NOT re-raised; check stderr file for stack trace info; "
                    + e
                    + ']';
          	System.out.println(msg);
          	System.err.println(msg);
           	e.printStackTrace(System.err);
    	}
    }
}