import java.util.Scanner;

public class TheGame {
	public static void main(String[] args) {
		Othello othello = new Othello();
		System.out.println("  Game Start!!");
		othello.printBoard();
		
		while (!othello.isGameOver()) {
			othello.enterStone();
		}
	}
}

