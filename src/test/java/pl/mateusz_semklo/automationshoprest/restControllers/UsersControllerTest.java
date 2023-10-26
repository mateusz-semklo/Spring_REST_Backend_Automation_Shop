package pl.mateusz_semklo.automationshoprest.restControllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;
import pl.mateusz_semklo.automationshoprest.config.ConfigProperties;
import pl.mateusz_semklo.automationshoprest.config.Mapper;
import pl.mateusz_semklo.automationshoprest.entities.User;
import pl.mateusz_semklo.automationshoprest.models.OrderModel;
import pl.mateusz_semklo.automationshoprest.models.UserModel;
import pl.mateusz_semklo.automationshoprest.repositories.UsersRepository;
import pl.mateusz_semklo.automationshoprest.services.UsersService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class UsersControllerTest {
    @Autowired
    ConfigProperties configProperties;

    @Autowired
    UsersService usersService;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    Mapper modelMapper;

    WebTestClient webTestClient;
    @Autowired
    private UsersRepository usersRepository;

    @BeforeEach
    void init(){
        webTestClient= MockMvcWebTestClient.bindToApplicationContext(webApplicationContext).build();
    }

    @Test
    void getUsers() {
        List<UserModel> userModels=webTestClient.get()
                .uri(configProperties.serverUrl+"/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(UserModel.class)
                .returnResult().getResponseBody();

        assertThat(userModels,notNullValue());
        assertThat(userModels,not(empty()));
    }

    @Test
    void getUserByUsername() {
        UserModel userModel=webTestClient.get()
                .uri(configProperties.serverUrl+"/users/mateusz2606")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(UserModel.class)
                .returnResult().getResponseBody();

        assertThat(userModel.getUsername(),equalTo("mateusz2606"));
    }

    @Test
    void getAuthorities() {
        List<String> authorities=webTestClient.get()
                .uri(configProperties.serverUrl+"/users/mateusz2606/authorities")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(String.class)
                .returnResult().getResponseBody();

        assertThat(authorities,notNullValue());
        assertThat(authorities,not(empty()));
    }

    @Test
    void getOrders() {
        List<OrderModel> ordersModels =webTestClient.get()
                .uri(configProperties.serverUrl+"/users/mateusz2606/orders")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(OrderModel.class)
                .returnResult().getResponseBody();

        assertThat(ordersModels,notNullValue());
        assertThat(ordersModels,not(empty()));
    }

    @Test
    void getOrdersById() {
        OrderModel OrderModel=webTestClient.get()
                .uri(configProperties.serverUrl+"/users/mateusz2606/orders/1050")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(OrderModel.class)
                .returnResult().getResponseBody();

        assertThat(OrderModel.getOrderId(),equalTo(1050));
    }

    @Test
    void saveUser() throws JsonProcessingException {
       //////////////////USER/////////////////////
        User user= new User();
        user.setUsername("tom12345");
        user.setPassword(passwordEncoder.encode("tom12345"));
        user.setEnabled(true);
        user.setUserEmail("tom1234@wp.pl");
        user.setUserFirstname("tomasz");
        user.setUserLastname("cegilski");
        user.setUserStreet("wiosenna 23/2");
        user.setUserCity("Swrzędz");
        user.setUserCountry("Poland");
        user.setUserPostCode("64-120");
        List<String> authorities=new ArrayList<>();
        authorities.add("ROLE_USER");
        authorities.add("ROLE_ADMIN");
        user.setAuthorities(authorities);
        UserModel userModel=modelMapper.convertToDTO(user);
        //////////////////////////////////////////////////


        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(userModel));

        UserModel result=webTestClient.post().uri(configProperties.serverUrl+"/users")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(userModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(UserModel.class)
                .returnResult().getResponseBody();

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(result));

        assertThat(result,notNullValue());
        assertThat(user.getUsername(),equalTo(result.getUsername()));

    }
    @Test
    void saveUserJSON() throws JsonProcessingException {
        //////////////////USER/////////////////////
        String userJson="{\"username\":\"tom12345\",\"password\":\"$2a$10$iuQl1yzrwiI2vOjDiAKuAec06B3ydLluVdpdoUsildTlET.R17y7K\",\"enabled\":true,\"userEmail\":\"tom1234@wp.pl\",\"userFirstname\":\"tomasz\",\"userLastname\":\"cegilski\",\"userStreet\":\"wiosenna 23/2\",\"userCity\":\"Swrzędz\",\"userCountry\":\"Poland\",\"userPostCode\":\"64-120\",\"authorities\":[\"ROLE_USER\",\"ROLE_ADMIN\"],\"orders\":[],\"links\":[]}";
        UserModel userModel=objectMapper.readValue(userJson,UserModel.class);
        //////////////////////////////////////////////////

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(userModel));

        UserModel result=webTestClient.post().uri(configProperties.serverUrl+"/users")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(userModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(UserModel.class)
                .returnResult().getResponseBody();

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(result));

        assertThat(result,notNullValue());
        assertThat(userModel.getUsername(),equalTo(result.getUsername()));
    }
    @Test
    void editUserJSON() throws JsonProcessingException {
        //////////////////USER/////////////////////
        String userJson="{\"username\":\"tom12345\",\"password\":\"$2a$10$iuQl1yzrwiI2vOjDiAKuAec06B3ydLluVdpdoUsildTlET.R17y7K\",\"enabled\":true,\"userFirstname\":\"tomasz\",\"userLastname\":\"cegilski\",\"userStreet\":\"wiosenna 23/2\",\"userCity\":\"Swrzędz\",\"userCountry\":\"Poland\",\"userPostCode\":\"64-120\",\"authorities\":[\"ROLE_USER\",\"ROLE_ADMIN\"],\"orders\":[],\"links\":[]}";
        UserModel userModel=objectMapper.readValue(userJson,UserModel.class);
        //////////////////////////////////////////////////

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(userModel));

        UserModel result=webTestClient.post().uri(configProperties.serverUrl+"/users")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(userModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(UserModel.class)
                .returnResult().getResponseBody();

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(result));

        assertThat(result,notNullValue());
        assertThat(userModel.getUsername(),equalTo(result.getUsername()));
    }

    @Test
    void putUser() {
    }

    @Test
    void patchUser() {
    }

    @Test
    void deleteUser() throws JsonProcessingException {
        //////////////////USER/////////////////////
        UserModel userModel=new UserModel();
        userModel.setUsername("tom12345");
        userModel.setPassword(passwordEncoder.encode("tom12345"));
        userModel.setEnabled(true);
        userModel.setUserEmail("tom1234@wp.pl");
        userModel.setUserFirstname("tomasz");
        userModel.setUserLastname("cegilski");
        userModel.setUserStreet("wiosenna 23/2");
        userModel.setUserCity("Swrzędz");
        userModel.setUserCountry("Poland");
        userModel.setUserPostCode("64-120");
        List<String> authorities=new ArrayList<>();
        authorities.add("ROLE_USER");
        authorities.add("ROLE_ADMIN");
        userModel.setAuthorities(authorities);
        //////////////////////////////////////////////////

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(userModel));

        UserModel result=webTestClient.post().uri(configProperties.serverUrl+"/users")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(userModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(UserModel.class)
                .returnResult().getResponseBody();

        System.out.println("-----------------------------------------------------------------");
        System.out.println(objectMapper.writeValueAsString(result));

        assertThat(result,notNullValue());
        assertThat(userModel.getUsername(),equalTo(result.getUsername()));

        webTestClient.delete().uri(configProperties.serverUrl+"/users/{id}",userModel.getUsername())
                .exchange()
                .expectStatus().isOk();

    }
}