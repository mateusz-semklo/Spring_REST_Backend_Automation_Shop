package pl.mateusz_semklo.automationshoprest.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.entities.Product;

import java.util.List;

@Transactional
@Service
public interface ProductsService {
    Product findById(Integer id);
    List<Product> findAll();
    Product save(Product products);
    void delete(Integer id);

}
