package com.mta.javacourse.model;

import com.mta.javacourse.Stock;

public class Portfolio {
	
	private final static int MAX_PROTFOLIO_SIZE = 5;
	
	private String title;
	private Stock[] stocks;
	private int protfolioSize = 0;
	
	public Portfolio(){
		stocks = new Stock[MAX_PROTFOLIO_SIZE];
	}
	
	public void addStock(Stock stocks){
		if (stocks != null && protfolioSize < MAX_PROTFOLIO_SIZE)
		{
			this.stocks[protfolioSize] = stocks;
			protfolioSize++;
		}
		else{
			System.out.println("Protfolio is full OR stock is null");
		}
	}
	
	public String getHtmlString(){
		
		String ret = new String ("<h1>" + getTitle() + "</h1>");
		
		for (int i=0 ; i<protfolioSize; i++){
			ret += this.stocks[i].getHtmlDescription() + "<br>";
		}
		
		return ret;
	}
	
	public Stock[] getStocks(){
		return stocks;
	}
	
	public void setStocks(Stock[] stock){
		this.stocks = stock;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}

}
