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

    private static final long serialVersionUID = 123459167L;

    @Getter
    @Setter
    @Embeddable
    public static class Id implements Serializable {

        private static final long serialVersionUID = 12367L;

        @Column(name = "ORDER_ID")
        public Integer orderId;

        @Column(name = "PRODUCT_ID")
        public Integer productId;

        public Id() {
        }

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
    public Id id = new Id();

    public OrdersProducts(Products product, Orders order) {

        this.id.orderId = order.getOrderId();
        this.id.productId = product.getProductId();

        this.order = order;
        this.product = product;

        product.getOrdersProductsList().add(this);
        order.getOrdersProductsList().add(this);

    }

    @ManyToOne
    @MapsId("id.orderId")
    private Orders order;

    @ManyToOne
    @MapsId("id.productId")
    private Products product;

}
