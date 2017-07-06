package com.ytc.constant;

public class QueryConstant {
	public static final String TAG_VALUE_LIST_BY_TAG_ID = "SELECT DISTINCT %s AS DIS_VALUE FROM %s";
	
	public static final String TAG_VALUE_CUSTOMER_WHERE_CLAUSE = " WHERE billToNumber IN (SELECT customerNumber from DalCustomer where accountManager = %s)";
	
	public static final String TAG_VALUE_CUSTOMER_PRICING_WHERE_CLAUSE = " WHERE billToNumber IN ('%s')";
	
	public static final String TAG_VALUE_LIST_ORDER_BY_CLAUSE = " ORDER BY DIS_VALUE ASC";	

	public static final String PROGRAM_LIST="select * from PROGRAM_DETAIL where PGM_HDR_ID in(select ID from PROGRAM_HEADER where CUSTOMER_ID in (:custId))and STATUS_ID in (:status) and PGM_END_DATE>(DATEADD(YEAR, -2,SYSDATETIME()))";
	
	public static final String PROGRAM_LIST_ALL="select * from PROGRAM_DETAIL where STATUS_ID in (:status) and PGM_END_DATE>(DATEADD(YEAR, -2,SYSDATETIME()))";
	
	public static final String CUSTOMER_LIST="select * from PROGRAM_DETAIL where PGM_HDR_ID in (select ID from PROGRAM_HEADER where REQUEST_ID in (:requestId) and STATUS_ID in (1, 2) ) and STATUS_ID in (1, 2) and PGM_END_DATE>(DATEADD(YEAR, -2,SYSDATETIME()))";
		
	public static final String PRICING_LIST="select * from PRICING_HEADER where CREATED_BY in (:requestId) and STATUS in (1, 2, 3) ";
	
	public static final String TBP_CUSTOMER_LIST="select * from PROGRAM_DETAIL where STATUS_ID in (1, 2) and PGM_END_DATE>(DATEADD(YEAR, -2,SYSDATETIME()))";
	
	public static final String TBP_CUSTOMER_PRICING_LIST="select * from PRICING_HEADER where STATUS in (1, 2)";
	
	public static final String EMPLOYEE_HIER_LIST= "SELECT BASE_EMP_ID FROM EMPLOYEE_HIERARCHY where BASE_EMP_ID = :loginId or LVL1_EMP_ID = :loginId or LVL2_EMP_ID = :loginId or LVL3_EMP_ID = :loginId or LVL4_EMP_ID = :loginId or LVL5_EMP_ID = :loginId";
	
	public static final String CUSTOMER_LIST_MGR="select ID from CUSTOMER where ACCOUNT_MANAGER in(:userId)";

	public static final String TBP_QUERY = "select DISTINCT EMP_ID from WORKFLOW_MATRIX where id in ("
											+"select Max(id) from WORKFLOW_MATRIX where COMMENTS = 'TBP' group by SUBJECT_AREA, BUSINESS_UNIT)";
	
	public static final String NEW_CUSTOMER_QUERY = "SELECT * FROM CUSTOMER WHERE ID NOT IN (SELECT CUSTOMER_ID from PROGRAM_HEADER) ";
	
	public static final String NEW_CUSTOMER_ACCOUNT_MANAGER_CHECK = " AND ACCOUNT_MANAGER in (:userIdList)";
	
	//public static final String PRICING_LIST_P = "select * from NETDOWN_P where [Bill-To No] in (select CUSTOMER_NUMBER from CUSTOMER where ACCOUNT_MANAGER in (:empId)) and [Base Price] is not null and  [Base Price]>0";
	
	public static final String PRICING_LIST_P = "select N.* from NETDOWN_P N, PART_MASTER P where N.[Bill-To No] in (select CUSTOMER_NUMBER from CUSTOMER where ACCOUNT_MANAGER in (:empId))  and N.[Part No]=p.PART_NUMBER and N.[Base Price] is not null and  N.[Base Price]>0 and (P.STATUS='A' or P.STATUS='B')";
	
	public static final String PRICING_LIST_T = "select N.* from NETDOWN_T N, PART_MASTER P where N.[Bill-To No] in (select CUSTOMER_NUMBER from CUSTOMER where ACCOUNT_MANAGER in (:empId))  and N.[Part No]=p.PART_NUMBER and N.[Base Price] is not null and  N.[Base Price]>0 and (P.STATUS='A' or P.STATUS='B')";
	
	//public static final String TBP_USERS="SELECT * FROM TBP_USER where EMP_ID in (:empId)";
	
	public static final String TBP_USERS="select o from DalTbpUser o where o.empId in (:empId)";
}
