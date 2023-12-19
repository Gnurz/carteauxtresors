package com.carbonit.carteauxtresors.infrastructure.parsers.impl;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.carbonit.carteauxtresors.infrastructure.parsers.ExtractFileService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ExtractFilesInformations implements ExtractFileService {

	private static final int LENGTH_MAP_OR_MOUNTAIN = 3;
	private static final int LENGTH_TREASURE = 4;
	private static final int LENGTH_ADVENTURER = 6;

	/**
	 * Lit le contenu d'un fichier et retourne une liste de String après avoir été
	 * formatée (mise en majuscule et suppression des espaces blanc).
	 *
	 * @param path Le chemin du fichier à lire.
	 * @return Une liste de chaînes représentant les lignes du fichier.
	 */
	@Override
	public List<String> readFileMap(String path) {

		List<String> inputLines = new ArrayList<String>();

		Path pathMapFile = Paths.get(path);

		try (BufferedReader br = Files.newBufferedReader(pathMapFile)) {
			String lines = "";

			while ((lines = br.readLine()) != null) {
				String formatLine = formatLine(lines);
				if (!StringUtils.startsWith(formatLine, "#")) {

					processLine(inputLines, formatLine);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputLines;
	}

	/**
	 * Traite une ligne formatée et l'ajoute à la liste spécifiée si elle satisfait
	 * les critères de vérification.
	 *
	 * @param inputLines La liste dans laquelle ajouter la ligne traitée.
	 * @param formatLine La ligne formatée à traiter.
	 */
	private void processLine(List<String> inputLines, String formatLine) {

		String[] splitFormatLine = StringUtils.splitPreserveAllTokens(formatLine, "-");
		String parseAction = splitFormatLine[0];
		if (verifyLine(parseAction, splitFormatLine)) {
			inputLines.add(formatLine);
		}

	}

	/**
	 * Vérifie si la ligne formatée est valide en fonction de l'identifiant de
	 * l'action.
	 *
	 * @param identifier   L'identifiant de l'action à vérifier.
	 * @param elementCheck Les éléments à vérifier dans la ligne formatée.
	 * @return true si la ligne est valide, sinon false.
	 */
	private boolean verifyLine(String identifier, String[] elementCheck) {

		switch (identifier) {
		case "C":
		case "M":
			return verifyFormatLineInput(elementCheck, LENGTH_MAP_OR_MOUNTAIN);
		case "T":
			return verifyFormatLineInput(elementCheck, LENGTH_TREASURE);
		case "A":
			return verifyFormatLineInput(elementCheck, LENGTH_ADVENTURER);
		default:
			// Si l'identifiant n'est pas reconnu, la ligne n'est pas valide.
			return false;
		}
	}

	/**
	 * Vérifie si le format de la ligne est correct en fonction de la longueur
	 * attendue.
	 *
	 * @param lines          Les éléments de la ligne à vérifier.
	 * @param expectedLength La longueur attendue pour la ligne.
	 * @return true si le format est correct, sinon false.
	 * @throws IllegalArgumentException Si la longueur de la ligne n'est pas
	 *                                  conforme.
	 */
	private boolean verifyFormatLineInput(String[] lines, int expectedLength) {

		if (lines.length != expectedLength) {
			throw new IllegalArgumentException("Le format attendu n'est pas correct");
		}

		switch (expectedLength) {
		case LENGTH_MAP_OR_MOUNTAIN:
			return isCorrectFormatMapOrMountain(lines);
		case LENGTH_TREASURE:
			return isCorrectFormatTreasure(lines);

		case LENGTH_ADVENTURER:
			return isCorrectFormatAdventurer(lines);

		default:
			return false;
		}
	}

	/**
	 * Vérifie si le format de la ligne est correct pour une carte ou une montagne.
	 *
	 * @param lines Les éléments de la ligne à vérifier.
	 * @return true si le format est correct, sinon false.
	 */
	private boolean isCorrectFormatMapOrMountain(String[] lines) {
		return StringUtils.isNumeric(lines[1]) && StringUtils.isNumeric(lines[2]);
	}

	/**
	 * Vérifie si le format de la ligne est correct pour un trésor.
	 *
	 * @param lines Les éléments de la ligne à vérifier.
	 * @return true si le format est correct, sinon false.
	 */
	private boolean isCorrectFormatTreasure(String[] lines) {
		return StringUtils.isNumeric(lines[1]) && StringUtils.isNumeric(lines[2]) && StringUtils.isNumeric(lines[3]);
	}

	/**
	 * Vérifie si le format de la ligne est correct pour un aventurier.
	 *
	 * @param lines Les éléments de la ligne à vérifier.
	 * @return true si le format est correct, sinon false.
	 */
	private boolean isCorrectFormatAdventurer(String[] lines) {
		return StringUtils.isNumeric(lines[2]) && StringUtils.isNumeric(lines[3])
				&& (StringUtils.equalsIgnoreCase(lines[4], "S") || StringUtils.equalsIgnoreCase(lines[4], "N")
						|| StringUtils.equalsIgnoreCase(lines[4], "E") || StringUtils.equalsIgnoreCase(lines[4], "O"));
	}
	
	/**
	 * Formate la ligne en supprimant les espaces et en la mettant en majuscules.
	 *
	 * @param lines La ligne à formater.
	 * @return La ligne formatée.
	 */
	private String formatLine(String lines) {
		return StringUtils.deleteWhitespace(StringUtils.upperCase(lines));

	}

}
