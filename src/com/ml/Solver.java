package com.ml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Solver {

	private Board board;
	public static int FLAG_HAMMING = 1;
	public static int FLAG_MANHATTAN = 2;
	public static int FLAG_LINEAR_CONFLICT = 3;
	public static int HEURISTIC = FLAG_LINEAR_CONFLICT;
	public static int[][] goalBlocks;
	private ArrayList<Board> boardList = new ArrayList<Board>();
	private Comparator<Board> multiPurposeComparator = new Comparator<Board>() {

		@Override
		public int compare(Board board1, Board board2) {
			if (HEURISTIC == FLAG_HAMMING) {
				if (board1.getHamming() < board2.getHamming())
					return -1;
				else
					return 1;
			}if (HEURISTIC == FLAG_LINEAR_CONFLICT) {
				if (board1.getLinearConflict() < board2.getLinearConflict())
					return -1;
				else
					return 1;
			} else {
				if (board1.getManhattan() < board2.getManhattan())
					return -1;
				else
					return 1;
			}

		}
	};
	private PriorityQueue<Board> queue = new PriorityQueue<Board>(2, multiPurposeComparator);

	public Solver(Board b) {
		this.board = b;
		
		generateGoalState();
		queue.add(board);
		// board.generateNeighbors();
		System.out.println("Hamming" + board.getHamming());
		System.out.println("MANHATTAN: " + board.getManhattan());
		//b.getLinearConflict();
	}

	public ArrayList<Board> solve() {
		Board topBoard = queue.poll();
		while (!topBoard.isGoalState()) {
			ArrayList<Board> neighbors = topBoard.generateNeighbors();
			int listSize = neighbors.size();
			for (int i = 0; i < listSize; i++) {
				Board tempBoard = neighbors.get(i);
				if (!topBoard.isSameWithParent(tempBoard)) {
					tempBoard.setParent(topBoard);
					tempBoard.setMoves(topBoard.getMoves() + 1);
					queue.offer(tempBoard);
				}
			}
			topBoard = queue.poll();
			// topBoard.printSelf();
		}
		topBoard.printSelf();
		System.out.println("moves:" + topBoard.getMoves());
		boardList.add(topBoard);
		while (topBoard.getParent() != null) {
			topBoard = topBoard.getParent();
			boardList.add(topBoard);
		}
		Collections.reverse(boardList);
		return boardList;

	}

	private void generateGoalState() {
		int size = board.getSize();
		goalBlocks = new int[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				goalBlocks[i][j] = i * size + j + 1;
		goalBlocks[size - 1][size - 1] = 0;
	}

}
