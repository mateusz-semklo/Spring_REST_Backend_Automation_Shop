package pl.mateusz_semklo.automationshoprest.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.entities.Category;

import java.util.List;

@Transactional
@Service
public interface CategoriesService {
    Category findById(Integer id);
    List<Category> findByName(String name);
    List<Category> findAll();
    Category save(Category category);
    void delete(Integer id);



}
