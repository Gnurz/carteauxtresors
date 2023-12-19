package com.carbonit.carteauxtresors.domain.map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Treasure extends Square{
	private int nbTreasure;

	public Treasure(int x, int y, Dimension dimension, int nbTreasure) {
		super(x, y, dimension);
		// TODO Auto-generated constructor stub
		this.nbTreasure = nbTreasure;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "(" + nbTreasure + ")";
	}
		
}
