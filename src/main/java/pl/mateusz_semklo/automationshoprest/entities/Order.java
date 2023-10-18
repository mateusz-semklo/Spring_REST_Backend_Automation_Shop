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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "orderId",scope = Order.class)
public class Order implements Serializable {
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
    @Column(name = "ORDER_POST_CODE")
    private String ordesPostCode;

    @ManyToOne(optional = false)
    @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME", nullable = false)
    private User user;


    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    private List<OrderProduct> ordersProductsList=new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(getOrderId(), order.getOrderId()) && Objects.equals(getOrderDate(), order.getOrderDate()) && Objects.equals(getOrderStreet(), order.getOrderStreet()) && Objects.equals(getOrderCity(), order.getOrderCity()) && Objects.equals(getOrderCountry(), order.getOrderCountry()) && Objects.equals(getOrdesPostCode(), order.getOrdesPostCode()) && Objects.equals(getUser(), order.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderId(), getOrderDate(), getOrderStreet(), getOrderCity(), getOrderCountry(), getOrdesPostCode(), getUser());
    }

    public List<Product> addProducts(List<Product> productsList){
        productsList.forEach((products -> this.ordersProductsList.add(new OrderProduct(products,this))));
        return this.getOrdersProductsList().stream().map((orderProduct -> orderProduct.getProduct())).toList();
    }
    public List<Product> getProducts(){
        return this.getOrdersProductsList().stream().map((orderProduct -> orderProduct.getProduct())).toList();
    }
    public void removeAllProducts(){
        this.ordersProductsList.clear();
    }
    public void removeProductById(Integer product_id){
         OrderProduct orderProductx=this.ordersProductsList.stream().filter((orderProduct -> orderProduct.getId().equals(new OrderProduct.Id(this.orderId,product_id))))
                .toList().get(0);
        this.ordersProductsList.remove(orderProductx);
    }

}
