package pl.mateusz_semklo.automationshoprest.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.mateusz_semklo.automationshoprest.entities.User;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


@SpringBootTest
class UsersServiceTest {

    @Autowired
    UsersService usersService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void findByUsernameMateusz2606() {
        User user=usersService.findByUsername("mateusz2606");

        assertThat(user,notNullValue());
        assertThat(user.getUsername(),equalTo("mateusz2606"));

    }

    @Test
    void findAllUsers() {
        List<User> usersList=usersService.findAll();

        assertThat(usersList,notNullValue());
        assertThat(usersList,not(empty()));
    }

    @Test
    void saveUserAlicjaWithRandomAuthorities() {
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

        User usersAlicja=usersService.save(user);

        assertThat(user,notNullValue());
        assertThat(user.getUsername(),equalTo("alicja1234"));
        assertThat(user.getAuthorities(),notNullValue());
        assertThat(user.getAuthorities(),equalTo(authorities));
        assertThat(user.getAuthorities().size(),equalTo(3));

    }
    @Test
    void saveUserStefanWithoutAuthorities() {
        User user=new User();
        user.setUsername("stefan1234");
        user.setPassword(passwordEncoder.encode("stefan1234"));
        user.setEnabled(true);
        user.setUserEmail("stefan1234@wp.pl");
        user.setUserFirstname("stefan");
        user.setUserLastname("olszak");
        user.setUserStreet("wiosenna 23/2");
        user.setUserCity("Swrzędz");
        user.setUserCountry("Poland");
        user.setUserPostCode("64-120");

        User usersAlicja=usersService.save(user);

        assertThat(user,notNullValue());
        assertThat(user.getUsername(),equalTo("stefan1234"));
        assertThat(user.getAuthorities(),notNullValue());
        assertThat(user.getAuthorities().size(),equalTo(0));

    }
    @Test
    void updateUserMateuszWithCity() {

        User usersMateusz=usersService.findByUsername("mateusz2606");
        usersMateusz.setUserCity("Warszawa");

        User mateusz=usersService.save(usersMateusz);

        assertThat(mateusz,notNullValue());
        assertThat(mateusz.getUserCity(),equalTo("Warszawa"));


    }
    @Test
    void updateUserMateuszWithAddAuthorities() {
        User mateusz2606=usersService.findByUsername("mateusz2606");

        List<String> authorities=mateusz2606.getAuthorities();
        authorities.add("NOWA_ROLc");

        User mateusz=usersService.save(mateusz2606);

        assertThat(mateusz,notNullValue());
        assertThat(mateusz.getAuthorities().size(),equalTo(authorities.size()));

        mateusz.getAuthorities().forEach((auth)-> System.out.println(auth));
    }
    @Test
    void removeOneAuthorityInUserMateusz(){
        User usersMateusz=usersService.findByUsername("mateusz2606");
        int count=usersMateusz.getAuthorities().size();
        usersMateusz.getAuthorities().remove(0);

        User mateusz=usersService.save(usersMateusz);

        assertThat(mateusz,notNullValue());
        assertThat(mateusz.getAuthorities().size(),equalTo(count-1));

    }
    @Test
    void removeALLAuthorityInUserMateusz(){
        User usersMateusz=usersService.findByUsername("mateusz2606");
        usersMateusz.getAuthorities().clear();

        assertDoesNotThrow(()->usersService.save(usersMateusz));
    }

    @Test
    void deleteUserAlicja() {

        User user=new User();
        user.setUsername("alicja12345");
        user.setPassword(passwordEncoder.encode("alicja12345"));
        user.setEnabled(true);
        user.setUserEmail("alicja12345@wp.pl");
        user.setUserFirstname("alicjaa");
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

        usersService.save(user);

        boolean del=usersService.delete("alicja12345");
        User usersAlicja2=usersService.findByUsername("alicja12345");
        assertThat(usersAlicja2,nullValue());
        assertThat(del,is(true));

    }
}