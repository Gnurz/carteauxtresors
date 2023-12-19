package com.carbonit.carteauxtresors.domain.map.api.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.carbonit.carteauxtresors.domain.exceptions.BadCoordinatesException;
import com.carbonit.carteauxtresors.domain.exceptions.BadDimensionException;
import com.carbonit.carteauxtresors.domain.exceptions.BadValueException;
import com.carbonit.carteauxtresors.domain.map.AdventureMap;
import com.carbonit.carteauxtresors.domain.map.Square;
import com.carbonit.carteauxtresors.domain.map.Mountain;
import com.carbonit.carteauxtresors.domain.map.Treasure;
import com.carbonit.carteauxtresors.domain.map.api.MapGenerator;
import com.carbonit.carteauxtresors.domain.map.api.impl.CreateAMap;

public class CreateAMapTest {

	MapGenerator mapGenerator;

	@BeforeEach
	void setup() {

		mapGenerator = new CreateAMap();

	}

	@Test
	void should_CreateMapWithGoodDimension_When_InputDimensionIsCorrect() {

		// Given

		int nbCasesHorizontales = 3;
		int nbCasesVerticales = 4;
		AdventureMap expectedMap = new AdventureMap(nbCasesHorizontales, nbCasesVerticales,
				new HashMap<String, Square>());

		// Then
		AdventureMap actualMap = mapGenerator.createMap(nbCasesHorizontales, nbCasesVerticales);

		// When

		assertThat(actualMap).isNotNull();
		assertThat(expectedMap.nbCaseHorizontale()).isEqualTo(actualMap.nbCaseHorizontale());
		assertThat(expectedMap.nbCaseVerticale()).isEqualTo(actualMap.nbCaseVerticale());

	}

	@Test
	void should_ThrowsDimensionException_When_WidthIsNegative() {
		// Given
		int nbCasesHorizontales = -3;
		int nbCasesVerticales = 4;
		
		// Then
		assertThatThrownBy(() -> mapGenerator.createMap(nbCasesHorizontales, nbCasesVerticales))
				.isInstanceOf(BadDimensionException.class)
				.hasMessage("La largeur ou la hauteur de la carte ne peuvent pas être négatives => largeur = "
						+ nbCasesHorizontales + " | hauteur = " + nbCasesVerticales);
	}

	@Test
	void should_ThrowsDimensionException_When_HeightIsNegative() {
		
		// Given
		int nbCasesHorizontales = 3;
		int nbCasesVerticales = -4;

		// Then
		assertThatThrownBy(() -> mapGenerator.createMap(nbCasesHorizontales, nbCasesVerticales))
				.isInstanceOf(BadDimensionException.class)
				.hasMessage("La largeur ou la hauteur de la carte ne peuvent pas être négatives => largeur = "
						+ nbCasesHorizontales + " | hauteur = " + nbCasesVerticales);
	}

	@Test
	void should_ThrowsDimensionException_WhenDimensionhaveWidthAndHeightNegative() {
		// Given
		int nbCasesHorizontales = 3;
		int nbCasesVerticales = -4;

		// When

		assertThatThrownBy(() -> mapGenerator.createMap(nbCasesHorizontales, nbCasesVerticales))
				.isInstanceOf(BadDimensionException.class)
				.hasMessage("La largeur ou la hauteur de la carte ne peuvent pas être négatives => largeur = "
						+ nbCasesHorizontales + " | hauteur = " + nbCasesVerticales);
	}

	@Test
	void should_ThrowsDimensionException_WhenCasesHorizontalsIsZero() {

		// Given
		int nbCasesHorizontales = 0;
		int nbCasesVerticales = 2;

		// When

		assertThatThrownBy(() -> mapGenerator.createMap(nbCasesHorizontales, nbCasesVerticales))
				.isInstanceOf(BadDimensionException.class)
				.hasMessage("La largeur et la hauteur de la carte ne peuvent pas être égales à 0");

	}

	@Test
	void should_ThrowsDimensionException_WhenVerticalSquareIsZero() {

		// Given
		int nbCasesHorizontales = 2;
		int nbCasesVerticales = 0;

		// When

		assertThatThrownBy(() -> mapGenerator.createMap(nbCasesHorizontales, nbCasesVerticales))
				.isInstanceOf(BadDimensionException.class)
				.hasMessage("La largeur et la hauteur de la carte ne peuvent pas être égales à 0");

	}

	@Test
	void should_ThrowsDimensionException_WhenDimensionIsZero() {

		// Given
		int nbCasesHorizontales = 0;
		int nbCasesVerticales = 0;

		// When

		assertThatThrownBy(() -> mapGenerator.createMap(nbCasesHorizontales, nbCasesVerticales))
				.isInstanceOf(BadDimensionException.class)
				.hasMessage("La largeur et la hauteur de la carte ne peuvent pas être égales à 0");

	}

