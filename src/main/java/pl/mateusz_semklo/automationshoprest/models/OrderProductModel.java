package pl.mateusz_semklo.automationshoprest.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import pl.mateusz_semklo.automationshoprest.entities.Order;
import pl.mateusz_semklo.automationshoprest.entities.Product;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName(value = "category")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderProductModel {
    Order order;

}
