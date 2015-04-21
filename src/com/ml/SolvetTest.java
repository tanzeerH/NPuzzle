package com.ml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class SolvetTest {
	

	public static void main(String[] args) {
		String fileName = args[0];
		int size=0;
		Scanner scanner=null;
		try {
		    scanner=new Scanner(new File(fileName));
			size=scanner.nextInt();
			System.out.println(""+size);
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		int [][] blocks=new int[size][size];
		
		for(int i=0;i<size;i++)
		   for(int j=0;j<size;j++)
			   blocks[i][j]=scanner.nextInt();
		Board initialBoard=new Board(blocks, size);
		if(initialBoard.isSolveable())
		{
			Solver solver=new Solver(initialBoard);
			ArrayList<Board> list=solver.solve();
			for(int i=0;i<list.size();i++)
				list.get(i).printSelf();
			 
			
			System.out.println("Number Of moves: "+ (list.size()-1));
		}
		else
			JOptionPane.showMessageDialog(null,"Unsolvable Puzzle.");
	}
	
}
