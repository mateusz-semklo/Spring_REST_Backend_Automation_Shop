package pl.mateusz_semklo.automationshoprest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.entities.Users;
import pl.mateusz_semklo.automationshoprest.repositories.extensions.UsersRepositoryExtension;

@Repository
@Transactional
public interface UsersRepository extends JpaRepository<Users,String>, UsersRepositoryExtension {
}
