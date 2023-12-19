package com.carbonit.carteauxtresors.infrastructure.DTO;

import com.carbonit.carteauxtresors.domain.adventurers.Adventurer;

public class MapperDTO {
	
	
	  public static AdventurerDTO fromAdventurer(Adventurer adventurer) {
	        return new AdventurerDTO(
	                adventurer.nom(),
	                adventurer.initialCoordinate(),
	                adventurer.treasure(),
	                adventurer.orientation()
	        );
	    }
	  

}
