package com.crazydiamond;

import java.util.Random;
import java.util.Scanner;

public class PuissanceQuatre {
	private static final int ROWS = 6;
	private static final int COLS = 7;
	private static final char EMPTY = ' ';
	private static final char PLAYER1 = 'O';
	private static final char PLAYER2 = 'X';

	/**
	 * Displays the current state of the game board.
	 *
	 * @param board - the game board to display
	 */
	public static void displayBoard(char[][] board) {
		System.out.println();
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				System.out.printf("| %c ", board[i][j]);
			}
			System.out.println("|");
		}
		System.out.println();
	}

	/**
	 * Initialises the game board with empty spaces.
	 *
	 * @param board - the game board to initialise
	 */
	public static void initializeBoard(char[][] board) {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				board[i][j] = EMPTY;
			}
		}
	}

	/**
	 * Checks if a move is valid.
	 *
	 * @param col   - the column to check
	 * @param board - the game board to check
	 * @return true if the move is valid, false otherwise
	 */
	public static boolean isValidMove(int col, char[][] board) {
		return board[0][col] == EMPTY;
	}

	/**
	 * Makes a move on the game board.
	 *
	 * @param col    - the column to make the move in
	 * @param player - the player making the move
	 * @param board  - the game board to make the move on
	 */
	public static void makeMove(int col, char player, char[][] board) {
		for (int i = ROWS - 1; i >= 0; i--) {
			if (board[i][col] == EMPTY) {
				board[i][col] = player;
				break;
			}
		}
	}

	/**
	 * Checks if a player has won the game.
	 *
	 * @param player - the player to check for a win
	 * @param board  - the game board to check
	 * @return true if the player has won, false otherwise
	 */
	public static boolean checkWinner(char player, char[][] board) {
		// Horizontal check
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS - 3; j++) {
				if (board[i][j] == player && board[i][j + 1] == player && board[i][j + 2] == player
						&& board[i][j + 3] == player) {
					return true;
				}
			}
		}
		// Vertical check
		for (int i = 0; i < ROWS - 3; i++) {
			for (int j = 0; j < COLS; j++) {
				if (board[i][j] == player && board[i + 1][j] == player && board[i + 2][j] == player
						&& board[i + 3][j] == player) {
					return true;
				}
			}
		}
		// Diagonal check (top left to bottom right)
		for (int i = 0; i < ROWS - 3; i++) {
			for (int j = 0; j < COLS - 3; j++) {
				if (board[i][j] == player && board[i + 1][j + 1] == player && board[i + 2][j + 2] == player
						&& board[i + 3][j + 3] == player) {
					return true;
				}
			}
		}
		// Diagonal check (top right to bottom left)
		for (int i = 0; i < ROWS - 3; i++) {
			for (int j = 3; j < COLS; j++) {
				if (board[i][j] == player && board[i + 1][j - 1] == player && board[i + 2][j - 2] == player
						&& board[i + 3][j - 3] == player) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if the game board is full.
	 *
	 * @param board - the game board to check
	 * @return true if the game board is full, false otherwise
	 */
	public static boolean isBoardFull(char[][] board) {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (board[i][j] == EMPTY) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Gets the human player's move.
	 *
	 * @param board - the game board to make the move on
	 * @return the column the player chose to make their move in
	 */
	public static int getHumanMove(char[][] board) {
		Scanner scanner = new Scanner(System.in);
		int col;
		System.out.print("Choisissez une colonne pour placer votre pion (1-7): ");
		col = scanner.nextInt();
		while (col < 1 || col > 7 || !isValidMove(col - 1, board)) {
			System.out.print("Choisissez une colonne valide (1-7): ");
			col = scanner.nextInt();
			scanner.close();
		}
		return col - 1;
	}

	/**
	 * Gets the computer player's move.
	 *
	 * @param board - the game board to make the move on
	 * @return the column the computer chose to make their move in
	 */
	public static int getComputerMove(char[][] board) {
		// TODO: implement the computer's move algorithm to calculate the best move
		// For now, the computer just chooses a random column
		Random random = new Random();
		int col = random.nextInt(COLS);
		while (!isValidMove(col, board)) {
			col = random.nextInt(COLS);
		}
		System.out.printf("L'ordinateur choisit la colonne %d\n", col + 1);
		return col;
	}

	public static void main(String[] args) {
		char[][] board = new char[ROWS][COLS];
		int currentPlayer = 1;
		boolean winner = false;

		initializeBoard(board);

		while (!winner && !isBoardFull(board)) {
			displayBoard(board);
			if (currentPlayer == 1) {
				int col = getHumanMove(board);
				makeMove(col, PLAYER1, board);
			} else {
				int col = getComputerMove(board);
				makeMove(col, PLAYER2, board);
			}
			winner = checkWinner(currentPlayer == 1 ? PLAYER1 : PLAYER2, board);
			currentPlayer = currentPlayer == 1 ? 2 : 1;
		}

		displayBoard(board);
		if (winner) {
			System.out.printf("Le joueur %c a gagne !\n", currentPlayer == 1 ? PLAYER2 : PLAYER1);
		} else {
			System.out.println("Match nul.");
		}
	}
}