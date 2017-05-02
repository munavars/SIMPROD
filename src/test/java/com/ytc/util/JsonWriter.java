package com.ytc.util;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.codehaus.jackson.map.JsonMappingException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ytc.common.model.Customer;

public class JsonWriter {
	
	private static Logger logger = Logger.getLogger(JsonWriter.class);

	public static void main(String[] args) throws JsonMappingException {
		ObjectMapper jsonWriter = new ObjectMapper();
		try {
			Customer customer = new Customer();
			customer.setCM_CREDIT_STATUS("Active");
			String modifedObjectJson = jsonWriter.writeValueAsString(customer);
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
