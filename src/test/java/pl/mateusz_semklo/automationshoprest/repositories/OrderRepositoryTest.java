package pl.mateusz_semklo.automationshoprest.repositories;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.mateusz_semklo.automationshoprest.entities.Order;
import pl.mateusz_semklo.automationshoprest.entities.User;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class OrderRepositoryTest {
    @Autowired
    OrdersRepository orderRepository;
    @Autowired
    private UsersRepository usersRepository;


    @Test
    void findOrdersAll(){
        List<Order> ordersList=orderRepository.findAll();

        assertThat(ordersList,notNullValue());
        assertThat(ordersList.size(),is(greaterThan(0)));
        ordersList.forEach((categories -> System.out.println(categories.getOrderId())));

    }
    @Test
    void findOrdersById1056(){
        Optional<Order> optionalCategory=orderRepository.findById(1050);

        assertThat(optionalCategory.isPresent(),notNullValue());
        assertThat(optionalCategory.get().getOrderId(),equalTo(1050));
    }

    @Test
    void saveNewOrderForJanKOwalskiWithOutProduct(){
        Optional<User> optionalUsers=usersRepository.findById("jankowalski");
        User user=optionalUsers.get();

        Order orders=new Order();
        orders.setOrderDate(new Date(System.currentTimeMillis()));
        orders.setOrderCountry(user.getUserCountry());
        orders.setOrderCity(user.getUserCity());
        orders.setOrderPostCode(user.getUserPostCode());
        orders.setUser(user);
        orders.setOrderStreet(user.getUserStreet());

        Order result= orderRepository.save(orders);
        assertThat(result,notNullValue());
        assertThat(result.getUser(),equalTo(user));
        assertThat(result.getOrderId(),notNullValue());
    }

}