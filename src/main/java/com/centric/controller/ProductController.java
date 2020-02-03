package com.centric.controller;

import com.centric.annotations.SecurityInterceptor;
import com.centric.dto.ProductDTO;
import com.centric.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value="Product Controller")
@RestController
@RequestMapping("/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    private static final String TOTAL_COUNT_HEADER_KEY = "X-Total-Count";

    @ApiOperation(value = "Create new product endpoint - will return created product", response = ProductDTO.class)
    @RequestMapping(method = RequestMethod.POST)
    @SecurityInterceptor
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductDTO createdProductDTO = productService.createProduct(productDTO);
        return new ResponseEntity(createdProductDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Find products endpoint - will return list of endpoint by category name. Pagination params are optionally", response = List.class)
    @RequestMapping(method = RequestMethod.GET)
    @SecurityInterceptor
    public ResponseEntity<List<ProductDTO>> findProducts(@RequestParam("category") String category,
                                                         @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
                                                         @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit) {
        List<ProductDTO> productDTOList = productService.findProducts(category, pageNumber, limit);
        return new ResponseEntity(productDTOList, HttpStatus.OK);
    }

    @ApiOperation(value = "Find products counts - amount of products by category name will be returned in response header in param X-Total-Count")
    @RequestMapping(method = RequestMethod.HEAD)
    @SecurityInterceptor
    public ResponseEntity findProductsCount(@RequestParam("category") String category) {
        Integer count = productService.findProductsCount(category);
        HttpHeaders responseHeaders = new HttpHeaders();

        responseHeaders.add(TOTAL_COUNT_HEADER_KEY, count.toString());
        responseHeaders.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, TOTAL_COUNT_HEADER_KEY);
        return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
    }
}
