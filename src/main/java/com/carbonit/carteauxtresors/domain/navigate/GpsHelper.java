package com.carbonit.carteauxtresors.domain.navigate;

import java.util.List;

import com.carbonit.carteauxtresors.domain.adventurers.Adventurer;
import com.carbonit.carteauxtresors.domain.map.AdventureMap;
import com.carbonit.carteauxtresors.domain.map.Square;
import com.carbonit.carteauxtresors.domain.map.Coordinate;

public interface GpsHelper {

	Square whereIAm(AdventureMap adventureMap, Coordinate coordinate);


	Adventurer turnLeft(AdventureMap adventureMap, Adventurer adventurer);
	
	Adventurer turnRight(AdventureMap adventureMap, Adventurer adventurer);

	Adventurer moveForward(AdventureMap adventureMap, Adventurer currentAdventurer, List<Adventurer> adventurers);

}
