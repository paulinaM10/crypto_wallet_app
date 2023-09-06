package com.teamtwo.controller;

import com.teamtwo.dto.entity.UserCoins;
import com.teamtwo.model.service.UserCoinsService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api")
public class UserCoinsController {
    @Autowired
    private UserCoinsService userCoinsService;

    // Existing code...

    @GetMapping("/usercoins/{userId}")
    public ResponseEntity<List<UserCoins>> getUserCoins(@PathVariable int userId) {
        List<UserCoins> userCoins = userCoinsService.getUserCoins(userId);
        return ResponseEntity.ok(userCoins);
    }
}