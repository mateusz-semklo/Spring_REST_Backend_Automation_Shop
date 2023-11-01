package pl.mateusz_semklo.automationshoprest.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import pl.mateusz_semklo.automationshoprest.entities.Category;
import pl.mateusz_semklo.automationshoprest.entities.Product;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName(value = "cart")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartModel extends RepresentationModel<CartModel>  {

    private int cartProductId;
    private Integer count;
    private Product productId;

}
