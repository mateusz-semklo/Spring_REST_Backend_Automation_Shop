package pl.mateusz_semklo.automationshoprest.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.entities.Product;
import pl.mateusz_semklo.automationshoprest.repositories.ProductsRepository;
import pl.mateusz_semklo.automationshoprest.services.ProductsService;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    ProductsRepository productsRepository;

    @Override
    public Product findById(Integer id) {
        Optional<Product> optionalProducts=productsRepository.findById(id);
        if(optionalProducts.isPresent())
            return optionalProducts.get();
        else
            return null;
    }

    @Override
    public List<Product> findAll() {
        return productsRepository.findAll();
    }

    @Override
    public Product save(Product products) {
        return productsRepository.save(products);
    }

    @Override
    public void delete(Integer id) {
        productsRepository.deleteById(id);
    }
}
