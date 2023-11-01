package pl.mateusz_semklo.automationshoprest.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import pl.mateusz_semklo.automationshoprest.config.ConfigProperties;

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
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "productId",scope = Product.class)
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
    @JsonBackReference
    @JoinColumn(name = "CATEGORY_NAME", referencedColumnName = "CATEGORY_NAME", nullable = false)
    private Category category;

}
