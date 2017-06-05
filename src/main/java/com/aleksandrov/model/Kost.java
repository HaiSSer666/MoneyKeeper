package com.aleksandrov.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Kost {
	public double amount;
	public String category;
	SpendType spendType;
	public LocalDateTime testCreationDate;
	public LocalDate dateOfPurchaseOrIncome;
	public String comment; 
	
	public Kost(double amount, String category, SpendType spendType, LocalDate dateOfPurchaseOrIncome){
		this.amount=amount;
		this.category=category;
		this.spendType=spendType;
		this.dateOfPurchaseOrIncome=dateOfPurchaseOrIncome;
		this.testCreationDate=LocalDateTime.now();
	}

	public String getCategory() {
		return category;
	}

	public double getAmount() {
		return amount;
	}
	
	public SpendType getSpendType() {
		return spendType;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment=comment;
	}

	public LocalDate getDateOfPurchaseOrIncome() {
		return dateOfPurchaseOrIncome;
	}

	public LocalDateTime getTestCreationDate() {
		return testCreationDate;
	}
}
