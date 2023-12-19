package com.carbonit.carteauxtresors.domain.map.api.impl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.carbonit.carteauxtresors.domain.adventurers.Adventurer;
import com.carbonit.carteauxtresors.domain.exceptions.BadCoordinatesException;
import com.carbonit.carteauxtresors.domain.exceptions.BadDimensionException;
import com.carbonit.carteauxtresors.domain.exceptions.BadValueException;
import com.carbonit.carteauxtresors.domain.map.AdventureMap;
import com.carbonit.carteauxtresors.domain.map.Square;
import com.carbonit.carteauxtresors.domain.map.Dimension;
import com.carbonit.carteauxtresors.domain.map.Mountain;
import com.carbonit.carteauxtresors.domain.map.Plain;
import com.carbonit.carteauxtresors.domain.map.Treasure;
import com.carbonit.carteauxtresors.domain.map.api.MapGenerator;

@Service
public class CreateAMap implements MapGenerator {

	/**
	 * Crée une carte d'aventure avec les dimensions spécifiées.
	 *
	 * @param horizontalSquare Le nombre de cases horizontales de la carte.
	 * @param verticalSquare   Le nombre de cases verticales de la carte.
	 * @return Une instance de {@link AdventureMap} représentant la carte générée.
	 * @throws BadDimensionException Si la largeur ou la hauteur de la carte est
	 *                               négative, ou si l'une des deux est égale à 0.
	 */
	@Override
	public AdventureMap createMap(int horizontalSquare, int verticalSquare) {

		if (horizontalSquare < 0 || verticalSquare < 0) {
			throw new BadDimensionException(
					"La largeur ou la hauteur de la carte ne peuvent pas être négatives => largeur = "
							+ horizontalSquare + " | hauteur = " + verticalSquare);
		}
		if (horizontalSquare == 0 || verticalSquare == 0) {
			throw new BadDimensionException("La largeur et la hauteur de la carte ne peuvent pas être égales à 0");
		}

		Map<String, Square> addCases = defineEachBlocksInMap(horizontalSquare, verticalSquare);

		return new AdventureMap(horizontalSquare, verticalSquare, addCases);
	}

	/**
	 * Définit chaque bloc dans la carte en fonction du nombre de cases horizontales
	 * et verticales spécifiées. Les blocs de "base" sont des plaines
	 * 
	 * @param horizontalSquare Le nombre de cases horizontales dans la carte.
	 * @param verticalSquare   Le nombre de cases verticales dans la carte.
	 * @return Une {@link Map} contenant chaque case de la carte, avec les
	 *         coordonnées comme clé et une instance de {@link Plain} comme valeur.
	 */
	public Map<String, Square> defineEachBlocksInMap(int horizontalSquare, int verticalSquare) {

		Map<String, Square> squares = new HashMap<String, Square>();

		for (int i = 0; i < horizontalSquare; i++) {
			for (int j = 0; j < verticalSquare; j++) {
				String coordinate = i + ";" + j;
				squares.put(coordinate, new Plain(i, j, new Dimension(1, 1)));
			}

		}

		return squares;
	}

	/**
	 * Ajoute une montagne à la carte d'aventure aux coordonnées spécifiées.
	 *
	 * @param adventureMap      La carte d'aventure à laquelle ajouter la montagne.
	 * @param abscissa L'abscisse de la montagne.
	 * @param ordinate L'ordonnée de la montagne.
	 * @throws BadCoordinatesException Si les coordonnées spécifiées sont en dehors
	 *                                 de la carte.
	 */
	@Override
	public void addMountain(AdventureMap adventureMap, int abscissa, int ordinate) {
		if (!verifyCoodonates(adventureMap, abscissa, ordinate)) {
			throw new BadCoordinatesException(
					"L'abscisse ou l'ordonnée sont en dehors de la carte, la montagne n'a pas été placée");
		}
		String cle = abscissa + ";" + ordinate;
		adventureMap.squares().put(cle, new Mountain(abscissa, ordinate, new Dimension(1, 1)));

	}

	/**
	 * Ajoute un trésor à la carte d'aventure aux coordonnées spécifiées avec la
	 * quantité spécifiée.
	 *
	 * @param map       La carte d'aventure à laquelle ajouter le trésor.
	 * @param abscissa  L'abscisse du trésor.
	 * @param ordinate  L'ordonnée du trésor.
	 * @param nbTreasures La quantité de trésors à ajouter.
	 * @throws BadCoordinatesException Si les coordonnées spécifiées sont en dehors
	 *                                 de la carte.
	 * @throws BadValueException       Si la quantité de trésors spécifiée est
	 *                                 inférieure ou égale à 0.
	 */
	@Override
	public void addTreasure(AdventureMap map, int abscissa, int ordinate, int nbTreasures) {

		if (!verifyCoodonates(map, abscissa, ordinate)) {
			throw new BadCoordinatesException(
					"L'abscisse ou l'ordonnée sont en dehors de la carte, le trésor n'a pas été placée");
		}
		if (nbTreasures <= 0) {
			throw new BadValueException(
					"Vous essayez d'ajouter un nombre de trésor inférieur ou égal à 0, le trésor n'a pas été ajouté");
		}
		String cle = abscissa + ";" + ordinate;
		map.squares().put(cle, new Treasure(abscissa, ordinate, new Dimension(1, 1), nbTreasures));

	}

