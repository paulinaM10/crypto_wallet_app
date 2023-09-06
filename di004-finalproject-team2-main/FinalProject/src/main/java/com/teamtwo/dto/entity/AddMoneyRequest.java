package com.teamtwo.dto.entity;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddMoneyRequest {
    private int userId;
    private int walletId;
    
 
    @Min(value = 1, message = "Amount must be greater than 0")
    private double amount;


}
