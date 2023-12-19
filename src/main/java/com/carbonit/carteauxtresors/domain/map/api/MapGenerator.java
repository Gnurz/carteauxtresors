package com.carbonit.carteauxtresors.domain.map.api;

import java.util.List;
import java.util.Map;

import com.carbonit.carteauxtresors.domain.adventurers.Adventurer;
import com.carbonit.carteauxtresors.domain.exceptions.BadCoordinatesException;
import com.carbonit.carteauxtresors.domain.map.AdventureMap;
import com.carbonit.carteauxtresors.domain.map.Square;

public interface MapGenerator {

	AdventureMap createMap(int casesHorizontales, int casesVerticales);

	void addMountain(AdventureMap map, int abscisse, int ordonnee) throws BadCoordinatesException;

	Map<String, Square> defineEachBlocksInMap(int casesHorizontales, int casesVerticales);

	void addTreasure(AdventureMap map, int abscisse, int ordonnee, int nbtresors);


	String modelize(AdventureMap adventureMap, List<Adventurer> adventurers);
	String modelize(AdventureMap adventureMap);

}
