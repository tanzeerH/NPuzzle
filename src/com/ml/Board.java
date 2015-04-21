package com.ml;

public class Board {
   private int size=0;
   public int [][] blocks;
   public Board(int [][] initBoard,int size)
   {
	   this.size=size;
	   blocks=new int[size][size];
	   for(int i=0;i<size;i++)
		   for(int j=0;j<size;j++)
			   blocks[i][j]=initBoard[i][j];
   }
   public boolean isSolveable()
   {
	  
	    int inverstions=getInversionNumbers();
	    int blankRow=getBlankRow();
	    System.out.println("inversions:"+inverstions);
	    if(inverstions%2==1 && size%2==1)
	    	return false;
	    if((inverstions+blankRow)%2==0 && size%2==0)
	    	return false;
	    return true;
	    
	   
   }
   private int getInversionNumbers()
   {
	   int inversions=0;
	   int totalBlocks=size*size-1;
	   for(int i=1;i<=totalBlocks;i++)
		   for(int j=i+1;j<=totalBlocks;j++)
			   if(IsInverse(i,j))
				   inversions+=1;
	   
	  return inversions;
			   
   }

	private boolean IsInverse(int x, int y) {
		int xPosition = 0;
		int yPosition = 0;
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				if (blocks[i][j] == x)
					xPosition = i * size + j;
				else if (blocks[i][j] == y)
					yPosition = i * size + j;
			}
		//System.out.println("x:"+x+"pos:"+xPosition+"y:"+y+"pos:"+yPosition);
		if(xPosition>yPosition)
			return true;
		else
			return false;
	}
	private int getBlankRow()
	{
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (blocks[i][j] == 0)
					return i;
	  return 0;
	}
}
