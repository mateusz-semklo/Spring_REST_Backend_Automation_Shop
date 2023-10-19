package pl.mateusz_semklo.automationshoprest.representationAssemblers;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import pl.mateusz_semklo.automationshoprest.RestControllers.ProductsController;
import pl.mateusz_semklo.automationshoprest.entities.Product;
import pl.mateusz_semklo.automationshoprest.models.ProductModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductModelAssembler implements RepresentationModelAssembler<Product, ProductModel> {

    ModelMapper modelMapper=new ModelMapper();

    @Override
    public ProductModel toModel(Product entity) {
        ProductModel productModel= modelMapper.map(entity, ProductModel.class);
        productModel.add(linkTo(methodOn(ProductsController.class).getProductById(productModel.getProductId())).withSelfRel());
        productModel.add(linkTo(methodOn(ProductsController.class).getOrders(productModel.getProductId())).withRel("/orders"));
        return productModel;
    }

    @Override
    public CollectionModel<ProductModel> toCollectionModel(Iterable<? extends Product> entities) {
        CollectionModel<ProductModel> productModels=RepresentationModelAssembler.super.toCollectionModel(entities);
        productModels.add(linkTo(methodOn(ProductsController.class).getProducts()).withSelfRel());
        return productModels;

    }
}
