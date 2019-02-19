package de.intelllinet.csvprint.mock.models;

/**
 * @author Arthur Schneider
 *
 */
public class Adress {

	private String zip;
	private String city;
	private String street;
	private String houseNumber;

	public Adress() {
	}

	public Adress(String zip, String city, String street, String houseNumber) {
		this.zip = zip;
		this.city = city;
		this.street = street;
		this.houseNumber = houseNumber;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

}
