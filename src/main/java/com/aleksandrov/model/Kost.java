package com.aleksandrov.model;

import java.time.LocalDate;
import java.util.Date;

public class Kost {
	public double amount;
	public String category;
	SpendType spendType;
	public Date date;
	public LocalDate dateOfPurchaseIncome;
	public String comment; 
	
	public Kost(double amount, String category, SpendType spendType){
		this.amount=amount;
		this.category=category;
		this.spendType=spendType;
		this.date=new Date();
	}
	
	public Kost(double amount, String category, SpendType spendType, LocalDate dateOfPurchaseIncome){
		this.amount=amount;
		this.category=category;
		this.spendType=spendType;
		this.dateOfPurchaseIncome=dateOfPurchaseIncome;
	}

	public String getCategory() {
		return category;
	}

	public double getAmount() {
		return amount;
	}
	
	public Date getDate() {
		return date;
	}
	
	public LocalDate getLocalDate() {
	return dateOfPurchaseIncome;
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
}
