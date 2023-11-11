package pl.mateusz_semklo.automationshoprest.restControllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mateusz_semklo.automationshoprest.config.Mapper;
import pl.mateusz_semklo.automationshoprest.entities.Cart;
import pl.mateusz_semklo.automationshoprest.entities.Order;
import pl.mateusz_semklo.automationshoprest.entities.Product;
import pl.mateusz_semklo.automationshoprest.entities.User;
import pl.mateusz_semklo.automationshoprest.models.CartModel;
import pl.mateusz_semklo.automationshoprest.models.OrderModel;
import pl.mateusz_semklo.automationshoprest.models.OrderPostModel;
import pl.mateusz_semklo.automationshoprest.models.ProductModel;
import pl.mateusz_semklo.automationshoprest.representationAssemblers.CartModelAssembler;
import pl.mateusz_semklo.automationshoprest.representationAssemblers.OrderModelAssembler;
import pl.mateusz_semklo.automationshoprest.representationAssemblers.ProductModelAssembler;
import pl.mateusz_semklo.automationshoprest.services.CartsService;
import pl.mateusz_semklo.automationshoprest.services.OrdersService;
import pl.mateusz_semklo.automationshoprest.services.ProductsService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    OrdersService ordersService;

    @Autowired
    CartsService cartsService;

    @Autowired
    Mapper mapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OrderModelAssembler orderModelAssembler;

    @Autowired
    CartModelAssembler cartModelAssembler;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderModel> getOrders(){
        List<Order> orders=ordersService.findAll();
        return orderModelAssembler.toCollectionModel(orders).getContent().stream().toList();
    }
    
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderModel getOrderById(@PathVariable("id") Integer id){
        Order order=ordersService.findById(id);
        if(order!=null) return orderModelAssembler.toModel(order);
        else return null;
    }

    @GetMapping(value = "/{id}/carts",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CartModel> getCarts(@PathVariable("id") Integer id){
        Order order=ordersService.findById(id);
        List<Cart> carts=order.getCarts();
        return cartModelAssembler.toCollectionModel(carts).getContent().stream().toList();
    }

    @GetMapping(value = "/{id}/carts/{cart_product_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public CartModel getCartsById(@PathVariable("id") Integer id,@PathVariable("cart_product_id") Integer cart_product_id){
        Cart cart= cartsService.findById(cart_product_id);
        if(cart!=null) return cartModelAssembler.toModel(cart);
        else return null;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderModel> save(@RequestBody OrderModel orderModel) throws JsonProcessingException {
        Order order=mapper.convertToEntity(orderModel);
        Order result=ordersService.save(order);
        OrderModel model=mapper.convertToDTO(result);
        ResponseEntity<OrderModel> response=new ResponseEntity<>(model,HttpStatus.CREATED);
        return response;
    }

    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderModel> putOrder(@RequestBody OrderModel orderModel,@PathVariable("id") Integer id){
        orderModel.setOrderId(id);
        Order order1=mapper.convertToEntity(orderModel);
        Order result=ordersService.save(order1);
        OrderModel model=mapper.convertToDTO(result);
        ResponseEntity<OrderModel> response=new ResponseEntity<>(model,HttpStatus.OK);
        return response;


    }
    
    @PatchMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderModel> patchOrder(@RequestBody OrderModel orderModel,@PathVariable("id") Integer id){
        orderModel.setOrderId(id);
        Order order1=mapper.convertToEntity(orderModel);
        Order result=ordersService.save(order1);
        OrderModel model=mapper.convertToDTO(result);
        ResponseEntity<OrderModel> response=new ResponseEntity<>(model,HttpStatus.OK);
        return response;

    }

    @DeleteMapping(value = "/{id}")
    public void deleteOrder(@PathVariable("id") Integer id){
        ordersService.delete(id);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = "/post",consumes = MediaType.TEXT_PLAIN_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderModel> saveOrder(@RequestBody String order) throws JsonProcessingException {
        OrderPostModel order1=objectMapper.readValue(order, OrderPostModel.class);
        Order result=ordersService.saveOrder(order1);
        OrderModel model=mapper.convertToDTO(result);
        ResponseEntity<OrderModel> response=new ResponseEntity<>(model,HttpStatus.CREATED);
        return response;
    }

    @PostMapping(value = "/origin",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderModel> saveOrder2(@RequestBody Order order) throws JsonProcessingException {
        Order result=ordersService.save(order);
        OrderModel model=mapper.convertToDTO(result);
        ResponseEntity<OrderModel> response=new ResponseEntity<>(model,HttpStatus.CREATED);
        return response;
    }

    @GetMapping(value = "/origin", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> getOrdersO() {
        List<Order> users = ordersService.findAll();
        return users;
    }

    @GetMapping(value = "/origin/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Order getOrderByIdO(@PathVariable("id") Integer id) {
        return ordersService.findById(id);
    }



}
