package com.carbonit.carteauxtresors.functionsTests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.carbonit.carteauxtresors.domain.adventurers.Adventurer;
import com.carbonit.carteauxtresors.domain.adventurers.Orientation;
import com.carbonit.carteauxtresors.domain.adventurers.MovementSequence;
import com.carbonit.carteauxtresors.domain.adventurers.TreasureFarmed;
import com.carbonit.carteauxtresors.domain.adventurers.api.AdventurerGenerator;
import com.carbonit.carteauxtresors.domain.adventurers.api.impl.CreateAnAdventurer;
import com.carbonit.carteauxtresors.domain.map.AdventureMap;
import com.carbonit.carteauxtresors.domain.map.Square;
import com.carbonit.carteauxtresors.domain.map.Coordinate;
import com.carbonit.carteauxtresors.domain.map.Mountain;
import com.carbonit.carteauxtresors.domain.map.Plain;
import com.carbonit.carteauxtresors.domain.map.api.MapGenerator;
import com.carbonit.carteauxtresors.domain.map.api.impl.CreateAMap;
import com.carbonit.carteauxtresors.domain.navigate.GpsAdventurer;
import com.carbonit.carteauxtresors.domain.navigate.GpsHelper;
import com.carbonit.carteauxtresors.domain.navigate.mouvementstrategy.EstMovement;
import com.carbonit.carteauxtresors.domain.navigate.mouvementstrategy.NorthMovement;
import com.carbonit.carteauxtresors.domain.navigate.mouvementstrategy.WestMovement;
import com.carbonit.carteauxtresors.domain.navigate.mouvementstrategy.SouthMovement;


public class GenerateAdventurerTest {

	private String nom = "Potter";
	private Coordinate coordonnees = new Coordinate(1, 1);
	private Orientation orientation = Orientation.SOUTH;
	private List<String> mouvements = new ArrayList<>(Arrays.asList("A", "A", "D", "A", "D", "A"));
	private MovementSequence movementSequence;
	private MapGenerator mapGenereator;
	private GpsHelper gpsHelper;

	private AdventurerGenerator adventurerGenerator;

	@BeforeEach
	void setup() {

		adventurerGenerator = new CreateAnAdventurer();
		this.movementSequence = new MovementSequence(mouvements);
		this.gpsHelper = new GpsAdventurer(Arrays.asList(new EstMovement(), new WestMovement(), new SouthMovement(), new NorthMovement()));
		this.mapGenereator = new CreateAMap();

	}

	@Test
	void should_GenerateAdventurer_When_InputIsCorrect() {
		// Given

		Adventurer expectedAdventurer = new Adventurer(nom, coordonnees, movementSequence, new TreasureFarmed(0), Orientation.SOUTH);

		// When
		Adventurer actualAdventurer = adventurerGenerator.generateAdventurer(nom, coordonnees, movementSequence,
				orientation);

		// Then
		assertThat(expectedAdventurer).isEqualTo(actualAdventurer);

	}

	@Test
	void shoul_ReturnPlaine_When_AdventurerIsOnPlain() {
		//Given
		AdventureMap adventureMap = mapGenereator.createMap(4, 4);
		Adventurer adventurer = adventurerGenerator.generateAdventurer(nom, coordonnees, movementSequence,
				orientation);

		//When
		Square actual = gpsHelper.whereIAm(adventureMap, coordonnees);
		
		//Then
		assertThat(actual).isInstanceOf(Plain.class);

	}
	@Test
	void shoul_ReturnMontagne_When_AdventurerIsOnMountain() {
		//Given
		AdventureMap adventureMap = mapGenereator.createMap(4, 4);
		mapGenereator.addMountain(adventureMap, 1, 1);
		Adventurer adventurer = adventurerGenerator.generateAdventurer(nom, coordonnees, movementSequence,
				orientation);

		//When
		Square actual = gpsHelper.whereIAm(adventureMap, coordonnees);
		
		//Then
		assertThat(actual).isInstanceOf(Mountain.class);

	}

}
