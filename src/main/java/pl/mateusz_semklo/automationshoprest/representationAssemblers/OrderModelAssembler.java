package pl.mateusz_semklo.automationshoprest.representationAssemblers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import pl.mateusz_semklo.automationshoprest.restControllers.OrdersController;
import pl.mateusz_semklo.automationshoprest.entities.Order;
import pl.mateusz_semklo.automationshoprest.models.OrderModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, OrderModel> {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public OrderModel toModel(Order entity) {
        OrderModel orderModel= modelMapper.map(entity, OrderModel.class);
        orderModel.add(linkTo(methodOn(OrdersController.class).getOrderById(orderModel.getOrderId())).withSelfRel());
        orderModel.add(linkTo(methodOn(OrdersController.class).getCarts(orderModel.getOrderId())).withRel("carts"));
        return orderModel;
    }

    @Override
    public CollectionModel<OrderModel> toCollectionModel(Iterable<? extends Order> entities) {
        CollectionModel<OrderModel> orderModels=RepresentationModelAssembler.super.toCollectionModel(entities);
        orderModels.add(linkTo(methodOn(OrdersController.class).getOrders()).withSelfRel());
        return orderModels;

    }
}
