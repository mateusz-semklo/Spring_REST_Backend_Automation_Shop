package pl.mateusz_semklo.automationshoprest.restControllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;
import pl.mateusz_semklo.automationshoprest.config.ConfigProperties;
import pl.mateusz_semklo.automationshoprest.config.Mapper;
import pl.mateusz_semklo.automationshoprest.entities.Category;
import pl.mateusz_semklo.automationshoprest.entities.Product;
import pl.mateusz_semklo.automationshoprest.models.CategoryModel;
import pl.mateusz_semklo.automationshoprest.models.OrderModel;
import pl.mateusz_semklo.automationshoprest.models.ProductModel;
import pl.mateusz_semklo.automationshoprest.services.CategoriesService;
import pl.mateusz_semklo.automationshoprest.services.ProductsService;
import pl.mateusz_semklo.automationshoprest.services.UsersService;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProductsControllerTest {

    @Autowired
    ConfigProperties configProperties;

    @Autowired
    ProductsService productsService;

    @Autowired
    CategoriesService categoriesService;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    Mapper modelMapper;

    WebTestClient webTestClient;

    @BeforeEach
    void init(){
        webTestClient= MockMvcWebTestClient.bindToApplicationContext(webApplicationContext).build();
    }

    @Test
    void getProducts() {
        List<ProductModel> productModels=webTestClient.get()
                .uri(configProperties.serverUrl+"/products")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(ProductModel.class)
                .returnResult().getResponseBody();

        assertThat(productModels,notNullValue());
        assertThat(productModels,not(empty()));
    }

    @Test
    void getProductById() {
        ProductModel productModel=webTestClient.get()
                .uri(configProperties.serverUrl+"/products/1010")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ProductModel.class)
                .returnResult().getResponseBody();

        assertThat(productModel.getProductId(),equalTo(1010));
    }

    @Test
    void getOrders() {
        List<OrderModel> ordersModels =webTestClient.get()
                .uri(configProperties.serverUrl+"/products/1010/orders")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(OrderModel.class)
                .returnResult().getResponseBody();

        assertThat(ordersModels,notNullValue());
    }


    @Test
    void saveProduct() throws JsonProcessingException {
        //////////PRODUCT////////////////////////////
        Product product=new Product();
        product.setProductName("nowy product");
        product.setProductDescription("product description");
        product.setProductImageUrl("/products/new");
        product.setProductPrice(34);

        Category category=categoriesService.findById(1001);
        product.setCategory(category);
        /////////////////////////////////////////////

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(product));

        Product result=webTestClient.post().uri("/products")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(product)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Product.class)
                .returnResult().getResponseBody();

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(result));

    }
    @Test
    void saveProductJSON() throws JsonProcessingException {
        //////////PRODUCT////////////////////////////
        String productJSON="{\"productName\":\"nowy product\",\"productDescription\":\"product description\",\"productImageUrl\":\"/products/new\",\"productPrice\":34,\"category\":{\"categoryId\":1001}}";
        Product product=objectMapper.readValue(productJSON,Product.class);
        /////////////////////////////////////////////

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(product));

        Product result=webTestClient.post().uri("/products")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(product)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Product.class)
                .returnResult().getResponseBody();

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(result));

        Product productx=productsService.findById(result.getProductId());
        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(productx));


    }

    @Test
    void putProduct() {
    }

    @Test
    void patchProduct() {
    }

    @Test
    void deleteProduct() {
    }

}