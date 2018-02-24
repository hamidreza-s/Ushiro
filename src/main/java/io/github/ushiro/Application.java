package io.github.ushiro;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

public class Application
{
    private final static int EXIT_CODE_SUCCESS = 0;
    private final static int EXIT_CODE_FAILURE = 1;
    private static Server httpServer;

    public static void start() throws Exception {
        if(httpServer == null || httpServer.isStopped()) {
            String httpHost = Config.getHttpHost();
            Integer httpPort = Integer.parseInt(Config.getHttpPort());
            Integer httpIdleTimeout = Integer.parseInt(Config.getHttpPort());

            httpServer = new Server();
            Handler httpHandler = new Handler();
            ServerConnector httpConnector = new ServerConnector(httpServer);
            httpConnector.setHost(httpHost);
            httpConnector.setPort(httpPort);
            httpConnector.setIdleTimeout(httpIdleTimeout);

            httpServer.addConnector(httpConnector);
            httpServer.setHandler(httpHandler);
            httpServer.start();
            System.out.printf("Ushiro has been started on %d port!\n", httpPort);
        }

    }

    public static void stop() throws Exception {
        if(httpServer != null || httpServer.isRunning()) {
            httpServer.stop();
            System.out.println("Ushiro has been stopped!");
        }
    }

    public static void main(String[] args) {
        try {
            start();
            httpServer.join();
        } catch (Exception exception) {
            exception.printStackTrace();
            System.exit(EXIT_CODE_FAILURE);
        }
    }
}
