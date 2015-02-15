import java.util.Scanner;

public class Othello {
	private final int LENGTH = 8; 
	private int[][] board = new int[LENGTH][LENGTH]; // 0: empty, 1: black, 2: white
	private int player = 1;
	
	public Othello() {
		for (int row = 0; row < LENGTH; row++) {
			for (int col = 0; col < LENGTH; col++) {
				if ((row == 3 && col == 3) || (row == 4 && col == 4)) {
					board[row][col] = 1;
				} else if ((row == 3 && col == 4) || (row == 4 && col == 3)) {
					board[row][col] = 2;
				} else {
					board[row][col] = 0;
				}
			}
		}
	}
	
	public void printBoard() {
		System.out.println("  0 1 2 3 4 5 6 7");
		for (int row = 0; row < LENGTH; row++) {
			System.out.print(row + "|");
			for (int col = 0; col < LENGTH; col++) {
				switch(board[row][col]) {
				case 1:
					System.out.print("B");
					break;
				case 2:
					System.out.print("W");
					break;
				default:
					System.out.print(" ");
					break;
				}
				System.out.print("|");
			}
			System.out.println();
		}
	}
	
	public boolean isGameOver() {
		return false;
	}
	
	public void enterStone() {		
		while (true) {
			String playerColor = player == 1 ? "Black" : "White";
			System.out.println(playerColor + " turn.\nEnter the stone location(row col): ");
			
			Scanner input = new Scanner(System.in);
			int row = input.nextInt();
			int col = input.nextInt();

			if (isPlaceable(row, col) && flip(row, col)) {
				setStone(row, col);
				printBoard();
				player = player == 1 ? 2 : 1;
				break;				
			} else {
				System.out.println("You cannot put the stone there.");				
			}
		}
	}
	
	private boolean isPlaceable(int row, int col) {
		if (row >= LENGTH || col >= LENGTH) {
			return false;
		}
		if (row < 0 || col < 0) {
			return false;
		}
		if (board[row][col] != 0) {
			return false;
		}
		return true;
	}
	
	private boolean isFlippable(int row, int col) {
		int playerColor = player;
		int opponentColor = player == 1 ? 2 : 1;
		boolean isHorizontallyFlippable = false;
		boolean isVerticallyFlippable = false;
		boolean isDiagonallyFlippable1 = false;
		boolean isDiagonallyFlippable2 = false;
		
		// Horizaontal
		int leftCol = col - 1;
		int rightCol = col + 1;
		if (leftCol >= 0 && board[row][leftCol] == opponentColor) {
			for (int c = leftCol; c >= 0; c--) {
				if (board[row][c] == playerColor) {
					isHorizontallyFlippable = true;
				}
			}
		} else if (rightCol < LENGTH && board[row][rightCol] == opponentColor) {
			for (int c = rightCol; c < LENGTH; c++) {
				if (board[row][c] == playerColor) {
					isHorizontallyFlippable = true;
				}
			}
		}
		
		// Vertical
		int aboveRow = row - 1;
		int belowRow = row + 1;
		if (aboveRow >= 0 && board[aboveRow][col] == opponentColor) {
			for (int r = aboveRow; r >= 0; r--) {
				if (board[r][col] == playerColor) {
					isVerticallyFlippable = true;
				}
			}
		} else if (belowRow < LENGTH && board[belowRow][col] == opponentColor) {
			for (int r = belowRow; r < LENGTH; r++) {
				if (board[r][col] == playerColor) {
					isVerticallyFlippable = true;
				}
			}
		}
		
		// Diagonal1 (from the stone to left top and from the stone to right top)
		int r = row - 1;
		int c = col - 1;
		if ((r >= 0 && c >= 0) && board[r][c] == opponentColor) {
			while (r >= 0 && c >= 0) {
				if (board[r][c] == playerColor) {
					isDiagonallyFlippable1 = true;
				}
				r--;
				c--;
			}
		}
		r = row - 1;
		c = col + 1;
		if ((r >= 0 && c < LENGTH) && board[r][c] == opponentColor) {
			while (r >= 0 && c < LENGTH) {
				if (board[r][c] == playerColor) {
					isDiagonallyFlippable1 = true;
				}
				r--;
				c++;
			}
		}
		
		// Diagonal2 (from the stone to left bottom and from the stone to right bottom)
		r = row + 1;
		c = col - 1;
		if ((r < LENGTH && c >= 0) && board[r][c] == opponentColor) {
			while (r < LENGTH && c >= 0) {
				if (board[r][c] == playerColor) {
					isDiagonallyFlippable2 = true;
				}
				r++;
				c--;
			}
		}
		r = row + 1;
		c = col + 1;
		if ((r < LENGTH && c < LENGTH) && board[r][c] == opponentColor) {
			while (r < LENGTH && c < LENGTH) {
				if (board[r][c] == playerColor) {
					isDiagonallyFlippable2 = true;
				}
				r++;
				c++;
			}
		}
		
		return (isHorizontallyFlippable || isVerticallyFlippable || isDiagonallyFlippable1 || isDiagonallyFlippable2);
	}
	
