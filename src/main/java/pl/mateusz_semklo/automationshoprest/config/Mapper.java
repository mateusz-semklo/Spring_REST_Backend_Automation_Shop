package pl.mateusz_semklo.automationshoprest.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mateusz_semklo.automationshoprest.entities.*;
import pl.mateusz_semklo.automationshoprest.models.*;

@Component
public class Mapper {
    @Autowired
    ModelMapper modelMapper;

    public CategoryModel convertToDTO(Category category){
        return modelMapper.map(category,CategoryModel.class);
    }
    
    public Category convertToEntity(CategoryModel categoryModel){
        return modelMapper.map(categoryModel,Category.class);
    }
    
    public OrderModel convertToDTO(Order order){
        return modelMapper.map(order,OrderModel.class);
    }
    
    public Order convertToEntity(OrderModel orderModel){
        return modelMapper.map(orderModel,Order.class);
    }

    public ProductModel convertToDTO(Product product){
        return modelMapper.map(product,ProductModel.class);
    }
    
    public Product convertToEntity(ProductModel productModel){
        return modelMapper.map(productModel,Product.class);
    }

    public UserModel convertToDTO(User user){
        return modelMapper.map(user,UserModel.class);
    }
    
    public User convertToEntity(UserModel userModel){
        return modelMapper.map(userModel,User.class);
    }

    public CartModel convertToDTO(Cart cart){
        return modelMapper.map(cart, CartModel.class);
    }

    public Cart convertToEntity(CartModel cartModel){
        return modelMapper.map(cartModel,Cart.class);
    }
    
}
