package pl.mateusz_semklo.automationshoprest.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import pl.mateusz_semklo.automationshoprest.config.Mapper;
import pl.mateusz_semklo.automationshoprest.entities.Cart;
import pl.mateusz_semklo.automationshoprest.entities.Order;
import pl.mateusz_semklo.automationshoprest.models.CartModel;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartsServiceTest {

    @Autowired
    OrdersService ordersService;

    @Autowired
    UsersService usersService;

    @Autowired
    ProductsService productsService;

    @Autowired
    CartsService cartsService;

    @Autowired
    Mapper mapper;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void findAll() {
        List<Cart> carts=cartsService.findAll();
        assertThat(carts.get(0),notNullValue());
        assertThat(carts.get(0),isA(Cart.class));
        assertThat(carts,not(empty()));
    }

    @Test
    void saveCartWithNoProduct() {
        Cart cart=new Cart();
        cart.setCount(4);
        assertThrows(DataIntegrityViolationException.class,()->{this.cartsService.save(cart);});
    }
    @Test
    void saveCartWithProduct() {
        Cart cart=new Cart();
        cart.setCount(4);
        cart.setProduct(this.productsService.findAll().get(0));
        Cart result=this.cartsService.save(cart);
        Cart result2=this.cartsService.findById(result.getCartProductId());

        assertThat(result2,notNullValue());
        assertThat(result2,isA(Cart.class));
    }

    @Test
    void saveCartWithProductCartModel() {
        Cart cart=new Cart();
        cart.setCount(4);
        cart.setProduct(this.productsService.findAll().get(0));

        CartModel cartModel=mapper.convertToDTO(cart);
        Cart cart1=mapper.convertToEntity(cartModel);

        Cart result=this.cartsService.save(cart);
        Cart result2=this.cartsService.findById(result.getCartProductId());

        assertThat(result2,notNullValue());
        assertThat(result2,isA(Cart.class));
    }

    @Test
    void saveCartWithProductJOSN() throws JsonProcessingException {


        String cartJson="{\"count\":5,\"product\":{\"productId\":1006}}";
        Cart cart=objectMapper.readValue(cartJson,Cart.class);

        Cart result=this.cartsService.save(cart);
        Cart result2=this.cartsService.findById(result.getCartProductId());

        assertThat(result2,notNullValue());
        assertThat(result2,isA(Cart.class));
    }


    @Test
    void delete() {
        Cart cart=new Cart();
        cart.setCount(5);
        cart.setProduct(this.productsService.findAll().get(0));
        Cart result=this.cartsService.save(cart);
        Cart result2=this.cartsService.findById(result.getCartProductId());

        assertThat(result2,notNullValue());
        assertThat(result2,isA(Cart.class));

        this.cartsService.delete(result2.getCartProductId());
        Cart result3=this.cartsService.findById(result2.getCartProductId());
        assertThat(result3,nullValue());

    }

    @Test
    void edit(){
        Cart cart=this.cartsService.findAll().get(0);
        cart.setProduct(this.productsService.findById(1028));
        Cart result1=this.cartsService.save(cart);
        Cart result2=this.cartsService.findById(result1.getCartProductId());

        assertThat(result2,notNullValue());
        assertThat(result2.getProduct().getProductId(),equalTo(1028));
    }

    @Test
    void editJOSN() throws JsonProcessingException {

        Cart cart=this.cartsService.findAll().get(0);
        String cartJson="{\"cartProductId\":"+cart.getCartProductId()+",\"count\":67,\"product\":{\"productId\":1009}}";
        Cart result=objectMapper.readValue(cartJson,Cart.class);

        Cart result2=this.cartsService.save(result);
        Cart result3=this.cartsService.findById(result2.getCartProductId());

        assertThat(result3,notNullValue());
        assertThat(result3,isA(Cart.class));
        System.out.println(objectMapper.writeValueAsString(result3));
        assertThat(result3.getProduct().getProductId(),equalTo(1009));
    }
}