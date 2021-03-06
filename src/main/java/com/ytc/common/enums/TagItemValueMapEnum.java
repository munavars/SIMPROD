package com.ytc.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum Name : TagItemValueMapEnum
 * Purpose : Enum  is used to get the table and column for any particular tag id.
 * 			 In program details page, when user selects a particular tag value in drop down,
 * 			 system has to populate the tag drop down based on tag value selected by user.
 * 			 this enum acts an interface to get the table name and column name to get the list dynamically.
 * @author Cognizant.
 *
 */
public enum TagItemValueMapEnum {

	ZERO(0, "0", "", ""),
	BILL_TO_NUMBER(1,"Bill To", "DalShipToMaster","CONCAT(billToNumber, ' - ', billToName)"),
	CHANNEL_3(2,"Channel3","DalShipToMaster","CONCAT(channelCode, ' - ', channelName)"),
	BUSINESS_UNIT(3,"Business Unit","DalShipToMaster","CONCAT(businessUnit, ' - ', businessUnitName)"),
	BRAND(4,"Brand","DalPartMaster","brand"),
	PRODUCT_LINE(5,"Prod Line","DalPartMaster","productLine"),
	TREAD_DESC(6,"Tread","DalPartMaster","treadDesc"),
	PART_NUMBER(7,"All Products","DalPartMaster","CONCAT(partNumber, ' - ', partDesc)"),
	ALL_CUSTOMERS(8,"All Customers", "DalShipToMaster","CONCAT(billToNumber, ' - ', billToName)"),		
	ALL_PRODUCTS(9,"Part","DalPartMaster","CONCAT(partNumber, ' - ', partDesc)"),
	SHIP_TO_NUMBER(10,"Ship To","DalShipToMaster","CONCAT(shipToNumber, ' - ', shipToName)"),
	CORP_NUMBER(11,"Corp","DalShipToMaster","CONCAT(corpNumber, ' - ', corpName)"),
	CHANNEL_CODE(12,"Channel1","DalShipToMaster","CONCAT(channelGroup, ' - ', channelGroupName)"),
	CHANNEL_GROUP(13,"Channel2","DalShipToMaster","CONCAT(SUBSTRING(channelCode, 1, 2), ' - ', channelName)"),
	/**As per new requirement, below values should be displayed to the user at the top of tag item
	 * drop down.*/
	COMMERCIAL_PRODUCT_LINE(14,"Commercial Product Line","",""),
	CONSUMER_PRODUCT_LINE(15,"Consumer Product Line","",""),
	OTR_PRODUCT_LINE(16,"OTR Product Line","","");

	private static final Map<Integer, TagItemValueMapEnum> map = new HashMap<>(values().length, 0.75f);
	
	static {
		for (TagItemValueMapEnum c : values()) map.put(c.getTagId(), c);
	}

	private final Integer tagId;
	private final String masterColumnName;
	private final String tableName;
	private final String fetchColumnName;
	
	private TagItemValueMapEnum(Integer tagId, String masterColumnName,
								String tableName, String fetchColumnName) {
		this.tagId = tagId;
		this.masterColumnName = masterColumnName;
		this.tableName = tableName;
		this.fetchColumnName = fetchColumnName;
	}

	public Integer getTagId() {
		return tagId;
	}

	public String getMasterColumnName() {
		return masterColumnName;
	}

	public String getTableName() {
		return tableName;
	}

	public String getFetchColumnName() {
		return fetchColumnName;
	}

	/**
	 * Method to get the table details based on tag id passed.
	 * @param tagId tagId.
	 * @return TagItemValueMapEnum i.e, enum instance of that particular tag id.
	 */
	public static TagItemValueMapEnum tableDetail(Integer tagId) {
		TagItemValueMapEnum result = map.get(tagId);
	    if (result == null) {
	      throw new IllegalArgumentException("Invalid tag id: " + tagId);
	    }
	    return result;
	  }
}
