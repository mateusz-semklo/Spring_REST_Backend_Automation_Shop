package pl.mateusz_semklo.automationshoprest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.entities.Orders;
import pl.mateusz_semklo.automationshoprest.repositories.extensions.OrdersRepositoryExtenstion;

@Repository
@Transactional
public interface OrdersRepository extends JpaRepository<Orders,Integer>, OrdersRepositoryExtenstion {
}
