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
import com.carbonit.carteauxtresors.domain.adventurers.api.AdventurerGenerator;
import com.carbonit.carteauxtresors.domain.adventurers.api.impl.CreateAnAdventurer;
import com.carbonit.carteauxtresors.domain.map.AdventureMap;
import com.carbonit.carteauxtresors.domain.map.Coordinate;
import com.carbonit.carteauxtresors.domain.map.api.MapGenerator;
import com.carbonit.carteauxtresors.domain.map.api.impl.CreateAMap;
import com.carbonit.carteauxtresors.domain.navigate.GpsAdventurer;
import com.carbonit.carteauxtresors.domain.navigate.GpsHelper;
import com.carbonit.carteauxtresors.domain.navigate.mouvementstrategy.EstMovement;
import com.carbonit.carteauxtresors.domain.navigate.mouvementstrategy.NorthMovement;
import com.carbonit.carteauxtresors.domain.navigate.mouvementstrategy.WestMovement;
import com.carbonit.carteauxtresors.domain.navigate.mouvementstrategy.SouthMovement;

public class MouvementFunctionTest {

	private MapGenerator mapGenerator;
	private AdventurerGenerator adventurerGenerator;
	private GpsHelper gpsHelper;
	private List<Adventurer> adventurers = new ArrayList<Adventurer>();

	@BeforeEach
	void setup() {
		this.mapGenerator = new CreateAMap();
		this.adventurerGenerator = new CreateAnAdventurer();
		this.gpsHelper = new GpsAdventurer(Arrays.asList(new EstMovement(),
				new WestMovement(), new SouthMovement(), new NorthMovement()));

	}

