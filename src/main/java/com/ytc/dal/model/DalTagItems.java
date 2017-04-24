package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "TAG_ITEMS")
@NamedQueries({
	@NamedQuery(name="DalTagItems.getAllDetails", query = "select o from DalTagItems o")
})
public class DalTagItems {
	

	private Integer itemId;
	

	private Integer entityId;
	
	
	private String item;
	
	
	private Integer tagLevel;

	@Id
	@Column(name = "ITEM_ID")
	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	@Column(name = "ENTITY_ID")
	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	@Column(name = "ITEM")
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	@Column(name = "TAG_LEVEL")
	public Integer getTagLevel() {
		return tagLevel;
	}

	public void setTagLevel(Integer tagLevel) {
		this.tagLevel = tagLevel;
	}
}
