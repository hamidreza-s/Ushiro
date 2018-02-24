package io.github.ushiro;

import io.github.ushiro.controller.InfoController;
import io.github.ushiro.controller.PingController;
import io.github.ushiro.controller.UrlController;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Handler extends AbstractHandler {

    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) {
        try {
            switch (request.getRequestURI()) {
                case "/ping":
                    PingController.handle(request, response);
                    break;
                case "/url":
                    UrlController.handle(request, response);
                    break;
                case "/info":
                    InfoController.handle(request, response);
                    break;
                default:
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }

        } catch(Exception exception) {
            exception.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        baseRequest.setHandled(true);
    }
}
