package com.ytc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
//import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;



public class RequestInterceptor extends HandlerInterceptorAdapter {

    public RequestInterceptor() {
        super();
    }
  

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startMs = System.currentTimeMillis();
        request.setAttribute("startMs", startMs);

     //   HandlerMethod hm = (HandlerMethod) handler;
       // String methodName = hm.getMethod().getName();
      //  String controllerName = hm.getMethod().getDeclaringClass().getSimpleName();

   /*     if (methodName.equalsIgnoreCase("getErrorPage") || (controllerName.toLowerCase().contains("servicescontroller") && methodName.toLowerCase().contains("getapplicationsettings"))) {
            return true;
        }*/
            // Ensure user is authenticated if controller method requires it
        if (StringUtils.isBlank("")) {
           
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

       
       
        return super.preHandle(request, response, handler);
    }

   
}
