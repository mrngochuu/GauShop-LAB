-- Database export via SQLPro (https://www.sqlprostudio.com/allapps.html)
-- Exported by ngochuu at 01-03-2020 21:43.
-- WARNING: This file may contain descructive statements such as DROPs.
-- Please ensure that you are running the script at the proper location.
CREATE DATABASE ShopingOnline;
USE ShopingOnline

-- BEGIN TABLE dbo.Categories
CREATE TABLE dbo.Categories (
	CategoryID int NOT NULL IDENTITY(1,1),
	CategoryName varchar(50) NOT NULL
);
GO

ALTER TABLE dbo.Categories ADD CONSTRAINT PK__Categori__19093A2B834B4CDA PRIMARY KEY (CategoryID);
GO

-- Inserting 8 rows into dbo.Categories
-- Insert batch #1
INSERT INTO dbo.Categories (CategoryName) VALUES
('Vegetables'),
('Fruits'),
('Meat and Poultry'),
('Fish and Seafood'),
('Fast foods'),
('Coffee'),
('Beer cocktail'),
('Soft drinks');

-- END TABLE dbo.Categories

-- BEGIN TABLE dbo.OrderDetails
CREATE TABLE dbo.OrderDetails (
	Quantity int NOT NULL DEFAULT ('1'),
	Price real(4) NOT NULL,
	OrderID int NOT NULL,
	ProductID int NOT NULL
);
GO

-- Inserting 5 rows into dbo.OrderDetails
-- Insert batch #1
INSERT INTO dbo.OrderDetails (Quantity, Price, OrderID, ProductID) VALUES
(1, 18, 1, 4),
(1, 18, 4, 4),
(4, 123, 4, 7),
(5, 80, 5, 5),
(1, 13, 5, 1);

-- END TABLE dbo.OrderDetails

-- BEGIN TABLE dbo.Orders
CREATE TABLE dbo.Orders (
	OrderID int NOT NULL IDENTITY(1,1),
	isCheckout bit NOT NULL DEFAULT ('0'),
	OrderPhone varchar(20) NULL DEFAULT (NULL),
	OrderAddress varchar(max) NULL DEFAULT (NULL),
	PaymentType varchar(30) NULL,
	Username varchar(50) NOT NULL,
	RecipientName varchar(50) NULL DEFAULT (NULL),
	CheckoutDate datetime NULL DEFAULT (NULL)
);
GO

ALTER TABLE dbo.Orders ADD CONSTRAINT PK__Orders__C3905BAF8B207DF5 PRIMARY KEY (OrderID);
GO

-- Inserting 5 rows into dbo.Orders
-- Insert batch #1
INSERT INTO dbo.Orders (isCheckout, OrderPhone, OrderAddress, PaymentType, Username, RecipientName, CheckoutDate) VALUES
(1, '039-7189696', '1', 'cast', 'huudnse130557@fpt.edu.vn', 'Do Ngoc Huu', '2020-03-01 21:32:20'),
(0, NULL, NULL, NULL, 'huudnse130557@fpt.edu.vn', NULL, NULL),
(1, '039-7189696', '381 PVT, Binh Thanh', 'cast', 'dongochuu95@gmail.com', 'a', '2020-03-01 21:40:01'),
(1, '039-7189696', '381 PVT, Binh Thanh', 'cast', 'dongochuu95@gmail.com', 'a', '2020-03-01 21:40:44'),
(0, NULL, NULL, NULL, 'dongochuu95@gmail.com', NULL, NULL);

-- END TABLE dbo.Orders

-- BEGIN TABLE dbo.Products
CREATE TABLE dbo.Products (
	ProductID int NOT NULL IDENTITY(1,1),
	ProductName varchar(50) NOT NULL,
	Price real(4) NOT NULL,
	Quantity int NOT NULL DEFAULT ('1'),
	ImgUrl varchar(max) NOT NULL,
	Status varchar(20) NOT NULL DEFAULT ('active'),
	CategoryID int NOT NULL,
	postingDate datetime NOT NULL,
	Description varchar(200) NOT NULL
);
GO

ALTER TABLE dbo.Products ADD CONSTRAINT PK__Products__B40CC6EDEB37341E PRIMARY KEY (ProductID);
GO

-- Inserting 27 rows into dbo.Products
-- Insert batch #1
INSERT INTO dbo.Products (ProductName, Price, Quantity, ImgUrl, Status, CategoryID, postingDate, Description) VALUES
('Chili-Rubbed Steak', 13, 19, 'chili.jpeg', 'active', 5, '2020-03-01 16:45:07', 'Chili-Rubbed Steak Tacos'),
('Asian Noodle', 5, 2, '1580145705319.jpeg', 'active', 5, '2020-03-01 16:46:29', 'Asian Noodle Salad'),
('Chocolate mint strawberry water', 13, 20, 'Screen Shot 2020-03-01 at 16.47.59.png', 'inactive', 2, '2020-03-01 16:50:01', 'P2 hCG Diet Fruit Drink Recipe | Chocolate Mint Strawberry Water | SP\r\n'),
('Tropical Fruit Punch for Juicers', 18, 6, 'Screen Shot 2020-03-01 at 16.51.50.png', 'active', 2, '2020-03-01 17:31:01', 'Juicing fresh fruit is a quick and excellent way to get your vitamin shots for the day.'),
('SEAFOOD PAELLA RECIPE', 80, 2, 'Screen Shot 2020-03-01 at 16.54.32.png', 'active', 4, '2020-03-01 16:55:44', 'This seafood Paella is a cool dish to make for a summer dinner party.'),
('Fishery & Seafood', 123, 98, 'istockphoto-678837162-612x612.jpg', 'inactive', 4, '2020-03-01 21:41:00', 'Biodiversity has become a trademark of Indonesian marine fishery products.'),
('Fishery & Seafood', 123, 95, 'istockphoto-678837162-612x612.jpg', 'inactive', 4, '2020-03-01 21:41:22', 'Biodiversity has become a trademark of Indonesian marine fishery products.'),
('Fishery & Seafood', 123, 99, 'istockphoto-678837162-612x612.jpg', 'active', 4, '2020-03-01 17:01:50', 'Biodiversity has become a trademark of Indonesian marine fishery products.'),
('Fishery & Seafood', 123, 99, 'istockphoto-678837162-612x612.jpg', 'active', 4, '2020-03-01 17:01:50', 'Biodiversity has become a trademark of Indonesian marine fishery products.'),
('Fishery & Seafood', 123, 99, 'istockphoto-678837162-612x612.jpg', 'active', 4, '2020-03-01 17:01:50', 'Biodiversity has become a trademark of Indonesian marine fishery products.'),
('Fishery & Seafood', 123, 99, 'istockphoto-678837162-612x612.jpg', 'active', 4, '2020-03-01 17:01:50', 'Biodiversity has become a trademark of Indonesian marine fishery products.'),
('Fishery & Seafood', 123, 1, 'istockphoto-678837162-612x612.jpg', 'inactive', 4, '2020-03-01 21:41:14', 'Biodiversity has become a trademark of Indonesian marine fishery products.'),
('Fishery & Seafood', 123, 99, 'istockphoto-678837162-612x612.jpg', 'active', 4, '2020-03-01 17:01:50', 'Biodiversity has become a trademark of Indonesian marine fishery products.'),
('Fishery & Seafood', 123, 99, 'istockphoto-678837162-612x612.jpg', 'active', 4, '2020-03-01 17:01:50', 'Biodiversity has become a trademark of Indonesian marine fishery products.'),
('Fishery & Seafood', 123, 99, 'istockphoto-678837162-612x612.jpg', 'active', 4, '2020-03-01 17:01:50', 'Biodiversity has become a trademark of Indonesian marine fishery products.'),
('Fishery & Seafood', 123, 99, 'istockphoto-678837162-612x612.jpg', 'active', 4, '2020-03-01 17:01:50', 'Biodiversity has become a trademark of Indonesian marine fishery products.'),
('Fishery & Seafood', 123, 99, 'istockphoto-678837162-612x612.jpg', 'active', 4, '2020-03-01 17:01:50', 'Biodiversity has become a trademark of Indonesian marine fishery products.'),
('Fihery & Seafood', 123, 99, 'istockphoto-678837162-612x612.jpg', 'active', 4, '2020-03-01 17:01:50', 'Biodiversity has become a trademark of Indonesian marine fishery products.'),
('Fishery & Seafood', 123, 99, 'istockphoto-678837162-612x612.jpg', 'active', 4, '2020-03-01 17:01:50', 'Biodiversity has become a trademark of Indonesian marine fishery products.'),
('Fishery & Seafood', 123, 99, 'istockphoto-678837162-612x612.jpg', 'active', 4, '2020-03-01 17:01:50', 'Biodiversity has become a trademark of Indonesian marine fishery products.'),
('Fishery & Seafood', 123, 99, 'istockphoto-678837162-612x612.jpg', 'active', 4, '2020-03-01 17:01:50', 'Biodiversity has become a trademark of Indonesian marine fishery products.'),
('Fishery & Seafood', 123, 99, 'istockphoto-678837162-612x612.jpg', 'active', 4, '2020-03-01 17:01:50', 'Biodiversity has become a trademark of Indonesian marine fishery products.'),
('Fishery & Seafood', 123, 99, 'istockphoto-678837162-612x612.jpg', 'active', 4, '2020-03-01 17:01:50', 'Biodiversity has become a trademark of Indonesian marine fishery products.'),
('Fishery & Seafood', 123, 99, 'istockphoto-678837162-612x612.jpg', 'active', 4, '2020-03-01 17:01:50', 'Biodiversity has become a trademark of Indonesian marine fishery products.'),
('Fishery & Seafood', 123, 99, 'istockphoto-678837162-612x612.jpg', 'active', 4, '2020-03-01 17:01:50', 'Biodiversity has become a trademark of Indonesian marine fishery products.'),
('Fishery & Seafood', 123, 99, 'istockphoto-678837162-612x612.jpg', 'active', 4, '2020-03-01 17:01:50', 'Biodiversity has become a trademark of Indonesian marine fishery products.'),
('Fishery & Seafood', 123, 99, 'istockphoto-678837162-612x612.jpg', 'active', 4, '2020-03-01 17:01:50', 'Biodiversity has become a trademark of Indonesian marine fishery products.');

-- END TABLE dbo.Products

-- BEGIN TABLE dbo.Roles
CREATE TABLE dbo.Roles (
	RoleID int NOT NULL IDENTITY(1,1),
	RoleName varchar(20) NOT NULL
);
GO

ALTER TABLE dbo.Roles ADD CONSTRAINT PK__Roles__8AFACE3AD86C904E PRIMARY KEY (RoleID);
GO

-- Inserting 2 rows into dbo.Roles
-- Insert batch #1
INSERT INTO dbo.Roles (RoleName) VALUES
('user'),
('admin');

-- END TABLE dbo.Roles

-- BEGIN TABLE dbo.Users
CREATE TABLE dbo.Users (
	Username varchar(50) NOT NULL,
	Password varchar(max) NULL DEFAULT (NULL),
	Fullname varchar(50) NULL DEFAULT (NULL),
	Phone varchar(20) NULL DEFAULT (NULL),
	Address varchar(max) NULL DEFAULT (NULL),
	RoleID int NOT NULL DEFAULT ('1')
);
GO

ALTER TABLE dbo.Users ADD CONSTRAINT PK__Users__536C85E5F65A3575 PRIMARY KEY (Username);
GO

-- Inserting 3 rows into dbo.Users
-- Insert batch #1
INSERT INTO dbo.Users (Username, Password, Fullname, Phone, Address, RoleID) VALUES
('admin', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', 'Do Ngoc Huu', '039-7189696', '381 PVT, Binh Thanh', 2),
('dongochuu95@gmail.com', NULL, NULL, NULL, NULL, 1),
('huudnse130557@fpt.edu.vn', NULL, NULL, NULL, NULL, 1);

-- END TABLE dbo.Users

IF OBJECT_ID('dbo.OrderDetails', 'U') IS NOT NULL AND OBJECT_ID('dbo.Products', 'U') IS NOT NULL
	ALTER TABLE dbo.OrderDetails
	ADD CONSTRAINT FK__OrderDeta__Produ__4316F928
	FOREIGN KEY (ProductID)
	REFERENCES dbo.Products (ProductID);

IF OBJECT_ID('dbo.OrderDetails', 'U') IS NOT NULL AND OBJECT_ID('dbo.Orders', 'U') IS NOT NULL
	ALTER TABLE dbo.OrderDetails
	ADD CONSTRAINT FK__OrderDeta__Order__4222D4EF
	FOREIGN KEY (OrderID)
	REFERENCES dbo.Orders (OrderID);

IF OBJECT_ID('dbo.Orders', 'U') IS NOT NULL AND OBJECT_ID('dbo.Users', 'U') IS NOT NULL
	ALTER TABLE dbo.Orders
	ADD CONSTRAINT FK__Orders__Username__403A8C7D
	FOREIGN KEY (Username)
	REFERENCES dbo.Users (Username);

IF OBJECT_ID('dbo.Products', 'U') IS NOT NULL AND OBJECT_ID('dbo.Categories', 'U') IS NOT NULL
	ALTER TABLE dbo.Products
	ADD CONSTRAINT FK__Products__Catego__3A81B327
	FOREIGN KEY (CategoryID)
	REFERENCES dbo.Categories (CategoryID);

IF OBJECT_ID('dbo.Users', 'U') IS NOT NULL AND OBJECT_ID('dbo.Roles', 'U') IS NOT NULL
	ALTER TABLE dbo.Users
	ADD CONSTRAINT FK__Users__RoleID__3D5E1FD2
	FOREIGN KEY (RoleID)
	REFERENCES dbo.Roles (RoleID);

