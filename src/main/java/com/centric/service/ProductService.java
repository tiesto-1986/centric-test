package com.centric.service;

import com.centric.converters.ProductConverter;
import com.centric.dto.ProductDTO;
import com.centric.entity.Product;
import com.centric.exception.CentricException;
import com.centric.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Will create product according to DTO
     * NOTE! There is no constraint in the code or in database level about unique name or brand name
     * @param productDTO product model to create
     * @return
     */
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product createdProduct = productRepository.save(ProductConverter.toEntity(productDTO));
        return ProductConverter.fromEntity(createdProduct);
    }

    /**
     * Will return pagenated list of products ordered (DESC) by created Date
     * @param category the name of category to filter data
     * @param pageNumber optional. Default is 1 - page number
     * @param limit optional parameter. Default is 20 - amount of products per page
     * @return
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> findProducts(String category, Integer pageNumber, Integer limit) {
        if (pageNumber < 1) {
            throw new CentricException("PageNumber is lower then 1", HttpStatus.BAD_REQUEST);
        }
        if (limit < 1) {
            throw new CentricException("Limit is lower then 1", HttpStatus.BAD_REQUEST);
        }
        Integer offset = (pageNumber - 1) * limit;
        List<Product> products = productRepository.findProducts(category,  limit, offset);

        return products.stream()
                .map(ProductConverter::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * To apply proper pagination this service method which is related to HEAD endpoint is usefull
     * @param category name of category to applly filter
     * @return
     */
    @Transactional(readOnly = true)
    public Integer findProductsCount(String category) {
        return productRepository.getProductsCount(category);
    }
}
