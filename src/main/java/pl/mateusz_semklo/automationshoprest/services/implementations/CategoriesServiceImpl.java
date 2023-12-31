package pl.mateusz_semklo.automationshoprest.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.entities.Category;
import pl.mateusz_semklo.automationshoprest.entities.Product;
import pl.mateusz_semklo.automationshoprest.repositories.CategoriesRepository;
import pl.mateusz_semklo.automationshoprest.services.CategoriesService;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    CategoriesRepository categoriesRepository;


    @Override
    public Category findByName(String name) {
        Optional<Category> optionalCatrgory=categoriesRepository.findById(name);
        if(optionalCatrgory.isPresent())
            return optionalCatrgory.get();
        else
            return null;
    }


    @Override
    public List<Category> findAll() {
        return categoriesRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        return categoriesRepository.save(category);
    }

    @Override
    public boolean delete(String id) {

        if(categoriesRepository.existsById(id)) {
            categoriesRepository.deleteById(id);
            return true;
        }
        else
            return false;
    }

}
