/*
 * $History: TiredexUtils.java $
 * 
 * *****************  Version 51  *****************
 * User: Alaing       Date: 8/29/14    Time: 1:56p
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * Added to support new Lynk Delivery Receipt program
 * 
 * *****************  Version 50  *****************
 * User: Alaing       Date: 1/29/14    Time: 11:04a
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * Add support to eliminate DR credit approval process.
 * 
 * *****************  Version 49  *****************
 * User: Alaing       Date: 7/11/13    Time: 3:33p
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * SSR 3509: add method to remove some punctuation characters from a
 * string.
 * 
 * *****************  Version 48  *****************
 * User: Alaing       Date: 4/26/13    Time: 5:25p
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * SSR 3382: bug fix: if the same DOT Serial Number is provided, sum the
 * quantities and enter a single DOT with the sum in the database.
 * 
 * *****************  Version 47  *****************
 * User: Alaing       Date: 8/27/12    Time: 12:10p
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * Make performance enhancement recommended by Java 6 compiler.
 * 
 * *****************  Version 46  *****************
 * User: Alaing       Date: 5/31/12    Time: 4:32p
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * SSR 3432: changes to support new SSO functionality
 * 
 * *****************  Version 45  *****************
 * User: Alaing       Date: 3/19/12    Time: 6:07p
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * SSR 3382: updates to support the new Tire Registration process.
 * 
 * *****************  Version 44  *****************
 * User: Alaing       Date: 7/18/11    Time: 3:25p
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * WO 24250: bug in processing Customer Part Numbers if there are more
 * than 10 specified.
 * 
 * *****************  Version 43  *****************
 * User: Alaing       Date: 7/08/11    Time: 11:47a
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * SSR 3324: fix look-and feel
 * 
 * *****************  Version 42  *****************
 * User: Keic         Date: 12/15/10   Time: 2:00p
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * SSR3289 - new and modified functions to support add row function via
 * ajax.
 * 
 * *****************  Version 41  *****************
 * User: Alaing       Date: 4/08/10    Time: 3:50p
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * WO 20617: Optimize logging.
 * 
 * *****************  Version 40  *****************
 * User: Alaing       Date: 3/15/10    Time: 11:02a
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * WO 20600 - Change logging so that it does not interfere with concurrent
 * threads.  Remove synchorization where it is not needed.  Improve
 * logging performance.
 * 
 * *****************  Version 39  *****************
 * User: Alaing       Date: 8/27/09    Time: 12:17p
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * SSR 3088: add flag to enable or disable the new A/R Documents operation
 * at runtime.
 * 
 * *****************  Version 38  *****************
 * User: Alaing       Date: 5/27/09    Time: 12:05p
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * Fix formatting.
 * 
 * *****************  Version 37  *****************
 * User: Alaing       Date: 4/22/09    Time: 4:23p
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * SSR 2828: bug fix in phone number validation.
 * 
 * *****************  Version 36  *****************
 * User: Alaing       Date: 12/02/08   Time: 1:11p
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * SSR 3004: add logging for debugging purposes.
 * 
 * *****************  Version 35  *****************
 * User: Alaing       Date: 11/10/08   Time: 1:33p
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * SSR 3004: bug fix, error splitting list when list falls on specific
 * interval.
 * 
 * *****************  Version 34  *****************
 * User: Alaing       Date: 10/24/08   Time: 11:29a
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * SSR 3004: add SourceSafe commands.
 * 
 */

package com.tiredex.yoko.utils;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import javax.mail.Address;
//import javax.mail.Message;
//import javax.mail.Message.RecipientType;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileUploadBase.InvalidContentTypeException;

import com.tiredex.yoko.exceptions.TiredexAppException;
import com.tiredex.yoko.exceptions.TiredexUnhandledException;
import com.tiredex.yoko.web.event.TiredexEvent;

public class TiredexUtils implements Serializable
{
	private static final String CLASS_NAME = TiredexUtils.class.getSimpleName();
	
    private static final String CONFIG_MGMT_VERSION = "$Revision: 51 $";
    // use SourceSafe revision number for the serialVersionUID
    private static final int firstSpace = CONFIG_MGMT_VERSION.indexOf(' ');
    private static final int lastSpace = CONFIG_MGMT_VERSION.lastIndexOf(' ');
    static final long serialVersionUID = Integer.parseInt(CONFIG_MGMT_VERSION.substring(firstSpace + 1, lastSpace));

	private static DataSource dlrWebDataSrv = null;
	private static String userName = "";
	private static String password = "";
	private static String dlrWebDataSrcName = "";
	private static String initialContextFactory = "";
	public static final String TIREDEX_UTILS_BUNDLE = "com.tiredex.yoko.utils.TiredexUtilsBundle";
	private static final String SCREEN_NAMES_BUNDLE = "com.tiredex.yoko.utils.ScreenNamesBundle";
	private static final String REQUEST_TO_EVENT_BUNDLE = "com.tiredex.yoko.utils.RequestToEventBundle";
	private static final String NEXT_PREVIOUS_BUNDLE = "com.tiredex.yoko.utils.NextPrevBundle";
//	private static final String USER_ACCESS_LEVEL_TO_FUNCTIONALITY_BUNDLE="com.tiredex.yoko.utils.UserAccessLevelToFunctionalityBundle";	// SSR 2945: no longer used
//	private static final String EMAIL_SENDER_DETAILS_BUNDLE = "com.tiredex.yoko.utils.EmailSenderDetailBundle";
	
	private static DataSource tireRegDataSrc = null;
	private static String tireRegUserName = "";
	private static String tireRegPassword = "";
	private static String tireRegDataSrcName = "";
//	private static final String TIREDEX_UTILS_BUNDLE = "com.tiredex.yoko.utils.TireRegUtilsBundle";

	private static DataSource uniMirrorDataSrc = null;
	private static String uniMirrorUserName = "";
	private static String uniMirrorPassword = "";
	private static String uniMirrorDataSrcName = "";

    private static DataSource uniArchiveDataSrc = null;
    private static String uniArchiveUserName = "";
    private static String uniArchivePassword = "";
    private static String uniArchiveDataSrcName = "";

    /*
	 *  YODA is on SQL Server 2000 and we must use an older JDBC 3.0 driver from Microsoft
	 *  configure jdbc to use server: casql03\busdiv, port: 1518, jar file: sqljdbc4.jar
	 */
	private static DataSource yodaDataSrc = null;
	private static String yodaUserName = "";
	private static String yodaPassword = "";
	private static String yodaDataSrcName = "";

	private static DataSource biDataSrc = null;
	private static String biUserName = "";
	private static String biPassword = "";
	private static String biDataSrcName = "";

	//	private static int hrNow = -1;	// declared here for optimization
	private static int minNow = -1;
	private static int secNow = -1;
//	private static int dayNow = -1;
//	private static int lastHr = -1;
	private static int lastMin = -1;
	private static int lastSec = -1;
//	private static int lastDay = -1;

													// index:    0123456789-12345678
//	private static StringBuffer timeStampStr = new StringBuffer("yyyy-mm-dd hh:mm:ss");
	private static String timeStamp = null;

	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static NumberFormat numFormatter = NumberFormat.getCurrencyInstance(Locale.US);
	
//	private static final int YR_POS = 0;	// save starting indices for each piece of the date and time
//	private static final int MON_POS = 5;
//	private static final int DAY_POS = 8;
//	private static final int HR_POS = 11;
//	private static final int MIN_POS = 14;
//	private static final int SEC_POS = 17;

//	private static final int YR_END = YR_POS + 4;	// save ending indices
//	private static final int MON_END = MON_POS + 2;
//	private static final int DAY_END = DAY_POS + 2;
//	private static final int HR_END = HR_POS + 2;
//	private static final int MIN_END = MIN_POS + 2;
//	private static final int SEC_END = SEC_POS + 2;
	
	// declare these variables to save time at runtime
//	private static StringBuffer hrStr = new StringBuffer("hh");
//	private static StringBuffer minStr = new StringBuffer("mm");
//	private static StringBuffer secStr = new StringBuffer("ss");
//	private static StringBuffer monStr = new StringBuffer("hh");
//	private static StringBuffer dayStr = new StringBuffer("dd");
//	private static StringBuffer yearStr = new StringBuffer("yyyy");
	
	
	
	public static final String AR_DOC_PROP_KEY = "ENABLE_DI_AR_OPERATION";

	public static final String APP_URL_PROP_KEY = "APP_URL";
	public static final String APP_ENVIRONMENT_PROP_KEY = "APP_ENVIRONMENT";

	public static final String LYNK_PROGRAM_FLAG_PROP_KEY = "ENABLE_USE_LYNK_PROGRAM_FLAG";
	
	public static final String ORDER_ANALYSIS_FLAG_PROP_KEY = "ENABLE_ORDER_ANALYSIS_FLAG";
	
	public static final String ENABLE_WS_DR_OPERATION_PROP_KEY = "ENABLE_WS_DR_OPERATION";
	
	/**
	 * CUST_LOGO_MAX_HEIGHT_PROP_KEY is in px (pixels). If no value provided, then there is no max.
	 */
	public static final String CUST_LOGO_MAX_HEIGHT_PROP_KEY = "CUST_LOGO_MAX_HEIGHT";
	/**
	 * CUST_LOGO_MAX_WIDTH_PROP_KEY is in px (pixels). If no value provided, then there is no max.
	 */
	public static final String CUST_LOGO_MAX_WIDTH_PROP_KEY = "CUST_LOGO_MAX_WIDTH";
	public static final String CUST_LOGO_UPLOAD_DIR_PROP_KEY = "CUST_LOGO_UPLOAD_DIR";
	
	public static final String CUST_DR_UPLOAD_DIR_PROP_KEY = "CUST_DR_UPLOAD_DIR";
	
	public static final String CUST_RAW_UPLOAD_DIR_PROP_KEY = "RAW_UPLOAD_DIR";

	public static final String VALID_DR_IMAGE_FILE_TYPES_PROP_KEY = "VALID_DR_IMAGE_FILE_TYPES";

	
				
public TiredexUtils()
{}



/**
 * Remove ? * %
 * 
 * @param valueStr
 * @return
 */
public static String removePunc(String valueStr) {
	final String METHOD_NAME = "removePunc";
	
	final String regEx = "[?*%]";

	String outStr = valueStr;
	if (valueStr != null) {
        Pattern pat = Pattern.compile(regEx);
        Matcher match = pat.matcher(valueStr);
        if (match.find()) {
            outStr = match.replaceAll(" ");
    		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "valueStr before = [" + valueStr + "]; after = [" + outStr + ']');
        }
	}

	return outStr;

}



