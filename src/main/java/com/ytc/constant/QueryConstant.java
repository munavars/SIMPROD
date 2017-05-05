package com.ytc.constant;

public class QueryConstant {
	public static final String TAG_VALUE_LIST_BY_TAG_ID = "SELECT DISTINCT %s AS DIS_VALUE FROM %s";
	
	public static final String TAG_VALUE_LIST_ORDER_BY_CLAUSE = " ORDER BY DIS_VALUE ASC";
	
	public static final String PROGRAM_LIST="select * from PROGRAM_DETAIL where PGM_HDR_ID in(select ID from PROGRAM_HEADER where CUSTOMER_ID=:custId)and STATUS_ID in (:status)";
	
}
