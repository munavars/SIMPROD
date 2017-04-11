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
@Table(name = "FREQUENCY")
public class DalFrequency extends DalModel{

	private int frequencyId;
	private String frequency;
	/**
	 * @return the frequencyId
	 */
	@Id
	@Column(name = "FREQ_ID")
	public int getFrequencyId() {
		return frequencyId;
	}
	/**
	 * @param frequencyId the frequencyId to set
	 */
	public void setFrequencyId(int frequencyId) {
		this.frequencyId = frequencyId;
	}
	/**
	 * @return the frequency
	 */
	@Column(name = "FREQUENCY")
	public String getFrequency() {
		return frequency;
	}
	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
}
