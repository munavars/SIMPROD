package com.tiredex.yoko.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * This enum class contains the userAccountControl flags which are used in Microsoft Active Directory.  The flags are
 * returned when searching for the "userAccountControl" attributes.
 * 
	  ADS_UF_SCRIPT                                  = 1,        // 0x1<br>
	  ADS_UF_ACCOUNTDISABLE                          = 2,        // 0x2<br>
	  ADS_UF_HOMEDIR_REQUIRED                        = 8,        // 0x8<br>
	  ADS_UF_LOCKOUT                                 = 16,       // 0x10<br>
	  ADS_UF_PASSWD_NOTREQD                          = 32,       // 0x20<br>
	  ADS_UF_PASSWD_CANT_CHANGE                      = 64,       // 0x40<br>
	  ADS_UF_ENCRYPTED_TEXT_PASSWORD_ALLOWED         = 128,      // 0x80<br>
	  ADS_UF_TEMP_DUPLICATE_ACCOUNT                  = 256,      // 0x100<br>
	  ADS_UF_NORMAL_ACCOUNT                          = 512,      // 0x200<br>
	  ADS_UF_INTERDOMAIN_TRUST_ACCOUNT               = 2048,     // 0x800<br>
	  ADS_UF_WORKSTATION_TRUST_ACCOUNT               = 4096,     // 0x1000<br>
	  ADS_UF_SERVER_TRUST_ACCOUNT                    = 8192,     // 0x2000<br>
	  ADS_UF_DONT_EXPIRE_PASSWD                      = 65536,    // 0x10000<br>
	  ADS_UF_MNS_LOGON_ACCOUNT                       = 131072,   // 0x20000<br>
	  ADS_UF_SMARTCARD_REQUIRED                      = 262144,   // 0x40000<br>
	  ADS_UF_TRUSTED_FOR_DELEGATION                  = 524288,   // 0x80000<br>
	  ADS_UF_NOT_DELEGATED                           = 1048576,  // 0x100000<br>
	  ADS_UF_USE_DES_KEY_ONLY                        = 2097152,  // 0x200000<br>
	  ADS_UF_DONT_REQUIRE_PREAUTH                    = 4194304,  // 0x400000<br>
	  ADS_UF_PASSWORD_EXPIRED                        = 8388608,  // 0x800000<br>
	  ADS_UF_TRUSTED_TO_AUTHENTICATE_FOR_DELEGATION  = 16777216  // 0x1000000<br>
 *
 * @author AlainG
 *
 */
public enum ADUserAccountControlFlagEnum {
	
	ADS_UF_SCRIPT                                 (1),
	ADS_UF_ACCOUNTDISABLE                         (2),
	ADS_UF_HOMEDIR_REQUIRED                       (8),
	ADS_UF_LOCKOUT                                (16),
	ADS_UF_PASSWD_NOTREQD                         (32),
	ADS_UF_PASSWD_CANT_CHANGE                     (64),
	ADS_UF_ENCRYPTED_TEXT_PASSWORD_ALLOWED        (128),
	ADS_UF_TEMP_DUPLICATE_ACCOUNT                 (256),
	ADS_UF_NORMAL_ACCOUNT                         (512),
	ADS_UF_INTERDOMAIN_TRUST_ACCOUNT              (2048),
	ADS_UF_WORKSTATION_TRUST_ACCOUNT              (4096),
	ADS_UF_SERVER_TRUST_ACCOUNT                   (8192),
	ADS_UF_DONT_EXPIRE_PASSWD                     (65536),
	ADS_UF_MNS_LOGON_ACCOUNT                      (131072),
	ADS_UF_SMARTCARD_REQUIRED                     (262144),
	ADS_UF_TRUSTED_FOR_DELEGATION                 (524288),
	ADS_UF_NOT_DELEGATED                          (1048576),
	ADS_UF_USE_DES_KEY_ONLY                       (2097152),
	ADS_UF_DONT_REQUIRE_PREAUTH                   (4194304),
	ADS_UF_PASSWORD_EXPIRED                       (8388608),
	ADS_UF_TRUSTED_TO_AUTHENTICATE_FOR_DELEGATION (16777216);

	
	
	private int flag;
	
	private ADUserAccountControlFlagEnum(int aFlag) {
		flag = aFlag;
	}

	
	
	public int getFlag() {
    	return flag;
	}
	
	
	
	/**
	 * @param userAcctCtrlValue the int value which will be converted to a List of ADUserAccountControlFlagEnum
	 * @return The List of ADUserAccountControlFlagEnum
	 */
	public static List<ADUserAccountControlFlagEnum> getFlags(int userAcctCtrlValue) {
		
		List<ADUserAccountControlFlagEnum> results = new ArrayList<ADUserAccountControlFlagEnum>();
		
		if (userAcctCtrlValue > 0) {
			for (ADUserAccountControlFlagEnum s : ADUserAccountControlFlagEnum.values()) {
				if ((s.getFlag() & userAcctCtrlValue) != 0) {
					results.add(s);
				}
			} // for
		} // if
		
		return results;
	}
	
	
	
	/**
	 * @param userAcctCtrlStr the String value which will be converted to a List of ADUserAccountControlFlagEnum
	 * @return The List of ADUserAccountControlFlagEnum
	 */
	public static List<ADUserAccountControlFlagEnum> getFlags(String userAcctCtrlStr) {
		List<ADUserAccountControlFlagEnum> results = null;
		
		if (userAcctCtrlStr != null) {
			String ctrl = userAcctCtrlStr.trim();
			if (!ctrl.equalsIgnoreCase("")) {
				try {
					int flags = Integer.valueOf(ctrl).intValue();
					results = getFlags(flags);
				}
				catch (Exception e) {}
			}
		}
		
		return results;
	}
	
	
	
	/**
	 * This is a convenience method to see if the given flagToCheck is set in userAcctCtrlValue
	 * @param flagToCheck
	 * @param userAcctCtrlValue
	 * @return true flagToCheck is set in userAcctCtrlValue
	 */
	public static boolean isFlagSet (ADUserAccountControlFlagEnum flagToCheck, int userAcctCtrlValue) {
		boolean isSet = false;
		if (flagToCheck != null && userAcctCtrlValue > 0) {
			isSet = ((flagToCheck.getFlag() & userAcctCtrlValue) != 0);
		}
		return isSet;
	}
	
	
	
	/**
	 * This is a convenience method to see if the given flagToCheck is set in userAcctCtrlValue
	 * @param flagToCheck
	 * @param userAcctCtrlValue
	 * @return true flagToCheck is set in userAcctCtrlValue
	 */
	public static boolean isFlagSet (ADUserAccountControlFlagEnum flagToCheck, String userAcctCtrlValue) {
		boolean isSet = false;
		if (flagToCheck != null && userAcctCtrlValue != null) {
			String val = userAcctCtrlValue.trim();
			if (!val.equalsIgnoreCase("")) {
				try {
					int flags = Integer.valueOf(val).intValue();
					isSet = isFlagSet(flagToCheck, flags);
				}
				catch (Exception e) {}
			}
		}
		return isSet;
	}
}