/**
 * This method assigns address2 field the conacatenated value of city,state and zip if address2 is blank
 * @author Hemant Gaur
 * @param AddressView addressView
 * @return AddressView
 */
public static com.tiredex.yoko.utils.AddressView formatAddress(AddressView addressView) 
{
//  ***	String address = "";

	if(addressView.address2==null)
		addressView.address2 = "";

	if(addressView.city==null)
		addressView.city = "";

	if(addressView.state==null)
		addressView.state = "";

	if(addressView.zipCode==null)
		addressView.zipCode = "";
	else
		addressView.zipCode = formatZip(addressView.zipCode);
		
//***	if(!addressView.city.equals(""))
//***		address = addressView.city+", ";

//***	if(!addressView.state.equals(""))
//***		address = address + addressView.state + " ";
//***	address = address + formatZip(addressView.zipCode);

//***	if(addressView.address2.trim().equals(""))
//***	{
//***		addressView.address2 = address;
//***		addressView.city = "";
//***	}
//***	else
//***		addressView.city = address;
	
//***	addressView.state = "";
//***	addressView.zipCode = "";
		
	return addressView;
}
/**
 * This method adds hyphen(-) in phone number. Here all telephone numbers in database are required to be
 * of 10 digits. So the format in which phone numbers are displayed to administrator is "FFF-FFF-FFFF".
 * 
 * @author Prashant Jain
 * @return the formatted phone number as a String in xxx-yyy-zzzz format
 * @param phoneNumber java.lang.String
 */
public static String formatPhoneNumber(String phoneNumber)
{
	String formattedPhoneNumber = "";
    
    if (phoneNumber != null) {
        int lengthOfPhoneNumber = phoneNumber.length();

        if (lengthOfPhoneNumber > 7) {  
            formattedPhoneNumber = phoneNumber.substring(0, 3) + '-' + phoneNumber.substring(3, 6) + '-' + phoneNumber.substring(6, lengthOfPhoneNumber);
        }
        else if (lengthOfPhoneNumber > 3) {
            formattedPhoneNumber = phoneNumber.substring(0, 3) + '-' + phoneNumber.substring(3,lengthOfPhoneNumber);
        }
        else {
            formattedPhoneNumber = phoneNumber;
        }
    } // if phoneNumber != null
		
	return formattedPhoneNumber;
}

/**
 * This convenience method removes all characters that are not digits from the phone number.  A stripped
 * phone number is usually what is stored in the database.  The format can be:
 * NNN-NNN-NNNN or (NNN) NNN-NNNN or NNN/NNN-NNNN.  An optional extension in xNNNN format is also accepted 
 * when it is appended to the number such as NNN-NNN-NNNN xNNNN.  A leading "1" such as 1-NNN-NNN-NNNN is 
 * valid but is removed.
 *  
 * @param phoneNumber
 * @return a String stripped of punctuation and spaces.  If phoneNumber is null, then this method returns null. 
 * Otherwise, this method returns a phone number in NNNNNNNNNNxNNNN format.  The xNNNN is optional. 
 */
public static String stripPhoneNumber(String phoneNumber) {
    final String METHOD_NAME = "stripPhoneNumber";

    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method.");
    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    argument: phoneNumber = [" + phoneNumber + ']');

    String result = null;
    
    if (phoneNumber != null) {
        /* standardize phone number to 9 digits + "x" + (n)digits; remove any punctuation like
         * "( )", space, "-", or "/" commonly used to make phone number more readable. 
         */
        String regEx = "[ ()-/]";   // remove these chars from phone number.  See Javadoc for Pattern class
        String phone = phoneNumber.toString().trim(); 
        result = phone.replaceAll(regEx, "");
    }

    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");
    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    returning result = [" + result + ']');

    return result;
}



/**
 * This is a convenience method to check if the phone number is in a valid format.  The format can be:
 * NNN-NNN-NNNN or (NNN) NNN-NNNN or NNN/NNN-NNNN.  An optional extension in xNNNN format is also accepted 
 * when it is appended to the number such as NNN-NNN-NNNN xNNNN.  A leading "1" such as 1-NNN-NNN-NNNN is 
 * valid.
 * 
 * @param phoneNumber
 * @return TRUE if phone number is valid
 */
public static boolean isPhoneValid (String phoneNumber) {
    final String METHOD_NAME = "isPhoneValid";

    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method.");
    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    argument: phoneNumber = [" + phoneNumber + ']');

    boolean isOk = false;
    
    if (phoneNumber != null) {
        String phone = stripPhoneNumber(phoneNumber);
        
        if (!phone.equalsIgnoreCase("")) {
            // check for 9 digits and an optional extension which has "x" + zero or more digits
            Pattern p1 = Pattern.compile("^\\d{10}$");
            Matcher m = p1.matcher(phone);
            if (m.matches()) {
                isOk = true;
                TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "phone matched pattern1 [" + p1.pattern() + ']');
            }
            else {
                TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "phone failed to match pattern1 [" + p1.pattern() + ']');
                Pattern p2 = Pattern.compile("^\\d{10}(x|X)\\d*$");
                Matcher m2 = p2.matcher(phone);
                if (m2.matches()) {
                    isOk = true;
                    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "phone matched pattern2 [" + p2.pattern() + ']');
                }
                else {
                    isOk = false;
                    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "phone failed to match pattern2 [" + p2.pattern() + ']');
                }
            } // else
        } // if !phone.equalsIgnoreCase("")
        else {
            isOk = false;
        }
    }
    else {
        isOk = false;
    }
    
    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");
    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    returning isOk = [" + isOk + ']');

    return isOk;

}
/**
 * This is a convenience method to check if the email is in a valid format.  The format can be:
 * myname@example.com or my.name@example.com or myName@example.com
 * 
 * @param phoneNumber
 * @return TRUE if phone number is valid
 */
public static boolean isEmailValid (String email) {
    final String METHOD_NAME = "isEmailValid";

    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method.");
    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    argument: email = [" + email + ']');

    boolean isOk = false;
    
    if (email != null) {
        
        Pattern p1 = Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b");
        Matcher m = p1.matcher(email.trim().toUpperCase());
        if (m.matches()) {
            isOk = true;
            TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "email matched pattern [" + p1.pattern() + ']');
        }
        else {
            isOk = false;
            TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "phone failed to match pattern [" + p1.pattern() + ']');
        }
    } // if email != null
    else {
        isOk = false;
    }
    
    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");
    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    returning isOk = [" + isOk + ']');

    return isOk;

}

    

/**
 * This method adds hyphen(-) in postal zip code separating 5-digit zip code and zip+4 code.
 * @author Alain Graziani
 * @return java.lang.String
 * @param zip java.lang.String
 */
public static String formatZip(String zip)
{
	String formattedZip = "";

	int lengthOfZip = zip.length();

	if(lengthOfZip>5)
		formattedZip = zip.substring(0,5) + '-' + zip.substring(5,lengthOfZip);
	else
		formattedZip = zip;
		
	return formattedZip;
}
// SSR 2945: Access Level is no longer used
//public static String getAccessLevelFunction(String userAccessLevel) throws TiredexUnhandledException
//{
//	final String METHOD_NAME = "getAccessLevelFunction";
//	
//	String accessLevelFunctionality="";
//
//	try
//	{
//		PropertyResourceBundle configBundle = (PropertyResourceBundle)PropertyResourceBundle.getBundle(USER_ACCESS_LEVEL_TO_FUNCTIONALITY_BUNDLE);
//		accessLevelFunctionality = configBundle.getString(userAccessLevel);
//		return accessLevelFunctionality;
//	}
//	catch(Exception e)
//	{
//		String msg = "Error in reading the " + userAccessLevel + " parameter from the " + USER_ACCESS_LEVEL_TO_FUNCTIONALITY_BUNDLE + " property file : " + e;
//		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, msg);
//		throw new TiredexUnhandledException(msg);
//	}
//}



/**
 * @param applicationName
 * @return
 * @throws TiredexUnhandledException
 */
public static String getApplicationDocPath(String applicationName) throws TiredexUnhandledException
{
	final String METHOD_NAME = "getApplicationDocPath";
	String applicationDocPath="";

	try
	{
		PropertyResourceBundle configBundle = (PropertyResourceBundle)PropertyResourceBundle.getBundle(TIREDEX_UTILS_BUNDLE);
		applicationDocPath = configBundle.getString(applicationName);
		return applicationDocPath;
	}
	catch(Exception e)
	{
		String msg = "Exception while reading the " + applicationName + " parameter from the " + TIREDEX_UTILS_BUNDLE + " property file : " + e;
		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, msg);
		throw new TiredexUnhandledException(msg);
	}
}



/**
 * @param applicationName
 * @return
 * @throws TiredexUnhandledException
 */
public static String getApplicationWebPath(String applicationName) throws TiredexUnhandledException
{
	final String METHOD_NAME = "getApplicationWebPath";
	String applicationWebPath="";

	try
	{
		PropertyResourceBundle configBundle = (PropertyResourceBundle)PropertyResourceBundle.getBundle(TIREDEX_UTILS_BUNDLE);
		applicationWebPath = configBundle.getString(applicationName);
		return applicationWebPath;
	}
	catch(Exception e)
	{
		String msg = "Exception while reading the " + applicationName + " parameter from the " + TIREDEX_UTILS_BUNDLE + " property file : " + e;
		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, msg);
		throw new TiredexUnhandledException(msg);
	}
}



public static String getApplicationUrl() throws TiredexUnhandledException
{
	final String METHOD_NAME = "getApplicationUrl";
	String appUrl = "";

	try {
		appUrl = getCommonFnBdlProperty(APP_URL_PROP_KEY);
	}
	catch(Exception e) {
		String msg = "Exception while reading the " + APP_URL_PROP_KEY + " parameter from the " + TIREDEX_UTILS_BUNDLE + " property file : " + e;
		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, msg);
		throw new TiredexUnhandledException(msg);
	}
	
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "appUrl = [" + appUrl + ']');
	return appUrl;
}



/**
 * @return true if ENABLE_USE_DB_CREDIT_FLAG in TiredexUtilsBundle.property file is set to "true"
 */
public static boolean isUsingDbCredit() /*throws TiredexUnhandledException*/
{
	final String METHOD_NAME = "isUsingDbCredit";
	final String USE_DB_CREDIT_PROP_KEY = "ENABLE_USE_DB_CREDIT_FLAG";
	String dbCreditPropValue = null;
	boolean isEnabled = false;

	try
	{
		dbCreditPropValue = getCommonFnBdlProperty(USE_DB_CREDIT_PROP_KEY);
		
		isEnabled = dbCreditPropValue != null && (
				dbCreditPropValue.equalsIgnoreCase("true") || 
				dbCreditPropValue.equalsIgnoreCase("t") || 
				dbCreditPropValue.equalsIgnoreCase("yes") ||
				dbCreditPropValue.equalsIgnoreCase("y"));
	}
	catch(Exception e)
	{
		String msg = "Exception while reading the " + USE_DB_CREDIT_PROP_KEY + " parameter from the " + TIREDEX_UTILS_BUNDLE + " property file : " + e + ". Defaulting to FALSE.";
		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, msg);
		isEnabled = false;
	}
	
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "isEnabled = [" + isEnabled + ']');
	
	return isEnabled;
}

