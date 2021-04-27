package com.coin.kimp.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;

@WebListener
@Configuration
public class SessionConfig implements HttpSessionListener, ServletContextListener {
    private static final Map<String, HttpSession> sessions = new ConcurrentHashMap<>();
    static private int activeSessions = 0;

    public static int getActiveSessions() {
        return activeSessions;
    }

    public void contextInitialized(ServletContextEvent sce) throws RuntimeException {
        ServletContext context = sce.getServletContext();
        System.out.println("!!!!!웹 어플리케이션 시작!!!!!");
        System.out.println("서버 정보 : " + context.getServerInfo());
    }

    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("!!!!!웹 어플리케이션 종료!!!!!");
    }

    @Override
    public void sessionCreated(HttpSessionEvent sessionEve) {
        activeSessions++;
        System.out.println("+++세션 시작 SessionCnt:" + activeSessions + " Session ID"
                .concat(sessionEve.getSession().getId()).concat(" created at ").concat(new Date().toString()));
        sessions.put(sessionEve.getSession().getId(), sessionEve.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEve) {
        System.out.println("접속자 종료");
        activeSessions--;
        if (sessions.get(sessionEve.getSession().getId()) != null) {
            sessions.get(sessionEve.getSession().getId()).invalidate();
            System.out.println("---세션 종료 SessionCnt:" + activeSessions + " Session ID"
                    .concat(sessionEve.getSession().getId()).concat(" created at ").concat(new Date().toString()));
            sessions.remove(sessionEve.getSession().getId());
        }
    }
}
