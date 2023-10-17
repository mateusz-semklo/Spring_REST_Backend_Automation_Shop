package pl.mateusz_semklo.automationshoprest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.repositories.OrdersRepository;

@Transactional
@Service
public class OrdersService {

    @Autowired
    OrdersRepository ordersRepository;
}
