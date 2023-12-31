package pl.mateusz_semklo.automationshoprest.representationAssemblers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import pl.mateusz_semklo.automationshoprest.config.ConfigProperties;
import pl.mateusz_semklo.automationshoprest.restControllers.ProductsController;
import pl.mateusz_semklo.automationshoprest.entities.Product;
import pl.mateusz_semklo.automationshoprest.models.ProductModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductModelAssembler implements RepresentationModelAssembler<Product, ProductModel> {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ProductModel toModel(Product entity) {
        ProductModel productModel= modelMapper.map(entity, ProductModel.class);
        productModel.add(linkTo(methodOn(ProductsController.class).getProductById(productModel.getProductId())).withSelfRel());
        return productModel;
    }

    @Override
    public CollectionModel<ProductModel> toCollectionModel(Iterable<? extends Product> entities) {
        CollectionModel<ProductModel> productModels=RepresentationModelAssembler.super.toCollectionModel(entities);
        productModels.add(linkTo(methodOn(ProductsController.class).getProducts()).withSelfRel());
        return productModels;

    }
}
