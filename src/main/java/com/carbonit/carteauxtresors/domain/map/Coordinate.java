package com.carbonit.carteauxtresors.domain.map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Coordinate {
	
	
	private int abscissa;
	private int ordinate;
	
	
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Coordinate coordonnee = (Coordinate) obj;
		return coordonnee.getAbscissa() == this.abscissa && coordonnee.getOrdinate() == this.ordinate;
	}
	
	

}
