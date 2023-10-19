package pl.mateusz_semklo.automationshoprest.representationAssemblers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import pl.mateusz_semklo.automationshoprest.entities.Category;
import pl.mateusz_semklo.automationshoprest.models.CategoryModel;

import static org.springframework.hateoas.server.core.WebHandler.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class CategoryModelAssembler implements RepresentationModelAssembler<Category, CategoryModel> {
    @Override
    public CategoryModel toModel(Category entity) {
        return null;
    }

    @Override
    public CollectionModel<CategoryModel> toCollectionModel(Iterable<? extends Category> entities) {
        CollectionModel<CategoryModel> categoryModels=RepresentationModelAssembler.super.toCollectionModel(entities);
      //  categoryModels.add(linkTo(methodOn()))
                return null;

    }
}
