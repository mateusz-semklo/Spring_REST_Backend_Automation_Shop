package pl.mateusz_semklo.automationshoprest.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import pl.mateusz_semklo.automationshoprest.entities.Category;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
@SpringBootTest
class CategoriesServiceTest {

    @Autowired
    CategoriesService categoriesService;


    @Test
    void findByName() {
        Category category=categoriesService.findByName("Czujniki");
        assertThat(category,notNullValue());
        assertThat(category,isA(Category.class));
        assertThat(category.getCategoryName(),equalTo("Czujniki"));
    }

    @Test
    void findAll() {
        List<Category> categories=categoriesService.findAll();

        assertThat(categories,notNullValue());
        assertThat(categories,not(empty()));
    }

    @Test
    void saveCategoryNowaKategoria() {
        Category category=new Category();
        category.setCategoryName("nowaKategoria");
        Category result=categoriesService.save(category);

        assertThat(result,notNullValue());
        assertThat(result.getCategoryName(),equalTo("nowaKategoria"));
    }

    @Test
    void deleteCategoryNowaCategoriaWithNullProducts() {

        Category category=categoriesService.findByName("nowaKategoria");

        boolean del=categoriesService.delete(category.getCategoryName());

        Category resultx=categoriesService.findByName(category.getCategoryName());
        assertThat(resultx,nullValue());
        assertThat(del,is(true));

    }
    @Test
    void deleteCategoriesCzujnikiWithProducts(){

        Category category=categoriesService.findByName("Czujniki");
        assertThrows(DataIntegrityViolationException.class,
                ()->categoriesService.delete(category.getCategoryName()));
    }
    @Test
    void editCategoriesWithProducts(){

        Category category=categoriesService.findByName("Mikrokontrolery");
        category.setCategoryName("Microkontrolery!!!");
        Category result=categoriesService.save(category);

        assertThat(result,notNullValue());
        assertThat(result.getCategoryName(),equalTo("Microkontrolery!!!"));

        Category categoryx=categoriesService.findByName("Microkontrolery!!!");
        categoryx.setCategoryName("Mikrokontrolery");
        categoriesService.save(categoryx);
    }


}