	private void setStone(int row, int col) {
		board[row][col] = player;
	}
	
	private boolean flip(int row, int col) {
		int playerColor = player;
		int opponentColor = player == 1 ? 2 : 1;
		boolean isHorizontallyFlippable = false;
		boolean isVerticallyFlippable = false;
		boolean isDiagonallyFlippable = false;
		
		// Horizaontal
		int leftCol = col - 1;
		int rightCol = col + 1;
		if (leftCol >= 0 && board[row][leftCol] == opponentColor) {
			for (int c = leftCol; c >= 0; c--) {
				if (board[row][c] == 0) {
					break;
				} else if (board[row][c] == playerColor) {
					isHorizontallyFlippable = true;
					for (int i = c + 1; i < col; i++) {
						board[row][i] = playerColor;
					}
				}
			}
		} else if (rightCol < LENGTH && board[row][rightCol] == opponentColor) {
			for (int c = rightCol; c < LENGTH; c++) {
				if (board[row][c] == 0) {
					break;
				} else if (board[row][c] == playerColor) {
					isHorizontallyFlippable = true;
					for (int i = rightCol; i < c; i++) {
						board[row][i] = playerColor;
					}
				}
			}
		}
		
		// Vertical
		int aboveRow = row - 1;
		int belowRow = row + 1;
		if (aboveRow >= 0 && board[aboveRow][col] == opponentColor) {
			for (int r = aboveRow; r >= 0; r--) {
				if (board[r][col] == 0) {
					break;
				} else if (board[r][col] == playerColor) {
					isVerticallyFlippable = true;
					for (int i = r + 1; i < row; i++) {
						board[i][col] = playerColor;
					}
				}
			}
		} else if (belowRow < LENGTH && board[belowRow][col] == opponentColor) {
			for (int r = belowRow; r < LENGTH; r++) {
				if (board[r][col] == 0) {
					break;
				} else if (board[r][col] == playerColor) {
					isVerticallyFlippable = true;
					for (int i = row + 1; i < r; i++) {
						board[i][col] = playerColor;
					}
				}
			}
		}

		// Diagonal
		int r = row - 1;
		int c = col - 1;
		if ((r >= 0 && c >= 0) && board[r][c] == opponentColor) {
			while (r >= 0 && c >= 0) {
				if (board[r][c] == 0) {
					break;
				} else if (board[r][c] == playerColor) {
					isDiagonallyFlippable = true;
					int i = row - 1;
					int j = col - 1;
					while (i != r && j != c) {
						board[i][j] = playerColor;
						i--;
						j--;
					}
				}
				r--;
				c--;
			}
		}
		r = row - 1;
		c = col + 1;
		if ((r >= 0 && c < LENGTH) && board[r][c] == opponentColor) {
			while (r >= 0 && c < LENGTH) {
				if (board[r][c] == 0) {
					break;
				} else if (board[r][c] == playerColor) {
					isDiagonallyFlippable = true;
					int i = row - 1;
					int j = col + 1;
					while (i != r && j != c) {
						board[i][j] = playerColor;
						i--;
						j++;
					}
				}
				r--;
				c++;
			}
		}
		r = row + 1;
		c = col - 1;
		if ((r < LENGTH && c >= 0) && board[r][c] == opponentColor) {
			while (r < LENGTH && c >= 0) {
				if (board[r][c] == 0) {
					break;
				} else if (board[r][c] == playerColor) {
					isDiagonallyFlippable = true;
					int i = row + 1;
					int j = col - 1;
					while (i != r && j != c) {
						board[i][j] = playerColor;
						i++;
						j--;
					}
				}
				r++;
				c--;
			}
		}
		r = row + 1;
		c = col + 1;
		if ((r < LENGTH && c < LENGTH) && board[r][c] == opponentColor) {
			while (r < LENGTH && c < LENGTH) {
				if (board[r][c] == 0) {
					break;
				} else if (board[r][c] == playerColor) {
					isDiagonallyFlippable = true;
					int i = row + 1;
					int j = col + 1;
					while (i != r && j != c) {
						board[i][j] = playerColor;
						i++;
						j++;
					}
				}
				r++;
				c++;
			}
		}
		
		return isHorizontallyFlippable || isVerticallyFlippable || isDiagonallyFlippable;
	}
}

