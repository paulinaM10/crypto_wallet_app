package com.teamtwo.controller;

import com.teamtwo.dto.entity.AddMoneyRequest;
import com.teamtwo.dto.entity.BuyCoinRequest;
import com.teamtwo.dto.entity.RemoveMoneyRequest;
import com.teamtwo.dto.entity.Wallet;
import com.teamtwo.exception.InsufficientBalanceException;
import com.teamtwo.model.service.WalletService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
public class WalletController {

    @Autowired
    private WalletService walletService;
    


    @GetMapping("/wallet/{walletId}")
    public ResponseEntity<Wallet> getWalletById(@PathVariable int walletId) {
        try {
            // Fetch the user's wallet information by walletId
            Wallet wallet = walletService.getWalletByWalletId(walletId);
            if (wallet != null) {
                return ResponseEntity.ok(wallet);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @PostMapping("/wallet/addmoney")
    public ResponseEntity<Wallet> addMoney(@Valid @RequestBody AddMoneyRequest request, BindingResult bindingResult) {
    	if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String fieldName = fieldError.getField();
                String errorMessageForField = fieldError.getDefaultMessage();

                if ("amount".equals(fieldName) && "Amount must be greater than 0".equals(errorMessageForField)) {
                    errorMessage.append("Please enter a valid positive amount.").append("; ");
                } else if ("amount".equals(fieldName) && "Please enter a valid number".equals(errorMessageForField)) {
                    errorMessage.append("Please enter a valid number for the amount.").append("; ");
                } else {
                    errorMessage.append(fieldName).append(": ").append(errorMessageForField).append("; ");
                }
            }
            return ResponseEntity.badRequest().body(null);
        }
    
    	try {
            Wallet wallet = walletService.addMoney(request.getUserId(), request.getWalletId(), request.getAmount());

            return ResponseEntity.ok(wallet);
        } catch (InsufficientBalanceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    

    @PostMapping("/wallet/removemoney")
    public ResponseEntity<Wallet> removeMoney(@Valid @RequestBody RemoveMoneyRequest request, BindingResult bindingResult) {
    	if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String fieldName = fieldError.getField();
                String errorMessageForField = fieldError.getDefaultMessage();

                if ("amount".equals(fieldName) && "Amount must be greater than 0".equals(errorMessageForField)) {
                    errorMessage.append("Please enter a valid positive amount.").append("; ");
                } else if ("amount".equals(fieldName) && "Please enter a valid number".equals(errorMessageForField)) {
                    errorMessage.append("Please enter a valid number for the amount.").append("; ");
                } else {
                    errorMessage.append(fieldName).append(": ").append(errorMessageForField).append("; ");
                }
            }
            return ResponseEntity.badRequest().body(null);
    	}
    	
    	try {
            Wallet wallet = walletService.removeMoney(request.getUserId(), request.getWalletId(), request.getAmount());
            return ResponseEntity.ok(wallet);
        } catch (InsufficientBalanceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    
    @PostMapping("wallet/{walletId}/transaction")
    public ResponseEntity<String> processTransaction(
            @PathVariable int walletId,
            @RequestParam double amount
    ) {
        try {
            Wallet wallet = walletService.removeMoney(walletId, walletId, amount);
            return ResponseEntity.ok("Transaction successful. New balance: " + wallet.getBalance());
        } catch (InsufficientBalanceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    
    @PostMapping("/wallet/buycoin")
    public ResponseEntity<Wallet> buyCoin(@RequestBody BuyCoinRequest request) {
        try {
            Wallet wallet = walletService.buyCoin(request);

            return ResponseEntity.ok(wallet);
        } catch (InsufficientBalanceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    
    @PostMapping("/wallet/sellcoin")
    public ResponseEntity<Wallet> sellCoin(@RequestBody BuyCoinRequest request) {
        try {
            Wallet wallet = walletService.sellCoin(request);
            return ResponseEntity.ok(wallet);
        } catch (InsufficientBalanceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    
}


  
    