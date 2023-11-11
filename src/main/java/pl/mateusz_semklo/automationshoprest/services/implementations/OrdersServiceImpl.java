package pl.mateusz_semklo.automationshoprest.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.entities.Cart;
import pl.mateusz_semklo.automationshoprest.entities.Order;
import pl.mateusz_semklo.automationshoprest.entities.Product;
import pl.mateusz_semklo.automationshoprest.entities.User;
import pl.mateusz_semklo.automationshoprest.models.OrderPostModel;
import pl.mateusz_semklo.automationshoprest.repositories.CartsRepository;
import pl.mateusz_semklo.automationshoprest.repositories.OrdersRepository;
import pl.mateusz_semklo.automationshoprest.repositories.ProductsRepository;
import pl.mateusz_semklo.automationshoprest.services.OrdersService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    CartsRepository cartsRepository;

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
    public Order save(Order order) {
        return ordersRepository.save(order);
    }

    @Override
    public Order saveOrder(OrderPostModel orderPostModel) {
        Order order= orderPostModel.getOrder();
        List<Cart> carts =new ArrayList<>();
        orderPostModel.getCarts().forEach((cartProductId)->{
            carts.add(cartsRepository.findById(cartProductId).get());
        });
        order.setCarts(carts);
        Order result=ordersRepository.save(order);
        return result;
    }

    @Override
    public boolean delete(Integer id) {
        if(ordersRepository.existsById(id)) {
            Order order=ordersRepository.findById(id).get();
            User user=order.getUser();
            if(user!=null)  user.getOrders().remove(order);
            ordersRepository.deleteById(id);
            return true;
        }
        else
            return false;

    }
}
