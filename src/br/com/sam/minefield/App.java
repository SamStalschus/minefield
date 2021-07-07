package br.com.sam.minefield;

import br.com.sam.minefield.model.Board;
import br.com.sam.minefield.view.BoardConsole;

public class App {

	public static void main(String[] args) {
		
		Board board = new Board(6, 6, 6);
		
		new BoardConsole(board);
	}
}
