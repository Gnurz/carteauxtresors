package com.carbonit.carteauxtresors.domain.navigate;

import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.carbonit.carteauxtresors.domain.adventurers.Adventurer;
import com.carbonit.carteauxtresors.domain.adventurers.Orientation;
import com.carbonit.carteauxtresors.domain.adventurers.TreasureFarmed;
import com.carbonit.carteauxtresors.domain.exceptions.BadCoordinatesException;
import com.carbonit.carteauxtresors.domain.map.AdventureMap;
import com.carbonit.carteauxtresors.domain.map.Square;
import com.carbonit.carteauxtresors.domain.map.Coordinate;
import com.carbonit.carteauxtresors.domain.map.Dimension;
import com.carbonit.carteauxtresors.domain.map.Mountain;
import com.carbonit.carteauxtresors.domain.map.Treasure;
import com.carbonit.carteauxtresors.domain.navigate.mouvementstrategy.MovementStrategy;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class GpsAdventurer implements GpsHelper {

	private final List<MovementStrategy> movementStrategies;

	/**
	 * Récupère la case à la position spécifiée sur la carte d'aventure.
	 *
	 * @param adventureMap La carte d'aventure.
	 * @param currentCoordonate  Les coordonnées de la case.
	 * @return La case à la position spécifiée.
	 * @throws BadCoordinatesException Si les coordonnées ne sont pas valides sur la
	 *                                 carte d'aventure.
	 */
	@Override
	public Square whereIAm(AdventureMap adventureMap, Coordinate currentCoordonate) {

		String coordonateKey = currentCoordonate.getAbscissa() + ";" + currentCoordonate.getOrdinate();

		if (!adventureMap.squares().containsKey(coordonateKey)) {
			throw new BadCoordinatesException(
					"Votre aventurier se trouve en terre inconnue. Il est perdu : " + coordonateKey);
		}

		return adventureMap.squares().get(coordonateKey);
	}
	

	/**
	 * Fais avancer un aventurier sur la carte d'aventure, en fonction de son
	 * orientation.
	 *
	 * @param adventureMap      La carte d'aventure.
	 * @param currentAdventurer L'aventurier actuel.
	 * @param adventurers       La liste des aventuriers sur la carte.
	 * @return Un nouvel aventurier après le déplacement.
	 * @throws BadCoordinatesException Si les coordonnées de la nouvelle position
	 *                                 sont invalides.
	 */
	@Override
	public Adventurer moveForward(AdventureMap adventureMap, Adventurer currentAdventurer, List<Adventurer> adventurers) {

		Orientation orientation = currentAdventurer.orientation();
		Coordinate initialCoordonate = currentAdventurer.initialCoordinate();
		TreasureFarmed adventurerTreasures = currentAdventurer.treasure();
		

		MovementStrategy strategyToProcess = movementStrategies.stream()
				.filter(strategy -> strategy.isDirection(orientation)).findFirst().orElseThrow();
		Coordinate destinationCoordonate = strategyToProcess.calculateNewCoordinates(initialCoordonate);

		if (destinationCoordonate == null) {
			throw new BadCoordinatesException("Oups votre aventurier à mystérieusement disparu dans une faille");
		}

		try {
			if (adventurerIsBlocked(adventureMap, destinationCoordonate, adventurers)) {
				destinationCoordonate = initialCoordonate;
			} else if (Treasure.class == whereIAm(adventureMap, destinationCoordonate).getClass()) {

				if (isTreasureChestNotEmpty(adventureMap, adventurerTreasures, destinationCoordonate)) {
					adventurerTreasures = new TreasureFarmed(adventurerTreasures.nbtreasure() + 1);
				}

			}
		} catch (BadCoordinatesException e) {
			log.error(e.getMessage());
		}

		return new Adventurer(currentAdventurer.nom(), destinationCoordonate, currentAdventurer.movementSequences(),
				adventurerTreasures, currentAdventurer.orientation());
	}

	/**
	 * Vérifie si la case d'arrivée de l'aventurier est accessible.
	 *
	 * @param adventureMap     La carte d'aventure sur laquelle se déplace l'aventurier.
	 * @param destinationCoordonate Les coordonnées d'arrivée de l'aventurier.
	 * @param adventurers      La liste des aventuriers présents sur la carte.
	 * @return true si l'aventurier est bloqué, sinon false.
	 */
	private boolean adventurerIsBlocked(AdventureMap adventureMap, Coordinate destinationCoordonate,
			List<Adventurer> adventurers) {
		return whereIAm(adventureMap, destinationCoordonate).getClass() == Mountain.class
				|| adventurerPresentOnSquare(adventurers, destinationCoordonate);
	}

	/**
	 * Vérifie si un aventurier est présent sur une case donnée.
	 *
	 * @param adventurers       La liste des aventuriers à vérifier.
	 * @param destinationCoordonate Les coordonnées de la case à vérifier.
	 * @return true si un aventurier est présent sur la case, sinon false.
	 */
	private boolean adventurerPresentOnSquare(List<Adventurer> adventurers, Coordinate destinationCoordonate) {

		return adventurers.stream().anyMatch(adventurer -> adventurer.initialCoordinate().equals(destinationCoordonate));
	}

	/**
	 * Récolte un trésor sur la case spécifiée, met à jour le nombre de trésors et
	 * la map.
	 *
	 * @param adventureMap      La carte de l'aventure.
	 * @param treasure          Le trésor actuel du joueur.
	 * @param destinationCoordinate Les coordonnées de la case où le trésor doit être
	 *                          récolté.
	 * @return true si le trésor a été récolté avec succès, sinon false.
	 */
	private boolean isTreasureChestNotEmpty(AdventureMap adventureMap, TreasureFarmed treasure, Coordinate destinationCoordinate) {

		Square square = whereIAm(adventureMap, destinationCoordinate);

		if (square instanceof Treasure) {
			Treasure treasureSquare = (Treasure) square;
			int nbTreasures = treasureSquare.getNbTreasure();

			if (nbTreasures > 0) {
				int newNbTreasure = nbTreasures - 1;

				// Créez un nouveau trésor avec le nombre de trésors mis à jour
				Treasure updatedTreasure = new Treasure(destinationCoordinate.getAbscissa(),
						destinationCoordinate.getOrdinate(), new Dimension(1, 1), newNbTreasure);

				String coordinate = destinationCoordinate.getAbscissa() + ";" + destinationCoordinate.getOrdinate();

				// Mets à jour la case dans la map
				adventureMap.squares().replace(coordinate, updatedTreasure);

				return true;
			}
		}

		return false;
	}

	/**
	 * Fait tourner l'aventurier vers la gauche.
	 *
	 * @param adventureMap La carte de l'aventure.
	 * @param adventurer L'aventurier actuel.
	 * @return Un nouvel aventurier avec l'orientation mise à jour.
	 */
	@Override
	public Adventurer turnLeft(AdventureMap adventureMap, Adventurer adventurer) {
		return turn(adventureMap, adventurer, MovementStrategy::turnLeft);
	}
	
	/**
	 * Fait tourner l'aventurier vers la droite.
	 *
	 * @param adventureMap La carte de l'aventure.
	 * @param adventurer L'aventurier actuel.
	 * @return Un nouvel aventurier avec l'orientation mise à jour.
	 */
	@Override
	public Adventurer turnRight(AdventureMap adventureMap, Adventurer adventurer) {
		return turn(adventureMap, adventurer, MovementStrategy::turnRight);
	}

	/**
	 * Fait tourner l'aventurier dans la direction spécifiée par la fonction de rotation.
	 *
	 * @param adventureMap La carte de l'aventure.
	 * @param adventurer L'aventurier actuel.
	 * @param turnFunction La fonction de rotation (gauche ou droite).
	 * @return Un nouvel aventurier avec l'orientation mise à jour.
	 */
	private Adventurer turn(AdventureMap adventureMap, Adventurer adventurer,
			Function<MovementStrategy, Orientation> turnFunction) {
		Orientation orientation = adventurer.orientation();

		MovementStrategy strategyToProcess = movementStrategies.stream()
				.filter(strategy -> strategy.isDirection(orientation)).findFirst().orElseThrow();

		Orientation newOrientation = turnFunction.apply(strategyToProcess);

		return new Adventurer(adventurer.nom(), adventurer.initialCoordinate(), adventurer.movementSequences(),
				adventurer.treasure(), newOrientation);
	}

}
