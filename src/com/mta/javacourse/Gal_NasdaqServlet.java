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
	}
}
