package com.aleksandrov.model;

import java.time.LocalDate;
import java.util.Date;

public class Kost {
	public double amount;
	public String category;
	SpendType spendType;
	public Date creationDate;
	public LocalDate dateOfPurchaseOrIncome;
	public String comment; 
	
	public Kost(double amount, String category, SpendType spendType, LocalDate dateOfPurchaseOrIncome){
		this.amount=amount;
		this.category=category;
		this.spendType=spendType;
		this.dateOfPurchaseOrIncome=dateOfPurchaseOrIncome;
		this.creationDate=new Date();
	}

	public String getCategory() {
		return category;
	}

	public double getAmount() {
		return amount;
	}
	
	public Date getCreationDate() {
		return creationDate;
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
}
