package com.carbonit.carteauxtresors.infrastructure.parsers.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.carbonit.carteauxtresors.domain.adventurers.Adventurer;
import com.carbonit.carteauxtresors.domain.adventurers.Orientation;
import com.carbonit.carteauxtresors.domain.adventurers.MovementSequence;
import com.carbonit.carteauxtresors.domain.adventurers.api.AdventurerGenerator;
import com.carbonit.carteauxtresors.domain.map.AdventureMap;
import com.carbonit.carteauxtresors.domain.map.Coordinate;
import com.carbonit.carteauxtresors.domain.map.api.MapGenerator;
import com.carbonit.carteauxtresors.infrastructure.actionStrategy.ActionStrategy;
import com.carbonit.carteauxtresors.infrastructure.parsers.ParseFile;
import com.carbonit.carteauxtresors.infrastructure.printer.InformationsToPrint;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AdventureMapParser implements ParseFile {
	private final MapGenerator mapGenerator;
	private final AdventurerGenerator adventurerGenerator;
	private final List<ActionStrategy> actionStrategies;

	private static final String SPLIT_ITERATOR = "-";

	/**
	 * Génère le jeu en se basant sur les informations fournies par une liste en
	 * entrée qui contient les informations du fichier indiqué en argument du
	 * programme.
	 *
	 * Cette méthode génère de la carte d'aventure, le placement des montagnes et
	 * des trésors. Puis elle permet la génération des aventuriers, ainsi que leur
	 * déplacements sur la carte. Les informations résultantes sont ensuite définies
	 * dans le singleton InformationsToPrint.
	 *
	 * @param file La liste de String représentant le contenu du fichier.
	 */
	@Override
	public void generateGame(List<String> file) {

		AdventureMap adventureMap = generateMap(file);
		generateMountain(file, adventureMap);
		generateTreasure(file, adventureMap);
		List<Adventurer> adventurers = generateAdventurers(file);
		moveAdventurersOnMap(adventureMap, adventurers);
		InformationsToPrint.getInstance().setAventurersDto(adventurers);
		InformationsToPrint.getInstance().setAdventureMap(adventureMap);

	}

	/**
	 * Effectue le déplacement des aventuriers sur la carte d'aventure en fonction
	 * de leurs séquences de mouvements et en tour par tour.
	 *
	 * @param adventureMap La carte d'aventure sur laquelle les aventuriers se
	 *                     déplacent.
	 * @param adventurers  La liste des aventuriers à déplacer.
	 */
	private void moveAdventurersOnMap(AdventureMap adventureMap, List<Adventurer> adventurers) {
		String mapGenerate = "";
		// Détermine le nombre de tour maximum de déplacement
		int maxTour = adventurers.stream().map(adventurer -> adventurer.movementSequences().mouvements().size())
				.max(Integer::compare).orElse(0);

		for (int tour = 0; tour < maxTour; tour++) {
			for (Adventurer adventurer : adventurers) {
				List<String> adventurerMovements = adventurer.movementSequences().mouvements();
				// Vérifie que l'aventurier a des mouvements restants
				if (tour < adventurerMovements.size()) {
					int index = adventurers.indexOf(adventurer);
					String movement = adventurerMovements.get(tour);
					// Récupération de la stratégie de mouvement
					ActionStrategy strategyToProcess = getMovementStrategy(movement);
					// Application de la stratégie
					adventurer = strategyToProcess.process(adventureMap, adventurer, adventurers);
					// Mise à jour de l'aventurier dans la liste
					adventurers.set(index, adventurer);
					// Génération de la carte à jour après le mouvement de l'aventurier
					mapGenerate = mapGenerator.modelize(adventureMap, adventurers);
				}
			}
		}
		// Enregistre la modélisation finale de la carte telle qu'elle sera inscrite
		// dans le fichier de sortie
		InformationsToPrint.getInstance().setMapModelization(mapGenerate);
	}

	/**
	 * Récupère la stratégie de mouvement associée à un type de mouvement donné.
	 *
	 * @param movement Le type de mouvement pour récupérer la bonne stratégie.
	 * @return La stratégie de mouvement correspondante.
	 * @throws IllegalArgumentException Si aucune stratégie n'est trouvée pour le
	 *                                  type de mouvement spécifié.
	 */
	private ActionStrategy getMovementStrategy(String movement) {
		return actionStrategies.stream().filter(strategy -> strategy.moveType(movement)).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("No strategy found for movement: " + movement));
	}

	/**
	 * Génère la carte d'aventure vierge à partir des informations contenues dans le
	 * fichier d'entrée.
	 *
	 * @param file : Une liste de String correspondant au fichier d'entrée.
	 * @return La carte d'aventure générée.
	 * @throws NoSuchElementException Si aucune ligne ne contient l'information de
	 *                                la carte d'aventure.
	 */
	private AdventureMap generateMap(List<String> file) {

		try {
			String adventureMapToParse = file.stream().filter(line -> StringUtils.containsIgnoreCase(line, "C-"))
					.findFirst().orElseThrow();

			Integer horizontalSquares = Integer
					.parseInt(StringUtils.splitPreserveAllTokens(adventureMapToParse, SPLIT_ITERATOR)[1]);
			Integer verticalSquares = Integer
					.parseInt(StringUtils.splitPreserveAllTokens(adventureMapToParse, SPLIT_ITERATOR)[2]);

			// Ajout de la ligne de sortie au Printer
			InformationsToPrint.getInstance().setMapInformation(adventureMapToParse);

			return mapGenerator.createMap(horizontalSquares, verticalSquares);

		} catch (NumberFormatException e) {
			throw new NumberFormatException("Les informations de la carte ne sont pas considérées comme des nombres");
		}
			
	}

	/**
	 * Ajoute les montagnes à la carte existante à partir des informations contenues
	 * dans le fichier d'entrée.
	 *
	 * @param file         La liste des lignes du fichier d'entrée.
	 * @param adventureMap La carte d'aventure à laquelle ajouter les montagnes.
	 * @throws NumberFormatException Si les informations sur les montagnes ne
	 *                               peuvent pas être interprétées comme des
	 *                               nombres.
	 */
	private void generateMountain(List<String> file, AdventureMap adventureMap) {
		file.stream().filter(line -> StringUtils.containsIgnoreCase(line, "M-")).forEach(mountain -> {
			try {
				Integer abscisse = Integer.parseInt(StringUtils.splitPreserveAllTokens(mountain, SPLIT_ITERATOR)[1]);
				Integer ordonnee = Integer.parseInt(StringUtils.splitPreserveAllTokens(mountain, SPLIT_ITERATOR)[2]);
				InformationsToPrint.getInstance().getMountains().add(mountain);

				mapGenerator.addMountain(adventureMap, abscisse, ordonnee);
			} catch (NumberFormatException e) {
				throw new NumberFormatException(
						"Les informations sur la montagne sont pas considérées comme des nombres");
			}
		});

	}

	/**
	 * Génère les trésors sura la carte d'aventure à partir des informations
	 * contenues dans le fichier d'entrée.
	 *
	 * @param file         La liste des lignes du fichier d'entrée.
	 * @param adventureMap La carte d'aventure à laquelle ajouter les trésors.
	 * @throws NumberFormatException Si les informations sur les trésors ne peuvent
	 *                               pas être interprétées comme des nombres.
	 */
	private void generateTreasure(List<String> file, AdventureMap adventureMap) {
		file.stream().filter(line -> StringUtils.containsIgnoreCase(line, "T-")).forEach(treasures -> {
			try {
				Integer abscisse = Integer.parseInt(StringUtils.splitPreserveAllTokens(treasures, SPLIT_ITERATOR)[1]);
				Integer ordonnee = Integer.parseInt(StringUtils.splitPreserveAllTokens(treasures, SPLIT_ITERATOR)[2]);
				Integer nbTreasure = Integer.parseInt(StringUtils.splitPreserveAllTokens(treasures, SPLIT_ITERATOR)[3]);
				mapGenerator.addTreasure(adventureMap, abscisse, ordonnee, nbTreasure);
			} catch (NumberFormatException e) {
				throw new NumberFormatException(
						"Les informations sur les trésors ne sont pas considérées comme des nombres");
			}
		});

	}

	/**
	 * Génère la liste des aventuriers à partir des informations contenues dans le fichier d'entrée.
	 *
	 * @param file La liste des lignes du fichier d'entrée.
	 * @return La liste des aventuriers générée.
	 * @throws NumberFormatException Si certaines informations sur les aventuriers ne peuvent pas être interprétées comme des nombres.
	 * @throws IllegalArgumentException Si l'orientation d'un aventurier ne peut pas être interprétée.
	 */
	private List<Adventurer> generateAdventurers(List<String> file) {
		List<Adventurer> adventurers = new ArrayList<Adventurer>();
		file.stream().filter(line -> StringUtils.containsIgnoreCase(line, "A-")).forEach(line -> {
			try {
				String[] aventurerFormatter = StringUtils.splitPreserveAllTokens(line, SPLIT_ITERATOR);
				String prenom = aventurerFormatter[1];
				Integer abscisse = Integer.parseInt(aventurerFormatter[2]);
				Integer ordonnee = Integer.parseInt(aventurerFormatter[3]);
				Orientation orientationDepart = defineOrientation(aventurerFormatter[4]);
				MovementSequence movementSequence = defineMovement(aventurerFormatter[5]);

				adventurers.add(adventurerGenerator.generateAdventurer(prenom, new Coordinate(abscisse, ordonnee),
						movementSequence, orientationDepart));

			} catch (NumberFormatException e) {
				throw new NumberFormatException(
						"Certaines informations sur les aventuriers ne peuvent pas être interprétées comme des nombres");
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("L'orientation d'un aventurier ne peut pas être interprétée");
			}
		});

		return adventurers;
	}

	/**
	 * Définit la séquence de mouvements à partir de la chaîne de caractères spécifiée.
	 *
	 * @param movementString La chaîne de caractères représentant la séquence de mouvements.
	 * @return La séquence de mouvements créée.
	 */
	private MovementSequence defineMovement(String movementString) {
		List<String> mouvements = new ArrayList<String>();
		for (int i = 0; i < movementString.length(); i++) {
			String mouvement = String.valueOf(movementString.charAt(i));
			mouvements.add(mouvement);
		}
		return new MovementSequence(mouvements);
	}

	/**
	 * Définit l'orientation à partir de la chaîne de caractères spécifiée.
	 *
	 * @param orientation La chaîne de caractères représentant l'orientation.
	 * @return L'orientation correspondante.
	 * @throws IllegalArgumentException Si la chaîne de caractères ne correspond à aucune orientation connue.
	 */
	private Orientation defineOrientation(String orientation) {

		switch (orientation) {
		case "S":
			return Orientation.SOUTH;
		case "E":
			return Orientation.EST;
		case "O":
			return Orientation.WEST;
		case "N":
			return Orientation.NORTH;
		default:
			throw new IllegalArgumentException("Impossible de parse l'orientaiton : " + orientation);
		}
	}

}

