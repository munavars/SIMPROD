package com.ytc.constant;

public class QueryConstant {
	public static final String TAG_VALUE_LIST_BY_TAG_ID = "SELECT DISTINCT %s AS DIS_VALUE FROM %s";
	
	public static final String TAG_VALUE_CUSTOMER_WHERE_CLAUSE = " WHERE billToNumber IN (SELECT customerNumber from DalCustomer where accountManager = %s)";
	
	public static final String TAG_VALUE_CUSTOMER_PRICING_WHERE_CLAUSE = " WHERE billToNumber IN ('%s')";
	
	public static final String TAG_VALUE_LIST_ORDER_BY_CLAUSE = " ORDER BY DIS_VALUE ASC";	

	public static final String PROGRAM_LIST="select * from PROGRAM_DETAIL where PGM_HDR_ID in(select ID from PROGRAM_HEADER where CUSTOMER_ID in (:custId))and STATUS_ID in (:status) and PGM_END_DATE>(DATEADD(YEAR, -2,SYSDATETIME()))";
	
	public static final String PROGRAM_LIST_ALL="select * from PROGRAM_DETAIL where STATUS_ID in (:status) and PGM_END_DATE>(DATEADD(YEAR, -2,SYSDATETIME()))";
	
	public static final String CUSTOMER_LIST="select * from PROGRAM_DETAIL where PGM_HDR_ID in (select ID from PROGRAM_HEADER where REQUEST_ID in (:requestId) and STATUS_ID in (1, 2, 3) ) and STATUS_ID in (1, 2, 3) and PGM_END_DATE>(DATEADD(YEAR, -2,SYSDATETIME()))";
		
	public static final String PRICING_LIST="select * from PRICING_HEADER where CREATED_BY in (:requestId) and STATUS in (1, 2, 3) ";
	
	public static final String TBP_CUSTOMER_LIST="select * from PROGRAM_DETAIL where STATUS_ID in (1, 2) and PGM_END_DATE>(DATEADD(YEAR, -2,SYSDATETIME()))";
	
	public static final String TBP_CUSTOMER_PRICING_LIST="select * from PRICING_HEADER where STATUS in (1, 2)";
	
	public static final String EMPLOYEE_HIER_LIST= "SELECT BASE_EMP_ID FROM EMPLOYEE_HIERARCHY where BASE_EMP_ID = :loginId or LVL1_EMP_ID = :loginId or LVL2_EMP_ID = :loginId or LVL3_EMP_ID = :loginId or LVL4_EMP_ID = :loginId or LVL5_EMP_ID = :loginId";
	
	public static final String SIM_EMPLOYEE_HIER_LIST= "SELECT LVL1_EMP_ID FROM SIM_EMPLOYEE_HIERARCHY where (LVL1_EMP_ID = :loginId or LVL2_EMP_ID = :loginId or LVL3_EMP_ID = :loginId or LVL4_EMP_ID = :loginId or LVL5_EMP_ID = :loginId) and LVL1_EMP_ID is not NULL";
	
	public static final String CUSTOMER_LIST_MGR="select ID from CUSTOMER where ACCOUNT_MANAGER in(:userId)";

	public static final String TBP_QUERY = "select DISTINCT EMP_ID from WORKFLOW_MATRIX where id in ("
											+"select Max(id) from WORKFLOW_MATRIX where COMMENTS = 'TBP' group by SUBJECT_AREA, BUSINESS_UNIT)";
	
	public static final String NEW_CUSTOMER_QUERY = "SELECT * FROM CUSTOMER WHERE ID NOT IN (SELECT CUSTOMER_ID from PROGRAM_HEADER) ";
	
	public static final String NEW_CUSTOMER_ACCOUNT_MANAGER_CHECK = " AND ACCOUNT_MANAGER in (:userIdList)";
	
	//public static final String PRICING_LIST_P = "select * from NETDOWN_P where [Bill-To No] in (select CUSTOMER_NUMBER from CUSTOMER where ACCOUNT_MANAGER in (:empId)) and [Base Price] is not null and  [Base Price]>0";
	
	public static final String PRICING_LIST_P = "select N.* from NETDOWN_P N, PART_MASTER P where N.[Bill-To No] in (select CUSTOMER_NUMBER from CUSTOMER where ACCOUNT_MANAGER in (:empId) AND ID in (:customerId) )  and N.[Part No]=p.PART_NUMBER and N.[Base Price] is not null and  N.[Base Price]>0 and (P.STATUS='A' or P.STATUS='B')";
	
	public static final String PRICING_LIST_T = "select N.* from NETDOWN_T N, PART_MASTER P where N.[Bill-To No] in (select CUSTOMER_NUMBER from CUSTOMER where ACCOUNT_MANAGER in (:empId) AND ID in (:customerId) )  and N.[Part No]=p.PART_NUMBER and N.[Base Price] is not null and  N.[Base Price]>0 and (P.STATUS='A' or P.STATUS='B')";
	
