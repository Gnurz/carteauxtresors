package com.carbonit.carteauxtresors.domain.navigate.mouvementstrategy;

import com.carbonit.carteauxtresors.domain.adventurers.Orientation;
import com.carbonit.carteauxtresors.domain.map.Coordinate;

public interface MovementStrategy {
	
	Coordinate calculateNewCoordinates(Coordinate coordoneesDepart);
	Orientation turnLeft();
	Orientation turnRight();
	boolean isDirection(Orientation orientation);

}
