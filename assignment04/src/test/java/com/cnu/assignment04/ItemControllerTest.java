package com.cnu.assignment04;

import com.cnu.assignment04.utils.AssignmentResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import java.io.IOException;
import static com.cnu.assignment04.utils.AssignmentResultHandler.assignTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private Integer restaurantId;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    public static <T>  Object convertJSONStringToObject(String json, Class<T> objectClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.readValue(json, objectClass);
    }

    private Integer generateRestaurant() throws Exception {
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

        AssignmentResult result = new AssignmentResult();

        this.mockMvc.perform(
                post("/api/restaurants")
                        .content(restaurant.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(assignTo("$.data.id", result));

        return (Integer)result.getValue();
    }

    private Integer generateItem(JSONObject item, Integer restaurantId) throws Exception {
        AssignmentResult result = new AssignmentResult();

        this.mockMvc.perform(
                post("/api/restaurants/" + restaurantId + "/items")
                        .content(item.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(assignTo("$.data.id", result));

        return (Integer)result.getValue();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesItemController() {
        ServletContext servletContext = wac.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("itemController"));
    }

    @Test
    public void givenItemURIWithPostAndFormData_whenMockMVC_thenResponseOK() throws Exception {
        Integer restaurantId = generateRestaurant();

        JSONObject item = new JSONObject()
                .put("name", "Lasagna Verdure")
                .put("price", "49.99");

        this.mockMvc.perform(
                post("/api/restaurants/" + restaurantId + "/items")
                        .content(item.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.id").isNumber())
                .andExpect(jsonPath("$.data.name").value(item.get("name")))
                .andExpect(jsonPath("$.data.price").value(item.get("price")));

    }

    @Test
    public void givenItemURIWithPostAndInvalidFormData_whenMockMVC_thenResponseBadRequest() throws Exception {
        Integer restaurantId = generateRestaurant();

        JSONObject item = new JSONObject()
                .put("name", "afewafew");

        this.mockMvc.perform(
                post("/api/restaurants/" + restaurantId + "/items")
                        .content(item.toString())
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
    public void givenItemURIWithGetAndId_whenMockMVC_thenResponseOK() throws Exception {
        Integer restaurantId = generateRestaurant();

        JSONObject item = new JSONObject()
                .put("name", "Lasagna Verdure")
                .put("price", "49.99");

        Integer itemId = generateItem(item, restaurantId);

        this.mockMvc.perform(
                get("/api/restaurants/" + restaurantId + "/items/" + itemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.id").isNumber())
                .andExpect(jsonPath("$.data.name").value(item.get("name")))
                .andExpect(jsonPath("$.data.price").value(item.get("price")));

    }

    @Test
    public void givenItemURIWithGetAndInvalidId_whenMockMVC_thenResponseNotFound() throws Exception {
        Integer restaurantId = generateRestaurant();

        this.mockMvc.perform(
                get("/api/restaurants/" + restaurantId + "/items/-1")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenItemURIWithDeleteAndId_whenMockMVC_thenResponseOK() throws Exception {
        Integer restaurantId = generateRestaurant();

        JSONObject item = new JSONObject()
                .put("name", "Lasagna Verdure")
                .put("price", "49.99");

        Integer itemId = generateItem(item, restaurantId);

        this.mockMvc.perform(
                delete("/api/restaurants/" + restaurantId + "/items/" + itemId)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void givenItemURIWithDeleteAndInvalidId_whenMockMVC_thenResponseIsNotFound() throws Exception {
        Integer restaurantId = generateRestaurant();
        this.mockMvc.perform(
                delete("/api/restaurants/" + restaurantId + "/items/-1")
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenItemURIWithPutAndIdAndFormdata_whenMockMVC_thenResponseOK() throws Exception {
        Integer restaurantId = generateRestaurant();

        JSONObject item = new JSONObject()
                .put("name", "Lasagna Verdure")
                .put("price", "49.99");

        Integer itemId = generateItem(item, restaurantId);

        JSONObject newItem = new JSONObject()
                .put("name", "Pasta")
                .put("price", "24.99");

        this.mockMvc.perform(
                put("/api/restaurants/" + restaurantId + "/items/" + itemId)
                        .content(newItem.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    public void givenItemURIWithPutAndInvalidIdAndFormdata_whenMockMVC_thenResponseBadRequest() throws Exception {
        Integer restaurantId = generateRestaurant();

        JSONObject item = new JSONObject()
                .put("name", "Lasagna Verdure")
                .put("price", "49.99");

        this.mockMvc.perform(
                put("/api/restaurants/" + restaurantId + "/items/-1")
                        .content(item.toString())
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
    public void givenItemURIWithPutAndIdAndInvalidFormdata_whenMockMVC_thenResponseBadRequest() throws Exception {
        Integer restaurantId = generateRestaurant();

        JSONObject item = new JSONObject()
                .put("name", "Lasagna Verdure")
                .put("price", "49.99");

        Integer itemId = generateItem(item, restaurantId);

        JSONObject newItem = new JSONObject()
                .put("name", "yo");

        this.mockMvc.perform(
                put("/api/restaurants/" + restaurantId + "/items/" + itemId)
                        .content(newItem.toString())
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
    public void givenItemURIWithGetAndSearchdata_whenMockMVC_thenResponseOK() throws Exception {
        Integer restaurantId = generateRestaurant();

        JSONObject item1 = new JSONObject()
                .put("name", "Lasagna Verdure")
                .put("price", "49.99");

        Integer itemId1 = generateItem(item1, restaurantId);

        JSONObject item2 = new JSONObject()
                .put("name", "Pasta")
                .put("price", "24.99");

        Integer itemId2 = generateItem(item2, restaurantId);


        this.mockMvc.perform(
                get("/api/items/?name=" + item1.get("name"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data[0].id").isNumber())
                .andExpect(jsonPath("$.data[0].name").value(item1.get("name")))
                .andExpect(jsonPath("$.data[0].price").value(item1.get("price")));

        this.mockMvc.perform(
                get("/api/items/?maxPrice=" + "30")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data[0].id").isNumber())
                .andExpect(jsonPath("$.data[0].name").value(item2.get("name")))
                .andExpect(jsonPath("$.data[0].price").value(item2.get("price")));

    }
}