	public static final String TBP_PRICING_LIST_P = "select N.* from NETDOWN_P N, PART_MASTER P where N.[Bill-To No] in "
													+ "	(select CUSTOMER_NUMBER from CUSTOMER )  "
													+ "and N.[Part No]=p.PART_NUMBER and N.[Base Price] is not null and  "
													+ "N.[Base Price]>0 and (P.STATUS='A' or P.STATUS='B')";
	
	public static final String TBP_PRICING_LIST_T = "select N.* from NETDOWN_T N, PART_MASTER P where N.[Bill-To No] in "
											+ "(select CUSTOMER_NUMBER from CUSTOMER )  and N.[Part No]=p.PART_NUMBER and N.[Base Price] is not null and  "
											+ "N.[Base Price]>0 and (P.STATUS='A' or P.STATUS='B')";
	
	//public static final String TBP_USERS="SELECT * FROM TBP_USER where EMP_ID in (:empId)";
	
	public static final String TBP_USERS="select o from DalTbpUser o where o.empId in (:empId)";
	
	public static final String IS_TBP_USERS="SELECT EMP_ID FROM TBP_USER WHERE EMP_ID IN (:empId)";

	public static final String CCM_REPORT="SELECT CUST.BU AS BUSINESS_UNIT\n"+
			", HIER.LVL3_EMP_NAME AS ZONE_MGR\n"+
			", HIER.LVL1_EMP_NAME AS ACCT_MGR\n"+
			", FREQ.FREQUENCY AS FREQENCY\n"+
			", PGM_DTL.ID AS PROGRAM_ID\n"+
			", PGM_MAST.PROGRAM AS PGM_NAME\n"+
			", PGM_DTL.SHORT_DESC AS PGM_DESC -- BUBBLE NOTE\n"+
			", ACCRUAL.PAID_BASED_ON\n"+
			", CASE WHEN PGM_DTL.IS_TIERED = 0 THEN \'No\' ELSE \'Yes\' END AS GUARANTEE\n"+
			", ACCRUAL.ACCRUAL_AMOUNT AS AMOUNT\n"+
			", ACCRUAL.ACCRUAL_TYPE AS TYPE\n"+
			", SUM(ACCRUAL.ACCRUED_CREDIT) AS CREDIT_ACCRUED\n"+
			", SHIP_TO.CORP_NUMBER AS BILL_TO_NO\n"+
			", SHIP_TO.CORP_NAME AS BILL_TO_NAME\n"+
			", SUM(ACCRUAL.PAID_INCL_UNITS_SOLD - ACCRUAL.PAID_EXCL_UNITS_SOLD) AS UNITS\n"+
			", SUM(ACCRUAL.PAID_INCL_BONUSABLE_UNITS - ACCRUAL.PAID_EXCL_BONUSABLE_UNITS) AS BONUSABLE_UNITS\n"+
			", SUM(ACCRUAL.PAID_INCL_NAD_UNITS - ACCRUAL.PAID_EXCL_NAD_UNITS) AS NAD_UNITS\n"+
			", SUM((ACCRUAL.PAID_INCL_UNITS_SOLD - ACCRUAL.PAID_EXCL_UNITS_SOLD) + (ACCRUAL.PAID_INCL_NAD_UNITS - ACCRUAL.PAID_EXCL_NAD_UNITS)) AS UNITS_PLUS_NAD\n"+
			", SUM((ACCRUAL.PAID_INCL_BONUSABLE_UNITS - ACCRUAL.PAID_EXCL_BONUSABLE_UNITS) + (ACCRUAL.PAID_INCL_NAD_UNITS - ACCRUAL.PAID_EXCL_NAD_UNITS)) AS BONUSABLE_UNITS_PLUS_NAD\n"+
			", SUM(ACCRUAL.PAID_INCL_INV_SALES - ACCRUAL.PAID_EXCL_INV_SALES) AS INV_SALES\n"+
			", SUM(ACCRUAL.PAID_INCL_BONUSABLE_SALES - ACCRUAL.PAID_EXCL_BONUSABLE_SALES) AS BONUSABLE_SALES\n"+
			", SUM(ACCRUAL.PAID_INCL_NAD_CREDIT - ACCRUAL.PAID_EXCL_NAD_CREDIT) AS NAD_SALES\n"+
			", SUM((ACCRUAL.PAID_INCL_INV_SALES - ACCRUAL.PAID_EXCL_INV_SALES) + (ACCRUAL.PAID_INCL_NAD_CREDIT - ACCRUAL.PAID_EXCL_NAD_CREDIT)) AS INV_SALES_PLUS_NAD\n"+
			", SUM((ACCRUAL.PAID_INCL_BONUSABLE_SALES - ACCRUAL.PAID_EXCL_BONUSABLE_SALES) + (ACCRUAL.PAID_INCL_NAD_CREDIT - ACCRUAL.PAID_EXCL_NAD_CREDIT)) AS BONUSABLE_SALES_PLUS_NAD\n"+
			", SUM(ACCRUAL.PAID_INCL_WRC_CREDIT - ACCRUAL.PAID_EXCL_WRC_CREDIT) AS WARRANTY\n"+
			", PGM_DTL.PAY_TO\n"+
			", PGM_DTL.PGM_START_DATE AS PGM_BEGIN_DATE\n"+
			", PGM_DTL.PGM_END_DATE AS PGM_END_DATE\n"+
			", PGM_DTL.GL_CODE AS GL\n"+
			", PAYMENT_TYPE.TYPE AS PAYMENT_METHOD\n"+
			", BI.ID AS BASE_ITEM_ID\n"+
			"FROM ACCRUAL_DATA_PGM_DTL_PART_CUST ACCRUAL\n"+
			"JOIN PROGRAM_DETAIL PGM_DTL ON ACCRUAL.PGM_DETAIL_ID = PGM_DTL.ID\n"+
			"JOIN PROGRAM_HEADER PGM_HDR ON PGM_DTL.PGM_HDR_ID = PGM_HDR.ID\n"+
			"JOIN CUSTOMER CUST ON CUST.ID = PGM_HDR.CUSTOMER_ID\n"+
			"JOIN EMPLOYEE_HIERARCHY HIER ON CUST.ACCOUNT_MANAGER = HIER.BASE_EMP_ID\n"+
			"JOIN FREQUENCY FREQ ON PGM_DTL.PAID_BASED_FREQ_ID = FREQ.ID\n"+
			"JOIN PROGRAM_MASTER PGM_MAST ON PGM_DTL.PGM_ID = PGM_MAST.ID\n"+
			"JOIN SHIP_TO_MASTER SHIP_TO ON ACCRUAL.SHIP_TO_NO = SHIP_TO.SHIP_TO_NUMBER\n"+
			"JOIN PAID_TYPE PAYMENT_TYPE ON PGM_DTL.PAID_TYPE_ID = PAYMENT_TYPE.ID\n"+
			"JOIN BASE_ITEMS BI ON PGM_DTL.PAID_BASED_METRIC_ID = BI.ID\n"+
			"WHERE FREQ.FREQUENCY = :frequency"+
			" AND CUST.BU = :bu"+
			" AND ACCRUAL.INV_DATE BETWEEN :beginDate AND :endDate"+
			" GROUP BY CUST.BU, HIER.LVL1_EMP_NAME, HIER.LVL3_EMP_NAME, FREQ.FREQUENCY, PGM_DTL.ID, PGM_MAST.PROGRAM, PGM_DTL.SHORT_DESC, ACCRUAL.PAID_BASED_ON, PGM_DTL.IS_TIERED, ACCRUAL.ACCRUAL_AMOUNT, ACCRUAL.ACCRUAL_TYPE, SHIP_TO.CORP_NUMBER, SHIP_TO.CORP_NAME, PGM_DTL.PAY_TO, PGM_DTL.PGM_START_DATE, PGM_DTL.PGM_END_DATE, PGM_DTL.GL_CODE, PAYMENT_TYPE.TYPE, BI.ID\n"+
			"ORDER BY CUST.BU, HIER.LVL3_EMP_NAME, HIER.LVL1_EMP_NAME, FREQ.FREQUENCY, PGM_DTL.ID";