	@Test
	void should_ReturnCorrectNumberOfBlock_When_defineEachBlocksInMap() {

		// Given
		int nbCasesHorizontales = 3;
		int nbCasesVerticales = 4;
		int expectedSize = 12;

		// When
		AdventureMap adventureMap = mapGenerator.createMap(nbCasesHorizontales, nbCasesVerticales);

		// Then
		assertThat(adventureMap.squares().size()).isEqualTo(expectedSize);
	}

	@Test
	void should_KeysAreCorrectCoordonates_When_One_Case() {

		// Given
		int nbCasesHorizontales = 1;
		int nbCasesVerticales = 1;
		String expectedKeys = 0 + ";" + 0;

		// When
		Map<String, Square> squares = mapGenerator.defineEachBlocksInMap(nbCasesHorizontales, nbCasesVerticales);

		AdventureMap adventureMap = mapGenerator.createMap(nbCasesHorizontales, nbCasesVerticales);
		// Then

		assertThat(adventureMap.squares()).containsKey(expectedKeys);
	}

	@ParameterizedTest
	@CsvSource({ "0,0", "0,1", "0,2", "1,0", "1,1", "1,2", "2,0", "2,1", "2,2" })
	void should_KeysAreCorrectCoordonates_When_DefinEachBlocks(int abscisse, int ordonnee) {

		// Given
		int nbCasesHorizontales = 3;
		int nbCasesVerticales = 3;
		String expectedKeys = abscisse + ";" + ordonnee;

		// When
		Map<String, Square> squares = mapGenerator.defineEachBlocksInMap(nbCasesHorizontales, nbCasesVerticales);

		AdventureMap adventureMap = mapGenerator.createMap(nbCasesHorizontales, nbCasesVerticales);
		// Then

		assertThat(adventureMap.squares()).containsKey(expectedKeys);
	}

	@Test()
	void should_AddMountain_WhenPositionIsCorrect() {

		// Given
		AdventureMap adventureMap = new AdventureMap(3, 4, new HashMap<String, Square>());
		int abscisse = 1;
		int ordonnee = 3;

		mapGenerator.addMountain(adventureMap, abscisse, ordonnee);

		// Then
		assertThat(adventureMap).isNotNull();
		assertThat(adventureMap.squares()).hasSize(1);
		assertThat(adventureMap.squares().entrySet().iterator().next().getValue()).isInstanceOf(Mountain.class)
				.extracting("abscisse", "ordonnee").containsExactly(abscisse, ordonnee);

	}

	@ParameterizedTest
	@CsvSource({ "4, 3", "-1, 3" })
	void should_ThrowBadPosition_WhenAbscisseIsOutofMap(int abscisse, int ordonnee) {

		// Given
		AdventureMap adventureMap = new AdventureMap(3, 4, new HashMap<String, Square>());

		// Then
		assertThatThrownBy(() -> mapGenerator.addMountain(adventureMap, abscisse, ordonnee))
				.isInstanceOf(BadCoordinatesException.class)
				.hasMessage("L'abscisse ou l'ordonnée sont en dehors de la carte, la montagne n'a pas été placée");

	}

	@ParameterizedTest
	@CsvSource({ "3, 5", "0, -2" })
	void should_ThrowBadPosition_When_OrdonneeIsOutofMap(int abscisse, int ordonnee) {

		// Given
		AdventureMap adventureMap = new AdventureMap(3, 4, new HashMap<>());

		// Then
		assertThatThrownBy(() -> mapGenerator.addMountain(adventureMap, abscisse, ordonnee))
				.isInstanceOf(BadCoordinatesException.class)
				.hasMessage("L'abscisse ou l'ordonnée sont en dehors de la carte, la montagne n'a pas été placée");
	}

	@Test()
	void should_CorrectNumberofTreasure_When_PositionIsCorrectAndNuberOfTreasureIsPositif() {

		// Given
		AdventureMap adventureMap = new AdventureMap(3, 4, new HashMap<String, Square>());
		int abscisse = 1;
		int ordonnee = 3;
		int treasureNumber = 4;

		mapGenerator.addTreasure(adventureMap, abscisse, ordonnee, treasureNumber);

		// Then
		assertThat(adventureMap).isNotNull();
		assertThat(adventureMap.squares()).hasSize(1);
		assertThat(adventureMap.squares().entrySet().iterator().next().getValue()).isInstanceOf(Treasure.class)
				.extracting("abscisse", "ordonnee", "nbTreasure").containsExactly(abscisse, ordonnee, treasureNumber);

	}

	@ParameterizedTest
	@CsvSource({ "0", "-2" })
	void should_ThrowsBadValueException_When_TreasureNumberIsNegativeOrZero(int treasureNumber) {

		// Given
		AdventureMap adventureMap = new AdventureMap(3, 4, new HashMap<String, Square>());
		int abscisse = 1;
		int ordonnee = 3;

		// Then
		assertThatThrownBy(() -> mapGenerator.addTreasure(adventureMap, abscisse, ordonnee, treasureNumber))
				.isInstanceOf(BadValueException.class).hasMessage(
						"Vous essayez d'ajouter un nombre de trésor inférieur ou égal à 0, le trésor n'a pas été ajouté");
	}

	

}