	@Test
	void shouldReturnCorrecNewtCoordonate_When_SquareIsCorrectAndOrientationSud() {
		// Given
		AdventureMap adventureMap = mapGenerator.createMap(4, 4);
		MovementSequence mouvements = new MovementSequence(
				Arrays.asList("A"));
		Adventurer adventurer = adventurerGenerator.generateAdventurer(
				"Indiana", new Coordinate(1, 1), mouvements, Orientation.SOUTH);
		adventurers.add(adventurer);
		// When
		Adventurer actualAdventurer = gpsHelper.moveForward(adventureMap,
				adventurer, adventurers);

		// Then
		assertThat(actualAdventurer.initialCoordinate())
				.extracting("abscissa", "ordinate").containsExactly(1, 2);

	}
	@Test
	void shouldReturnCorrecNewtCoordonate_When_SquareIsCorrectAndOrientationEst() {
		// Given
		AdventureMap adventureMap = mapGenerator.createMap(4, 4);
		MovementSequence mouvements = new MovementSequence(
				Arrays.asList("A"));
		Adventurer adventurer = adventurerGenerator.generateAdventurer(
				"Indiana", new Coordinate(1, 1), mouvements, Orientation.EST);
		adventurers.add(adventurer);
		// When
		Adventurer actualAdventurer = gpsHelper.moveForward(adventureMap,
				adventurer, adventurers);

		// Then
		assertThat(actualAdventurer.initialCoordinate())
				.extracting("abscissa", "ordinate").containsExactly(2, 1);

	}
	@Test
	void shouldReturnCorrecNewtCoordonate_When_SquareIsCorrectAndOrientationNorth() {
		// Given
		AdventureMap adventureMap = mapGenerator.createMap(4, 4);
		MovementSequence mouvements = new MovementSequence(
				Arrays.asList("A"));
		Adventurer adventurer = adventurerGenerator.generateAdventurer(
				"Indiana", new Coordinate(1, 1), mouvements, Orientation.NORTH);
		adventurers.add(adventurer);
		// When
		Adventurer actualAdventurer = gpsHelper.moveForward(adventureMap,
				adventurer, adventurers);

		// Then
		assertThat(actualAdventurer.initialCoordinate())
				.extracting("abscissa", "ordinate").containsExactly(1, 0);

	}
	@Test
	void shouldReturnCorrecNewtCoordonate_When_SquareIsCorrectAndOrientationOuest() {
		// Given
		AdventureMap adventureMap = mapGenerator.createMap(4, 4);
		MovementSequence mouvements = new MovementSequence(
				Arrays.asList("A"));
		Adventurer adventurer = adventurerGenerator.generateAdventurer(
				"Indiana", new Coordinate(1, 1), mouvements,
				Orientation.WEST);
		adventurers.add(adventurer);
		// When
		Adventurer actualAdventurer = gpsHelper.moveForward(adventureMap,
				adventurer, adventurers);

		// Then
//		assertThat(actualAdventurer.initialCoordinate())
//				.extracting("abscissa", "ordinate").containsExactly(0, 1);
		assertThat(actualAdventurer.initialCoordinate().getAbscissa()).isEqualTo(0);
		assertThat(actualAdventurer.initialCoordinate().getOrdinate()).isEqualTo(1);

	}
	@Test
	void shouldReturnNewOrientation_When_TurnLeftAndOrientationIsSouth() {
		// Given
		AdventureMap adventureMap = mapGenerator.createMap(4, 4);
		MovementSequence mouvements = new MovementSequence(
				Arrays.asList("G"));
		Adventurer adventurer = adventurerGenerator.generateAdventurer(
				"Indiana", new Coordinate(1, 1), mouvements,
				Orientation.SOUTH);

		// When
		Adventurer actualAdventurer = gpsHelper.turnLeft(adventureMap,
				adventurer);

		// Then
		assertThat(actualAdventurer.orientation()).isEqualByComparingTo(Orientation.EST);
				
	}
	@Test
	void shouldReturnNewOrientation_When_TurnRightAndOrientationIsSouth() {
		// Given
		AdventureMap adventureMap = mapGenerator.createMap(4, 4);
		MovementSequence mouvements = new MovementSequence(
				Arrays.asList("D"));
		Adventurer adventurer = adventurerGenerator.generateAdventurer(
				"Indiana", new Coordinate(1, 1), mouvements,
				Orientation.SOUTH);

		// When
		Adventurer actualAdventurer = gpsHelper.turnRight(adventureMap,
				adventurer);

		// Then
		assertThat(actualAdventurer.orientation()).isEqualByComparingTo(Orientation.WEST);
				
	}
	@Test
	void shouldReturnNewOrientation_When_TurnLeftAndOrientationIsNorth() {
		// Given
		AdventureMap adventureMap = mapGenerator.createMap(4, 4);
		MovementSequence mouvements = new MovementSequence(
				Arrays.asList("G"));
		Adventurer adventurer = adventurerGenerator.generateAdventurer(
				"Indiana", new Coordinate(1, 1), mouvements,
				Orientation.NORTH);

		// When
		Adventurer actualAdventurer = gpsHelper.turnLeft(adventureMap,
				adventurer);

		// Then
		assertThat(actualAdventurer.orientation()).isEqualByComparingTo(Orientation.WEST);
				
	}
	@Test
	void shouldReturnNewOrientation_When_TurnRightAndOrientationIsNorth() {
		// Given
		AdventureMap adventureMap = mapGenerator.createMap(4, 4);
		MovementSequence mouvements = new MovementSequence(
				Arrays.asList("D"));
		Adventurer adventurer = adventurerGenerator.generateAdventurer(
				"Indiana", new Coordinate(1, 1), mouvements,
				Orientation.NORTH);

		// When
		Adventurer actualAdventurer = gpsHelper.turnRight(adventureMap,
				adventurer);

		// Then
		assertThat(actualAdventurer.orientation()).isEqualByComparingTo(Orientation.EST);
				
	}
	@Test
	void shouldReturnNewOrientation_When_TurnLeftAndOrientationIsOuest() {
		// Given
		AdventureMap adventureMap = mapGenerator.createMap(4, 4);
		MovementSequence mouvements = new MovementSequence(
				Arrays.asList("G"));
		Adventurer adventurer = adventurerGenerator.generateAdventurer(
				"Indiana", new Coordinate(1, 1), mouvements,
				Orientation.WEST);

		// When
		Adventurer actualAdventurer = gpsHelper.turnLeft(adventureMap,
				adventurer);

		// Then
		assertThat(actualAdventurer.orientation()).isEqualByComparingTo(Orientation.SOUTH);
				
	}
	@Test
	void shouldReturnNewOrientation_When_TurnRightAndOrientationIsOuest() {
		// Given
		AdventureMap adventureMap = mapGenerator.createMap(4, 4);
		MovementSequence mouvements = new MovementSequence(
				Arrays.asList("D"));
		Adventurer adventurer = adventurerGenerator.generateAdventurer(
				"Indiana", new Coordinate(1, 1), mouvements,
				Orientation.WEST);

		// When
		Adventurer actualAdventurer = gpsHelper.turnRight(adventureMap,
				adventurer);

		// Then
		assertThat(actualAdventurer.orientation()).isEqualByComparingTo(Orientation.NORTH);
				
	}
	@Test
	void shouldReturnNewOrientation_When_TurnLeftAndOrientationIsEst() {
		// Given
		AdventureMap adventureMap = mapGenerator.createMap(4, 4);
		MovementSequence mouvements = new MovementSequence(
				Arrays.asList("G"));
		Adventurer adventurer = adventurerGenerator.generateAdventurer(
				"Indiana", new Coordinate(1, 1), mouvements,
				Orientation.EST);

		// When
		Adventurer actualAdventurer = gpsHelper.turnLeft(adventureMap,
				adventurer);

		// Then
		assertThat(actualAdventurer.orientation()).isEqualByComparingTo(Orientation.NORTH);
				
	}
	@Test
	void shouldReturnNewOrientation_When_TurnRightAndOrientationIsEst() {
		// Given
		AdventureMap adventureMap = mapGenerator.createMap(4, 4);
		MovementSequence mouvements = new MovementSequence(
				Arrays.asList("D"));
		Adventurer adventurer = adventurerGenerator.generateAdventurer(
				"Indiana", new Coordinate(1, 1), mouvements,
				Orientation.EST);

		// When
		Adventurer actualAdventurer = gpsHelper.turnRight(adventureMap,
				adventurer);

		// Then
		assertThat(actualAdventurer.orientation()).isEqualByComparingTo(Orientation.SOUTH);
				
	}
	@Test
	void shouldAdventurerIsBlock_When_DesTinationContainsAdventurer() {
		// Given
		AdventureMap adventureMap = mapGenerator.createMap(4, 4);
		MovementSequence mouvements = new MovementSequence(
				Arrays.asList("A"));
		Adventurer adventurer = adventurerGenerator.generateAdventurer(
				"Indiana", new Coordinate(1, 1), mouvements,
				Orientation.SOUTH);
		Adventurer adventurer2 = adventurerGenerator.generateAdventurer(
				"Indiana", new Coordinate(1, 2), mouvements,
				Orientation.SOUTH);
		List<Adventurer> adventurers = List.of(adventurer, adventurer2);
		Coordinate coordonneeExpected = new Coordinate(1,1);

		// When
		Adventurer actualAdventurer = gpsHelper.moveForward(adventureMap,
				adventurer, adventurers);

		// Then
		assertThat(actualAdventurer.initialCoordinate()).usingRecursiveComparison().isEqualTo(coordonneeExpected);
				
	}
	
	@Test
	void shouldAdventurerIsBlock_When_DesTinationIsMountains() {
		// Given
		AdventureMap adventureMap = mapGenerator.createMap(4, 4);
		mapGenerator.addMountain(adventureMap, 1, 2);
		MovementSequence mouvements = new MovementSequence(
				Arrays.asList("A"));
		Adventurer adventurer = adventurerGenerator.generateAdventurer(
				"Indiana", new Coordinate(1, 1), mouvements,
				Orientation.SOUTH);
		List<Adventurer> adventurers = List.of(adventurer);
		Coordinate coordonneeExpected = new Coordinate(1,1);

		// When
		Adventurer actualAdventurer = gpsHelper.moveForward(adventureMap,
				adventurer, adventurers);

		// Then
		assertThat(actualAdventurer.initialCoordinate()).usingRecursiveComparison().isEqualTo(coordonneeExpected);
				
	}
	

}
