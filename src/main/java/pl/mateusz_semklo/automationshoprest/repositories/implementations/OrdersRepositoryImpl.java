package pl.mateusz_semklo.automationshoprest.repositories.implementations;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.repositories.extensions.OrdersRepositoryExtenstion;

@Repository
@Transactional
public class OrdersRepositoryImpl implements OrdersRepositoryExtenstion {
}
