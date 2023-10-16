package pl.mateusz_semklo.automationshoprest.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Immutable;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ORDERS_PRODUCTS")
public class OrdersProducts implements Serializable {

    private static final long serialVersionUID = 1234567L;

    @Getter
    @Setter
    @Embeddable
    public static class Id implements Serializable{

        @Basic
        @Column(name="ORDER_ID")
        private Integer orderId;

        @Basic
        @Column(name="PRODUCT_ID")
        private Integer productId;

        public Id(){}

        public Id(Integer orderId, Integer productId) {
            this.orderId = orderId;
            this.productId = productId;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Id id = (Id) o;
            return orderId.equals(id.orderId) && productId.equals(id.productId);
        }
        @Override
        public int hashCode() {
            return Objects.hash(orderId, productId);
        }
    }
    @EmbeddedId
    protected Id id=new Id();

    @ManyToOne
    @JoinColumn(name = "ORDER_ID", referencedColumnName = "ORDER_ID", nullable = false,insertable = false,updatable = false)
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID", nullable = false,insertable = false,updatable = false)
    private Products product;

    public OrdersProducts(Products product,Orders order){
        this.order=order;
        this.product=product;

        this.id.setProductId(product.getProductId());
        this.id.setOrderId(order.getOrderId());
        
        product.getOrdersProducts().add(this);
        order.getOrdersProducts().add(this);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdersProducts that = (OrdersProducts) o;
        return getId().equals(that.getId()) && Objects.equals(getOrder(), that.getOrder()) && Objects.equals(getProduct(), that.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOrder(), getProduct());
    }
}