/**
 * @return TRUE if this app will get credit authorization for National Account
 * in real-time during DR processing.  If this is FALSE, then the National Account's
 * eligibility is determined by a field in the database.
 */
public static boolean isWillGetCreditAuth() {
	return !isUsingDbCredit();
}


/**
 * @return
 * @throws TiredexUnhandledException
 */
public static boolean isARDocsEnabled() throws TiredexUnhandledException
{
	final String METHOD_NAME = "isARDocsEnabled";
	String arDocPropValue = "";
	boolean isEnabled = false;

	try {
		arDocPropValue = getCommonFnBdlProperty(AR_DOC_PROP_KEY);
		
		isEnabled = arDocPropValue != null && (
				arDocPropValue.equalsIgnoreCase("true") || 
				arDocPropValue.equalsIgnoreCase("t") || 
				arDocPropValue.equalsIgnoreCase("yes") ||
				arDocPropValue.equalsIgnoreCase("y"));
	}
	catch(Exception e)
	{
		String msg = "Exception while reading the " + AR_DOC_PROP_KEY + " parameter from the " + TIREDEX_UTILS_BUNDLE + " property file : " + e;
		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, msg);
		isEnabled = false;
	}
	
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "isEnabled = [" + isEnabled + ']');
	
	return isEnabled;
}



/**
 * @return TRUE if the ENABLE_USE_LYNK_PROGRAM_FLAG property is set to TRUE in the com.tiredex.yoko.utils.TiredexUtilsBundle property file
 * @throws TiredexUnhandledException
 */
public static boolean isLynkProgramEnabled() throws TiredexUnhandledException
{
	final String METHOD_NAME = "isLynkProgramEnabled";
	String lynkPropValue = "";
	boolean isEnabled = false;
	
	try {
		lynkPropValue = getCommonFnBdlProperty(LYNK_PROGRAM_FLAG_PROP_KEY);
		
		isEnabled = lynkPropValue != null && (
				lynkPropValue.equalsIgnoreCase("true") || 
				lynkPropValue.equalsIgnoreCase("t") || 
				lynkPropValue.equalsIgnoreCase("yes") ||
				lynkPropValue.equalsIgnoreCase("y"));
	}
	catch(Exception e)
	{
		String msg = "Exception while reading the " + LYNK_PROGRAM_FLAG_PROP_KEY + " parameter from the " + TIREDEX_UTILS_BUNDLE + " property file : " + e;
		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, msg);
		isEnabled = false;
	}
	
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "isEnabled = [" + isEnabled + ']');
	
	return isEnabled;
}



public static boolean isOrderAnalysisEnabled() throws TiredexUnhandledException
{
	final String METHOD_NAME = "isOrderAnalysisEnabled";
	String oaPropValue = "";
	boolean isEnabled = false;
	
	try {
		oaPropValue = getCommonFnBdlProperty(ORDER_ANALYSIS_FLAG_PROP_KEY);
		
		isEnabled = oaPropValue != null && (
				oaPropValue.equalsIgnoreCase("true") || 
				oaPropValue.equalsIgnoreCase("t") || 
				oaPropValue.equalsIgnoreCase("yes") ||
				oaPropValue.equalsIgnoreCase("y"));
	}
	catch(Exception e)
	{
		String msg = "Exception while reading the " + ORDER_ANALYSIS_FLAG_PROP_KEY + " parameter from the " + TIREDEX_UTILS_BUNDLE + " property file : " + e;
		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, msg);
		isEnabled = false;
	}
	
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "isEnabled = [" + isEnabled + ']');
	
	return isEnabled;
}



/**
 * @return The value of the APP_ENVIRONMENT_PROP_KEY. This method will never return null; if
 * the APP_ENVIRONMENT_PROP_KEY is not defined, this method will return an empty String (""). 
 * 
 * @throws TiredexUnhandledException
 */
public static String getAppEnvironment() throws TiredexUnhandledException {
	final String METHOD_NAME = CLASS_NAME + ".getAppEnvironment>  ";
	
	PropertyResourceBundle configBundle = null;
	try {
		configBundle = (PropertyResourceBundle)PropertyResourceBundle.getBundle(TIREDEX_UTILS_BUNDLE);
	}
	catch(Exception e) {
		String msg = METHOD_NAME + "Error in opening " + TIREDEX_UTILS_BUNDLE + " property file : " + e;
		throw new TiredexUnhandledException(msg);
	}
	
	
	String envPrefix = null;
	try {
		envPrefix = configBundle.getString(APP_ENVIRONMENT_PROP_KEY); 
	}
	catch(Exception e) {
		envPrefix = "";
	}
	
	return envPrefix;
}



/**
 * @param key
 * @return
 * @throws TiredexUnhandledException
 */
public static String getCommonFnBdlProperty(String key) throws TiredexUnhandledException
{
	final String METHOD_NAME = CLASS_NAME + ".getCommonFnBdlProperty>  ";
	
	/*
	 * find out what environment the app is running; values should be "DEV", "TEST", "PROD", or nothing
	 */
	PropertyResourceBundle configBundle = null;
	try {
		configBundle = (PropertyResourceBundle)PropertyResourceBundle.getBundle(TIREDEX_UTILS_BUNDLE);
	}
	catch(Exception e) {
		String msg = METHOD_NAME + "Error in opening " + TIREDEX_UTILS_BUNDLE + " property file : " + e;
		throw new TiredexUnhandledException(msg);
	}
	
	
	
	/*
	 * Use the env as a prefix for the property key. If the key exists without a prefix, use that one; otherwise, try to find
	 * a key with the prefix.
	 */
	String value = null;
	try {
		value = configBundle.getString(key); 
		// TiredexLogger.logMessage below causes an infinite loop and eventually stack overflow
//		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, TIREDEX_UTILS_BUNDLE + " key [" + key + "] = [" + value + ']');
	}
	catch(Exception e) {
	}
	
	
	
	if (value == null || value.trim().equalsIgnoreCase("")) {
		// If the code reaches this point, the key was not found, so we'll try to find the key with the prefix.
		String envPrefix = getAppEnvironment();
		
		String envKey = envPrefix + '_' + key;
		try {
			value = configBundle.getString(envKey); 
		}
		catch (Exception e) {
			String msg = METHOD_NAME + "Error in reading the " + key + " and " + envKey + " parameter from the " + TIREDEX_UTILS_BUNDLE + " property file : " + e;
			throw new TiredexUnhandledException(msg);
		}
	}
	
	return value; 
}



public static Connection getConnection() throws TiredexUnhandledException
{
	final String METHOD_NAME = "getConnection";
	
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method.");
	
	try
	{
		InitialContext ic = getInitialContext();
		if(dlrWebDataSrv == null)
		{
			dlrWebDataSrv = (DataSource)ic.lookup(dlrWebDataSrcName);
		}
		Connection conn = dlrWebDataSrv.getConnection(userName,password);
		
		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method. Returning Connection = [" + conn + ']');
		return conn;
	}
	catch(NamingException e)
	{
		String msg = "NamingException occured while looking up the DataSource object : " + e;
		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, msg);
		throw new TiredexUnhandledException(msg);
	}
	catch(SQLException e)
	{
		String msg = "SQLException occured while taking Connection object : " + e;
		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, msg);
		throw new TiredexUnhandledException(msg);
	}
    catch (Exception e) {
        String msg = "Exception occured while getting Connection object.";
        TiredexLogger.logException(CLASS_NAME, METHOD_NAME, msg + ' ', e);
        throw new TiredexUnhandledException(msg);
    }
}



/**
 * YODA is on SQL Server 2000 and we must use an older JDBC 3.0 driver from Microsoft
 * configure jdbc to use server: casql03\busdiv, port: 1518, jar file: sqljdbc4.jar
 * 
 * @return database connection
 * @throws TiredexUnhandledException
 */
public static Connection getYodaConnection() throws TiredexUnhandledException
{
	final String METHOD_NAME = "getYodaConnection";
	
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method.");
	
	try
	{
		InitialContext ic = getInitialContext();
		if(yodaDataSrc == null)
		{
			yodaDataSrc = (DataSource)ic.lookup(yodaDataSrcName);
		}
		Connection conn = yodaDataSrc.getConnection(yodaUserName,yodaPassword);
		
		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method. Returning Connection = [" + conn + ']');
		return conn;
	}
	catch(NamingException e)
	{
		String msg = "NamingException occured while looking up the DataSource object : " + e;
		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, msg);
		throw new TiredexUnhandledException(msg);
	}
	catch(SQLException e)
	{
		String msg = "SQLException occured while taking Connection object : " + e;
		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, msg);
		throw new TiredexUnhandledException(msg);
	}
    catch (Exception e) {
        String msg = "Exception occured while getting Connection object.";
        TiredexLogger.logException(CLASS_NAME, METHOD_NAME, msg + ' ', e);
        throw new TiredexUnhandledException(msg);
    }
}



public static Connection getBIConnection() throws TiredexUnhandledException
{
	final String METHOD_NAME = "getBIConnection";
	
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method.");
	
	try
	{
		InitialContext ic = getInitialContext();
		if(biDataSrc == null)
		{
			biDataSrc = (DataSource)ic.lookup(biDataSrcName);
		}
		Connection conn = biDataSrc.getConnection(biUserName, biPassword);
		
		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method. Returning Connection = [" + conn + ']');
		return conn;
	}
	catch(NamingException e)
	{
		String msg = "NamingException occured while looking up the DataSource object : " + e;
		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, msg);
		throw new TiredexUnhandledException(msg);
	}
	catch(SQLException e)
	{
		String msg = "SQLException occured while taking Connection object : " + e;
		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, msg);
		throw new TiredexUnhandledException(msg);
	}
    catch (Exception e) {
        String msg = "Exception occured while getting Connection object.";
        TiredexLogger.logException(CLASS_NAME, METHOD_NAME, msg + ' ', e);
        throw new TiredexUnhandledException(msg);
    }
}



