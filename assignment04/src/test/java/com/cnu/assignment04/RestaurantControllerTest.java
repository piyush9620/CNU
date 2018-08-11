package com.cnu.assignment04;

import com.cnu.assignment04.entities.Restaurant;
import com.cnu.assignment04.repositories.RestaurantRepository;
import com.cnu.assignment04.response.SuccessResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestaurantControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    public static <T>  Object convertJSONStringToObject(String json, Class<T> objectClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.readValue(json, objectClass);
    }

    private int generateRestaurant(JSONObject restaurant) throws Exception {
        this.mockMvc.perform(
                post("/api/restaurants")
                        .content(restaurant.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        return 1;
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesRestaurantController() {
        ServletContext servletContext = wac.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("restaurantController"));
    }

    private RestaurantRepository mockRestaurantRepository;

    @Test
    public void givenRestaurantURIWithPostAndFormData_whenMockMVC_thenResponseOK() throws Exception {
        JSONArray cuisines = new JSONArray()
                .put("italian")
                .put("continental");

        JSONObject restaurant = new JSONObject()
                .put("name", "Flying Spaghetti Monster")
                .put("rating", 8.1)
                .put("latitude", 8.121212)
                .put("longitude", 5.35239)
                .put("cuisines", cuisines)
                .put("is_open", true)
                .put("city", "Wadiya");


        this.mockMvc.perform(
                        post("/api/restaurants")
                        .content(restaurant.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.id").isNumber())
                .andExpect(jsonPath("$.data.name").value(restaurant.get("name")))
                .andExpect(jsonPath("$.data.rating").value(restaurant.get("rating")))
                .andExpect(jsonPath("$.data.latitude").value(restaurant.get("latitude")))
                .andExpect(jsonPath("$.data.longitude").value(restaurant.get("longitude")))
                .andExpect(jsonPath("$.data.cuisines").value(Matchers.hasSize(cuisines.length())))
                .andExpect(jsonPath("$.data.is_open").value(restaurant.get("is_open")))
                .andExpect(jsonPath("$.data.city").value(restaurant.get("city")));

    }

    @Test
    public void givenRestaurantURIWithPostAndInvalidFormData_whenMockMVC_thenResponseBadRequest() throws Exception {
        JSONObject restaurant = new JSONObject()
                .put("name", "afewafew");


        this.mockMvc.perform(
                        post("/api/restaurants")
                        .content(restaurant.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status").value("failed"))
                .andExpect(jsonPath("$.reason").isString());

    }

    @Test
    public void givenRestaurantURIWithGetAndId_whenMockMVC_thenResponseOK() throws Exception {
        JSONArray cuisines = new JSONArray()
                .put("italian")
                .put("continental");

        JSONObject restaurant = new JSONObject()
                .put("name", "Flying Spaghetti Monster")
                .put("rating", 8.1)
                .put("latitude", 8.121212)
                .put("longitude", 5.35239)
                .put("cuisines", cuisines)
                .put("is_open", true)
                .put("city", "Wadiya");

        Integer restaurantId = generateRestaurant(restaurant);

        this.mockMvc.perform(
                        get("/api/restaurants/" + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.id").isNumber())
                .andExpect(jsonPath("$.data.name").value(restaurant.get("name")))
                .andExpect(jsonPath("$.data.rating").value(restaurant.get("rating")))
                .andExpect(jsonPath("$.data.latitude").value(restaurant.get("latitude")))
                .andExpect(jsonPath("$.data.longitude").value(restaurant.get("longitude")))
                .andExpect(jsonPath("$.data.cuisines").value(Matchers.hasSize(cuisines.length())))
                .andExpect(jsonPath("$.data.is_open").value(restaurant.get("is_open")))
                .andExpect(jsonPath("$.data.city").value(restaurant.get("city")));

    }

    @Test
    public void givenRestaurantURIWithGetAndInvalidId_whenMockMVC_thenResponseNotFound() throws Exception {
        this.mockMvc.perform(
                get("/api/restaurants/-1")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenRestaurantURIWithDeleteAndId_whenMockMVC_thenResponseOK() throws Exception {
        JSONArray cuisines = new JSONArray()
                .put("italian")
                .put("continental");

        JSONObject restaurant = new JSONObject()
                .put("name", "Flying Spaghetti Monster")
                .put("rating", 8.1)
                .put("latitude", 8.121212)
                .put("longitude", 5.35239)
                .put("cuisines", cuisines)
                .put("is_open", true)
                .put("city", "Wadiya");

        Integer restaurantId = generateRestaurant(restaurant);

        this.mockMvc.perform(
                        delete("/api/restaurants/" + restaurantId)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void givenRestaurantURIWithDeleteAndInvalidId_whenMockMVC_thenResponseIsNotFound() throws Exception {
        this.mockMvc.perform(
                        delete("/api/restaurants/-1")
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenRestaurantURIWithPutAndIdAndFormdata_whenMockMVC_thenResponseOK() throws Exception {
        JSONArray cuisines = new JSONArray()
                .put("italian")
                .put("continental");


        JSONObject restaurant = new JSONObject()
                .put("name", "Flying Spaghetti Monster")
                .put("rating", 8.1)
                .put("latitude", 8.122)
                .put("longitude", 5.359)
                .put("cuisines", cuisines)
                .put("is_open", true)
                .put("city", "Wadiya");

        Integer restaurantId = generateRestaurant(restaurant);

        JSONObject newRestaurant = new JSONObject()
                .put("name", "Flying Stti Monster")
                .put("rating", 82.1)
                .put("latitude", 82.212)
                .put("longitude", 52.239)
                .put("is_open", true)
                .put("city", "Wadifrferwya");


        this.mockMvc.perform(
                        put("/api/restaurants/" + restaurantId)
                        .content(newRestaurant.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.id").value(restaurantId))
                .andExpect(jsonPath("$.data.name").value(newRestaurant.get("name")))
                .andExpect(jsonPath("$.data.rating").value(newRestaurant.get("rating")))
                .andExpect(jsonPath("$.data.latitude").value(newRestaurant.get("latitude")))
                .andExpect(jsonPath("$.data.longitude").value(newRestaurant.get("longitude")))
                .andExpect(jsonPath("$.data.is_open").value(newRestaurant.get("is_open")))
                .andExpect(jsonPath("$.data.city").value(newRestaurant.get("city")));
    }

    @Test
    public void givenRestaurantURIWithPutAndInvalidIdAndFormdata_whenMockMVC_thenResponseBadRequest() throws Exception {
        JSONArray cuisines = new JSONArray()
                .put("italian")
                .put("continental");

        JSONObject restaurant = new JSONObject()
                .put("name", "Flying Spaghetti Monster")
                .put("rating", 8.1)
                .put("latitude", 8.121212)
                .put("longitude", 5.35239)
                .put("cuisines", cuisines)
                .put("is_open", true)
                .put("city", "Wadiya");

        this.mockMvc.perform(
                        put("/api/restaurants/-1")
                        .content(restaurant.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status").value("failed"))
                .andExpect(jsonPath("$.reason").isString());
    }

    @Test
    public void givenRestaurantURIWithPutAndIdAndInvalidFormdata_whenMockMVC_thenResponseBadRequest() throws Exception {
        JSONArray cuisines = new JSONArray()
                .put("italian")
                .put("continental");

        JSONObject restaurant = new JSONObject()
                .put("name", "Flying Spaghetti Monster")
                .put("rating", 8.1)
                .put("latitude", 8.121212)
                .put("longitude", 5.35239)
                .put("cuisines", cuisines)
                .put("is_open", true)
                .put("city", "Wadiya");

        Integer restaurantId = generateRestaurant(restaurant);

        JSONObject newRestaurant = new JSONObject()
                .put("name", "Flying Spaghetti Monster");

        this.mockMvc.perform(
                put("/api/restaurants/" + restaurantId)
                        .content(newRestaurant.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status").value("failed"))
                .andExpect(jsonPath("$.reason").isString());
    }

    @Test
    public void givenRestaurantURIWithGetAndSearchdata_whenMockMVC_thenResponseOK() throws Exception {
        JSONArray cuisines1 = new JSONArray()
                .put("italian")
                .put("continental");

        JSONObject restaurant1 = new JSONObject()
                .put("name", "Flying Spaghetti Monster")
                .put("rating", 8.1)
                .put("latitude", 8.121212)
                .put("longitude", 5.35239)
                .put("cuisines", cuisines1)
                .put("is_open", true)
                .put("city", "Wadiya");

        Integer restaurantId1 = generateRestaurant(restaurant1);

        JSONArray cuisines2 = new JSONArray()
                .put("indian")
                .put("arabian");

        JSONObject restaurant2 = new JSONObject()
                .put("name", "Pirate")
                .put("rating", 25)
                .put("latitude", 8.121212)
                .put("longitude", 5.35239)
                .put("cuisines", cuisines2)
                .put("is_open", true)
                .put("city", "Badiya");

        Integer restaurantId2 = generateRestaurant(restaurant2);



    }


}