package pl.mateusz_semklo.automationshoprest.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.entities.Order;
import pl.mateusz_semklo.automationshoprest.repositories.OrdersRepository;
import pl.mateusz_semklo.automationshoprest.services.OrdersService;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    OrdersRepository ordersRepository;

    @Override
    public Order findById(Integer id) {
        Optional<Order> optionalOrder=ordersRepository.findById(id);
        if(optionalOrder.isPresent())
            return optionalOrder.get();
        else
            return null;
    }

    @Override
    public List<Order> findAll() {
        return ordersRepository.findAll();
    }

    @Override
    public Order save(Order orders) {
        return ordersRepository.save(orders);
    }

    @Override
    public Order saveOrder(Order order) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        if(ordersRepository.existsById(id)) {
            Order order=ordersRepository.findById(id).get();
            order.getUser().getOrders().remove(order);
            ordersRepository.deleteById(id);
            return true;
        }
        else
            return false;

    }
}
