package com.carbonit.carteauxtresors.infrastructure.printer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import com.carbonit.carteauxtresors.domain.adventurers.Adventurer;
import com.carbonit.carteauxtresors.domain.map.AdventureMap;
import com.carbonit.carteauxtresors.domain.map.Square;
import com.carbonit.carteauxtresors.domain.map.Treasure;
import com.carbonit.carteauxtresors.infrastructure.DTO.AdventurerDTO;
import com.carbonit.carteauxtresors.infrastructure.DTO.MapperDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InformationsToPrint {

	private static class Singleton {
		private static final InformationsToPrint instance = new InformationsToPrint();
	}

	private String mapInformation;
	private List<String> mountains = new ArrayList<String>();
	private List<AdventurerDTO> aventurersDto = new ArrayList<>();
	private AdventureMap adventureMap;
	private String mapModelization;

	private InformationsToPrint() {
	}

	public static InformationsToPrint getInstance() {
		return Singleton.instance;
	}

	@Override
	public String toString() {
		StringBuilder sbMountain = new StringBuilder();
		mountains.stream().forEach(mountain -> {
			sbMountain.append(mountain).append("\n");
		});
		StringBuilder sbAdventurer = new StringBuilder();
		aventurersDto.stream().forEach(aventurer -> {
			sbAdventurer.append(aventurer.toString()).append("\n");
		});
		return formatOutput(mapInformation) + "\n" + formatOutput(sbMountain.toString()) + getTreasures()
				+ sbAdventurer.toString() + "\n" + mapModelization;
	}

	/**
	 * Définit les aventuriers DTO en fonction des aventuriers fournis.
	 *
	 * @param adventurers La liste des aventuriers à définir en tant que DTO.
	 */
	public void setAventurersDto(List<Adventurer> adventurers) {

		adventurers.stream().forEach(adventurer -> {

			AdventurerDTO adventurerDto = MapperDTO.fromAdventurer(adventurer);
			aventurersDto.add(adventurerDto);
		});

	}

	/**
	 * Obtient les informations sur les trésors sous forme d'une chaîne de
	 * caractères formatée.
	 *
	 * @return La chaîne de caractères formatée contenant les informations sur les
	 *         trésors.
	 */
	private String getTreasures() {

		StringBuilder sb = new StringBuilder();
		String separator = " - ";

		adventureMap.squares().entrySet().stream().forEach(entry -> {
			Square square = entry.getValue();
			if (square instanceof Treasure) {

				int nbTresor = ((Treasure) square).getNbTreasure();
				if (nbTresor > 0) {
					int abscisse = ((Treasure) square).getAbscisse();
					int ordonnee = ((Treasure) square).getOrdonnee();
					sb.append("T").append(separator).append(abscisse).append(separator).append(ordonnee)
							.append(separator).append(nbTresor).append("\n");
				}
			}
		});

		return sb.toString();

	}

	/**
	 * Formate la chaîne de caractères en sortie en remplaçant les tirets par un
	 * espace suivi d'un tiret.
	 *
	 * @param inputString La chaîne de caractères d'entrée.
	 * @return La chaîne de caractères formatée.
	 */
	private String formatOutput(String inputString) {
		return RegExUtils.replaceAll(inputString, "-", " - ");
	}

}
