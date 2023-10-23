package pl.mateusz_semklo.automationshoprest.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.entities.Order;
import pl.mateusz_semklo.automationshoprest.models.OrderPostModel;

import java.util.List;

@Transactional
@Service
public interface OrdersService {
    Order findById(Integer id);
    List<Order> findAll();
    Order save(Order order);
    Order saveOrder(OrderPostModel orderPostModel);
    boolean delete(Integer id);

}
