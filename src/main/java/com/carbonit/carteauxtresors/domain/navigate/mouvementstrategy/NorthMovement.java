package com.carbonit.carteauxtresors.domain.navigate.mouvementstrategy;

import org.springframework.stereotype.Component;

import com.carbonit.carteauxtresors.domain.adventurers.Orientation;
import com.carbonit.carteauxtresors.domain.map.Coordinate;

@Component
public class NorthMovement implements MovementStrategy {

	@Override
	public Coordinate calculateNewCoordinates(Coordinate coordoneesDepart) {
		return new Coordinate(coordoneesDepart.getAbscissa(), coordoneesDepart.getOrdinate() -1);

	}
	
	@Override
	public Orientation turnLeft() {
		return Orientation.WEST;
	}
	@Override
	public Orientation turnRight() {
		return Orientation.EST;
	}

	@Override
	public boolean isDirection(Orientation orientation) {
		// TODO Auto-generated method stub
		return orientation == Orientation.NORTH;
	}
}
