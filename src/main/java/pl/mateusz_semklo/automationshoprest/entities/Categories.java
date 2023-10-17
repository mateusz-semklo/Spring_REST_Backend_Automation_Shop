package pl.mateusz_semklo.automationshoprest.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "categoryId",scope = Categories.class)
public class Categories implements Serializable {

    private static final long serialVersionUID = 1234567L;

    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "MySequencex")
    @SequenceGenerator(name="MySequencex",sequenceName = "MYSEQ",allocationSize = 1)
    @Id
    @Column(name = "CATEGORY_ID")
    private Integer categoryId;

    @Basic
    @Column(name = "CATEGORY_NAME")
    String categoryName;

    @OneToMany(mappedBy = "category")
    private List<Products> products=new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categories that = (Categories) o;
        return Objects.equals(getCategoryId(), that.getCategoryId()) && Objects.equals(getCategoryName(), that.getCategoryName()) && Objects.equals(getProducts(), that.getProducts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCategoryId(), getCategoryName(), getProducts());
    }
}
