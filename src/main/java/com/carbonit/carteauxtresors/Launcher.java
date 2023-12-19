package com.carbonit.carteauxtresors;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.carbonit.carteauxtresors.infrastructure.parsers.ExtractFileService;
import com.carbonit.carteauxtresors.infrastructure.parsers.ParseFile;
import com.carbonit.carteauxtresors.infrastructure.printer.InformationsToPrint;
import com.carbonit.carteauxtresors.infrastructure.printer.WriteFileService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class Launcher implements CommandLineRunner {

	private final ExtractFileService extractFileService;
	private final ParseFile parseFile;
	private final WriteFileService writeFileService;

	@Override
	public void run(String... args) throws Exception {

		log.info("Debut du programme");
		if (args.length == 2) {

			if (StringUtils.isNotBlank(args[0]) && verifyInputFilePath(args[0])) {

				log.info("Lecture du fichier d'entrée");

				List<String> fileToParse = extractFileService.readFileMap(args[0]);

				log.info("Génération de la carte");
				parseFile.generateGame(fileToParse);

				log.info("Impression du fichier de sortie à l'emplacement " + args[1]);
				writeFileService.writeFinalFile(args[1], InformationsToPrint.getInstance().toString());
				log.info("Le fichier a été généré");
			} else {
				log.error("Le fichier d'entrée n'existe pas ou l'argument renseigné est vide");
			}
		} else {
			log.error(
					"Le nombre d'argument attendu est de 2. \n 1. le chemin du fichier d'entrée \n 2. Le chemin du fichier de sortie");
		}
		log.info("Fin programme");
		System.exit(0);
	}

	private boolean verifyInputFilePath(String path) {

		return Files.exists(Paths.get(path));

	}

}
