package com.carbonit.carteauxtresors.infrastructure.actionStrategy;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.carbonit.carteauxtresors.domain.adventurers.Adventurer;
import com.carbonit.carteauxtresors.domain.map.AdventureMap;
import com.carbonit.carteauxtresors.domain.navigate.GpsHelper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ForwardAction implements ActionStrategy {
	
	private final GpsHelper gpsHelper;

	@Override
	public boolean moveType(String move) {
		return StringUtils.equals(move, "A");
	}

	@Override
	public Adventurer process(AdventureMap adventureMap, Adventurer adventurer, List<Adventurer>adventurers) {
 		return gpsHelper.moveForward(adventureMap, adventurer, adventurers);

	}

}
