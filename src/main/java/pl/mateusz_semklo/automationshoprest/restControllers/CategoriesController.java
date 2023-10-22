package pl.mateusz_semklo.automationshoprest.restControllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.mateusz_semklo.automationshoprest.config.Mapper;
import pl.mateusz_semklo.automationshoprest.entities.Category;
import pl.mateusz_semklo.automationshoprest.entities.Product;
import pl.mateusz_semklo.automationshoprest.models.CategoryModel;
import pl.mateusz_semklo.automationshoprest.models.ProductModel;
import pl.mateusz_semklo.automationshoprest.representationAssemblers.CategoryModelAssembler;
import pl.mateusz_semklo.automationshoprest.representationAssemblers.ProductModelAssembler;
import pl.mateusz_semklo.automationshoprest.services.CategoriesService;
import pl.mateusz_semklo.automationshoprest.services.ProductsService;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    CategoriesService categoriesService;

    @Autowired
    ProductsService productsService;

    @Autowired
    ProductModelAssembler productModelAssembler;

    @Autowired
    CategoryModelAssembler categoryModelAssembler;

    @Autowired
    Mapper mapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryModel> getCategories(){
        List<Category> categories=categoriesService.findAll();
        return categoryModelAssembler.toCollectionModel(categories).getContent().stream().toList();
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryModel getCategoryById(@PathVariable("id") Integer id){
        Category category=categoriesService.findById(id);
        return categoryModelAssembler.toModel(category);
    }

    @GetMapping(value = "/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryModel> getCategoriesByName(@PathVariable("name") String name){
        List<Category> categories=categoriesService.findByName(name);
        return categoryModelAssembler.toCollectionModel(categories).getContent().stream().toList();
    }

    @GetMapping(value = "/{id}/products",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductModel> getProducts(@PathVariable("id") Integer id){
        Category category=categoriesService.findById(id);
        return productModelAssembler.toCollectionModel(category.getProducts()).getContent().stream().toList();
    }

    @GetMapping(value = "/{id}/products/{product_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductModel getProductsById(@PathVariable("id") Integer id,@PathVariable("product_id") Integer product_id){
        Product product=productsService.findById(product_id);
        return productModelAssembler.toModel(product);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteCategory(@PathVariable("id") Integer id){
        categoriesService.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveCategory(@RequestBody CategoryModel categoryModel){
        Category category=this.mapper.convertToEntity(categoryModel);
        categoriesService.save(category);
    }

    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void putCategory(@RequestBody CategoryModel categoryModel,@PathVariable("id") Integer id){
        Category category=mapper.convertToEntity(categoryModel);
        category.setCategoryId(id);
        categoriesService.save(category);
    }

    @PatchMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void patchCategory(@RequestBody CategoryModel categoryModel,@PathVariable("id") Integer id){
        Category category=mapper.convertToEntity(categoryModel);
        category.setCategoryId(id);
        categoriesService.save(category);
    }




}
