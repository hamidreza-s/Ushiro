package io.github.ushiro;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.junit.*;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;
import javax.servlet.http.HttpServletResponse;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UshiroControllerTest {

    private static HttpClient httpClient = new HttpClient();
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
        assertTrue(response.getContentAsString().equals("pong"));
        assertEquals(response.getStatus(), HttpServletResponse.SC_OK);
    }

}