package com.mta.javacourse.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mta.javacourse.model.Portfolio.ALGO_RECOMMENDATION;

//data members
public class Stock {

	private String symbol;
	private float ask;
	private float bid;
	private Date date;
	private ALGO_RECOMMENDATION recommendation;
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
	this.recommendation = copyStock.getRecommendation();
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
	String ret = "<b>stock symbol: </b>" + symbol + "<b>,  ask: </b>" + ask + "<b>,  bid: </b>" + bid + "<b>, quantity: </b>" + stockQuantity + "<b>,  Date: </b>" + dateStr + "<br>";
	
	return ret;
}

public ALGO_RECOMMENDATION getRecommendation() {
	return recommendation;
}

public void setRecommendation(ALGO_RECOMMENDATION recommendation) {
	this.recommendation = recommendation;
}

public int getStockQuantity() {
	return stockQuantity;
}

public void setStockQuantity(int stockQuantity) {
	this.stockQuantity = stockQuantity;
}

}


