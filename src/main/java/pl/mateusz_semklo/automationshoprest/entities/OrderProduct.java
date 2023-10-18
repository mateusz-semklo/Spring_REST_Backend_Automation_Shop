package pl.mateusz_semklo.automationshoprest.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ORDERS_PRODUCTS")
public class OrderProduct implements Serializable {

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


    @ManyToOne
    @JoinColumn(name="ORDER_ID",insertable = false,updatable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name="PRODUCT_ID",insertable = false,updatable = false)
    private Product product;

    public OrderProduct(Product product, Order order) {

        this.id.orderId = order.getOrderId();
        this.id.productId = product.getProductId();

        this.order = order;
        this.product = product;

        product.getOrdersProductsList().add(this);
        order.getOrdersProductsList().add(this);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProduct that = (OrderProduct) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getOrder(), that.getOrder()) && Objects.equals(getProduct(), that.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOrder(), getProduct());
    }
}
