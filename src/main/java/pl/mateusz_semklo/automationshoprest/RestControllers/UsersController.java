package pl.mateusz_semklo.automationshoprest.RestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.mateusz_semklo.automationshoprest.entities.Order;
import pl.mateusz_semklo.automationshoprest.models.OrderModel;
import pl.mateusz_semklo.automationshoprest.models.UserModel;
import pl.mateusz_semklo.automationshoprest.services.UsersService;
import pl.mateusz_semklo.automationshoprest.services.UsersService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    UsersService usersService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserModel> getUsers(){
        return null;
    }

    @GetMapping(value = "/{username}",produces = MediaType.APPLICATION_JSON_VALUE)
    public UserModel getUserByUsername(@PathVariable("username") String username){
        return null;
    }

    @GetMapping(value = "/{username}/authorities",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getAuthorities(@PathVariable("username") String username){
        return null;
    }

    @GetMapping(value = "/{username}/orders",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderModel> getOrders(@PathVariable("username") String username){
        return null;
    }

    @GetMapping(value = "/{username}/orders/{order_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderModel getOrdersById(@PathVariable("username") String username,@PathVariable("order_id") Integer order_id){
        return null;
    }

    @DeleteMapping(value = "/{username}")
    public void deleteUser(@PathVariable("username") String username){
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveUser(@RequestBody UserModel userModel){
    }

    @PutMapping(value = "/{username}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void putUser(@RequestBody UserModel userModel,@PathVariable("username") String username){
    }

    @PatchMapping(value = "/{username}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void patchUser(@RequestBody UserModel userModel,@PathVariable("username") String username){
    }
}
