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
import pl.mateusz_semklo.automationshoprest.entities.Cart;
import pl.mateusz_semklo.automationshoprest.models.CartModel;
import pl.mateusz_semklo.automationshoprest.models.ProductModel;
import pl.mateusz_semklo.automationshoprest.repositories.CartsRepository;
import pl.mateusz_semklo.automationshoprest.services.CategoriesService;
import pl.mateusz_semklo.automationshoprest.services.ProductsService;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartsControllerTest {

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
    @Autowired
    private CartsRepository cartsRepository;

    @BeforeEach
    void init(){
        webTestClient= MockMvcWebTestClient.bindToApplicationContext(webApplicationContext).build();
    }

    @Test
    void getCarts() {
        List<CartModel> cartsModels=webTestClient.get()
                .uri(configProperties.serverUrl+"/carts")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(CartModel.class)
                .returnResult().getResponseBody();

        assertThat(cartsModels,notNullValue());
        assertThat(cartsModels,not(empty()));
    }


    @Test
    void saveNewCartWithExistProductJSON() throws JsonProcessingException {

        String cartJson="{\"count\":5,\"product\":{\"productId\":1005}}";

        CartModel result=this.webTestClient.post()
                .uri(configProperties.serverUrl+"/carts/post")
                .bodyValue(cartJson)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(CartModel.class)
                .returnResult().getResponseBody();

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(result));

        assertThat(result,notNullValue());
    }

    @Test
    void saveNewCartWithExistProductJSON2() throws JsonProcessingException {

        String cartJson="{\"cartProductId\":null,\"count\":5,\"product\":{\"productId\":1005,\"productPrice\":40}}";

        Cart cart=objectMapper.readValue(cartJson,Cart.class);
        CartModel cartModel=modelMapper.convertToDTO(cart);


        CartModel result=this.webTestClient.post()
                .uri(configProperties.serverUrl+"/carts")
                .bodyValue(cartModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(CartModel.class)
                .returnResult().getResponseBody();

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(result));

        assertThat(result,notNullValue());
    }

    @Test
    void editCartWithExistProductJSON2() throws JsonProcessingException {

        String cartJson="{\"cartProductId\":1050,\"count\":5,\"product\":{\"productId\":1006}}";

        Cart cart=objectMapper.readValue(cartJson,Cart.class);
        CartModel cartModel=modelMapper.convertToDTO(cart);


        CartModel result=this.webTestClient.post()
                .uri(configProperties.serverUrl+"/carts")
                .bodyValue(cartModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(CartModel.class)
                .returnResult().getResponseBody();

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(result));

        assertThat(result,notNullValue());
    }

    @Test
    void saveCart() throws JsonProcessingException {

        Cart cart=this.cartsRepository.findAll().get(1);
        cart.setProduct(this.productsService.findAll().get(12));

        CartModel cartModel=modelMapper.convertToDTO(cart);

        CartModel result=this.webTestClient.post()
                .uri(configProperties.serverUrl+"/carts")
                .bodyValue(cartModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(CartModel.class)
                .returnResult().getResponseBody();

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(result));

        assertThat(result,notNullValue());
    }

    @Test
    void putCart() {
    }

    @Test
    void patchCart() {
    }

    @Test
    void deleteCart() {
    }
}