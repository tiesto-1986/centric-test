package com.centric.converters;

import com.centric.dto.ProductDTO;
import com.centric.entity.Product;

import java.util.Date;
import java.util.UUID;

public class ProductConverter {

    public static Product toEntity(ProductDTO productDTO) {
        return Product.builder()
                .brand(productDTO.getBrand())
                .category(productDTO.getCategory())
                .description(productDTO.getDescription())
                .name(productDTO.getName())
                .tags(productDTO.getTags())
                .id(UUID.randomUUID().toString())
                .createdDate(new Date())
                .build();
    }

    public static ProductDTO fromEntity(Product product) {
        return ProductDTO.builder()
                .brand(product.getBrand())
                .category(product.getCategory())
                .description(product.getDescription())
                .name(product.getName())
                .tags(product.getTags())
                .id(product.getId())
                .createdDate(product.getCreatedDate())
                .build();
    }
}
