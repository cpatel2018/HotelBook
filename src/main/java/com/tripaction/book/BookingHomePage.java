package com.tripaction.book;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BookingHomePage extends BasePage {
	
	SearchResultsPage searchResults;
	
	public BookingHomePage(WebDriver driver) {
		super(driver);
		searchResults = new SearchResultsPage(driver);
	}
	
	@FindBy(name = "ss")
	public WebElement destination;
	
	@FindBy(xpath = "//div[contains(@class,'xp__dates-inner xp__dates__checkin')]//button[contains(@type,'button')]")
	public WebElement checkIn;

	@FindBy(xpath = "//table[@class='bui-calendar__dates']//tr[7]//td[2]")
	public WebElement checkInDate;
	
	@FindBy(xpath = "//div[contains(@class,'bui-calendar__content')]//div[2]//table[1]//tbody[1]//tr[2]//td[3]")
	public WebElement checkoutDate;
	
	@FindBy(xpath = "//span[contains(text(),'2 adults')]")
	public WebElement noOfAdults;
	
	@FindBy(xpath = "//div[contains(@class,'sb-searchbox-submit-col -submit-button')]//button[@type='submit']")
	public WebElement searchButton;

	@FindBy(className = "header-signin-prompt")
	public WebElement signinPopup;
	
	@FindBy(css = "div[class='sb-searchbox__error -visible']")
	public WebElement invalidSearchMsgLabel;
	
	public SearchResultsPage search(String city) {
		closeSignInPopup(driver);
        destination.sendKeys(city);
        checkIn.click();
        checkInDate.click();
        checkoutDate.click();
        noOfAdults.click();
        searchButton.click();
		return searchResults;		
	}

	public String invalidSearch(String city) {
		String result = null;
		closeSignInPopup(driver);
        destination.sendKeys(city);
        searchButton.click();
        if(invalidSearchMsgLabel != null) {
        	result = invalidSearchMsgLabel.getText();
        }
		return result;		
	}
	
	private  void closeSignInPopup(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(signinPopup));
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ESCAPE).build().perform();
    }

}
