package com.aleksandrov.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatter {

	public static String formateDate (LocalDateTime unformatedDate){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = unformatedDate.format(formatter);
        return formatDateTime;
	}

}
