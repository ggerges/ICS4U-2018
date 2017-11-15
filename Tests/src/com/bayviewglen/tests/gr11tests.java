package com.bayviewglen.tests;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class gr11tests {

	//Q1 Jan (1) - March (3)
	//Q2 Apr (4) - Jun (6)
	//Q3 July (7) - Sept (9)
	// Q4 Oct (10) - Dec (12)
	//check if month and year is in the Q4
	
	
	public static void main(String[] args) {

		int x = 7;
		int y = 2017;
		boolean test = lastQuarter(x,y);
		System.out.println(test);
		
	}
	
	public static boolean lastQuarter(int month, int year) {
		
		LocalDateTime timePoint = LocalDateTime.now(); //todays date find this quarter then
														//check if the quarter before is what the month and year is
	
		int month1 = timePoint.getMonthValue();
		int year1 = timePoint.getYear();
		
		
		if(month1 >= 1 && month1 <= 3) { //present day is Q1, check if month and year is Q before
			
			if(year1 == year-1 && (month >= 10 && month <= 12)) {//year down
				
				return true;

			}else {
				return false;
			}
			
		}else if(month1 >= 4 && month1 <= 6) {//Q2
			
			if(year1 == year && (month >=1 && month <=3)) {//Q1 && same year
				 
					return true;
				}else {
					return false;
				}
		
			
		}else if(month1 >= 7 && month1 <= 9) {//Q3
			
			if(year1 == year && (month >=4 && month <=6)) {//Q2 && same year
				 
				return true;
			}else {
				return false;
			}
	
		}else {//Q4
			
			if(year1 == year && (month >=7 && month <=9)) {//Q3 && same year
				 
				return true;
			}else {
				return false;
			}
		}
		
	
		
		
	}
	
}
