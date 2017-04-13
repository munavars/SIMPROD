package com.tiredex.yoko.utils;

import com.tiredex.yoko.exceptions.TiredexUnhandledException;

//import com.ytc.activedirectory.exceptions.ProfileUnhandledException;

/**
 * @author Kei Chan
 * 
 *         This is an utility class to provide various timer functions.
 */
public class GlobalAddressListBundleUtils {
//	private static Logger logger = Logger
//			.getLogger(GlobalAddressListBundleUtils.class.getName());

	private static final String CLASS_NAME = GlobalAddressListBundleUtils.class.getSimpleName();

	private static String CUSTOMIZEDXMLFILENAME = null;
	private static String CUSTOMIZEDXMLFILEPATH = null;
	private String ActiveDirectoryURL = null;
//	private static String PATH = "";
//	private static String ActiveDirectoryUserName = null;
//	private static String ActiveDirectoryPassword = null;
	private static GlobalAddressListBundleUtils instance = null;
//	private static String SERVERURL = null;
//	private static boolean debugLogging = false;
	
	private static PropertyFileUtils propFile = new PropertyFileUtils("com.tiredex.yoko.utils.ActiveDirectoryBundle");

//	public void setDebuglogging (boolean value) {
//		debugLogging = value;
//	}
	
//	private String getPropertyValue(String key)
//			throws ProfileUnhandledException {
//
//		final String GLOBAL_ADDRESS_LIST_BUNDLE = "com.ytc.activedirectory.utils.globalAddressListBundle";
//
//		try {
//			PropertyResourceBundle configBundle = (PropertyResourceBundle) PropertyResourceBundle
//					.getBundle(GLOBAL_ADDRESS_LIST_BUNDLE);
//			return configBundle.getString(key);
//		} catch (MissingResourceException e) {
//			String msg = "Error in reading the " + key + " parameter from the "
//					+ GLOBAL_ADDRESS_LIST_BUNDLE
//					+ " property file.  MissingResourceException: " + e;
//			logger.error(msg);
//			throw new ProfileUnhandledException(msg);
//		} catch (Exception e) {
//			String msg = "Error in reading the " + key + " parameter from the "
//					+ GLOBAL_ADDRESS_LIST_BUNDLE + " property file.  Exception: " + e;
//			logger.error(msg);
//			throw new ProfileUnhandledException(msg);
//		}
//	}

	/**
	 * A utility class to provide functions related to CMS
	 * 
	 * @param serverurl
	 *            - the CMS URL
	 * @param path
	 *            - the directory to store the downloaded files
	 */
//	private GlobalAddressListBundleUtils(String serverurl, String path) {
//	    final String METHOD_NAME = "GlobalAddressListBundleUtils(serverurl, path)";
//
//		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "arguments");
//		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    serverurl = [" + serverurl + ']');
//		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    path = [" + path + ']');
//
//		if (path.length() > 0) {
//			PATH = path;
//		}
//		
//		SERVERURL = serverurl;
////		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Begin method.");
//	}

	
	
