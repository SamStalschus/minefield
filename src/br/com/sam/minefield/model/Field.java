package br.com.sam.minefield.model;

import java.util.ArrayList;
import java.util.List;

import br.com.sam.minefield.exception.ExplosionException;

public class Field {

	private final int line;
	private final int column;
	
	private boolean mined;
	private boolean open;
	private boolean marked;
	
	private List<Field> neighbors = new ArrayList<>();
	
	Field(int line, int column) {
		this.line = line;
		this.column = column;
	}
	
	boolean addNeighbor(Field neighbor) {
		boolean lineDiff = line != neighbor.line;
		boolean columnDiff = column != neighbor.line;
		boolean diagonalLine = lineDiff && columnDiff;
		
		int deltaLine = Math.abs(line - neighbor.line);
		int deltaColumn = Math.abs(line - neighbor.column);
		int deltaAll = deltaColumn + deltaLine;
		
		if(deltaAll == 1 && !diagonalLine) {
			neighbors.add(neighbor);
			return true;
		} else if (deltaAll == 2 && diagonalLine) {
			neighbors.add(neighbor);
			return true;
		} 
			
		return false;
		
	}
	
	void toggleMarked() {
		if(!open) {
			marked = !marked;
		}
	}
	
	boolean open() {
		
		if(!open && !marked) {
			open = true;
			
			if(mined) {
				throw new ExplosionException();
			}
			
			if(neighborsSecurity()) {
				neighbors.forEach(v -> v.open());
			}
			
			return true;
		}
		
		return false;
	}
	
	public boolean isMarked() {
		return marked;
	}
	
	boolean neighborsSecurity() {
		return neighbors.stream().noneMatch(v -> v.mined);
	}
	
	void mine() {
		mined = true;
	}
	
	public boolean isMined() {
		return mined;
	}
	
	public boolean isOpen() {
		return open;
	}

	void setOpen(boolean open) {
		this.open = open;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}
	
	boolean objectiveArchieved() {
		boolean unveiled = !mined && open;
		boolean protectted = mined && marked;
		return unveiled || protectted;
	}
	
	long neighboringMines() {
		return neighbors.stream().filter(v-> v.mined).count();	
	}
	
	void reset() {
		open = false;
		mined = false;
		marked = false;
	}
	
	public String toString() {
		if(marked) {
			return "x";
		} else if(open && mined) {
			return "*";
		} else if(open && neighboringMines() > 0) {
			return Long.toString(neighboringMines());
		} else if(open) {
			return " ";
		} else {
			return "?";
		}
	}
}
