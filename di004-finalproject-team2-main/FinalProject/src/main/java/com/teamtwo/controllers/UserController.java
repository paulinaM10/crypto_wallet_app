package com.teamtwo.controllers;

import com.teamtwo.dto.entity.Transactions;
import com.teamtwo.dto.entity.User;
import com.teamtwo.dto.entity.Wallet;
import com.teamtwo.model.service.TransactionsService;
import com.teamtwo.model.service.UserService;
import com.teamtwo.model.service.WalletService;

import jakarta.servlet.http.HttpSession;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;
    
    @Autowired
    WalletService walletService;
    
    @Autowired
    TransactionsService transactionService;
       
    @PostMapping(path = "/signUp", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        if (createdUser != null) {
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    //need to check if this is needed
    @GetMapping("/walletId")
    public ResponseEntity<Map<String, Object>> getUserWalletId(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            Wallet userWallet = userService.getUserWallet(userId);
            if (userWallet != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("walletId", userWallet.getWalletId());
                response.put("balance", userWallet.getBalance());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    
    @GetMapping("/walletInfo")
    public ResponseEntity<Wallet> getWalletInfo(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId != null) {
            User user = userService.getUserById(userId);

            if (user != null) {
                Wallet wallet = walletService.createUserWallet(user);

                if (wallet != null) {
                    Wallet response = new Wallet(wallet.getWalletId(), wallet.getBalance(), wallet.getUserId());

                    return ResponseEntity.ok(response);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @GetMapping("/transactionInfo")
    public ResponseEntity<List<Transactions>> getTransactionInfo(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId != null) {
            List<Transactions> transactions = transactionService.getTransactionsByUserId(userId);
            if (!transactions.isEmpty()) {
                return ResponseEntity.ok(transactions);
            }
        }
        return ResponseEntity.ok(Collections.emptyList());
    }


    @PutMapping(path="/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody User updatedData) {
        updatedData.setUserId(id);
        User updatedUser = userService.updateUser(updatedData);
        return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
    }

    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable("id") int userId) {
        User user = userService.getUserById(userId);
        return new ResponseEntity<User>(user, HttpStatus.FOUND);
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users != null && !users.isEmpty()) {
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
        }
    }

    
    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> loginUser(@RequestBody User user, HttpSession session) {
        User foundUser = userService.getUserByEmail(user.getEmail());
        if (foundUser != null) {
            if (BCrypt.checkpw(user.getUserPassword(), foundUser.getUserPassword())) {
                // Passwords match, store user id in session and return OK response
                session.setAttribute("userId", foundUser.getUserId());
                return new ResponseEntity<>("Login successful", HttpStatus.OK);
            } else {
                // Passwords don't match, return UNAUTHORIZED response
                return new ResponseEntity<>("Incorrect password", HttpStatus.UNAUTHORIZED);
            }
        } else {
            // User not found, return NOT_FOUND response
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpSession session) {
        // Invalidate the session to clear all data
        session.invalidate();
        return new ResponseEntity<>("You have been successfully logged out", HttpStatus.OK);
    }

    
    @PutMapping("/update")
    public ResponseEntity<User> updateUserDetails(HttpSession session, @RequestBody User updatedUser) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            updatedUser.setUserId(userId);
            User updatedUserResult = userService.updateUser(userId, updatedUser);
            if (updatedUserResult != null) {
                return ResponseEntity.ok(updatedUserResult);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUserAccount(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            boolean wasDeleted = userService.deleteUser(userId);
            if (wasDeleted) {
                session.invalidate();
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    
    
    
}