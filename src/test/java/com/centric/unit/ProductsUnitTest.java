package com.centric.unit;

import com.centric.dto.ProductDTO;
import com.centric.entity.Product;
import com.centric.repository.ProductRepository;
import com.centric.service.ProductService;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ProductsUnitTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void createProductTest() {
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(Product.builder()
                .name("name")
                .description("description")
                .brand("brand")
                .id("aaa")
                .createdDate(new Date())
                .build());
        ProductDTO productDTO = productService.createProduct(ProductDTO.builder()
                .name("name")
                .description("description")
                .brand("brand")
                .build());

        Assert.assertNotNull(productDTO.getCreatedDate());
        Assert.assertNull(productDTO.getTags());
        Assert.assertEquals("name", productDTO.getName());
        Assert.assertEquals("description", productDTO.getDescription());
        Assert.assertEquals("brand", productDTO.getBrand());
        Assert.assertEquals("aaa", productDTO.getId());
    }

    @Test
    public void findProductsEmptyTest() {
        Mockito.when(productRepository.findProducts(Mockito.eq("category"), Mockito.any(), Mockito.any()))
                .thenReturn(Lists.newArrayList());
        List<ProductDTO> productDTOList = productService.findProducts("category", 1, 10);

        Assert.assertEquals(0, productDTOList.size());
    }

    @Test
    public void countProductsTest() {
        Mockito.when(productRepository.getProductsCount(Mockito.eq("category")))
                .thenReturn(1);
        Integer cnt = productService.findProductsCount("category");

        Assert.assertEquals(1, cnt.intValue());
    }
}
