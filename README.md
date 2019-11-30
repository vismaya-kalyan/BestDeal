# BestDeal

1.Paste BestDeal file in Tomcat webapps folder

2.Navigate to the class file folder BestDeal\WEB-INF\classes

3.Run the java files javac *.java

4.Start MySQL Server

5.Run the following command in mysql

create database bestdealdatabase;

use bestdealdatabase;

create table Registration(username varchar(40),password varchar(40),repassword varchar(40),
usertype varchar(40));


Create table CustomerOrders
(
    OrderId integer,
    userName varchar(40),
    orderName varchar(40),
    orderPrice double,
    userAddress varchar(40),
    creditCardNo varchar(40),
    orderTime varchar(40),
    Primary key(OrderId,userName,orderName)
);

Create table Productdetails
(
	ProductType varchar(20),
	Id varchar(20),
	productName varchar(40),
	productPrice double,
	productImage varchar(40),
	productManufacturer varchar(40),
	productCondition varchar(40),
	productDiscount double,
	productQuantity integer,
	productOnSale varchar(40),
	productRebate varchar(40),
	Primary key(Id)
);

CREATE TABLE Product_accessories (
    productName varchar(20),
    accessoriesName  varchar(20),
    

    FOREIGN KEY (productName) REFERENCES Productdetails(Id) ON DELETE SET NULL
        ON UPDATE CASCADE,
    FOREIGN KEY (accessoriesName) REFERENCES Productdetails(Id) ON DELETE SET NULL
        ON UPDATE CASCADE    
);


6.start Mongod.exe and mongo.exe
Create a database called Reviews (use Reviews)
Create collections using command db.createCollection(productReviews) 
and to see inside collection
db.productReviews.find()

7. Sart the tomcat server

8. go to browser and type http://localhost/BestDeal/