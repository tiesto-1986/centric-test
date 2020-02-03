package com.centric.integration;

import com.centric.dto.ErrorDetails;
import com.centric.dto.ProductDTO;
import com.centric.entity.Product;
import com.centric.repository.ProductRepository;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ProductsControllerTest extends TestsSetup {

    private RestActionHelper restActionHelper;

    @Autowired
    private ProductRepository productRepository;

    @Value("${security.api.key}")
    private String apiKey;

    private final static String CATEGORY_NAME = "category";

    @Before
    public void before() throws Exception {
        super.setup();
        this.restActionHelper = RestActionHelper.start(mvc, objectMapper);
        productRepository.deleteAll();
    }

    @Test
    public void createNewProductWithWrongApiKeyTest() throws Exception {
        ErrorDetails errorDetails = restActionHelper.createProduct(getProductDTO(), "wrong-api-key", HttpStatus.FORBIDDEN, ErrorDetails.class);
        Assert.assertEquals("Api key value is missing or invalid", errorDetails.getMessage());
    }

    @Test
    public void createNewProductValidationTest() throws Exception {
        ProductDTO productDTO = getProductDTO();
        productDTO.setName(null);
        ErrorDetails errorDetails = restActionHelper.createProduct(productDTO, apiKey, HttpStatus.BAD_REQUEST, ErrorDetails.class);
        Assert.assertTrue(errorDetails.getMessage().contains("Product name couldn't be empty"));

        productDTO = getProductDTO();
        productDTO.setBrand(null);
        errorDetails = restActionHelper.createProduct(productDTO, apiKey, HttpStatus.BAD_REQUEST, ErrorDetails.class);
        Assert.assertTrue(errorDetails.getMessage().contains("Brand name couldn't be empty"));

        productDTO = getProductDTO();
        productDTO.setCategory(null);
        errorDetails = restActionHelper.createProduct(productDTO, apiKey, HttpStatus.BAD_REQUEST, ErrorDetails.class);
        Assert.assertTrue(errorDetails.getMessage().contains("Product category couldn't be empty"));
    }

    @Test
    public void createNewProductTest() throws Exception {
        ProductDTO productDTO = restActionHelper.createProduct(getProductDTO(), apiKey, HttpStatus.OK, ProductDTO.class);
        assertProduct(productDTO);

        //just to make sure we will check DAO level
        Product product = productRepository.findById(productDTO.getId()).get();
        assertProduct(product);
    }

    @Test
    public void getProductCountTest() throws Exception {
        String cnt = restActionHelper.getProductCount(apiKey, CATEGORY_NAME);
        Assert.assertEquals("0", cnt);

        restActionHelper.createProduct(getProductDTO(), apiKey, HttpStatus.OK, ProductDTO.class);
        cnt = restActionHelper.getProductCount(apiKey, CATEGORY_NAME);
        Assert.assertEquals("1", cnt);

        //invalid category
        cnt = restActionHelper.getProductCount(apiKey, "fake category");
        Assert.assertEquals("0", cnt);
    }

    @Test
    public void findCategoryByCategoryFilterTest() throws Exception {
        List<ProductDTO> products = restActionHelper.getProductList(apiKey, CATEGORY_NAME, null, null);
        Assert.assertEquals(0, products.size());
        restActionHelper.createProduct(getProductDTO(), apiKey, HttpStatus.OK, ProductDTO.class);

        products = restActionHelper.getProductList(apiKey, CATEGORY_NAME, null, null);
        Assert.assertEquals(1, products.size());
        products = restActionHelper.getProductList(apiKey, "fake-category", null, null);
        Assert.assertEquals(0, products.size());
    }

    @Test
    public void findCategoryByCategoryFilterValidationTest() throws Exception {
        ErrorDetails errorDetails = restActionHelper.getProductList(apiKey, CATEGORY_NAME, "-1", "1", HttpStatus.BAD_REQUEST);
        Assert.assertEquals("Limit is lower then 1", errorDetails.getMessage());

        errorDetails = restActionHelper.getProductList(apiKey, CATEGORY_NAME, "1", "-1", HttpStatus.BAD_REQUEST);
        Assert.assertEquals("PageNumber is lower then 1", errorDetails.getMessage());
    }

    @Test
    public void findCategoryPaginationTest() throws Exception {
        for (int i = 0; i < 3; i++) {
            restActionHelper.createProduct(getProductDTO(), apiKey, HttpStatus.OK, ProductDTO.class);
        }

        String cnt = restActionHelper.getProductCount(apiKey, CATEGORY_NAME);
        Assert.assertEquals("3", cnt);

        List<ProductDTO> products = restActionHelper.getProductList(apiKey, CATEGORY_NAME, "2", "1");
        Assert.assertEquals(2, products.size());
        products = restActionHelper.getProductList(apiKey, CATEGORY_NAME, "2", "2");
        Assert.assertEquals(1, products.size());

        products = restActionHelper.getProductList(apiKey, CATEGORY_NAME, "5", "1");
        Assert.assertEquals(3, products.size());

        products = restActionHelper.getProductList(apiKey, CATEGORY_NAME, null, null);
        Assert.assertEquals(3, products.size());

    }

    private static ProductDTO getProductDTO() {
        return ProductDTO.builder()
                .brand("brand")
                .category(CATEGORY_NAME)
                .description("description")
                .tags(Lists.newArrayList("tag1", "tag2", "tag3"))
                .name("name")
                .build();
    }

    private void assertProduct(ProductDTO productDTO) {
        Assert.assertEquals("name", productDTO.getName());
        Assert.assertEquals("description", productDTO.getDescription());
        Assert.assertEquals(CATEGORY_NAME, productDTO.getCategory());
        Assert.assertEquals("brand", productDTO.getBrand());
        Assert.assertEquals(3, productDTO.getTags().size());
        Assert.assertNotNull(productDTO.getId());
        Assert.assertNotNull(productDTO.getCreatedDate());
        Assert.assertTrue(productDTO.getTags().contains("tag1"));
        Assert.assertTrue(productDTO.getTags().contains("tag2"));
        Assert.assertTrue(productDTO.getTags().contains("tag3"));
    }

    private void assertProduct(Product product) {
        Assert.assertEquals("name", product.getName());
        Assert.assertEquals("description", product.getDescription());
        Assert.assertEquals(CATEGORY_NAME, product.getCategory());
        Assert.assertEquals("brand", product.getBrand());
        Assert.assertEquals(3, product.getTags().size());
        Assert.assertNotNull(product.getId());
        Assert.assertNotNull(product.getCreatedDate());
        Assert.assertTrue(product.getTags().contains("tag1"));
        Assert.assertTrue(product.getTags().contains("tag2"));
        Assert.assertTrue(product.getTags().contains("tag3"));
    }
}
