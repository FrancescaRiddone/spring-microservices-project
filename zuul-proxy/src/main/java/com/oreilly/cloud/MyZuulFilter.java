package com.oreilly.cloud;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.oreilly.cloud.object.FlightReservationRequest;
import com.oreilly.cloud.object.HotelReservationRequest;
import com.oreilly.cloud.repository.UserRepository;

public class MyZuulFilter extends ZuulFilter {
	
	@Autowired
    private UserRepository userRepository;
	
	
	@Override
	public boolean shouldFilter() {
		RequestContext requestContext = RequestContext.getCurrentContext(); 
        HttpServletRequest request = requestContext.getRequest();
        String requestUrl = URI.create(request.getRequestURI()).getPath();
        if(requestUrl.contains("cart") || 
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
		//HttpServletRequest request = context.getRequest();
        //String requestUrl = URI.create(request.getRequestURI()).getPath();
		
		addUserIdParamToRequest(username, context);
		/*
	    if(requestUrl.contains("cart/flights/newFlight") || requestUrl.contains("cart/hotels/newHotel")) {
	    	addUserEmailInRequestBody(username, context, requestUrl);
	    }
	    */
	    
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
	
	private void addUserEmailInRequestBody(String username, RequestContext context, String requestUrl) {
		System.out.println("VALORE IN REQUESTURL : " + requestUrl);
		
		try {
			InputStream in = (InputStream) context.get("requestEntity");
			if (in == null) {
				in = context.getRequest().getInputStream();
			}
			String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
			
			if(requestUrl.contains("cart/flights/newFlight")) {
				
				System.out.println("SONO NELL'IF CON REQUEST : " + requestUrl);
				
				FlightReservationRequest flightRequest = new ObjectMapper().readValue(body, FlightReservationRequest.class);
				flightRequest.setUserEmail(username);
				
				ObjectMapper obj = new ObjectMapper(); 
				String jsonStr = obj.writeValueAsString(flightRequest);
				System.out.println(jsonStr);
				
				context.set("requestEntity", new ByteArrayInputStream(jsonStr.getBytes("UTF-8")));
				
			} else {
				
				System.out.println("SONO NELL'IF CON REQUEST : " + requestUrl);
				
				HotelReservationRequest hotelRequest = new ObjectMapper().readValue(body, HotelReservationRequest.class);
				hotelRequest.setUserEmail(username);
				
				ObjectMapper obj = new ObjectMapper(); 
				String jsonStr = obj.writeValueAsString(hotelRequest);
				System.out.println(jsonStr);
				
				context.set("requestEntity", new ByteArrayInputStream(jsonStr.getBytes("UTF-8")));
				
			}
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


}
