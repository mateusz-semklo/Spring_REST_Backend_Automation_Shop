package pl.mateusz_semklo.automationshoprest.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "CART_PRODUCTS", schema = "PUBLIC", catalog = "DATASHOP")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1234567L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CART_PRODUCT_ID")
    private int cartProductId;

    @Basic
    @Column(name = "COUNT")
    private Integer count;

    @OneToOne(optional = false)
    @JoinColumn(name="PRODUCT_ID")
    private Product productId;


}
