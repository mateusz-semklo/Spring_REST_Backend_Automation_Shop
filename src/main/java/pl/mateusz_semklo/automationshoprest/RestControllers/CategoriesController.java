package pl.mateusz_semklo.automationshoprest.RestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.mateusz_semklo.automationshoprest.models.CategoryModel;
import pl.mateusz_semklo.automationshoprest.models.ProductModel;
import pl.mateusz_semklo.automationshoprest.services.CategoriesService;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    CategoriesService categoriesService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryModel> getCategories(){
        return null;
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryModel getCategoryById(@PathVariable("id") Integer id){
        return null;
    }

    @GetMapping(value = "/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryModel> getCategoriesByName(@PathVariable("name") String name){
        return null;
    }

    @GetMapping(value = "/{id}/products",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductModel> getProducts(@PathVariable("id") Integer id){
        return null;
    }

    @GetMapping(value = "/{id}/products/{product_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductModel getProductsById(@PathVariable("id") Integer id,@PathVariable("product_id") Integer product_id){
        return null;
    }

    @DeleteMapping(value = "/{id}")
    public void deleteCategory(@PathVariable("id") Integer id){
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveCategory(@RequestBody CategoryModel categoryModel){
    }

    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void putCategory(@RequestBody CategoryModel categoryModel,@PathVariable("id") Integer id){
    }

    @PatchMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void patchCategory(@RequestBody CategoryModel categoryModel,@PathVariable("id") Integer id){
    }


}
