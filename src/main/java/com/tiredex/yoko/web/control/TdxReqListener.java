package com.tiredex.yoko.web.control;

/*
 * $History: TdxReqListener.java $
 * 
 * *****************  Version 1  *****************
 * User: Alaing       Date: 7/20/12    Time: 2:00p
 * Created in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/web/control
 * Class is activated when a new HTTP Request is arrives.
 * 
 * 
 * 
 */

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import com.tiredex.yoko.utils.TiredexLogger;

/**
 * Application Lifecycle Listener implementation class TdxReqListener
 *
 */
public class TdxReqListener implements ServletRequestListener {
	
	private final static String CLASS_NAME = "TdxReqListener";
	
    private final static String CONFIG_MGMT_VERSION = "$Revision: 1 $";

    
    
    /**
     * Default constructor. 
     */
    public TdxReqListener() {
		super();
		final String METHOD_NAME = "TdxReqListener";
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "instatiated.  CONFIG_MGMT_VERSION = [" + CONFIG_MGMT_VERSION + ']');
    }

    
    
	/**
     * @see ServletRequestListener#requestDestroyed(ServletRequestEvent)
     */
    public void requestDestroyed(ServletRequestEvent sre) {
		final String METHOD_NAME = "requestDestroyed";
		
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "- - - - - - - - - - - - - - - - - - - - - - - - - ");
        
        int id = sre.getServletRequest().hashCode();
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "ServletRequestEvent hashCode [" + id + "] destroyed.");
    }

    
    
	/**
     * @see ServletRequestListener#requestInitialized(ServletRequestEvent)
     */
    public void requestInitialized(ServletRequestEvent sre) {
		final String METHOD_NAME = "requestInitialized";
		
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "+ + + + + + + + + + + + + + + + + + + + + + + + +");

        int id = sre.getServletRequest().hashCode();
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "ServletRequestEvent hashCode [" + id + "] created...");
    }
	
}
