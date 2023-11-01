package pl.mateusz_semklo.automationshoprest.repositories.implementations;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.repositories.extensions.ProductsRepositoryExtension;

@Repository
@Transactional
public class CartsRepositoryImpl implements ProductsRepositoryExtension {
}
