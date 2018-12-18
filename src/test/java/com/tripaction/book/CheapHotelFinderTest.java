package com.tripaction.book;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.tripaction.book.BookingHomePage;
import com.tripaction.book.CheckoutDetailsPage;
import com.tripaction.book.HotelDetailsPage;
import com.tripaction.book.PaymentDetails;
import com.tripaction.book.ReservationPage;
import com.tripaction.book.SearchResultsPage;


/**
 * This class tests for following scenarios:
 *  1. Success Scenario : End-to-end booking flow
 *  2. Failure Scenario : Invalid Phone Number
 *  3. Failure Scenario : Invalid Destination
 *  
 *  The video screenshot showing the tests execution along with the TestNG Report is accessible at : 
 *  https://www.screencast.com/t/pnaQs0OcAN7
 *  
 *  Note : To make the test more comprehensive following tasks would need to be done as well:
 *  1. Make the Test Data Driven i.e. Move the input arguments (e.g. destination name, check-in date, etc.) to properties file or excel file
 *  2. The Date picker logic should dynamically computed so its based on today's date and future date.
 *  3. Take the screenshot on test failure to help diagnose the problem.
 *  4. Add more logging using log4j for easy diagnosing of the problem.
 *  5. Make the Driver implementation code more modular to support cross-browser testing via configuration changes 
 *  
 * @author Chhaya
 *
 */
public class CheapHotelFinderTest {
	private WebDriver driver;
	
	@BeforeMethod
	public void setup( ) {
	  System.setProperty("webdriver.chrome.driver", "/Users/admin/eclipse-workspace/HotelBook/drivers/chromedriver");
	  driver = new ChromeDriver();
	  driver.get("https://www.booking.com");
	  //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);		
	}
	
	@Test
	public void testBookHotelSuccess() {	
		
	  String city = "Los Angeles";
	  
	  BookingHomePage homePage = new BookingHomePage(driver);  
	  
	  SearchResultsPage searchResults = homePage.search(city);
	  assertTrue(searchResults.destinationMatches(city));
	  
	  HotelDetailsPage hotelDetails = searchResults.firstHotel();
	  assertTrue(hotelDetails.hotelNameMatches(searchResults.getSelectedHotelName()));

	  ReservationPage reserve = hotelDetails.reserveCheapestHotel();
	  assertTrue(reserve.reserveredHotelNameMatches(searchResults.getSelectedHotelName()));
	  
	  PaymentDetails paymentDetails = createPaymentDetails();
	  CheckoutDetailsPage checkout = reserve.enterUserDetails();
	  checkout.enterPaymentDetails(paymentDetails);
	  assertTrue(checkout.isPaymentDetailsValid());
	   
	}

	private PaymentDetails createPaymentDetails() {
		return new PaymentDetails("100 Oak Street", "San Francisco", "94404", "4151231234");
	}


	@Test
	public void testPhoneInvalid() {	
	  String city = "Los Angeles";
	  BookingHomePage homePage = new BookingHomePage(driver);  
	  
	  SearchResultsPage searchResults = homePage.search(city);
	  assertTrue(searchResults.destinationMatches(city));
	  
	  HotelDetailsPage hotelDetails = searchResults.firstHotel();
	  assertTrue(hotelDetails.hotelNameMatches(searchResults.getSelectedHotelName()));

	  ReservationPage reserve = hotelDetails.reserveCheapestHotel();
	  assertTrue(reserve.reserveredHotelNameMatches(searchResults.getSelectedHotelName()));
	  
	  PaymentDetails paymentDetails = createInvalidPhonePaymentDetails();
	  CheckoutDetailsPage checkout = reserve.enterUserDetails();
	  checkout.enterPaymentDetails(paymentDetails);
	  assertFalse(checkout.isPaymentDetailsValid());
	  
	}

	private PaymentDetails createInvalidPhonePaymentDetails() {
		return new PaymentDetails("100 Oak Street", "San Francisco", "94404", "");
	}

	@Test
	public void testDestnationInvalid() {	
	  String city = "$";
	  BookingHomePage homePage = new BookingHomePage(driver);  
	  
	  String errorMessage = homePage.invalidSearch(city);
	  
	  assertTrue(errorMessage.contains("Sorry, we don't recognize that name."));
	}

	@AfterMethod
	public void teardown() {
		if(driver != null) {
			driver.quit();
		}
	}
}
