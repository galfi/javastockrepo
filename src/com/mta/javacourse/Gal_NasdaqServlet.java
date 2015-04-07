package com.mta.javacourse;
import java.io.IOException;
import javax.servlet.http.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Gal_NasdaqServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		resp.getWriter().println("Hello my new Servlet");
		
		int radius;
		double pi;
		
		radius=50;
		pi=1.4;
		double result1= radius * pi;
		
		String line1= new String ("<h1> Calculation 1: Area of circle with radius: "+radius+" * "+pi+" = "+result1+" </h1>");	
		resp.getWriter().println(line1);
		
		int opposite;
		int hypotenuse;
			
		opposite=30;
		hypotenuse=50;
		int result2= opposite / hypotenuse ;
		
		String line2= new String ("<h1> Calculation 2: Length of opposite where angle B is 30 degrees and Hypotenuse length is 50 cm is: "+opposite+" / "+hypotenuse+" = "+result2+"  cm </h1>");	
		resp.getWriter().println(line2);
		
		int base;
		int exp;
		
		base=20;
		exp=13;
		double result3= base ^ exp;
		
		String line3= new String ("<h1> Calculation 3: Power of 20 with exp of 13 is: "+base+" ^ "+exp+" = "+result3+"  cm </h1>");	
		resp.getWriter().println(line3);
		
		String resultStr= line1 + "<br>" + line2 + "<br>" +line3;
		resp.getWriter().println (resultStr);
		
		
	}
}
