package io.github.ushiro.server;

import io.github.ushiro.Config;
import io.github.ushiro.data.DataModel;
import io.github.ushiro.data.DataDriver;
import io.github.ushiro.utils.RandomGenerator;
import io.github.ushiro.utils.UrlValidator;
import org.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class CreateHandler extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println(request);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String longUrl = request.getParameter("long-url");
        if(!UrlValidator.isUrlValid(longUrl)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        long timestamp = (new Date()).getTime();

        String keyUrl = RandomGenerator.generate();
        String baseUrl = "http://" + Config.getHttpHost() + ":" + Config.getHttpPort();
        String shortUrl = baseUrl + "/" + keyUrl;

        DataModel dataModel = new DataModel(keyUrl, longUrl, timestamp, 0);
        DataDriver.store(dataModel);

        JSONObject responseJson = new JSONObject();
        responseJson.put("short-url", shortUrl);
        responseJson.put("long-url", longUrl);
        response.getWriter().print(responseJson.toString());
        response.setStatus(HttpServletResponse.SC_OK);
    }
}