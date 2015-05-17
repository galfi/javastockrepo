package com.mta.javacourse.model;

import java.util.Date;

public class Portfolio {
	
	private final static int MAX_PROTFOLIO_SIZE = 5;
	
	public static enum ALGO_RECOMMENDATION{
		BUY, SELL, REMOVE, HOLD
	};
	
	private float balance;
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
	public void addStock(Stock stock){
		if (stocks !=null && portfolioSize < MAX_PROTFOLIO_SIZE){ //check if there is enugh room in portfolio
			int index = getStockIndex(stock.getSymbol());
			if (index==-1){
				this.stocks[portfolioSize] = stock;
				this.stocks[portfolioSize].setStockQuantity(0);
				portfolioSize++;
			}
			else {
				//Stock exists
			}
		}
	}
	
	//remove stock from portfolio
	public boolean removeStock(String eraseSymbol){
		boolean result=false;
		int stockIndex = getStockIndex(eraseSymbol);
		if (stockIndex == -1) {
			//Stock does not exist
		}
		else {
			sellStock(eraseSymbol,-1);
			stocks[stockIndex] = stocks[portfolioSize-1];
			stocks[portfolioSize-1] = null;
			portfolioSize--;
			result = true;
		}
		return result;
	}
	
	//sell stock	
	public boolean sellStock(String stockSymbol, int amount){
		boolean result= false;
		int stockIndex= getStockIndex(stockSymbol);
		if (stockIndex ==-1 || amount < -1){
			//Stock does not exists or illegal sell count
		}
		else {
			Stock tempStock=stocks[stockIndex];
			if (amount == -1){
				amount= tempStock.getStockQuantity();
			}
			if (amount > tempStock.getStockQuantity()) {
				System.out.println("not enough stocks to sell");
			}
			else {
				updateBalance(tempStock.getBid()*amount);
				tempStock.setStockQuantity(tempStock.getStockQuantity()-amount);
				result = true;
			}
		}
		return result;
	}
	
	//buy stock
	public boolean buyStock(Stock stock,int quantity){
		boolean result = false;
		int stockIndex = getStockIndex(stock.getSymbol());
		if (quantity < -1){
			//illegal stock count
		}
		else {
			if (quantity == -1) {
				quantity = (int)(balance / stock.getAsk());
			}
			if (stockIndex == -1) {
				addStock(stock);
				stockIndex = getStockIndex(stock.getSymbol());
			}
			if ((stock.getAsk()*quantity) <= balance) {
				updateBalance(stock.getAsk()*quantity*-1);
				stocks[stockIndex].setStockQuantity(quantity);
				result = true;
			}
			else {
				System.out.println("Not enough balance to complete purchase");
			}
		}
		return result;
	}
	
	public float getBalance(){
		return balance;
	}
	
	//get stocks value
	public float getStocksValue(){
		float totalSum = 0;
		for(int i=0; i<portfolioSize; i++){
			Stock tempStock = stocks[i];
			totalSum += tempStock.getBid()*tempStock.getStockQuantity(); 
		}
		return totalSum;
	}
	
	//get portfolio value
	public float getPortfolioValue(){
		return getBalance()+getStocksValue();
	}
	
	//get stock by index
	public Stock getStockByIndex(int index) {
		if (index < 0 || index >= portfolioSize) {
			return null;
		}
		return stocks[index];
	}
	
	//get stock's index
	public int getStockIndex(String symbol){
		int result=-1;
		for(int i=0; i<portfolioSize; i++){
			Stock tempStock = stocks[i];
			if (tempStock.getSymbol().equals(symbol)){
				result=i;
				break;
			}
		}
		return result;
	}
	
	//updating balance
	public void updateBalance(float amount){
		if(balance+amount<0){
			System.out.println("Balance is too low");
		}else {
			balance+=amount;
		}
	}
	
	//methods
	public String getHtmlString(){
		
		String ret = "<h1>" + getTitle() + "</h1>";
		ret += "<h2> Total Portfolio Value:" + getPortfolioValue() + ", Total Stocks value: " 
				+ getStocksValue() + ", Balance: " + getBalance() + "</h2>";
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
