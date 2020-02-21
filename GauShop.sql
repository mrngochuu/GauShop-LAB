-- Database export via SQLPro (https://www.sqlprostudio.com/allapps.html)
-- Exported by ngochuu at 21-02-2020 20:14.
-- WARNING: This file may contain descructive statements such as DROPs.
-- Please ensure that you are running the script at the proper location.

--CREATE DB
CREATE DATABASE GauShop;
USER GauShop;

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
INSERT INTO dbo.Categories (CategoryID, CategoryName) VALUES
(1, 'Vegetables'),
(2, 'Fruits'),
(3, 'Meat and Poultry'),
(4, 'Fish and Seafood'),
(5, 'Fast foods'),
(6, 'Coffee'),
(7, 'Beer cocktail'),
(8, 'Soft drinks');

-- END TABLE dbo.Categories

-- BEGIN TABLE dbo.OrderDetails
CREATE TABLE dbo.OrderDetails (
	Quantity int NOT NULL DEFAULT ('1'),
	Price int NOT NULL,
	OrderID int NOT NULL,
	ProductID int NOT NULL
);
GO

-- Table dbo.OrderDetails contains no data. No inserts have been genrated.
-- Inserting 0 rows into dbo.OrderDetails


-- END TABLE dbo.OrderDetails

-- BEGIN TABLE dbo.Orders
CREATE TABLE dbo.Orders (
	OrderID int NOT NULL IDENTITY(1,1),
	isCheckout bit NOT NULL DEFAULT ('0'),
	OrderPhone varchar(20) NOT NULL,
	OrderAddress varchar(max) NOT NULL,
	PaymentType varchar(30) NOT NULL DEFAULT ('cast'),
	Username varchar(20) NOT NULL
);
GO

ALTER TABLE dbo.Orders ADD CONSTRAINT PK__Orders__C3905BAF8B207DF5 PRIMARY KEY (OrderID);
GO

-- Table dbo.Orders contains no data. No inserts have been genrated.
-- Inserting 0 rows into dbo.Orders


-- END TABLE dbo.Orders

-- BEGIN TABLE dbo.Products
CREATE TABLE dbo.Products (
	ProductID int NOT NULL IDENTITY(1,1),
	ProductName varchar(50) NOT NULL,
	Price int NOT NULL,
	Quantity int NOT NULL DEFAULT ('1'),
	ImgUrl varchar(max) NOT NULL,
	Status varchar(20) NOT NULL DEFAULT ('active'),
	CategoryID int NOT NULL
);
GO

ALTER TABLE dbo.Products ADD CONSTRAINT PK__Products__B40CC6EDEB37341E PRIMARY KEY (ProductID);
GO

-- Table dbo.Products contains no data. No inserts have been genrated.
-- Inserting 0 rows into dbo.Products


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
INSERT INTO dbo.Roles (RoleID, RoleName) VALUES
(1, 'user'),
(2, 'admin');

-- END TABLE dbo.Roles

-- BEGIN TABLE dbo.Users
CREATE TABLE dbo.Users (
	Username varchar(20) NOT NULL,
	Password varchar(max) NOT NULL,
	Fullname varchar(50) NOT NULL,
	Phone varchar(20) NOT NULL,
	Address varchar(max) NOT NULL,
	RoleID int NOT NULL DEFAULT ('1')
);
GO

ALTER TABLE dbo.Users ADD CONSTRAINT PK__Users__536C85E5600E591A PRIMARY KEY (Username);
GO

-- Table dbo.Users contains no data. No inserts have been genrated.
-- Inserting 0 rows into dbo.Users


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