public static Connection getTireRegConnection() throws TiredexUnhandledException
{
	final String METHOD_NAME = "getTireRegConnection";
	
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method.");
	
	try
	{
		InitialContext ic = getInitialContext();
		if(tireRegDataSrc == null)
		{
			tireRegDataSrc = (DataSource)ic.lookup(tireRegDataSrcName);
		}
		Connection conn = tireRegDataSrc.getConnection(tireRegUserName, tireRegPassword);
		
		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method. Returning Tire Registration database Connection = [" + conn + ']');
		return conn;
	}
	catch(NamingException e)
	{
		String msg = "NamingException occured while looking up the DataSource object : " + e;
		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, msg);
		throw new TiredexUnhandledException(msg);
	}
	catch(SQLException e)
	{
		String msg = "SQLException occured while taking Connection object : " + e;
		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, msg);
		throw new TiredexUnhandledException(msg);
	}
    catch (Exception e) {
        String msg = "Exception occured while getting Connection object.";
        TiredexLogger.logException(CLASS_NAME, METHOD_NAME, msg + ' ', e);
        throw new TiredexUnhandledException(msg);
    }
}



public static TiredexEvent getEvent(String request) throws TiredexUnhandledException
{
	final String METHOD_NAME = "getEvent";
	try
	{
		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Searching REQUEST_TO_EVENT_BUNDLE for request = [" + request + ']');
		PropertyResourceBundle eventBundle = (PropertyResourceBundle)PropertyResourceBundle.getBundle(REQUEST_TO_EVENT_BUNDLE);
		String event = eventBundle.getString(request);
		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Request = [" + request + "]; Event = [" + event + ']');

		if (event != null && !event.equalsIgnoreCase("")) {
			Class cls = Class.forName(event);
			Object obj = cls.newInstance();
			return (TiredexEvent)obj;
		}
		else {
			return null;
		}
	}
	catch(Exception e)
	{
		String msg = "***** POSSIBLE SOFTWARE BUG EXCEPTION *****  Exception while reading the " + request + " parameter from the " + REQUEST_TO_EVENT_BUNDLE + " property file : " + e;
		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, msg);
		return null;
	}
}
/**
 * This function is created to support dynamically created 
 * order entry input fields.  The web site used to have 
 * 10 set (5 rows)of input fields but now it can have as many 
 * input fields on the same page as the user desired.
 * 
 * The function will loop through all the elements in HttpServletRequest
 * and count how many input text fields that starts with the name 
 * parameterStartsWith("partNumber").  
 * 
 * @param requestObject
 * @param parameterStartsWith
 * @return
 */

	public static int getParameterSize(HttpServletRequest requestObject, String parameterStartsWith) {

		final String METHOD_NAME = "getParameterSize";

		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method.");
	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    arguments:");
		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        parameterStartsWith = [" + parameterStartsWith + ']');
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        req = [" + requestObject + "]");
		
//		 paramName = "";
//		String indexString = "";
		int tmpIndex = 0;
		int counter = 0;

		boolean isParamFound = false;
		Enumeration paramNames = requestObject.getParameterNames();

		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Parameter ["	+ paramName + ']');
			if (((paramName.length() >= parameterStartsWith.length())
					&& (paramName.substring(0, parameterStartsWith.length()))
							.equals(parameterStartsWith)) && (paramName.length() > parameterStartsWith.length())) {
			    try
			    {
			       // attempt to convert the String to an int
			       String indexString = paramName.substring(parameterStartsWith.length());
			       if (indexString.length() > 0) {
						tmpIndex = Integer.parseInt(indexString);
						counter++;
						isParamFound = true;
					}
			    }
			    catch ( NumberFormatException e )
			    {
					TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "paramName ["
							+ paramName + "] is not a " + parameterStartsWith + " input field");
			    }				
				TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "counter = [" + counter + "] ");
			} // if
		} // while
		
		
		
		if (!isParamFound) {
			TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "parameterStartsWith = [" + parameterStartsWith + "] not found in list of request object parameters.  Searching in list of request object attributes...");
			Enumeration attrNames = requestObject.getAttributeNames();
			
			while (attrNames.hasMoreElements()) {
				String attrName = (String) attrNames.nextElement();
				TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Attribute ["	+ attrName + ']');
				if (((attrName.length() >= parameterStartsWith.length())
						&& (attrName.substring(0, parameterStartsWith.length()))
								.equals(parameterStartsWith)) && (attrName.length() > parameterStartsWith.length())) {
				    try
				    {
				       // attempt to convert the String to an int
				    	String indexString = attrName.substring(parameterStartsWith.length());
				       if (indexString.length() > 0) {
							tmpIndex = Integer.parseInt(indexString);
							counter++;
							isParamFound = true;
						}
				    }
				    catch ( NumberFormatException e )
				    {
						TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "attrName ["
								+ attrName + "] is not a " + parameterStartsWith + " input field");
				    }				
					TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "counter = ["
							+ counter + "] ");
				} // if
			} // while
		} // if !isParamFound
		
		
		
		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method. Returning value [" + counter + ']');
		return counter;
	}

/**
 * 
 * This getMaxParameterPosition will return the last number of set of 
 * input fields (partNumberx and quantityx).  
 * For example, if there are 14 set of inputs (7 rows), this function should 
 * returns 14.
 * 
 * @param requestObject
 * @param parameterStartsWith
 * @return
 */

	public static int getMaxParameterPosition(HttpServletRequest requestObject, String parameterStartsWith) {

		final String METHOD_NAME = "getMaxParameterPosition";

		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method.");
	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    arguments:");
		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        parameterStartsWith = [" + parameterStartsWith + ']');
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        req = [" + requestObject + "]");

		String paramName = "";
		String indexString = "";
		int index = 0;
		int tmpIndex = 0;

		Enumeration paramNames = requestObject.getParameterNames();

		while (paramNames.hasMoreElements()) {
			paramName = (String) paramNames.nextElement();
			TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Parameter [" + paramName + ']');
			if ((paramName.length() >= parameterStartsWith.length())
					&& (paramName.substring(0, parameterStartsWith.length())
							.equals(parameterStartsWith))) {
				indexString = paramName.substring(parameterStartsWith.length());
				if (indexString.length() > 0) {
					try {
						tmpIndex = Integer.parseInt(indexString);
						TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME,
								"Parameter [" + indexString + "] --> ["
										+ tmpIndex + ']');
						if (tmpIndex > index)
							index = tmpIndex;
					} catch (NumberFormatException e) {
						TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME,
								"paramName [" + paramName
										+ "] is not a " + parameterStartsWith + " input field");
					}
				} // if paramName
			} // if indexString
		} // while

		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method. Returning value [" + index + ']');
		return index;
	}

	
	
/**
 * Convenience method which returns the value of the specified parameterName
 * within the requestObject. The method will attempt to locate the parameter in
 * the following order: 1. request object parameter, 2. request object
 * attribute, 3. session object attribute, 4. request object header. Once the 
 * value has been found, the method stops looking. There is some overhead to 
 * using this method but it helps support data coming from a web page or 
 * web service. 
 * 
 * @param methodName
 *            the name of the method calling this method
 * @param requestObject
 *            the HttpServletRequest object which is to be searched
 * @param parameterName
 *            the name of the parameter we are looking for
 * @return an Object containing the value of parameterName. Returns null if the
 *         parameterName was not found.
 */
public static Object getParameter(HttpServletRequest requestObject, String parameterName) {

	final String METHOD_NAME = "getParameter";
	
	Object value = null;
	
	if (parameterName != null && !parameterName.equalsIgnoreCase("")) {
		value = requestObject.getParameter(parameterName); // returns String
		if (value != null) {
			TiredexLogger.logMessage(
					CLASS_NAME,
					METHOD_NAME,
					"Parameter ["
						+ parameterName
						+ "] found in request object parameter list, value = ["
						+ value
						+ ']');
		}
		else {	// value == null
			value = requestObject.getAttribute(parameterName); // returns Object
			if (value != null) {
				TiredexLogger.logMessage(
						CLASS_NAME,
						METHOD_NAME,
						"Parameter ["
							+ parameterName
							+ "] found in request object parameter attribute list, value = ["
							+ value
							+ ']');
			}
			else {	// value == null
				HttpSession session = requestObject.getSession(false);
				value = session.getAttribute(parameterName); // returns Object
				if (value != null) {
					TiredexLogger.logMessage(
						CLASS_NAME,
						METHOD_NAME,
						"Parameter ["
							+ parameterName
							+ "] found in session object attribute list, value = ["
							+ value
							+ ']');
				}
				else {	// value == null
					value = requestObject.getHeader(parameterName);
					if (value != null) {
						TiredexLogger.logMessage(
								CLASS_NAME,
								METHOD_NAME,
								"Parameter ["
									+ parameterName
									+ "] found in request object header list, value = ["
									+ value
									+ ']');
					}
					else {	// value == null
						TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Parameter [" + parameterName + "] not found.");
					}
				}
			}
		}
	}
	else {
		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Invalid parameter [" + parameterName + "].");
	}

	return value;
}



/**
 * This is a convenience method for retrieving an array of objects from the HttpServletRequest object.  
 * The method will attempt to locate the parameter in the following order: 1. request object parameter using
 * the getParameterValues, 2. request object attribute, 3. session object attribute.  Once the value has 
 * been found, the method stops looking.  There is some overhead to using this method but it helps support 
 * data coming from a web page or web service.
 *   
 * @param requestObject
 * @param parameterName
 * @return An array of objects
 */
public static Object[] getParameterValues(HttpServletRequest requestObject, String parameterName) {
	final String METHOD_NAME = "getParameterValues";
	
	Object value[] = null;
	
	if (parameterName != null && !parameterName.equalsIgnoreCase("")) {
		value = requestObject.getParameterValues(parameterName); // returns String[]
		if (value != null) {
			TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Parameter [" + parameterName
						+ "] found in request object parameter list; value(s):");
			TiredexLogger.logArrayDetails(CLASS_NAME, METHOD_NAME, "    ", value);
		}
		else {	// value == null
			value = (Object [])requestObject.getAttribute(parameterName); // returns Object
			if (value != null) {
				TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Parameter [" + parameterName
						+ "] found in request object attribute list; value(s):");
				TiredexLogger.logArrayDetails(CLASS_NAME, METHOD_NAME, "    ", value);
			}
			else {	// value == null
				HttpSession session = requestObject.getSession(false);
				value = (Object [])session.getAttribute(parameterName); // returns Object
				if (value != null) {
					TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Parameter [" + parameterName
							+ "] found in session object attribute list; value(s):");
					TiredexLogger.logArrayDetails(CLASS_NAME, METHOD_NAME, "    ", value);
				}
				else {	// value == null
					TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Parameter [" + parameterName + "] not found.");
				}
			}
		}
	}
	else {
		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Invalid parameter [" + parameterName + "].");
	}

	return value;
}



public static InitialContext getInitialContext() throws TiredexUnhandledException
{
	final String METHOD_NAME = "getInitialContext";
    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method");
	String param = null;
	try
	{
		PropertyResourceBundle configBundle = (PropertyResourceBundle)PropertyResourceBundle.getBundle(TIREDEX_UTILS_BUNDLE);
		Enumeration<String> k = configBundle.getKeys();
		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "configBundle = [" + configBundle + ']');
		while (k.hasMoreElements()) {
			String key = k.nextElement();
			TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    key = [" + key + "] = [" + configBundle.getString(key) + ']');
		}

		param = "USERID";
		userName = getCommonFnBdlProperty(param);
		param = "PASSWORD";
		password = getCommonFnBdlProperty(param);
		param = "DATA_SOURCE_NAME";
		dlrWebDataSrcName = getCommonFnBdlProperty(param);
		param = "INITIAL_CONTEXT_FACTORY";
		initialContextFactory = getCommonFnBdlProperty(param);

		param = "TIRE_REG_USERID";
		tireRegUserName = getCommonFnBdlProperty(param);
		param = "TIRE_REG_PASSWORD";
		tireRegPassword = getCommonFnBdlProperty(param);
		param = "TIRE_REG_DATA_SOURCE_NAME";
		tireRegDataSrcName = getCommonFnBdlProperty(param);

		param = "UNIDATA_MIRROR_USERID";
		uniMirrorUserName = getCommonFnBdlProperty(param);
		param = "UNIDATA_MIRROR_PASSWORD";
		uniMirrorPassword = getCommonFnBdlProperty(param);
		param = "UNIDATA_MIRROR_DATA_SOURCE_NAME";
		uniMirrorDataSrcName = getCommonFnBdlProperty(param);

        param = "UNIDATA_ARCHIVE_USERID";
        uniArchiveUserName = getCommonFnBdlProperty(param);
        param = "UNIDATA_ARCHIVE_PASSWORD";
        uniArchivePassword = getCommonFnBdlProperty(param);
        param = "UNIDATA_ARCHIVE_DATA_SOURCE_NAME";
        uniArchiveDataSrcName = getCommonFnBdlProperty(param);

		param = "YODA_USERID";
		yodaUserName = getCommonFnBdlProperty(param);
		param = "YODA_PASSWORD";
		yodaPassword = getCommonFnBdlProperty(param);
		param = "YODA_DATA_SOURCE_NAME";
		yodaDataSrcName = getCommonFnBdlProperty(param);

		param = "BI_USERID";
		biUserName = getCommonFnBdlProperty(param);
		param = "BI_PASSWORD";
		biPassword = getCommonFnBdlProperty(param);
		param = "BI_DATA_SOURCE_NAME";
		biDataSrcName = getCommonFnBdlProperty(param);
	}
	catch(Exception e)
	{
		String msg = "Error in reading the " + param + " parameter from the " + TIREDEX_UTILS_BUNDLE + " property file : " + e;
		TiredexLogger.logException(CLASS_NAME, METHOD_NAME, msg, e);
		throw new TiredexUnhandledException(msg);
	}

	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "retrieving InitialContext from websphere...");
	Properties p =  new Properties();
	try {
//		p.put(Context.PROVIDER_URL, "iiop:///" );
		p.put(Context.INITIAL_CONTEXT_FACTORY, initialContextFactory);
		InitialContext ictx = new InitialContext(p);
		
//		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "context = [" + ictx.toString() + ']');
//		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "context.getNameInNamespace = [" + ictx.getNameInNamespace() + ']');

		NamingEnumeration<NameClassPair> list = ictx.list("");

		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "context name-class pairs:");
		while (list.hasMore()) {
		    NameClassPair nc = list.next();
			TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    " + nc);
		}

		Hashtable<?, ?> ht = ictx.getEnvironment();
		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "context environment hashtable:");
		TiredexLogger.logHashtableDetails(CLASS_NAME, METHOD_NAME, "    ", ht);

        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method.  Returning InitialContext = [" + ictx + ']');
        return ictx;
	}
	catch(Exception e)
	{
		String msg = "Error in getting InitialContext with properties [" + p + "]";
		TiredexLogger.logException(CLASS_NAME, METHOD_NAME, msg, e);
		throw new TiredexUnhandledException(msg);
	}
}



