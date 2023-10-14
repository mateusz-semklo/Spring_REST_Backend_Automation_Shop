package pl.mateusz_semklo.automationshoprest.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="ORDERS")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "orderId",scope = Orders.class)
public class Orders implements Serializable {
    private static final long serialVersionUID = 1234567L;

    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "MySequencex")
    @SequenceGenerator(name="MySequencex",sequenceName = "MYSEQ",allocationSize = 1)
    @Id
    @Column(name = "ORDER_ID")
    private Integer orderId;

    @Basic
    @Column(name = "ORDER_DATE")
    private Date orderDate;

    @Basic
    @Column(name = "ORDER_STREET")
    private String orderStreet;

    @Basic
    @Column(name = "ORDER_CITY")
    private String orderCity;

    @Basic
    @Column(name = "ORDER_COUNTRY")
    private String orderCountry;

    @Basic
    @Column(name = "ORDES_POST_CODE")
    private String ordesPostCode;


    @ManyToOne(optional = false)
    @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME", nullable = false,updatable = false,insertable = false)
    private Users user;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrdersProducts> ordersProducts=new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return Objects.equals(getOrderId(), orders.getOrderId()) && Objects.equals(getOrderDate(), orders.getOrderDate()) && Objects.equals(getOrderStreet(), orders.getOrderStreet()) && Objects.equals(getOrderCity(), orders.getOrderCity()) && Objects.equals(getOrderCountry(), orders.getOrderCountry()) && Objects.equals(getOrdesPostCode(), orders.getOrdesPostCode()) && Objects.equals(getUser(), orders.getUser()) && Objects.equals(getOrdersProducts(), orders.getOrdersProducts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderId(), getOrderDate(), getOrderStreet(), getOrderCity(), getOrderCountry(), getOrdesPostCode(), getUser(), getOrdersProducts());
    }
}
