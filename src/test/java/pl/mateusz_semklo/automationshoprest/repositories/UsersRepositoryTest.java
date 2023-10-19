package pl.mateusz_semklo.automationshoprest.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.mateusz_semklo.automationshoprest.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class UsersRepositoryTest {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void findUsersAll(){
        List<User> listUsers=usersRepository.findAll();
        System.out.println("LISTA WSZYSTKICH USERS");
        listUsers.forEach((users -> System.out.println(users.getUsername())));
        assertThat(listUsers,notNullValue());
        assertThat(listUsers,isA(List.class));
    }
    @Test
    void findUsersByUsernameForAdmin(){
        Optional<User> usersOptional=usersRepository.findById("admin");
        User user=usersOptional.get();
        System.out.println("USER ADMIN");
        System.out.println(user.getUsername());
        assertThat(user,notNullValue());
        assertThat(user,isA(User.class));
        assertThat(user.getUsername(),equalToIgnoringCase("admin"));
    }
    @Test
    void findUsersByAuthoritiesForAdmin(){
        Optional<User> usersOptional=usersRepository.findById("admin");
        List<String> authorities=usersOptional.get().getAuthorities();
        System.out.println("LISTA WSZYSTKICH AUTHORITIES DLA USER ADMIN");
        authorities.forEach((authority)-> System.out.println(authority));
        assertThat(authorities,notNullValue());
        assertThat(authorities,hasItem("ROLE_ADMIN"));
        assertThat(authorities,hasItem("ROLE_USER"));
    }
    @Test
    public void saveUserAlicja(){
        User user=new User();
        user.setUsername("alicja1234");
        user.setPassword(passwordEncoder.encode("alicja1234"));
        user.setEnabled(true);
        user.setUserEmail("alicja1234@wp.pl");
        user.setUserFirstname("alicja");
        user.setUserLastname("olszewska");
        user.setUserStreet("wiosenna 23/2");
        user.setUserCity("Swrzędz");
        user.setUserCountry("Poland");
        user.setUserPostCode("64-120");

        List<String> authorities=new ArrayList<>();
        authorities.add("ROLE_USER");
        authorities.add("ROLE_USER_EXTRA");
        authorities.add("ROLE_ADMIN");
        user.setAuthorities(authorities);

        User usersAlicja=usersRepository.save(user);
        assertThat(usersAlicja,notNullValue());
        assertThat(usersAlicja,isA(User.class));
        System.out.println("ZAPISANY USER ALICJA");
        System.out.println(usersAlicja.getUsername());
        System.out.println("KOLEKCJA AUTHORITIES DLA ALICJI");
        usersAlicja.getAuthorities().forEach((auth)-> System.out.println(auth));

    }
    @Test
    public void updateAuthoritiesForUserAlicja(){
        Optional<User> optionalUsers=usersRepository.findById("alicja1234");
        User user;
        if(optionalUsers.isPresent()) {
             user = optionalUsers.get();
        }
        else {
            user=null;
        }

        List<String> authorities=new ArrayList<>();
        authorities.add("ROLE_MAIN");
        user.setAuthorities(authorities);

        User usersAlicja=usersRepository.save(user);
        assertThat(usersAlicja,notNullValue());
        assertThat(usersAlicja,isA(User.class));
        System.out.println("KOLEKCJA AUTHORITIES DLA ALICJI");
        usersAlicja.getAuthorities().forEach((auth)-> System.out.println(auth));
    }
    @Test
    public void deleteUserAlicja(){
        usersRepository.deleteById("alicja1234");
        Optional<User> optionalUsers=usersRepository.findById("alicja1234");
        assertThat(optionalUsers.isEmpty(),is(true));

    }

}