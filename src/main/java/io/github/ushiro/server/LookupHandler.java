package io.github.ushiro.server;

import io.github.ushiro.data.DataDriver;
import io.github.ushiro.data.DataModel;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LookupHandler extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String requestPath = request.getPathInfo();
        String keyUrl = requestPath.substring(1);

        DataModel dataModel = DataDriver.retrieve(keyUrl);
        if(dataModel != null) {
            response.sendRedirect(dataModel.getLongUrl());
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}