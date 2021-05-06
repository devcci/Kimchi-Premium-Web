package com.coin.kimp.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionConfig implements HttpSessionListener {
    private static final Map<String, HttpSession> sessions = new ConcurrentHashMap<>();
    static private int activeSessions = 0;

    public static int getActiveSessions() {
        return activeSessions;
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
        activeSessions--;
        if (sessions.get(sessionEve.getSession().getId()) != null) {
            sessions.get(sessionEve.getSession().getId()).invalidate();
            System.out.println("---세션 종료 SessionCnt:" + activeSessions + " Session ID"
                    .concat(sessionEve.getSession().getId()).concat(" created at ").concat(new Date().toString()));
            sessions.remove(sessionEve.getSession().getId());
        }
    }

}
