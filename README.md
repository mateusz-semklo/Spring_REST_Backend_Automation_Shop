# Spring_REST_Backend_Automation_Shop

REST API dla testowania i prototypowania.
https://infinite-crag-10033-9a1c63145ebd.herokuapp.com

Dane umieszczone w aplikacji są wykorzystyawne dla celów testowych aplikacji sklepu z produktami automatyki przemysłowej.
Niektóre dane są dostępne po autoryzacji
login: admin
haslo: admin

POST /authenticate - pozwala na otrzymanie token JWT po poprawnym uwierzytelnieniu i wykorzystać do otrzymania autoryzowanych danych. Ciało żądania musi zawierać dane uwierzytelniające:
{
"username":"login",
"password":"haslo"
}
W odpowiedzi zostanie wysłany token JWT. Kolejne zapytania clienta mogą zawierać nagłówek Authorization : Bearer token , w celu otrzymania autoryzowanych danych.


Lista zasobów:
/products
/categories
/users
/orders
/carts


Przykłady:

GET:
/products
/products/id
/users
/users/username
/orders
/orders/id
/categories
/categories/categoryName
/carts
/carts/id

POST:
/products
/users
/orders
/categories
/carts


MODELE:

User:
                    
                        export class User {
                            username: string;
                            enabled: boolean;
                            userEmail: string;
                            userFirstname: string;
                            userLastname: string;
                            userStreet: string;
                            userCity: string;
                            userCountry: string;
                            userPostCode: string;
                            authorities: String[];
                            orders: Order[]
                          }
                    
                    
Product:
                    
                        export class Product {
                            productId: number;
                            productName: string;
                            productDescription: string;
                            productImageUrl: string;
                            productPrice: number;
                            category: Category;
                          }
                    
                    
Category:
                    
                        export class Category {
                            categoryName: string;
                            products: Product[];
                          }
                    
                    
Order:
                    
                        export class Order {
                            orderId: number;
                            orderDate: any;
                            orderStreet: string;
                            orderCity: string;
                            orderCountry: string;
                            orderPostCode: string;
                            user: User;
                            carts: CartItem[];
                          }
                    
                    
CartItem:
                    
                        export class CartItem {
                            cartProductId: number;
                            count: number;
                            product:Product;
                          }
                    
