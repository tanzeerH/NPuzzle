package com.ml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SolvetTest {
	

	public static void main(String[] args) {
		String fileName = args[0];
		try {
			Scanner scanner=new Scanner(new File(fileName));
			int size=scanner.nextInt();
			System.out.println(""+size);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
