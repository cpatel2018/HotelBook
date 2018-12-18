package com.tripaction.book;

public class PaymentDetails {
	private String address1;
	private String city;
	private String zipcode;
	private String phoneNumber;
	
	public PaymentDetails(String address1, String city, String zipcode, String phoneNumber) {
		super();
		this.address1 = address1;
		this.city = city;
		this.zipcode = zipcode;
		this.phoneNumber = phoneNumber;
	}
	
	public String getAddress1() {
		return address1;
	}
	public String getCity() {
		return city;
	}
	public String getZipcode() {
		return zipcode;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
}
