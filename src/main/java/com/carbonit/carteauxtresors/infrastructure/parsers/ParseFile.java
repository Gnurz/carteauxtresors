package com.carbonit.carteauxtresors.infrastructure.parsers;

import java.util.List;

import com.carbonit.carteauxtresors.domain.map.AdventureMap;

public interface ParseFile {
	
	void generateGame(List<String> file);

}
