package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "TAG_ITEMS")
@NamedQueries({
	@NamedQuery(name="DalTagItems.getAllDetails", query = "select o from DalTagItems o order by o.item")
})
public class DalTagItems extends DalModel {

	private Integer entityId;
	
	
	private String item;
	
	
	private Integer tagLevel;
	
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
