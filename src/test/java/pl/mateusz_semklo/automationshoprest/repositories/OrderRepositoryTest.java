package pl.mateusz_semklo.automationshoprest.repositories;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import pl.mateusz_semklo.automationshoprest.entities.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class OrderRepositoryTest {
    @Autowired
    OrdersRepository orderRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ProductsRepository productsRepository;


    @Test
    void findOrdersAll(){
        List<Orders> ordersList=orderRepository.findAll();

        assertThat(ordersList,notNullValue());
        assertThat(ordersList.size(),is(greaterThan(0)));
        ordersList.forEach((categories -> System.out.println(categories.getOrderId())));

    }
    @Test
    void findOrdersById1061(){
        Optional<Orders> optionalCategory=orderRepository.findById(1061);

        assertThat(optionalCategory.isPresent(),notNullValue());
        assertThat(optionalCategory.get().getOrderId(),equalTo(1061));
    }

    @Test
    void saveNewOrderForJanKOwalskiWithOutProduct(){
        Optional<Users> optionalUsers=usersRepository.findById("jankowalski");
        Users user=optionalUsers.get();

        Orders orders=new Orders();
        orders.setOrderDate(new Date(System.currentTimeMillis()));
        orders.setOrderCountry(user.getUserCountry());
        orders.setOrderCity(user.getUserCity());
        orders.setOrdesPostCode(user.getUserPostCode());
        orders.setUser(user);
        orders.setOrderStreet(user.getUserStreet());

        Orders result= orderRepository.save(orders);
        assertThat(result,notNullValue());
        assertThat(result.getUser(),equalTo(user));
        assertThat(result.getOrderId(),notNullValue());



    }

    @Test
    void deleteOrderForJanKowalski(){
        List<Orders> ordersList=  orderRepository.findOrdersByUsername("jankowalski");
        orderRepository.deleteById(ordersList.get(0).getOrderId());
        Optional<Orders> optionalOrders=orderRepository.findById(ordersList.get(0).getOrderId());
        assertThat(optionalOrders.isEmpty(),equalTo(true));

    }

    @Test
    void saveNewOrderForJanKOwalskiWithProduct(){
        Optional<Users> optionalUsers=usersRepository.findById("jankowalski");
        Users user=optionalUsers.get();

        Orders orders=new Orders();
        orders.setOrderDate(new Date(System.currentTimeMillis()));
        orders.setOrderCountry(user.getUserCountry());
        orders.setOrderCity(user.getUserCity());
        orders.setOrdesPostCode(user.getUserPostCode());
        orders.setUser(user);
        orders.setOrderStreet(user.getUserStreet());

        List<Products> productsList=productsRepository.findProductByCategoryName("Czujniki");
       // List<OrdersProducts> ordersProductsList=orders.addProducts(productsList);
        orders=orderRepository.addProducts(orders,productsList);

        Orders result= orderRepository.save(orders);

        assertThat(productsList,notNullValue());
        assertThat(productsList,not(empty()));
       // assertThat(ordersProductsList,notNullValue());
      //  assertThat(ordersProductsList,not(empty()));
        assertThat(result,notNullValue());
        assertThat(result.getUser(),equalTo(user));
        assertThat(result.getOrderId(),notNullValue());

    }

    @Test
    public void xxx(){
        orderRepository.xxx();
    }



}