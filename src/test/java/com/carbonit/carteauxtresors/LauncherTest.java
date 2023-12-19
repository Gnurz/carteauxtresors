package com.carbonit.carteauxtresors;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.carbonit.carteauxtresors.domain.adventurers.api.AdventurerGenerator;
import com.carbonit.carteauxtresors.domain.adventurers.api.impl.CreateAnAdventurer;
import com.carbonit.carteauxtresors.domain.map.api.MapGenerator;
import com.carbonit.carteauxtresors.domain.map.api.impl.CreateAMap;
import com.carbonit.carteauxtresors.domain.navigate.GpsAdventurer;
import com.carbonit.carteauxtresors.domain.navigate.GpsHelper;
import com.carbonit.carteauxtresors.domain.navigate.mouvementstrategy.EstMovement;
import com.carbonit.carteauxtresors.domain.navigate.mouvementstrategy.NorthMovement;
import com.carbonit.carteauxtresors.domain.navigate.mouvementstrategy.SouthMovement;
import com.carbonit.carteauxtresors.domain.navigate.mouvementstrategy.WestMovement;
import com.carbonit.carteauxtresors.infrastructure.actionStrategy.ForwardAction;
import com.carbonit.carteauxtresors.infrastructure.actionStrategy.LeftAction;
import com.carbonit.carteauxtresors.infrastructure.actionStrategy.RightAction;
import com.carbonit.carteauxtresors.infrastructure.parsers.ExtractFileService;
import com.carbonit.carteauxtresors.infrastructure.parsers.ParseFile;
import com.carbonit.carteauxtresors.infrastructure.parsers.impl.AdventureMapParser;
import com.carbonit.carteauxtresors.infrastructure.parsers.impl.ExtractFilesInformations;
import com.carbonit.carteauxtresors.infrastructure.printer.InformationsToPrint;
import com.carbonit.carteauxtresors.infrastructure.printer.WriteFileService;

public class LauncherTest {

	private ExtractFileService extractFileService;
	private ParseFile parseFile;
	private WriteFileService writeFileService;

	@BeforeEach
	void setup() {

		GpsHelper gpsHelper = new GpsAdventurer(
				Arrays.asList(new EstMovement(), new WestMovement(), new SouthMovement(), new NorthMovement()));
		this.extractFileService = new ExtractFilesInformations();
		List<com.carbonit.carteauxtresors.infrastructure.actionStrategy.ActionStrategy> movementsStrategies = List
				.of(new ForwardAction(gpsHelper), new LeftAction(gpsHelper), new RightAction(gpsHelper));
		MapGenerator mapGenerator = new CreateAMap();
		AdventurerGenerator adventurerGenerator = new CreateAnAdventurer();
		this.parseFile = new AdventureMapParser(mapGenerator, adventurerGenerator, movementsStrategies);
		this.writeFileService = new WriteFileService();
	}

	@Test
	void shouldReturnTrue_When_OutputFileIsGenerated() throws IOException {

		List<String> fileToParse = extractFileService.readFileMap("./src/test/resources/in.txt");
		parseFile.generateGame(fileToParse);

		writeFileService.writeFinalFile("./src/test/resources/outTest.txt",
				InformationsToPrint.getInstance().toString());

		assertThat(Files.exists(Path.of("./src/test/resources/outTest.txt"))).isTrue();

		Files.delete(Path.of("./src/test/resources/outTest.txt"));
	}

	@Test
	public void testCompareFileContents() throws IOException {

		// Lancementdu du programme
		List<String> fileToParse = extractFileService.readFileMap("./src/test/resources/in.txt");
		parseFile.generateGame(fileToParse);

		writeFileService.writeFinalFile("./src/test/resources/outTest.txt",
				InformationsToPrint.getInstance().toString());

		// Chemins des fichiers à comparer
		Path expected = Paths.get("./src/test/resources/expected.txt");
		Path actual = Paths.get("./src/test/resources/outTest.txt");

		// Lecture du contenu des fichiers
		List<String> fileExpected = Files.readAllLines(expected);
		List<String> fileActual = Files.readAllLines(actual);

		// Comparaison du contenu des fichiers ligne par ligne
		assertThat(fileExpected).isEqualTo(fileActual);
	}

	@AfterEach
	void cleanSingleton() {

		InformationsToPrint.getInstance().setAdventureMap(null);
		InformationsToPrint.getInstance().setMapInformation("");
		InformationsToPrint.getInstance().setMapModelization("null");
		InformationsToPrint.getInstance().getMountains().clear();
		InformationsToPrint.getInstance().getAventurersDto().clear();

	}

}
