package pl.mateusz_semklo.automationshoprest.representationAssemblers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import pl.mateusz_semklo.automationshoprest.entities.Cart;
import pl.mateusz_semklo.automationshoprest.models.CartModel;
import pl.mateusz_semklo.automationshoprest.restControllers.CartsController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CartModelAssembler implements RepresentationModelAssembler<Cart, CartModel> {

    @Autowired
    ModelMapper modelMapper;


    @Override
    public CartModel toModel(Cart entity) {
        CartModel cartModel= modelMapper.map(entity, CartModel.class);
        cartModel.add(linkTo(methodOn(CartsController.class).getCartById(cartModel.getCartProductId())).withSelfRel());
        return cartModel;
    }

    @Override
    public CollectionModel<CartModel> toCollectionModel(Iterable<? extends Cart> entities) {
        CollectionModel<CartModel> cartModels=RepresentationModelAssembler.super.toCollectionModel(entities);
        return cartModels;

    }
}
