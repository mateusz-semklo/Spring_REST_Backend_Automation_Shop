package pl.mateusz_semklo.automationshoprest.representationAssemblers;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import pl.mateusz_semklo.automationshoprest.RestControllers.UsersController;
import pl.mateusz_semklo.automationshoprest.entities.User;
import pl.mateusz_semklo.automationshoprest.models.UserModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, UserModel> {

    ModelMapper modelMapper=new ModelMapper();

    @Override
    public UserModel toModel(User entity) {
        UserModel userModel= modelMapper.map(entity, UserModel.class);
        userModel.add(linkTo(methodOn(UsersController.class).getUserByUsername(userModel.getUsername())).withSelfRel());
        userModel.add(linkTo(methodOn(UsersController.class).getAuthorities(userModel.getUsername())).withRel("authorities"));
        userModel.add(linkTo(methodOn(UsersController.class).getOrders(userModel.getUsername())).withRel("orders"));
        return userModel;
    }

    @Override
    public CollectionModel<UserModel> toCollectionModel(Iterable<? extends User> entities) {
        CollectionModel<UserModel> UserModels=RepresentationModelAssembler.super.toCollectionModel(entities);
        UserModels.add(linkTo(methodOn(UsersController.class).getUsers()).withSelfRel());
        return UserModels;

    }
}