public static String getNextPrevURL(String screen) throws TiredexUnhandledException
{
	final String METHOD_NAME = "getNextPrevURL";
	String screenURL="";

	try
	{
		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Searching NEXT_PREVIOUS_BUNDLE for screen = [" + screen + ']');
		PropertyResourceBundle nextPreviousBundle = (PropertyResourceBundle)PropertyResourceBundle.getBundle(NEXT_PREVIOUS_BUNDLE);
		screenURL = nextPreviousBundle.getString(screen);
		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "screen = [" + screen + "] found screenURL = [" + screenURL + ']');
		return screenURL;
	}
	catch(Exception e)
	{
		String msg = "Error in reading the " + screen + " parameter from the " + NEXT_PREVIOUS_BUNDLE + " property file : " + e;
		TiredexLogger.logException(CLASS_NAME, METHOD_NAME, msg, e);
		throw new TiredexUnhandledException(msg);
	}
}
public static String getScreenURL(String screen) throws TiredexUnhandledException
{
	final String METHOD_NAME = "getScreenURL";
	String screenURL="";

	try
	{
		PropertyResourceBundle configBundle = (PropertyResourceBundle)PropertyResourceBundle.getBundle(SCREEN_NAMES_BUNDLE);
		screenURL = configBundle.getString(screen);
		return screenURL;
	}
	catch(Exception e)
	{
		String msg = "Error in reading the " + screen + " parameter from the " + SCREEN_NAMES_BUNDLE + " property file : " + e;
		TiredexLogger.logException(CLASS_NAME, METHOD_NAME, msg, e);
		throw new TiredexUnhandledException(msg);
	}
}
/**
 * This method concatentes system date and time and returns the string
 * @return String
 */
public static String getTimeStamp()
{
	/*
	 * Get the Current Time and Date.
	 */
	Calendar gc = GregorianCalendar.getInstance();

//	hrNow = gc.get(Calendar.HOUR_OF_DAY);
	minNow = gc.get(Calendar.MINUTE);
	
	/*
	 * Since secNow, lastSec and timeStamp are global variables, normally this section of 
	 * code should be "synchronized" but since we are working with the current date and time,  
	 * it does not matter if multiple threads update the same variable, the time is the same.
	 */
	
	secNow = gc.get(Calendar.SECOND);

	// this "if" is new.  see if it's faster than the code below that is commented out.
	
	/*
	 * If the time has not changed within the last second, there is no point in updating
	 * the timeStamp string.
	 */
	if (minNow != lastMin || secNow != lastSec) {
		lastSec = secNow;
		lastMin = minNow;
		timeStamp = DATE_FORMATTER.format(gc.getTime());
	}
	
	
//	/*
//	 * If the time has not changed since the last time this method was called, in order to 
//	 * optimize code, do not regenerate the entire string representation of the date and time.
//	 * Only the piece of data that has changed will be updated.
//	 */ 
//
//		if (secNow != lastSec) {
//			lastSec = secNow;
//			secStr.delete(0, secStr.length());
//			if (secNow < 10) {
//				secStr.append('0');
//			}
//			secStr.append(secNow);
//			timeStampStr.replace(SEC_POS, SEC_END, secStr.toString());
//		}
//
//		// Although we could have put the "if minNow..." statement below within the scope of the 
//		// "if secNow..." above, it is possible that 2 consecutive timestamps could be requested 
//		// exactly 60 seconds apart, a 1/60 chance.
//		if (minNow != lastMin) {
//			lastMin = minNow;
//			minStr.delete(0, minStr.length());
//			if (minNow < 10) {
//				minStr.append('0');
//			}
//			minStr.append(minNow);
//			timeStampStr.replace(MIN_POS, MIN_END, minStr.toString());
//		}
//		
//		if (hrNow != lastHr) {
//			lastHr = hrNow;
//			hrStr.delete(0, hrStr.length());
//			if (hrNow < 10) {
//				hrStr.append('0');
//			}
//			hrStr.append(hrNow);
//			timeStampStr.replace(HR_POS, HR_END, hrStr.toString());
//
//			dayNow = gc.get(Calendar.DAY_OF_MONTH);
//			if (dayNow != lastDay) {
//				// since this section is executed once a day at the most, we don't worry about
//				// optimization and we rebuild the entire timeStampStr even though the code 
//				// execution above was a waste of time.
//
//				lastDay = dayNow;
//
//				dayStr.delete(0, dayStr.length());
//				if (dayNow < 10) {
//					dayStr.append('0');
//				}
//				dayStr.append(dayNow);
//
//				int monthNow = gc.get(Calendar.MONTH) + 1;
//				monStr.delete(0, monStr.length());
//				if (monthNow < 10) {
//					monStr.append('0');
//				}
//				monStr.append(monthNow);
//
//				int yrNow = gc.get(Calendar.YEAR);
//				yearStr.delete(0, yearStr.length());
//				yearStr.append(yrNow);
//						
//				// format: yyyy-mm-dd hh:mm:ss
//				timeStampStr.delete(0, timeStampStr.length());
//				timeStampStr.append(yrNow)
//					.append('-')
//					.append(monStr)
//					.append('-')
//					.append(dayStr)
//					.append(' ')
//					.append(hrStr)
//					.append(':')
//					.append(minStr)
//					.append(':')
//					.append(secStr);
//			
//			} // if (dayNow != lastDay)
//
//		} // if (hrNow != lastHr)
//	
//	return (timeStampStr.toString());
	
	
	
	return (timeStamp);
}

public static String returnSubstring(String sourceString, char stringBreaker) 
{
	StringTokenizer stringTokenizer = new StringTokenizer(sourceString,new Character(stringBreaker).toString());
	String a="";
	while (stringTokenizer.hasMoreTokens()) 
	{
		 a = stringTokenizer.nextToken();
	}
	
	return a;
}
/**
 * This Method uses Java mail Api for sending mail.
 * @param userEmail java.lang.String
 * @param subject java.lang.String
 * @param emailText java.lang.String
 * @exception java.rmi.TiredexUnhandledException The exception description.
 * @exception com.tiredex.yoko.exceptions.TiredexAppException The exception description.
 */
