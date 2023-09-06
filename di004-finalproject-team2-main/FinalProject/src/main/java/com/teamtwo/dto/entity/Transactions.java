package com.teamtwo.dto.entity;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transactions {

	@Id
	private int transactionId;
	private int walletId;
	private int userId;
	private double amount;

	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	private LocalDateTime transactionDate;
	
}
