package io.github.ushiro.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PingController {

    public static void handle(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print("pong");
    }
}
