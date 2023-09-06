package com.teamtwo.dto.entity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyCoinRequest {

    private Integer userId;
    private Integer walletId;
    private String coinSymbol;
    private double amount;
    
}