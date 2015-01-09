package com.wokonn.luna.mail;

import com.wokonn.luna.mail.config.MVCConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Created by neo on 13/10/14.
 */
public class MailServer {

    public static void main(String[] args) throws Exception {

        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(MVCConfig.class);

        ServletHolder servletHolder = new ServletHolder(new DispatcherServlet(applicationContext));
        ServletContextHandler context = new ServletContextHandler();

        context.setErrorHandler(null); // use Spring exception handler(s)
        context.setContextPath("/");
        context.addServlet(servletHolder, "/");

        String webPort = System.getenv("VCAP_APP_PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "13002";
        }
        Server server = new Server(Integer.valueOf(webPort));
        server.setHandler(context);
        server.start();
        server.join();
    }
}
