package com.microservices.project;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.microservices.project.repository.UserRepository;

public class MyZuulFilter extends ZuulFilter {
	
	@Autowired
    private UserRepository userRepository;
	
	
	@Override
	public boolean shouldFilter() {
		RequestContext requestContext = RequestContext.getCurrentContext(); 
        HttpServletRequest request = requestContext.getRequest();
        String requestUrl = URI.create(request.getRequestURI()).getPath();
        if(requestUrl.contains("cart") || requestUrl.contains("registry") ||
        		requestUrl.contains("confirmedFlightReservations") || requestUrl.contains("confirmedHotelReservations")) {
        	
        	return true;
        }
        
        return false;
	}

	@Override
	public Object run() throws ZuulException {
		System.out.println("sono nello Zuul Filter..");
		
		String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		RequestContext context = RequestContext.getCurrentContext();
		
		addUserIdParamToRequest(username, context);
	    
	    return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}
	
	
	private void addUserIdParamToRequest(String username, RequestContext context) {
		
		
		int userId = userRepository.findByUsername(username).getUserId();
		
		System.out.println("username attuale: " + username);
		
		Map<String, List<String>> newParameterMap = new HashMap<>();
	    Map<String, String[]> parameterMap = context.getRequest().getParameterMap();
	    
	    for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
	      String key = entry.getKey();
	      String[] values = entry.getValue();
	      newParameterMap.put(key, Arrays.asList(values));
	    }
	    
	    String newKey = "userId";
	    newParameterMap.put(newKey,Arrays.asList(userId + ""));
	    context.setRequestQueryParams(newParameterMap);
	}


}
