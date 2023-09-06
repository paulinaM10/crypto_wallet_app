create database FinalProject;

use FinalProject;


select * from users;
select * from spring_session_attributes;


CREATE TABLE Users (
  userId INT PRIMARY KEY AUTO_INCREMENT,
  fullName VARCHAR(50) NOT NULL,
  email VARCHAR(50) UNIQUE NOT NULL,
  userPassword VARCHAR(30) NOT NULL,
  bankDetails VARCHAR(70) NOT NULL,
  walletId int, 
 FOREIGN KEY (WalletID) REFERENCES Wallet(WalletID));
  
select * from users;
  

CREATE TABLE Wallet (
  walletId INT PRIMARY KEY AUTO_INCREMENT,
  balance DOUBLE DEFAULT 0);
 ALTER TABLE Wallet AUTO_INCREMENT = 1111;
 ALTER TABLE Wallet ADD COLUMN userId INT;

select * from wallet;

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
 ALTER TABLE Transactions
MODIFY COLUMN transactionType ENUM('ADD', 'REMOVE', 'BUY', 'SELL');
 
select * from transactions;

 
CREATE TABLE UserCoins (
  userCoinId INT PRIMARY KEY AUTO_INCREMENT,
  quantity INT NOT NULL,
  price DOUBLE NOT NULL,
  datePurchased DATETIME DEFAULT CURRENT_TIMESTAMP,
  userId INT NOT NULL,
  FOREIGN KEY (userId) REFERENCES Users(userId));

CREATE TABLE Accounts (
  UserName VARCHAR(60) PRIMARY KEY NOT NULL,
  userPassword VARCHAR(60) NOT NULL,
  userId INT NOT NULL,
  FOREIGN KEY (userId) REFERENCES Users(userId));
  
       
select * from Accounts;
drop table accounts;

drop table UserCoins;
