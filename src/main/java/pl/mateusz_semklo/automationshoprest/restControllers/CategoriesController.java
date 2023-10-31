package pl.mateusz_semklo.automationshoprest.restControllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(value = "/origin",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Category> getCategoriesO(){
        List<Category> categories=categoriesService.findAll();
        return categories;
    }

    @GetMapping(value = "/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryModel getCategoryByName(@PathVariable("name") String name){
        Category category=categoriesService.findByName(name);
        return categoryModelAssembler.toModel(category);
    }
    @GetMapping(value = "/origin/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Category getCategoryByNameO(@PathVariable("name") String name){
        return categoriesService.findByName(name);

    }

    @GetMapping(value = "/{name}/products",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductModel> getProducts(@PathVariable("name") String name){
        Category category=categoriesService.findByName(name);
        return productModelAssembler.toCollectionModel(category.getProducts()).getContent().stream().toList();
    }

    @GetMapping(value = "/{name}/products/{product_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductModel getProductsById(@PathVariable("name") String name,@PathVariable("product_id") Integer product_id){
        Product product=productsService.findById(product_id);
        return productModelAssembler.toModel(product);
    }

    @DeleteMapping(value = "/{name}")
    public void deleteCategory(@PathVariable("name") String name){
        categoriesService.delete(name);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryModel> saveCategory(@RequestBody CategoryModel categoryModel){
        Category category=this.mapper.convertToEntity(categoryModel);
        Category result=categoriesService.save(category);
        ResponseEntity<CategoryModel> response=new ResponseEntity<>(mapper.convertToDTO(result),HttpStatus.CREATED);
        return response;
    }

    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryModel> putCategory(@RequestBody CategoryModel categoryModel,@PathVariable("id") Integer id){
        Category category=this.mapper.convertToEntity(categoryModel);
        Category result=categoriesService.save(category);
        ResponseEntity<CategoryModel> response=new ResponseEntity<>(mapper.convertToDTO(result),HttpStatus.OK);
        return response;

    }

    @PatchMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryModel> patchCategory(@RequestBody CategoryModel categoryModel,@PathVariable("id") Integer id){
        Category category=this.mapper.convertToEntity(categoryModel);
        Category result=categoriesService.save(category);
        ResponseEntity<CategoryModel> response=new ResponseEntity<>(mapper.convertToDTO(result),HttpStatus.OK);
        return response;

    }




}