//public static void sendEmail(String userEmail, String subject, String emailText) throws TiredexUnhandledException, TiredexAppException 
//{
//	sendEmail(userEmail, subject, emailText, "");
//	/*
//*	final String METHOD_NAME = "sendEmail";
//*	String fromAddress = "";
//*	String ipAddress = "";
//*	try
//*	{
//*		PropertyResourceBundle senderDetailBundle = (PropertyResourceBundle)PropertyResourceBundle.getBundle (EMAIL_SENDER_DETAILS_BUNDLE);
//*		fromAddress = senderDetailBundle.getString("MAILFROM");
//*		ipAddress = senderDetailBundle.getString("IPADDRESS");
//*		Properties props = new Properties();
//*		props.put("mail.smtp.host", ipAddress);
//*		Session session = Session.getDefaultInstance(props, null);
//*		javax.mail.Message msg = new MimeMessage(session);
//*		Address toAddrs[] = new InternetAddress[1];
//*		toAddrs[0] = new InternetAddress(userEmail);
//*		Address fromAddr = new InternetAddress(fromAddress);
//*		msg.setFrom(fromAddr);
//*		msg.setRecipients(javax.mail.Message$RecipientType.TO, toAddrs);
//*		msg.setSubject(subject);
//*		String content = emailText;
//*		msg.setContent(content, "text/plain");
//*		Transport.send(msg);
//*	}
//*	catch (AddressException e)
//*	{
//*		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, "Exception caught: " + e);
//*		throw new TiredexUnhandledException(e.getMessage());
//*	}
//*	catch (MessagingException e)
//*	{
//*		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, "Exception caught: " + e);
//*		throw new TiredexUnhandledException(e.getMessage());
//*	}
//	*/
//}
/**
 * This Method uses Java mail Api for sending mail.
 * @param userEmail java.lang.String
 * @param subject java.lang.String
 * @param emailText java.lang.String
 * @param senderType java.lang.String Should be P or T or an Internet email address
 * @exception java.rmi.TiredexUnhandledException The exception description.
 * @exception com.tiredex.yoko.exceptions.TiredexAppException The exception description.
 */
//public static void sendEmail(String userEmail, String subject, String emailText, String senderType) throws TiredexUnhandledException, TiredexAppException 
//{
//	final String METHOD_NAME = "sendEmail(4 args)";
//
//    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method");
//    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    arguments:");
//    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        userEmail = [" + userEmail + ']');
//    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        subject = [" + subject + ']');
//    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        emailText = [" + emailText + ']');
//    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        senderType = [" + senderType + ']');
//
//	String fromAddress = "";
//	String ipAddress = "";
//	try
//	{
//		String param = null;
//		try {
//			PropertyResourceBundle senderDetailBundle = (PropertyResourceBundle)PropertyResourceBundle.getBundle(EMAIL_SENDER_DETAILS_BUNDLE);
//
//			if (senderType == null) {
//				senderType = "";
//			}
//			
//			senderType = senderType.trim();
//			if (senderType.length() <= 1) {
//				param = "MAILFROM" + senderType.toUpperCase();
//			    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "retrieving property [" + param + ']');
//				fromAddress = senderDetailBundle.getString(param);
//			    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, param + " = [" + fromAddress + ']');
//			}
//			else {
//				fromAddress = senderType;
//			    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Using [" + fromAddress + "] as 'from' email address.");
//			}
//
//			param = "IPADDRESS";
//		    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "retrieving property [" + param + ']');
//			ipAddress = senderDetailBundle.getString(param);
//		    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, param + " = [" + ipAddress +']');
//		}
//		catch (Exception e)
//		{
//			String msg = "Error in reading the " + param + " parameter from the " + EMAIL_SENDER_DETAILS_BUNDLE + " property file : " + e;
//			TiredexLogger.logError(CLASS_NAME, METHOD_NAME, msg);
//			throw e;
//		}
//		Properties props = new Properties();
//		props.put("mail.smtp.host", ipAddress);
//
//	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "creating mail session on host [" + ipAddress +']');
//		Session session = Session.getDefaultInstance(props, null);
//
//	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "building mail message...");
//		Message msg = new MimeMessage(session);
//		Address toAddrs[] = new InternetAddress[1];
//		toAddrs[0] = new InternetAddress(userEmail);
//		Address fromAddr = new InternetAddress(fromAddress);
//		msg.setFrom(fromAddr);
//		msg.setRecipients(RecipientType.TO, toAddrs);
//		msg.setSubject(subject);
//		msg.setContent(emailText, "text/plain");
//		msg.setSentDate(new Date());
//
//	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "sending mail message...");
//		Transport.send(msg);
//	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "message sent");
//	}
//	catch (Exception e)
//	{
//		TiredexLogger.logException(CLASS_NAME, METHOD_NAME, "Exception caught: ", e);
//		throw new TiredexUnhandledException(e.getMessage());
//	}
////	catch (MessagingException e)
////	{
////		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, "Exception caught: " + e);
////		throw new TiredexUnhandledException(e.getMessage());
////	}
//
//    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");
//
//}
/**
 * This method removes the character passed from the string that is passed as a parameter. 
 * @author Hemant Gaur
 * @param char charToBeRemoved
 * @param java.lang.String sourceString
 * @return java.lang.String finalString
 */
public static String skipQuate(String sourceString,char charToBeRemoved) 
{
	String finalString=""; 
	char[] sourceStringArray=  sourceString.toCharArray();
	boolean first = true;
	for (int i = 0, len = sourceString.length(); i < len; i++)
	{
		if(sourceStringArray[i] == charToBeRemoved)
		{
			if(first)
			{
				first = false;
				continue;
			}
			else
				first = true;	
		}
		finalString += sourceStringArray[i];
	}
	return finalString;
}
/**
 * This method removes the character passed from the string that is passed as a parameter. 
 * @author Hemant Gaur
 * @param char charToBeRemoved
 * @param java.lang.String sourceString
 * @return java.lang.String finalString
 */
public static String stripString(String sourceString,char charToBeRemoved) 
{
	String finalString=""; 
	char[] sourceStringArray=  sourceString.toCharArray();
	
	for (int i = 0, len = sourceString.length(); i < len; i++)
	{
		if(sourceStringArray[i]!=charToBeRemoved)
		{
			finalString += sourceStringArray[i];
		}
	}
	return finalString;
}



/**
 * This is a convenience method for returning a string or empty (aka "").
 * If the input String is null, then the empty String is returned.
 * @param obj
 * @return
 */
public static String getTrimmedOrEmptyString(Object obj) {
	String answer = null;
	if (obj != null) {
		answer = obj.toString();
	}
	else {
		answer = "";
	}
	return answer;
}
/**
 * This is a convenience method for returning a string which is either trimmed or empty (aka "").
 * If the input String is null, then the empty String is returned.
 * 
 * @param aString
 * @return
 */
public static String getTrimmedOrEmptyString(String aString) {
    
    String result = null;
    if (aString != null) {
        result = aString.trim();
    }
    else {
        result = "";
    }
    return result;
}



/**
 * This is a convenience method for returning a string or null.
 * If the input String is null, then null is returned.
 * @param obj
 * @return
 */
public static String getTrimmedOrNullString(Object obj) {
	String answer = null;
	if (obj != null) {
		answer = obj.toString();
	}
	return answer;
}
/**
 * This is a convenience method for returning a string which is either trimmed or null.
 * If the input String is null, then null is returned.
 * 
 * @param aString
 * @return
 */
public static String getTrimmedOrNullString(String aString) {
    
    String result = null;
    if (aString != null) {
        result = aString.trim();
    }
    return result;
}



/**
 * Gets the value for the given property from the EMAIL_SENDER_DETAILS_BUNDLE property file
 * @return Returns a String
 *
 */
//private static String getEmailPropValue(String propertyName) throws TiredexUnhandledException {
//	final String METHOD_NAME = "getEmailPropValue";
//
//    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method");
//    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    arguments:");
//    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        propertyName = [" + propertyName + ']');
//
//	String propValue = null;
//	
//	try {
//	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "getting PropertyResourceBundle [" + EMAIL_SENDER_DETAILS_BUNDLE + ']');
//		PropertyResourceBundle senderDetailBundle = (PropertyResourceBundle)PropertyResourceBundle.getBundle(EMAIL_SENDER_DETAILS_BUNDLE);
//
//		propertyName = propertyName.trim();
//		
//	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "retrieving property [" + propertyName + ']');
//		propValue = senderDetailBundle.getString(propertyName);
//	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, propertyName + " = [" + propValue +']');
//	}
//	catch (Exception e)
//	{
//		String msg = "Error in reading the " + propertyName + " parameter from the " + EMAIL_SENDER_DETAILS_BUNDLE + " property file : " + e + "; raising TiredexUnhandledException";
//		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, msg);
//		throw new TiredexUnhandledException(msg);
//	}
//
//    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");
//    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    returning String value = [" + propValue + ']');
//    return propValue;
//}



///**
// * Gets the Consumer Marketing Materials Request email address which will appear in the FROM section of the email
// * @return returns a String
// */
//public static String getMktgMatReqEmailAddrFromYtcCons() throws TiredexUnhandledException {
//	final String METHOD_NAME = "getMktgMatReqEmailAddrFromYtcCons";
//
//    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method");
//
//	String addr = null;
//	
//	try {
//		addr = getEmailPropValue("MKTG_MAT_REQ_EMAIL_ADDR_FROM_YTC_P");
//	}
//	catch (Exception e) 
//	{
//		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, "Exception caught: " + e + "; raising TiredexUnhandledException");
//		throw new TiredexUnhandledException(e.toString());
//	}
//
//    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");
//    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    returning address String = [" + addr + ']');
//	return addr;
//}
//
///**
// * Gets the Consumer Marketing Materials Request email address which will receive the order
// * @return returns a String
// */
//public static String getMktgMatReqEmailAddrToYtcCons() throws TiredexUnhandledException {
//	final String METHOD_NAME = "getMktgMatReqEmailAddrToYtcCons";
//
//    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method");
//
//	String addr = null;
//	
//	try {
//		addr = getEmailPropValue("MKTG_MAT_REQ_EMAIL_ADDR_TO_YTC_P");
//	}
//	catch (Exception e) 
//	{
//		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, "Exception caught: " + e + "; raising TiredexUnhandledException");
//		throw new TiredexUnhandledException(e.toString());
//	}
//
//    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");
//    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    returning address String = [" + addr + ']');
//	return addr;
//}
//
//
//
///**
// * Gets the Commercial Marketing Materials Request email address which will appear in the FROM section of the email
// * @return Returns a String
// */
//public static String getMktgMatReqEmailAddrFromYtcComm() throws TiredexUnhandledException {
//	final String METHOD_NAME = "getMktgMatReqEmailAddrFromYtcComm";
//
//    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method");
//
//	String addr = null;
//	
//	try {
//		addr = getEmailPropValue("MKTG_MAT_REQ_EMAIL_ADDR_FROM_YTC_T");
//	}
//	catch (Exception e) 
//	{
//		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, "Exception caught: " + e + "; raising TiredexUnhandledException");
//		throw new TiredexUnhandledException(e.toString());
//	}
//
//    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");
//    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    returning address String = [" + addr + ']');
//	return addr;
//}
//
///**
// * Gets the Commercial Marketing Materials Request email address which will receive the order
// * @return returns a String
// */
//public static String getMktgMatReqEmailAddrToYtcComm() throws TiredexUnhandledException {
//	final String METHOD_NAME = "getMktgMatReqEmailAddrToYtcComm";
//
//    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method");
//
//	String addr = null;
//	
//	try {
//		addr = getEmailPropValue("MKTG_MAT_REQ_EMAIL_ADDR_TO_YTC_T");
//	}
//	catch (Exception e) 
//	{
//		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, "Exception caught: " + e + "; raising TiredexUnhandledException");
//		throw new TiredexUnhandledException(e.toString());
//	}
//
//    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");
//    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    returning address String = [" + addr + ']');
//	return addr;
//}



