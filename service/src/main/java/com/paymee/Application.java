package com.paymee;

/**
 * Created by alex on 28/03/2015.
 */

import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.http.server.HttpServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

public class Application {

    private final HttpServer httpServer;

    private final ApplicationContext context;

    public Application() {
        context = new AnnotationConfigApplicationContext("com.paymee");
        httpServer = ServerBuilder.createBuilder(context).build();
    }

    public void start() throws IOException {
        httpServer.start();
    }

    public GrizzlyFuture<HttpServer> stop() {
        return httpServer.shutdown();
    }

    public static void main(String[] args) throws Exception {
        Application application = new Application();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                GrizzlyFuture<HttpServer> shutdown = application.stop();
                System.out.print("Waiting for server to shutdown.");
                while (!shutdown.isDone()) {
                    try {
                        Thread.sleep(1000);
                        System.out.print(".");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(" Shutdown completed.");
            }
        }));
        try {
            application.start();
            System.out.println("Server started: ctrl-c to stop");
            Thread.currentThread().join();
        } catch (Exception e) {
            System.exit(1);
        }
    }
}
