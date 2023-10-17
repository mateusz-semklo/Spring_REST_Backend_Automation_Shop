package pl.mateusz_semklo.automationshoprest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.entities.Orders;
import pl.mateusz_semklo.automationshoprest.repositories.extensions.OrdersRepositoryExtenstion;

import java.util.List;

@Repository
@Transactional
public interface OrdersRepository extends JpaRepository<Orders,Integer>, OrdersRepositoryExtenstion {

    @Query("SELECT o FROM Orders o WHERE o.user.username=:username")
    List<Orders> findOrdersByUsername(@Param("username") String username);



}
