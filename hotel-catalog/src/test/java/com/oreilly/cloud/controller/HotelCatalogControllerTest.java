package com.oreilly.cloud.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.oreilly.cloud.object.CheckTime;
import com.oreilly.cloud.object.HotelProperties;
import com.oreilly.cloud.object.HotelReservationRequest;
import com.oreilly.cloud.object.RoomProperties;
import com.oreilly.cloud.object.SearchHotelRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelCatalogControllerTest {
	
	@Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
	
	
    /*
	 * TESTS on URI /hotels/hotel/{hotelId}
	 */
    
    @Test
    public void foundHotelWithValidId() throws Exception {
    	String URI = "/hotels/hotel/5";

        mockMvc.perform(get(URI))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.hotelId").value(5))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.hotelName").value("Hilton London Bankside"))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.city").value("London"))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.stars").value(5))
                .andExpect(status().isOk());
    }
    
    @Test
    public void hotelNotFoundWithNotFoundId() throws Exception {
    	String URI = "/hotels/hotel/50000";

        mockMvc.perform(get(URI))
        		.andExpect(status().is(404));
    }
    
    @Test
    public void hotelNotFoundWithInvalidId() throws Exception {
    	String URI = "/hotels/hotel/0";

        mockMvc.perform(get(URI))
        		.andExpect(status().is(400));
    }
	
    /*
	 * TESTS on URI /hotels/requiredHotels
	 */
	
    @Test
    public void foundHotelsWithSearchHotelRequest() throws Exception {
    	String URI = "/hotels/requiredHotels";
    	SearchHotelRequest hotelsRequest = createSearchHotelRequest("valid"); 
    	String requestJson = convertHotelRequestInJson(hotelsRequest);
    	
    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
    			.andExpect(MockMvcResultMatchers.jsonPath("$[0].hotelId").value(2))
    			.andExpect(MockMvcResultMatchers.jsonPath("$[0].hotelName").value("Cheshire Hotel"))
    			.andExpect(MockMvcResultMatchers.jsonPath("$[0].city").value("London"))
    			.andExpect(MockMvcResultMatchers.jsonPath("$[0].availableRoomsIds").isNotEmpty())
    			.andExpect(MockMvcResultMatchers.jsonPath("$[1].hotelId").value(3))
    			.andExpect(MockMvcResultMatchers.jsonPath("$[1].hotelName").value("DoubleTree by Hilton Hotel London-Tower of London"))
    			.andExpect(MockMvcResultMatchers.jsonPath("$[1].city").value("London"))
    			.andExpect(MockMvcResultMatchers.jsonPath("$[1].availableRoomsIds").isNotEmpty())
    			.andExpect(status().isOk());
    }
    
    @Test
    public void hotelsNotFoundWithSearchHotelRequest() throws Exception {
    	String URI = "/hotels/requiredHotels";
    	SearchHotelRequest hotelsRequest = createSearchHotelRequest("notFound"); 
    	String requestJson = convertHotelRequestInJson(hotelsRequest);
    	
    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
    			.andExpect(status().is(404));
    }
    
    @Test
    public void hotelsNotFoundWithInvalidSearchHotelRequest() throws Exception {
    	String URI = "/hotels/requiredHotels";
    	SearchHotelRequest hotelsRequest = createSearchHotelRequest("invalid"); 
    	String requestJson = convertHotelRequestInJson(hotelsRequest);
    	
    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
    			.andExpect(status().is(400));
    }
    
    /*
	 * TESTS on URI /hotels/rooms/room/{roomId}
	 */
    
    @Test
    public void foundRoomWithValidId() throws Exception {
    	String URI = "/hotels/rooms/room/2";

        mockMvc.perform(get(URI))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.roomId").value(2))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.hotel").value("St Giles London, A St Giles Hotel"))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.hostsNumber").value(2))
                .andExpect(status().isOk());
    }

    @Test
    public void roomNotFoundWithValidId() throws Exception {
    	String URI = "/hotels/rooms/room/20000";

        mockMvc.perform(get(URI))
        		.andExpect(status().is(404));
    }
    
    @Test
    public void roomNotFoundWithInvalidId() throws Exception {
    	String URI = "/hotels/rooms/room/-1";

        mockMvc.perform(get(URI))
        		.andExpect(status().is(400));
    }
    
    /*
	 * TESTS on URI /hotels/rooms/{roomIds}
	 */
    
    @Test
    public void foundRoomsWithIds() throws Exception {
    	String URI = "/hotels/rooms/1, 2, 3";
    	
        mockMvc.perform(get(URI))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[0].roomId").value(1))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[0].hotel").value("St Giles London, A St Giles Hotel"))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[0].hostsNumber").value(2))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[0].singleBeds").value(0))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[1].roomId").value(2))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[1].hotel").value("St Giles London, A St Giles Hotel"))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[1].hostsNumber").value(2))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[1].singleBeds").value(2))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[2].roomId").value(3))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[2].hotel").value("St Giles London, A St Giles Hotel"))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[2].hostsNumber").value(2))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[2].standardDailyPrice").value(168))
        		.andExpect(status().isOk());     
    }
    
    @Test
    public void roomsNotFoundWithValidIds() throws Exception {
    	String URI = "/hotels/rooms/1, 200000";
    	
        mockMvc.perform(get(URI))
        		.andExpect(status().is(404));     
    }
    
    @Test
    public void roomsNotFoundWithInvalidIds() throws Exception {
    	String URI = "/hotels/rooms/2, -1";
    	
        mockMvc.perform(get(URI))
        		.andExpect(status().is(400));     
    }
    
    /*
	 * TESTS on URI /hotels/reservations/reservation/{reservationId}
	 */
    
    @Test
    public void foundReservationWithId() throws Exception {
    	String URI = "/hotels/reservations/reservation/1";

        mockMvc.perform(get(URI)
                .accept(MediaType.APPLICATION_JSON))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.reservationId").value(1))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.room.roomId").value(1))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.room.hotel").value("St Giles London, A St Giles Hotel"))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.price").value(320))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.reservationType").value("breakfast"))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.hostsNumber").value(2))
                .andExpect(status().isOk());
    }
    
    @Test
    public void reservationNotFoundWithValidId() throws Exception {
    	String URI = "/hotels/reservations/reservation/1000000";

        mockMvc.perform(get(URI))
                .andExpect(status().is(404));
    }
    
    @Test
    public void reservationNotFoundWithInvalidId() throws Exception {
    	String URI = "/hotels/reservations/reservation/0";

        mockMvc.perform(get(URI))
                .andExpect(status().is(400));
    }
    
    /*
	 * TESTS on URI /hotels/reservations/{reservationIds}
	 */
    
    @Test
    public void foundReservationsWithIds() throws Exception {
    	String URI = "/hotels/reservations/1, 3";
    	
        mockMvc.perform(get(URI))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[0].reservationId").value(1))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[0].room.roomId").value(1))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(320))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[0].reservationType").value("breakfast"))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[0].hostsNumber").value(2))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[1].reservationId").value(3))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[1].room.roomId").value(13))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[1].price").value(2520))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[1].reservationType").value("standard"))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[1].hostsNumber").value(2))
        		.andExpect(status().isOk());     
    }
    
    @Test
    public void reservationsNotFoundWithValidIds() throws Exception {
    	String URI = "/hotels/reservations/1, 200000";
    	
        mockMvc.perform(get(URI))
        		.andExpect(status().is(404));     
    }
    
    @Test
    public void reservationsNotFoundWithInvalidIds() throws Exception {
    	String URI = "/hotels/reservations/2, -1";
    	
        mockMvc.perform(get(URI))
        		.andExpect(status().is(400));     
    }
    
    /*
	 * TESTS on URI /hotels/reservations/new
	 */
    
    @Test
    public void createReservationWithValidHotelReservationRequest() throws Exception {
    	String URI = "/hotels/reservations/new";
    	HotelReservationRequest reservationRequest = createHotelReservationRequest("valid");
    	String requestJson = convertHotelReservationRequestInJson(reservationRequest);
    	
    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
    			.andExpect(MockMvcResultMatchers.jsonPath("$.room.roomId").value(5))
    			.andExpect(MockMvcResultMatchers.jsonPath("$.price").value(352))
    			.andExpect(MockMvcResultMatchers.jsonPath("$.reservationType").value("with breakfast"))
    			.andExpect(MockMvcResultMatchers.jsonPath("$.hostsNumber").value(2))
    			.andExpect(status().isOk());	
    }
    
    @Test
    public void createReservationWithInvalidHotelReservationRequest() throws Exception {
    	String URI = "/hotels/reservations/new";
    	HotelReservationRequest reservationRequest = createHotelReservationRequest("invalid");
    	String requestJson = convertHotelReservationRequestInJson(reservationRequest);
    	
    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
    			.andExpect(status().is(400));	
    }
    
    @Test
    public void createReservationWithNotFoundRoom() throws Exception {
    	String URI = "/hotels/reservations/new";
    	HotelReservationRequest reservationRequest = createHotelReservationRequest("notFound");
    	String requestJson = convertHotelReservationRequestInJson(reservationRequest);
    	
    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
    			.andExpect(status().is(404));
    }
    
    /*
	 * TESTS on URI /hotels/reservations/confirmedReservation/{reservationId}
	 */
	
    @Test
    public void notConfirmedReservationWithNotPresentId() throws Exception {
    	String URI = "/hotels/reservations/confirmedReservation/1000000";

        mockMvc.perform(put(URI)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }
    
    @Test
    public void notConfirmedReservationWithInvalidId() throws Exception {
    	String URI = "/hotels/reservations/confirmedReservation/0";

        mockMvc.perform(put(URI)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }
    
    /*
     * TESTS on URI /hotels/reservations/reservation/{reservationId}
     */
    /*
     */
    
    @Test
    public void hotelNotDeletedWithInvalidId() throws Exception {
    	String URI = "/hotels/reservations/reservation/-2";

        mockMvc.perform(delete(URI)
                .accept(MediaType.APPLICATION_JSON))
        		.andExpect(status().is(400));
    }
    
    @Test
    public void hotelNotDeletedWithNotPresentId() throws Exception {
    	String URI = "/hotels/reservations/reservation/4000";

        mockMvc.perform(delete(URI)
                .accept(MediaType.APPLICATION_JSON))
        		.andExpect(status().is(404));
    }
    

    private SearchHotelRequest createSearchHotelRequest(String requestType) {
    	SearchHotelRequest request = new SearchHotelRequest();
    	if(requestType != null) {
    		if(requestType.equals("invalid")){
        		request.setCity("");
        	} else {
        		request.setCity("London");
        	}
    	}
    	request.setCheckIn(new CheckTime(13, 3, 2019));
    	request.setCheckOut(new CheckTime(16, 3, 2019));
    	if(requestType != null) {
    		if(requestType.equals("notFound")){
    			request.setHostsNumber(20);
        	} else {
        		request.setHostsNumber(3);
        	}
    	}
    	HotelProperties hotelProperties = new HotelProperties();
    	hotelProperties.setReservationType("standard");
    	request.setHotelProperties(hotelProperties);
    	RoomProperties roomProperties = new RoomProperties();
    	request.setRoomProperties(roomProperties);
    	
    	return request;
    }
    
    private String convertHotelRequestInJson(SearchHotelRequest hotelsRequest) {
    	ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = "";
        
        try {
			requestJson = ow.writeValueAsString(hotelsRequest);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} 
        
        return requestJson;
    }
    
    private HotelReservationRequest createHotelReservationRequest(String typeOfRequest) {
    	HotelReservationRequest reservationRequest = new HotelReservationRequest();
    	if(typeOfRequest != null) {
	    	if(typeOfRequest.equals("valid")) {
	    		reservationRequest.setRoomId(5);
	    		reservationRequest.setUserEmail("lucabazuca@gmail.com");
	    		reservationRequest.setHostsNumber(2);
	    		reservationRequest.setReservationType("with breakfast");
	    		CheckTime checkIn = new CheckTime(27, 3, 2019);
	    		CheckTime checkOut = new CheckTime(29, 3, 2019);
	    		reservationRequest.setCheckIn(checkIn);
	    		reservationRequest.setCheckOut(checkOut);
	    	} else if(typeOfRequest.equals("invalid")){
	    		reservationRequest.setRoomId(5);
	    		reservationRequest.setUserEmail("lucabazuca@gmail.com");
	    		reservationRequest.setHostsNumber(4);
	    		reservationRequest.setReservationType("with breakfast");
	    		CheckTime checkIn = new CheckTime(27, 3, 2019);
	    		CheckTime checkOut = new CheckTime(29, 3, 2019);
	    		reservationRequest.setCheckIn(checkIn);
	    		reservationRequest.setCheckOut(checkOut);
	    	} else if(typeOfRequest.equals("notFound")){
	    		reservationRequest.setRoomId(10000);
	    		reservationRequest.setUserEmail("lucabazuca@gmail.com");
	    		reservationRequest.setHostsNumber(2);
	    		reservationRequest.setReservationType("with breakfast");
	    		CheckTime checkIn = new CheckTime(27, 3, 2019);
	    		CheckTime checkOut = new CheckTime(29, 3, 2019);
	    		reservationRequest.setCheckIn(checkIn);
	    		reservationRequest.setCheckOut(checkOut);
	    	}
    	}
    	
        return reservationRequest;
    }
    
    private String convertHotelReservationRequestInJson(HotelReservationRequest reservationRequest) {
    	ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = "";
        
        try {
			requestJson = ow.writeValueAsString(reservationRequest);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} 
        
        return requestJson;
    }
     

}
