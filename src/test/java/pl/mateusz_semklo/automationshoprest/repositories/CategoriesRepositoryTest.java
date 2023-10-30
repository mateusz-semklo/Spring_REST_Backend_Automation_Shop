package pl.mateusz_semklo.automationshoprest.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import pl.mateusz_semklo.automationshoprest.config.ConfigProperties;
import pl.mateusz_semklo.automationshoprest.entities.Category;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class CategoriesRepositoryTest {
    @Autowired
    CategoriesRepository categoriesRepository;
    @Autowired
    ProductsRepository productsRepository;

    @Test
    void findCategoriesAll(){
        List<Category> categoriesList=categoriesRepository.findAll();

        assertThat(categoriesList,notNullValue());
        assertThat(categoriesList.size(),is(greaterThan(0)));
        categoriesList.forEach((categories -> System.out.println(categories.getCategoryName())));

    }
    @Test
    void findCategoriesById1002Czujniki(){
        Optional<Category> optionalCategory=categoriesRepository.findById("Czujniki");

        assertThat(optionalCategory.isPresent(),notNullValue());
        assertThat(optionalCategory.get().getCategoryName(),equalTo("Czujniki"));
    }
    @Test
    void saveNewCategories(){
        if(categoriesRepository.findById("nowa").isEmpty()) {
            Category category = new Category();
            category.setCategoryName("nowa");
            Category result = categoriesRepository.save(category);
            assertThat(result, notNullValue());
            assertThat(result.getCategoryName(), equalTo("nowa"));
        }

    }
    @Test
    void deleteCategoriesWithNoProducts(){

        if(categoriesRepository.findById("nowa").isPresent()) {
            categoriesRepository.deleteById("nowa");
            Optional<Category> optionalCategory = categoriesRepository.findById("nowa");
            assertThat(optionalCategory.isEmpty(), equalTo(true));
        }

    }
    @Test
    void deleteCategoriesCzujnikiWithProducts(){

        Category category=categoriesRepository.findById("Czujniki").get();

        assertThrows(DataIntegrityViolationException.class,
                ()->categoriesRepository.deleteById(category.getCategoryName()));
    }


}