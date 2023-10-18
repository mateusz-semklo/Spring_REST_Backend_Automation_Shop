package pl.mateusz_semklo.automationshoprest.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.mateusz_semklo.automationshoprest.entities.Category;
import pl.mateusz_semklo.automationshoprest.entities.Product;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class ProductsRepositoryTest {
    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    CategoriesRepository categoriesRepository;

    @Test
    void findProductsAll(){
        List<Product> listProducts=productsRepository.findAll();
        System.out.println("LISTA WSZYSTKICH PRODUCTS");
        listProducts.forEach((products -> System.out.println(products.getProductName())));
        assertThat(listProducts,notNullValue());
        assertThat(listProducts,isA(List.class));
    }
    @Test
    void findProductsById1010(){
        Optional<Product> productOptional=productsRepository.findById(1010);
        Product product=productOptional.get();
        System.out.println("PRODCUCT ID 1010");
        System.out.println(product.getProductId()+" Product name: "+product.getProductName());
        assertThat(product,notNullValue());
        assertThat(product,isA(Product.class));
        assertThat(product.getProductId(),equalTo(1010));
    }

    @Test
    public void saveNewProductWithCategoryId1001(){

        Product products=new Product();
        products.setProductName("nowy product");
        products.setProductDescription("product description");
        products.setProductImageUrl("/products/new");
        products.setProductPrice(34);

        Optional<Category> optionalCategories=categoriesRepository.findById(1001);
        Category category=optionalCategories.get();
        products.setCategory(category);

        assertThat(category,notNullValue());
        assertThat(category.getCategoryId(),equalTo(1001));

        Product result=productsRepository.save(products);

        assertThat(result,notNullValue());
        assertThat(result,isA(Product.class));
        assertThat(result,hasProperty("productName"));
        assertThat(result.getCategory().getCategoryId(),equalTo(1001));


    }
    @Test
    public void updateCategoryForProduct1014withCategoryIdTo1005(){
        Optional<Product> optionalProduct=productsRepository.findById(1014);
        Product product=optionalProduct.get();

        Optional<Category> optionalCategories=categoriesRepository.findById(1005);
        Category category=optionalCategories.get();
        assertThat(category,notNullValue());
        assertThat(category.getCategoryId(),equalTo(1005));

        product.setCategory(category);
        Product result=productsRepository.save(product);

        assertThat(product,notNullValue());
        assertThat(product.getProductId(),equalTo(1014));
        assertThat(result,notNullValue());
        assertThat(result.getProductId(),equalTo(1014));
        assertThat(result.getCategory().getCategoryId(),equalTo(1005));

        System.out.println("KATEGORIA DLA PRODUKTU");
        System.out.println(result.getCategory().getCategoryName());

    }
    @Test
    public void deleteProductId1020(){

        productsRepository.deleteById(1020);
        Optional<Product> productsOptional=productsRepository.findById(1020);
        assertThat(productsOptional.isEmpty(),is(true));
    }

}