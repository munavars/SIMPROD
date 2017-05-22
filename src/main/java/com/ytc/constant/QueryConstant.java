package com.ytc.constant;

public class QueryConstant {
	public static final String TAG_VALUE_LIST_BY_TAG_ID = "SELECT DISTINCT %s AS DIS_VALUE FROM %s";
	
	public static final String TAG_VALUE_LIST_ORDER_BY_CLAUSE = " ORDER BY DIS_VALUE ASC";	

	//public static final String PROGRAM_LIST="select * from PROGRAM_DETAIL where PGM_HDR_ID in(select ID from PROGRAM_HEADER where CUSTOMER_ID in (:custId))and STATUS_ID in (:status)";
	
	//public static final String PROGRAM_LIST_TOP="select top (:count) PROGRAM_DETAIL.* from PROGRAM_DETAIL, PROGRAM_HEADER where PROGRAM_DETAIL.PGM_HDR_ID=PROGRAM_HEADER.ID and PROGRAM_HEADER.CUSTOMER_ID in (:custId) and PROGRAM_DETAIL.STATUS_ID in (:status)";
	
	public static final String PROGRAM_LIST_FILTER="SELECT * FROM(select PROGRAM_DETAIL.*, ROW_NUMBER() OVER (ORDER BY PROGRAM_DETAIL.id) as row from PROGRAM_DETAIL, PROGRAM_HEADER where PROGRAM_DETAIL.PGM_HDR_ID=PROGRAM_HEADER.ID and PROGRAM_HEADER.CUSTOMER_ID in (:custId) and PROGRAM_DETAIL.STATUS_ID in (:status))a WHERE a.row > :start and a.row <= :end";
	
	public static final String PROGRAM_LIST="select PROGRAM_DETAIL.* from PROGRAM_DETAIL, PROGRAM_HEADER where PROGRAM_DETAIL.PGM_HDR_ID=PROGRAM_HEADER.ID and PROGRAM_HEADER.CUSTOMER_ID in (:custId) and PROGRAM_DETAIL.STATUS_ID in (:status)";
	
	public static final String PROGRAM_LIST_COUNT="select count(1) from PROGRAM_DETAIL, PROGRAM_HEADER where PROGRAM_DETAIL.PGM_HDR_ID=PROGRAM_HEADER.ID and PROGRAM_HEADER.CUSTOMER_ID in (:custId) and PROGRAM_DETAIL.STATUS_ID in (:status)";
	
	//public static final String PROGRAM_LIST_ALL_TOP="select top (:count) * from PROGRAM_DETAIL where STATUS_ID in (:status)";
	
	public static final String PROGRAM_LIST_ALL_FILTER="SELECT * FROM(select *,ROW_NUMBER() OVER (ORDER BY PROGRAM_DETAIL.id) as row from PROGRAM_DETAIL where STATUS_ID in (:status))a WHERE a.row > :start and a.row <= :end";
	
	public static final String PROGRAM_LIST_ALL="select * from PROGRAM_DETAIL where STATUS_ID in (:status)";
	
	public static final String PROGRAM_LIST_ALL_COUNT="select count(1) from PROGRAM_DETAIL where STATUS_ID in (:status)";
	
	public static final String CUSTOMER_LIST="select * from PROGRAM_HEADER where REQUEST_ID in (:requestId) and STATUS_ID=2";
	
	public static final String EMPLOYEE_HIER_LIST= "SELECT BASE_EMP_ID FROM EMPLOYEE_HIERARCHY where BASE_EMP_ID = :loginId or LVL1_EMP_ID = :loginId or LVL2_EMP_ID = :loginId or LVL3_EMP_ID = :loginId or LVL4_EMP_ID = :loginId or LVL5_EMP_ID = :loginId";
	
	public static final String CUSTOMER_LIST_MGR="select ID from CUSTOMER where ACCOUNT_MANAGER in(:userId)";

}
