package com.ytc.common.comparator;

import java.util.Comparator;

import com.ytc.dal.model.DalWorkflowStatus;

/**
 * Class WorkflowStatusComparatorByModifiedDate
 * Purpose : Comparator class for dal work flow status based on modified by date.
 * @author Cognizant.
 *
 */
public class WorkflowStatusComparatorByModifiedDate implements Comparator<DalWorkflowStatus> {

	@Override
	public int compare(DalWorkflowStatus o1, DalWorkflowStatus o2) {
		if (o1.getModifiedDate() == null || o2.getModifiedDate() == null)
	        return 0;
	      return o1.getModifiedDate().compareTo(o2.getModifiedDate());
	}	
}
