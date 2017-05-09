package com.ytc.service;

import java.util.UUID;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.ytc.common.model.Employee;


@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServiceContext {
	
	private Integer userId;

	private Employee employee;

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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
