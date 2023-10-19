package pl.mateusz_semklo.automationshoprest.RestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.mateusz_semklo.automationshoprest.entities.Order;
import pl.mateusz_semklo.automationshoprest.models.OrderModel;
import pl.mateusz_semklo.automationshoprest.models.ProductModel;
import pl.mateusz_semklo.automationshoprest.services.ProductsService;
import pl.mateusz_semklo.automationshoprest.services.ProductsService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    ProductsService productsService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductModel> getProducts(){
        return null;
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductModel getProductById(@PathVariable("id") Integer id){
        return null;
    }

    @GetMapping(value = "/{id}/orders",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderModel> getOrders(@PathVariable("id") Integer id){
        return null;
    }

    @GetMapping(value = "/{id}/orders/{order_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderModel getOrderById(@PathVariable("id") Integer id,@PathVariable("order_id") Integer order_id){
        return null;
    }

    @DeleteMapping(value = "/{id}")
    public void deleteProduct(@PathVariable("id") Integer id){
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveProduct(@RequestBody ProductModel ProductModel){
    }

    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void putProduct(@RequestBody ProductModel ProductModel,@PathVariable("id") Integer id){
    }

    @PatchMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void patchProduct(@RequestBody ProductModel ProductModel,@PathVariable("id") Integer id){

    }
}
