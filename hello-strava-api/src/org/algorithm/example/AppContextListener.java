package org.algorithm.example;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class AppContextListener
 *
 */
@WebListener
public class AppContextListener implements ServletContextListener {
	
    public AppContextListener() {
    }

    public void contextDestroyed(ServletContextEvent sce)  { 
         //RefreshTokenTask.getInstance().stop();
    }

    public void contextInitialized(ServletContextEvent sce)  { 
         //RefreshTokenTask.getInstance().start();
    }
	
}
