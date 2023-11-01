package pl.mateusz_semklo.automationshoprest.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mateusz_semklo.automationshoprest.config.Mapper;
import pl.mateusz_semklo.automationshoprest.entities.Cart;
import pl.mateusz_semklo.automationshoprest.models.CartModel;
import pl.mateusz_semklo.automationshoprest.representationAssemblers.OrderModelAssembler;
import pl.mateusz_semklo.automationshoprest.representationAssemblers.CartModelAssembler;
import pl.mateusz_semklo.automationshoprest.services.OrdersService;
import pl.mateusz_semklo.automationshoprest.services.CartsService;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartsController {
    @Autowired
    CartsService cartsService;

    @Autowired
    OrdersService ordersService;

    @Autowired
    Mapper mapper;

    @Autowired
    CartModelAssembler cartModelAssembler;

    @Autowired
    OrderModelAssembler orderModelAssembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CartModel> getCarts(){
        List<Cart> carts=cartsService.findAll();
        return cartModelAssembler.toCollectionModel(carts).getContent().stream().toList();
    }
    
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public CartModel getCartById(@PathVariable("id") Integer id){
        Cart cart=cartsService.findById(id);
        return cartModelAssembler.toModel(cart);
    }
    
    @DeleteMapping(value = "/{id}")
    public void deleteCart(@PathVariable("id") Integer id){
        cartsService.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartModel> saveCart(@RequestBody CartModel cartModel){
        Cart cart=mapper.convertToEntity(cartModel);
        Cart result=cartsService.save(cart);
        ResponseEntity<CartModel> response=new ResponseEntity<>(mapper.convertToDTO(result), HttpStatus.CREATED);
        return response;
    }

    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartModel> putCart(@RequestBody CartModel cartModel,@PathVariable("id") Integer id){
        cartModel.setCartProductId(id);
        Cart cart=mapper.convertToEntity(cartModel);
        Cart result=cartsService.save(cart);
        ResponseEntity<CartModel> response=new ResponseEntity<>(mapper.convertToDTO(result), HttpStatus.OK);
        return response;

    }

    @PatchMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartModel> patchCart(@RequestBody CartModel cartModel,@PathVariable("id") Integer id){
        cartModel.setCartProductId(id);
        Cart cart=mapper.convertToEntity(cartModel);
        Cart result=cartsService.save(cart);
        ResponseEntity<CartModel> response=new ResponseEntity<>(mapper.convertToDTO(result), HttpStatus.OK);
        return response;
    }


}
