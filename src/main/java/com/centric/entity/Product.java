package com.centric.entity;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@TypeDefs(@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class))
public class Product {

    @Id
    private String id;

    private String name;

    private String description;

    private String brand;

    private String category;

    @Column(name = "created_date")
    private Date createdDate;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", name="tags")
    private List<String> tags;

}
