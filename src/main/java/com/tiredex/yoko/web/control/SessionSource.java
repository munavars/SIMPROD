package com.tiredex.yoko.web.control;

/*
 * $History: SessionSource.java $
 * 
 * *****************  Version 1  *****************
 * User: Alaing       Date: 7/27/12    Time: 2:04p
 * Created in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/web/control
 * SSR 3432: class to support to SSO functionality.
 * 
 * 
 */

/**
 * This is an Enumeration type that must be one of the following:
 *	Website,
 *	DealerWebService,
 *	SSOWebService,
 *  Other
 * 
 * @author AlainG
 *
 */
public enum SessionSource {
	Website,
	DealerWebService,
	SSOWebService,
	Other

}
