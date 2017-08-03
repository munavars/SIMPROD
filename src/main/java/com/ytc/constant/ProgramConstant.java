package com.ytc.constant;

import java.util.Arrays;
import java.util.List;

public class ProgramConstant {
	public static final String INCLUDED = "1";
	public static final String EXCLUDED = "2";
	public static final String DATE_FORMAT = "MM/dd/yyyy";
	public static final String YES = "Yes";
	public static final String NO = "No";
	public static final String Y = "Y";
	public static final String N = "N";
	public static final String ZERO="0";
	public static final String ONE="1";
	
	public static final String TAG_VALUE_DELIMITER = "-";
	public static final String NAME_DELIMITER = " ";
	public static final String TAG_VALUE_DELIMITER_SPACE = " - ";
	
	public static final String ACTIVE_STATUS = "ACTIVE";
	public static final String APPROVED_STATUS = "APPROVED";
	public static final String IN_ACTIVE_STATUS = "IN ACTIVE";
	public static final String IN_PROGRESS_STATUS = "IN PROGRESS";
	public static final String PENDING_STATUS = "PENDING";
	public static final String WAITING_STATUS = "WAITING";
	public static final String REJECTED_STATUS = "REJECTED";
	
	public static final String CALCULATED_PROGRAM_TYPE = "CALCULATED PROGRAM";
	public static final String DDF_PROGRAM_TYPE = "DDF";
	public static final String COOP_PROGRAM_TYPE = "COOP";
	public static final String PRICING_FORM_TYPE = "Pricing Form";
	
	public static final String SAVE = "SAVE";
	public static final String SUBMIT = "SUBMIT";

	
	public static final String USER_LEVEL_1 = "1";
	public static final String USER_LEVEL_2 = "2";
	public static final String USER_LEVEL_3 = "3";
	
	public static final Integer EMP_HIERARCHY_1 = 1;
	public static final Integer EMP_HIERARCHY_2 = 2;
	public static final Integer EMP_HIERARCHY_3 = 3;
	public static final Integer EMP_HIERARCHY_4 = 4;
	public static final Integer EMP_HIERARCHY_5 = 5;
	
	public static final String PROGRAM_DESCRIPTION_NOT_FOUND = "Program Description not available";
	
	public static final String FORWARD_SLASH = "/";
	public static final String NO_LIMIT = "No Limit";
	
	public static final String OPERATOR_G = ">";
	public static final String OPERATOR_GE = ">=";
	public static final String OPERATOR_LE = "<=";
	
	public static final String BREAK_NEW_LINE_CODE = "&#013";
	
	public static final String COLON_WITH_SPACE = " : ";
	public static final String BLANK = "";
	public static final String STATUS_HISTORY_DATA_MESSAGE = "Status history details not available";
	
	public static final String COMMERCIAL = "COMMERCIAL";
	public static final String CONSUMER = "CONSUMER";
	public static final String OTR = "OTR";
	public static final List<Integer> COMMERCIAL_TAG_ID_LIST = Arrays.asList(5);
	public static final List<String> COMMERCIAL_TAG_VALUE_LIST =Arrays.asList("TBS");
	public static final List<Integer> CONSUMER_TAG_ID_LIST = Arrays.asList(5);
	public static final List<Integer> CONSUMER_EXCLUDE_TAG_ID_LIST = Arrays.asList(4);
	public static final List<String> CONSUMER_TAG_VALUE_LIST =Arrays.asList("A", "P", "HPT", "LTB", "LTR", "PCB", "PCR");
	public static final List<String> CONSUMER_EXCLUDE_TAG_VALUE_LIST =Arrays.asList("A", "P");
	public static final List<Integer> OTR_TAG_ID_LIST = Arrays.asList(5);
	public static final List<String> OTR_TAG_VALUE_LIST =Arrays.asList("ORL", "ORM", "ORS", "ORX", "OTL", "OTM", "OTR", "OTS", "OTX");
	
	public static final Integer MAX_END_RANGE = 99999999;
	
	public static final String PROGRAM_CREATER_ERROR = "Approval data does not exist. You are not authorized to create this program";
}
