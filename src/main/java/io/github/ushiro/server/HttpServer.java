package io.github.ushiro.server;

import io.github.ushiro.Config;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

public class HttpServer {

    private static org.eclipse.jetty.server.Server httpServer;

    public static void start() throws Exception {
        if(httpServer == null || httpServer.isStopped()) {
            String httpHost = Config.getHttpHost();
            Integer httpPort = Integer.parseInt(Config.getHttpPort());
            Integer httpIdleTimeout = Integer.parseInt(Config.getHttpPort());

            httpServer = new org.eclipse.jetty.server.Server();
            ServerConnector httpConnector = new ServerConnector(httpServer);
            httpConnector.setHost(httpHost);
            httpConnector.setPort(httpPort);
            httpConnector.setIdleTimeout(httpIdleTimeout);

            ServletHandler servletHandler = new ServletHandler();
            servletHandler.addServletWithMapping(PingHandler.class, "/ping");
            servletHandler.addServletWithMapping(InfoHandler.class, "/info");
            servletHandler.addServletWithMapping(CreateHandler.class, "/create/*");
            servletHandler.addServletWithMapping(LookupHandler.class, "/*");

            httpServer.setHandler(servletHandler);
            httpServer.addConnector(httpConnector);
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

    public static void join() throws InterruptedException {
        httpServer.join();
    }
}
