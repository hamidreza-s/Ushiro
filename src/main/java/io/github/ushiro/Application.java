package io.github.ushiro;

import io.github.ushiro.server.Server;
import io.github.ushiro.data.DataDriver;


public class Application
{
    private final static int EXIT_CODE_SUCCESS = 0;
    private final static int EXIT_CODE_FAILURE = 1;
    private static org.eclipse.jetty.server.Server httpServer;

    public static void start() throws Exception {
        DataDriver.start();
        Server.start();
    }

    public static void stop() throws Exception {
        DataDriver.stop();
        Server.stop();
    }

    public static void main(String[] args) {
        try {
            start();
            Server.join();
        } catch (Exception exception) {
            exception.printStackTrace();
            System.exit(EXIT_CODE_FAILURE);
        }
    }
}