/**
 * This is a convenience method which will take a list of numbers and break it down into
 * a set of string containing no more than 1,000 numbers.  Oracle can only handle up to 1,000
 * elements in the "in" clause of its "select" statement.
 * 
 * @param aList of Strings
 * @return
 */
public static String[] getOracleLists(List aList) {
    final String METHOD_NAME = "getOracleLists";

    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method");
    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    arguments:");
    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        aList.size() = " + aList.size());

    final int maxSize = 1000;
    final int listSize =  aList.size();

    // Oracle can only lookup 1,000 ship-to numbers at a time.
    int groups = (listSize / maxSize) + 1;
    if (listSize > 0 && ((listSize % maxSize) == 0)) {
        groups--;
    }

    String subList[] = new String[groups];
    int listIndex = 0;
    for (int k = 0; k < groups; k++) {
        int max;
        if (k < (groups - 1)) {
            max = maxSize;
        }
        else {
            // k == (groups - 1)
            if ((listSize % maxSize) != 0) { 
                max = listSize % maxSize;
            }
            else {
                max = maxSize;
            }
        }

        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "subList [" + k + "] number of shipTo's = [" + max +']');
        
        StringBuffer shipToSet = new StringBuffer(max * 11); // estimating (1 single quote + 8 chars + 1 single quote) / shipto + 1 comma
        for (int j = 0; j < max; j++) {
            shipToSet.append("'").append((String)aList.get(listIndex)).append("'");
            listIndex++;
            if (j < (max - 1)) {
                shipToSet.append(',');
            }
        } // for j
        subList[k] = shipToSet.toString();
    } // for k
    
    
    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");
    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    returning subList array of Strings:");
    TiredexLogger.logArrayDetails(CLASS_NAME, METHOD_NAME, "        ", subList );
    return subList;
}



/**
 * A convenience method that calculates the elapse time between 2 times and returns the value
 * as a string in hh:mm:ss.sss format.  If either startTime or endTime is null, then the
 * method returns an empty string ("").
 * 
 * @param startTime
 * @param endTime
 * @return A String representation of the elapse time in hh:mm:ss.sss format OR an empty string.
 */
public static String getElapsedTimeString (Calendar startTime, Calendar endTime) {
	
	String answer = "";
	
	if (startTime != null && endTime != null) {
		long sMill = startTime.getTimeInMillis();
		long eMill = endTime.getTimeInMillis();
		long elapse = eMill - sMill;
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		answer = dateFormat.format(new Date(elapse));
	}
	
	return answer;
}



/**
 * Convenience method to check if a string is too long.  If it is too long, it will be truncated to the
 * specified length.
 * 
 * @param stringToCheck
 * @param maxLength
 * @return a String copy of stringToCheck and has a length less than or equal to maxLength
 */
public static String trimDbString(String stringToCheck, int maxLength) {
	final String METHOD_NAME = "trimDbString";
	
	String result = null;
	if (stringToCheck != null) {
		if(stringToCheck.length() <= maxLength) {
    		result = stringToCheck;
		}
    	else {
            TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, 
            		"***** programming exception ***** POSSIBLE BUG: string value [" 
            		+ stringToCheck 
    				+ "] too large for database field; limit is " 
    				+ maxLength 
    				+ " and actual length = [" 
    				+ stringToCheck.length() 
    				+ "]. Data truncated.  No exceptions thrown.");
    		result = stringToCheck.substring(0, maxLength);
    	}
	}
	return result;
}



/**
 * This is a convenience method to easily remove unwanted characters from a HTTP Session Id and
 * replace them with underscores ("_").  The following characters will be removed: []-+*%/
 * 
 * @param sessionId the HTTP Session Id. If the value is null, the method will return null.
 * @return
 */
public static String cleanSessionId(String sessionId) {
	if (sessionId != null) {
		sessionId = sessionId.replaceAll("[-+*/%]", "_");
	}
	return sessionId;
}



/**
 * Convenience method to format a BigDecimal into String using Locale specs.
 * @param aNumber
 * @return A String representation of aNumber in $n,nnn.nn format.  If aNumber is null, return an empty String.
 */
public static String formatBigDecimal(BigDecimal aNumber) {
	
	String numStr = null;
	if (aNumber != null) {
		numStr = numFormatter.format(aNumber);
	}
	else {
		numStr = "";
	}
	return numStr;
}



public static Connection getUniMirrorConnection() throws TiredexUnhandledException
{
	final String METHOD_NAME = "getUniMirrorConnection";
	
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method.");
	
	try
	{
		InitialContext ic = getInitialContext();
		if(uniMirrorDataSrc == null)
		{
			uniMirrorDataSrc = (DataSource)ic.lookup(uniMirrorDataSrcName);
		}
		Connection conn = uniMirrorDataSrc.getConnection(uniMirrorUserName, uniMirrorPassword);
		
		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method. Returning Unidata Mirror database Connection = [" + conn + ']');
		return conn;
	}
	catch(NamingException e)
	{
		String msg = "NamingException occured while looking up the DataSource object : " + e;
		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, msg);
		throw new TiredexUnhandledException(msg);
	}
	catch(SQLException e)
	{
		String msg = "SQLException occured while taking Connection object : " + e;
		TiredexLogger.logError(CLASS_NAME, METHOD_NAME, msg);
		throw new TiredexUnhandledException(msg);
	}
    catch (Exception e) {
        String msg = "Exception occured while getting Connection object.";
        TiredexLogger.logException(CLASS_NAME, METHOD_NAME, msg + ' ', e);
        throw new TiredexUnhandledException(msg);
    }
}



public static Connection getUniArchiveConnection() throws TiredexUnhandledException
{
    final String METHOD_NAME = "getUniArchiveConnection";
    
    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method.");
    
    try
    {
        InitialContext ic = getInitialContext();
        if(uniArchiveDataSrc == null)
        {
            uniArchiveDataSrc = (DataSource)ic.lookup(uniArchiveDataSrcName);
        }
        Connection conn = uniArchiveDataSrc.getConnection(uniArchiveUserName, uniArchivePassword);
        
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method. Returning Unidata Mirror Archive database Connection = [" + conn + ']');
        return conn;
    }
    catch(NamingException e)
    {
        String msg = "NamingException occured while looking up the DataSource object : " + e;
        TiredexLogger.logError(CLASS_NAME, METHOD_NAME, msg);
        throw new TiredexUnhandledException(msg);
    }
    catch(SQLException e)
    {
        String msg = "SQLException occured while taking Connection object : " + e;
        TiredexLogger.logError(CLASS_NAME, METHOD_NAME, msg);
        throw new TiredexUnhandledException(msg);
    }
    catch (Exception e) {
        String msg = "Exception occured while getting Connection object.";
        TiredexLogger.logException(CLASS_NAME, METHOD_NAME, msg + ' ', e);
        throw new TiredexUnhandledException(msg);
    }
}



/**
 * Return the most common file type for the given HTML contentType.  For example, if
 * contentType = "application/pdf", the this method will return pdf.  Another example,
 * for contentType = "image/jpeg", return "jpg".  This method helps determine the true
 * nature of the file users are uploading and not relying on the stated file type in the
 * file name.
 * 
 * @param contentType
 * @return String file extension, return "" if the contentType is not recognized
 */
public static String getFileType(String contentType) {
	
	final String METHOD_NAME = "getFileType";

	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method");
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    arguments:");
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        contentType = [" + contentType + ']');
	
	String fileType = null;
	MimeTypesEnum marcel = MimeTypesEnum.valueOfMimeType(contentType);
	if (marcel != null) {
		fileType = marcel.getFileExtension();
		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "found mimeType = [" + marcel + ']');
		
		// remove leading "." if any
		int dot = fileType.indexOf(".");
		if (dot >= 0) {
			fileType = fileType.substring(dot + 1);
		}
	} // if marcel
	else {
		fileType = "";
		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "***** POSSIBLE BUG *****  no MIME type found for contentType = [" + contentType + "].  No exceptions thrown.  Continuing...");
	}
	
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    returning fileType = [" + fileType + ']');
	return fileType;
}



/**
 * @param req
 * @param uploadDir is an operating system directory
 * @param fileName
 * @return a Map containing "fileDir", "fileName", and "fileType", in addition to all the form fields on the HTML form
 */
	public static Map<String, String[]> getMultiPartParameters(
			HttpServletRequest req, String uploadDir, String fileName)
			throws TiredexAppException, TiredexUnhandledException {
		
		final String METHOD_NAME = "getMultiPartParameters";

		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method");
		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    arguments:");
		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        fileDir = [" + uploadDir + ']');
		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        fileName = [" + fileName + ']');
		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        HttpServletRequest:");
		TiredexLogger.logHttpRequest(CLASS_NAME, METHOD_NAME, req);

		Map<String, String[]> parameterMap = new HashMap<String, String[]>();

		try {
			// HttpSession session = req.getSession(false);

			// String appImageDir =
			// session.getServletContext().getRealPath("AppImage");
			// TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME,
			// "appImageDir = [" + appImageDir + ']');

			// these 4 statements are used to download the parameters from the
			// user to the server
			DiskFileItemFactory diskFactory = new DiskFileItemFactory();
			ServletFileUpload fileUploader = new ServletFileUpload(diskFactory);
			List<FileItem> multiparts = null;
			try {
				multiparts = fileUploader.parseRequest(req);
			}
			catch (InvalidContentTypeException e) {	
				// this exception will get thrown when the web service is running this method
				TiredexLogger.logError(CLASS_NAME, METHOD_NAME, "caught exception [" + e + "].  Not re-thrown.  Continuing...");
			}
			
			
			
			/*
			 * loop thru all the parts of the request object; save the raw data
			 * to a file and load the plain text parameter into a map object
			 */
			if (multiparts != null) {
				for (FileItem item : multiparts) {
					String fieldName = item.getFieldName();
					TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "processing fieldName = [" + fieldName + ']');
					if (!item.isFormField()) {
						// this is a file we need to download from the user and write a file
						String fileSpec = item.getName();
						TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "fileSpec = [" + fileSpec + ']');

						// if the user specified a file name, he/she is uploading a
						// new image to the database
						if (fileSpec != null && !fileSpec.equalsIgnoreCase("")) {
							String nameAndType = new File(fileSpec).getName();
							TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "nameAndType = [" + nameAndType + ']');

							// try to figure out what kind of data
							String contentType = item.getContentType();
							TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "contentType = [" + contentType + ']');
							String fileType = getFileType(contentType);
							
							int lastDot = nameAndType.lastIndexOf(".");
							if (lastDot > -1) {
								fileName += nameAndType.substring(0, lastDot);
								if (fileType.equalsIgnoreCase("")) {
									// if we couldn't determine the type of data, use the fileType of the user's file
									fileType = nameAndType.substring(lastDot + 1);
								}
							} 
							else {
								fileName += nameAndType;
							}
							TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "fileName = [" + fileName + ']');
							TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "fileType = [" + fileType + ']');

							// get some of the stats
							boolean isInMemory = item.isInMemory();
							TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "isInMemory = [" + isInMemory + ']');

							long sizeInBytes = item.getSize();
							TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "sizeInBytes = [" + sizeInBytes + ']');

							// write the data from the remote user to a local file
							final String outFileSpec = uploadDir + File.separator + fileName + '.' + fileType;
							TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "saving original image file to [" + outFileSpec + ']');
							File outFile = new File(outFileSpec);
							item.write(outFile);

							TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "File uploaded successfully");

							parameterMap.put("fileDir", new String[] { uploadDir });
							parameterMap.put("fileName", new String[] { fileName });
							parameterMap.put("fileType", new String[] { fileType });
							
						} // if fileSpec
						else {
							TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME,
									"fileSpec = []; therefore, if mode = upload_logo, leave it as is; if mode = delete_logo, we delete the logo.");
						}
					} // if
					else {
						// we found a non-file parameter; save it for later in a Map
						TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "processing form field item = [" + item + ']');
						processFormField(item, parameterMap);
					}
				} // for
			} // if multiparts
			else {
				TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "multi-part parameters not found.  This might be a web service.");
			}

		} 
		catch (TiredexAppException e) {
			TiredexLogger.logError(CLASS_NAME, METHOD_NAME, e.toString());
			throw e;
		} 
		catch (TiredexUnhandledException e) {
			TiredexLogger.logError(CLASS_NAME, METHOD_NAME, e.toString());
			throw e;
		} 
		catch (Exception e) {
			TiredexLogger.logException(CLASS_NAME, METHOD_NAME, "", e);
			throw new TiredexUnhandledException(
					ErrorMessageInterface.REMOTE_REMOTE + ' ' + e.toString());
		}

		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");
		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME,
				"    returning parameterMap:");
		TiredexLogger.logMapDetails(CLASS_NAME, METHOD_NAME, "        ",
				parameterMap);

		return parameterMap;

	}


