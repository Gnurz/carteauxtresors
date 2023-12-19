package com.carbonit.carteauxtresors.domain.map;

import java.util.Map;

public record AdventureMap(int nbCaseHorizontale, int nbCaseVerticale, Map<String, Square> squares) {
	
}