	/**
	 * Vérifie si les coordonnées spécifiées sont valides pour la carte d'aventure
	 * donnée.
	 *
	 * @param map      La carte d'aventure.
	 * @param abscissa L'abscisse à vérifier.
	 * @param ordinate L'ordonnée à vérifier.
	 * @return {@code true} si les coordonnées sont valides, {@code false} sinon.
	 */
	private boolean verifyCoodonates(AdventureMap map, int abscissa, int ordinate) {

		return (abscissa < 0 || ordinate < 0 || abscissa > map.nbCaseHorizontale()
				|| ordinate > map.nbCaseHorizontale()) ? false : true;

	}

	/**
	 * Modélise la carte d'aventure avec les aventuriers.
	 *
	 * @param adventureMap La carte d'aventure.
	 * @param adventurers  La liste des aventuriers.
	 * @return Une représentation textuelle de la carte avec les aventuriers.
	 */
	@Override
	public String modelize(AdventureMap adventureMap, List<Adventurer> adventurers) {

		int xMax = adventureMap.nbCaseHorizontale();
		int yMax = adventureMap.nbCaseVerticale();
		StringBuilder stringBuilder = new StringBuilder();
		// Cette valeur sert à définir l'écart entre chaque case pour aligner la carte
		// en sortie
		int maxCaseWidth = calculateMaxCaseWidth(getMaxAdventurerString(adventurers));

		for (int j = 0; j < yMax; j++) {
			for (int i = 0; i < xMax; i++) {
				String coordonnee = i + ";" + j;

				Square currentCase = adventureMap.squares().get(coordonnee);

				if (i != 0) {
					stringBuilder.append(" ");
				}
				if (currentCase != null) {
					// Récupère la valeur de la case
					String caseValue = currentCase.toString();
					for (Adventurer adventurer : adventurers) {
						if (adventurer.initialCoordinate().getAbscissa() == i
								&& adventurer.initialCoordinate().getOrdinate() == j) {
							// Si un aventurier est présent sur la case on prend son nom en valeur
							caseValue = adventurer.toString();
							break;

						}
					}
					// Complète le nombre d'espace par rapport à maxCaseWidth
					stringBuilder.append(StringUtils.rightPad(caseValue, maxCaseWidth));
				}
			}
			if (j >= 0) {
				stringBuilder.append("\n");
			}
		}

		return stringBuilder.toString();
	}

	/**
	 * Calcule la largeur maximale d'une case en fonction de sa valeur.
	 *
	 * @param caseValue La valeur de la case.
	 * @return La longueur maximale de la case.
	 */
	private int calculateMaxCaseWidth(String caseValue) {
		return caseValue.length();
	}

	/**
	 * Permet d'obtenir l'aventurier avec le plus grand nom.
	 * 
	 * @param adventurers La liste des aventuriers.
	 * @return La représentation sous forme de chaîne de caractères de l'aventurier
	 *         avec la plus grande longueur.
	 */
	private String getMaxAdventurerString(List<Adventurer> adventurers) {
		return adventurers.stream().map(Adventurer::toString).max(Comparator.comparingInt(String::length)).orElse("");
	}

	/**
	 * Génère une représentation textuelle de la carte d'aventure sans tenir compte
	 * des aventuriers présents. (Cette méthode est déconseillée, veuillez utiliser
	 * la version prenant en compte les aventuriers.)
	 *
	 * @param adventureMap La carte d'aventure.
	 * @return Une représentation textuelle de la carte d'aventure sans les
	 *         aventuriers.
	 * @deprecated Utilisez la méthode modelize(AdventureMap adventureMap,
	 *             List<Adventurer> adventurers) pour inclure les aventuriers.
	 */
	@Override
	@Deprecated
	public String modelize(AdventureMap adventureMap) {

		int xMax = adventureMap.nbCaseHorizontale();
		int yMax = adventureMap.nbCaseVerticale();
		int maxCaseWidth = calculateMaxCaseWidth(adventureMap);
		StringBuffer stringBuffer = new StringBuffer();

		for (int j = 0; j < yMax; j++) {
			for (int i = 0; i < xMax; i++) {
				String coordonnee = i + ";" + j;
				Square currentCase = adventureMap.squares().get(coordonnee);

				if (i != 0) {
					stringBuffer.append(" ");
				}
				if (currentCase != null) {
					stringBuffer.append(StringUtils.rightPad(currentCase.toString(), maxCaseWidth));
				}
			}
			if (j >= 0) {
				stringBuffer.append("\n");
			}
		}

		return stringBuffer.toString();
	}

	/**
	 * Calcule la largeur maximale des cases de la carte d'aventure pour aligner la
	 * représentation textuelle.
	 *
	 * @param adventureMap La carte d'aventure.
	 * @return La largeur maximale des cases.
	 */
	private int calculateMaxCaseWidth(AdventureMap adventureMap) {

		return adventureMap.squares().values().stream().mapToInt(currentSquare -> currentSquare.toString().length()).max()
				.orElseGet(() -> 5);

	}

}
