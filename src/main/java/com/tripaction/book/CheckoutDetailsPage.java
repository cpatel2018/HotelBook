package com.tripaction.book;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutDetailsPage extends BasePage{
	
	public CheckoutDetailsPage(WebDriver driver)  {
		super(driver);
	}
	    
	@FindBy(css = "input[name='address1']")
	public WebElement address1;
	
	@FindBy(css = "input[name='city']")
	public WebElement city;
	
	@FindBy(css = "input[name='zip']")
	public WebElement zipcode;
	
	@FindBy(css = "input[id='phone']")
	public WebElement phone;

	@FindBy(css ="div[class='bp-overview-buttons-wrapper'] span[class='bp_submit_button__copy']")
	public WebElement completeBookingButton;
	
//	@FindBy(css = "p[id='bp_form_cc_number_msg']")
//	public WebElement ccNumberMsg;

	@FindBy(css = "div[class='bp_hotel_details'] h1[class='bp_hotel_name_title']")
	public WebElement checkoutPageHotelText;
	
	@FindBy(css ="p[id='bp_form_phone_msg']")
	public WebElement phoneErrorMsg;
	
	public boolean reserveredHotelTextMatches(String checkoutPageHotelTitle) {
		boolean result = false;
		if(checkoutPageHotelTitle.equals(checkoutPageHotelText.getText())) {
			result = true;
		}
		return result;
	}
	
	public void enterPaymentDetails(PaymentDetails paymentDetails)  {
		try {
		address1.sendKeys(paymentDetails.getAddress1());
		city.sendKeys(paymentDetails.getCity());
		zipcode.sendKeys(paymentDetails.getZipcode());
		}
		catch(NoSuchElementException e) {
			System.out.println("Address Web Elements are not part of the Check out page, so ignore those elements" + e.getMessage());
			//e.printStackTrace();
		}
		
		phone.sendKeys(paymentDetails.getPhoneNumber());
		completeBookingButton.submit();
		 WebDriverWait wait1 = new WebDriverWait(driver, 15);
	     By ccNumberMsg = By.cssSelector("p[id='bp_form_cc_number_msg']");
	     wait1.until(ExpectedConditions.visibilityOfElementLocated(ccNumberMsg));
		
	}
	
	public boolean isPaymentDetailsValid() {
		boolean valid = true;
		String phoneNumberErrorMessageText = "Please enter a valid phone number";
		if(phoneErrorMsg != null) {
			System.out.println(phoneErrorMsg.getText());
			if(phoneNumberErrorMessageText.equals(phoneErrorMsg.getText())) {
				valid = false;
			}	
		}
		return valid;
	}
	
}
