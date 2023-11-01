package pl.mateusz_semklo.automationshoprest.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "CART_PRODUCTS")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cartProductId",scope = Cart.class)
public class Cart implements Serializable {

    private static final long serialVersionUID = 1234567L;

    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "MySequencex")
    @SequenceGenerator(name="MySequencex",sequenceName = "MYSEQ",allocationSize = 1)
    @Id
    @Column(name = "CART_PRODUCT_ID")
    private int cartProductId;

    @Basic
    @Column(name = "COUNT")
    private Integer count;

    @ManyToOne(optional = false)
    @JoinColumn(name="PRODUCT_ID")
    private Product product;



}
