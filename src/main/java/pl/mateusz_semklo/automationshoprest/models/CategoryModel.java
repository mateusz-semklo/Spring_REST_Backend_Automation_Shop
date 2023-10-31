package pl.mateusz_semklo.automationshoprest.models;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import pl.mateusz_semklo.automationshoprest.entities.Category;
import pl.mateusz_semklo.automationshoprest.entities.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName(value = "category")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryModel extends RepresentationModel<CategoryModel>  {

    private String categoryName;
    private List<Product> products=new ArrayList<>();
}
