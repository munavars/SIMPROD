package com.tiredex.yoko.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import com.tiredex.yoko.exceptions.TiredexAppException;
import com.tiredex.yoko.exceptions.TiredexUnhandledException;

public class LdapUtil {
	
    private static final String CLASS_NAME = LdapUtil.class.getSimpleName();
    
    private LdapContext ldapCtx = null;
    
    private static final String AD_USER_ID_KEY = "AD_USER_ID";
    private static final String AD_PASSWORD_KEY = "AD_PASSWORD";
    
    
    
    /**
     * This method is usually called when a non-Yokohama employee (such as a dealer or National Account customer) 
     * is using the website and needs access Active Directory info.  The method will try to authenticate into Active
     * Directory using a default application userid and password.
     * 
     * @throws TiredexAppException
     * @throws TiredexUnhandledException
     */
    private void doDefaultLogin() throws TiredexAppException, TiredexUnhandledException {
	    final String METHOD_NAME = "doDefaultLogin";

		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Begin method");

		try {
		    PropertyFileUtils props = new PropertyFileUtils(TiredexUtils.TIREDEX_UTILS_BUNDLE);

		    String defUserid = props.getProperty(AD_USER_ID_KEY);
		    String defPassword = props.getProperty(AD_PASSWORD_KEY);
		    
		    ldapCtx = authenticate(defUserid, defPassword);
	    }
//        catch (TiredexAppException e) {
//            TiredexLogger.logError(CLASS_NAME, METHOD_NAME, e.toString());
//            throw e;
//        }
        catch (TiredexUnhandledException e) {
            TiredexLogger.logError(CLASS_NAME, METHOD_NAME, e.toString());
            throw e;
        }
        catch (Exception e) {
            TiredexLogger.logException(CLASS_NAME, METHOD_NAME, ErrorMessageInterface.REMOTE_REMOTE, e);
            throw new TiredexUnhandledException(ErrorMessageInterface.REMOTE_REMOTE + ' ' + e);
        }

		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");
    }
    
    
    
	/**
	 * @param userid in the domain \ username format
	 * @param windowsPwd
	 * @return
	 */
	public boolean isValidYtcUser(String userid, String windowsPwd) {
	    final String METHOD_NAME = "isValidYtcUser";

		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Begin method");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    arguments:");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "         userid = [" + userid + ']');
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "         windowsPwd = [" + windowsPwd + ']');

        
        
		boolean isLegitUser = false;
		try {
			// If user id and password fail, exception will get thrown
			if (ldapCtx == null) {
	            TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "ctx == null, calling authenticate...");
				ldapCtx = authenticate(userid, windowsPwd);
			}

			isLegitUser = (ldapCtx != null);
		} 
		catch (Exception e) {
			TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Windows Active Directory authentication failed");
			TiredexLogger.logException(CLASS_NAME, METHOD_NAME, "Unhandled Exception caught", e);
		}
		finally {
			if (ldapCtx != null) {
				try {
	                ldapCtx.close();
                }
                catch (Exception e) {}
			}
		}
		
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    returning isLegitUser = [" + isLegitUser + ']');

		return isLegitUser;
	}
	
	
	
	/**
	 * This method logs into the Windows Active Directory and gets the user's direct reports.  This method assumes
	 * the isValidYtcUser method was successfully called.
	 * 
	 * @param userid in the domain \ username format
	 * @return
	 */
	public List<LdapRecord> getDirectReports(String userid) {
	    final String METHOD_NAME = "getDirectReports(1 arg)";

		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Begin method");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    arguments:");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "         userid = [" + userid + ']');

        Calendar startTime = Calendar.getInstance();

        
        
        List<LdapRecord> dirReps = getDirectReports(userid, ldapCtx, null);
        
        
        
    	Calendar endTime = Calendar.getInstance();

        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method.  Elapsed time [" + TiredexUtils.getElapsedTimeString(startTime, endTime) + ']');
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    returning list of dirReps:");
        TiredexLogger.logArrayListDetails(CLASS_NAME, METHOD_NAME, "        ", dirReps);
        
        return dirReps;
	}
	
	
	
	/**
	 * This method logs into the Windows Active Directory and gets the user's direct reports.
	 * @param userid in the domain \ username format
	 * @param windowsPwd
	 * @return List of direct reports
	 */
	public List<LdapRecord> getDirectReports(String userid, String windowsPwd) {
	    final String METHOD_NAME = "getDirectReports(2 args)";

		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Begin method");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    arguments:");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "         userid = [" + userid + ']');
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "         windowsPwd = [" + windowsPwd + ']');
        
        Calendar startTime = Calendar.getInstance();

        if (ldapCtx == null) {
            TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "ctx == null, calling authenticate...");
            ldapCtx = authenticate(userid, windowsPwd);
        }
        
        List<LdapRecord> dirReps = getDirectReports(userid, ldapCtx, null);		// ***** testing *****
        
