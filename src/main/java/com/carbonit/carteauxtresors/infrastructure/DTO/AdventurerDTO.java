package com.carbonit.carteauxtresors.infrastructure.DTO;

import com.carbonit.carteauxtresors.domain.adventurers.Orientation;
import com.carbonit.carteauxtresors.domain.adventurers.MovementSequence;
import com.carbonit.carteauxtresors.domain.adventurers.TreasureFarmed;
import com.carbonit.carteauxtresors.domain.map.Coordinate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdventurerDTO {

	private String name;
	private Coordinate initialCoordinate;
	private TreasureFarmed treasure;
	private Orientation orientation;

	@Override
	public String toString() {
		return "A - " + getName() + " - " + initialCoordinate.getAbscissa() + " - " + initialCoordinate.getOrdinate() + " - "
				+ orientation.getInitial() + " - " + treasure.nbtreasure();
	}
	
	public String getName() {
		return this.name.substring(0,1) + name.substring(1).toLowerCase();
	}

}
