package com.carbonit.carteauxtresors.functionsTests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.carbonit.carteauxtresors.domain.adventurers.Adventurer;
import com.carbonit.carteauxtresors.domain.adventurers.Orientation;
import com.carbonit.carteauxtresors.domain.adventurers.MovementSequence;
import com.carbonit.carteauxtresors.domain.adventurers.api.AdventurerGenerator;
import com.carbonit.carteauxtresors.domain.adventurers.api.impl.CreateAnAdventurer;
import com.carbonit.carteauxtresors.domain.map.AdventureMap;
import com.carbonit.carteauxtresors.domain.map.Coordinate;
import com.carbonit.carteauxtresors.domain.map.api.MapGenerator;
import com.carbonit.carteauxtresors.domain.map.api.impl.CreateAMap;

public class ModelizeMapFunctionTest {
	
	MapGenerator mapGenerator;
	AdventurerGenerator adventurerGenerator;
	
	@BeforeEach
	void setup () {
		this.mapGenerator = new CreateAMap();
		this.adventurerGenerator = new CreateAnAdventurer();
		
	}
	@Test
	void should_ModelizeCleanMap_When_AdventureMapContainsOneSquare() {
		
		//Given
		AdventureMap adventureMap = mapGenerator.createMap(1, 1);
		String expected = ".\n";
		//When
		String actual = mapGenerator.modelize(adventureMap);
		
		//Then
		assertThat(actual).isEqualTo(expected);
		
		
	}
	
	@Test
	void should_ModelizeCleanMap_When_AdventureMapContainsTwoHorizontalSquare() {
		
		//Given
		AdventureMap adventureMap = mapGenerator.createMap(2, 1);
		String expected = ". .\n";
		//When
		String actual = mapGenerator.modelize(adventureMap);
		
		//Then
		assertThat(actual).isEqualTo(expected);
		
		
	}
	
	@Test
	void should_ModelizeCleanMap_When_AdventureMapContainsTwoVerticalSquare() {
		
		//Given
		AdventureMap adventureMap = mapGenerator.createMap(1, 2);
		String expected = ".\n.\n";
		//When
		String actual = mapGenerator.modelize(adventureMap);
		
		//Then
		assertThat(actual).isEqualTo(expected);
		
		
	}
	
	@ParameterizedTest
	@CsvSource({"2, 1, '. .\n'", "3, 1, '. . .\n'", "4, 1, '. . . .\n'", "5, 1, '. . . . .\n'"})
	void should_ModelizeCleanMap_When_AdventureMapContainsOnlyHorizontalSquares(int horizontalSquare, int verticalSquare, String expected) {
		
		//Given
		AdventureMap adventureMap = mapGenerator.createMap(horizontalSquare, verticalSquare);
		//When
		String actual = mapGenerator.modelize(adventureMap);
		
		//Then
		assertThat(actual).isEqualTo(expected);
		
		
	}
	

	@ParameterizedTest
	@CsvSource({"1, 2, '.\n.'", "1, 3, '.\n.\n.'", "1, 4, '.\n.\n.\n.'", "1, 5, '.\n.\n.\n.\n.'"})
	void should_ModelizeCleanMap_When_AdventureMapContainsOnlyVerticalSquares(int horizontalSquare, int verticalSquare, String expected) {
		
		//Given
		AdventureMap adventureMap = mapGenerator.createMap(horizontalSquare, verticalSquare);
		//When
		String actual = mapGenerator.modelize(adventureMap);
		expected = expected + "\n";
		//Then
		assertThat(actual).isEqualTo(expected);
		
		
	}
	
	@ParameterizedTest
	@CsvSource({"2, 2, '. .\n. .\n'", "3, 3, '. . .\n. . .\n. . .", "4, 4, '. . . .\n. . . .\n. . . .\n. . . .", "2, 3, '. .\n. .\n. .\n'"})
	void should_ModelizeCleanMap_When_AdventureMapComplete(int horizontalSquare, int verticalSquare, String expected) {
		
		//Given
		AdventureMap adventureMap = mapGenerator.createMap(horizontalSquare, verticalSquare);
		//When
		String actual = mapGenerator.modelize(adventureMap);
		//Then
		assertThat(actual).isEqualTo(expected);
		
		
	}
	@ParameterizedTest
	@CsvSource({"4, 4, '.   .   .   .  \n.   .   .   .  \n.   .   .   .  \n.   .   (M) .  "})
	void should_ModelizeCleanMap_WithAMountain(int horizontalSquare, int verticalSquare, String expected) {
		
		//Given
		AdventureMap adventureMap = mapGenerator.createMap(horizontalSquare, verticalSquare);
		mapGenerator.addMountain(adventureMap, 2, 3);
		//When
		String actual = mapGenerator.modelize(adventureMap);
		
		
		//Then
		assertThat(actual).isEqualTo(expected);
		
		
	}
	
	@ParameterizedTest
	@CsvSource({"4, 4, '.   .   .   .  \n.   .   .   .  \n.   (M) .   .  \n.   .   (4) .  "})
	void should_ModelizeCleanMap_With4Treasure(int horizontalSquare, int verticalSquare, String expected) {
		
		//Given
		AdventureMap adventureMap = mapGenerator.createMap(horizontalSquare, verticalSquare);
		mapGenerator.addTreasure(adventureMap, 2, 3, 4);
		mapGenerator.addMountain(adventureMap, 1, 2);
		//When
		String actual = mapGenerator.modelize(adventureMap);
		
		//Then
		assertThat(actual).isEqualTo(expected);
		
		
	}
	
	@ParameterizedTest
	@CsvSource({"4,4, 'A(toto) .       .       .      \n.       .       .       .      \n.       (M)     .       .      \n.       .       (4)     .      "})
	void should_ModelizeMapWithAdventurer(int horizontalSquare, int verticalSquare, String expected) {
		
		//Given
		AdventureMap adventureMap = mapGenerator.createMap(horizontalSquare, verticalSquare);
		Adventurer adventurer = adventurerGenerator.generateAdventurer("toto", new Coordinate(0, 0), new MovementSequence(List.of("A")), Orientation.SOUTH);
		List<Adventurer> adventurers = new ArrayList<Adventurer>();
		adventurers.add(adventurer);
		mapGenerator.addTreasure(adventureMap, 2, 3, 4);
		mapGenerator.addMountain(adventureMap, 1, 2);
		
		//When
		String actual = mapGenerator.modelize(adventureMap, adventurers);
		
		//Then
		assertThat(actual).isEqualTo(expected);
		
		
	}
	
	


}
