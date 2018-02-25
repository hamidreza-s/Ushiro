package io.github.ushiro.server;

import io.github.ushiro.cache.Cache;
import io.github.ushiro.data.DataCache;
import io.github.ushiro.data.DataDriver;
import io.github.ushiro.data.DataModel;
import org.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * An HTTP servlet class which handles getting the URL information
 * based on its short URL. It returns a JSON object containing
 * long URL, creation time, and view-count. It tries to retrieve
 * data from cache if exists, otherwise it retrieves the data
 * from database and stores it in cache before responding.
 */
public class InfoHandler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Parse the key of the short URL
        String requestPath = request.getPathInfo();
        String keyUrl = requestPath.substring(1);

        // Get the singleton instance of cache and check if it contains the data
        Cache<String, DataModel> dataCache = DataCache.getInstance();
        if(dataCache.contains(keyUrl)) {

            // Get the data from cache
            DataModel dataModel = dataCache.get(keyUrl);

            // Create a JSON object of the URL information
            JSONObject responseObject = createResponse(dataModel);

            // Send the URL information with successful status code
            response.getWriter().print(responseObject.toString());
            response.setStatus(HttpServletResponse.SC_OK);

        } else {

            // Retrieve the data from database
            DataModel dataModel = DataDriver.retrieve(keyUrl);
            if (dataModel != null) {

                // Put the retrieved data in cache for future usage
                dataCache.put(dataModel.getKeyUrl(), dataModel);

                // Create a JSON object of the URL information
                JSONObject responseObject = createResponse(dataModel);

                // Send the URL information with successful status code
                response.getWriter().print(responseObject.toString());
                response.setStatus(HttpServletResponse.SC_OK);

            } else {

                // Respond with NOT_FOUND if the URL does not exist
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);

            }
        }
    }

    /**
     * Create JSON response from data model
     *
     * @param dataModel A data model object for sending as response
     * @return Generated JSON object
     */
    private JSONObject createResponse(DataModel dataModel) {
        JSONObject responseObject = new JSONObject();
        responseObject.put("key-url", dataModel.getKeyUrl());
        responseObject.put("long-url", dataModel.getLongUrl());
        responseObject.put("created-at", dataModel.getCreatedAt());
        responseObject.put("view-count", dataModel.getViewCount());
        return responseObject;
    }
}
