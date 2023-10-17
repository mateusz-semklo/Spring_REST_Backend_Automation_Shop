package pl.mateusz_semklo.automationshoprest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.entities.Categories;
import pl.mateusz_semklo.automationshoprest.repositories.extensions.CategoriesRepositoryExtension;

import java.util.List;

@Repository
@Transactional
public interface CategoriesRepository extends JpaRepository<Categories,Integer>, CategoriesRepositoryExtension {

    @Query("SELECT c FROM Categories c WHERE c.categoryName=:categoryName")
    public List<Categories> findCategoriesByCategoryNames(@Param("categoryName") String categoryName);

}
