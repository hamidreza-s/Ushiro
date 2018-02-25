package io.github.ushiro;

import io.github.ushiro.server.HttpServer;
import io.github.ushiro.data.DataDriver;

/**
 * The application class containing main, start, and stop
 * methods for controlling the service life cycle.
 */
public class Application {
    private final static int EXIT_CODE_SUCCESS = 0;
    private final static int EXIT_CODE_FAILURE = 1;

    /**
     * A static method for starting the application which
     * starts both HTTP server and data driver.
     *
     * @throws Exception
     */
    public static void start() throws Exception {
        DataDriver.start();
        HttpServer.start();
    }

    /**
     * A static method for stopping the application which
     * stops both HTTP server and data driver.
     *
     * @throws Exception
     */
    public static void stop() throws Exception {
        DataDriver.stop();
        HttpServer.stop();
    }

    /**
     * The main method for running the whole application
     *
     * @param args command-line arguments
     */
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
