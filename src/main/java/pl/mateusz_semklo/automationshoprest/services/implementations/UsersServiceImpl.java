package pl.mateusz_semklo.automationshoprest.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.entities.User;
import pl.mateusz_semklo.automationshoprest.repositories.UsersRepository;
import pl.mateusz_semklo.automationshoprest.services.UsersService;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public User findByUsername(String username) {
         Optional<User> optionalUsers=usersRepository.findById(username);
             if(optionalUsers.isPresent())
                 return optionalUsers.get();
             else
                 return null;
    }

    @Override
    public List<User> findAll() {
        return usersRepository.findAll();
    }

    @Override
    public User save(User user) {
        return usersRepository.save(user);
    }

    @Override
    public void delete(String username) {
        usersRepository.deleteById(username);
    }
}
