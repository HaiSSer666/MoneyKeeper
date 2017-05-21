package com.aleksandrov.model;

public class pseudoMain {

	public static void main(String[] args) {
		Kost k1 = new Kost (20, "***", SpendType.GAIN);
		Kost k2 = new Kost (50, "222", SpendType.GAIN);
		System.out.println(k1.getSpendType());
		System.out.println(k2.getDate());
	}

}

// date in format Mounth/Date/Year
//System.out.printf("%1$s %2$tB %2$td, %2$tY", "Due date:", k1.getDate());