package com.centric.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Product model")
public class ProductDTO {

    @ApiModelProperty(notes = "Brand name of product", required = true)
    @NotEmpty(message = "Brand name couldn't be empty")
    private String brand;

    @ApiModelProperty(notes = "Name of product", required = true)
    @NotEmpty(message = "Product name couldn't be empty")
    private String name;

    @ApiModelProperty(notes = "Category name of product", required = true)
    @NotEmpty(message = "Product category couldn't be empty")
    private String category;

    @ApiModelProperty(notes = "List of tags for product")
    private List<String> tags;

    @ApiModelProperty(notes = "Product description")
    private String description;

    @ApiModelProperty(notes = "Product unique ID")
    private String id;

    @ApiModelProperty(notes = "Product created Date. Will be represented in ISO-8601 format")
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")//ISO-8601
    private Date createdDate;

}
