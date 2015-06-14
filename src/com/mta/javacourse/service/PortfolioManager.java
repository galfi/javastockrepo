package com.mta.javacourse.service;

import java.util.Date;

import org.algo.dto.PortfolioTotalStatus;
import org.algo.exception.PortfolioException;
import org.algo.model.PortfolioInterface;
import org.algo.service.PortfolioManagerInterface;
import org.algo.dto.PortfolioDto;
import org.algo.dto.StockDto;
import org.algo.exception.SymbolNotFoundInNasdaq;
import org.algo.model.StockInterface;
import org.algo.service.DatastoreService;
import org.algo.service.MarketService;
import org.algo.service.PortfolioManagerInterface;
import org.algo.service.ServiceManager;

import com.mta.javacourse.exception.BalanceException;
import com.mta.javacourse.exception.PortfolioFullException;
import com.mta.javacourse.exception.StockNotExistException;
import com.mta.javacourse.model.Portfolio;
import com.mta.javacourse.model.Stock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortfolioManager implements PortfolioManagerInterface{
	private DatastoreService datastoreService = ServiceManager.datastoreService();
	
	@Override
	public PortfolioInterface getPortfolio(){
		PortfolioDto portfolioDto =  datastoreService.getPortfolilo();
		return fromDto(portfolioDto);
	}

	public void update(){
		StockInterface[] stocks = getPortfolio().getStocks();
		List<String> symbols = new ArrayList<>(Portfolio.getMaxSize());
		for (StockInterface si : stocks){
			symbols.add(si.getSymbol());
		}
		List<Stock> update = new ArrayList<>(Portfolio.getMaxSize());
		List<Stock> currentStocksList = new ArrayList<Stock>();
		try{
			List<StockDto> stocksList = MarketService.getInstance().getStocks(symbols);
			for (StockDto stockDto : stocksList){
				Stock stock = fromDto(stockDto);
				currentStocksList.add(stock);
			}
			for (Stock stock : currentStocksList){
				update.add(new Stock(stock));
			}
			datastoreService.saveToDataStore(toDtoList(update));
		}
		catch (SymbolNotFoundInNasdaq e){
			System.out.println(e.getMessage());
		}
	}
		
	//get portfolio totals
	@Override
	public PortfolioTotalStatus[] getPortfolioTotalStatus(){
		Portfolio portfolio = (Portfolio) getPortfolio();
		Map<Date, Float> map = new HashMap<>();
		
		StockInterface[] stocks = portfolio.getStocks();
		for (int i = 0; i < stocks.length; i++){
			StockInterface stock = stocks[i];
			
			if(stock != null){
				List<StockDto> stockHistory = null;
				try{
					stockHistory =  datastoreService.getStockHistory(stock.getSymbol(),30);
				}
				catch (Exception e){
					return null;
				}
				for (StockDto stockDto : stockHistory){
					Stock stockStatus = fromDto(stockDto);
					float value = stockStatus.getBid()*stockStatus.getStockQuantity();
					Date date = stockStatus.getDate();
					Float total = map.get(date);
					
					if(total == null) {
						total = value;
					}
					else{
						total += value;
					}
					
					map.put(date, value);
				}
			}
		}
		
		PortfolioTotalStatus[] ret = new PortfolioTotalStatus[map.size()];
		
		int index = 0;
		for (Date date : map.keySet()){
			ret[index] = new PortfolioTotalStatus(date, map.get(date));
			index++;
		}
		
		Arrays.sort(ret);
		return ret;
	}
		
	//add stock to portfolio
	public void addStock(String symbol) throws PortfolioFullException, BalanceException, StockNotExistException{
		Portfolio portfolio = (Portfolio) getPortfolio();
		try{
			StockDto stockDto = ServiceManager.marketService().getStock(symbol);
			
			//get current symbol values from nasdaq
			Stock stock = fromDto(stockDto);
			
			//first thing, add it to portfolio
			try{
				portfolio.addStock(stock);
			}
			catch (PortfolioFullException e){
				System.out.println(e.getMessage());
				throw e;
			}
			System.out.println("Stock added");
			
			//second thing, save the new stock to the database
			datastoreService.saveStock(toDto(portfolio.findStock(symbol)));
			
			flush(portfolio);
			}
			
			catch (SymbolNotFoundInNasdaq e){
				System.out.println("Stock Not Exists: "+symbol);
			}
		}
		
		//set title
		public void setTitle(String title){
			Portfolio portfolio = (Portfolio) getPortfolio();
			portfolio.setTitle(title);
			flush(portfolio);
		}
		
		//update balance
		public void updateBalance(float value) throws BalanceException{
			Portfolio portfolio = (Portfolio) getPortfolio();
			try{
			portfolio.updateBalance(value);
			}
			catch(BalanceException e) {
				System.out.println(e.getMessage());
				throw e;
			}
			flush(portfolio);
		}
		
		//buy stock
		public void buyStock(String symbol, int quantity) throws PortfolioException,BalanceException, PortfolioFullException, StockNotExistException {
			try{
				Portfolio portfolio = (Portfolio) getPortfolio();
				Stock stock = (Stock) portfolio.findStock(symbol);
				
				if(stock == null){
					stock = fromDto(ServiceManager.marketService().getStock(symbol));
				}
				
				portfolio.buyStock(stock, quantity);
				flush(portfolio);
				System.out.println("Purchase Succeeeded");
			}
			catch(BalanceException e) {
				System.out.println(e.getMessage());
				throw e;
			}
			catch(PortfolioFullException e) {
				System.out.println(e.getMessage());
				throw e;
			}
			catch(StockNotExistException e) {
				System.out.println(e.getMessage());
				throw e;
			}
			catch (Exception e){
				System.out.println("Exception: "+e);
			}
		}
		
		//Sell stock
		public void sellStock(String symbol, int quantity) throws PortfolioException, StockNotExistException{
			Portfolio portfolio = (Portfolio) getPortfolio();
			portfolio.sellStock(symbol, quantity);
			flush(portfolio);
			System.out.println("Stock sold");
		}
		
		//remove stock
		public void removeStock(String symbol) throws StockNotExistException{
			Portfolio portfolio = (Portfolio) getPortfolio();
			try{
				portfolio.removeStock(symbol);
			}
			catch(StockNotExistException e){
				System.out.println(e.getMessage());
				throw e;
			}
			flush(portfolio);
			System.out.println("Stock removed");
		}
		
		//update database with new portfolio's data
		private void flush(Portfolio portfolio){
			datastoreService.updatePortfolio(toDto(portfolio));
		}
		
		//get stock from Data Transfer Object
		private Stock fromDto(StockDto stockDto){
			Stock newStock = new Stock();
			newStock.setSymbol(stockDto.getSymbol());
			newStock.setAsk(stockDto.getAsk());
			newStock.setBid(stockDto.getBid());
			newStock.setDate(stockDto.getDate().getTime());
			newStock.setStockQuantity(stockDto.getQuantity());
			
			if(stockDto.getRecommendation() != null){
				newStock.setRecommendation(Portfolio.ALGO_RECOMMENDATION.valueOf(stockDto.getRecommendation()));
			}
			else{
				newStock.setRecommendation(Portfolio.ALGO_RECOMMENDATION.valueOf("HOLD"));
			}
			return newStock;
		}
		
		//covert Stock to Stock DTO
		private StockDto toDto(StockInterface inStock){
			if (inStock == null){
				return null;
			}
			
			Stock stock = (Stock) inStock;
			return new StockDto(stock.getSymbol(), stock.getAsk(), stock.getBid(),
						stock.getDate(), stock.getStockQuantity(), stock.getRecommendation().name());
		}
		
		private PortfolioDto toDto(Portfolio portfolio){
			StockDto[] array = null;
			StockInterface[] stocks = portfolio.getStocks();
			if(stocks != null){
				array = new StockDto[stocks.length];
				for (int i = 0; i < stocks.length; i++){
					array[i] = toDto(stocks[i]);
				}
			}
			return new PortfolioDto(portfolio.getTitle(), portfolio.getBalance(), array);
		}
		
		//converts portfolioDto to Portfolio
		private Portfolio fromDto(PortfolioDto dto){
			StockDto[] stocks = dto.getStocks();
			Portfolio ret;
			
			if(stocks == null){
				ret = new Portfolio();
			}
			else{
				List<Stock> stockList = new ArrayList<Stock>();
				for (StockDto stockDto : stocks){
					stockList.add(fromDto(stockDto));
				}
				
				Stock[] stockArray = stockList.toArray(new Stock[stockList.size()]);
				ret = new Portfolio(stockArray);
			}
			ret.setTitle(dto.getTitle());
			try{
				ret.updateBalance(dto.getBalance());
			}
			catch (Exception e){
				e.printStackTrace();
			}
			return ret;
		}
		
		//convert List of Stocks to list of Stock DTO
		private List<StockDto > toDtoList(List<Stock> stocks){
			List<StockDto> ret = new ArrayList<StockDto>();
			for (Stock stockStatus : stocks){
				ret.add(toDto(stockStatus));
			}
			return ret;
		}
		
		//returns new instance of Portfolio from other instance
		public Portfolio duplicatePortfolio(Portfolio portfolio){
			Portfolio copyPortfolio = new Portfolio(portfolio);
			return copyPortfolio;
		}
	}