package com.digyai.notificationservice.websocket;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

public class UserHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(
            ServerHttpRequest request,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) {

        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpSession session = servletRequest
                    .getServletRequest()
                    .getSession(false);

            if (session != null) {
                String userId = (String) session.getAttribute("USER_ID");
                if (userId != null) {
                    return () -> userId; // 🔥 THIS IS THE KEY
                }
            }
        }
        return null;
    }
}
