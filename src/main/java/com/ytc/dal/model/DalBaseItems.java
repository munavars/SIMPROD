/**
 * 
 */
package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ArunP
 *
 */
@Entity
@Table(name = "BASE_ITEMS")
public class DalBaseItems extends DalModel{
	
	private int baseItemId;
	private String baseItem;
	private String base;
	/**
	 * @return the baseItemId
	 */
	@Id
	@Column(name = "BASE_ITEM_ID")
	public int getBaseItemId() {
		return baseItemId;
	}
	/**
	 * @param baseItemId the baseItemId to set
	 */
	public void setBaseItemId(int baseItemId) {
		this.baseItemId = baseItemId;
	}
	/**
	 * @return the baseItem
	 */
	@Column(name = "BASE_ITEM")
	public String getBaseItem() {
		return baseItem;
	}
	/**
	 * @param baseItem the baseItem to set
	 */
	public void setBaseItem(String baseItem) {
		this.baseItem = baseItem;
	}
	/**
	 * @return the base
	 */
	@Column(name = "BASE")
	public String getBase() {
		return base;
	}
	/**
	 * @param base the base to set
	 */
	public void setBase(String base) {
		this.base = base;
	}

	
	

}
