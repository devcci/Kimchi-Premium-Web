package com.coin.kimp.service.impl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfig implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) throws RuntimeException {
        ServletContext context = sce.getServletContext();
        System.out.println("!!!!!웹 어플리케이션 시작!!!!!");
        System.out.println("서버 정보 : " + context.getServerInfo());
    }

    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("!!!!!웹 어플리케이션 종료!!!!!");
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
    tomcat.addAdditionalTomcatConnectors(createStandardConnector());
    return tomcat;
    }

    private Connector createStandardConnector() {
    Connector connector = new
    Connector("org.apache.coyote.http11.Http11NioProtocol");
    connector.setPort(8080);
    return connector;
    }
}
