package org.luvx.commons;

public class NumberUtils {

	public static void main(String[] args) {
		// int a =2;
		// int b =7;
		Integer a = 2;
		Integer b = 7;
		
		// 1
		// b = b - a;
		// a = a + b;
		// b = a - b;

		// 2
		// int temp = a;
		// a = b;
		// b = temp;

		// 3
		a = a ^ b;
		b = a ^ b;
		a = b ^ a;
		
		System.out.println(a);
		System.out.println(b);

	}
}
