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
    void findById1002() {
        Category category=categoriesService.findById(1002);
        assertThat(category,notNullValue());
        assertThat(category,isA(Category.class));
        assertThat(category.getCategoryId(),equalTo(1002));
    }

    @Test
    void findByName() {
        List<Category> categories=categoriesService.findByName("Czujniki");
        assertThat(categories.get(0),notNullValue());
        assertThat(categories.get(0),isA(Category.class));
        assertThat(categories.get(0).getCategoryName(),equalTo("Czujniki"));
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
        Category category=new Category();
        category.setCategoryName("nowaKategoriax");
        Category result=categoriesService.save(category);

        assertThat(result,notNullValue());
        assertThat(result.getCategoryName(),equalTo("nowaKategoriax"));

        List<Category> categories=categoriesService.findByName("nowaKategoriax");
        Category categoryx=categories.get(0);

        boolean del=categoriesService.delete(categoryx.getCategoryId());

        Category resultx=categoriesService.findById(categoryx.getCategoryId());
        assertThat(resultx,nullValue());
        assertThat(del,is(true));

    }
    @Test
    void deleteCategoriesCzujnikiWithProducts(){

        Category category=categoriesService.findByName("Czujniki").get(0);
        assertThrows(DataIntegrityViolationException.class,
                ()->categoriesService.delete(category.getCategoryId()));
    }
    @Test
    void editCategoriesWithProducts(){

        Category category=categoriesService.findById(1001);
        category.setCategoryName("Microkontrolery!!!");
        Category result=categoriesService.save(category);

        assertThat(result,notNullValue());
        assertThat(result.getCategoryName(),equalTo("Microkontrolery!!!"));

        Category categoryx=categoriesService.findById(1001);
        categoryx.setCategoryName("Mikrokontrolery");
        categoriesService.save(categoryx);
    }


}