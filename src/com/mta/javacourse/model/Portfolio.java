package com.mta.javacourse.model;

import org.algo.model.PortfolioInterface;
import org.algo.model.StockInterface;

import com.mta.javacourse.exception.BalanceException;
import com.mta.javacourse.exception.PortfolioFullException;
import com.mta.javacourse.exception.StockNotExistException;

public class Portfolio implements PortfolioInterface{
	
	private final static int MAX_PORTFOLIO_SIZE = 5;
	
	public static enum ALGO_RECOMMENDATION{
		BUY, SELL, REMOVE, HOLD
	};
	
	private float balance;
	private String title;
	private StockInterface[] stocks;
	private int portfolioSize;
	
	public Portfolio (){
		this.title = "New Portfolio";
		stocks = new StockInterface[MAX_PORTFOLIO_SIZE];
		portfolioSize = 0;
		this.balance = 0;
	}

	//this c'tor creates a portfolio object
	public Portfolio (String title){
		this();
		this.title = title;
	}
	
	//this c'tor creates a portfolio object
	//gets title of portfolio, logical size of portfolio stocks and the balance
	public Portfolio(String title, int portfolioSize, float balance){
		this.title = title;
		this.portfolioSize = portfolioSize;
		this.balance = balance;
		this.stocks = new Stock[MAX_PORTFOLIO_SIZE];
	}
	
	public Portfolio(Portfolio portfolio){
		this(new String (portfolio.getTitle()), portfolio.getPortfolioSize(), portfolio.getBalance());
		StockInterface[] copyied = portfolio.getStocks();
		for(int i = 0; i< this.portfolioSize; i++){
			this.stocks[i] = new Stock((Stock)copyied[i]);
		}
	}
	
	//this c'tor creates a portfolio object
	public Portfolio(Stock[] stockArray){
		this.portfolioSize = 0;
		this.balance = 0;
		this.stocks = new Stock[MAX_PORTFOLIO_SIZE];
		this.title = null;
		for(int i = 0; i< stockArray.length; i++){
			this.stocks[i] =  stockArray[i];
			this.portfolioSize++;
		}
	}
	
	//add stock to portfolio
	public void addStock(Stock stock) throws PortfolioFullException, BalanceException, StockNotExistException{
		if(stock == null){
			throw new StockNotExistException(balance);
		}
		else if (this.portfolioSize == MAX_PORTFOLIO_SIZE){
			throw new PortfolioFullException(MAX_PORTFOLIO_SIZE);
		}
		else{ 
			int index = findIndexBySymbol(stock.getSymbol());
			if (index==this.portfolioSize){ //if not exists
				this.stocks[portfolioSize] = stock;
				((Stock) this.stocks[this.portfolioSize]).setStockQuantity(0);
				this.portfolioSize++;
			}
			else {
				throw new BalanceException("message", 1);
			}
		}
	}
	
	//remove stock from portfolio
	public void removeStock(String stockSymbol) throws StockNotExistException{
		if(stockSymbol.equals(null)){
			throw new StockNotExistException("stock");
		}
		else{
			int index= findIndexBySymbol(stockSymbol);
			if(index < this.portfolioSize){//exist
				if(((Stock) this.stocks[index]).getStockQuantity() != 0 ){
					sellStock(stockSymbol, -1);
					this.stocks[index] = this.stocks[this.portfolioSize-1];
					this.stocks[this.portfolioSize-1] = null;
					this.portfolioSize--;
				}
				else{
					this.stocks[index] = this.stocks[this.portfolioSize-1];
					this.stocks[this.portfolioSize-1] = null;
					this.portfolioSize--;
				}
			}
			else{
				throw new StockNotExistException("stock");
			}
		}
	}
	
	//sell stock	
	public void sellStock(String stockSymbol, int amount) throws StockNotExistException{
		if(stockSymbol.equals(null)){
			throw new StockNotExistException("stock");
		}
		else if(amount == 0 || amount < -1){
			throw new StockNotExistException("stock");
		}
		else{
			int index= findIndexBySymbol(stockSymbol);
			if(index == this.portfolioSize){
				throw new StockNotExistException();
			}
			else if(((Stock) this.stocks[index]).getStockQuantity() == 0){
				throw new StockNotExistException(index);
			}
			else if(amount > ((Stock) this.stocks[index]).getStockQuantity()){
				throw new StockNotExistException(amount);
			}
			else if (amount == -1){
				this.balance =this.balance + (((Stock) this.stocks[index]).getStockQuantity()* this.stocks[index].getBid());
				((Stock) this.stocks[index]).setStockQuantity(0);
			}
			else{
				int stockQuantity = ((Stock) this.stocks[index]).getStockQuantity();
				this.balance =this.balance + (stockQuantity* this.stocks[index].getBid());
				((Stock) this.stocks[index]).setStockQuantity(stockQuantity - amount);
			}
		}
	}
	
