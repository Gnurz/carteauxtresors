package com.carbonit.carteauxtresors.infrastructure.printer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.carbonit.carteauxtresors.domain.adventurers.Adventurer;
import com.carbonit.carteauxtresors.domain.adventurers.MovementSequence;
import com.carbonit.carteauxtresors.domain.adventurers.Orientation;
import com.carbonit.carteauxtresors.domain.adventurers.api.AdventurerGenerator;
import com.carbonit.carteauxtresors.domain.adventurers.api.impl.CreateAnAdventurer;
import com.carbonit.carteauxtresors.domain.map.AdventureMap;
import com.carbonit.carteauxtresors.domain.map.Coordinate;
import com.carbonit.carteauxtresors.domain.map.api.MapGenerator;
import com.carbonit.carteauxtresors.domain.map.api.impl.CreateAMap;

class InformationsToPrintTest {

	private InformationsToPrint informationsToPrint;
	private MapGenerator mapGenerator;
	private AdventurerGenerator adventurerGenerator;
	private AdventureMap adventureMap;

	@BeforeEach
	public void setUp() {
		informationsToPrint = InformationsToPrint.getInstance();
		this.mapGenerator = new CreateAMap();
		this.adventurerGenerator = new CreateAnAdventurer();
		adventureMap = mapGenerator.createMap(4, 4);
		mapGenerator.addTreasure(adventureMap, 0, 3, 2);
		mapGenerator.addTreasure(adventureMap, 1, 3, 3);
		Adventurer lara = adventurerGenerator.generateAdventurer("Lara", new Coordinate(1, 0),
				new MovementSequence(List.of("A", "D", "A", "D", "A", "D", "A", "A", "G", "A")), Orientation.SOUTH);
		Adventurer indiana = adventurerGenerator.generateAdventurer("Indiana", new Coordinate(1, 0),
				new MovementSequence(List.of("A", "A", "A", "G", "A", "G", "A", "A")), Orientation.SOUTH);

		informationsToPrint.setMapInformation("C-4-4");
		informationsToPrint.setMountains(new ArrayList<String>(Arrays.asList("M-1-0", "M-2-1", "M-0-2")));
		informationsToPrint.setAdventureMap(adventureMap);
		informationsToPrint.setAventurersDto(List.of(lara, indiana));

	}

	@Test
	public void testToString() {
		// Given
		String expected = """
				C - 4 - 4
				M - 1 - 0
				M - 2 - 1
				M - 0 - 2
				T - 0 - 3 - 2
				T - 1 - 3 - 3
				A - Lara - 1 - 0 - S - 0
				A - Indiana - 1 - 0 - S - 0
				
				null""";

		// When
		String actual = informationsToPrint.toString();
		
		// Then
		assertThat(actual).isEqualTo(expected);

	}
	
	
}
