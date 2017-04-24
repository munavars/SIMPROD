package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "BASE_ITEMS")
@NamedQueries({
	@NamedQuery(name="DalBaseItems.getAllDetails", query = "select o from DalBaseItems o")
})
public class DalBaseItems {

	private Integer baseItemId;
	private String baseItem;
	private String base;
	
	@Id
	@Column(name = "BASE_ITEM_ID")
	public Integer getBaseItemId() {
		return baseItemId;
	}
	public void setBaseItemId(Integer baseItemId) {
		this.baseItemId = baseItemId;
	}
	
	@Column(name = "BASE_ITEM")
	public String getBaseItem() {
		return baseItem;
	}
	public void setBaseItem(String baseItem) {
		this.baseItem = baseItem;
	}
	
	@Column(name = "BASE")
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	
	
}
