package com.mta.javacourse;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;

@SuppressWarnings("serial")
public class StockDetailsServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		resp.setContentType("text/html");
		Calendar cal = Calendar.getInstance();
									
		cal.set(2014, 10, 15);
		Date date1 = cal.getTime();

		cal.set(2014, 10, 15);
		Date date2 = cal.getTime();
		
		cal.set(2014, 10, 15);
		Date date3 = cal.getTime();
		
		//Stock 1
		float ask1= (float) 13.1;
		float bid1= (float) 12.4;
		Stock stock1= new Stock ("PIH",ask1,bid1,date1);
		
		//Stock 2
		float ask2= (float) 5.78;
		float bid2= (float) 5.5;
		Stock stock2= new Stock ("AAL",ask2,bid2,date2);
	
		//Stock 3
		float ask3= (float) 32.2;
		float bid3= (float) 31.5;
		Stock stock3= new Stock ("CAAS",ask3,bid3,date3);
		
		resp.getWriter().println(stock1.getHtmlDescription());
		resp.getWriter().println(stock2.getHtmlDescription());
		resp.getWriter().println(stock3.getHtmlDescription());

	}
}
