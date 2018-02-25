package io.github.ushiro.server;

import io.github.ushiro.Config;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * The class is responsible for starting and stopping the
 * HTTP server. Also, it sets the servlet handler mappings and
 * its connector. The host, port, and other configurations are
 * retrieved from config file. It keeps the server instance
 * as a singleton object in a static field.
 */
public class HttpServer {

    private static Server httpServer;

    /**
     * Configure and start the HTTP server if it not started
     *
     * @throws Exception
     */
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
            servletHandler.addServletWithMapping(InfoHandler.class, "/info/*");
            servletHandler.addServletWithMapping(CreateHandler.class, "/create/*");
            servletHandler.addServletWithMapping(LookupHandler.class, "/*");

            httpServer.setHandler(servletHandler);
            httpServer.addConnector(httpConnector);
            httpServer.start();
            System.out.printf("Ushiro has been started on %d port!\n", httpPort);
        }
    }

    /**
     * Stop the HTTP server if it is running
     *
     * @throws Exception
     */
    public static void stop() throws Exception {
        if(httpServer != null || httpServer.isRunning()) {
            httpServer.stop();
            System.out.println("Ushiro has been stopped!");
        }
    }

    /**
     * Keep the tread waiting to handle HTTP requests
     *
     * @throws InterruptedException
     */
    public static void join() throws InterruptedException {
        httpServer.join();
    }
}
