package pl.mateusz_semklo.automationshoprest.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;
import pl.mateusz_semklo.automationshoprest.config.ConfigProperties;
import pl.mateusz_semklo.automationshoprest.entities.Category;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName(value = "product")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductModel extends RepresentationModel<ProductModel>  {

    private Integer productId;
    private String productName;
    private String productDescription;
    private String productImageUrl;
    private int productPrice;
    private Category category;

}
