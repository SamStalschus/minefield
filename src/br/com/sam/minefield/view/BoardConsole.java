package br.com.sam.minefield.view;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.sam.minefield.exception.ExitException;
import br.com.sam.minefield.exception.ExplosionException;
import br.com.sam.minefield.model.Board;

public class BoardConsole {

	private Board board;
	private Scanner scan = new Scanner(System.in);
	
	public BoardConsole(Board board) {
		this.board = board;
		
		executeGame();
	}

	private void executeGame() {
		try {
			boolean continuee = true;
			
			while(continuee) {
				
				loopGame();
				
				System.out.println("Outra partida? (S/n) ");
				String response = scan.nextLine();
				if("n".equalsIgnoreCase(response)) {
					continuee = false;
				} else {
					board.reset();
				}
			}
		} catch (ExitException e) {
			System.out.println("Bybye!!!");
		} finally {
			scan.close();
		}
		
	}

	private void loopGame() {
		try {
			
			while(!board.objectiveArchieved()) {
				System.out.println(board);
				
				String typed = captureTypedValue("Digite (x, y): ");
				
				Iterator<Integer> xy =Arrays.stream(typed.split(","))
					.map(e -> Integer.parseInt(e.trim()))
					.iterator();
				

				typed = captureTypedValue("1 - Abrir ou 2 (Des)Marcar: ");

				if("1".equals(typed)) {
					board.open(xy.next(), xy.next());
				} else if("2".equals(typed)) {
					board.toggleMarked(xy.next(), xy.next());
				}
			}
			
			System.out.println(board);
			System.out.println("Você ganhou!!!");
		} catch (ExplosionException e) {
			System.out.println(board);
			System.out.println("Você perdeu!!!");
		}
	}
	
	private String captureTypedValue(String text) {
		System.out.println(text);
		String typed = scan.nextLine();
		
		if("sair".equalsIgnoreCase(typed)) {
			throw new ExitException();
		}
		
		return typed;
	}
}
