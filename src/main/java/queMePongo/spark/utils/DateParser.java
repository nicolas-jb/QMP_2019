package queMePongo.spark.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateParser {
	
	public static LocalDate parseDate(String date) {
		return parseDate(date,"-");
	}
	
	public static LocalDate parseDate(String date, String separator){
		String[] splitted = date.split(separator);
		return parseDate(splitted[0], splitted[1], splitted[2]);
	}
	
	public static LocalDate parseDate(String year, String month, String day){
		return LocalDate.parse(year + "-" + add0(month) + "-" + add0(day), DateTimeFormatter.ISO_LOCAL_DATE);
	}
	private static String add0(String string){
		return string.length() > 1 ? string : "0" + string;
	}
	
	public static String dateFormat(){
		return "%d-%d-%d";
	}
	
	public static String parseToString(LocalDate date){
		return date.toString();
	}
}
