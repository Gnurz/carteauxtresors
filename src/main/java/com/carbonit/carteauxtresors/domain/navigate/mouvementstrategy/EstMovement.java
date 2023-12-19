package com.carbonit.carteauxtresors.domain.navigate.mouvementstrategy;

import org.springframework.stereotype.Component;

import com.carbonit.carteauxtresors.domain.adventurers.Orientation;
import com.carbonit.carteauxtresors.domain.map.Coordinate;


@Component
public class EstMovement implements MovementStrategy{

	
	@Override
	public Coordinate calculateNewCoordinates(Coordinate initialCoordinate) {
		// TODO Auto-generated method stub
		return 	 new Coordinate(initialCoordinate.getAbscissa()+1, initialCoordinate.getOrdinate()); 
	}
	
	@Override
	public Orientation turnLeft() {
		// TODO Auto-generated method stub
		return Orientation.NORTH;
	}
	@Override
	public Orientation turnRight() {
		// TODO Auto-generated method stub
		return Orientation.SOUTH;
	}
	
	@Override
	public boolean isDirection(Orientation orientation) {
		return orientation == Orientation.EST;
	}
}
