package com.mta.javacourse.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mta.javacourse.model.Portfolio;
import com.mta.javacourse.service.PortfolioManager;

@SuppressWarnings("serial")
public class PortfolioServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		resp.setContentType("text/html");
		
		String printStr = "<b> **First print** </b>";
		
		PortfolioManager pManager = new PortfolioManager();
		Portfolio portfolio1 = pManager.getPortfolio();
		Portfolio portfolio2 = new Portfolio(portfolio1);
		
		portfolio2.setTitle("Portfolio 2");
		
		printStr += portfolio1.getHtmlString();
		printStr += portfolio2.getHtmlString();
		
		portfolio1.removeStock(portfolio1.getStocks()[0].getSymbol());
		
		printStr += "<br><b> **Second print** </b>";
		printStr += portfolio1.getHtmlString();
		printStr += portfolio2.getHtmlString();
		
		portfolio2.getStockByIndex(2).setBid((float)55.55);
		
		printStr += "<br><b> **Third print** </b>";
		printStr += portfolio1.getHtmlString();
		printStr += portfolio2.getHtmlString();
		
		resp.getWriter().println(printStr);
	
	}
}
