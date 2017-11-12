package com.bayviewglen.dynamicprogramming;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class quiz {

	public static void main(String[] args) throws FileNotFoundException {
		
		
		int[] fromFile = getQuizExamples();
		
		for(int i = 0; i < fromFile.length; i+=4) {
			int number = fromFile[i];
			int[] nLength = new int[number+1]; //makes a new array n length
		
			System.out.println("Number : " + number);
			
			int divisor1 = fromFile[i+1];  //gets divisors
			int divisor2 = fromFile[i+2];
			int divisor3= fromFile[i+3];
			
		
			
			nLength[2] = 1;
			for(int j= 3; j<nLength.length; j++) {
				int steps = 0;
				if(j%divisor1 ==0) {
					steps = j/divisor1;
					
				}else if(j%divisor2 == 0) {
					steps = j/divisor2;
				
				}else if(j%divisor3 == 0) {
					steps =1 +  j/divisor3;
					
				}
				
				nLength[j] = Math.min(1+ nLength[j-1], 1+nLength[steps]);
			}
			
			System.out.println("Min num steps :" + nLength[number]); 
		
		
			
		}

	}
	
	
	private static int[] getQuizExamples() throws FileNotFoundException {
		int [] answers = new int[28];
		String[]tempAnswers = new String[3];
		
		Scanner input = new Scanner(new File("data/quiz.dat"));
		String temp;
		for(int i = 0; i<answers.length;i++) {
				temp = input.nextLine();
				tempAnswers = temp.split(" ") ;
				
				for(int j = 0; j<tempAnswers.length;j++) {
					answers[i+j] = Integer.parseInt(tempAnswers[j]);
				}
				if(tempAnswers.length>1) {
					i+=2;
				}
				
			}
		return answers;
		}
		
			
		


}
