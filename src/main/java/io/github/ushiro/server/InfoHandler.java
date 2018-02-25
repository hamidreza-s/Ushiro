package io.github.ushiro.server;

import io.github.ushiro.data.DataDriver;
import io.github.ushiro.data.DataModel;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class InfoHandler extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String requestPath = request.getPathInfo();
        String keyUrl = requestPath.substring(1);

        DataModel dataModel = DataDriver.retrieve(keyUrl);
        if (dataModel != null) {
            JSONObject responseObject = new JSONObject();
            responseObject.put("key-url", keyUrl);
            responseObject.put("long-url", dataModel.getLongUrl());
            responseObject.put("created-at", dataModel.getCreatedAt());
            responseObject.put("view-count", dataModel.getViewCount());
            response.getWriter().print(responseObject.toString());
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

    }
}