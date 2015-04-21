package com.ml;

import java.util.ArrayList;

public class Board {
	private int size = 0;
	public int[][] blocks;
	private int blankRow;
	private int blankColumn;
	private int inverstions;
	private int moves = 0;
	private Board parent = null;

	public Board(int[][] initBoard, int size) {
		this.size = size;
		blocks = new int[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				blocks[i][j] = initBoard[i][j];
		blankRow = getBlankRow();
		blankColumn = getBlankColumn();
	}

	public int getSize() {
		return size;
	}

	public void setParent(Board p) {
		this.parent = p;
	}

	public Board getParent() {
		return this.parent;
	}

	public int getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}

	public int[][] getBlocks() {
		return blocks;
	}

	public boolean isSolveable() {

		inverstions = getInversionNumbers();
		System.out.println("inversions:" + inverstions);
		if (inverstions % 2 == 1 && size % 2 == 1)
			return false;
		if ((inverstions + blankRow) % 2 == 0 && size % 2 == 0)
			return false;
		return true;

	}

	private int getInversionNumbers() {
		int inversions = 0;
		int totalBlocks = size * size - 1;
		for (int i = 1; i <= totalBlocks; i++)
			for (int j = i + 1; j <= totalBlocks; j++)
				if (IsInverse(i, j))
					inversions += 1;

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
		// System.out.println("x:"+x+"pos:"+xPosition+"y:"+y+"pos:"+yPosition);
		if (xPosition > yPosition)
			return true;
		else
			return false;
	}

	private int getBlankRow() {
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (blocks[i][j] == 0)
					return i;
		return 0;
	}

	private int getBlankColumn() {
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (blocks[i][j] == 0)
					return j;
		return 0;
	}

	public ArrayList<Board> generateNeighbors() {
		ArrayList<Board> listOfNeighbours = new ArrayList<Board>();
		Board temp;
		temp = getTopNeighbor();
		if (temp != null)
			listOfNeighbours.add(temp);
		temp = getBottomNeighbor();
		if (temp != null)
			listOfNeighbours.add(temp);
		temp = getLeftNeighbor();
		if (temp != null)
			listOfNeighbours.add(temp);
		temp = getRightNeighbor();
		if (temp != null)
			listOfNeighbours.add(temp);

		return listOfNeighbours;
	}

	private Board getTopNeighbor() {

		if (blankRow == 0)
			return null;
		int[][] tempBlocks = new int[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				tempBlocks[i][j] = blocks[i][j];
		int x = tempBlocks[blankRow - 1][blankColumn];
		tempBlocks[blankRow][blankColumn] = x;
		tempBlocks[blankRow - 1][blankColumn] = 0;
		// System.out.println("printing top neighbour:");
		// printBlocks(tempBlocks, size);
		return new Board(tempBlocks, size);

	}

	private Board getBottomNeighbor() {

		if (blankRow == size - 1)
			return null;
		int[][] tempBlocks = new int[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				tempBlocks[i][j] = blocks[i][j];
		int x = tempBlocks[blankRow + 1][blankColumn];
		tempBlocks[blankRow][blankColumn] = x;
		tempBlocks[blankRow + 1][blankColumn] = 0;
		// System.out.println("printing bottom neighbour:");
		// printBlocks(tempBlocks, size);
		return new Board(tempBlocks, size);

	}

	private Board getLeftNeighbor() {

		if (blankColumn == 0)
			return null;
		int[][] tempBlocks = new int[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				tempBlocks[i][j] = blocks[i][j];
		int x = tempBlocks[blankRow][blankColumn - 1];
		tempBlocks[blankRow][blankColumn] = x;
		tempBlocks[blankRow][blankColumn - 1] = 0;
		// System.out.println("printing left neighbour:");
		// printBlocks(tempBlocks, size);
		return new Board(tempBlocks, size);

	}

	private Board getRightNeighbor() {

		if (blankColumn == size - 1)
			return null;
		int[][] tempBlocks = new int[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				tempBlocks[i][j] = blocks[i][j];
		int x = tempBlocks[blankRow][blankColumn + 1];
		tempBlocks[blankRow][blankColumn] = x;
		tempBlocks[blankRow][blankColumn + 1] = 0;
		// System.out.println("printing right neighbour:");
		// printBlocks(tempBlocks, size);
		return new Board(tempBlocks, size);

	}

	public int getHamming() {
		// printBlocks(Solver.goalBlocks, size);
		int hamming = 0;
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (blocks[i][j] != 0)
					if (blocks[i][j] != Solver.goalBlocks[i][j])
						hamming += 1;
		return hamming + moves;
	}

	public int getManhattan() {
		int manhattan = 0;
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (blocks[i][j] != 0) {
					manhattan += getDistanceInRowPosition(blocks[i][j], i)
							+ getDistanceInColumnPosition(blocks[i][j], j);
				}
		return manhattan + moves;
	}

	private int getDistanceInRowPosition(int member, int row) {
		int actualRow = 0;
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (Solver.goalBlocks[i][j] == member)
					actualRow = i;
		int diff = actualRow - row;
		if (diff < 0)
			diff = -1 * diff;

		return diff;

	}

	private int getDistanceInColumnPosition(int member, int column) {
		int actualColumn = 0;
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (Solver.goalBlocks[i][j] == member)
					actualColumn = j;
		int diff = actualColumn - column;
		if (diff < 0)
			diff = -1 * diff;

		return diff;

	}

	public boolean isGoalState() {
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (Solver.goalBlocks[i][j] != blocks[i][j])
					return false;
		return true;
	}

	public boolean isSameWithParent(Board neighborBoard) {
		if (parent == null)
			return false;
		int[][] neighboursBlocks = neighborBoard.getBlocks();
		int[][] parentBlocks = parent.getBlocks();
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (neighboursBlocks[i][j] != parentBlocks[i][j])
					return false;
		return true;

	}

	public int getLinearConflict() {
		int conflicts = 0;
		int totalBlocks = size * size - 1;
		for (int i = 1; i <= totalBlocks; i++)
			for (int j = i + 1; j <= totalBlocks; j++) {
				if (isConflict(i, j))
					conflicts += 1;
				if (isConflict(j, i))
					conflicts += 1;
			}
		System.out.println("Conflicts:" + conflicts);
		return 2 * conflicts + getManhattan();
	}

	private boolean isConflict(int x, int y) {
		int xRow = 0;
		int yRow = 0;
		int xCol = 0;
		int yCol = 0;
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				if (blocks[i][j] == x) {
					xRow = i;
					xCol = j;
				} else if (blocks[i][j] == y) {
					yRow = i;
					yCol = j;
				}
			}
		int gRowX = 0;
		int gRowY = 0;
		int gColX = 0;
		int gColY = 0;
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				if (Solver.goalBlocks[i][j] == x) {
					gRowX = i;
					gColX = j;
				} else if (Solver.goalBlocks[i][j] == y) {
					gRowY = i;
					gColY = j;
				}
			}
		if (xRow == yRow && gRowX == gRowY) {
			//System.out.println(x + " " + y);
			if (xCol > yCol && gColX < gColY)
				return true;
		}
		return false;

	}

	private void printBlocks(int[][] initBoard, int size) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++)
				System.out.print("" + initBoard[i][j]);
			System.out.println("");
		}
	}

	public void printSelf() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++)
				System.out.print("" + blocks[i][j]);
			System.out.println("");
		}
		System.out.println("");
	}
}
