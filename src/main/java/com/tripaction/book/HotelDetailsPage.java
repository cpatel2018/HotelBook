package com.tripaction.book;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HotelDetailsPage extends BasePage {

	private ReservationPage reserve;

	public HotelDetailsPage(WebDriver driver) {
		super(driver);
		reserve = new ReservationPage(driver);
	}
	
	@FindBy(css = "div[class='hprt-reservation-cta'] button")
	public WebElement reserveButton;
	
	@FindBy(css = "div[class='hp__hotel-title'] h2[class='hp__hotel-name']")
	public WebElement hotelDetailPageText;
	
	public boolean hotelNameMatches(String hotelDetailPageTitle) {
    	switchWindow();
		boolean result = false;
		String hotelNameFound = hotelDetailPageText.getText();
		System.out.println("HotelDetailsPage : " + hotelNameFound);
		if(hotelNameFound != null && hotelNameFound.contains(hotelDetailPageTitle)) {
			result = true;
		}
		return result;
	} 
	public ReservationPage reserveCheapestHotel() {
		int cheapestPriceIndex = findCheapPriceIndex();
	    System.out.println("cheapindex:" + cheapestPriceIndex);
	    //select #of Room=1
        WebElement selectElement = driver.findElement(By.cssSelector("table[class='hprt-table  hprt-table-long-language '] tbody tr:nth-child(" + (cheapestPriceIndex + 1) + ") td select[class='hprt-nos-select']"));
        Select select = new Select(selectElement);
        select.selectByValue("1");   
        //Click Reserve button
        reserveButton.submit();
		return reserve;
	}

	private  int findCheapPriceIndex() {
    	switchWindow();
        WebDriverWait wait = new WebDriverWait(driver, 15);
        //By hotelPrice = By.cssSelector("table[class='hprt-table  hprt-table-long-language '] tbody tr[class*='hprt-table-last-row'] td:nth-child(3) div[class='hprt-price-price '] span[class*='hprt-price-price-standard']");
        By hotelPrice = By.cssSelector("table[class='hprt-table  hprt-table-long-language '] tbody tr[data-et-view] td span[class='hprt-price-price-standard ']");
        wait.until(ExpectedConditions.presenceOfElementLocated(hotelPrice));
    
        List<WebElement> elements = driver.findElements(hotelPrice);
        double cheapestPrice = 999999999;

        int cheapestPriceIndex = -1;

        for (int i = 0; i < elements.size(); i++) {
            WebElement element = elements.get(i);
            String priceStr = element.getText();
            System.out.println("priceStr:" + priceStr);
            priceStr = priceStr.replace("$", "");
            priceStr = priceStr.replace(",", "");
            double price = Double.parseDouble(priceStr);
           // System.out.println("price:" + price);
            if (price < cheapestPrice) {
                cheapestPrice = price;
                cheapestPriceIndex = i;
            }
        }
        return cheapestPriceIndex;
    }
	
	 private  void switchWindow() {
	        for(String winHandle : driver.getWindowHandles()){
	            driver.switchTo().window(winHandle);
	        }
	    }
}
