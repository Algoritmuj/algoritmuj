package org.algorithm.example.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.algorithm.example.api.Strava;

public class ApiServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6889876836819003746L;
       
    public ApiServlet() {
        super();
    }
    
    
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("activities", Strava.activities());
		req.getRequestDispatcher("strava.jsp").forward(req, resp);
	}
}
