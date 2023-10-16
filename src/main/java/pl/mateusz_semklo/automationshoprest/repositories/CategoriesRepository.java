package pl.mateusz_semklo.automationshoprest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.entities.Categories;
import pl.mateusz_semklo.automationshoprest.repositories.extensions.CategoriesRepositoryExtension;

@Repository
@Transactional
public interface CategoriesRepository extends JpaRepository<Categories,String>, CategoriesRepositoryExtension {
}
