package pl.mateusz_semklo.automationshoprest.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
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
        categoriesList.forEach((categories -> System.out.println(categories.getCategoryId())));

    }
    @Test
    void findCategoriesById1002Czujniki(){
        Optional<Category> optionalCategory=categoriesRepository.findById(1002);

        assertThat(optionalCategory.isPresent(),notNullValue());
        assertThat(optionalCategory.get().getCategoryName(),equalTo("Czujniki"));
    }
    @Test
    void saveNewCategories(){
        Category category=new Category();
        category.setCategoryName("nowa_kategoria");
        Category result=categoriesRepository.save(category);
        assertThat(result,notNullValue());
        assertThat(result.getCategoryName(),equalTo("nowa_kategoria"));

    }
    @Test
    void deleteCategoriesWithNoProducts(){
        Category category=new Category();
        category.setCategoryName("nowa_kategoriax");
        Category result=categoriesRepository.save(category);
        assertThat(result,notNullValue());
        assertThat(result.getCategoryName(),equalTo("nowa_kategoriax"));

        List<Category> categoriesList=categoriesRepository.findCategoriesByCategoryName("nowa_kategoriax");
        categoriesRepository.deleteById(categoriesList.get(0).getCategoryId());
        Optional<Category> optionalCategory=categoriesRepository.findById(categoriesList.get(0).getCategoryId());
        assertThat(optionalCategory.isEmpty(),equalTo(true));

    }
    @Test
    void deleteCategoriesCzujnikiWithProducts(){

        List<Category> categoriesList=categoriesRepository.findCategoriesByCategoryName("Czujniki");

        assertThrows(DataIntegrityViolationException.class,
                ()->categoriesRepository.deleteById(categoriesList.get(0).getCategoryId()));
    }


}