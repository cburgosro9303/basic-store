package com.techbox.productservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "PRODUCT", name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_gen")
    @SequenceGenerator(name = "product_seq_gen", sequenceName = "product.product_id_seq",allocationSize = 1)
    @Column(name="ID")
    private Long id;

    @Column(name = "NAME",length = 100, unique = true)
    @NotAudited
    private String name;

    @Column(name = "PRICE", precision = 21, scale = 6)
    @NotAudited
    private BigDecimal price;

    @Column(name = "STOCK")
    private Long stock;

    @Column(name = "discount", precision = 4, scale = 3)
    @NotAudited
    private Float discount;

    @ManyToOne
    @JoinColumn(name="PRODUCT_STATE_ID", nullable=false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private State state;

    @ManyToOne
    @JoinColumn(name="BRAND_ID", nullable=false)
    @NotAudited
    private Brand brand;

}
