package vPakkaus.Controllers;

import java.util.Scanner;

public class jjj {
	
	public static void main(String[] args){
	Scanner sc = new Scanner(System.in);
	
		int base = 0;
		int power = 0;
		
		
		System.out.print("Vakio : ");
		base = sc.nextInt();
		
		System.out.print("Eksponentti : ");
		power = sc.nextInt();
		
		sc.close();
		
		System.out.println("Vastaus : "+base+"^"+power+" = "+powerN(base,power));
	
	}
	
	
	public static int powerN(int base, int n) {
	    int result = 1;
	    for (int i = 0; i < n; i++) {
	        result *= base;
	    }

	    return result;
	}
}
