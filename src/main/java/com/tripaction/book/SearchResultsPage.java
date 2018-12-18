package com.tripaction.book;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchResultsPage extends BasePage {
	
	private HotelDetailsPage hotelDetails;
	private String selectedHotelName;

	@FindBy(css = "span[class='close_inspire_filter_block']")
	public WebElement filterOverlayClose;
	
	@FindBy(css = "div[id='hotellist_inner'] div:nth-child(1) div div div a[class='hotel_name_link url']")
	public WebElement firstHotelUrl;
	
	@FindBy(css = "div[id='inspire_filter_block']")
	public WebElement filterOverlay;

	@FindBy(id = "ss")
	public WebElement destinationText ;

	@FindBy(css = "div[id='hotellist_inner'] div:nth-child(1) div div div a[class='hotel_name_link url'] span[class*='sr-hotel__name']")
	public WebElement firstHotelTitle;

	public SearchResultsPage(WebDriver driver) {
		super(driver);
		hotelDetails = new HotelDetailsPage(driver);
	}

	public HotelDetailsPage firstHotel() {		
        handleOcassionalOverlay();
		return hotelDetails;
	}
	
	public boolean destinationMatches(String destination) {
		boolean result = false;
		if(destination.contains(destinationText.getText())) {
			result = true;
		}
		return result;
	}
	
	private void handleOcassionalOverlay() {
		try {
			selectedHotelName = firstHotelTitle.getText();
			firstHotelUrl.click();
		}
		catch(WebDriverException e) {
			if(e.getMessage().contains("</a> is not clickable at point")) {
				closeOverlay();
				WebDriverWait wait1 = new WebDriverWait(driver, 15);
				wait1.until(ExpectedConditions.elementToBeClickable(firstHotelUrl));
				firstHotelUrl.click();	
			}
		}
	}
	
	private void closeOverlay() {
	        WebDriverWait wait1 = new WebDriverWait(driver, 15);
	        By filterOverlayBy = By.cssSelector("div[id='inspire_filter_block']");
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(filterOverlayBy));
	        filterOverlayClose.click();
	    }

	public String getSelectedHotelName() {
		System.out.println(selectedHotelName);
		return selectedHotelName;
	}

	
}


