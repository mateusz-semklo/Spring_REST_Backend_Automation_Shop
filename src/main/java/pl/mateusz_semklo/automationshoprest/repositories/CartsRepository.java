package pl.mateusz_semklo.automationshoprest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.entities.Cart;
import pl.mateusz_semklo.automationshoprest.repositories.extensions.CartsRepositoryExtension;

@Repository
@Transactional
public interface CartsRepository extends JpaRepository<Cart,Integer>, CartsRepositoryExtension {



}
