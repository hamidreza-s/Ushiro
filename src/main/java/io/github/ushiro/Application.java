package io.github.ushiro;

import io.github.ushiro.server.HttpServer;
import io.github.ushiro.data.AccessLayer;


public class Application
{
    private final static int EXIT_CODE_SUCCESS = 0;
    private final static int EXIT_CODE_FAILURE = 1;
    private static org.eclipse.jetty.server.Server httpServer;

    public static void start() throws Exception {
        AccessLayer.start();
        HttpServer.start();
    }

    public static void stop() throws Exception {
        AccessLayer.stop();
        HttpServer.stop();
    }

    public static void main(String[] args) {
        try {
            start();
            HttpServer.join();
        } catch (Exception exception) {
            exception.printStackTrace();
            System.exit(EXIT_CODE_FAILURE);
        }
    }
}
