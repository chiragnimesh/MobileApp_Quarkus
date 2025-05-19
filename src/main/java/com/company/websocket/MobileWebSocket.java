package com.company.websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.inject.Singleton;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
//My new changes is here by chiggi boy bodmosh
@ServerEndpoint("/ws/mobiles")
@Singleton
public class MobileWebSocket {

    private static final Set<Session> sessions = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        sessions.remove(session);
        throwable.printStackTrace();
    }

    public static void broadcast(String message) {
        for (Session session : sessions) {
            if (session.isOpen()) {
                session.getAsyncRemote().sendText(message);
            }
        }
    }
}
