package pl.mateusz_semklo.automationshoprest.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import pl.mateusz_semklo.automationshoprest.entities.Category;
import pl.mateusz_semklo.automationshoprest.entities.OrderProduct;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName(value = "product")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductModel extends RepresentationModel<ProductModel> {

    private Integer productId;
    private String productName;
    private String productDescription;
    private String productImageUrl;
    private int productPrice;
    private Category category;
    //private List<OrderProduct> ordersProductsList;

}
