package pl.mateusz_semklo.automationshoprest.restControllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mateusz_semklo.automationshoprest.config.Mapper;
import pl.mateusz_semklo.automationshoprest.entities.Category;
import pl.mateusz_semklo.automationshoprest.entities.Order;
import pl.mateusz_semklo.automationshoprest.entities.Product;
import pl.mateusz_semklo.automationshoprest.entities.User;
import pl.mateusz_semklo.automationshoprest.models.CategoryModel;
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
    Mapper mapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserModel> getUsers(){
        List<User> users=usersService.findAll();
        return userModelAssembler.toCollectionModel(users).getContent().stream().toList();
    }

    @GetMapping(value = "/{username}",produces = MediaType.APPLICATION_JSON_VALUE)
    public UserModel getUserByUsername(@PathVariable("username") String username){
        User user=usersService.findByUsername(username);
        if(user==null) return null;
        else return userModelAssembler.toModel(user);
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
    public ResponseEntity<UserModel> saveUser(@RequestBody UserModel userModel){
        User user=mapper.convertToEntity(userModel);
        User result=usersService.save(user);
        ResponseEntity<UserModel> response=new ResponseEntity<>(mapper.convertToDTO(result), HttpStatus.CREATED);
        return response;
    }

    @PutMapping(value = "/{username}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> putUser(@RequestBody UserModel userModel,@PathVariable("username") String username){
        userModel.setUsername(username);
        User user=mapper.convertToEntity(userModel);
        User result=usersService.save(user);
        ResponseEntity<UserModel> response=new ResponseEntity<>(mapper.convertToDTO(result), HttpStatus.OK);
        return response;

    }

    @PatchMapping(value = "/{username}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> patchUser(@RequestBody UserModel userModel,@PathVariable("username") String username){
        userModel.setUsername(username);
        User user=mapper.convertToEntity(userModel);
        User result=usersService.save(user);
        ResponseEntity<UserModel> response=new ResponseEntity<>(mapper.convertToDTO(result), HttpStatus.OK);
        return response;

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping(value = "/origin", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsersO() {
        List<User> users = usersService.findAll();
        return users;
    }

    @GetMapping(value = "/origin/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUsersByNameO(@PathVariable("name") String username) {
        return usersService.findByUsername(username);
    }

}
