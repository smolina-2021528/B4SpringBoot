drop database if exists DBMechanicalShop;
create database DBMechanicalShop;
use DBMechanicalShop;

CREATE TABLE Employee (
    id int not null auto_increment,
    first_name varchar(50),
    last_name varchar(50),
    email varchar(100) unique,
    primary key (id)
);

CREATE TABLE Customer (
    id int not null auto_increment,
    full_name varchar(100),
    phone varchar(20),
    email varchar(100) unique,
    primary key (id)
);

CREATE TABLE Product (
    id int not null auto_increment,
    product_name varchar(100),
    brand varchar(50),
    price decimal(10,2),
    stock int not null,
    primary key (id)
);

CREATE TABLE Sale (
    id int not null auto_increment,
    customer_id int,
    employee_id int,
    product_id int,
    total decimal(10,2),
	primary key (id),
    foreign key (customer_id) references Customer(id),
    foreign key(employee_id) references Employee(id),
    foreign key (product_id) references Product(id)
);


-- trigger para poner automatico el precio de ujna venta
DELIMITER $$
CREATE TRIGGER tr_set_sale_price
BEFORE INSERT ON Sale
FOR EACH ROW
BEGIN
    DECLARE prod_price DECIMAL(10,2);
    SELECT price INTO prod_price FROM Product WHERE id = NEW.product_id;
    SET NEW.total = prod_price;
END$$
DELIMITER ;

-- trigger para que el stock baje con cada venta
DELIMITER $$
CREATE TRIGGER trg_update_product_stock
AFTER INSERT ON Sale
FOR EACH ROW
BEGIN
    UPDATE Product
    SET stock = stock - 1
    WHERE id = NEW.product_id;
END$$
DELIMITER ;

select * from Employee;
select * from Sale;
select * from Customer;
select * from Product;