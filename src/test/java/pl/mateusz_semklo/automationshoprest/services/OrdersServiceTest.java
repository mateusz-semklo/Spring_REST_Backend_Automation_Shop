package pl.mateusz_semklo.automationshoprest.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import pl.mateusz_semklo.automationshoprest.entities.Cart;
import pl.mateusz_semklo.automationshoprest.entities.Order;
import pl.mateusz_semklo.automationshoprest.entities.User;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class OrdersServiceTest {

    @Autowired
    OrdersService ordersService;

    @Autowired
    UsersService usersService;

    @Autowired
    ProductsService productsService;

    @Autowired
    CartsService cartsService;

    @Test
    void findOrderById1052() {
        Order order =ordersService.findById(1052);
        assertThat(order,notNullValue());
        assertThat(order,isA(Order.class));
        assertThat(order.getOrderId(),equalTo(1052));
    }

    @Test
    void findAll() {
        List<Order> orders=ordersService.findAll();
        assertThat(orders.get(0),notNullValue());
        assertThat(orders.get(0),isA(Order.class));
        assertThat(orders,not(empty()));
    }

    @Test
    void saveNewOrderWithUser() {
        User user=usersService.findByUsername("jankowalski");

        Order orders=new Order();
        orders.setOrderDate(new Date(System.currentTimeMillis()));
        orders.setOrderCountry(user.getUserCountry());
        orders.setOrderCity(user.getUserCity());
        orders.setOrderPostCode(user.getUserPostCode());
        orders.setUser(user);
        orders.setOrderStreet(user.getUserStreet());

        Order result= ordersService.save(orders);

        assertThat(result,notNullValue());
        assertThat(result.getUser(),equalTo(user));
        assertThat(result.getOrderId(),notNullValue());

    }

    @Test
    void saveNewOrderWithExistsProduct() {
        User user=usersService.findByUsername("jankowalski");

        Order orders=new Order();
        orders.setOrderDate(new Date(System.currentTimeMillis()));
        orders.setOrderCountry(user.getUserCountry());
        orders.setOrderCity(user.getUserCity());
        orders.setOrderPostCode(user.getUserPostCode());
        orders.setUser(user);
        orders.setOrderStreet(user.getUserStreet());

        Cart cart1=cartsService.findById(1049);
        Cart cart2=cartsService.findById(1050);
        orders.getCarts().add(cart1);
        orders.getCarts().add(cart2);

        Order result= ordersService.save(orders);

    }


    @Test
    void saveNewOrderWithoutUser() {

        Order orders=new Order();
        orders.setOrderDate(new Date(System.currentTimeMillis()));
        orders.setOrderCountry("poland");
        orders.setOrderCity("wawa");
        orders.setOrderPostCode("65-609");
        orders.setOrderStreet("lesna 5");

        assertThrows(DataIntegrityViolationException.class,()->ordersService.save(orders));
    }

    @Test
    void deleteOrderById() {
        User user=usersService.findByUsername("jankowalski");

        Order orders=new Order();
        orders.setOrderDate(new Date(System.currentTimeMillis()));
        orders.setOrderCountry(user.getUserCountry());
        orders.setOrderCity(user.getUserCity());
        orders.setOrderPostCode(user.getUserPostCode());
        orders.setUser(user);
        orders.setOrderStreet(user.getUserStreet());

        Order result= ordersService.save(orders);

        assertThat(result,notNullValue());
        assertThat(result.getUser(),equalTo(user));
        assertThat(result.getOrderId(),notNullValue());

        boolean del=ordersService.delete(result.getOrderId());
        Order order=ordersService.findById(result.getOrderId());
        assertThat(order,nullValue());
        assertThat(del,is(true));
    }

    @Test
    void editOrderWithADDProducts(){
        Order order=ordersService.findById(1052);
        List<Cart> cartList=new ArrayList<>();
        Cart cart1=new Cart();
        Cart cart2=new Cart();
        cart1.setProduct(this.productsService.findById(1020));
        cart2.setProduct(this.productsService.findById(1021));

        this.cartsService.save(cart1);
        this.cartsService.save(cart2);
        cartList.add(cart1);
        cartList.add(cart2);

        cartList.forEach((cart -> order.getCarts().add(cart)));
        Order result=ordersService.save(order);

        assertThat(result,notNullValue());

    }
    @Test
    void editOrderWithREPLACEProducts(){
        Order order=ordersService.findById(1052);
        List<Cart> cartList=cartsService.findAll();

        order.setCarts(cartList);
        Order result=ordersService.save(order);

        assertThat(result,notNullValue());

    }

    @Test
    void removeProductByIdFromOrder(){

        Order order=ordersService.findById(1052);
        Cart cart=order.getCarts().get(0);
        int count=order.getCarts().size();

        Cart x=order.getCarts().get(0);
        order.getCarts().remove(0);

        Order result=ordersService.save(order);
        assertThat(result,notNullValue());
        assertThat(result.getCarts().size(),equalTo(count-1));

        order.getCarts().add(x);
        Order xx=this.ordersService.save(order);
        assertThat(xx.getCarts().size(),equalTo(count));


    }


}