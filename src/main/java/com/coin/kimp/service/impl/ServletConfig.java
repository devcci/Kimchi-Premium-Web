package com.coin.kimp.service.impl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
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
    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory(){
    @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
    tomcat.addAdditionalTomcatConnectors(createStandardConnector());
    return tomcat;
    }

    private Connector createStandardConnector() {
    Connector connector = new
    Connector("org.apache.coyote.http11.Http11NioProtocol");
    connector.setScheme("http");
    connector.setSecure(false);
    connector.setPort(8080);
    connector.setRedirectPort(8443);
    return connector;
    }
}
