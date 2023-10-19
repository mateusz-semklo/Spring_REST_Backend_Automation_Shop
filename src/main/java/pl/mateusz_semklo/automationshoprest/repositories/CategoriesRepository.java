package pl.mateusz_semklo.automationshoprest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.entities.Category;
import pl.mateusz_semklo.automationshoprest.entities.Product;
import pl.mateusz_semklo.automationshoprest.repositories.extensions.CategoriesRepositoryExtension;

import java.util.List;

@Repository
@Transactional
public interface CategoriesRepository extends JpaRepository<Category,Integer>, CategoriesRepositoryExtension {

    @Query("SELECT c FROM Category c WHERE c.categoryName=:categoryName")
    public List<Category> findCategoriesByCategoryName(@Param("categoryName") String categoryName);

}
