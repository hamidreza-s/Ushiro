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
public class UshiroControllerTest {

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
        assertEquals(response.getStatus(), HttpServletResponse.SC_OK);
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
        assertEquals(lookupResponse.getStatus(), HttpServletResponse.SC_OK);
        assertTrue(lookupResponse.getContentAsString().equals("pong"));
    }

    @Test
    public void testValidURL() throws Exception {

    }


}