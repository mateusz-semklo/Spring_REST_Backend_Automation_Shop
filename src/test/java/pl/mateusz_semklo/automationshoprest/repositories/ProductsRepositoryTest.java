package pl.mateusz_semklo.automationshoprest.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.mateusz_semklo.automationshoprest.entities.Categories;
import pl.mateusz_semklo.automationshoprest.entities.Products;
import pl.mateusz_semklo.automationshoprest.entities.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductsRepositoryTest {
    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    CategoriesRepository categoriesRepository;

    @Test
    void findProductsAll(){
        List<Products> listProducts=productsRepository.findAll();
        System.out.println("LISTA WSZYSTKICH PRODUCTS");
        listProducts.forEach((products -> System.out.println(products.getProductName())));
        assertThat(listProducts,notNullValue());
        assertThat(listProducts,isA(List.class));
    }
    @Test
    void findProductsById1010(){
        Optional<Products> productOptional=productsRepository.findById(1010);
        Products product=productOptional.get();
        System.out.println("PRODCUCT ID 1010");
        System.out.println(product.getProductId()+" Product name: "+product.getProductName());
        assertThat(product,notNullValue());
        assertThat(product,isA(Products.class));
        assertThat(product.getProductId(),equalTo(1010));
    }

    @Test
    public void saveNewProductWithCategoryMikrokontoler(){

        Products products=new Products();
        products.setProductName("nowy product");
        products.setProductDescription("product description");
        products.setProductImageUrl("/products/new");
        products.setProductPrice(34);

        Optional<Categories> optionalCategories=categoriesRepository.findById("Mikrokontrolery");
        Categories category=optionalCategories.get();
        products.setCategory(category);

        assertThat(category,notNullValue());
        assertThat(category.getCategoryNameId(),equalTo("Mikrokontrolery"));

        Products result=productsRepository.save(products);

        assertThat(result,notNullValue());
        assertThat(result,isA(Products.class));
        assertThat(result,hasProperty("productName"));
        assertThat(result.getCategory(),hasProperty("categoryNameId",equalTo("Mikrokontrolery")));
        System.out.println("ZAPISANY PRODUKT Z KAREGORIĄ MIKROKONTROLER");
        System.out.println(products.getProductName());
        System.out.println("KATEGORIA");
        System.out.println(products.getCategory().getCategoryNameId());

    }
    @Test
    public void updateCategoryForProduct1020withCategoryCzujniki(){
        Optional<Products> optionalProduct=productsRepository.findById(1020);
        Products product=optionalProduct.get();

        Optional<Categories> optionalCategories=categoriesRepository.findById("Czujniki");
        Categories category=optionalCategories.get();
        assertThat(category,notNullValue());
        assertThat(category.getCategoryNameId(),equalTo("Czujniki"));

        product.setCategory(category);
        Products result=productsRepository.save(product);

        assertThat(product,notNullValue());
        assertThat(product.getProductId(),equalTo(1020));
        assertThat(result,notNullValue());
        assertThat(result.getProductId(),equalTo(1020));
        assertThat(result.getCategory().getCategoryNameId(),equalTo("Czujniki"));

        System.out.println("KATEGORIA DLA PRODUKTU O ID 1020");
        System.out.println(result.getCategory().getCategoryNameId());

    }
    @Test
    public void deleteProductId1010(){

        productsRepository.deleteById(1023);
        Optional<Products> productsOptional=productsRepository.findById(1023);
        assertThat(productsOptional.isEmpty(),is(true));
    }

}