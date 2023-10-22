package pl.mateusz_semklo.automationshoprest.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.mateusz_semklo.automationshoprest.entities.Category;
import pl.mateusz_semklo.automationshoprest.entities.Product;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class ProductsServiceTest {

    @Autowired
    ProductsService productsService;

    @Autowired
    CategoriesService categoriesService;

    @Test
    void findById1017() {
        Product product=productsService.findById(1017);
        assertThat(product,notNullValue());
        assertThat(product,isA(Product.class));
        assertThat(product.getProductId(),equalTo(1017));
    }

    @Test
    void findAll() {
        List<Product> products=productsService.findAll();
        assertThat(products.get(0),notNullValue());
        assertThat(products.get(0),isA(Product.class));
        assertThat(products,not(empty()));
    }

    @Test
    void saveProduct() {
        Product products=new Product();
        products.setProductName("nowy product");
        products.setProductDescription("product description");
        products.setProductImageUrl("/products/new");
        products.setProductPrice(34);

        Category category=categoriesService.findById(1001);
        products.setCategory(category);

        Product result=productsService.save(products);

        assertThat(result,notNullValue());
        assertThat(result,isA(Product.class));
        assertThat(result,hasProperty("productName"));
        assertThat(result.getCategory().getCategoryId(),equalTo(1001));

    }

    @Test
    void deleteProductId1026() {

        Product products=new Product();
        products.setProductName("nowy product");
        products.setProductDescription("product description");
        products.setProductImageUrl("/products/new");
        products.setProductPrice(34);

        Category category=categoriesService.findById(1001);
        products.setCategory(category);

        Product result=productsService.save(products);

        assertThat(result,notNullValue());
        assertThat(result,isA(Product.class));
        assertThat(result,hasProperty("productName"));
        assertThat(result.getCategory().getCategoryId(),equalTo(1001));

        boolean del=productsService.delete(result.getProductId());
        assertThat(productsService.findById(result.getProductId()),nullValue());
        assertThat(del,is(true));
    }


}