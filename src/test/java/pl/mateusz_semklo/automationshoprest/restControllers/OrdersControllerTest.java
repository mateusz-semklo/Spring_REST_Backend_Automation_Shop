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
import pl.mateusz_semklo.automationshoprest.entities.Order;
import pl.mateusz_semklo.automationshoprest.entities.Product;
import pl.mateusz_semklo.automationshoprest.entities.User;
import pl.mateusz_semklo.automationshoprest.models.CategoryModel;
import pl.mateusz_semklo.automationshoprest.models.OrderModel;
import pl.mateusz_semklo.automationshoprest.models.ProductModel;
import pl.mateusz_semklo.automationshoprest.services.OrdersService;
import pl.mateusz_semklo.automationshoprest.services.ProductsService;
import pl.mateusz_semklo.automationshoprest.services.UsersService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OrdersControllerTest {

    @Autowired
    ConfigProperties configProperties;

    @Autowired
    ProductsService productsService;

    @Autowired
    OrdersService ordersService;

    @Autowired
    UsersService usersService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    Mapper modelMapper;

    WebTestClient webTestClient;

    @BeforeEach
    void init(){
        webTestClient= MockMvcWebTestClient.bindToApplicationContext(webApplicationContext).build();
    }

    @Test
    void getOrders() {
        List<OrderModel> orderModels=webTestClient.get()
                .uri(configProperties.serverUrl+"/orders")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(OrderModel.class)
                .returnResult().getResponseBody();

        assertThat(orderModels,notNullValue());
        assertThat(orderModels,not(empty()));
    }

    @Test
    void getOrderById() throws JsonProcessingException {
        OrderModel orderModel=webTestClient.get()
                .uri(configProperties.serverUrl+"/orders/1056")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(OrderModel.class)
                .returnResult().getResponseBody();

        System.out.println(objectMapper.writeValueAsString(orderModel));
        assertThat(orderModel.getOrderId(),equalTo(1056));
    }

    @Test
    void getProducts() {
        List<ProductModel> productModels =webTestClient.get()
                .uri(configProperties.serverUrl+"/orders/1056/products")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(ProductModel.class)
                .returnResult().getResponseBody();

        assertThat(productModels,notNullValue());
    }

    @Test
    void saveOrder() throws JsonProcessingException {
        //////////ORDER////////////////////////////
        Order order=new Order();
        User user=usersService.findByUsername("mateusz2606");
        order.setOrderCountry(user.getUserCountry());
        order.setOrderCity(user.getUserCity());
        order.setOrderPostCode(user.getUserPostCode());
        order.setUser(user);
        order.setOrderStreet(user.getUserStreet());
        /////////////////////////////////////////////


        //System.out.println("-----------------------------------------------------------------");
        //System.out.println(objectMapper.writeValueAsString(orderModelPost));



       // System.out.println("-----------------------------------------------------------------");
       // System.out.println(objectMapper.writeValueAsString(result));

    }
    @Test
    void saveOrderWithProducts() throws JsonProcessingException {
        //////////ORDER////////////////////////////


        Order order=new Order();
        User user=usersService.findByUsername("jankowalski");
        order.setOrderCountry(user.getUserCountry());
        order.setOrderCity(user.getUserCity());
        order.setOrderPostCode(user.getUserPostCode());
        order.setUser(user);
        order.setOrderStreet(user.getUserStreet());

        List<Product> products=new ArrayList<>();
        Product product=productsService.findById(1014);
        product.setProductName("aaaaaaaaaaaaaaaaaaaaaaa");
        products.add(product);
        products.add(productsService.findById(1015));


        /////////////////////////////////////////////

       // System.out.println("-----------------------------------------------------------------");
        //System.out.println(objectMapper.writeValueAsString(orderModelPost));

        webTestClient.post().uri("/orders")
                .accept(MediaType.ALL)
                .bodyValue(order)
                .exchange()
               // .expectStatus().isCreated()
               // .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Order.class);
              //  .returnResult().getResponseBody();

      //  System.out.println("-----------------------------------------------------------------");
       // System.out.println(objectMapper.writeValueAsString(result));
    }

    @Test
    void saveOrderJSON() throws JsonProcessingException {
        //////////ORDER////////////////////////////
        //String orderJSON="{\"orderDate\":\"2023-10-21\",\"orderStreet\":\"Obornicka 2a/2\",\"orderCity\":\"Poznan\",\"orderCountry\":\"Polska\",\"orderPostCode\":\"61-122\",\"user\":{\"username\":\"mateusz2606\"}}";
        String orderJSON="{\"orderStreet\":\"Obornicka 2a/2\",\"orderCity\":\"Poznan\",\"orderCountry\":\"Polska\",\"orderPostCode\":\"61-122\",\"user\":{\"username\":\"mateusz2606\"}}";
        Order order=objectMapper.readValue(orderJSON,Order.class);
        /////////////////////////////////////////////




    }

    @Test
    void saveOrderWithProductsJSON() throws JsonProcessingException {
        //////////ORDER////////////////////////////
        //////////zapisuje tylko sam obiekt order a nie zapisze obiektów products/////
        ///błędu nie ma/////////////cascade.OFF w Order//////////
        String orderJSON="{\"orderStreet\":\"Obornicka 2a/2\",\"orderCity\":\"Poznan\",\"orderCountry\":\"Polska\",\"orderPostCode\":\"61-122\",\"user\":{\"username\":\"mateusz2606\"},\"products\":[1142]}";
        Order order=objectMapper.readValue(orderJSON,Order.class);
        /////////////////////////////////////////////

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(order));

        Order result=webTestClient.post().uri("/orders")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(order)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Order.class)
                .returnResult().getResponseBody();

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(result));
    }

    @Test
    void putOrder() {
    }

    @Test
    void patchOrder() {
    }

    @Test
    void deleteOrder() {
    }
}