package pl.mateusz_semklo.automationshoprest.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

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
@EqualsAndHashCode(callSuper = false)
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
    private Date orderDate=new Date(System.currentTimeMillis());

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
    private String orderPostCode;

    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME", nullable = false)
    private User user;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="ORDERS_PRODUCTS",
            joinColumns = @JoinColumn(name="ORDER_ID"),
            inverseJoinColumns = @JoinColumn(name="PRODUCT_ID"))
    List<Product> products =new ArrayList<>();



}
