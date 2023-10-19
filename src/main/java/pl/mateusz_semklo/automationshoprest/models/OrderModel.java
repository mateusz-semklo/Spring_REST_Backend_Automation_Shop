package pl.mateusz_semklo.automationshoprest.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import pl.mateusz_semklo.automationshoprest.entities.Product;
import pl.mateusz_semklo.automationshoprest.entities.User;

import java.sql.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName(value = "order")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderModel extends RepresentationModel<OrderModel> {

    private Integer orderId;
    private Date orderDate;
    private String orderStreet;
    private String orderCity;
    private String orderCountry;
    private String ordesPostCode;
    private User user;
    private List<Product> products;
    //private List<OrderProduct> ordersProductsList;

}
