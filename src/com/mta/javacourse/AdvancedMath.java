package com.mta.javacourse;
import java.io.IOException;

import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings({ "serial", "unused" })
public class AdvancedMath extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		resp.getWriter().println("Hello my new Servlet");
		
		int radius;
		double pi;
		
		radius=50;
		pi=Math.PI;
		double result1= radius * radius * pi;
		
		String line1= new String ("<h1> Calculation 1: Area of circle with radius: "+radius+" * "+radius+" * "+pi+" = "+result1+" </h1>");	
		
		double opposite;
		double hypotenuse;
		double result2;
		double B=30;
		double B_radian= Math.toRadians(B);
			
		hypotenuse=50;
		opposite=hypotenuse * Math.sin(B_radian);
		
		result2=opposite;
		
		String line2= new String ("<h1> Calculation 2: Length of opposite where angle B is 30 degrees and Hypotenuse length is 50 cm is: "+result2+"  radians </h1>");	
		
		double base;
		double exp;
		
		base=20;
		exp=13;
		double result3= java.lang.Math.pow(20, 13);
		
		String line3= new String ("<h1> Calculation 3: Power of 20 with exp of 13 is: "+base+" ^ "+exp+" = "+result3+"  cm </h1>");	
		
		String resultStr= line1 + "<br>" + line2 + "<br>" +line3;
		resp.getWriter().println (resultStr);
		
		
	}

	private void toRadians(double result2) {
		// TODO Auto-generated method stub
		
	}
}
