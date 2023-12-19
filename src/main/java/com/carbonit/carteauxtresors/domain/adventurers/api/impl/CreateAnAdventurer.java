package com.carbonit.carteauxtresors.domain.adventurers.api.impl;

import org.springframework.stereotype.Service;

import com.carbonit.carteauxtresors.domain.adventurers.Adventurer;
import com.carbonit.carteauxtresors.domain.adventurers.Orientation;
import com.carbonit.carteauxtresors.domain.adventurers.MovementSequence;
import com.carbonit.carteauxtresors.domain.adventurers.TreasureFarmed;
import com.carbonit.carteauxtresors.domain.adventurers.api.AdventurerGenerator;
import com.carbonit.carteauxtresors.domain.map.Coordinate;

@Service
public class CreateAnAdventurer implements AdventurerGenerator {

	/**
	 * Génère un aventurier avec les informations spécifiées.
	 *
	 * @param nom Le nom de l'aventurier.
	 * @param coordonnees Les coordonnées de départ de l'aventurier.
	 * @param movementSequence La séquence de mouvements de l'aventurier.
	 * @param orientation L'orientation initiale de l'aventurier.
	 * @return Un nouvel aventurier initialisé.
	 */
	@Override
	public Adventurer generateAdventurer(String nom, Coordinate coordonnes, MovementSequence movementSequence,
			Orientation orientation) {

		return new Adventurer(nom, coordonnes, movementSequence, new TreasureFarmed(0), orientation);
	}
}
