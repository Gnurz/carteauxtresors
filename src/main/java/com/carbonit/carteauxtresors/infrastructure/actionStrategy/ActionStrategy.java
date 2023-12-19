package com.carbonit.carteauxtresors.infrastructure.actionStrategy;

import java.util.List;

import com.carbonit.carteauxtresors.domain.adventurers.Adventurer;
import com.carbonit.carteauxtresors.domain.map.AdventureMap;

public interface ActionStrategy {
	
	boolean moveType(String move);
	Adventurer process(AdventureMap adventureMap, Adventurer adventurer, List<Adventurer> adventurers);

}
