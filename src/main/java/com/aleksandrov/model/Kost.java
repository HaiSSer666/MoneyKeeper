package com.aleksandrov.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.aleksandrov.utils.LocalDateTimeFormatter;

public class Kost {
	public double amount;
	public String category;
	SpendType spendType;
	public String creationDate;
	public LocalDate dateOfPurchaseOrIncome;
	public String comment; 

	public Kost(double amount, String category, SpendType spendType, LocalDate dateOfPurchaseOrIncome, String comment){
		this.amount=amount;
		this.category=category;
		this.spendType=spendType;
		this.dateOfPurchaseOrIncome=dateOfPurchaseOrIncome;
		this.creationDate=LocalDateTimeFormatter.formateDate(LocalDateTime.now());
		this.comment=comment;
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

	public LocalDate getDateOfPurchaseOrIncome() {
		return dateOfPurchaseOrIncome;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public String getComment() {
		return comment;
	}
}
