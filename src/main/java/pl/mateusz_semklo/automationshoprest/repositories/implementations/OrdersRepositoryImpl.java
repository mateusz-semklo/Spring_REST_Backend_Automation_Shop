package pl.mateusz_semklo.automationshoprest.repositories.implementations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.entities.Orders;
import pl.mateusz_semklo.automationshoprest.entities.OrdersProducts;
import pl.mateusz_semklo.automationshoprest.entities.Products;
import pl.mateusz_semklo.automationshoprest.entities.Users;
import pl.mateusz_semklo.automationshoprest.repositories.OrdersRepository;
import pl.mateusz_semklo.automationshoprest.repositories.UsersRepository;
import pl.mateusz_semklo.automationshoprest.repositories.extensions.OrdersRepositoryExtenstion;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class OrdersRepositoryImpl implements OrdersRepositoryExtenstion {

    @PersistenceContext
    EntityManager entityManager;


    public Orders addProducts(Orders order, List<Products> productsList){
        List<OrdersProducts> ordersProductsList=order.getOrdersProductsList();
        productsList.forEach((products -> ordersProductsList.add(new OrdersProducts(products,order))));

        return order;
    }

    public void xxx(){

        Orders orders=entityManager.find(Orders.class,1055);
        Products products=entityManager.find(Products.class,1024);

        OrdersProducts ordersProducts=new OrdersProducts(products,orders);
        entityManager.persist(ordersProducts);


    }
}
