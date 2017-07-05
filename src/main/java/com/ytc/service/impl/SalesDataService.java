package com.ytc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.apache.commons.collections4.map.MultiValueMap;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalPartMaster;
import com.ytc.dal.model.DalPgmDetPaidInclude;
import com.ytc.dal.model.DalProgramDetPaid;
import com.ytc.dal.model.DalTagItems;
import com.ytc.service.ISalesDataService;

public class SalesDataService implements ISalesDataService{

	private static final Object ProdLine = null;

	private static final Object Tread = null;

	private static final Object Part = null;

	private static final Object Brand = null;

	@PersistenceContext
	protected EntityManager entityManager;
	
	@Autowired
	private IDataAccessLayer baseDao;

	private int HashMap;

	


	@Override
	public List<String> getBaseData(Integer id){
		
		Query result = entityManager.createNativeQuery("CALL sp.PgmDetPartPaidInclude", DalPgmDetPaidInclude.class);
		
		CriteriaQuery<DalProgramDetPaid> criteria = entityManager.getCriteriaBuilder().createQuery(DalProgramDetPaid.class);

		Root<DalProgramDetPaid> data = criteria.from(DalProgramDetPaid.class); 

		criteria.select(data);
		criteria.where(entityManager.getCriteriaBuilder().equal(data.get("dalProgramDetails"), id));
		List<DalProgramDetPaid> listOfBaseData = entityManager.createQuery(criteria).getResultList();
		
		List<String> listOfTags = new ArrayList<>();
		  MultiMap<String, Object> multiMap = new MultiValueMap<String, Object>();
		  MultiMap<String, Object> multiMapOut = new MultiValueMap<String, Object>();
		  
		for(DalProgramDetPaid pgmDetPaid: listOfBaseData){
			DalTagItems dalTag= baseDao.getById(DalTagItems.class, pgmDetPaid.getTagId());
			
			if(dalTag.getEntityId()==2 && pgmDetPaid.getMethod().equals("1")){
				multiMap.put(dalTag.getItem().toString(),pgmDetPaid.getValue());
			
			}else if(dalTag.getEntityId()==2 && pgmDetPaid.getMethod().equals("2")){
				multiMapOut.put(dalTag.getItem().toString(),pgmDetPaid.getValue());
			}
			}
		
		 Set<String> keys = multiMap.keySet();
		 Set<String> keysOut = multiMapOut.keySet();
		  
		 StringBuilder jpqlQuery = new StringBuilder();
		jpqlQuery.append("SELECT pm.partNumber FROM DalPartMaster pm WHERE ");
         // iterate through the key set and display key and values
		 int i =1;
		 for (String key : keys)
		 {
			 if(key.equalsIgnoreCase("Brand")){
				 jpqlQuery.append(" pm.brand ");
			 }else if (key.equalsIgnoreCase("Prod Line")){
				 jpqlQuery.append(" pm.productLine ");
			 }else if (key.equalsIgnoreCase("Tread")){
				 jpqlQuery.append(" pm.treadDesc ");
			 }else if (key.equalsIgnoreCase("Part")){
				 jpqlQuery.append((" pm.partNumer "));
			 }
			 jpqlQuery.append(" IN ");
			 jpqlQuery.append( multiMap.get(key));		
			 if(keys.size() !=i){
				 jpqlQuery.append( " AND ");
			 }
			 //System.out.println("Key = " + key);
			 //System.out.println("Values = " + multiMap.get(key) + "/n");
			 i++;
		 }
		 
		 int j =1;
		 for (String key1 : keysOut)
		 {
			 jpqlQuery.append(" AND ");
			 if(key1.equalsIgnoreCase("Brand")){
				 jpqlQuery.append(" pm.brand ");
			 }else if (key1.equalsIgnoreCase("Prod Line")){
				 jpqlQuery.append(" pm.productLine ");
			 }else if (key1.equalsIgnoreCase("Tread")){
				 jpqlQuery.append(" pm.treadDesc ");
			 }else if (key1.equalsIgnoreCase("Part")){
				 jpqlQuery.append((" pm.partNumer "));
			 }
			 
			 jpqlQuery.append(" NOT IN ");
			 jpqlQuery.append( multiMap.get(key1));		
			 //System.out.println("Key = " + key);
			 //System.out.println("Values = " + multiMap.get(key) + "/n");
			 if(keys.size() !=j){
				 jpqlQuery.append( " AND ");
			 }
			 j++;
		 }
		 	Map<String, Object> queryParams = new HashMap<>();
		 	queryParams.put("Prod Line", ProdLine);
	        queryParams.put("Tread", Tread);
	        queryParams.put("Part", Part);
	        queryParams.put("Brand", Brand);
		 	baseDao.getByJPQL(String.class, jpqlQuery.toString(), queryParams);
			
			return listOfTags;
		
		//}

		//Map<String, Object> queryParams = new HashMap<>();	       
		//return baseDao.getByJPQL(String.class, jpqlQuery.toString(), queryParams);

		//return null;


	}

}
