package com.carbonit.carteauxtresors.infrastructure.printer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.springframework.stereotype.Service;

@Service
public class WriteFileService {

	public void writeFinalFile(String fileOut, String informationToWrite) {

		Path pathOut = Paths.get(fileOut);
		try (BufferedWriter bw = Files.newBufferedWriter(pathOut, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE)) {

			bw.write(informationToWrite);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