//        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "***** testing *****   setting ytc user to robertmi...");		// ***** testing *****
//        List<LdapRecord> testReps = getDirectReports("ca\\robertmi", ctx, null);										// ***** testing *****
//        dirReps.addAll(testReps);																						// ***** testing *****
        
    	Calendar endTime = Calendar.getInstance();

        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method.  Elapsed time [" + TiredexUtils.getElapsedTimeString(startTime, endTime) + ']');
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    returning list of dirReps:");
        TiredexLogger.logArrayListDetails(CLASS_NAME, METHOD_NAME, "        ", dirReps);
        
        return dirReps;
	}
	
	/**
	 * This method recursively finds all the direct reports for the given userid.
	 * 
	 * @param userid String with the current user's login id needed to access the Active Directory; in the domain \ username format
	 * @param ctx LdapContext which has the context of the userid which is connected to the Active Directory
	 * @param searchForUser String in Active Directory LDAP format, such as 
	 * 		CN=Graziani\\, Alain
	 * and this we'll return Alain's direct reports.
	 * @return
	 */
	private List<LdapRecord> getDirectReports(String userid, LdapContext ctx, LdapRecord searchForUser) {
	    final String METHOD_NAME = "getDirectReports(3 args) userid = ["  + userid + ']';

		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Begin method");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    arguments:");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "         userid = [" + userid + ']');
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "         ctx = [" + ctx + ']');
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "         searchForUser = [" + searchForUser + ']');
		
        List<LdapRecord> dirReps = null;
		
        String searchFilter = "(&(objectClass=user)";

		try {
//			String searchFilter = "(&(objectClass=user))";
			if (userid != null && !userid.trim().equalsIgnoreCase("")) {
				// remove the domain (ex: ca\alaing the domain is ca)
				int pos = userid.indexOf("\\");
				String username = null;
				if (pos > 0) {
					username = userid.substring(pos + 1);
				}
				else {
					username = userid;
				}
				searchFilter += "(sAMAccountName=" + username + ')';
			}
			
			if (searchForUser != null && !searchForUser.getCnValue().trim().equalsIgnoreCase("")) {
				searchFilter += '(' + searchForUser.getCnValue().trim() + ')';
			}
			
			searchFilter += ')';
			
			
			
			String searchBase = "DC=CA, DC=NA, DC=YTC";
//	        String searchBase = "OU=CA Users, DC=CA, DC=NA, DC=YTC";
//			String searchBaseMexico = "OU=Mexico, DC=CA, DC=NA, DC=YTC";
			
			/*
			 * DC: Domain Component. DC objects represent the top of an LDAP tree that uses DNS to define its namespace. 
			 * Active Directory is an example of such an LDAP tree. The designator for an Active Directory domain with the 
			 * DNS name Company.com would be dc=Company,dc=com.
			 *  
			 * OU: Organizational Unit. OU objects act as containers that hold other objects. They provide structure to 
			 * the LDAP namespace. OUs are the only general-purpose container available to administrators in Active Directory. 
			 * An example OU name would be ou=Accounting.
			 *  
			 */

			SearchControls searchControl = new SearchControls();

//			returnedAtts = activeDirectoryAttribute;
//			searchControl.setReturningAttributes(returnedAtts);

			// Specify the search scope
			searchControl.setCountLimit(0);
			searchControl.setSearchScope(SearchControls.SUBTREE_SCOPE);
			
			String returnedAtts[] = { "name", "sn", "givenName", "samAccountName", "canonicalName", 
					"ou", "o", "organizationalStatus", "manager", "title",
					"msDS-UserAccountDisabled", "department", "userAccountControl", "directReports", "mailNickname", 
					"userPrincipalName", "mail",  "mobile", "telephoneNumber" };
			searchControl.setReturningAttributes(returnedAtts);

			TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "calling ctx.search; arguments:");
			TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    searchBase = [" + searchBase + ']');
			TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    searchFilter = [" + searchFilter + ']');
			TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    searchControl = [" + searchControl + ']');
			NamingEnumeration<SearchResult> results = ctx.search(searchBase, searchFilter, searchControl);
			
			dirReps = new ArrayList<LdapRecord>();
			boolean firstPerson = false;
			
			while (results != null && results.hasMore()) {
				SearchResult entry = results.next();
				if (entry.getName().length() > 0) {
					TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "entry.getName() = [" + entry.getName() + ']');
			        Attributes attrs = entry.getAttributes();
			        
			        
			        String cn = getCn(entry.getName());
			        if (searchForUser == null) {
			        	firstPerson = true;
				        searchForUser = new LdapRecord();
				        searchForUser.setCnValue(cn);
			        }
			        
			        Attribute at = attrs.get("name");
			        String name = (at != null) ? at.get().toString() : "";
			        searchForUser.setAcctName(name);
			        
			        at = attrs.get("givenName");
			        String givenName = (at != null) ? at.get().toString() : "";
			        searchForUser.setFirstName(givenName);
			        
			        at = attrs.get("sn");
			        String sn = (at != null) ? at.get().toString() : "";
			        searchForUser.setLastName(sn);
			        
			        at = attrs.get("mailNickname");
			        String mailNickname = (at != null) ? at.get().toString() : "";
			        searchForUser.setMailNickname(mailNickname);
			        
			        at = attrs.get("mail");
			        String email = (at != null) ? at.get().toString() : "";
			        searchForUser.setEmail(email);
			        
			        at = attrs.get("department");
			        String dept = (at != null) ? at.get().toString() : "";
			        searchForUser.setDepartment(dept);
			        
			        at = attrs.get("title");
			        String title = (at != null) ? at.get().toString() : "";
			        searchForUser.setTitle(title);
			        
			        at = attrs.get("manager");
			        String managerADFormat = (at != null) ? at.get().toString() : "";
			        searchForUser.setManagerInAD(managerADFormat);
			        
			        at = attrs.get("mobile");
			        String mobile = (at != null) ? at.get().toString() : "";
			        searchForUser.setMobilePhone(mobile);
			        
			        at = attrs.get("telephoneNumber");
			        String telephoneNumber = (at != null) ? at.get().toString() : "";
			        searchForUser.setOfficePhone(telephoneNumber);
			        
			        if (firstPerson) {
			        	dirReps.add(searchForUser);
			        }
			        
			        NamingEnumeration n = attrs.getAll();
			        while (n.hasMoreElements()) {
			        	Attribute a = (Attribute) n.next();
		        		String attrName = a.getID();
		        		
		        		
			        	for (int j = 0, size = a.size(); j < size; j++) {
			        		String attrVal = a.get(j).toString();
			        		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    attribute: " + attrName + '[' + j + "] = [" + attrVal + ']');
			        		if (attrName != null && attrName.equalsIgnoreCase("directReports")) {
			        			/*
			        			 * The attrVal will be something like: 
			        			 * 		CN=Baggett\, Mike,OU=OTR,OU=CA Users,DC=CA,DC=NA,DC=YTC
			        			 * but we only want to keep the CN portion.
			        			 * If the string contains "\," then go to the 2nd comma ","
			        			 */
						        LdapRecord ldapRec = new LdapRecord();
			        			String cnValue = getCn(attrVal);
			        			ldapRec.setCnValue(cnValue);
			        			dirReps.add(ldapRec);
			        			
			        			// check if this direct report also has direct reports
				        		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "recursively call getDirectReports for [" + cnValue + "] to see if he/she has any direct reports...");
			        			List<LdapRecord> subs = getDirectReports(null, ctx, ldapRec);
				        		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "back from getDirectReports for [" + cnValue + ']');

				        		if (subs != null && subs.size() > 0) {
				        			dirReps.addAll(subs);
			        			}
			        		} // if attrName.equalsIgnoreCase("directReports")
			        		
			        		
			        		
			        		if (attrName != null && attrName.equalsIgnoreCase("userAccountControl")) {
			        			List<ADUserAccountControlFlagEnum> flags = ADUserAccountControlFlagEnum.getFlags(attrVal);
				        		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    userAccountControl flags = [" + flags + ']');
			        			
			        		} // if attrName.equalsIgnoreCase("userAccountControl")
			        	} // for
			        } // while
				} // if entry.getName().length() > 0
			} // while
			
			
		} 
		catch (AuthenticationException e) {
			TiredexLogger.logException(CLASS_NAME, METHOD_NAME, "caught AuthenticationException exception", e);
			LdapErrorsEnum emsg = LdapErrorsEnum.getLdapErrorFromException(e.getMessage());
			TiredexLogger.logError(CLASS_NAME, METHOD_NAME, "LdapErrors = [" + emsg + ']');
		}
		catch (NamingException e) {
			TiredexLogger.logException(CLASS_NAME, METHOD_NAME, "caught NamingException exception", e);
			LdapErrorsEnum emsg = LdapErrorsEnum.getLdapErrorFromException(e.getMessage());
			TiredexLogger.logError(CLASS_NAME, METHOD_NAME, "LdapErrors = [" + emsg + ']');
		}
		catch (Exception e) {
			TiredexLogger.logException(CLASS_NAME, METHOD_NAME, "Unhandled Exception caught", e);
		}
		
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    returning directReports List of [" + searchFilter + "] :");
        TiredexLogger.logArrayListDetails(CLASS_NAME, METHOD_NAME, "        ", dirReps);
        
        return dirReps;
	}
	
	
	
	/**
	 * The attrVal will be something like: 
	 * 		CN=Baggett\, Mike,OU=OTR,OU=CA Users,DC=CA,DC=NA,DC=YTC
	 * but we only want to keep the CN portion.
	 * If the string contains "\," then go to the 2nd comma ","
	 * @param attrVal a string such as CN=Baggett\, Mike,OU=OTR,OU=CA Users,DC=CA,DC=NA,DC=YTC
	 * @return the CN portion
	 */
	private String getCn(String attrVal) {
		
		int bkSlashComma = attrVal.indexOf("\\,");
		int commaPos = -1;
		if (bkSlashComma > 0) {
			commaPos = attrVal.indexOf(",", bkSlashComma + 2);
		}
		else {
			commaPos = attrVal.indexOf(",");
		}
		String cnValue = attrVal.substring(0, commaPos);
		
		return cnValue;
	}



	/**
	 * @param userid
	 * @param windowsPwd
	 * @return
	 */
	private LdapContext authenticate(String userid, String windowsPwd) {
	    final String METHOD_NAME = "authenticate";

		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Begin method");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    arguments:");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "         userid = [" + userid + ']');
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "         windowsPwd = [" + windowsPwd + ']');

        
        
    	GlobalAddressListBundleUtils galbu = GlobalAddressListBundleUtils.getInstance();

		String ldapUrl = galbu.getServerURL();
		
		Hashtable<String, Object> env = new Hashtable<String, Object>(11);
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

		/* Specify host and port to use for directory service */
		env.put(Context.PROVIDER_URL, ldapUrl);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, userid);
		env.put(Context.SECURITY_CREDENTIALS, windowsPwd);

		
		LdapContext ctx = null;
		try {
			TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "calling InitialLdapContext to validate Windows Active Directory credentials. Env: ");
			TiredexLogger.logHashtableDetails(CLASS_NAME, METHOD_NAME, "   ", env);

			/*
			 * If user id and password fail, exception will get thrown at "new InitialLdapContext(env, null)"
			 * 
 			 * If you get an exception such as: 
			 * 	javax.naming.AuthenticationException: [LDAP: error code 49 - 80090308: LdapErr: DSID-0C0903A9, comment: AcceptSecurityContext error, data 701, v1db0]
			 * Check the "data" value in the list below:
			 * 	525		user not found
			 * 	52e		invalid credentials
			 * 	530		not permitted to logon at this time
			 *	531		not permitted to logon at this workstation
			 *	532		password expired
			 *	533		account disabled
			 *	701		account expired
			 *	773		user must reset password
			 *	775		user account locked 
			 */
			ctx = new InitialLdapContext(env, null);	

			TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Windows Active Directory authentication succeeded");
		} 
		catch (AuthenticationException e) {
			TiredexLogger.logException(CLASS_NAME, METHOD_NAME, "caught AuthenticationException exception", e);
			LdapErrorsEnum emsg = LdapErrorsEnum.getLdapErrorFromException(e.getMessage());
			TiredexLogger.logError(CLASS_NAME, METHOD_NAME, "LdapErrors = [" + emsg + ']');
		}
		catch (NamingException e) {
			TiredexLogger.logException(CLASS_NAME, METHOD_NAME, "caught NamingException exception", e);
			LdapErrorsEnum emsg = LdapErrorsEnum.getLdapErrorFromException(e.getMessage());
			TiredexLogger.logError(CLASS_NAME, METHOD_NAME, "LdapErrors = [" + emsg + ']');
		}
		catch (Exception e) {
			TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Windows Active Directory authentication failed");
			TiredexLogger.logException(CLASS_NAME, METHOD_NAME, "Unhandled Exception caught", e);
		}
		finally {
			if (ctx != null) {
				try {
	                ctx.close();
                }
                catch (Exception e) {}
			}
		}
		
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    returning LdapContext = [" + ctx + ']');

		return ctx;
	}



	/**
	 * This method LdapRecord for the given userid.
	 * 
	 * @param userid String with the current user's login id needed to access the Active Directory; in the domain \ username format
	 * @param ctx LdapContext which has the context of the userid which is connected to the Active Directory
	 * @param searchForUser String in Active Directory LDAP format, such as 
	 * 		CN=Graziani\\, Alain
	 * and this we'll return Alain's direct reports.
	 * @return
	 */
	public List<LdapRecord> getLdapRecord(String userid, /*LdapContext ctx, */LdapRecord searchForUser) {
	    final String METHOD_NAME = "getLdapRecord";

		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Begin method");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    arguments:");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "         userid = [" + userid + ']');
