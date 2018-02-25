package io.github.ushiro.server;

import io.github.ushiro.Config;
import io.github.ushiro.data.DataModel;
import io.github.ushiro.data.DataDriver;
import io.github.ushiro.utils.RandomGenerator;
import io.github.ushiro.utils.UrlValidator;
import org.json.JSONObject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * An HTTP servlet class which handles the short URL creation
 * request which is a POST method containing the long URL.
 * After validating the given long URL, generating a random
 * short URL, and storing it in the data layer, the handler
 * will respond with the short URL and some meta data.
 */
public class CreateHandler extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // Parse and validate the the given long URL
        String longUrl = request.getParameter("long-url");
        if(!UrlValidator.isUrlValid(longUrl)) {

            // Respond with BAD_REQUEST if the long URL is not valid
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;

        }

        // Generate meta data for the URL
        long timestamp = (new Date()).getTime();
        String keyUrl = RandomGenerator.generate();
        String baseUrl = "http://" + Config.getHttpHost() + ":" + Config.getHttpPort();
        String shortUrl = baseUrl + "/" + keyUrl;

        // Instantiate the data model and store it by data driver
        DataModel dataModel = new DataModel(keyUrl, longUrl, timestamp, 0);
        DataDriver.store(dataModel);

        // Create a JSON object of URL data and metadata
        JSONObject responseObject = new JSONObject();
        responseObject.put("key-url", keyUrl);
        responseObject.put("short-url", shortUrl);
        responseObject.put("long-url", longUrl);

        // Send the response with successful status code
        response.getWriter().print(responseObject.toString());
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
