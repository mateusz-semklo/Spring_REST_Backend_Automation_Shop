package pl.mateusz_semklo.automationshoprest.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.entities.Order;
import pl.mateusz_semklo.automationshoprest.entities.Product;
import pl.mateusz_semklo.automationshoprest.models.OrderPostModel;
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
    ProductsRepository productsRepository;

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
        List<Product> products =new ArrayList<>();
        orderPostModel.getProducts().forEach((productId)->{
            products.add(productsRepository.findById(productId).get());
        });
        order.setProducts(products);
        Order result=ordersRepository.save(order);
        return result;
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
