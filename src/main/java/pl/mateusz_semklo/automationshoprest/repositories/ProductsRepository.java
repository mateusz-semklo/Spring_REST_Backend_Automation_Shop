package pl.mateusz_semklo.automationshoprest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.entities.Product;
import pl.mateusz_semklo.automationshoprest.repositories.extensions.ProductsRepositoryExtension;

import java.util.List;

@Repository
@Transactional
public interface ProductsRepository extends JpaRepository<Product,Integer>, ProductsRepositoryExtension {

    @Query("SELECT p FROM Product p WHERE p.category.categoryName=:categoryName")
    public List<Product> findProductsByCategoryName(@Param(value = "categoryName") String categoryName);

}
