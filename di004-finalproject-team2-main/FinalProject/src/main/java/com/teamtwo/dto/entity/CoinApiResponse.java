package com.teamtwo.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinApiResponse {
    private String symbol;
    private String bidPrice;
    private String bidQty;
    private String askPrice;
    private String askQty; 
}
