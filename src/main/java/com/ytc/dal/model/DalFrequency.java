package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "FREQUENCY")
@NamedQueries({
	@NamedQuery(name="DalFrequency.getAllDetailsWithSort", query = "select o from DalFrequency o order by o.sortSequence")
})
public class DalFrequency extends DalModel {
	/**
	 * Default serial version id.
	 */
	private static final long serialVersionUID = 1L;
	private String frequency;
	private Integer sortSequence;

	@Column(name = "FREQUENCY")
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	@Column(name = "SORT_SEQ")
	public Integer getSortSequence() {
		return sortSequence;
	}
	public void setSortSequence(Integer sortSequence) {
		this.sortSequence = sortSequence;
	}
}
