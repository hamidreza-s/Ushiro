package io.github.ushiro;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.json.JSONObject;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.junit.*;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UshiroServerApiTest {

    private static HttpClient httpClient = new HttpClient(new SslContextFactory());
    private static String serverAddress = "http://" + Config.getHttpHost() + ":" + Config.getHttpPort();

    @BeforeClass
    public static void beforeSuite() throws Exception {
        httpClient.start();
        Application.start();
    }

    @AfterClass
    public static void afterSuite() throws Exception {
        httpClient.stop();
        Application.stop();
    }

    @Test
    public void testPingRequest() throws Exception {
        ContentResponse response = httpClient.GET(serverAddress + "/ping");
        assertEquals(response.getStatus(), HttpServletResponse.SC_OK);
        assertTrue(response.getContentAsString().equals("pong"));
    }

    @Test
    public void testUrlCreateRequest() throws Exception {
        ContentResponse response = httpClient.POST(serverAddress + "/create")
                .param("long-url", "http://www.dice.se")
                .send();
        JSONObject responseObject = new JSONObject(response.getContentAsString());
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertTrue(responseObject.has("long-url"));
        assertTrue(responseObject.has("short-url"));
    }

    @Test
    public void testUrlLookupRequest() throws Exception {
        ContentResponse createResponse = httpClient.POST(serverAddress + "/create")
                .param("long-url", serverAddress + "/ping")
                .send();
        JSONObject createResponseObject = new JSONObject(createResponse.getContentAsString());
        String shortUrl = createResponseObject.getString("short-url");

        ContentResponse lookupResponse = httpClient.GET(shortUrl);
        assertEquals(HttpServletResponse.SC_OK, lookupResponse.getStatus());
        assertTrue(lookupResponse.getContentAsString().equals("pong"));
    }

    @Test
    public void testInfoRequest() throws Exception {
        ContentResponse createResponse = httpClient.POST(serverAddress + "/create")
                .param("long-url", "http://www.ea.com")
                .send();
        JSONObject createResponseObject = new JSONObject(createResponse.getContentAsString());
        String keyUrl = createResponseObject.getString("key-url");
        String shortUrl = createResponseObject.getString("short-url");


        int expectedViewCount = 10;
        for(int i = 1; i < expectedViewCount; i++) {
            ContentResponse lookupResponse = httpClient.GET(shortUrl);
            assertEquals(HttpServletResponse.SC_OK, lookupResponse.getStatus());
        }

        ContentResponse infoResponse = httpClient.GET(serverAddress + "/info/" + keyUrl);
        JSONObject infoResponseObject = new JSONObject(infoResponse.getContentAsString());
        assertEquals(HttpServletResponse.SC_OK, infoResponse.getStatus());
        assertEquals(expectedViewCount, infoResponseObject.getInt("view-count"));
        assertTrue(keyUrl.equals(infoResponseObject.getString("key-url")));
    }

}