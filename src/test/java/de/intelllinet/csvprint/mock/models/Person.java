package de.intelllinet.csvprint.mock.models;

import java.time.LocalDate;

public class Person {

	private int age;
	private String firstname;
	private String lastname;
	private boolean married;
	private LocalDate birthday;
	private double income;

	public Person(int age, String firstname, String lastname, boolean married, LocalDate birthday, double income) {
		this.age = age;
		this.firstname = firstname;
		this.lastname = lastname;
		this.married = married;
		this.birthday = birthday;
		this.income = income;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public boolean isMarried() {
		return married;
	}

	public void setMarried(boolean hasCar) {
		this.married = hasCar;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

}
