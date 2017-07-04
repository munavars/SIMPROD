package com.ytc.helper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.ytc.common.model.PricingDetail;
import com.ytc.common.model.PricingHeader;
import com.ytc.dal.model.DalPricingDetail;
import com.ytc.dal.model.DalPricingHeader;

public class PricingServiceHelper {
	
	public static Set<Integer> getUpdatedIds(PricingHeader pricingHeader){
		Set<Integer> existingIdsPresent = new HashSet<Integer>();
		
		if(pricingHeader != null && pricingHeader.getPricingDetailList() != null && !pricingHeader.getPricingDetailList().isEmpty()){
			for(PricingDetail detail : pricingHeader.getPricingDetailList()){
				existingIdsPresent.add(detail.getId());
			}
		}
		
		return existingIdsPresent;
	}
	
	public static PricingDetail getUpdatedPricingDetailRow(Integer id, PricingHeader pricingHeader){
		PricingDetail pricingDetail = null;
		if(id != null && pricingHeader != null && pricingHeader.getPricingDetailList() != null && !pricingHeader.getPricingDetailList().isEmpty()){
			for(PricingDetail detail : pricingHeader.getPricingDetailList()){
				if(id.equals(detail.getId())){
					pricingDetail = detail;
				}
			}
		}
		
		return pricingDetail;
	}
	
	public static List<DalPricingDetail> updatedNewlyAddedRow(PricingHeader pricingHeader,
												DalPricingHeader dalPricingHeader) {
		List<DalPricingDetail> addedDalPricingDetailList = null;
		DalPricingDetail dalPricingDetail = null;
		for (PricingDetail pricingDetail : pricingHeader.getPricingDetailList()) {
			dalPricingDetail = new DalPricingDetail();
			if (pricingDetail != null && pricingDetail.getId() == null) {
				dalPricingDetail.setAddChgDel(pricingDetail.getAddChangeDel());
				// dalPricingDetail.setDiscType(pricingDetail.getComments());
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(pricingDetail.getBeginDate().getTime());
				dalPricingDetail.setStartDate(cal);
				Calendar cal1 = Calendar.getInstance();
				cal1.setTimeInMillis(pricingDetail.getEndDate().getTime());
				dalPricingDetail.setEndDate(cal1);
				if (!StringUtils.isEmpty(pricingDetail.getInvoiceDisc())) {
					dalPricingDetail.setInvoiceDisc(Double.valueOf(trimPercentage(pricingDetail.getInvoiceDisc())));
				}
				if (!StringUtils.isEmpty(pricingDetail.getNetPrice())) {
					dalPricingDetail.setNetPrice(Double.valueOf(pricingDetail.getNetPrice()));
				}

				dalPricingDetail.setIsBonusableUnits(pricingDetail.getBonusUnits());
				dalPricingDetail.setIsCommissionable(pricingDetail.getCommissionable());
				dalPricingDetail.setPartNumber(pricingDetail.getPart());
				dalPricingDetail.setProdLine(pricingDetail.getProdLine());
				dalPricingDetail.setProdTread(pricingDetail.getTread());
				dalPricingDetail.setComments(pricingDetail.getComments());
				dalPricingDetail.setDalPricingHeader(dalPricingHeader);
				if(addedDalPricingDetailList == null){
					addedDalPricingDetailList = new ArrayList<DalPricingDetail>();
				}
				addedDalPricingDetailList.add(dalPricingDetail);
			}

		}
		return addedDalPricingDetailList;
	}
	
	public static String trimPercentage(String str) {
		if (!StringUtils.isEmpty(str)) {
			if (str.charAt(str.length() - 1) == '%') {
				str = str.replace(str.substring(str.length() - 1), "");
				return str;
			} else {
				return str;
			}
		}
		return str;
	}
}
