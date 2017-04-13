package com.tiredex.yoko.web.control;

/*****************************************************************************
Program Name		: RequestToEventTranslator.java
Program Description	: See JavaDoc Comments

Program History		:
$History: RequestToEventTranslator.java $
 * 
 * *****************  Version 7  *****************
 * User: Alaing       Date: 4/26/07    Time: 4:07p
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/web/control
 * SSR 2933: change logging

Programmer		Date		Description
Niranjan Soni		10-01-01		NEW
*****************************************************************************/

import javax.servlet.http.HttpServletRequest;

import com.tiredex.yoko.utils.TiredexLogger;
import com.tiredex.yoko.utils.TiredexUtils;
import com.tiredex.yoko.web.event.TiredexEvent;

/* This component takes the Client Request from the browser and identifies the business event associated
 * with the client request. All the business events in the Tiredex Application are returned from this component
 * as their parent event which is TiredexEvent.
 * @author Niranjan Soni
 */

public class RequestToEventTranslator {

    private static final String CLASS_NAME = "RequestToEventTranslator";

    public RequestToEventTranslator() {
    }
    /* This method takes out the business event from the HTTP request from the Client browser. The identified
     * business event is then returned as its parent which is TiredexEvent.
     * @author Niranjan Soni
     * @param HttpServletRequest req
     * @return void
     * @exception Exception
     */
    public TiredexEvent processRequest(HttpServletRequest req) throws Exception {
        final String METHOD_NAME = "processRequest";

        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "      req = [" + req + "]");

        String selectedURL = req.getPathInfo();
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "request.getPathInfo = [" + selectedURL + ']');

        if (selectedURL.startsWith("/Ie") || selectedURL.startsWith("/Netscape")) {
            if (selectedURL.startsWith("/Ie")) {
                selectedURL = selectedURL.substring(3);
            }

            if (selectedURL.startsWith("/Netscape")) {
                selectedURL = selectedURL.substring(9);
            }

        }

        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "selectedURL = [" + selectedURL +']');
        TiredexEvent event = TiredexUtils.getEvent(selectedURL);

        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "req.mode = " + req.getParameter("mode"));

        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    returning event = [" + event + ']');

        return event;
    }
}