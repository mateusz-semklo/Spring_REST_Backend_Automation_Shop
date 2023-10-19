package pl.mateusz_semklo.automationshoprest.representationAssemblers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import pl.mateusz_semklo.automationshoprest.restControllers.CategoriesController;
import pl.mateusz_semklo.automationshoprest.entities.Category;
import pl.mateusz_semklo.automationshoprest.models.CategoryModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CategoryModelAssembler implements RepresentationModelAssembler<Category, CategoryModel> {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CategoryModel toModel(Category entity) {
        CategoryModel categoryModel= modelMapper.map(entity, CategoryModel.class);
        categoryModel.add(linkTo(methodOn(CategoriesController.class).getCategoryById(categoryModel.getCategoryId())).withSelfRel());
        categoryModel.add(linkTo(methodOn(CategoriesController.class).getCategoriesByName(categoryModel.getCategoryName())).withSelfRel());
        categoryModel.add(linkTo(methodOn(CategoriesController.class).getProducts(categoryModel.getCategoryId())).withRel("/products"));
        return categoryModel;
    }

    @Override
    public CollectionModel<CategoryModel> toCollectionModel(Iterable<? extends Category> entities) {
        CollectionModel<CategoryModel> categoryModels=RepresentationModelAssembler.super.toCollectionModel(entities);
        categoryModels.add(linkTo(methodOn(CategoriesController.class).getCategories()).withSelfRel());
        return categoryModels;

    }
}
