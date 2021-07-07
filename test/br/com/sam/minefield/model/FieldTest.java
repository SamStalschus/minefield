package br.com.sam.minefield.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.sam.minefield.exception.ExplosionException;

public class FieldTest {

	private Field field;
	
	@BeforeEach
	void initField() {
		field = new Field(3, 3);
	}
	
	@Test
	void testNeighborDistanceOneValid() {
		Field neighbor = new Field(3, 2);
		
		boolean result = field.addNeighbor(neighbor);
		
		assertTrue(result);
	}
	
	@Test
	void testNeighborDistanceTwoValid() {
		Field neighbor = new Field(2, 2);
		
		boolean result = field.addNeighbor(neighbor);
		
		assertTrue(result);
	}
	
	@Test
	void testNeighborDistanceTwoInvalid() {
		Field neighbor = new Field(3, 1);
		
		boolean result = field.addNeighbor(neighbor);
		
		assertFalse(result);
	}
	
	@Test
	void valueMarkedDefault() {
		assertFalse(field.isMarked());
	}
	
	@Test
	void toggleMarked() {
		field.toggleMarked();
		assertTrue(field.isMarked());
	}
	
	@Test
	void toggleMarkedTwoCalled() {
		field.toggleMarked();
		field.toggleMarked();
		assertFalse(field.isMarked());
	}
	
	@Test
	void openFieldNoMinedAndNoMarked() {
		assertTrue(field.open());
	}
	
	@Test
	void openFieldNoMinedAndMarked() {
		field.toggleMarked();
		assertFalse(field.open());
	}
	
	@Test
	void openFieldMinedAndMarked() {
		field.toggleMarked();
		field.mine();
		assertFalse(field.open());
	}
	
	@Test
	void openFieldMinedAndNoMarked() {
		field.mine();
		
		assertThrows(ExplosionException.class, () -> {
			field.open();
		});
	}
	
	@Test
	void openFieldWithNeighbors() {
		
		Field neighbor = new Field(1, 1);
		Field neighborOfNeighbor = new Field(2, 2);
		
		neighborOfNeighbor.addNeighbor(neighbor);
		
		field.addNeighbor(neighborOfNeighbor);
		field.open();

		assertTrue(neighborOfNeighbor.isOpen() && neighbor.isOpen());
	}
	
	@Test
	void openFieldWithNeighborsOfMined() {
		
		Field neighbor = new Field(1, 1);
		Field neighborMined = new Field(1, 2);
		neighborMined.mine();
		
		Field neighborOfNeighbor = new Field(2, 2);
		
		neighborOfNeighbor.addNeighbor(neighbor);
		neighborOfNeighbor.addNeighbor(neighborMined);
		
		field.addNeighbor(neighborOfNeighbor);
		field.open();

		assertTrue(neighborOfNeighbor.isOpen() && !neighbor.isOpen());
	}
}
