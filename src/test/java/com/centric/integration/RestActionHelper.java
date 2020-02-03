package com.centric.integration;

import com.centric.dto.ErrorDetails;
import com.centric.dto.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Ignore
public class RestActionHelper extends TestsSetup {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private RestActionHelper(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    public static RestActionHelper start(MockMvc mockMvc, ObjectMapper objectMapper) {
        return new RestActionHelper(mockMvc, objectMapper);
    }

    public <T> T createProduct(ProductDTO productDTO, String apikey, HttpStatus httpStatus, Class<T> returnType) throws Exception{
        MvcResult result = mockMvc.perform(post("/v1/products")
                .header("apikey", apikey)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().is(httpStatus.value())).andReturn();

        return  getObjectFromResponse(result, returnType);
    }

    public List<ProductDTO> getProductList(String apikey, String category, String limit, String pageNumber) throws Exception{
        MvcResult result =  mockMvc.perform(get("/v1/products")
                .param("category", category)
                .param("pageNumber", pageNumber)
                .param("limit", limit)
                .header("apikey", apikey)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        return  getListFromResponse(result, ProductDTO.class);
    }

    public ErrorDetails getProductList(String apikey, String category, String limit, String pageNumber, HttpStatus httpStatus) throws Exception{
        MvcResult result =  mockMvc.perform(get("/v1/products")
                .param("category", category)
                .param("pageNumber", pageNumber)
                .param("limit", limit)
                .header("apikey", apikey)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(httpStatus.value())).andReturn();

        return  getObjectFromResponse(result, ErrorDetails.class);
    }

    public String getProductCount(String apikey, String category) throws Exception{
        return mockMvc.perform(head("/v1/products?category=" + category)
                .header("apikey", apikey)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getHeader("X-Total-Count");
    }
}
