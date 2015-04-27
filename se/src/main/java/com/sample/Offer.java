package com.sample;

public class Offer {
	private String country;
	private String town;
	private String hotel;
	private double price;
	
	public Offer (String country, String town, String hotel, double price) {
		this.country = country;
		this.town = town;
		this.hotel = hotel;
		this.price = price;
	}
	
	public String getCountry() {
		return this.country;
	}
	
	public String getTown() {
		return this.town;
	}
	
	public String getHotel() {
		return this.hotel;
	}
	
	public double getPrice() {
		return this.price;
	}
}
