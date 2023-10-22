package pl.mateusz_semklo.automationshoprest.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.entities.User;

import java.util.List;

@Transactional
@Service
public interface UsersService {

    User findByUsername(String username);
    List<User> findAll();
    User save(User users);
    boolean delete(String username);

}
