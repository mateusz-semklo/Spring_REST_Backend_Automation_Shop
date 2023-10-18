package pl.mateusz_semklo.automationshoprest.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToMany(mappedBy = "product",fetch = FetchType.EAGER)
    private List<OrderProduct> ordersProductsList=new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return getProductPrice() == product.getProductPrice() && Objects.equals(getProductId(), product.getProductId()) && Objects.equals(getProductName(), product.getProductName()) && Objects.equals(getProductDescription(), product.getProductDescription()) && Objects.equals(getProductImageUrl(), product.getProductImageUrl()) && Objects.equals(getCategory(), product.getCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getProductName(), getProductDescription(), getProductImageUrl(), getProductPrice(), getCategory());
    }
}