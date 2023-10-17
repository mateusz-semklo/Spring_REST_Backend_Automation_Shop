package pl.mateusz_semklo.automationshoprest.repositories.extensions;

import pl.mateusz_semklo.automationshoprest.entities.Orders;
import pl.mateusz_semklo.automationshoprest.entities.Products;

import java.util.List;

public interface OrdersRepositoryExtenstion {

    public Orders addProducts(Orders order, List<Products> productsList);
    public void xxx();

}
