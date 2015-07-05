package com.paymee;

import jersey.repackaged.com.google.common.base.Preconditions;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.grizzly.http.server.StaticHttpHandlerBase;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.ApplicationContext;

import javax.ws.rs.Path;
import java.net.URI;
import java.util.Map;

public class ServerBuilder {

    private final String BASE_URI_CONFIG = "grizzly.baseUri";
    private final String STATIC_HTTP_DOC_ROOT = "grizzly.static.http.docRoot";
    private final String STATIC_HTTP_CONTEXT = "grizzly.static.http.context";

    private final ApplicationContext springContext;

    private ServerBuilder(ApplicationContext springContext) {
        this.springContext = springContext;
    }

    public static ServerBuilder createBuilder(ApplicationContext springContext) {
        Preconditions.checkNotNull(springContext, "Exception when building Grizzly server. Spring context must not be null.");
        return new ServerBuilder(springContext);
    }

    /**
     * Builds a Grizzly Jersey REST server.
     *
     * @return a Grizzly {@link org.glassfish.grizzly.http.server.HttpServer}
     */
    public HttpServer build() {
        final String baseUri = springContext.getEnvironment().getProperty(BASE_URI_CONFIG);
        Preconditions.checkNotNull(baseUri, "Exception when building Grizzly server. Base URI must not be null.");
        final String staticHttpContext = springContext.getEnvironment().getProperty(STATIC_HTTP_CONTEXT);
        Preconditions.checkNotNull(staticHttpContext, "Exception when building Grizzly server. Static http context must not be null.");
        final HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(
                URI.create(baseUri),
                ResourceConfigBuilder.createBuilder(springContext)
                        .configureSpringManagedResources()
                        .build());
        httpServer.getServerConfiguration()
                .addHttpHandler(createStaticHttpHandler(), staticHttpContext);
        return httpServer;
    }

    private StaticHttpHandlerBase createStaticHttpHandler() {
        final String docRoot = springContext.getEnvironment().getProperty(STATIC_HTTP_DOC_ROOT);
        Preconditions.checkNotNull(docRoot, "Exception when building Grizzly server. Static http doc root must not be null.");
        return new StaticHttpHandler(docRoot);
    }

    /**
     * A private {@link org.glassfish.jersey.server.ResourceConfig} builder class
     * to configure the server.
     */
    private static class ResourceConfigBuilder {

        private final ApplicationContext springContext;
        private final ResourceConfig resourceConfig;

        private ResourceConfigBuilder(ApplicationContext springContext) {
            this.springContext = springContext;
            this.resourceConfig = new ResourceConfig();
        }

        public static ResourceConfigBuilder createBuilder(ApplicationContext springContext) {
            return new ResourceConfigBuilder(springContext);
        }

        /**
         * Given a Spring context, finds all the beans annotated with {@link javax.ws.rs.Path},
         * registers them as Jersey resources and returns the builder.
         */
        public ResourceConfigBuilder configureSpringManagedResources() {
            final Map<String, Object> beansWithAnnotation = springContext.getBeansWithAnnotation(Path.class);
            for (String beanName : beansWithAnnotation.keySet()) {
                Object resource = beansWithAnnotation.get(beanName);
                resourceConfig.register(resource);
            }
            return this;
        }

        public ResourceConfig build() {
            return resourceConfig;
        }

    }
}
