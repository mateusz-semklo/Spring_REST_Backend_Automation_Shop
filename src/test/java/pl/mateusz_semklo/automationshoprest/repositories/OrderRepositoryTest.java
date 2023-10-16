package pl.mateusz_semklo.automationshoprest.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderRepositoryTest {
    @Autowired
    OrdersRepository orderRepository;

}