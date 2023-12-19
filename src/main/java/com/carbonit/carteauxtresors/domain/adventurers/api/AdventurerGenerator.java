package com.carbonit.carteauxtresors.domain.adventurers.api;

import com.carbonit.carteauxtresors.domain.adventurers.Adventurer;
import com.carbonit.carteauxtresors.domain.adventurers.Orientation;
import com.carbonit.carteauxtresors.domain.adventurers.MovementSequence;
import com.carbonit.carteauxtresors.domain.map.Coordinate;

public interface AdventurerGenerator {

	
	Adventurer generateAdventurer(String nom, Coordinate coordonnes, MovementSequence movementSequence,
			Orientation orientation);
	
}
