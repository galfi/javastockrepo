package com.mta.javacourse.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//data members
public class Stock {
	
	private static final int BUY = 0;
	private static final int SELL = 1;
	private static final int REMOVE = 2;
	private static final int HOLD = 3;
	
	private String symbol;
	private float ask;
	private float bid;
	private Date date;
	private int recomandation;
	private int stockQuantity;

//constractor
public Stock(String symbol, float ask, float bid, Date date) {
	this.symbol = symbol;
	this.ask = ask;
	this.bid = bid;
	this.date = date;
}

//duplicate constructor stock
public Stock (Stock copyStock){
	this.symbol = copyStock.getSymbol();
	this.ask = copyStock.getAsk();
	this.bid = copyStock.getBid();
	this.date = copyStock.getDate();
	this.recomandation = copyStock.getRecomandation();
	this.stockQuantity = copyStock.getStockQuantity();
}

//methods
public String getSymbol() {
	return symbol;
}

public void setSymbol(String value) {
	this.symbol = value;
}

public float getAsk() {
	return ask;
}

public void setAsk(float ask) {
	this.ask = ask;
}

public float getBid() {
	return bid;
}

public void setBid(float bid) {
	this.bid = bid;
}

public void setDate(Date date) {
	this.date = date;
}

public Date getDate() {
	return date;
}

public String getHtmlDescription() {
	
	DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	String dateStr = df.format(getDate());
	
	//String ret = "<b>Name: </b>" + getName() + ", <b>Date: </b>" + getDate().getMonth() + "/" + getDate().getDate() + "/" + (1900 + getDate().getYear()) + ", &emsp;<br>Band: </b>" + band.getHtmlDescription();
	String ret = "<b>stock symbol: </b>" + symbol + "<b>,  ask: </b>" + ask + "<b>,  bid: </b>" + bid + "<b>,  Date: </b>" + dateStr + "<br>";
	
	return ret;
}

public int getRecomandation() {
	return recomandation;
}

public void setRecomandation(int recomandation) {
	this.recomandation = recomandation;
}

public int getStockQuantity() {
	return stockQuantity;
}

public void setStockQuantity(int stockQuantity) {
	this.stockQuantity = stockQuantity;
}

public static int getBuy() {
	return BUY;
}

public static int getSell() {
	return SELL;
}

public static int getRemove() {
	return REMOVE;
}

public static int getHold() {
	return HOLD;
}

}


