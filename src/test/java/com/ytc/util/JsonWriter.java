package com.ytc.util;

import java.io.IOException;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.codehaus.jackson.map.JsonMappingException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ytc.common.model.Customer;
import com.ytc.common.params.CreditMemoParams;

public class JsonWriter {
	
	private static Logger logger = Logger.getLogger(JsonWriter.class);

	public static void main(String[] args) throws JsonMappingException {
		ObjectMapper jsonWriter = new ObjectMapper();
		try {
			CreditMemoParams params = new CreditMemoParams();
			params.setPgmDetailId(1515);
			params.setStartDate(Calendar.getInstance());
			params.setEndDate(Calendar.getInstance());
			String modifedObjectJson = jsonWriter.writeValueAsString(params);
			System.out.println("Modified Json ... " +modifedObjectJson);
			JSONObject object = new JSONObject(modifedObjectJson);
			object.put("CMBLTOID", "1234");
			logger.info("Generated JSON:    " + object.getString("CMBLTOID"));
			// jsonWriter.readValue(modifedObjectJson, Customer.class);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block

		} catch (IOException e) {
			// TODO Auto-generated catch block

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
