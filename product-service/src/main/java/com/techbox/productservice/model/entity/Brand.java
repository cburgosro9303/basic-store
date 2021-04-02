package com.techbox.productservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "PRODUCT", name = "BRAND")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_seq_gen")
    @SequenceGenerator(name = "brand_seq_gen", sequenceName = "product.brand_id_seq",allocationSize = 1)
    @Column(name="ID")
    private Long id;

    @Column(name = "NAME",length = 100, unique = true)
    private String name;

}