	//buy stock
	public void buyStock (Stock stock, int quantity) throws BalanceException, PortfolioFullException, StockNotExistException{
		if(stock == null ){
			throw new StockNotExistException();
		}
		else if(quantity == 0 || quantity < -1){
			throw new BalanceException(MAX_PORTFOLIO_SIZE);
		}
		else if (this.balance == 0){
			throw new BalanceException("message");
		}
		else{
			int index = findIndexBySymbol(stock.symbol);
			if(index == this.portfolioSize){
				addStock(stock);
				if(quantity == -1){
					int quantityOfStock = (int)(this.balance / stock.getAsk());
					if(quantityOfStock > 0){
						float tempBalnce = quantityOfStock*stock.getAsk();
						if(tempBalnce <= this.balance){
							updateBalance(-tempBalnce);
							stock.setStockQuantity(((Stock) this.stocks[index]).getStockQuantity() + quantityOfStock);
						}
						else{
							throw new BalanceException("message");
						}
					}
				}
				else{
					float tempCountOfSell = quantity * stock.getAsk();
					if(tempCountOfSell <= this.balance){
						updateBalance(-tempCountOfSell);
						stock.setStockQuantity(((Stock) this.stocks[index]).getStockQuantity() + quantity);
					}
					else{
						throw new BalanceException("message");
					}
				}
			}
			else{
				if(quantity == -1){
					int quantityOfStock = (int)(this.balance / stock.getAsk());
					if(quantityOfStock > 0){
						float tempBalnce = quantityOfStock * stock.getAsk();
						if(tempBalnce <= this.balance){
							updateBalance(-tempBalnce);
							stock.setStockQuantity(((Stock) this.stocks[index]).getStockQuantity() + quantityOfStock);
						}
						else{
							throw new BalanceException("message");
						}
					}
				}
				else{
					float tempCountOfSell = quantity * stock.getAsk();
					if(tempCountOfSell <= this.balance){
						updateBalance(-tempCountOfSell);
						stock.setStockQuantity(((Stock) this.stocks[index]).getStockQuantity() + quantity);
					}
					else{
						throw new BalanceException("message");
					}
				}
			}
		}
	}
	
	public float getBalance(){
		return balance;
	}
	
	//get stocks value
	public float getStocksValue(){
		float totalSum = 0;
		for(int i=0; i<this.portfolioSize; i++){
			totalSum += (((Stock) this.stocks[i]).getStockQuantity() * this.stocks[i].getBid());
		}
		return totalSum;
	}
	
	//get portfolio value
	public float getPortfolioValue(){
		return getBalance()+getStocksValue();
	}
	
	//get total value
	public float getTotalValue(){
		float totalValueOfStocks = getStockValue() + getBalance();
		return totalValueOfStocks;
	}
	
	//display total value of all stocks
	public float getStockValue(){
		float totalValueOfStocks = 0;
		for(int i = 0; i < this.portfolioSize; i++){
			totalValueOfStocks = totalValueOfStocks + (((Stock) this.stocks[i]).getStockQuantity() * this.stocks[i].getBid());
		}
		return totalValueOfStocks;
	}
	
	//changes stock's bid
	public void changeStockBid(String stockSymbol, float newBid) throws StockNotExistException{
		if(stockSymbol.equals(null)){
			throw new StockNotExistException(stockSymbol);
		}
		else{
			int index = findIndexBySymbol(stockSymbol);
			if(index == this.portfolioSize){
				throw new StockNotExistException(stockSymbol);
			}
			else{
				((Stock) this.stocks[index]).setBid(newBid);
			}
		}
	}
	
	//gets stock symbol and finds its index in the array
	private int findIndexBySymbol(String stockSymbol) throws StockNotExistException{
		int index = -2;
		if(stockSymbol.equals(null)){
			throw new StockNotExistException(stockSymbol);
		}
		else if(this.portfolioSize == 0){
			return 0;
		}
		else{
			for(index = 0; index< this.portfolioSize; index++){
				if(this.stocks[index].getSymbol().equals(stockSymbol)){
					break;
				}
			}
		}
		return index;
	}
	
	//find stock in portfolio
	public Stock findStock(String stockSymbol) throws StockNotExistException{
		int index = 0;
		if(stockSymbol.equals(null)){
			throw new StockNotExistException("message");
		}
		else if(this.portfolioSize == 0){
			return null;
		}
		else{
			for(index = 0; index < this.portfolioSize; index++){
				if(this.stocks[index].getSymbol().equals(stockSymbol)){
					break;
				}
			}
		}
		return (Stock) stocks[index];
	}
	
	//updating balance
	public void updateBalance(float amount) throws BalanceException{
		float tempSumOfBalnce= this.balance + (amount);
		if(tempSumOfBalnce >= 0){
			this.balance = tempSumOfBalnce;
			System.out.print("blanace has been updated");
		}
		else{
			throw new BalanceException();
		}
	}
	
	// returns the max size of the stocks array
	public static int getMaxSize(){
		return MAX_PORTFOLIO_SIZE;
	}
	
	//methods
	public String getHtmlString(){
		String portfolioString = new String("<h1>" + getTitle() + "</h1>"+"<br>"+ "<b>Total poertfolio value</b>: "+getTotalValue()+"$"+ ", "+"<b>Total stock value</b>: "+getStockValue()+"$"+ ", "+"<b>Balance</b>: "+getBalance()+"$" + "<br>");
		for(int i = 0;  i < portfolioSize; i++){
			portfolioString = portfolioString + ((Stock) this.stocks[i]).getHtmlDescription() + "<br>";
		}
		return portfolioString;
	}
	
	//getters and setters
	public StockInterface[] getStocks(){
		return stocks;
	}
	
	public void setStocks(Stock[] stocks){
		this.stocks = stocks;
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
	
	public void setPortfolioSize(int PortfolioSize){
		this.portfolioSize = PortfolioSize;
	}
}