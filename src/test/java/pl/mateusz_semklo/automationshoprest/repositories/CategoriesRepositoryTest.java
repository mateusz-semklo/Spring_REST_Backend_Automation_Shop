package pl.mateusz_semklo.automationshoprest.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import pl.mateusz_semklo.automationshoprest.entities.Category;
import pl.mateusz_semklo.automationshoprest.entities.Product;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import java.util.List;
import java.util.Optional;


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
    void findCategoriesById1005(){
        Optional<Category> optionalCategory=categoriesRepository.findById(1005);

        assertThat(optionalCategory.isPresent(),notNullValue());
        assertThat(optionalCategory.get().getCategoryName(),equalTo("Mikrokontrolery"));
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
        List<Category> categoriesList=categoriesRepository.findCategoriesByCategoryName("nowa_kategoria");
        categoriesRepository.deleteById(categoriesList.get(0).getCategoryId());
        Optional<Category> optionalCategory=categoriesRepository.findById(categoriesList.get(0).getCategoryId());
        assertThat(optionalCategory.isEmpty(),equalTo(true));

    }
    @Test
    void deleteCategoriesMikrokontroleryWithProducts(){

        List<Category> categoriesList=categoriesRepository.findCategoriesByCategoryName("Mikrokontrolery");

        assertThrows(DataIntegrityViolationException.class,
                ()->categoriesRepository.deleteById(categoriesList.get(0).getCategoryId()));
    }
    @Test
    void editCategoriesWithProducts(){

        List<Category> categoriesList=categoriesRepository.findCategoriesByCategoryName("Mikrokontrolery");
        Category categories=categoriesList.get(0);
        categories.setCategoryName("Microkontrolery");
        Category result=categoriesRepository.save(categories);

        List<Product> productsList=productsRepository.findProductsByCategoryName("Microkontrolery");

        assertThat(result,notNullValue());
        assertThat(result.getCategoryName(),equalTo("Microkontrolery"));
        assertThat(productsList,notNullValue());
        assertThat(productsList,not(empty()));
        assertThat(productsList.get(0).getCategory().getCategoryName(),equalTo("Microkontrolery"));

    }





}