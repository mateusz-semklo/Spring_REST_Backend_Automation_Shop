package pl.mateusz_semklo.automationshoprest.RestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.mateusz_semklo.automationshoprest.models.OrderModel;
import pl.mateusz_semklo.automationshoprest.models.ProductModel;
import pl.mateusz_semklo.automationshoprest.services.OrdersService;
import pl.mateusz_semklo.automationshoprest.services.OrdersService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    OrdersService ordersService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderModel> getOrders(){
        return null;
    }
    
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderModel getOrderById(@PathVariable("id") Integer id){
        return null;
    }

    @GetMapping(value = "/{id}/products",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductModel> getProducts(@PathVariable("id") Integer id){
        return null;
    }

    @GetMapping(value = "/{id}/products/{product_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductModel getProductsById(@PathVariable("id") Integer id,@PathVariable("product_id") Integer product_id){
        return null;
    }

    @DeleteMapping(value = "/{id}")
    public void deleteOrder(@PathVariable("id") Integer id){
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveOrder(@RequestBody OrderModel OrderModel){
    }
    
    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void putOrder(@RequestBody OrderModel OrderModel,@PathVariable("id") Integer id){
    }
    
    @PatchMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void patchOrder(@RequestBody OrderModel OrderModel,@PathVariable("id") Integer id){
    }
}
