package pl.mateusz_semklo.automationshoprest.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.entities.Category;
import pl.mateusz_semklo.automationshoprest.entities.Product;

import java.util.List;

@Transactional
@Service
public interface CategoriesService {
    Category findByName(String name);
    List<Category> findAll();
    Category save(Category category);
    boolean delete(String id);


}
