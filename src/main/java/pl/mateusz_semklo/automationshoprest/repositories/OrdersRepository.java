package pl.mateusz_semklo.automationshoprest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.entities.Order;
import pl.mateusz_semklo.automationshoprest.repositories.extensions.OrdersRepositoryExtension;

import java.util.List;

@Repository
@Transactional
public interface OrdersRepository extends JpaRepository<Order,Integer>, OrdersRepositoryExtension {

    @Query("SELECT o FROM Order o WHERE o.user.username=:username")
    List<Order> findOrdersByUsername(@Param("username") String username);



}