	public static final String CCM_REPORT_NEW="SELECT o FROM DalCcmAccrualData o WHERE o.bu in (:bu) AND o.frequency in(:frequency) AND o.periodId in (:period) AND o.programStatus in (:status)";
	
	public static final String CCM_PERIOD="SELECT ID, MONTH_NAME FROM PERIOD_MASTER";
	
	public static final String CCM_UPDATE="UPDATE ACCRUAL_DATA_PGM_DTL_CORP_WITH_ADJUSTMENTS SET ADJUSTED_AMOUNT=:adjustedAmount, ADJUSTED_CREDIT=:adjustedCredit, STATUS_FLAG='Submitted', ADJUSTED_USER=:user, ADJUSTED_SYSDATE=SYSDATETIME() WHERE ID=:id";

	public static final String CCM_LIST="SELECT o FROM DalCcmAccrualData o WHERE o.id in (:id)";
	
	public static final String CCM_UPDATE_STATUS="UPDATE ACCRUAL_DATA_PGM_DTL_CORP_WITH_ADJUSTMENTS SET STATUS_FLAG=:status WHERE ID=:id";
	
	public static final String CCM_COMMENTS="SELECT COMMENTS FROM CCM_AUDIT_TABLE where CCM_ID in (:id) ORDER BY ADJUSTED_SYSDATE DESC";
	
	public static final String CCM_UPDATE_DOC="UPDATE ACCRUAL_DATA_PGM_DTL_CORP_WITH_ADJUSTMENTS SET DOC_NO=:docNo, DOC_DATE=:docDate WHERE ID=:id";
	
	public static final String BOOK_LIST="SELECT o FROM DalBookList o";
	
	public static final String CREATE_BOOK_LIST="INSERT INTO BOOK_LIST VALUES(SYSDATETIME(),:user,:booklabel,:bookdate,:bookrecord)";
	
	public static final String DELETE_BOOK_LIST="DELETE FROM BOOK_LIST WHERE ID IN (:id)";
	
	public static final String DELETE_ACCURAL_BOOK="DELETE FROM ACCRUAL_DATA_BOOK WHERE BOOK_ID IN (:id)";
	
}
