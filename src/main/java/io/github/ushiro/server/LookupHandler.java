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

/**
 * An HTTP servlet class which handles the short URL lookup
 * request which is a GET method containing the key of long URL.
 * The long URL can be retrieved from cache if it exists there,
 * otherwise it will be retrieved from database by the data driver.
 * After finding the long URL, the handler responds with a redirect
 * code and URL which will cause the HTTP client being forwarded to
 * the long URL.
 */
public class LookupHandler extends HttpServlet {

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

            // Increment the view-count in model
            dataModel.incrementViewCount();

            // Update the incremented view-count of URL in database
            DataDriver.store(dataModel);

            // Update the incremented view-count of URL in cache
            dataCache.put(dataModel.getKeyUrl(), dataModel);

            // Redirect to the long URL
            response.sendRedirect(dataModel.getLongUrl());

        } else {

            // Retrieve the data from database
            DataModel dataModel = DataDriver.retrieve(keyUrl);
            if (dataModel != null) {

                // Increment the view-count in model
                dataModel.incrementViewCount();

                // Update the incremented view-count of URL in database
                DataDriver.store(dataModel);

                // Put the retrieved data in cache for future usage
                dataCache.put(dataModel.getKeyUrl(), dataModel);

                // Redirect to the long URL
                response.sendRedirect(dataModel.getLongUrl());

            } else {

                // Respond with NOT_FOUND if the URL does not exist
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }

        }
    }
}
