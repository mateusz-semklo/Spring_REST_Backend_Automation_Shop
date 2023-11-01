package pl.mateusz_semklo.automationshoprest.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.entities.Cart;
import pl.mateusz_semklo.automationshoprest.entities.Product;

import java.util.List;

@Transactional
@Service
public interface CartsService {
    Cart findById(Integer id);
    List<Cart> findAll();
    Cart save(Cart cart);
    boolean delete(Integer id);

}
