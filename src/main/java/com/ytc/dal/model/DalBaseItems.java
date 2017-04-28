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
	@NamedQuery(name="DalBaseItems.getAllDetails", query = "select o from DalBaseItems o order by o.baseItem")
})
public class DalBaseItems extends DalModel{

	private String baseItem;
	private String base;
	
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
