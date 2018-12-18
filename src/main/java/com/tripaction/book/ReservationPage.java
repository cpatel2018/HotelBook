package com.tripaction.book;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ReservationPage extends BasePage {

	private CheckoutDetailsPage checkout;

	public ReservationPage(WebDriver driver) {
		super(driver);
		checkout = new CheckoutDetailsPage(driver);
	}
	
	@FindBy(css = "input[id='lastname']")
	public WebElement lastName;
	
	@FindBy(css = "input[id='email']")
	public WebElement email;
	
	@FindBy(css = "input[id='email_confirm']")
	public WebElement confirmEmail;
	
	@FindBy(css = "button[name='book']")
	public WebElement bookButton;

	@FindBy(css = "span[class='bp_pricedetails_room_price']")
	public WebElement price;
	
	@FindBy(css = "div[class='bp_hotel_details'] h1[class='bp_hotel_name_title']")
	public WebElement reserveredHotelText;
	

	public boolean reserveredHotelNameMatches(String reserveredHotel) {
		boolean result = false;
		if(reserveredHotel.contains(reserveredHotelText.getText())) {
			result = true;
		}
		return result;
	} 
	
	public CheckoutDetailsPage enterUserDetails() {
		System.out.println("Selected Price "+price.getText());	
		
		lastName.sendKeys("Smith");
		email.sendKeys("testbooking@yopmail.com");
		confirmEmail.sendKeys("testbooking@yopmail.com");
		bookButton.submit();
		return checkout;
		
	}
	
}
