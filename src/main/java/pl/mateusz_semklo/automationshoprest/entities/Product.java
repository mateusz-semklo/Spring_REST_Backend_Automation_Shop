package pl.mateusz_semklo.automationshoprest.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="PRODUCTS")
@EqualsAndHashCode(callSuper = false)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "productId",scope = Product.class)
public class Product implements Serializable {

    private static final long serialVersionUID = 1234567L;

    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "MySequencex")
    @SequenceGenerator(name="MySequencex",sequenceName = "MYSEQ",allocationSize = 1)
    @Id
    @Column(name = "PRODUCT_ID")
    private Integer productId;

    @Basic
    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Basic
    @Column(name = "PRODUCT_DESCRIPTION")
    private String productDescription;

    @Basic
    @Column(name = "PRODUCT_IMAGE_URL")
    private String productImageUrl;

    @Basic
    @Column(name = "PRODUCT_PRICE")
    private int productPrice;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "CATEGORY_ID", nullable = false)
    private Category category;

    @ManyToMany(mappedBy = "products",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    List<Order> orders=new ArrayList<>();

}
