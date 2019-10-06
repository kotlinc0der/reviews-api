# Reviews API 
Supports operations for writing reviews and listing reviews for a product.

### Prerequisites
MySQL and MongoDB need to be installed and configured. Instructions provided separately.

### Instructions
* Configure the MySQL & MongoDB Datasource in application.properties.
* You have to use basic authentication to access the rest api. (admin user configured)    
* To run this service you execute:
    
    ```
    $ mvn clean package
    ```
    
    ```
    $ java -jar target/reviews-api-0.0.1-SNAPSHOT.jar
    ```
    
    It can also be imported in your IDE as a Maven project.

### Documentation

Check out Swagger UI: http://localhost:8080/swagger-ui.html
