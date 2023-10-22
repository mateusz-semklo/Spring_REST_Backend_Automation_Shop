package pl.mateusz_semklo.automationshoprest.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import pl.mateusz_semklo.automationshoprest.entities.Order;
import pl.mateusz_semklo.automationshoprest.entities.Product;
import pl.mateusz_semklo.automationshoprest.entities.User;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrdersServiceTest {

    @Autowired
    OrdersService ordersService;

    @Autowired
    UsersService usersService;

    @Autowired
    ProductsService productsService;

    @Test
    void findOrderById1056() {
        Order order =ordersService.findById(1056);
        assertThat(order,notNullValue());
        assertThat(order,isA(Order.class));
        assertThat(order.getOrderId(),equalTo(1056));
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
    void deleteOrderById1055() {
        boolean del=ordersService.delete(1056);
        Order order=ordersService.findById(1056);
        assertThat(order,nullValue());
        assertThat(del,is(true));
    }

    @Test
    void saveOrderWithAddProducts(){
        Order order=ordersService.findById(1056);
        List<Product> productList=productsService.findAll().subList(17,20);
        int count=productList.size();

        productList.forEach((product -> order.getProducts().add(product)));

        Order result=ordersService.save(order);

        assertThat(result,notNullValue());
       // assertThat(result.getProducts().size(),equalTo(order.getProducts().size()+count));
    }

    @Test
    void removeProductByIdFromOrder(){

        Order order=ordersService.findById(1056);
        Optional<Product> optionalProduct=order.getProducts().stream().findFirst();
        int count=order.getProducts().size();

        order.getProducts().remove(0);

        Order result=ordersService.save(order);
        assertThat(result,notNullValue());
        assertThat(result.getProducts().size(),equalTo(count-1));
    }
    @Test
    void removeAllProductFromOrder(){
        Order order=ordersService.findById(1056);
        order.getProducts().clear();
        Order result=ordersService.save(order);

        assertThat(result,notNullValue());
        assertThat(result.getProducts().size(),equalTo(0));
    }


}