package pl.mateusz_semklo.automationshoprest.restControllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mateusz_semklo.automationshoprest.config.Mapper;
import pl.mateusz_semklo.automationshoprest.entities.Order;
import pl.mateusz_semklo.automationshoprest.entities.Product;
import pl.mateusz_semklo.automationshoprest.models.OrderModel;
import pl.mateusz_semklo.automationshoprest.models.OrderPostModel;
import pl.mateusz_semklo.automationshoprest.models.ProductModel;
import pl.mateusz_semklo.automationshoprest.representationAssemblers.OrderModelAssembler;
import pl.mateusz_semklo.automationshoprest.representationAssemblers.ProductModelAssembler;
import pl.mateusz_semklo.automationshoprest.services.OrdersService;
import pl.mateusz_semklo.automationshoprest.services.ProductsService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    OrdersService ordersService;

    @Autowired
    ProductsService productsService;

    @Autowired
    Mapper mapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OrderModelAssembler orderModelAssembler;

    @Autowired
    ProductModelAssembler productModelAssembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderModel> getOrders(){
        List<Order> orders=ordersService.findAll();
        return orderModelAssembler.toCollectionModel(orders).getContent().stream().toList();
    }
    
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderModel getOrderById(@PathVariable("id") Integer id){
        Order order=ordersService.findById(id);
        return orderModelAssembler.toModel(order);
    }

    @GetMapping(value = "/{id}/products",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductModel> getProducts(@PathVariable("id") Integer id){
        Order order=ordersService.findById(id);
        List<Product> products=order.getProducts();
        return productModelAssembler.toCollectionModel(products).getContent().stream().toList();
    }

    @GetMapping(value = "/{id}/products/{product_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductModel getProductsById(@PathVariable("id") Integer id,@PathVariable("product_id") Integer product_id){
        Product product= productsService.findById(product_id);
        return productModelAssembler.toModel(product);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteOrder(@PathVariable("id") Integer id){
        ordersService.delete(id);
    }

    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderModel> saveOrder(@RequestBody String order) throws JsonProcessingException {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        OrderPostModel order1=objectMapper.readValue(order, OrderPostModel.class);
        Order result=ordersService.saveOrder(order1);
        OrderModel model=mapper.convertToDTO(result);
        ResponseEntity<OrderModel> response=new ResponseEntity<>(model,HttpStatus.CREATED);
        return response;
    }
    @PostMapping(value = "/post",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderModel> save(@RequestBody Order order) throws JsonProcessingException {
        Order result=ordersService.save(order);
        OrderModel model=mapper.convertToDTO(result);
        ResponseEntity<OrderModel> response=new ResponseEntity<>(model,HttpStatus.CREATED);
        return response;
    }

    
    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void putOrder(@RequestBody OrderModel orderModel,@PathVariable("id") Integer id){

    }
    
    @PatchMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void patchOrder(@RequestBody OrderModel orderModel,@PathVariable("id") Integer id){

    }



}
