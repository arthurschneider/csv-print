package de.aschneider.csvprint.mock.models;

import java.time.LocalDate;

public class Family {

	private Person father;
	private Person mother;
	private boolean livingTogether;
	private LocalDate livingTogetherSince;

	public Family(Person father, Person mother, boolean livingTogether, LocalDate livingTogetherSince) {
		this.father = father;
		this.mother = mother;
		this.livingTogether = livingTogether;
		this.livingTogetherSince = livingTogetherSince;
	}

	public Person getFather() {
		return father;
	}

	public void setFather(Person father) {
		this.father = father;
	}

	public Person getMother() {
		return mother;
	}

	public void setMother(Person mother) {
		this.mother = mother;
	}

	public boolean getLivingTogether() {
		return livingTogether;
	}

	public void setLivingTogether(boolean livingTogether) {
		this.livingTogether = livingTogether;
	}

	public LocalDate getLivingTogetherSince() {
		return livingTogetherSince;
	}

	public void setLivingTogetherSince(LocalDate livingTogetherSince) {
		this.livingTogetherSince = livingTogetherSince;
	}

}
