/*
 * Created on Mar 22, 2012
 *
 * $History: PropertyFileUtils.java $
 * 
 * *****************  Version 2  *****************
 * User: Alaing       Date: 7/20/12    Time: 2:52p
 * Updated in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * Improve performance.
 * 
 * *****************  Version 1  *****************
 * User: Alaing       Date: 5/31/12    Time: 4:24p
 * Created in $/user interface code/websphere/appserver/classes/com/tiredex/yoko/utils
 * SSR 3398: add support for orders submitted through the web service.
 * 
 * 
 * 
 */
package com.tiredex.yoko.utils;

import java.io.Serializable;
import java.util.PropertyResourceBundle;

import com.tiredex.yoko.exceptions.TiredexUnhandledException;

/**
 * This class provides convenient methods for accessing the properties defined in a file.
 * 
 * @author AlainG
 * @version $Revision: 3 $   $Date: 4/16/15 $
 *
 */
public class PropertyFileUtils implements Serializable {

    private static final String CLASS_NAME = PropertyFileUtils.class.getSimpleName();
	
    private static final String CONFIG_MGMT_VERSION = "$Revision: 3 $";
    // use SourceSafe revision number for the serialVersionUID
    private static final int firstSpace = CONFIG_MGMT_VERSION.indexOf(' ');
    private static final int lastSpace = CONFIG_MGMT_VERSION.lastIndexOf(' ');
    static final long serialVersionUID = Integer.parseInt(CONFIG_MGMT_VERSION.substring(firstSpace + 1, lastSpace));

    
    
	private String propFileName = null;
	
	
	
	public PropertyFileUtils(String aPropFileName) {
		super();
	    TiredexLogger.logMessage(CLASS_NAME, "constructor", "class instatiated with argument aPropFileName = [" + aPropFileName + ']');
		setPropFileName(aPropFileName);
	}
	
	
	
	/**
	 * @return Returns the propFileName.
	 */
	public String getPropFileName() {
		return propFileName;
	}
	/**
	 * @param propFileName The propFileName to set.
	 */
	public void setPropFileName(String aPropFileName) {
		this.propFileName = aPropFileName;
	}



	/**
	 * This method returns the value of the property which is in the property file.  As of Java SE 6,
	 * the property file will be reloaded each time this method is called thus allowing for more
	 * realtime updates to the application.  If the property is not found, the application environment
	 * (APP_ENVIRONMENT_PROP_KEY) will be prefixed to find the property value.
	 * 
	 * @param propertyName to find in the propFileName.
	 * @return the value associated with the property
	 * @throws TiredexUnhandledException
	 */
	public String getProperty(String propertyName) throws TiredexUnhandledException {
		final String METHOD_NAME = "getProperty";
		
		String propValue = null;
		boolean isFileFound = false;

		try {
			PropertyResourceBundle.clearCache();
			
    	    isFileFound = false;
    	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "retrieving property resource bundle file [" + propFileName + ']');
    		PropertyResourceBundle configBundle = (PropertyResourceBundle) PropertyResourceBundle.getBundle(propFileName);
    	    isFileFound = true;

    	    try {
	    	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "retrieving property [" + propertyName + ']');
				propValue = configBundle.getString(propertyName);
	    	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, propertyName + " = [" + propValue +']');
    	    }
    	    catch (Exception e2) {
	    	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "property [" + propertyName + "] not found.  We'll try to find the environment-specific copy of this property.");
    	    }
    	    
    	    
    		if (propValue == null) {
    			// If the code reaches this point, the key was not found, so we'll try to find the key with the prefix.
    			String envPrefix = TiredexUtils.getAppEnvironment();
    			String envKey = envPrefix + '_' + propertyName;
    			try {
    	    	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "retrieving property [" + envKey + ']');
    				propValue = configBundle.getString(envKey); 
    	    	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, envKey + " = [" + propValue +']');
    			}
    			catch (Exception e) {
    				String msg = METHOD_NAME + "Error in reading the " + propertyName + " and " + envKey + " parameter from the " + propFileName + " property file : " + e;
    				throw new TiredexUnhandledException(msg);
    			}
    		}
    		
		}
		catch(Exception e) {
			String msg = "Error in reading the " + propertyName + " parameter from the " + propFileName + " property file : " + e;
			if (isFileFound) {
				TiredexLogger.logError(CLASS_NAME, METHOD_NAME, "property [" + propertyName + "] not found in file");
			}
			else {
				TiredexLogger.logError(CLASS_NAME, METHOD_NAME, "property file [" + propFileName + "] not found");
			}
			TiredexLogger.logException(CLASS_NAME, METHOD_NAME, msg, e);
			throw new TiredexUnhandledException(msg);
		}

	    return propValue; 
	}
	
	
	
	/**
	 * This is a convenience method to see if a property has a true or false value.  This method is safe to use in condition
	 * statements and will not throw NullPointerExceptions if there is a problem getting the value of the property.
	 * 
	 * @param propertyName
	 * @return TRUE only if the value of the given property is a value of "true" (excluding quotes) in the property 
	 * file.  Otherwise, FALSE is returned.
	 */
	public boolean isPropertyEnabled(String propertyName) {
        final String METHOD_NAME = "isPropertyEnabled";

        boolean isEnabled = false;
        try {
        	String enableStr = getProperty(propertyName);
			TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, propertyName + " = [" + enableStr + ']');
			isEnabled = enableStr != null && enableStr.trim().equalsIgnoreCase("true"); 
        }
        catch (Exception e) {
			TiredexLogger.logError(CLASS_NAME, METHOD_NAME, "Exception caught but NOT re-thrown.  Continuing and returning a boolean value of FALSE.");
        	isEnabled = false;
        }

        return isEnabled;
	}
}
