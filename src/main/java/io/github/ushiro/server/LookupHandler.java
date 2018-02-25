package io.github.ushiro.server;

import io.github.ushiro.cache.Cache;
import io.github.ushiro.data.DataCache;
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

        Cache<String, DataModel> dataCache = DataCache.getInstance();

        if(dataCache.contains(keyUrl)) {

            DataModel dataModel = dataCache.get(keyUrl);
            dataModel.incrementViewCount();
            DataDriver.store(dataModel);
            dataCache.put(dataModel.getKeyUrl(), dataModel);
            response.sendRedirect(dataModel.getLongUrl());

        } else {

            DataModel dataModel = DataDriver.retrieve(keyUrl);
            if (dataModel != null) {
                dataModel.incrementViewCount();
                DataDriver.store(dataModel);
                dataCache.put(dataModel.getKeyUrl(), dataModel);
                response.sendRedirect(dataModel.getLongUrl());
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }

        }
    }
}