package pl.mateusz_semklo.automationshoprest.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.entities.Cart;
import pl.mateusz_semklo.automationshoprest.repositories.CartsRepository;
import pl.mateusz_semklo.automationshoprest.services.CartsService;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CartsServiceImpl implements CartsService {

    @Autowired
    CartsRepository cartsRepository;

    @Override
    public Cart findById(Integer id) {
        Optional<Cart> optionalCarts= cartsRepository.findById(id);
        if(optionalCarts.isPresent())
            return optionalCarts.get();
        else
            return null;
    }

    @Override
    public List<Cart> findAll() {
        return cartsRepository.findAll();
    }

    @Override
    public Cart save(Cart cart) {
        return cartsRepository.save(cart);
    }

    @Override
    public boolean delete(Integer id) {
        if(cartsRepository.existsById(id)) {
            cartsRepository.deleteById(id);
            return true;
        }
        else
            return false;
    }
}
