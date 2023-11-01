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
import pl.mateusz_semklo.automationshoprest.entities.Order;
import pl.mateusz_semklo.automationshoprest.entities.Product;
import pl.mateusz_semklo.automationshoprest.entities.User;
import pl.mateusz_semklo.automationshoprest.models.CartModel;
import pl.mateusz_semklo.automationshoprest.models.OrderModel;
import pl.mateusz_semklo.automationshoprest.models.OrderPostModel;
import pl.mateusz_semklo.automationshoprest.models.ProductModel;
import pl.mateusz_semklo.automationshoprest.repositories.CategoriesRepository;
import pl.mateusz_semklo.automationshoprest.repositories.OrdersRepository;
import pl.mateusz_semklo.automationshoprest.services.CartsService;
import pl.mateusz_semklo.automationshoprest.services.OrdersService;
import pl.mateusz_semklo.automationshoprest.services.ProductsService;
import pl.mateusz_semklo.automationshoprest.services.UsersService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    CartsService cartsService;

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
                .uri(configProperties.serverUrl+"/orders/1052")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(OrderModel.class)
                .returnResult().getResponseBody();

        System.out.println(objectMapper.writeValueAsString(orderModel));
        assertThat(orderModel.getOrderId(),equalTo(1052));
    }

    @Test
    void getCarts() {
        List<CartModel> cartModels =webTestClient.get()
                .uri(configProperties.serverUrl+"/orders/1052/carts")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(CartModel.class)
                .returnResult().getResponseBody();

        assertThat(cartModels,notNullValue());
    }

    @Test
    void saveNewOrder() throws JsonProcessingException {
        //////////ORDER////////////////////////////
        Order order=new Order();
        User user=usersService.findByUsername("mateusz2606");
        order.setOrderCountry(user.getUserCountry());
        order.setOrderCity(user.getUserCity());
        order.setOrderPostCode(user.getUserPostCode());
        order.setUser(user);
        order.setOrderStreet(user.getUserStreet());
        OrderModel orderModel=modelMapper.convertToDTO(order);
        /////////////////////////////////////////////
        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(orderModel));

        OrderModel result=webTestClient.post().uri(configProperties.getServerUrl()+"/orders")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(orderModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(OrderModel.class)
                .returnResult().getResponseBody();

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(result));

        assertThat(result,notNullValue());
        assertThat(orderModel.getOrderCity(),equalTo(result.getOrderCity()));

    }

    @Test
    void saveNewOrderWithCard() throws JsonProcessingException {
        //////////ORDER////////////////////////////
        Order order=new Order();
        User user=usersService.findByUsername("mateusz2606");
        order.setOrderCountry(user.getUserCountry());
        order.setOrderCity(user.getUserCity());
        order.setOrderPostCode(user.getUserPostCode());
        order.setUser(user);
        order.setOrderStreet(user.getUserStreet());

        Cart cart=cartsService.findAll().get(2);
        order.getCarts().add(cart);

        OrderModel orderModel=modelMapper.convertToDTO(order);
        /////////////////////////////////////////////
        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(orderModel));

        OrderModel result=webTestClient.post().uri(configProperties.getServerUrl()+"/orders")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(orderModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(OrderModel.class)
                .returnResult().getResponseBody();

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(result));

        assertThat(result,notNullValue());
        assertThat(orderModel.getOrderCity(),equalTo(result.getOrderCity()));

    }




    @Test
    void saveNewOrderJSON() throws JsonProcessingException {

        //////////ORDER////////////////////////////
        String orderJSON="{\"orderDate\":\"2023-10-21\",\"orderStreet\":\"Obornicka 2a/2\",\"orderCity\":\"Poznan\",\"orderCountry\":\"Polska\",\"orderPostCode\":\"61-122\",\"user\":{\"username\":\"mateusz2606\"}}";
        OrderModel orderModel=objectMapper.readValue(orderJSON,OrderModel.class);
        /////////////////////////////////////////////
        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(orderModel));

        OrderModel result=webTestClient.post().uri(configProperties.getServerUrl()+"/orders")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(orderModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(OrderModel.class)
                .returnResult().getResponseBody();

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(result));

        assertThat(result,notNullValue());
        assertThat(orderModel.getOrderCity(),equalTo(result.getOrderCity()));
    }


    @Test
    void saveNewOrderWithExistsProductsJSON() throws JsonProcessingException {
        //////////ORDER////////////////////////////
       // String orderJSON="{\"orderId\":0,\"orderStreet\":\"Obornicka 2a/2\",\"orderCity\":\"Poznan\",\"orderCountry\":\"Polska\",\"orderPostCode\":\"61-122\",\"user\":{\"username\":\"mateusz2606\"},\"products\":[{\"productId\":1014,\"productName\":\"nowy product\",\"productDescription\":\"product description\",\"productImageUrl\":\"/products/new\",\"productPrice\":34,\"category\":{\"categoryId\":\"Mikrokontrolery\"}}]  }";
        String orderJSON2="{\"orderId\":0,\"orderStreet\":\"Obornicka 2a/2\",\"orderCity\":\"Poznan\",\"orderCountry\":\"Polska\",\"orderPostCode\":\"61-122\",\"user\":{\"username\":\"mateusz2606\"},\"carts\":[{\"cardProductId\":1049,\"count\":4,\"productId\":1009}]}";

        String orderJSON="{\n" +
                "  \"orderId\": 0,\n" +
                "  \"orderDate\": \"2023-11-01\",\n" +
                "  \"orderStreet\": \"Gowarzewo 2a/2\",\n" +
                "  \"orderCity\": \"Swarzedz\",\n" +
                "  \"orderCountry\": \"Polska\",\n" +
                "  \"orderPostCode\": \"60-002\",\n" +
                "  \"user\": {\n" +
                "    \"username\": \"mateusz2606\"\n" +
                "  },\n" +
                "  \"carts\": [\n" +
                "    {\n" +
                "      \"cartProductId\": 1051,\n" +
                "      \"count\": 2,\n" +
                "      \"product\": {\n" +
                "        \"productId\": 1025\n" +
                "       \n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"links\": []\n" +
                "}";
        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(orderJSON2));
        Order order=objectMapper.readValue(orderJSON,Order.class);
        OrderModel orderModel=modelMapper.convertToDTO(order);

        OrderModel result=webTestClient.post().uri(configProperties.serverUrl+"/orders")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(orderModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(OrderModel.class)
                .returnResult().getResponseBody();

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(result));
    }
    @Test
    void editOrderWithExistsProductsJSON() throws JsonProcessingException {
        //////////ORDER////////////////////////////
        String orderJSON="{\"orderId\":1051,\"orderStreet\":\"Obornicka 2a/2\",\"orderCity\":\"Poznan\",\"orderCountry\":\"Polska\",\"orderPostCode\":\"61-122\",\"user\":{\"username\":\"mateusz2606\"},\"products\":[{\"productId\":1014,\"productName\":\"nowy product\",\"productDescription\":\"product description\",\"productImageUrl\":\"/products/new\",\"productPrice\":34,\"category\":{\"categoryId\":1001}}]  }";
        OrderModel orderModel=objectMapper.readValue(orderJSON,OrderModel.class);
        /////////////////////////////////////////////

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(orderModel));

        OrderModel result=webTestClient.post().uri(configProperties.serverUrl+"/orders")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(orderModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(OrderModel.class)
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
    void deleteOrder() throws JsonProcessingException {

        webTestClient.delete().uri(configProperties.serverUrl+"/orders/{id}",1070)
                .exchange()
                .expectStatus().isOk();
    }


    @Test
    void saveNewOrderWithExistsProducts3() throws JsonProcessingException {
        //////////ORDER////////////////////////////
        User user=usersService.findByUsername("jankowalski");
        Order order=new Order();
        order.setOrderCountry(user.getUserCountry());
        order.setOrderCity(user.getUserCity());
        order.setOrderPostCode(user.getUserPostCode());
        order.setUser(user);
        order.setOrderStreet(user.getUserStreet());


        List<Integer> carts=new ArrayList<>();
        carts.add(1049);
        carts.add(1050);

        OrderPostModel orderPostModel =new OrderPostModel();
        orderPostModel.setOrder(order);
        orderPostModel.setCarts(carts);
        String tekst=objectMapper.writeValueAsString(orderPostModel);

        /////////////////////////////////////////////
        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(order));


        webTestClient.post().uri(configProperties.serverUrl+"/orders")
                .accept(MediaType.ALL)
                .bodyValue(tekst)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);

        System.out.println("-----------------------------------------------------------------");
       // System.out.println(objectMapper.writeValueAsString(result));
       // assertThat(result,notNullValue());
    }


}