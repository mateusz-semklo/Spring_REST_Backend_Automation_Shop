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
        orders.setOrdesPostCode(user.getUserPostCode());
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
        orders.setOrdesPostCode("65-609");
        orders.setOrderStreet("lesna 5");

        assertThrows(DataIntegrityViolationException.class,()->ordersService.save(orders));
    }

    @Test
    void deleteOrderById1055() {
        ordersService.delete(1055);
        Order order=ordersService.findById(1055);
        assertThat(order,nullValue());
    }

    @Test
    void saveOrderWithAddProducts(){
        Order order=ordersService.findById(1056);
        List<Product> productList=productsService.findAll().subList(17,20);
        int count=productList.size();

        order.addProducts(productList);
        Order result=ordersService.save(order);

        assertThat(result,notNullValue());
       // assertThat(result.getProducts().size(),equalTo(order.getProducts().size()+count));
    }

    @Test
    void removeAllProductFromOrder(){
        Order order=ordersService.findById(1056);
        order.removeAllProducts();
        Order result=ordersService.save(order);

        assertThat(result,notNullValue());
        assertThat(result.getProducts().size(),equalTo(0));
    }
    @Test
    void removeProductByIdFromOrder(){

        Order order=ordersService.findById(1056);
        Optional<Product> optionalProduct=order.getProducts().stream().findFirst();
        int count=order.getProducts().size();

        order.removeProductById(optionalProduct.get().getProductId());

        Order result=ordersService.save(order);
        assertThat(result,notNullValue());
        assertThat(result.getProducts().size(),equalTo(count-1));
    }


}