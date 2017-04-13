package com.tiredex.yoko.utils;

/*****************************************************************************
Program Name		: Error Message Interface
Program Description	: See JavaDoc Comments

Program History		:

Programmer		Date			Description
Hemant Gaur		29-01-01		NEW
*****************************************************************************/
public interface ErrorMessageInterface 
{
	public static final String INVALID_USERID = "Invalid User ID.";
	public static final String INVALID_PASSWORD = "Password is incorrect.";
	public static final String INACTIVE_USER = "This User ID has been deactivated.  Please contact Customer Service.";
	public static final String NULL_USERID = "User ID and Password are required.";
	public static final String NULL_PASSWORD = "User ID and Password are required.";
	public static final String NO_MATCHING_RECORDS = "No matching records found.";
	public static final String CANNOT_CONTINUE = "A processing error has occurred.  Please try to go BACK to the previous page and try again or report this error to Customer Service.";
	public static final String NULL_DATE = "Please enter a valid date.";
	public static final String INVALID_DATE= "Date entered is invalid.  Please enter a valid date.";
	public static final String NULL_MODE = "A processing error has occurred.  Please try to go BACK to the previous page and try again or report the following error to Customer Service.";
	public static final String NULL_VALUE = "A processing error has occurred.  Please report the following error to Customer Service.";
	public static final String NULL_BILL_TO_INDEX = "A processing error has occurred.  Please report the following error to Customer Service.";
	public static final String INVALID_INDEX = "A processing error has occurred.  Please report the following error to Customer Service: The index is not valid. The index retrieved was:";
	public static final String REMOTE_NAMING = "Our server cannot process your request at this time. Please try later or contact Customer Service.";
	public static final String REMOTE_REMOTE = "Our server cannot process your request at this time. Please try later or contact Customer Service.";
	public static final String REMOTE_CREATE = "Our server cannot process your request at this time. Please try later or contact Customer Service.";
	public static final String REMOTE_OBJECT = "Our server cannot process your request at this time. Please try later or contact Customer Service.";
	public static final String REMOTE_SQL = "Our server cannot process your request at this time. Please try later or contact Customer Service.";
	public static final String REMOTE_UNABLE_TO_WRITE= "Our server cannot process your request at this time. Please try later or contact Customer Service.";
	public static final String REMOTE_FINDER = "Our server cannot process your request at this time. Please try later or contact Customer Service.";
	public static final String REMOTE_DUPLICATE_KEY = "A user with this User ID already exists. Please try again with a new User ID.";
	public static final String NO_VALID_PARTD = "This account does not have a product profile established with Yokohama. The account is not currently authorized to perform inventory inquiries or to enter Orders and Delivery Receipts";
	public static final String NO_HISTORY = "There are no open orders, or orders closed within the last 30 days, for this Ship To.";
	public static final String NOT_AVAILABLE	= "This order was not found, or it belongs to another customer.";
	public static final String INVALID_PART_NUMBER = " Part number is not a valid part number. Please remove this part from the list and resubmit.";
	public static final String CURRENT_SESSION_EXPIRED = "Sorry! Your current session has expired. Please click CONTINUE to log in.";
	public static final String DR_ALREADY_EXISTS = "There is an unsubmitted Delivery Receipt in progress. Click CONTINUE to continue with this DR or CLEAR to delete this DR.";
	public static final String DR_ALREADY_SUBMITTED ="This Delivery Receipt has already been submitted or cleared. Please click 'Delivery Receipts' on the navigation bar to start a new DR.";
	public static final String DSHIP_TO_ID = "Dealer Ship To ID is blank.  Please enter a Dealer Ship To ID.";
	public static final String DUPLICATE_PART_NUMBER = " Part number is already included in your order. To change the quantity, please edit the requested quantity on the order.";
	public static final String ERROR_FROM_SMALL_WINDOW = "An error occurred while adding parts from Catalog Search window. Please close the window and try again.";
	public static final String INACTIVE_PART = " Part Number is an inactive part number. Please remove this part from the list and resubmit.";
	public static final String INSERT_OR_UPDATE_FAILED = "Our server cannot process your request at this time. Please try later or contact Customer Service.";
	public static final String INVALID_CUSTOMER_PART = " Customer Part Number not found and must be on file with Yokohama before they can be used for order entry. Please remove this part from the list and resubmit.";
	public static final String INVALID_DEALER_SHIP_TO_ID = "Invalid Dealer Ship To ID";
	public static final String INVALID_NA_SHIP_TO_ID = "Invalid National Account Number";
	public static final String NO_ACTIVE_SHIP_TO_LOCATION = "There is no active shipping location available for this Ship To ID. Please contact Customer Service.";
	public static final String NSHIP_TO_ID = "National Account Number is blank. Please enter a valid National Account Number.";
	public static final String ORDER_ALREADY_SUBMITTED = "The current order has already been submitted or deleted. Click 'Order Entry' on the navigation bar to start a new order.";
	public static final String PART_INVALID_FOR_CUSTOMER = " Part Number is not a valid part for the customer. Please remove this part from the list and resubmit.";
	public static final String PART_NOT_AVAILABLE_FOR_ORDER = " Part Number is not available for order. Please remove this part from the list and resubmit.";
	public static final String SHIP_TO_ID_CHANGED = "Ship To Location for the order in progress can not be changed. Select CLEAR to clear the current order or CONTINUE to undo the change.";
	public static final String SHOPPING_CART_LIMITATION = "You may not add more than 200 items to your order.";
	public static final String SHOPPING_CART_SIZE = " items have already been added to your order.";
	public static final String UNSUBMITTED_ORDER_EXISTS = "There is an unsubmitted order in progress. Click CONTINUE to continue with this order or CLEAR to delete this order.";
	public static final String USER_DOES_NOT_EXIST = "This User ID was not found in our records.  Please contact Customer Service.";
	public static final String WRONG_CUSTOMER_TYPE = "This National Account Number has the wrong customer type. Please enter a different National Account number.";
	public static final String WTYCLM_ALREADY_EXISTS = "There is an unsubmitted Warranty Claim in progress. Click CONTINUE to continue with this claim or CLEAR to delete this claim.";
	public static final String WTYCLM_ALREADY_SUBMITTED ="This Warranty Claim has already been submitted or cleared. Please click on one of the links on the navigation bar above.";
	public static final String INVALID_DATA ="The data provided is invalid.";
    public static final String MISSING_DATA ="Required data is missing.";
	public static final String INVALID_DOT_SERIAL_NUMBER ="The DOT Serial Number is invalid.";
	public static final String NOT_AUTHORIZED = "You are not authorized to perform this operation.  Please contact Customer Service.";
	public static final String PRIVILEGE_DENIED = "This operation is unavailable.  Please try later or contact Customer Service.";
	public static final String UNEXPECTED_EXCEPTION = "An unexpected error has occurred and our server cannot process your request at this time. Please try again later or contact Customer Service.";
}
