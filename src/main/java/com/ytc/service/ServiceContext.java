package com.ytc.service;

import java.util.UUID;




public class ServiceContext {
	
	private String userId;


	private static final ThreadLocal<ServiceContext> serviceContext = new ThreadLocal<ServiceContext>() {
		@Override
		protected ServiceContext initialValue() {
			return null;
		}
	};

	/**
	 * Gets the service context associated with this thread. May be null.
	 */
	public static ServiceContext getServiceContext() {
		return serviceContext.get();
	}

	/**
	 * Sets the service context associated with this thread. The previous service context, if any, is cleared.
	 */
	public static void setServiceContext(ServiceContext context) {
		serviceContext.set(context);
	}

	public ServiceContext() {
		UUID.randomUUID().toString().replaceAll("-", "");

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	


}
