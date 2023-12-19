package com.carbonit.carteauxtresors.domain.navigate.mouvementstrategy;

import org.springframework.stereotype.Component;

import com.carbonit.carteauxtresors.domain.adventurers.Orientation;
import com.carbonit.carteauxtresors.domain.map.Coordinate;

@Component
public class WestMovement implements MovementStrategy{

	@Override
	public Coordinate calculateNewCoordinates(Coordinate coordoneesDepart) {
		return new Coordinate(coordoneesDepart.getAbscissa()-1, coordoneesDepart.getOrdinate());
	}
	
	@Override
	public Orientation turnLeft() {
		return Orientation.SOUTH ;
	}
	@Override
	public Orientation turnRight() {
		return Orientation.NORTH;
	}
	
	@Override
	public boolean isDirection(Orientation orientation) {
		return orientation==Orientation.WEST;
	}
	
}
