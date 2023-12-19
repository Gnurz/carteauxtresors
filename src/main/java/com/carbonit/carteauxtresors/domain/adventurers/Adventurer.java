package com.carbonit.carteauxtresors.domain.adventurers;

import java.util.Objects;

import com.carbonit.carteauxtresors.domain.map.Coordinate;

public record Adventurer(String nom, Coordinate initialCoordinate, MovementSequence movementSequences,
		TreasureFarmed treasure, Orientation orientation) {

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass())
			return false;
		Adventurer thatAdventurer = (Adventurer) o;
		return nom.equals(thatAdventurer.nom) && initialCoordinate.equals(thatAdventurer.initialCoordinate);
	}

	@Override
	public String toString() {
		return "A(" + nom + ")";
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(nom, initialCoordinate, movementSequences, treasure, orientation);
	}
}
