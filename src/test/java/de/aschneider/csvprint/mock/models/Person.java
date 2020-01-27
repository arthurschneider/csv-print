package de.aschneider.csvprint.mock.models;

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

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public boolean isMarried() {
		return married;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public double getIncome() {
		return income;
	}

}
