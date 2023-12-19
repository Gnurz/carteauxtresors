package com.carbonit.carteauxtresors.domain.map;

import lombok.Getter;

@Getter
public abstract class Square {

	private final int abscisse;
	private final int ordonnee;
	private final Dimension dimension;

	
	public Square(int x, int y, Dimension dimension) {
		this.abscisse = x;
		this.ordonnee = y;
		this.dimension = dimension;

	}
}
