package pl.mateusz_semklo.automationshoprest.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import pl.mateusz_semklo.automationshoprest.models.ProductModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="CATEGORIES")
@EqualsAndHashCode(callSuper = false)
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "categoryName",scope = Category.class)
public class Category implements Serializable {

    private static final long serialVersionUID = 1234567L;

    @Id
    @Column(name = "CATEGORY_NAME")
    private String categoryName;

    @JsonManagedReference
    @OneToMany(mappedBy = "category",fetch = FetchType.EAGER)
    private List<Product> products=new ArrayList<>();

}
