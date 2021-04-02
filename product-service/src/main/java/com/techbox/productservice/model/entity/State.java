package com.techbox.productservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "PRODUCT", name = "PRODUCT_STATE")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_seq_gen")
    @SequenceGenerator(name = "brand_seq_gen", sequenceName = "brand_id_seq",allocationSize = 1)
    @Column(name="ID")
    private Long id;

    @Column(name = "NAME",length = 50, unique = true)
    private String name;

}
