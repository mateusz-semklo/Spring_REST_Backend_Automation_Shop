package pl.mateusz_semklo.automationshoprest.restControllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.mateusz_semklo.automationshoprest.entities.Order;
import pl.mateusz_semklo.automationshoprest.entities.Product;
import pl.mateusz_semklo.automationshoprest.entities.User;
import pl.mateusz_semklo.automationshoprest.models.OrderModel;
import pl.mateusz_semklo.automationshoprest.models.UserModel;
import pl.mateusz_semklo.automationshoprest.representationAssemblers.OrderModelAssembler;
import pl.mateusz_semklo.automationshoprest.representationAssemblers.UserModelAssembler;
import pl.mateusz_semklo.automationshoprest.services.OrdersService;
import pl.mateusz_semklo.automationshoprest.services.UsersService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    UsersService usersService;

    @Autowired
    OrdersService ordersService;

    @Autowired
    OrderModelAssembler orderModelAssembler;

    @Autowired
    UserModelAssembler userModelAssembler;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserModel> getUsers(){
        List<User> users=usersService.findAll();
        return userModelAssembler.toCollectionModel(users).getContent().stream().toList();
    }

    @GetMapping(value = "/{username}",produces = MediaType.APPLICATION_JSON_VALUE)
    public UserModel getUserByUsername(@PathVariable("username") String username){
        User user=usersService.findByUsername(username);
        return userModelAssembler.toModel(user);
    }

    @GetMapping(value = "/{username}/authorities",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getAuthorities(@PathVariable("username") String username){
        User user=usersService.findByUsername(username);
        List<String> authorities=user.getAuthorities();
        return authorities;
    }

    @GetMapping(value = "/{username}/orders",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderModel> getOrders(@PathVariable("username") String username){
        User user=usersService.findByUsername(username);
        List<Order> orders=user.getOrders();
        return orderModelAssembler.toCollectionModel(orders).getContent().stream().toList();
    }

    @GetMapping(value = "/{username}/orders/{order_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderModel getOrdersById(@PathVariable("username") String username,@PathVariable("order_id") Integer order_id){
        Order order=ordersService.findById(order_id);
        return orderModelAssembler.toModel(order);
    }

    @DeleteMapping(value = "/{username}")
    public void deleteUser(@PathVariable("username") String username){
        usersService.delete(username);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveUser(@RequestBody UserModel userModel){
        User user=convertToEntity(userModel);
        usersService.save(user);
    }

    @PutMapping(value = "/{username}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void putUser(@RequestBody UserModel userModel,@PathVariable("username") String username){
        User user=convertToEntity(userModel);
        user.setUsername(username);
        usersService.save(user);
    }

    @PatchMapping(value = "/{username}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void patchUser(@RequestBody UserModel userModel,@PathVariable("username") String username){
        User user=convertToEntity(userModel);
        user.setUsername(username);
        usersService.save(user);
    }

    private UserModel convertToDTO(User user){
        return modelMapper.map(user,UserModel.class);
    }
    private User convertToEntity(UserModel userModel){
        return modelMapper.map(userModel,User.class);
    }
}