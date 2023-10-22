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
import pl.mateusz_semklo.automationshoprest.models.CategoryModel;
import pl.mateusz_semklo.automationshoprest.models.ProductModel;
import pl.mateusz_semklo.automationshoprest.repositories.CategoriesRepository;
import pl.mateusz_semklo.automationshoprest.services.CategoriesService;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class CategoriesControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ConfigProperties configProperties;

    @Autowired
    CategoriesService categoriesService;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    Mapper mapper;

    WebTestClient webTestClient;
    @Autowired
    private CategoriesRepository categoriesRepository;

    @BeforeEach
    void init(){
        webTestClient= MockMvcWebTestClient.bindToApplicationContext(webApplicationContext).build();
    }

    @Test
    void getCategories() {
        List<CategoryModel> categoryModels=webTestClient.get()
                .uri(configProperties.serverUrl+"/categories")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(CategoryModel.class)
                .returnResult().getResponseBody();

        assertThat(categoryModels,notNullValue());
        assertThat(categoryModels,not(empty()));
    }

    @Test
    void getCategoryById() {
        CategoryModel categoryModel=webTestClient.get()
                .uri(configProperties.serverUrl+"/categories/1005")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(CategoryModel.class)
                .returnResult().getResponseBody();

        assertThat(categoryModel.getCategoryId(),equalTo(1005));
    }


    @Test
    void getProducts() {
        List<ProductModel> productModels =webTestClient.get()
                .uri(configProperties.serverUrl+"/categories/1005/products")
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
    void getProductsById() {
        ProductModel productModel=webTestClient.get()
                .uri(configProperties.serverUrl+"/categories/1005/products/1044")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ProductModel.class)
                .returnResult().getResponseBody();

        assertThat(productModel.getProductId(),equalTo(1044));
    }
    @Test
    void saveCategory() throws JsonProcessingException {
        //////////CATEGORY////////////////////////////
        Category category=new Category();
        String categoryName="kategoria";
        category.setCategoryName(categoryName);
        CategoryModel categoryModel=mapper.convertToDTO(category);
        /////////////////////////////////////////////

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(categoryModel));

        Category result=webTestClient.post().uri("/categories")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(category)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Category.class)
                .returnResult().getResponseBody();

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(result));

        assertThat(result,notNullValue());
        assertThat(category.getCategoryName(),equalTo(result.getCategoryName()));
    }
    @Test
    void saveNewCategoryJSON() throws JsonProcessingException {
        //////////CATEGORY////////////////////////////
        String categoryJSON="{\"categoryName\":\"kaaa\",\"products\":[]}";
        CategoryModel category=objectMapper.readValue(categoryJSON,CategoryModel.class);
        /////////////////////////////////////////////

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(category));

        CategoryModel result=webTestClient.post().uri("/categories")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(category)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(CategoryModel.class)
                .returnResult().getResponseBody();

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(result));

        assertThat(result,notNullValue());
        assertThat(category.getCategoryName(),equalTo(result.getCategoryName()));
    }
    @Test
    void editCategory() throws JsonProcessingException {
        //////////CATEGORY////////////////////////////
        Category category=categoriesService.findById(1004);
        category.setCategoryName("Technika napedowa");
        CategoryModel categoryModel=mapper.convertToDTO(category);
        /////////////////////////////////////////////

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(categoryModel));

        CategoryModel result=webTestClient.post().uri("/categories")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(categoryModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(CategoryModel.class)
                .returnResult().getResponseBody();

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(result));

        assertThat(result,notNullValue());
        assertThat(category.getCategoryName(),equalTo(result.getCategoryName()));
    }

    @Test
    void putCategory() {
    }

    @Test
    void patchCategory() {
    }

    @Test
    void saveAndDeleteCategory() throws JsonProcessingException {
        Category category=new Category();
        String categoryName="kategoria";
        category.setCategoryName(categoryName);
        CategoryModel categoryModel=mapper.convertToDTO(category);

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(categoryModel));

        CategoryModel result=webTestClient.post().uri("/categories")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(category)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(CategoryModel.class)
                .returnResult().getResponseBody();

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(result));

        assertThat(result,notNullValue());
        assertThat(category.getCategoryName(),equalTo(result.getCategoryName()));

        webTestClient.delete().uri("/categories/{id}",result.getCategoryId())
                .exchange()
                .expectStatus().isOk();

    }
}