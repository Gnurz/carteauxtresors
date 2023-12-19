package com.carbonit.carteauxtresors.domain.adventurers;

public enum Orientation {
	NORTH("N"), SOUTH("S"), EST("E"), WEST("O");

	private String initial;

	Orientation(String firstLetter) {
		this.setInitial(firstLetter);
	}

	public String getInitial() {
		return initial;
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}
}
