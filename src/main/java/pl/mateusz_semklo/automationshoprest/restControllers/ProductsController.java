package pl.mateusz_semklo.automationshoprest.restControllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.mateusz_semklo.automationshoprest.entities.Order;
import pl.mateusz_semklo.automationshoprest.entities.Product;
import pl.mateusz_semklo.automationshoprest.models.OrderModel;
import pl.mateusz_semklo.automationshoprest.models.ProductModel;
import pl.mateusz_semklo.automationshoprest.representationAssemblers.OrderModelAssembler;
import pl.mateusz_semklo.automationshoprest.representationAssemblers.ProductModelAssembler;
import pl.mateusz_semklo.automationshoprest.services.OrdersService;
import pl.mateusz_semklo.automationshoprest.services.ProductsService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    ProductsService productsService;

    @Autowired
    OrdersService ordersService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ProductModelAssembler productModelAssembler;

    @Autowired
    OrderModelAssembler orderModelAssembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductModel> getProducts(){
        List<Product> products=productsService.findAll();
        return productModelAssembler.toCollectionModel(products).getContent().stream().toList();
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductModel getProductById(@PathVariable("id") Integer id){
        Product product=productsService.findById(id);
        return productModelAssembler.toModel(product);
    }

    @GetMapping(value = "/{id}/orders",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderModel> getOrders(@PathVariable("id") Integer id){
        Product product=productsService.findById(id);
        List<Order> orders=product.getOrders();
        return orderModelAssembler.toCollectionModel(orders).getContent().stream().toList();
    }

    @GetMapping(value = "/{id}/orders/{order_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderModel getOrderById(@PathVariable("id") Integer id,@PathVariable("order_id") Integer order_id){
        Order order=ordersService.findById(order_id);
        return orderModelAssembler.toModel(order);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteProduct(@PathVariable("id") Integer id){
        productsService.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveProduct(@RequestBody ProductModel productModel){
        Product product=convertToEntity(productModel);
        productsService.save(product);
    }

    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void putProduct(@RequestBody ProductModel productModel,@PathVariable("id") Integer id){
        Product product=convertToEntity(productModel);
        product.setProductId(id);
        productsService.save(product);
    }

    @PatchMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void patchProduct(@RequestBody ProductModel productModel,@PathVariable("id") Integer id){
        Product product=convertToEntity(productModel);
        product.setProductId(id);
        productsService.save(product);
    }

    private ProductModel convertToDTO(Product product){
        return modelMapper.map(product,ProductModel.class);
    }

    private Product convertToEntity(ProductModel productModel){
        return modelMapper.map(productModel,Product.class);
    }
}
