package com.oreilly.cloud.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.oreilly.cloud.object.BirthDate;
import com.oreilly.cloud.object.UserCreationRequest;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
	
	@Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
	
    
    /*
	 * TESTS on URI /signUp
	 */
    
    @Test
    public void userSignedUpWithSuccess() throws Exception {
    	String URI = "/signUp";
    	UserCreationRequest userCreationRequest = createNewUserCreationRequest();
    	String requestJson = convertUserRequestInJson(userCreationRequest);

    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(MockMvcResultMatchers.jsonPath("$.username").value("giovannineri@yahoo.it"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Giovanni"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.surname").value("Neri"))
				.andExpect(status().isOk());	
    }
    
    @Test
    public void userSignedUpWithInvalidException() throws Exception {
    	String URI = "/signUp";
    	UserCreationRequest userCreationRequest = createNewInvalidUserCreationRequest();
    	String requestJson = convertUserRequestInJson(userCreationRequest);
    	
    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(status().is(400));	
    }
    
    /*
	 * TESTS on URI /users/user/{username}
	 */
    
    @Test
    public void foundUserWithUsername() throws Exception {
    	String URI = "/users/user/mariorossi@yahoo.it";
    	
        mockMvc.perform(get(URI))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("mariorossi@yahoo.it"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Mario"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.surname").value("Rossi"))
        		.andExpect(status().isOk());     
    }
    
    @Test
    public void userNotFoundWithValidUsername() throws Exception {
    	String URI = "/users/user/mariobianchi@yahoo.it";
    	
        mockMvc.perform(get(URI))
        		.andExpect(status().is(404));     
    }
    
    
	private UserCreationRequest createNewUserCreationRequest() {
		UserCreationRequest userCreationRequest = new UserCreationRequest();
		userCreationRequest.setUsername("giovannineri@yahoo.it");
		userCreationRequest.setPassword("secret");
		userCreationRequest.setName("Giovanni");
		userCreationRequest.setSurname("Neri");
		userCreationRequest.setBirthDate(new BirthDate(5, 7, 1994));
		
		return userCreationRequest;
	}
	
	private UserCreationRequest createNewInvalidUserCreationRequest() {
		UserCreationRequest userCreationRequest = new UserCreationRequest();
		userCreationRequest.setUsername("");
		userCreationRequest.setPassword("secret");
		userCreationRequest.setName("Giovanni");
		userCreationRequest.setSurname("Neri");
		userCreationRequest.setBirthDate(new BirthDate(5, 7, 1994));
		
		return userCreationRequest;
	}
	
    private String convertUserRequestInJson(UserCreationRequest userCreationRequest) {
    	ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = "";
        
        try {
			requestJson = ow.writeValueAsString(userCreationRequest);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} 
        
        return requestJson;
    }
    

}
