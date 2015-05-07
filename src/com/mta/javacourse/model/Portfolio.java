package com.mta.javacourse.model;

import java.util.Date;

public class Portfolio {
	
	private final static int MAX_PROTFOLIO_SIZE = 5;
	
	private String title;
	private Stock[] stocks;
	private int portfolioSize;

	public Portfolio (String title){
		this.title = title;
		stocks = new Stock[MAX_PROTFOLIO_SIZE];
		portfolioSize = 0;
	}
	
	//copy portfolio
	public Portfolio (Portfolio copyPortfolio){
		this(copyPortfolio.getTitle());
			
		for (int i=0 ; i<copyPortfolio.getPortfolioSize() ; i++){
			this.addStock(new Stock(copyPortfolio.getStockByIndex(i)));
		}
	}
	
	//add stock to portfolio
	public void addStock(Stock stocks){
		if (stocks !=null && portfolioSize < MAX_PROTFOLIO_SIZE){
			this.stocks[portfolioSize] = stocks;
			portfolioSize++;
		}
	}
	
	//remove stock from portfolio
	public void removeStock(String eraseSymbol){
		if (stocks[portfolioSize-1].getSymbol().equals(eraseSymbol)){
			stocks[portfolioSize-1] = null;
			portfolioSize--;
		}
		else{
			for (int i=0 ; i<portfolioSize ; i++){
				if (this.stocks[i].getSymbol().equals(eraseSymbol)){
					this.stocks[i] = this.stocks[portfolioSize-1];
					this.stocks[portfolioSize-1] = null;
					portfolioSize--;
				}
			}
		}
	}
	
	//get stock by index
	public Stock getStockByIndex(int index) {
		if (index < 0 || index >= portfolioSize) {
			return null;
		}
		return stocks[index];
	}
	
	//methods
	public String getHtmlString(){
		
		String ret = "<h1>" + getTitle() + "</h1>";
		for (int i = 0; i < portfolioSize; i++) {
			ret += stocks[i].getHtmlDescription() + "<br>";
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
	
	public int getPortfolioSize() {
		return portfolioSize;
	}

}