//        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "         ctx = [" + ctx + ']');
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "         searchForUser = [" + searchForUser + ']');
		
        List<LdapRecord> dirReps = null;
		
        String searchFilter = "(&(objectClass=user)";

		try {
//			String searchFilter = "(&(objectClass=user))";
			if (userid != null && !userid.trim().equalsIgnoreCase("")) {
				// remove the domain (ex: ca\alaing the domain is ca)
				int pos = userid.indexOf("\\");
				String username = null;
				if (pos > 0) {
					username = userid.substring(pos + 1);
				}
				else {
					username = userid;
				}
				searchFilter += "(sAMAccountName=" + username + ')';
			}
			
			if (searchForUser != null && !searchForUser.getCnValue().trim().equalsIgnoreCase("")) {
				searchFilter += '(' + searchForUser.getCnValue().trim() + ')';
			}
			
			searchFilter += ')';
			
			
			
			String searchBase = "DC=CA, DC=NA, DC=YTC";
//	        String searchBase = "OU=CA Users, DC=CA, DC=NA, DC=YTC";
//			String searchBaseMexico = "OU=Mexico, DC=CA, DC=NA, DC=YTC";
			
			/*
			 * DC: Domain Component. DC objects represent the top of an LDAP tree that uses DNS to define its namespace. 
			 * Active Directory is an example of such an LDAP tree. The designator for an Active Directory domain with the 
			 * DNS name Company.com would be dc=Company,dc=com.
			 *  
			 * OU: Organizational Unit. OU objects act as containers that hold other objects. They provide structure to 
			 * the LDAP namespace. OUs are the only general-purpose container available to administrators in Active Directory. 
			 * An example OU name would be ou=Accounting.
			 *  
			 */

			SearchControls searchControl = new SearchControls();

//			returnedAtts = activeDirectoryAttribute;
//			searchControl.setReturningAttributes(returnedAtts);

			// Specify the search scope
			searchControl.setCountLimit(0);
			searchControl.setSearchScope(SearchControls.SUBTREE_SCOPE);
			
			String returnedAtts[] = { "name", "sn", "givenName", "samAccountName", "canonicalName", 
					"ou", "o", "organizationalStatus", "manager", "title",
					"msDS-UserAccountDisabled", "department", "userAccountControl", "directReports", "mailNickname", 
					"userPrincipalName", "mail",  "mobile", "telephoneNumber" };
			searchControl.setReturningAttributes(returnedAtts);

			TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "calling ctx.search; arguments:");
			TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    searchBase = [" + searchBase + ']');
			TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    searchFilter = [" + searchFilter + ']');
			TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    searchControl = [" + searchControl + ']');
//			NamingEnumeration<SearchResult> results = ctx.search(searchBase, searchFilter, searchControl);

			if (ldapCtx == null) {
				TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "ldapCtx is null, calling doDefaultLogin");
				doDefaultLogin();
				TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "back from doDefaultLogin");
			}

			NamingEnumeration<SearchResult> results = null;
			if (ldapCtx != null) {
				results = ldapCtx.search(searchBase, searchFilter, searchControl);
			}
			else {
				TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "***** POSSIBLE BUG *****  ldapCtx is null and it shouldn't.  This suggests that a call to the "
						+ "authenticate method was not successful, therefore, LDAP access is not available.  No exceptions thrown.  Continuing...");
			}
			
			dirReps = new ArrayList<LdapRecord>();
			boolean firstPerson = false;
			
			while (results != null && results.hasMore()) {
				SearchResult entry = results.next();
				if (entry.getName().length() > 0) {
					TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "entry.getName() = [" + entry.getName() + ']');
			        Attributes attrs = entry.getAttributes();
			        
			        
			        String cn = getCn(entry.getName());
			        if (searchForUser == null) {
			        	firstPerson = true;
				        searchForUser = new LdapRecord();
				        searchForUser.setCnValue(cn);
			        }
			        
			        Attribute at = attrs.get("name");
			        String name = (at != null) ? at.get().toString() : "";
			        searchForUser.setAcctName(name);
			        
			        at = attrs.get("givenName");
			        String givenName = (at != null) ? at.get().toString() : "";
			        searchForUser.setFirstName(givenName);
			        
			        at = attrs.get("sn");
			        String sn = (at != null) ? at.get().toString() : "";
			        searchForUser.setLastName(sn);
			        
			        at = attrs.get("mailNickname");
			        String mailNickname = (at != null) ? at.get().toString() : "";
			        searchForUser.setMailNickname(mailNickname);
			        
			        at = attrs.get("mail");
			        String email = (at != null) ? at.get().toString() : "";
			        searchForUser.setEmail(email);
			        
			        at = attrs.get("department");
			        String dept = (at != null) ? at.get().toString() : "";
			        searchForUser.setDepartment(dept);
			        
			        at = attrs.get("title");
			        String title = (at != null) ? at.get().toString() : "";
			        searchForUser.setTitle(title);
			        
			        at = attrs.get("manager");
			        String managerADFormat = (at != null) ? at.get().toString() : "";
			        searchForUser.setManagerInAD(managerADFormat);
			        
			        at = attrs.get("mobile");
			        String mobile = (at != null) ? at.get().toString() : "";
			        searchForUser.setMobilePhone(mobile);
			        
			        at = attrs.get("telephoneNumber");
			        String telephoneNumber = (at != null) ? at.get().toString() : "";
			        searchForUser.setOfficePhone(telephoneNumber);
			        
			        if (firstPerson) {
			        	dirReps.add(searchForUser);
			        }
			        
			        NamingEnumeration n = attrs.getAll();
			        while (n.hasMoreElements()) {
			        	Attribute a = (Attribute) n.next();
		        		String attrName = a.getID();
		        		
		        		
			        	for (int j = 0, size = a.size(); j < size; j++) {
			        		String attrVal = a.get(j).toString();
			        		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    attribute: " + attrName + '[' + j + "] = [" + attrVal + ']');
//			        		if (attrName != null && attrName.equalsIgnoreCase("directReports")) {
//			        			/*
//			        			 * The attrVal will be something like: 
//			        			 * 		CN=Baggett\, Mike,OU=OTR,OU=CA Users,DC=CA,DC=NA,DC=YTC
//			        			 * but we only want to keep the CN portion.
//			        			 * If the string contains "\," then go to the 2nd comma ","
//			        			 */
//						        LdapRecord ldapRec = new LdapRecord();
//			        			String cnValue = getCn(attrVal);
//			        			ldapRec.setCnValue(cnValue);
//			        			dirReps.add(ldapRec);
//			        			
//			        			// check if this direct report also has direct reports
//				        		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "recursively call getDirectReports for [" + cnValue + "] to see if he/she has any direct reports...");
//			        			List<LdapRecord> subs = getDirectReports(null, ctx, ldapRec);
//				        		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "back from getDirectReports for [" + cnValue + ']');
//
//				        		if (subs != null && subs.size() > 0) {
//				        			dirReps.addAll(subs);
//			        			}
//			        		} // if attrName.equalsIgnoreCase("directReports")
			        		
			        		
			        		
			        		if (attrName != null && attrName.equalsIgnoreCase("userAccountControl")) {
			        			List<ADUserAccountControlFlagEnum> flags = ADUserAccountControlFlagEnum.getFlags(attrVal);
				        		TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    userAccountControl flags = [" + flags + ']');
			        			
			        		} // if attrName.equalsIgnoreCase("userAccountControl")
			        	} // for
			        } // while
				} // if entry.getName().length() > 0
			} // while
			
			
		} 
		catch (AuthenticationException e) {
			TiredexLogger.logException(CLASS_NAME, METHOD_NAME, "caught AuthenticationException exception", e);
			LdapErrorsEnum emsg = LdapErrorsEnum.getLdapErrorFromException(e.getMessage());
			TiredexLogger.logError(CLASS_NAME, METHOD_NAME, "LdapErrors = [" + emsg + ']');
		}
		catch (NamingException e) {
			TiredexLogger.logException(CLASS_NAME, METHOD_NAME, "caught NamingException exception", e);
			LdapErrorsEnum emsg = LdapErrorsEnum.getLdapErrorFromException(e.getMessage());
			TiredexLogger.logError(CLASS_NAME, METHOD_NAME, "LdapErrors = [" + emsg + ']');
		}
		catch (Exception e) {
			TiredexLogger.logException(CLASS_NAME, METHOD_NAME, "Unhandled Exception caught", e);
		}
		
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");
        TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    returning directReports List of [" + searchFilter + "] :");
        TiredexLogger.logArrayListDetails(CLASS_NAME, METHOD_NAME, "        ", dirReps);
        
        return dirReps;
	}
}
