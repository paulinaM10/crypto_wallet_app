create database FinalProjectTest;

use FinalProjectTest;

drop database FinalProjectTest;

select * from spring_session_attributes;


CREATE TABLE Users (
  userId INT PRIMARY KEY AUTO_INCREMENT,
  fullName VARCHAR(50) NOT NULL,
  email VARCHAR(50) UNIQUE NOT NULL,
  userPassword VARCHAR(30) NOT NULL,
  bankDetails VARCHAR(70) NOT NULL,
  walletId int, 
 FOREIGN KEY (WalletID) REFERENCES Wallet(WalletID));
 
 INSERT INTO Users (fullName, email, userPassword, bankDetails, walletId) VALUES 
  ('John Doe', 'john.doe@example.com', 'password123', '1234-5678-9012', 1111),
  ('Jane Smith', 'jane.smith@example.com', 'password456', '9876-5432-1098', 1112);


 select * from users;
  
drop table users;

CREATE TABLE Wallet (
walletId INT PRIMARY KEY AUTO_INCREMENT,
balance DOUBLE DEFAULT 0);
 ALTER TABLE Wallet AUTO_INCREMENT = 1111;
 ALTER TABLE Wallet ADD COLUMN userId INT;
 
 INSERT INTO Wallet (balance, userId) VALUES
 (100.00, 1),
(50.00, 2);
 select * from wallet;
 
 drop table wallet;


CREATE TABLE Transactions (
  transactionId INT PRIMARY KEY AUTO_INCREMENT,
  walletId INT NOT NULL,
  userId INT NOT NULL,
  amount DOUBLE NOT NULL,
  transactionType ENUM('Add', 'Remove') NOT NULL,
  transactionDate DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (walletId) REFERENCES Wallet(walletId),
  FOREIGN KEY (userId) REFERENCES Users(userId));
 
 ALTER TABLE Transactions  AUTO_INCREMENT = 111;
 ALTER TABLE Transactions;
 
 INSERT INTO Transactions (walletId, userId, amount, transactionType) VALUES 
  (1111, 1, 100.00, 'Add'),
  (1112, 2, 50.00, 'Remove');

 
select * from transactions;

drop table transactions;

 
CREATE TABLE UserCoins (
  userCoinId INT PRIMARY KEY AUTO_INCREMENT,
  quantity INT NOT NULL,
  price DOUBLE NOT NULL,
  datePurchased DATETIME DEFAULT CURRENT_TIMESTAMP,
  userId INT NOT NULL,
  FOREIGN KEY (userId) REFERENCES Users(userId));
  Alter table UserCoins DROP COLUMN PRICE;
  
  
  INSERT INTO UserCoins (quantity, price, userId) VALUES 
  (10, 50.00, 1),
   (5, 70.00, 2);
select *from UserCoins;

drop table UserCoins;


CREATE TABLE Accounts (
  UserName VARCHAR(60) PRIMARY KEY NOT NULL,
  userPassword VARCHAR(60) NOT NULL,
  userId INT NOT NULL,
  FOREIGN KEY (userId) REFERENCES Users(userId));
  
INSERT INTO Accounts (UserName, userPassword, userId) VALUES 
  ('john_doe', 'password123', 1),
  ('jane_smith', 'pass456', 2);
select * from Accounts;
drop table accounts;

