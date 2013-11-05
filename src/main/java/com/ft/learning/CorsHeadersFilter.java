package com.ft.learning;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsHeadersFilter implements Filter {
	     
	     public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
	             ServletException {
	         if (response instanceof HttpServletResponse) {
	             HttpServletResponse httpServletResponse = (HttpServletResponse) response;
	             HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	             httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
	             if ("OPTIONS".equals(httpServletRequest.getMethod())) {
	                 httpServletResponse.addHeader("Access-Control-Allow-Headers", "Content-Type");
	             }
	         }
	         chain.doFilter(request, response);
	     }
	 
	     public void init(FilterConfig filterConfig) throws ServletException {
	         // no configuration required
	     }
	 
	     public void destroy() {
	         // nothing to destroy
	     }

}