	private GlobalAddressListBundleUtils() {
	    final String METHOD_NAME = "GlobalAddressListBundleUtils";
	    
		String ActiveDirectoryURL_REF = "ACTIVEDIRECTORY_URL";
//		String ACTIVEDIRECTORY_USERNAME_REF = "ACTIVEDIRECTORY_USERNAME";
//		String ACTIVEDIRECTORY_PASSWORD_REF = "ACTIVEDIRECTORY_PASSWORD";
		String WORKING_XML_FILE_PATH_REF = "XMLFILEPATH";
		String WORKING_XML_FILE_NAME_REF = "XMLFILENAME";


		try {

			if (ActiveDirectoryURL == null) {
//				ActiveDirectoryURL = getPropertyValue(ActiveDirectoryURL_REF);  
				ActiveDirectoryURL = propFile.getProperty(ActiveDirectoryURL_REF);
//				if (debugLogging) logger.info(methodName+"retrieving " + ActiveDirectoryURL_REF
//				TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "retrieving " + ActiveDirectoryURL_REF
//						+ " from globalAddressListBundle.properties: "
//						+ ActiveDirectoryURL);
			}
//			if (ActiveDirectoryUserName == null) {
//				ActiveDirectoryUserName = getPropertyValue(ACTIVEDIRECTORY_USERNAME_REF);
//				ActiveDirectoryUserName = propFile.getProperty(ACTIVEDIRECTORY_USERNAME_REF);
//				TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "retrieving " + ACTIVEDIRECTORY_USERNAME_REF
//						+ " from globalAddressListBundle.properties: " + ActiveDirectoryUserName);
//			}
//			if (ActiveDirectoryPassword == null) {
//				ActiveDirectoryPassword = getPropertyValue(ACTIVEDIRECTORY_PASSWORD_REF);
//				ActiveDirectoryPassword = propFile.getProperty(ACTIVEDIRECTORY_PASSWORD_REF);
//				TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "retrieving " + ACTIVEDIRECTORY_PASSWORD_REF
//						+ " from globalAddressListBundle.properties: " + ActiveDirectoryPassword);
//			}

			if (CUSTOMIZEDXMLFILEPATH == null) {
//				CUSTOMIZEDXMLFILEPATH = getPropertyValue(WORKING_XML_FILE_PATH_REF);
				CUSTOMIZEDXMLFILEPATH = propFile.getProperty(WORKING_XML_FILE_PATH_REF);
//				TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "retrieving " + WORKING_XML_FILE_PATH_REF
//						+ " from globalAddressListBundle.properties: "
//						+ CUSTOMIZEDXMLFILEPATH);
			}

			if (CUSTOMIZEDXMLFILENAME == null) {
//				CUSTOMIZEDXMLFILENAME = getPropertyValue(WORKING_XML_FILE_NAME_REF);
				CUSTOMIZEDXMLFILENAME = propFile.getProperty(WORKING_XML_FILE_NAME_REF);
//				TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "retrieving " + WORKING_XML_FILE_NAME_REF
//						+ " from globalAddressListBundle.properties: "
//						+ CUSTOMIZEDXMLFILENAME);
			}
		} catch (TiredexUnhandledException e) {
			TiredexLogger.logError(CLASS_NAME, METHOD_NAME, "TiredexUnhandledException caught: " + e);
		} catch (Exception e) {
			TiredexLogger.logException(CLASS_NAME, METHOD_NAME, "Unhandled Exception: ", e);
		}
		
		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "SERVERURL: " + ActiveDirectoryURL);
		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method.");
	}

	
	
	public String getServerURL() {
//		String methodName = "Method getServerURL - ";
//		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "returning SERVERURL: " + ActiveDirectoryURL);
//		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method.");
		return ActiveDirectoryURL;
	}

	
	
	public void setXmlFilePath(String xmlfilepath) {
//		String methodName = "Method setXmlFilePath - ";
		CUSTOMIZEDXMLFILEPATH = xmlfilepath;
//		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "setting CUSTOMIZEDXMLFILEPATH: " + CUSTOMIZEDXMLFILEPATH);
//		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method.");
	}
	
	
	
	public String getXmlFilePath() {
//		String methodName = "Method getXmlFilePath - ";
//		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "returning CUSTOMIZEDXMLFILEPATH: " + CUSTOMIZEDXMLFILEPATH);
//		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method.");
		return CUSTOMIZEDXMLFILEPATH;
	}

	
	
	public void setXmlFileName(String xmlfilename) {
//		String methodName = "Method setXmlFileName - ";
		CUSTOMIZEDXMLFILENAME = xmlfilename;
//		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "setting CUSTOMIZEDXMLFILENAME: " + CUSTOMIZEDXMLFILENAME);
//		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method.");
	}
	
	
	
	public String getXmlFileName() {
//		String methodName = "Method getXmlFileName - ";
//		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "returning CUSTOMIZEDXMLFILENAME: " + CUSTOMIZEDXMLFILENAME);
//		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method.");
		return CUSTOMIZEDXMLFILENAME;
	}

	
	
//	public String getActiveDirectoryUserName() {
//		String methodName = "Method getActiveDirectoryUserName - ";
//		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "returning ActiveDirectoryUserName: " + ActiveDirectoryUserName);
//		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method.");
//		return ActiveDirectoryUserName;
//	}

	
	
//	public String getActiveDirectoryPassword() {
//		String methodName = "Method getActiveDirectoryPassword - ";
//		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "returning ActiveDirectoryPassword: " + ActiveDirectoryPassword);
//		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method.");
//		return ActiveDirectoryPassword;
//	}

	
	
	public static GlobalAddressListBundleUtils getInstance() {
	    final String METHOD_NAME = "getInstance";

		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Begin method");

		try {
			if (instance == null) {
				instance = new GlobalAddressListBundleUtils();
			} else {
				TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Reusing existing instance.");
			}
		} catch (Exception e) {
			TiredexLogger.logException(CLASS_NAME, METHOD_NAME, "Unhandled Exception caught", e);
		}

		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method.");

		return instance;
	}

}
