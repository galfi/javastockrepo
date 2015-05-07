package com.mta.javacourse.service;

import java.util.Date;
import java.util.Calendar;

import com.mta.javacourse.model.Portfolio;
import com.mta.javacourse.model.Stock;

public class PortfolioManager {
	
	public Portfolio getPortfolio(){
		Calendar cal = Calendar.getInstance();
		cal.set(2014, 10, 15);
		
		Date date1 = cal.getTime();
		Date date2 = cal.getTime();
		Date date3 = cal.getTime();
		
		//Stock1
		String portfolioTitle = "Gal's Portfolio";
		Stock stock1 = new Stock("PIH", 13.1f, 12.4f, date1);
		
		//Stock2
		Stock stock2 = new Stock("ALL", 5.78f, 5.5f, date2);
		
		//Stock3
		Stock stock3 = new Stock("CAAS", 32.2f, 31.5f, date3);
		
		Portfolio portfolio = new Portfolio(portfolioTitle);
		
		portfolio.addStock(stock1);
		portfolio.addStock(stock2);
		portfolio.addStock(stock3);
		
		return portfolio;
	}

}
