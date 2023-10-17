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
public class Products implements Serializable {

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
    private Categories category;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrdersProducts> ordersProductsList=new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Products products = (Products) o;
        return getProductPrice() == products.getProductPrice() && Objects.equals(getProductId(), products.getProductId()) && Objects.equals(getProductName(), products.getProductName()) && Objects.equals(getProductDescription(), products.getProductDescription()) && Objects.equals(getProductImageUrl(), products.getProductImageUrl()) && Objects.equals(getCategory(), products.getCategory()) && Objects.equals(getOrdersProductsList(), products.getOrdersProductsList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getProductName(), getProductDescription(), getProductImageUrl(), getProductPrice(), getCategory(), getOrdersProductsList());
    }
}
