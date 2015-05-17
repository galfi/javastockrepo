package com.mta.javacourse.service;

import java.util.Date;
import java.util.Calendar;

import com.mta.javacourse.model.Portfolio;
import com.mta.javacourse.model.Stock;

public class PortfolioManager {
	
	public Portfolio getPortfolio(){
		Calendar cal = Calendar.getInstance();
		cal.set(2014, 11, 15);
		
		Date date1 = cal.getTime();
		Date date2 = cal.getTime();
		Date date3 = cal.getTime();
		
		//Stock1
		String portfolioTitle = "Exercise 7 portfolio";
		Stock stock1 = new Stock("PIH", 10.0f, 8.5f, date1);
		
		//Stock2
		Stock stock2 = new Stock("AAL", 30.0f, 25.5f, date2);
		
		//Stock3
		Stock stock3 = new Stock("CAAS", 20.0f, 15.5f, date3);
		
		Portfolio portfolio = new Portfolio(portfolioTitle);
		portfolio.updateBalance(10000);
		portfolio.buyStock(stock1,20);
		portfolio.buyStock(stock2,30);
		portfolio.buyStock(stock3,40);
		
		portfolio.sellStock("AAL", -1);
		portfolio.removeStock("CAAS");
				
		return portfolio;
	}

}