/**
 * @param formField
 * @param parameterMap
 */
private static void processFormField(FileItem formField, Map<String, String[]> parameterMap) {
    final String METHOD_NAME = "processFormField";
    
    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method");
    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    arguments:");
    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        formField = [" + formField + ']');
    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        parameterMap = [" + parameterMap + ']');

    
    
    String name = formField.getFieldName();
    String value = formField.getString();
    String[] values = parameterMap.get(name);

    if (values == null) {
        // Not in parameter map yet, so add as new value.
        parameterMap.put(name, new String[] { value });
    } 
    else {
        // Multiple field values, so add new value to existing array.
        int length = values.length;
        String[] newValues = new String[length + 1];
        System.arraycopy(values, 0, newValues, 0, length);
        newValues[length] = value;
        parameterMap.put(name, newValues);
    }
    
    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");
    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    returning parameterMap:");
    TiredexLogger.logMapDetails(CLASS_NAME, METHOD_NAME, "        ", parameterMap);
}






/**
 * Convert date from "Mon Aug 01 2011 00:00:00 GMT-0700 (Pacific Daylight Time)" format to Date object
 * @param dateStr
 * @return the values of the "dateStr" parameter to java.sql.Date
 * @throws TiredexAppException
 * @throws TiredexUnhandledException
 */
public static java.sql.Date getDateParamWGMT(String dateStr) 
		throws TiredexAppException, TiredexUnhandledException {
	final String METHOD_NAME = "getDateParamWGMT";
	
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Begin method");
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    argument:");
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "         dateStr = [" + dateStr + ']');
	
	final String dFormat2 = "EEE MMM dd yyyy hh:mm:ss ";
	
	java.sql.Date sqlDate = null;
    /*
     * shipDateStr is in 
     * 		"Mon Aug 01 2011 00:00:00 GMT-0700 (Pacific Daylight Time)" format 
     */
    if (dateStr != null && !dateStr.trim().equalsIgnoreCase("")) {
    	try {
        	SimpleDateFormat df2 = new SimpleDateFormat(dFormat2);
        	// remove everything after the "GMT", we only need the date.
        	int paren = dateStr.indexOf("GMT");	
        	if (paren > 0) {
        		dateStr = dateStr.substring(0, paren);
        	}
	        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "attempting to parse dateStr = [" + dateStr + "] using [" + dFormat2 + ']');
        	java.util.Date d = df2.parse(dateStr);
        	sqlDate = new java.sql.Date(d.getTime());
	        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "shipDate = [" + sqlDate + ']');
    	}
        catch (Exception e) {
            TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Error parsing String date using format [" + dFormat2 + "], continuing...");
        }
    } // if startShipDateStr != null && ...
	
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    returning startShipDate = [" + sqlDate + ']');
	return sqlDate;
}






/**
 * Convert date from "Mon Aug 1 00:00:00 PDT 2011" format to Date object
 * @param dateStr
 * @return the values of the "dateStr" parameter as java.sql.Date
 * @throws TiredexAppException
 * @throws TiredexUnhandledException
 */
public static java.sql.Date getDateParamWTimeZone(String dateStr) 
		throws TiredexAppException, TiredexUnhandledException {
	final String METHOD_NAME = "getDateParamWTimeZone";
	
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Begin method");
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    argument:");
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "         dateStr = [" + dateStr + ']');
	
	final String dFormat1 = "EEE MMM dd hh:mm:ss z yyyy";
	
	java.sql.Date sqlDate = null;
    /*
     * shipDateStr is in 
     * 		"Mon Aug 1 00:00:00 PDT 2011" format
     */
    if (dateStr != null && !dateStr.trim().equalsIgnoreCase("")) {
    	try {
        	SimpleDateFormat df = new SimpleDateFormat(dFormat1);
	        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "attempting to parse dateStr = [" + dateStr + "] using [" + dFormat1 + ']');
        	java.util.Date d = df.parse(dateStr);
        	sqlDate = new java.sql.Date(d.getTime());
	        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "shipDate = [" + sqlDate + ']');
    	}
        catch (Exception e) {
            TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Error parsing String date using format [" + dFormat1 + "], continuing...");
        }
    } // if startShipDateStr != null && ...
	
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    returning startShipDate = [" + sqlDate + ']');
	return sqlDate;
}



/**
 * @param shortDateStr in mm/dd/yyyy format
 * @return java.sql.Date
 * @throws TiredexAppException
 * @throws TiredexUnhandledException
 */
public static java.sql.Date getDateParamMDY(String shortDateStr)
		throws TiredexAppException, TiredexUnhandledException {
	final String METHOD_NAME = "getDateParamMDY";
	
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Begin method");
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    argument:");
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "         shortDateStr = [" + shortDateStr + ']');
	
	final String dFmt = "MM/dd/yyyy";

	java.sql.Date shortDate = null;

	if (shortDateStr != null && !shortDateStr.trim().equalsIgnoreCase("")) {
    	try {
        	SimpleDateFormat df = new SimpleDateFormat(dFmt);
	        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "attempting to parse dateStr = [" + shortDateStr + "] using [" + dFmt + ']');
        	java.util.Date d = df.parse(shortDateStr);
        	shortDate = new java.sql.Date(d.getTime());
	        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "shortDate = [" + shortDate + ']');
    	}
        catch (Exception e) {
            TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Error parsing String date using format [" + dFmt + "], continuing...");
        }
    } // if shortDateStr != null && ...
    
    return shortDate;
}



/**
 * @param requestObject
 * @return the values of the "startShipDate" parameter from the requestObject
 * @throws TiredexAppException
 * @throws TiredexUnhandledException
 */
public static java.sql.Date getDateParam(String dateStr) 
		throws TiredexAppException, TiredexUnhandledException {
	final String METHOD_NAME = "getDateParam";
	
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Begin method");
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    argument:");
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "         shipDateStr = [" + dateStr + ']');
	

	
	java.sql.Date sqlDate = null;
	try {
        /*
         * shipDateStr is in 
         * 		"Mon Aug 1 00:00:00 PDT 2011" format
         * OR
         * 		"Mon Aug 01 2011 00:00:00 GMT-0700 (Pacific Daylight Time)" format 
         */
        if (dateStr != null && !dateStr.trim().equalsIgnoreCase("")) {
        	boolean isDateValid = true;
        	sqlDate = TiredexUtils.getDateParamWGMT(dateStr);
	        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "shipDate = [" + sqlDate + ']');
        	isDateValid = (sqlDate != null);

            if (!isDateValid) {
	        	sqlDate = TiredexUtils.getDateParamWTimeZone(dateStr);
		        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "shipDate = [" + sqlDate + ']');
	        	isDateValid = (sqlDate != null);
            } // if !isDateValid
            
            if (!isDateValid) {
	        	sqlDate = TiredexUtils.getDateParamMDY(dateStr);
		        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "shipDate = [" + sqlDate + ']');
	        	isDateValid = (sqlDate != null);
            } // if !isDateValid

            if (!isDateValid) {
            	throw new TiredexAppException("Unable to parse date [" + dateStr + ']');
            }
            
        } // if startShipDateStr != null && ...
	}
    catch (Exception e) {
        TiredexLogger.logException(CLASS_NAME, METHOD_NAME, "", e);
        throw new TiredexUnhandledException(ErrorMessageInterface.REMOTE_REMOTE + ' ' + e);
    }
	
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");
	TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    returning shipDate = [" + sqlDate + ']');
	return sqlDate;
}
}
